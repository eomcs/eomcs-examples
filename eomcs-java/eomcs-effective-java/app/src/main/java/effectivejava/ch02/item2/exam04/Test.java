// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam04;

import static effectivejava.ch02.item2.exam04.NyPizza.Size.*;
import static effectivejava.ch02.item2.exam04.Pizza.Topping.*;

// 빌더 패턴(Builder pattern)의 이점
// - 계층적으로 설계된 클래스와 함께 쓰기에 좋다.
// - 추상 클래스는 추상 빌더를 갖고, 구체 클래스는 구체 빌더를 갖는다.
public class Test {
  public static void main(String[] args) {
    NyPizza pizza = new NyPizza.Builder(SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
    Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();

    System.out.println(pizza);
    System.out.println(calzone);

    // [문제점]
    // - 객체를 만들려면 빌더부터 생성 -> 성능에 민감한 상황에서 문제가 될 수 있다.
    // - 점층적 생성자 패턴보다 코드가 장황해서 매개변수가 4개 이상 되어야 값어치를 한다.
    //   그럼에도 불구하고 API는 시간이 지날수록 매개변수가 많아지는 경향이 있기 때문에
    //   처음부터 빌더로 시작하는 게 나을 때가 많다.

    // [결론]
    // - 생성자나 정적 팩토리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는 게 더 낫다.
  }
}
