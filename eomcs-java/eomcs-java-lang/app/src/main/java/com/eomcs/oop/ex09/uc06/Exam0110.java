// 인터페이스 : caller를 만드는 입장
package com.eomcs.oop.ex09.uc06;

import java.util.ArrayList;
import java.util.Iterator;

public class Exam0110 {
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("홍길동");
    list.add("임꺽정");
    list.add("유관순");
    list.add("김구");

    // Iterator 는 인터페이스다.
    // ArrayList의 iterator() 메서드는 이 인터페이스를 구현한 클래스의 객체를 만들어 리턴한다.
    Iterator<String> iterator = list.iterator();

    // 인터페이스를 기준으로 한 개발자 입장:
    // => 인터페이스 호출 규칙에 따라 객체를 사용한다.
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}
