// 상수를 별도의 클래스로 분리하여 다루기
package com.eomcs.oop.ex11.g.step5;

public class Exam0110 {
  public static void main(String[] args) {
    Product p = new Product();
    p.category = Category.APPLIANCE_TV;
    p.name = "울트라비전 뷰";
    p.price = 2000000;

    Product p2 = new Product();
    p2.category = Category.COMPUTER_RAM;
    p2.name = "삼성모듈램 4GB";
    p2.price = 80000;

    Product p3 = new Product();
    p3.category = Category.BOOK_NOVEL;
    p3.name = "토지";
    p3.price = 18000;
  }
}
