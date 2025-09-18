// List 와 forEach() - forEach() 사용 후
package com.eomcs.oop.ex12;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Exam0920 {

  public static void main(String[] args) {
    ArrayList<String> names = new ArrayList<>();
    names.add("홍길동");
    names.add("임꺽정");
    names.add("유관순");
    names.add("김구");
    names.add("안중근");

    class MyConsumer<T> implements Consumer<T> {
      @Override
      public void accept(T item) {
        // 이 메서드는 List의 각 항목을 반복할 때마다 호출된다.
        // 따라서 List의 각 항목에 대해 처리할 작업이 있다면 
        // 이 메서드에 작성하라.
        System.out.println(item);
      }
    }
    names.forEach(new MyConsumer<String>());

    // forEach() 메서드는 대략적으로 다음과 같이 구현되어 있다.
    // 
    //    public void forEach(Consumer<? super E> action) {
    //      for (E value : this) {
    //        action.accept(value);
    //      }
    //    }

  }
}