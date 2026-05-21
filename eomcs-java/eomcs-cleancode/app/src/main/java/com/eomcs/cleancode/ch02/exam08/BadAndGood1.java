package com.eomcs.cleancode.ch02.exam08;

public class BadAndGood1 {

  // Bad
  // - Manager, Processor, Data, Info 는 너무 일반적이어서 책임이 전혀 드러나지 않는다.
  // - 이름만 보고는 무엇을 관리하고 처리하는 클래스인지 알 수 없다.
  static class Manager {
    void execute(String name, String email) {}
    void remove(int id) {}
  }

  static class Processor {
    double run(double price, int qty) { return 0; }
  }

  static class Data {
    String name;
    String email;
  }

  static class Info {
    double price;
    int quantity;
  }

  // Good
  // - Customer, Order, Invoice, Address → 도메인 개념을 직접 표현한다.
  // - 이름만 읽어도 역할과 책임을 즉시 이해할 수 있다.
  static class Customer {
    String name;
    String email;
  }

  static class Order {
    double price;
    int quantity;
  }

  static class Invoice {
    Customer customer;
    Order order;
    double totalAmount;
  }

  static class Address {
    String street;
    String city;
    String zipCode;
  }
}
