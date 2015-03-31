/**
 * 
 */
package com.demo.exercise.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.demo.exercise.model.Account;

/**
 * Implementation of Account Extended repository for custom query
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public class AccountRepositoryImpl implements AccountExtendedRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * Queries to find account information for given Account criteria
   */
  @Override
  public List<Account> findByCriteria(Account account, Pageable page) {

    // For a demo following filter is supported profession, city, zipCode

    return mongoTemplate.find(Query.query(prepareCriteria(account)).with(page), Account.class);
  }

  /**
   * Creates a criteria for given account values
   * 
   * @param account a type of {@link Account}
   * @return {@link Criteria}
   */
  private Criteria prepareCriteria(Account account) {
    Criteria criteria = new Criteria();

    if (null != account.getProfession()) {
      criteria = criteria.and("profession").regex(account.getProfession(), "i");
    }

    if (null != account.getCity()) {
      criteria = criteria.and("city").regex(account.getCity(), "i");
    }

    if (null != account.getZipCode()) {
      criteria = criteria.and("zipCode").is(account.getZipCode());
    }

    return criteria;
  }
}
