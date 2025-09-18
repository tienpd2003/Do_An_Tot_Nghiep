package com.shop.payment.service.impl;

import com.shop.payment.entity.UserBalance;
import com.shop.payment.repository.UserBalanceRepository;
import com.shop.payment.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vuongdev.common.exception.AppException;
import org.vuongdev.common.exception.ErrorCodeUtils;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
  private final UserBalanceRepository userBalanceRepository;

  @Transactional
  @Override
  public boolean deductBalance(Long userId, Long amount) {
    UserBalance ub = userBalanceRepository.findByUserId((userId)).orElseThrow(
        () -> new AppException(ErrorCodeUtils.ERROR)
    );
    if (ub.getBalance() < amount) {
      return false;
    }

    ub.setBalance(ub.getBalance() - amount);
    userBalanceRepository.save(ub);
    return true;
  }

  /**
   * Rollback (compensation) nếu service khác thất bại
   */
  @Override
  @Transactional
  public void refundBalance(Long userId, Long amount) {
    UserBalance ub = userBalanceRepository.findByUserId((userId)).orElseThrow(
            () -> new AppException(ErrorCodeUtils.ERROR)
    );
    ub.setBalance(ub.getBalance() + amount);
    userBalanceRepository.save(ub);
  }
}
