package com.example.oauth02.util;

public class Const {

  public final static class ROLE_ACTIVE {
    public final static int ROLE_ADMIN = 1;
    public final static int ROLE_USER = 2;
  }

  public final static class ACTIVE {
    public final static int OK = 1;
    public final static int LOCK = 2;
  }

  public final static class MESSENGER_NOT_FOUND {
    public final static String ROLE_NOT_EXIST = "Error: Role cannot be found!";
    public final static String USER_NOT_FOUND_EMAIL = "The account cannot be found with email : ";
    public final static String USER_NOT_FOUND_ID = "The account cannot be found with id";
    public final static String SESSION_NOT_FOUND = "Session doesn't exit !";
  }
}
