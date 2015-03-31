package com.demo.exercise.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home/entry for REST app
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@RestController
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "Code exercise demo application!";
  }

}
