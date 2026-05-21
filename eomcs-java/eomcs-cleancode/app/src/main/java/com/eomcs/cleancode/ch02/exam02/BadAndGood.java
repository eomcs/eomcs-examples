package com.eomcs.cleancode.ch02.exam02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BadAndGood {

  void example1() {
    // Bad: 실제로 List가 아닌데 List라고 이름 붙이는 경우
    Map<String, Account> accountList = new HashMap<>();

    // Good: 실제 타입을 반영한 이름
    Map<String, Account> accountMap = new HashMap<>();
    // 또는 더 간결하게
    Map<String, Account> accounts = new HashMap<>();
  }

  void example2() {
    // Bad: 숫자 0, 1과 혼동되는 변수명
    int l = 100; // 소문자 l
    int O1 = 200; // 대문자 O
    int O = 0; // 대문자 O

    int a = l;
    if (O == l) a = O1;
    else l = 01;

    // Good: 명확한 변수명 사용
    int itemCount = 1;
    int zeroValue = 0;
  }

  void example3() {
    // Bad: 미묘한 차이만 있는 유사한 이름
    XYZControllerForEfficientHandlingOfStrings handler;
    XYZControllerForEfficientStorageOfStrings storage;

    // Good: 차이가 명확하게 드러나는 이름
    StringHandler stringHandler;
    StringStorage stringStorage;
  }

  void example4() {
    // Bad: 약어/축약어로 인한 혼동
    String hp; // home phone number

    // Good: 약어 제거
    String homePhoneNumber;
  }

  void example5() {
    // Bad: 잘못된 개념 사용
    Set<User> userList = new HashSet<>();

    // Good: 자료 구조에 맞는 이름 사용
    Set<User> users = new HashSet<>();
  }

  class Account {}

  class XYZControllerForEfficientHandlingOfStrings {}

  class XYZControllerForEfficientStorageOfStrings {}

  class User {}

  class StringHandler {}

  class StringStorage {}
}
