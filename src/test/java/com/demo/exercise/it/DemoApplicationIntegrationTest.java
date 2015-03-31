package com.demo.exercise.it;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.exercise.DemoApplication;
import com.demo.exercise.TestMongoConfig;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestMongoConfig.class, DemoApplication.class})
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class DemoApplicationIntegrationTest {
  private static final Logger log = LoggerFactory.getLogger(DemoApplicationIntegrationTest.class);

  @Value("${local.server.port}")
  private int port;
  
  @Value("${local.management.port}")
  private int mngPort;

  private String apiToken;
  private Map<String, String> headerToken = new HashMap<String, String>();

  @Before
  public void setUp() throws Exception {
    // Assign Runtime port
    RestAssured.port = this.port;

    // Obtaining Auth Token for REST API
    JsonPath jsonPath =
        given().body("{\"username\": \"jeeva\", \"password\": \"demo123\"}")
            .contentType("application/json").when().post("/token").jsonPath();

    apiToken = jsonPath.getString("token");
    log.info("User logged with token :: " + apiToken);

    // Preparing Header Map
    headerToken.put("X-Auth-Token", apiToken);
  }

  @Test
  public void getHome() throws Exception {
    String res = when().get("/")
        .then().statusCode(HttpStatus.SC_OK)
        .extract().asString();
    
    assertEquals(res, "Code exercise demo application!");
  }

  @Test
  public void getAccounts() throws Exception {
    given().with()
      .headers(headerToken)
      .get("/accounts")
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body("username", hasItems("jeeva", "victor", "chris", "frank"));
  }
  
  @Test
  public void getAccountByUsername() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_OK)
      .body("username", equalTo("chris"))
      .body("email", equalTo("chris@myjeeva.com"))
      .body("lastName", equalTo("Bill"))
      .body("city", equalTo("Westwood"))
    .given().with()
      .headers(headerToken)
    .get("/accounts/{id}", "chris");
  }
  
  @Test
  public void getAccountsByFilter() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_OK)
      .body("city", hasItems("Los Angeles"))
      .body("username", hasItems("victor", "jeeva"))
    .given().with()
      .headers(headerToken)
      .queryParam("city", "los angeles")
      .get("/accounts");
  }
  
  @Test
  public void getDirectoryList() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_OK)
      .body("name", hasItems("src", "build.gradle"))
    .given().with()
      .headers(headerToken)
    .get("/fs");
  }
  
  @Test
  public void getSubDirectoryList() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_OK)      
      .body("name", hasItems("classes", "libs", "resources"))
    .given().with()
      .headers(headerToken)
      .queryParam("rp", "build")
    .get("/fs");
  }
  
  @Test
  public void getSubDirectoryListNotFound() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
    .given().with()
      .headers(headerToken)
      .queryParam("rp", "buildnot")
    .get("/fs");
  }
  
  @Test
  public void getAppHealth() throws Exception {
    expect()
      .statusCode(HttpStatus.SC_OK)
      .body("status", equalTo("UP"))
      .body("diskSpace.status", equalTo("UP"))
      .body("mongo.status", equalTo("UP"))
    .given().with()
      .headers(headerToken)
    .get("http://localhost:" + String.valueOf(mngPort) + "/health");
  }
}
