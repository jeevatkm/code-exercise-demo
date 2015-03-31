package com.demo.exercise.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.exercise.model.Account;

/**
 * Account repository for Accounts collection in MongoDB
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public interface AccountRepository extends MongoRepository<Account, String>, AccountExtendedRepository {}
