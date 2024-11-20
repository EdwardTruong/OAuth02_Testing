package com.example.oauth02.service;

import java.util.List;

import com.example.oauth02.entity.UserEntity;

public interface UserService {
  List<UserEntity> loadAll();

  UserEntity findById(Integer id);

  void save(UserEntity entity);

  void update(UserEntity entity);

  void delete(UserEntity entity);
}
