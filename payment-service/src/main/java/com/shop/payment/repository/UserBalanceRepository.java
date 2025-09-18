package com.shop.payment.repository;

import com.shop.payment.entity.UserBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends MongoRepository<UserBalance,String> {

  Optional<UserBalance> findByUserId(Long userId);
}
