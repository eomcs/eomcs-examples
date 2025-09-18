// 제네릭(Generic) 문법 정리 - 레퍼런스와 인스턴스 생성 
package com.eomcs.generic.ex02;

import java.util.ArrayList;

public class Exam0211 {
  public static void main(String[] args) {
    // 레퍼런스를 선언할 때 제네릭 타입을 지정하지 않으면 
    // 객체 생성시 어떤 제네릭 타입을 지정하더라도 상관없다.
    ArrayList list1; 
    //    list1 = new ArrayList(); // OK
    //    list1 = new ArrayList<>(); // OK
    //    list1 = new ArrayList<Object>(); // OK
    //    list1 = new ArrayList<String>(); // OK
    list1 = new ArrayList<Member>(); // OK

    //=> 레퍼런스를 선언할 때 제네릭 타입을 지정하지 않으면 
    //   ArrayList 객체를 생성할 때 지정한 제네릭 타입은 무시된다.
    list1.add(new String());
    list1.add(new Integer(100));
    list1.add(new java.util.Date());
    list1.add(new Member("홍길동", 20));


    // 레퍼런스를 선언할 때 제네릭 타입을 <?> 로 선언하면
    // 객체 생성시 어떤 제네릭 타입을 지정하더라도 상관없다.
    // 단, 메서드를 사용할 때 주의해야 한다.
    ArrayList<?> list2; 
    //    list2 = new ArrayList(); // OK
    //    list2 = new ArrayList<>(); // OK
    //    list2 = new ArrayList<Object>(); // OK
    //    list2 = new ArrayList<String>(); // OK
    list2 = new ArrayList<Member>(); // OK

    //=> 레퍼런스 선언할 때 제네릭 타입을 ? 로 설정했기 때문에
    //   add() 메서드의 파라미터 타입은 ? 가 된다.
    //   즉 파라미터 타입이 뭔지 정확하게 설정되지 않았기 때문에
    //   컴파일러는 문법의 유효여부를 검사할 수 없다.
    // 
    list2.add(new Object());
    list2.add(new String()); // 컴파일 오류!
    list2.add(new Integer(100)); // 컴파일 오류!
    list2.add(new java.util.Date()); // 컴파일 오류!
    list2.add(new Member("홍길동", 20)); // 컴파일 오류!
  }
}








