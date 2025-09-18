// 의존 객체 주입 자동화하기 - private 필드에 값을 넣는 방법
package com.eomcs.spring.ioc.ex08.d;

import java.lang.reflect.Field;

public class Exam02 {

  public static void main(String[] args) throws Exception {
    // private 멤버는 직접 접근 불가!
    Car c2 = new Car();
    //    c2.model = "비트비트"; // private 멤버이기 때문에 접근 불가! 컴파일 오류!
    c2.setModel("비트비트"); // 그래서 public 멤버인 셋터를 이용해서 값을 넣는 것이다.
    System.out.println(c2);


    // 그런데 Reflection API 사용하면 private 멤버도 접근할 수 있다.
    // 정말?
    //
    Field f = Car.class.getDeclaredField("model");
    f.setAccessible(true); // private 멤버이지만 난 접근할래!!!
    f.set(c2, "오호라2");
    System.out.println(c2);

  }

}


