package com.example.oauth02.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.oauth02.entity.RoleEntity;
import com.example.oauth02.util.ERole;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName(ERole name);
}
