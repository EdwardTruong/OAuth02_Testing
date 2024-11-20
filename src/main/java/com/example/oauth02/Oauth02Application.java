package com.example.oauth02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Login with client without jwt for authentication . 
 * In form login have link login by github or google or facebook. 
 * In another app will login by jwt and access token.
 */
@SpringBootApplication
public class Oauth02Application {

	public static void main(String[] args) {
		SpringApplication.run(Oauth02Application.class, args);
	}

}
