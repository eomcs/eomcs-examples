// 목록 조회: toArray(E[]) 사용
package com.eomcs.basic.ex03;

import java.util.ArrayList;

public class Exam0221 {
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

    //    Member[] arr = new Member[list.size()];
    //    Member[] arr2 = list.toArray(arr); // 파라미터로 받은 배열 그래도 리턴

    Member[] arr = new Member[0];
    Member[] arr2 = list.toArray(arr); // 새로 배열을 만들어 리턴
    System.out.println(arr == arr2);

    for (Member m : arr2) {
      System.out.printf("이름: %s, 나이: %d\n", m.name, m.age);
    }
  }
}
