package com.oathu02.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oathu02.demo.entity.RoleEntity;
import com.oathu02.demo.util.ERole;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName(ERole name);
}
