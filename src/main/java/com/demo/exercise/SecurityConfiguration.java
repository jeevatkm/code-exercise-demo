/**
 * 
 */
package com.demo.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.exercise.security.CustomAuthenticationEntryPoint;
import com.demo.exercise.security.RestLoginFilter;
import com.demo.exercise.security.TokenAuthenticationFilter;
import com.demo.exercise.service.AccountService;
import com.demo.exercise.service.AuthTokenService;

/**
 * Application security configuration
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AccountService accountService;
  
  @Autowired
  private AuthTokenService authTokenService;
  
  @Autowired
  private AuthenticationEventPublisher authenticationEventPublisher;

  public SecurityConfiguration() {
    super(true);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return accountService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .anonymous().and()
      .servletApi().and()
      .exceptionHandling().and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests()
      .antMatchers("/").permitAll()
      //allow anonymous POSTs to login
      .antMatchers(HttpMethod.POST, "/token").permitAll()
      
      //all other request need to be authenticated
      .anyRequest().hasRole("USER").and() 
      
      // JSON based authentication by POST of {"username":"<name>","password":"<password>"}
      .addFilterBefore(new RestLoginFilter("/token", authTokenService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

      // Token based validation & authentication based on the header X-Auth-Token
      .addFilterBefore(new TokenAuthenticationFilter(authTokenService, authenticationEventPublisher), UsernamePasswordAuthenticationFilter.class);
  }
  
  @Bean
  public AuthenticationEntryPoint unauthorizedEntryPoint() {
      return new CustomAuthenticationEntryPoint();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
