// # 아이템 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
// - null이 아닌, 빈 배열이나 컬렉션을 반환하라.
//   null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다.
//   그렇다고 성능이 좋은 것도 아니다.
//

package effectivejava.ch08.item54.exam01;

// [주제] 컬렉션이 비었을 때 null을 반환하는 경우

import java.util.ArrayList;
import java.util.List;

class CheeseStore {
  private final List<String> cheeses = new ArrayList<>();

  public void addCheese(String cheese) {
    cheeses.add(cheese);
  }

  public void removeCheese(String cheese) {
    cheeses.remove(cheese);
  }

  public List<String> getCheeses() {
    return cheeses.isEmpty() ? null : new ArrayList<>(cheeses); // 컬렉션이 비었을 때 null 반환
  }
}

public class Test {

  public static void main(String[] args) {
    CheeseStore store = new CheeseStore();
    store.addCheese("Parmesan");
    store.addCheese("Cheddar");

    List<String> cheeses = store.getCheeses();
    // cheeses가 null일 수 있으므로 NullPointerException 방지를 위해
    // 항상 null 체크를 해주어야 한다.
    if (cheeses != null) {
      for (String cheese : cheeses) {
        System.out.println(cheese);
      }
    }

    // [문제점]
    // - API 사용자가 매번 null 체크를 하는 방어 코드를 넣어줘야 한다.
    //   null 체크를 깜빡하면 NullPointerException이 발생할 수 있다.
    // - 코드가 장황해지고 가독성이 떨어진다.
    // - 성능 면에서도 null을 반환하는 것이 유리하지 않다.
    //   빈 컬렉션을 반환하는 것과 큰 차이가 없다.
  }
}
