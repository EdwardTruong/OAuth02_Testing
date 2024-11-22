package com.example.oauth02.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oauth02.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
Optional<UserEntity> findUserByEmail(String email);

Boolean existsByEmail(String email);

}