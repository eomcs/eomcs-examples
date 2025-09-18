// HashSet과 사용자 정의 데이터 타입 - hashCode()만 오버라이딩
package com.eomcs.basic.ex07;

import java.util.HashSet;
import java.util.Objects;

public class Exam0330 {

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
  }

  public static void main(String[] args) {

    Member v1 = new Member("홍길동", 20);
    Member v2 = new Member("임꺽정", 30);
    Member v3 = new Member("유관순", 16);
    Member v4 = new Member("안중근", 20);
    Member v5 = new Member("유관순", 16);

    System.out.println(v1.hashCode());
    System.out.println(v2.hashCode());
    System.out.println(v3.hashCode());
    System.out.println(v4.hashCode());
    System.out.println(v5.hashCode());

    System.out.println(v3.hashCode() == v5.hashCode()); // true
    System.out.println(v3.equals(v5)); 

    HashSet<Member> set = new HashSet<>();
    set.add(v1);
    set.add(v2);
    set.add(v3);
    set.add(v4);

    // equals()와 hashCode()를 함께 비교하기 때문에
    // hashCode() 만 오버라이딩 해봐야 소용없다.
    set.add(v5);

    System.out.println(set);
  }
}


