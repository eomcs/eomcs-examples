package com.eomcs.cleancode.ch02.exam14;

import java.util.ArrayList;
import java.util.List;

public class BadAndGood4 {

  static class Item {
    String name;
  }

  static class Product {
    String name;
    double price;
    Product(String name, double price) {
      this.name = name;
      this.price = price;
    }
  }

  // Bad
  // - ItemHandler → 너무 일반적이어서 실제 비즈니스 의미가 없다.
  // - handle → 무엇을 하는지 전혀 예측할 수 없다.
  static class BadItemHandler {
    void handle(Item item) {
      System.out.println("아이템 처리: " + item.name);
    }
  }

  // Good
  // - ShoppingCart → 사용자가 일상에서 사용하는 도메인 언어다.
  // - addProduct → 실제 사용자 개념(장바구니에 상품 추가)과 일치한다.
  static class GoodShoppingCart {
    private List<Product> products = new ArrayList<>();

    void addProduct(Product product) {
      products.add(product);
      System.out.println("장바구니에 추가: " + product.name);
    }
  }
}
