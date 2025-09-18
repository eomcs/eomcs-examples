// hash code 응용 II - MyKey의 hashCode()와 equals() 오버라이딩 하기
package com.eomcs.basic.ex01;

import java.util.HashMap;
import java.util.Objects;


public class Exam0153 {

  static class MyKey2 {
    String contents;

    public MyKey2(String contents) {
      this.contents = contents;
    }

    @Override
    public String toString() {
      return "MyKey2 [contents=" + contents + "]";
    }

    @Override
    public int hashCode() {
      return Objects.hash(contents);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      MyKey2 other = (MyKey2) obj;
      return Objects.equals(contents, other.contents);
    }


  }
  public static void main(String[] args) {
    HashMap<MyKey2,Student> map = new HashMap<>();

    MyKey2 k1 = new MyKey2("ok");
    MyKey2 k2 = new MyKey2("no");
    MyKey2 k3 = new MyKey2("haha");
    MyKey2 k4 = new MyKey2("ohora");
    MyKey2 k5 = new MyKey2("hul");

    map.put(k1, new Student("홍길동", 20, false));
    map.put(k2, new Student("임꺽정", 30, true));
    map.put(k3, new Student("유관순", 17, true));
    map.put(k4, new Student("안중근", 24, true));
    map.put(k5, new Student("윤봉길", 22, false));

    System.out.println(map.get(k3));

    // 다른 key 객체를 사용하여 값을 꺼내보자.
    MyKey2 k6 = new MyKey2("haha");

    System.out.println(map.get(k6)); // OK! 값을 정상적으로 꺼낼 수 있다.
    // k3와 k6는
    // hashCode()의 리턴 값이 같다
    // equals() 비교 결과도 true 이기 때문에
    // HashMap 클래스에서는 서로 같은 key라고 간주한다.

    System.out.println(k3 == k6); // 인스턴스는 다르다.
    System.out.printf("k3(%s), k6(%s)\n", k3, k6);
    System.out.println(k3.hashCode()); // hash code는 같다.
    System.out.println(k6.hashCode()); // hash code는 같다.
    System.out.println(k3.equals(k6)); // equals()의 비교 결과도 같다.
  }
}







