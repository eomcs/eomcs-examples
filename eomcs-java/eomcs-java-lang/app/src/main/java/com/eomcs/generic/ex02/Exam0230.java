// 제네릭(Generic) 문법 정리 - 제네릭 객체의 메서드 사용 
package com.eomcs.generic.ex02;

import java.util.ArrayList;
import java.util.HashSet;

public class Exam0230 {
  public static void main(String[] args) {
    
    ArrayList<Object> list1;
    list1 = new ArrayList<Object>();
    
    // ArrayList가 Object 타입의 데이터를 다루기로 지정되었으면, 
    // ArrayList의 메서드 파라미터나 리턴 타입은 Object로 설정된다.
    
    // => add(Object)
    list1.add(new Object());
    list1.add(new String());
    list1.add(new Integer(100));
    list1.add(new Member("홍길동", 20));
    
    // => Object get(int)
    Object obj = list1.get(0);
    String str = (String) list1.get(1);
    Integer i = (Integer) list1.get(2);
    Member m = (Member) list1.get(3);
    
    System.out.println("종료!");
  }
}








