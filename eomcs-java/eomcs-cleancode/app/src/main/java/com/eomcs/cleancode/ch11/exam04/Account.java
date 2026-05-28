package com.eomcs.cleancode.ch11.exam04;

public class Account {

  private final String id;

  public Account(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }
}
