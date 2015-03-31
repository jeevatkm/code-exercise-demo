/**
 * 
 */
package com.demo.exercise.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.demo.exercise.service.AuthTokenService;

/**
 * Auth Token processing filter
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public class TokenAuthenticationFilter extends GenericFilterBean {
  private AuthTokenService authTokenService;
  private AuthenticationEventPublisher authenticationEventPublisher;

  public TokenAuthenticationFilter(AuthTokenService authTokenService, AuthenticationEventPublisher authenticationEventPublisher) {
    this.authTokenService = authTokenService;
    this.authenticationEventPublisher = authenticationEventPublisher;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   * javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    if (isTokenExists(httpRequest)) {
      String authToken = httpRequest.getHeader(AuthTokenService.AUTH_TOKEN_HEADER_NAME);

      if (authTokenService.validate(authToken)) {
        // Since User is authentication earlier
        Authentication authentication = authTokenService.getUser(authToken);
        
        // set the authentication into the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authenticationEventPublisher.publishAuthenticationSuccess(authentication);
      }
    }

    // continue thru the filter chain
    chain.doFilter(request, response);
  }

  private boolean isTokenExists(HttpServletRequest request) {
    return StringUtils.hasText(request.getHeader(AuthTokenService.AUTH_TOKEN_HEADER_NAME));
  }
}
