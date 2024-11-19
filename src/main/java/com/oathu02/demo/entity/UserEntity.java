package com.oathu02.demo.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "email")
  @NotBlank(message = "Nhập địa chỉ mail")
  @Email(message = "Địa chỉ mail không đúng định dạng !", regexp = "^[\\w-\\+]+(\\.[\\w-\\+]+)*@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,6}$")
  String email;

  @Column(name = "username")
  String username;

  @JsonIgnore
  @Column(name = "password")
  String password;

  @ManyToAny
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<RoleEntity> roles;
}