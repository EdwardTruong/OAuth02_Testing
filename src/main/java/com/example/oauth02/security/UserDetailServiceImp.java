package com.example.oauth02.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.oauth02.dao.UserRepository;
import com.example.oauth02.entity.UserEntity;
import com.example.oauth02.exception.UserNotFoundException;
import com.example.oauth02.util.Const.MESSENGER_NOT_FOUND;

@Service
public class UserDetailServiceImp implements UserDetailsService {

  @Autowired
  UserRepository userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userDao.findUserByEmail(username)
        .orElseThrow(() -> new UserNotFoundException(MESSENGER_NOT_FOUND.USER_NOT_FOUND_EMAIL + username));
    UserDetailCustom userDetailCustom = new UserDetailCustom(userEntity);
    return userDetailCustom;
  }

}
