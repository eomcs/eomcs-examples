// HashSet과 사용자 정의 데이터 타입 - hashCode()와 equals() 모두 오버라이딩
package com.eomcs.basic.ex07;

import java.util.HashSet;
import java.util.Objects;

public class Exam0340 {

  // 사용자 정의 데이터 타입
  static class Member {
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
      Member other = (Member) obj;
      return age == other.age && Objects.equals(name, other.name);
    }
  }

  public static void main(String[] args) {
    Member v1 = new Member("홍길동", 20);
    Member v2 = new Member("임꺽정", 30);
    Member v3 = new Member("유관순", 16);
    Member v4 = new Member("안중근", 20);
    Member v5 = new Member("유관순", 16);

    System.out.printf("v3 == v5: %b\n", v3 == v5);
    System.out.printf("equals(): %b\n", v3.equals(v5));
    System.out.printf("hashCode(): %d, %d\n", v3.hashCode(), v5.hashCode());

    HashSet<Member> set = new HashSet<>();
    set.add(v1);
    set.add(v2);
    set.add(v3);
    set.add(v4);
    set.add(v5); // 중복 저장되지 않는다.

    System.out.println(set);
  }
}


