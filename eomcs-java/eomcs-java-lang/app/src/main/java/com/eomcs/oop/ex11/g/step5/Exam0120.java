// 상수를 별도의 클래스로 분리하여 다루기 - static 멤버 import
package com.eomcs.oop.ex11.g.step5;

import static com.eomcs.oop.ex11.g.step5.Category.APPLIANCE_TV;
import static com.eomcs.oop.ex11.g.step5.Category.BOOK_NOVEL;
import static com.eomcs.oop.ex11.g.step5.Category.COMPUTER_RAM;

// 또는 다음과 같이 wildcard를 사용해도 된다.
// import static com.eomcs.oop.ex11.c.step5.Category.*;

public class Exam0120 {
  public static void main(String[] args) {
    Product p = new Product();
    p.category = APPLIANCE_TV;
    p.name = "울트라비전 뷰";
    p.price = 2000000;

    Product p2 = new Product();
    p2.category = COMPUTER_RAM;
    p2.name = "삼성모듈램 4GB";
    p2.price = 80000;

    Product p3 = new Product();
    p3.category = BOOK_NOVEL;
    p3.name = "토지";
    p3.price = 18000;
  }
}
