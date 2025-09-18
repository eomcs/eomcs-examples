// 목록 조회: toArray()
package com.eomcs.basic.ex03;

public class Exam0312 {

  static class MyList<E> {
    Object[] list = new Object[5];
    int size;

    public void add(E value) {
      list[size++] = value;
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
      return (E) list[i];
    }

    public int size() {
      return size;
    }

    public Object[] toArray() {
      Object[] arr = new Object[size];
      for (int i = 0; i < size; i++) {
        arr[i] = list[i];
      }
      return arr;
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

    MyList<Member> list = new MyList<>();
    list.add(m1);
    list.add(m2);
    list.add(m3);

    Object[] arr = list.toArray();

    for (Object item : arr) {
      Member m = (Member) item;
      System.out.printf("이름: %s, 나이: %d\n", m.name, m.age);
    }
  }
}
