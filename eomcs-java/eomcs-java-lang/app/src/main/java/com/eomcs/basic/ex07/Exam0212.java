// java.util.HashSet 클래스 사용 - 값을 꺼내는 방법 I
package com.eomcs.basic.ex07;

import java.util.HashSet;

public class Exam0212 {
  public static void main(String[] args) {

    HashSet<String> set = new HashSet<>();
    set.add("aaa");
    set.add("bbb");
    set.add("ccc");
    set.add("ddd");
    set.add("eee");

    // 저장한 순서대로 꺼낼 수 없기 때문에 index를 이용하여 값을 꺼낼 수 없다.
    // set.get(0); //<== 이런 메서드가 없다.

    // 값을 꺼내는 방법
    // 1) HashSet에 들어있는 값을 배열로 받아 사용한다.

    // 입력한 값의 타입으로 배열을 받고 싶다면?
    // - 배열의 크기가 작다면 내부에서 배열을 만들어 리턴해준다.
    String[] arr = set.toArray(new String[0]);

    for (String value : arr) {
      System.out.print(value + ", ");
    }
    System.out.println();
  }

}



