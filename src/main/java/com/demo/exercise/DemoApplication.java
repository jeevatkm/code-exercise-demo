package com.demo.exercise;

import javax.servlet.Filter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.demo.exercise.model.Account;
import com.demo.exercise.service.AccountService;

/**
 * Application Bootstrap
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public Filter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

  @Bean
  CommandLineRunner init(final AccountService accountService) {

    return new CommandLineRunner() {

      @Override
      public void run(String... args) throws Exception {
        // Creating at least one user for demo application
        Account account = new Account();
        account.setUsername("jeeva");
        account.setPassword("demo123");
        account.setEmail("jeeva@myjeeva.com");
        account.setFirstName("Jeeva");
        account.setLastName("Madanagopal");
        account.setPhoneNumber("000-000-0000");
        account.setProfession("Technical Architect");
        account.setCity("Los Angeles");
        account.setState("CA");
        account.setCountry("USA");
        account.setZipCode(90025);

        accountService.create(account);

        account.setUsername("victor");
        account.setPassword("demo456");
        account.setEmail("victor@myjeeva.com");
        account.setFirstName("Victor");
        account.setLastName("Game");
        account.setPhoneNumber("000-000-0000");
        account.setProfession("Software Engineer");
        account.setCity("Los Angeles");
        account.setState("CA");
        account.setCountry("USA");
        account.setZipCode(92025);

        accountService.create(account);

        account.setUsername("john");
        account.setPassword("demo789");
        account.setEmail("john@myjeeva.com");
        account.setFirstName("John");
        account.setLastName("Gates");
        account.setPhoneNumber("000-000-0000");
        account.setProfession("Software Engineer");
        account.setCity("Culver City");
        account.setState("CA");
        account.setCountry("USA");
        account.setZipCode(92105);

        accountService.create(account);

        account.setUsername("chris");
        account.setPassword("demo789");
        account.setEmail("chris@myjeeva.com");
        account.setFirstName("Chris");
        account.setLastName("Bill");
        account.setPhoneNumber("000-000-0000");
        account.setProfession("Software Engineer");
        account.setCity("Westwood");
        account.setState("CA");
        account.setCountry("USA");
        account.setZipCode(92105);

        accountService.create(account);

        account.setUsername("frank");
        account.setPassword("demo010");
        account.setEmail("frank@myjeeva.com");
        account.setFirstName("Frank");
        account.setLastName("Emma");
        account.setPhoneNumber("000-000-0000");
        account.setProfession("Senior Software Engineer");
        account.setCity("Los Angeles");
        account.setState("CA");
        account.setCountry("USA");
        account.setZipCode(92005);

        accountService.create(account);
      }

    };
  }
}
