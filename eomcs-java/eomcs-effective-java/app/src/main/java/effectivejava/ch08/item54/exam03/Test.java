// # 아이템 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
// - null이 아닌, 빈 배열이나 컬렉션을 반환하라.
//   null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다.
//   그렇다고 성능이 좋은 것도 아니다.
//

package effectivejava.ch08.item54.exam03;

// [주제] 컬렉션이 비었을 때 빈 컬렉션을 반환하는 경우 - 매번 똑 같은 '불변' 빈 컬렉션 반환하기

import java.util.ArrayList;
import java.util.Collections;
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
    // Collections.emptyList()는 불변 빈 리스트를 반환한다.
    // 매번 똑 같은 빈 리스트를 반환하므로 메모리 낭비가 없다.
    return cheeses.isEmpty()
        ? Collections.emptyList()
        : new ArrayList<>(cheeses); // 컬렉션이 비었을 때 빈 컬렉션을 반환
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
    // - Collections.emptyList()를 사용하면 매번 똑 같은 '불변' 빈 컬렉션을 반환한다.
    //   객체 생성으로 인한 성능 저하를 막을 수 있다.
    // - 집합에는 Collections.emptySet()를 사용하고,
    //   맵에는 Collections.emptyMap()을 사용하면 된다.

  }
}
