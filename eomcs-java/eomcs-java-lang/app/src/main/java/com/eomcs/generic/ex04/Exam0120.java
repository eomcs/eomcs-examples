package com.eomcs.generic.ex04;

import java.lang.reflect.Array;

public class Exam0120 {

  // 제네릭의 타입을 지정할 때 수퍼 클래스를 지정하면 그 타입 및 서브 타입만 가능하다.
  // => 다음 클래스의 타입 파라미터에는 
  //    Manager 나 그 하위 클래스만 지정할 수 있다. 
  //
  static class ArrayList<T extends Manager> {
    T[] arr;
    int index = 0;

    @SuppressWarnings("unchecked")
    public ArrayList(Class<?> clazz) {
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

    //ArrayList<Member> obj = new ArrayList<>(Member.class); // 컴파일 오류!
    //ArrayList<Teacher> obj = new ArrayList<>(Teacher.class); // 컴파일 오류!
    //ArrayList<Student> obj = new ArrayList<>(Student.class); // 컴파일 오류!

    ArrayList<Manager> obj = new ArrayList<>(Manager.class); // OK!
    //ArrayList<Administrator> obj = new ArrayList<>(Administrator.class); // OK!

    obj.add(new Manager());
    obj.add(new Administrator());

    System.out.println(obj.get(0));
    System.out.println(obj.get(1));
  }

}
