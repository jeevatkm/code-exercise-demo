package com.demo.exercise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.exercise.data.AccountRepository;
import com.demo.exercise.model.Account;

/**
 * Account Service to handle CURD
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@Service
// Interface is not considered due to narrow nature of logic,
// however it is recommend to find out appropriate pattern based on requirement
public class AccountService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Create an account for gven user information
   * 
   * @param account a type of {@link Account}
   * @return {@link Account}
   */
  public Account create(Account account) {
    // Just doing password encode
    account.setPassword(passwordEncoder.encode(account.getPassword()));

    return accountRepository.save(account);
  }

  /**
   * Retrieve Account info for given username
   * 
   * @param username
   * @return {@link Account}
   */
  public Account find(String username) {
    return accountRepository.findOne(username);
  }

  /**
   * Retrieves all accounts in pagable fashion
   * 
   * @param page a type of {@link Pagable}
   * @return {@code Page<Account>}
   */
  public Page<Account> findAll(Pageable page) {
    return accountRepository.findAll(page);
  }

  /**
   * Retrieves all accounts in pagable given criteria
   * 
   * @param account a type of {@link Account}
   * @param page a type of {@link Pagable}
   * @return {@code List<Account>}
   */
  public List<Account> findByCriteria(Account account, Pageable page) {
    return accountRepository.findByCriteria(account, page);
  }

  /**
   * Removes a account for given username
   * 
   * @param username a type of {@link String}
   */
  public void delete(String username) {
    accountRepository.delete(username);
  }

  /**
   * Partial/Full update of account info for provide JSON payload
   * 
   * @param username a type {@link String}
   * @param account a type of {@link Account}
   * @return {@link Account}
   */
  public Account update(String username, Account account) {
    Account update = accountRepository.findOne(username);

    // username is not allowed to update since it's a primary id of user

    // Object/Model mapper is not used here (like modelmapper.org, dozer.sourceforge.net, etc)
    // It is highly recommend to use in project, to prevent trivial code and implement best
    // practices
    if (null != account.getPassword()) {
      // Just doing some hashing for password
      update.setPassword(passwordEncoder.encode(account.getPassword()));
    }

    if (null != account.getEmail()) {
      update.setEmail(account.getEmail());
    }

    if (null != account.getFirstName()) {
      update.setFirstName(account.getFirstName());
    }

    if (null != account.getLastName()) {
      update.setLastName(account.getLastName());
    }

    if (null != account.getPhoneNumber()) {
      update.setPhoneNumber(account.getPhoneNumber());
    }

    if (null != account.getProfession()) {
      update.setProfession(account.getProfession());
    }

    if (null != account.getCity()) {
      update.setCity(account.getCity());
    }

    if (null != account.getState()) {
      update.setState(account.getState());
    }

    if (null != account.getCountry()) {
      update.setCountry(account.getCountry());
    }

    if (null != account.getZipCode()) {
      update.setZipCode(account.getZipCode());
    }

    return accountRepository.save(update);
  }

  /**
   * Implements Spring security interface for user info
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = find(username);
    if (null != account) {
      // for demo every user is admin and basic user
      return new User(account.getUsername(), account.getPassword(), true, true, true, true,
          AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_ADMIN"));
    } else {
      throw new UsernameNotFoundException("could not find the user '" + username + "'");
    }
  }
}
