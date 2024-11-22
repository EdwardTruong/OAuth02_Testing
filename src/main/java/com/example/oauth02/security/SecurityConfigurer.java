package com.example.oauth02.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.AuthorizationCodeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
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

  public OAuth2User getOAuth2User(OAuth2User oAuth2User) {

    return oAuth2User;
  }

  /*
   * oauth2
   * Thêm vào sẽ chạy được các link login bằng các provider(github, google,
   * facebook) kể cả tutorial của spring boot :
   * https://spring.io/guides/tutorials/spring-boot-oauth2
   * 
   */
  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  private java.util.function.Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
    return customizer -> customizer
        .additionalParameters(params -> params.put("prompt", "consent"));
  }

  private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
      ClientRegistrationRepository clientRegistrationRepository) {

    DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(
        clientRegistrationRepository, "/oauth2/authorization");
    authorizationRequestResolver.setAuthorizationRequestCustomizer(
        authorizationRequestCustomizer());

    return authorizationRequestResolver;
  }

  /* oauth2 */

  /*
   * if using (CookieCsrfTokenRepository.withHttpOnlyFalse())
   * Add csrf by dependemncy js-cookie and add
   * <script src="/webjars/js-cookie/js.cookie.js" >
   * 
   * https://spring.io/guides/tutorials/spring-boot-oauth2
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/user", "/error", "/webjars/**").permitAll() // webjars using for UI :
            // https://spring.io/guides/tutorials/spring-boot-oauth2#_social_login_simple
            .requestMatchers(HttpMethod.POST, "/logout").permitAll() // tutorial of spring boot
            .requestMatchers(HttpMethod.GET, "/home").permitAll()
            .requestMatchers(HttpMethod.GET, "user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated())
        .formLogin(login -> login.loginPage("/login").loginProcessingUrl("/processLogin")
            .defaultSuccessUrl("/home", true).permitAll())

        .oauth2Login(oauth2 -> oauth2
            .authorizationEndpoint(authorization -> authorization
                .authorizationRequestResolver(
                    authorizationRequestResolver(this.clientRegistrationRepository))))

        .logout(logout -> {
          logout.invalidateHttpSession(true);
          logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).permitAll();
          logout.logoutSuccessUrl("/"); // tutorial of spring boot
          logout.deleteCookies("JSESSIONID");
        }).exceptionHandling(e -> e
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

        .csrf(c -> c // tutorial of spring boot - it's allow cookie have csrf token inside
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

    // Thuộc tính HttpOnly trong cookie quy
    // định xem liệu cookie có thể được truy
    // cập thông qua mã JavaScript hay
    // không.
    ;
    return http.build();
  }

  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  // http.csrf(c -> c
  // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
  // .authorizeHttpRequests(requests -> requests
  // .requestMatchers(HttpMethod.GET, "/home").permitAll()
  // .requestMatchers(HttpMethod.GET, "user/**").hasAnyRole("USER", "ADMIN")
  // .anyRequest().authenticated())
  // .formLogin(Customizer.withDefaults())
  // .oauth2Login(Customizer.withDefaults())
  // .logout(logout -> {
  // logout.invalidateHttpSession(true);
  // logout.logoutRequestMatcher(new
  // AntPathRequestMatcher("/auth/logout")).permitAll();
  // logout.logoutSuccessUrl("/");
  // logout.deleteCookies("JSESSIONID");
  // });
  // return http.build();
  // }

  // @Bean
  // public OAuth2AuthorizedClientManager authorizedClientManager(
  // ClientRegistrationRepository clientRegistrationRepository,
  // OAuth2AuthorizedClientRepository authorizedClientRepository) {

  // OAuth2AuthorizedClientProvider authorizedClientProvider =
  // OAuth2AuthorizedClientProviderBuilder.builder()
  // .authorizationCode()
  // .refreshToken()
  // .clientCredentials()
  // .password()
  // .build();

  // DefaultOAuth2AuthorizedClientManager authorizedClientManager = new
  // DefaultOAuth2AuthorizedClientManager(
  // clientRegistrationRepository, authorizedClientRepository);
  // authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

  // return authorizedClientManager;
  // }
}

/*
 * Many applications need to hold data about their users locally, even if
 * authentication is delegated to an external provider. We don’t show the code
 * here, but it is easy to do in two steps.
 * 
 * Choose a backend for your database, and set up some repositories (using
 * Spring Data, say) for a custom User object that suits your needs and can be
 * populated, fully or partially, from external authentication.
 * 
 * Implement and expose OAuth2UserService to call the Authorization Server as
 * well as your database. Your implementation can delegate to the default
 * implementation, which will do the heavy lifting of calling the Authorization
 * Server. Your implementation should return something that extends your custom
 * User object and implements OAuth2User.
 * 
 * Hint: add a field in the User object to link to a unique identifier in the
 * external provider (not the user’s name, but something that’s unique to the
 * account in the external provider).
 */