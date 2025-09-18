// 메서드 레퍼런스 - 활용예
package com.eomcs.oop.ex12;

import java.util.function.Predicate;

public class Exam0630 {

  public static void main(String[] args) {

    // Predicate<String> 인터페이스 구현체 준비하기
    // => 자바에서 제공하는 인터페이스
    // => 형식
    //    interface Predicate<T> {
    //      boolean test(T value);
    //      ...
    //    }

    // 1) 로컬 클래스로 인터페이스 구현체 만들기
    class MyPredicate<T> implements Predicate<T> {
      @Override
      public boolean test(T value) {
        return ((String)value).isEmpty();
      }
    }
    Predicate<String> p1 = new MyPredicate<>();
    System.out.println(p1.test("")); // true
    System.out.println(p1.test("Hello!")); // false

  }

}


