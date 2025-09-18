// java.util.ArrayList 사용법
package com.eomcs.basic.ex03;

import java.util.ArrayList;
import java.util.Date;

public class Exam0110 {

  public static void main(String[] args) {

    // 자바 컬렉션 API (collection API)
    // => 자바에서 데이터 목록을 다루는 클래스를 말한다.
    // => java.util.Collection 인터페이스를 구현한 객체이다.
    //
    // ArrayList
    // - 배열을 이용하여 객체 목록을 다룬다.
    // - 단점:
    //   - 배열의 크기가 고정되기 때문에 배열을 초과하여 값을 넣으려면
    //     더 큰 새 배열을 만들고, 기존 값을 복사해야 한다.
    //   - 배열의 크기가 늘 때마다 가비지(garbage)가 생기는 문제가 있다.
    //   - 새배열에 기존 배열의 값을 복사하기 때문에 속도가 느린 문제도 있다.
    //
    //
    ArrayList list = new ArrayList();
    list.add("Hello");
    list.add(Integer.valueOf(100));
    list.add(100); // auto-boxing => list.add(Integer.valueOf(100)) 
    list.add(new Date());

    // ArrayList는 제네릭이 적용되어 있기 때문에
    // 저장할 객체의 타입을 명확히 지정하라.
    // 모든 종류의 인스턴스를 저장하고 싶다면,
    // 다음과 같이 타입 파라미터에 Object 타입을 전달하라.
    ArrayList<Object> list2 = new ArrayList<>();
    list2.add("Hello");
    list2.add(Integer.valueOf(100));
    list2.add(100);
    list2.add(new Date());
  }
}




