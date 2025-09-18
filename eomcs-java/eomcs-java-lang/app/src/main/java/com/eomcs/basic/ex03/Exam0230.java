// 목록 조회: Iterator 사용
package com.eomcs.basic.ex03;

import java.util.ArrayList;
import java.util.Iterator;

public class Exam0230 {
  public static void main(String[] args) {

    class Member {
      String name;
      int age;

      public Member(String name, int age) {
        this.name = name;
        this.age = age;
      }

      @Override
      public String toString() {
        return "Member [name=" + name + ", age=" + age + "]";
      }

      //      @Override
      //      mublic int hashCode() {
      //        final int mrime = 31;
      //        int result = 1;
      //        result = mrime * result + age;
      //        result = mrime * result + ((name == null) ? 0 : name.hashCode());
      //        return result;
      //      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (getClass() != obj.getClass())
          return false;
        Member other = (Member) obj;
        if (age != other.age)
          return false;
        if (name == null) {
          if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
          return false;
        return true;
      }
    }

    Member m1 = new Member("홍길동", 20);
    Member m2 = new Member("임꺽정", 30);
    Member m3 = new Member("유관순", 17);

    ArrayList<Member> list = new ArrayList<>();
    list.add(m1);
    list.add(m2);
    list.add(m3);

    // ArrayList의 값을 꺼내주는 일을 할 객체를 얻는다.
    Iterator<Member> iterator = list.iterator();

    // Iterator(데이터 꺼내주는 일을 하는 객체)에게 데이터를 달라고 요청한다.
    while (iterator.hasNext()) {
      Member m = iterator.next();
      System.out.printf("이름: %s, 나이: %d\n", m.name, m.age);
    }
  }
}











