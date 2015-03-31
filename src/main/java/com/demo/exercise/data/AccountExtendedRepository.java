package com.demo.exercise.data;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.demo.exercise.model.Account;


/**
 * Account Extended repository for custom query
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public interface AccountExtendedRepository {
  List<Account> findByCriteria(Account account, Pageable page);
}
