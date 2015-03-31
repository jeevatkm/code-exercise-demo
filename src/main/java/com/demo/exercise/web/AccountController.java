package com.demo.exercise.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.exercise.model.Account;
import com.demo.exercise.service.AccountService;

/**
 * Account rest endpoints
 * <p><strong>Note:</strong> No validation are applied here</p>
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@RestController
@RequestMapping("/accounts")
@PreAuthorize("hasRole('USER')")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @RequestMapping(method = RequestMethod.GET)
  public List<Account> getAccounts(
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam(value = "count", defaultValue = "10", required = false) int count,
      @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
      @RequestParam(value = "sort", defaultValue = "lastName", required = false) String sortProperty,
      @RequestParam(value = "filter", defaultValue = "false", required = false) boolean filter,
      @RequestParam(value = "profession", required = false) String profession,
      @RequestParam(value = "city", required = false) String city,
      @RequestParam(value = "zipCode", required = false) Integer zipCode) {
    
    Pageable pageReq = new PageRequest(page, count, new Sort(direction, sortProperty));

    List<Account> accounts;
    if (filter) {
      accounts = accountService.findByCriteria(new Account(profession, city, zipCode), pageReq);
    } else {
      accounts = accountService.findAll(pageReq).getContent();
    }

    return accounts;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Account create(@RequestBody Account account) {
    return accountService.create(account);
  }

  @RequestMapping(method = RequestMethod.GET, value = "{id}")
  public Account find(@PathVariable String id) {
    return accountService.find(id);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void delete(@PathVariable String id) {
    accountService.delete(id);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Account update(@PathVariable String id, @RequestBody Account account) {
    return accountService.update(id, account);
  }

}
