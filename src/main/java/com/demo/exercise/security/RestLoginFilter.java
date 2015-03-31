/**
 * 
 */
package com.demo.exercise.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.demo.exercise.model.Account;
import com.demo.exercise.service.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * REST login filter
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public class RestLoginFilter extends AbstractAuthenticationProcessingFilter {

  private AuthTokenService tokenService;

  public RestLoginFilter(String urlMapping, AuthTokenService tokenService,
      AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(urlMapping));
    this.tokenService = tokenService;
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    final Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);

    final UsernamePasswordAuthenticationToken loginToken =
        new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
    return getAuthenticationManager().authenticate(loginToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {

    // Add the authentication to the Security context
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Storing Authentication & obtaining token
    String token = tokenService.store(authentication);

    // Candidate for bean definition
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode result = mapper.createObjectNode();
    result.put("username", authentication.getName());
    result.put("token", token);
    result.put("timestamp", new Date().getTime());

    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().print(mapper.writeValueAsString(result));
  }
}
