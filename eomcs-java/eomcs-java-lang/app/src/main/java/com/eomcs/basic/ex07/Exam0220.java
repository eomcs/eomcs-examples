// java.util.HashSet 클래스 사용 - 값을 꺼내는 방법 II
package com.eomcs.basic.ex07;

import java.util.HashSet;
import java.util.Iterator;

public class Exam0220 {
  public static void main(String[] args) {

    HashSet<String> set = new HashSet<>();
    set.add("aaa");
    set.add("bbb");
    set.add("ccc");
    set.add("ddd");
    set.add("eee");

    // 2) 창고에서 값을 꺼내주는 전문가에게 도움을 받는다.
    // - HashSet에서 값을 꺼내주는 일을 하는 도우미 객체를 얻는다.
    Iterator<String> 컬렉션에서값을꺼내주는객체 = set.iterator();

    // - 값을 꺼내주는 객체를 통해 값을 꺼낸다.
    while (컬렉션에서값을꺼내주는객체.hasNext()) {
      // - 꺼낼 데이터가 있다면 값을 꺼내달라고 명령한다.
      System.out.print(컬렉션에서값을꺼내주는객체.next() + ", ");
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


