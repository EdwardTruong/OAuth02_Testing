package com.oathu02.demo.exception;

public class UserNotFoundException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 6600020462111078406L;

  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
  }
}
