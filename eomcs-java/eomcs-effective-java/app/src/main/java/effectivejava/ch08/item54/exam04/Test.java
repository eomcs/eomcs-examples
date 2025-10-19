// # 아이템 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
// - null이 아닌, 빈 배열이나 컬렉션을 반환하라.
//   null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다.
//   그렇다고 성능이 좋은 것도 아니다.
//

package effectivejava.ch08.item54.exam04;

// [주제] 컬렉션이 비었을 때 빈 배열을 반환하는 경우

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

  public String[] toArray() {
    // 컬렉션이 비었다면 toArray()는 아규먼트로 받은 '길이가 0인 배열'을 그대로 반환할 것이다.
    return cheeses.toArray(new String[0]);
  }
}

public class Test {

  public static void main(String[] args) {
    CheeseStore store = new CheeseStore();
    store.addCheese("Parmesan");
    store.addCheese("Cheddar");

    String[] cheeses = store.toArray();
    for (String cheese : cheeses) {
      System.out.println(cheese);
    }
  }
}
