// Integer 클래스: new Integer() vs Integer.valueOf() 
package com.eomcs.oop.ex04;

public class Exam0221 {
  public static void main(String[] args) throws Exception {
    Integer obj1 = new Integer(100); 
    Integer obj2 = new Integer(100);
    Integer obj3 = new Integer(100);

    Integer i1 = Integer.valueOf(127); 
    Integer i2 = Integer.valueOf(127);
    Integer i3 = Integer.valueOf(127);

    // 1) 생성자를 통해 객체를 만들면 무조건 새 객체를 만든다.
    System.out.println(obj1 == obj2);
    System.out.println(obj1 == obj3);
    System.out.println(obj2 == obj3);
    System.out.println("---------------------------");

    // 2) valueOf()를 통해 객체를 만들면 자주 쓰는 값(-128 ~ +127)에 대해서는 한 번만 만들어 공유한다.
    System.out.println(i1 == i2);
    System.out.println(i1 == i3);
    System.out.println(i2 == i3);

  }
}





