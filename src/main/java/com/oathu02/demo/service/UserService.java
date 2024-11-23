package com.oathu02.demo.service;

import java.util.List;

import com.oathu02.demo.entity.UserEntity;

public interface UserService {
  List<UserEntity> loadAll();

  UserEntity findById(Integer id);

  void save(UserEntity entity);

  void update(UserEntity entity);

  void delete(UserEntity entity);
}
