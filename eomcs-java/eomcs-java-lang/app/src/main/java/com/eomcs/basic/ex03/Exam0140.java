// java.util.ArrayList 사용법 - indexOf()
package com.eomcs.basic.ex03;

import java.util.ArrayList;

public class Exam0140 {
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
    System.out.println(list);

    // indexOf(값)
    // - 목록에 같은 값을 가진 객체의 인덱스를 알아낸다.
    // - 값을 비교할 때는 contains()와 마찬가지로
    //   equals()의 리턴 값이 true인 경우 같은 값으로 간주한다.
    //
    Member m4 = new Member("유관순", 17);
    System.out.println(list.indexOf(m4)); // 2

    System.out.println(m3 == m4);
    System.out.println(m3.equals(m4));
    System.out.println(m3.hashCode() == m4.hashCode());

  }
}
