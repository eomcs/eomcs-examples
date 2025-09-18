// 메서드 레퍼런스 - 생성자 레퍼런스 활용
package com.eomcs.oop.ex12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Supplier;

public class Exam0751 {

  @SuppressWarnings("unchecked")
  static <T> Collection<T> prepareNames(Supplier<Collection<T>> factory, T... values) {
    Collection<T> list = factory.get(); // => new ArrayList<String>()
    for (T value : values) {
      list.add(value);
    }
    return list;
  }

  static <T> void print(Iterator<T> i) {
    while (i.hasNext()) {
      System.out.print(i.next() + ",");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // 1) 로컬 클래스 사용하여 Supplier 구현체 만들기
    class MySupplier<T> implements Supplier<T> {
      @Override
      public T get() {
        return (T) new ArrayList<>();
      }
    };
    Supplier<Collection<String>> supplier = new MySupplier<Collection<String>>();
    Collection<String> list = prepareNames(supplier, "홍길동", "임꺽정", "유관순", "임꺽정");
    print(list.iterator());

  }

}


