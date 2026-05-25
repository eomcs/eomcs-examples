package com.eomcs.cleancode.ch06.exam03;

// 예제 1: 기차 충돌 (Train Wrecks)
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Address {
    private String city;
    Address(String city) { this.city = city; }
    public String getCity() { return city; }
  }

  static class Customer {
    private Address address;
    Customer(Address address) { this.address = address; }
    public Address getAddress() { return address; }
  }

  // Bad: 호출자가 Order 내부 구조를 줄줄이 파고든다.
  // - Order가 Customer를 갖는다는 사실을 안다.
  // - Customer가 Address를 갖는다는 사실을 안다.
  // - Address가 City를 갖는다는 사실을 안다.
  // - Order의 내부 구조가 바뀌면 이 코드도 함께 바꿔야 한다.
  static class BadOrder {
    private Customer customer;

    BadOrder(Customer customer) { this.customer = customer; }

    public Customer getCustomer() { return customer; }
  }

  static class BadOrderClient {
    void printCity(BadOrder order) {
      String city = order.getCustomer()
                         .getAddress()
                         .getCity();
      System.out.println(city);
    }
  }

  // Good: 호출자는 Order에게만 메시지를 보낸다.
  // - Customer, Address 구조를 전혀 알 필요가 없다.
  // - Order 내부 표현이 바뀌어도 호출 코드는 변경되지 않는다.
  static class GoodOrder {
    private Customer customer;

    GoodOrder(Customer customer) { this.customer = customer; }

    public String getShippingCity() {
      return customer.getAddress().getCity();
    }
  }

  static class GoodOrderClient {
    void printCity(GoodOrder order) {
      String city = order.getShippingCity();
      System.out.println(city);
    }
  }
}
