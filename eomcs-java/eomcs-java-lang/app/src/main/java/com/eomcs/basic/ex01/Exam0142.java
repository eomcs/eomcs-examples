// Object 클래스 - hashCode() 오버라이딩
package com.eomcs.basic.ex01;

import java.util.Objects;

public class Exam0142 {
  public static void main(String[] args) {
    My obj1 = new My();
    obj1.name = "홍길동";
    obj1.age = 20;

    My obj2 = new My();
    obj2.name = "홍길동";
    obj2.age = 20;

    System.out.println(obj1 == obj2); // false
    System.out.println(obj1.equals(obj2)); // true

    System.out.println(Integer.toHexString(obj1.hashCode()));
    System.out.println(Integer.toHexString(obj2.hashCode()));

    System.out.println(obj1);
    System.out.println(obj2);
  }

  static class My {
    String name;
    int age;

    // Map에 값을 저장하는 key로 사용할 때 hashCode()를 오버라이딩 하라!
    // 보통 값이 같은지 비교할 때 equals()와 함께 사용된다.
    // 그래서 hashCode()를 오버라이딩 할 때 equals()도 함께 오버라이딩 한다.

    @Override
    public int hashCode() {
      return Objects.hash(age, name);
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      My other = (My) obj;
      return age == other.age && Objects.equals(name, other.name);
    }
  }

}







