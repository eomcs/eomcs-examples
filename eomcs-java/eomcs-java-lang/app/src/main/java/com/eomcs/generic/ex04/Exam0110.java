package com.eomcs.generic.ex04;

import java.lang.reflect.Array;

public class Exam0110 {

  // 제네릭의 타입을 지정할 때 수퍼 클래스를 지정하지 않으면 모든 타입이 가능하다.
  // 즉 T 자리에 어떤 타입이라도 올 수 있다.
  //
  static class ArrayList<T> {
    T[] arr;
    int index = 0;

    @SuppressWarnings("unchecked")
    public ArrayList(Class<?> clazz) {
      // 제네릭 타입 파라미터로 레퍼런스는 선언할 수 있지만, 인스턴스를 생성할 수는 없다.
      //this.arr = new T[10]; // 컴파일 오류!

      // 다음과 같이 Array.newInstance()로 배열을 생성해야 한다.
      this.arr = (T[])Array.newInstance(clazz, 10);
    }

    public void add(T v) {
      arr[index++] = v;
    }

    public T get(int index) {
      return arr[index];
    }
  }

  public static void main(String[] args) {

    ArrayList<Member> obj = new ArrayList<>(Member.class);
    obj.add(new Member());
    obj.add(new Student());
    obj.add(new Teacher());
    obj.add(new Manager());
    obj.add(new Administrator());

    // 클래스 관계:
    /*
     *            Member
     *        /     |       \
     *   Teacher  Student  Manager
     *                       |
     *                  Administrator
     */

    System.out.println(obj.get(0));
    System.out.println(obj.get(1));
    System.out.println(obj.get(2));
    System.out.println(obj.get(3));


    // 제네릭의 타입의 수퍼 클래스를 지정하지 않았기 때문에 어떤 타입이라도 가능하다.
    ArrayList<Manager> obj2 = new ArrayList<>(Manager.class);
    ArrayList<Teacher> obj3 = new ArrayList<>(Teacher.class);
    ArrayList<Student> obj4 = new ArrayList<>(Student.class);
    ArrayList<Administrator> obj5 = new ArrayList<>(Administrator.class);
    ArrayList<Manager> obj6 = new ArrayList<>(Manager.class);
    ArrayList<String> obj7 = new ArrayList<>(String.class);
  }

}








