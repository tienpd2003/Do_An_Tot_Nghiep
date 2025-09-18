package com.shop.payment.kafka;

import com.shop.payment.config.KafkaConfig;
import com.shop.payment.dto.event.CompensatePaymentEvent;
import com.shop.payment.dto.event.PaymentEvent;
import com.shop.payment.dto.request.ProcessPaymentRequest;
import com.shop.payment.entity.UserBalance;
import com.shop.payment.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.vuongdev.common.utils.ObjectMapperUtils;

@Service
@RequiredArgsConstructor
public class PaymentSagaHandler {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final RedisTemplate<String, Object> redis;
  private final UserBalanceRepository userBalanceRepository;

  private String balanceKey(Long userId) {
    return "balance:" + userId;
  }

  private String statusKey(String ticketId) {
    return "order:status:" + ticketId;
  }

  @KafkaListener(topics = KafkaConfig.ORDER_CMD_PROCESS_PAYMENT, groupId = "payment-service")
  @SneakyThrows
  public void onProcessPayment(String data) {

    ProcessPaymentRequest cmd = ObjectMapperUtils.convertToObject(data, ProcessPaymentRequest.class);

    assert cmd != null;
    Object balanceObj = redis.opsForValue().get(balanceKey(cmd.getUserId()));
    Long cached = balanceObj == null ? null : Long.valueOf(balanceObj.toString());
    Long amount = cmd.getAmount();
    Long balance;
    if (cached == null) {
      UserBalance ub = userBalanceRepository.findByUserId(cmd.getUserId()).orElseGet(() -> {
        UserBalance x = new UserBalance();
        x.setUserId(cmd.getUserId());
        x.setBalance(0L);
        return userBalanceRepository.save(x);
      });
      balance = ub.getBalance();
      redis.opsForValue().set(balanceKey(cmd.getUserId()), balance);
    } else {
      balance = cached;
    }

    if (balance < amount) {
      PaymentEvent event = new PaymentEvent(cmd.getTicketId());
      String msg = ObjectMapperUtils.convertToJson(event);
      kafkaTemplate.send(KafkaConfig.PAYMENT_EVT_FAILED, msg);
      return;
    }

    long newBalance = balance - amount;
    redis.opsForValue().set(balanceKey(cmd.getUserId()), newBalance);
    userBalanceRepository.findByUserId(cmd.getUserId()).ifPresent(ub -> {
      ub.setBalance(newBalance);
      userBalanceRepository.save(ub);
    });

    PaymentEvent event = PaymentEvent.builder()
            .ticketId(cmd.getTicketId())
            .build();
    kafkaTemplate.send(KafkaConfig.PAYMENT_EVT_COMPLETED, ObjectMapperUtils.convertToJson(event));
  }

  //roll back
  @KafkaListener(topics = KafkaConfig.ORDER_CMD_COMPENSATE_PAYMENT, groupId = "payment-service")
  @SneakyThrows
  public void compensate(String cmd) {
    CompensatePaymentEvent data = ObjectMapperUtils.convertToObject(cmd, CompensatePaymentEvent.class);
    assert data != null;
    UserBalance ub = userBalanceRepository.findByUserId(data.getUserId()).get();
    ub.setBalance(ub.getBalance() + data.getAmount());
    redis.opsForValue().set(balanceKey(data.getUserId()), ub.getBalance());
    userBalanceRepository.save(ub);
  }
}
