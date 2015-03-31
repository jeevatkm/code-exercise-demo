package com.demo.exercise.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Account data structure
 * <p>
 * <strong>Note:</strong> Currently user credentials attributes and info attributes are kept
 * together in Account. Since goal is demonstrate a capability.
 * </p>
 *
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@Document(collection = "accounts")
public class Account {

  @Id
  @NotBlank(message = "Username field is required value.")
  private String username;
  // @JsonIgnore // it won't be part of JSON response
  @NotBlank(message = "Password field is required value.")
  private String password;
  @NotBlank(message = "Email address field is required value.")
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String profession;
  private String city;
  private String state;
  private String country;
  private Integer zipCode;

  public Account() {}

  public Account(String profession, String city, Integer zipCode) {
    this.profession = profession;
    this.city = city;
    this.zipCode = zipCode;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the profession
   */
  public String getProfession() {
    return profession;
  }

  /**
   * @param profession the profession to set
   */
  public void setProfession(String profession) {
    this.profession = profession;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the zipCode
   */
  public Integer getZipCode() {
    return zipCode;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
  }
}
