package com.eomcs.cleancode.ch02.exam06;

import java.util.List;
import java.util.Set;

// 타입 인코딩
public class BadAndGood4 {
  class User {}

  // Bad
  void bad() {
    List<User> userList;
    Set<User> userSet;
  }

  // Good
  void good() {
    List<User> users;
    Set<User> uniqueUsers;
  }
}
