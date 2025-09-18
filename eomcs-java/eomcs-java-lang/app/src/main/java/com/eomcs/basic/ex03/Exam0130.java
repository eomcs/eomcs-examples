// java.util.ArrayList 사용법 - contains() 와 equals()의 관계
package com.eomcs.basic.ex03;

import java.util.ArrayList;

public class Exam0130 {
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
      //      public int hashCode() {
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
    System.out.println(list);

    // contains()
    // - 해당 인스턴스와 같은 객체가 있는지 알아낸다.
    // - 단 인스턴스 주소를 비교하는 것이 아니라
    //   equals()의 결과가 true 인지 비교한다.
    // - hashCode()의 리턴 값이 같을 필요는 없다.
    //
    Member m4 = new Member("임꺽정", 30);
    System.out.println(list.contains(m4)); // true

    System.out.println(m2 == m4);
    System.out.println(m2.equals(m4));
    System.out.println(m2.hashCode() == m4.hashCode());

  }
}
