package com.example.oauth02.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

  @Autowired
  private UserDetailServiceImp userDetailServiceImp;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
    System.out.println("Check in from Security Configurer : configureGlobal");
    builder.userDetailsService(userDetailServiceImp).passwordEncoder(passwordEncoder());

  }

  /*
   * if using (CookieCsrfTokenRepository.withHttpOnlyFalse())
   * Add csrf by dependemncy js-cookie and add
   * <script src="/webjars/js-cookie/js.cookie.js" >
   * 
   * https://spring.io/guides/tutorials/spring-boot-oauth2
   */
  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  // http.csrf(c -> c
  // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
  // .authorizeHttpRequests(requests -> requests
  // .requestMatchers(HttpMethod.GET, "/home").permitAll()
  // .requestMatchers(HttpMethod.GET, "user/**").hasAnyRole("USER", "ADMIN")
  // .anyRequest().authenticated())
  // .formLogin(login ->
  // login.loginPage("/login").loginProcessingUrl("/processLogin")
  // .defaultSuccessUrl("/home", true).permitAll())
  // .logout(logout -> {
  // logout.invalidateHttpSession(true);
  // logout.logoutRequestMatcher(new
  // AntPathRequestMatcher("/auth/logout")).permitAll();
  // logout.logoutSuccessUrl("/");
  // logout.deleteCookies("JSESSIONID");
  // });
  // return http.build();
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(c -> c
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.GET, "/home").permitAll()
            .requestMatchers(HttpMethod.GET, "user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .oauth2Login(Customizer.withDefaults())
        .logout(logout -> {
          logout.invalidateHttpSession(true);
          logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).permitAll();
          logout.logoutSuccessUrl("/");
          logout.deleteCookies("JSESSIONID");
        });
    return http.build();
  }
}
