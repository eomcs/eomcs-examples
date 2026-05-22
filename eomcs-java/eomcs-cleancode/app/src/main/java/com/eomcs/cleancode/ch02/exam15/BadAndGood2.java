package com.eomcs.cleancode.ch02.exam15;

public class BadAndGood2 {

  static class Address {
    String firstName;
    String lastName;
    String city;
    String state;
    String zipCode;
  }

  // Bad
  // - 인자의 의미를 알 수 없다. 순서를 기억해야 하며, 값을 잘못된 위치에 넣어도
  //   컴파일 오류가 나지 않아 실수 가능성이 높다.
  static class BadUserService {
    void demo() {
      save("Bernard", "Um", "Seoul", "Gangnam", "12345");
    }
    void save(String firstName, String lastName, String city, String state, String zipCode) {
      System.out.println(firstName + " " + lastName + ", " + city + " " + zipCode);
    }
  }

  // Good
  // - 각 값의 의미가 명확하다.
  // - 구조(Address)가 맥락을 제공하고 실수 가능성이 크게 줄어든다.
  static class GoodUserService {
    void demo() {
      Address address = new Address();
      address.firstName = "Bernard";
      address.lastName = "Um";
      address.city = "Seoul";
      address.state = "Gangnam";
      address.zipCode = "12345";
      save(address);
    }
    void save(Address address) {
      System.out.println(address.firstName + " " + address.lastName + ", " + address.city + " " + address.zipCode);
    }
  }
}
