package com.eomcs.io.ex05;

public class Member {
  String name;
  int age;
  boolean gender; // true(여자), false(남자)

  @Override
  public String toString() {
    return "Member [name=" + name + ", age=" + age + ", gender=" + gender + "]";
  }
}
