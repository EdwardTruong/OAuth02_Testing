package com.example.oauth02.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.oauth02.util.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity {

@Id
@Column(name = "id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonIgnore
int id;

@Enumerated(EnumType.STRING)
@Column(name = "name")
ERole name;
}
