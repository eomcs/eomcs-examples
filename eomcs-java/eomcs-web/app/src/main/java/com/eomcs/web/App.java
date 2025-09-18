package com.eomcs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App {

  public static void main(String[] args) {
    System.out.println("서버 시작!");
    SpringApplication.run(App.class, args);
  }

  @RequestMapping("/hello")
  String hello() {
    return "Hello World!";
  }

}
