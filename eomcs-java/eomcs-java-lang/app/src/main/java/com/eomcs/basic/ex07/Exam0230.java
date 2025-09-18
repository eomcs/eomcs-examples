// java.util.HashSet 클래스 사용 - 값을 꺼내는 방법 : for(:)
package com.eomcs.basic.ex07;

import java.util.HashSet;

public class Exam0230 {
  public static void main(String[] args) {

    HashSet<String> set = new HashSet<>();
    set.add("aaa");
    set.add("bbb");
    set.add("ccc");
    set.add("ddd");
    set.add("eee");

    // Iterable 구현체라면 다음과 같이 for(:)를 사용하여 값을 조회할 수 있다.
    for (String s : set) {
      System.out.print(s + ", ");
    }
    System.out.println();
  }
}

// Iterator 패턴
// - 컬렉션에서 값을 꺼내는 기능을 객체화시키는 방법
// - 즉 별도의 클래스로 정의하여 사용한다.
// - 이점
//   컬렉션의 타입에 상관없이 일관된 방식으로 값을 꺼낼 수 있다.
//   예) ArrayList => get(), Stack => pop(), Queue => poll()
//   Iterator를 사용하면 hasNext(), next()만 사용하면 된다.


