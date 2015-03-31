/**
 * 
 */
package com.demo.exercise.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * REST Auth Token service
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@Service
public class AuthTokenService {
  private static final Logger log = LoggerFactory.getLogger(AuthTokenService.class);

  public static final String AUTH_TOKEN_HEADER_NAME = "X-AUTH-TOKEN";

  // For scale out it should be stored in distributed auth token store via Hazelcast, Redis,
  // Infinispan, etc.
  // Demo purpose, I'm consider this way
  // Evict policy not consider for demo
  private ConcurrentHashMap<String, Authentication> tokenStore =
      new ConcurrentHashMap<String, Authentication>();

  /**
   * Generate a new token for REST Auth token store. Currently it uses simple UUID generator.
   * 
   * <p>
   * <strong>Note:</strong> In a real world it should be more secure and unique token generation
   * logic with combination
   * <ol>
   * <li>username</li>
   * <li>user ip</li>
   * <li>auth success time</li>
   * <li>auth expiry time</li>
   * <li>random string</li>
   * <li>salt value for unique encript and decrypt, etc</li>
   * </ol>
   * Finally translate into friendly base64 string.
   * </p>
   * 
   * @return {@link String}
   */
  public String newToken() {
    return UUID.randomUUID().toString();
  }

  /**
   * Stores Authentication object into cache store
   * 
   * @param authentication a type of {@link Authentication}
   * @return {@link String}
   */
  public String store(Authentication authentication) {
    String token = newToken();
    log.info("Storing token:: " + token);

    tokenStore.putIfAbsent(token, authentication);
    return token;
  }

  /**
   * Validates given token against cache store
   * 
   * @param token a type of {@link String}
   * @return boolean
   */
  public boolean validate(String token) {
    return tokenStore.containsKey(token);
  }

  /**
   * Retrieve Authentication object by token
   * 
   * @param token a type of {@link String}
   * @return {@link Authentication}
   */
  public Authentication getUser(String token) {
    return tokenStore.get(token);
  }
}
