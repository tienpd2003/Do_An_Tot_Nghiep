package com.shop.auth.service;

import com.shop.auth.entity.User;

import java.util.List;

public interface IUserService {
  List<User> getUsersByIds(List<Long> ids);
}
