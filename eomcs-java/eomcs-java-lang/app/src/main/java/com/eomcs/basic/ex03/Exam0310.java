// 목록 조회: 반복문과 인덱스를 이용한 목록 조회
package com.eomcs.basic.ex03;

public class Exam0310 {

  static class MyList {
    Object[] list = new Object[5];
    int size;

    public void add(Object value) {
      list[size++] = value;
    }

    public Object get(int i) {
      return list[i];
    }

    public int size() {
      return size;
    }
  }

  static class Member {
    String name;
    int age;

    public Member(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  public static void main(String[] args) {

    Member m1 = new Member("홍길동", 20);
    Member m2 = new Member("임꺽정", 30);
    Member m3 = new Member("유관순", 17);

    MyList list = new MyList();
    list.add(m1);
    list.add(m2);
    list.add(m3);

    for (int i = 0; i < list.size(); i++) {
      Member m = (Member) list.get(i);
      System.out.printf("이름: %s, 나이: %d\n", m.name, m.age);
    }
  }
}
