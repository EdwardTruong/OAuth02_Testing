package com.oathu02.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oathu02.demo.dao.UserRepository;
import com.oathu02.demo.entity.UserEntity;
import com.oathu02.demo.exception.UserNotFoundException;
import com.oathu02.demo.util.Const.MESSENGER_NOT_FOUND;

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
