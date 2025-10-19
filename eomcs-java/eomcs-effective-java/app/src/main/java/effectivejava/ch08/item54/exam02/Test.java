// # 아이템 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
// - null이 아닌, 빈 배열이나 컬렉션을 반환하라.
//   null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다.
//   그렇다고 성능이 좋은 것도 아니다.
//

package effectivejava.ch08.item54.exam02;

// [주제] 컬렉션이 비었을 때 빈 컬렉션을 반환하는 경우

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
    return new ArrayList<>(cheeses); // 컬렉션이 비었을 때 빈 컬렉션을 반환
  }
}

public class Test {

  public static void main(String[] args) {
    CheeseStore store = new CheeseStore();
    store.addCheese("Parmesan");
    store.addCheese("Cheddar");

    List<String> cheeses = store.getCheeses();
    for (String cheese : cheeses) {
      System.out.println(cheese);
    }

    // [정리]
    // - null 체크 코드를 작성할 필요가 없다.
    // - 코드가 간결해지고 가독성이 좋아진다.
  }
}
