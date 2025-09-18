// 제네릭(Generic) 활용 - java.util.ArrayList
package com.eomcs.generic.ex01;

import java.util.ArrayList;

public class Exam0610 {
  public static void main(String[] args) {
    // Generic 사용
    // 특징 1: 다루는 타입을 제한할 수 있다.
    // => 즉 ArrayList가 어떤 종류(타입,클래스)의 객체를 저장할 것인지 지정할 수 있다.
    //    지정된 타입 외에 다른 타입은 저장할 수 없다.
    // => 방법
    //    클래스명 옆에 다루고자 하는 타입의 이름을 지정한다.
    //    클래스명<타입명>
    //
    ArrayList<Member> list = new ArrayList<Member>();
    list.add(new Member("홍길동", 20));

    // ArrayList 객체를 생성할 때 지정한 타입이 아닌 경우에는 컴파일 오류가 발생한다.
    // => 컬렉션에 잘못된 값이 들어갈 상황을 없앨 수 있다.
    //    list.add(new String("Hello"));
    //    list.add(new Integer(100));
    //    list.add(new HashSet<E>());

    // 특징 2: 제네릭을 지정하면 그와 관련된 메서드의 타입 정보가 자동으로 바뀐다.
    // => 형변환하는 번거로움이 없다.
    Member member = list.get(0);
    System.out.println(member.name);
    System.out.println(member.age);

    // 만약 제네릭이라는 문법이 없다면?
    // 1) 값을 꺼낼 때 마다 형변환을 해야 한다.
    //    예) Member member = (Member) list.get(0);
    //
    // 2) 형변환하기 싫다면 값의 종류별로 ArrayList를 만들어야 한다.
    //    예) class MemberArrayList {...}
    //
    // 제네릭 문법이 존재하므로,
    // => 한 개의 클래스로 형변환 없이 특정 타입 전용 클래스로 만들 수 있다.
  }
}








