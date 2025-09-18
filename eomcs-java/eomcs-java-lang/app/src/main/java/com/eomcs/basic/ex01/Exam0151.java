// hash code 응용 - 문제 해결!
package com.eomcs.basic.ex01;

import java.util.HashSet;
import java.util.Objects;

public class Exam0151 {

  static class Student {
    String name;
    int age;
    boolean working;

    public Student(String name, int age, boolean working) {
      this.name = name;
      this.age = age;
      this.working = working;
    }

    @Override
    public int hashCode() {
      return Objects.hash(age, name, working);
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
      Student other = (Student) obj;
      return age == other.age && Objects.equals(name, other.name) && working == other.working;
    }


  }

  public static void main(String[] args) {
    Student s1 = new Student("홍길동", 20, false);
    Student s2 = new Student("홍길동", 20, false);
    Student s3 = new Student("임꺽정", 21, true);
    Student s4 = new Student("유관순", 22, true);

    System.out.println(s1 == s2);
    System.out.println(s1.equals(s2));

    System.out.println(s1.hashCode());
    System.out.println(s2.hashCode());
    System.out.println(s3.hashCode());
    System.out.println(s4.hashCode());
    System.out.println("--------------------");

    // 해시셋(집합)에 객체를 보관한다.
    HashSet<Student> set = new HashSet<Student>();
    set.add(s4);
    set.add(s3);
    set.add(s1);
    set.add(s2); // 이미 s2의 해시값과 같은 객체(s1)가 들어 있기 때문에 중복을 막기 위해 s2는 저장되지 않는다.

    // 해시셋에 보관된 객체를 꺼낸다.
    Object[] list = set.toArray();
    for (Object obj : list) {
      Student student = (Student) obj;
      System.out.printf("%s, %d, %s\n",
          student.name, student.age, student.working ? "재직중" : "실업중");
    }

    // 인스턴스가 다르더라도 인스턴스의 필드 값이 같을 경우
    // HashSet에 중복 저장되지 않도록 하려면,
    // hashCode()와 equals() 모두 오버라이딩 하라!
    // => hashCode()는 같은 필드 값을 갖는 경우 같은 해시코드를 리턴하도록 변경하고,
    // => equals()는 필드 값이 같을 경우 true를 리턴하도록 변경한다.
    //
  }

}







