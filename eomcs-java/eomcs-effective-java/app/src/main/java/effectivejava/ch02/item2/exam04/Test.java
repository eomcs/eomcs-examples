// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam04;

import static effectivejava.ch02.item2.exam04.NyPizza.Size.*;
import static effectivejava.ch02.item2.exam04.Pizza.Topping.*;

// Using the hierarchical builder (Page 16)
public class Test {
  public static void main(String[] args) {
    NyPizza pizza = new NyPizza.Builder(SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
    Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();

    System.out.println(pizza);
    System.out.println(calzone);
  }
}
