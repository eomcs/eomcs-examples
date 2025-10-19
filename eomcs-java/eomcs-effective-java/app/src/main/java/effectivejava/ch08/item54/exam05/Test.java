// # 아이템 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
// - null이 아닌, 빈 배열이나 컬렉션을 반환하라.
//   null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어난다.
//   그렇다고 성능이 좋은 것도 아니다.
//

package effectivejava.ch08.item54.exam05;

// [주제] 컬렉션이 비었을 때 빈 배열을 반환하는 경우 - 미리 생성한 빈 배열을 반환하기

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CheeseStore {
  // 빈 배열을 반환할 때 사용할 배열 객체 생성
  private static final String[] EMPTY_CHEESE_ARRAY = new String[0];

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
    // 단, 미리 생성한 빈 배열을 재사용하므로 메모리 낭비가 없다.
    //    return cheeses.toArray(EMPTY_CHEESE_ARRAY);

    // Java 11+ 환경에서 권장되는 사용법
    return cheeses.toArray(String[]::new);
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

    // [toArray() 메서드 사용 팁]
    // - toArray(new T[0])이 toArray(new T[size])보다 성능이 더 좋다.
    // - toArray(new T[0])이 성능이 더 좋은 이유
    //   1) 길이가 0인 배열을 넘기면 JDK 구현은 내부에서 정확한 크기의 새 배열을 한 번만 만들고,
    //      JIT이 이 경로를 잘 최적화(escape analysis, 배열 복사 경로 단순화 등)하는 경우가 많다.
    //   2) 큰 배열(정확 크기 또는 그 이상)을 미리 만들어 넘기면,
    //      다음과 같은 작업을 수행할 수 있기 때문에 오히려 성능이 떨어질 수 있다.
    //      - 길이 비교 분기,
    //      - (길이가 큰 경우) a[size] = null 쓰기,
    //      - 호출 지점에서의 배열 생성 비용(이미 한 번 생성했음)
    //   3) 어차피 결과를 담을 정확한 크기의 배열 하나는 반드시 필요합니다.
    //      - 미리 만들면 호출자 쪽에서 1회 할당
    //      - new T[0]를 넘기면 toArray 안에서 1회 할당
    //      - 할당 횟수는 결국 1회이며, 후자의 경로가 JIT에 의해 더 잘 최적화되는 사례가 많다.
    //   4) 연구/벤치마크 결과
    //      - 여러 JDK 버전에서의 벤치마크(마이크로벤치 포함)에서
    //        toArray(new T[0])가 동등하거나 더 빠른 경우가 흔했고,
    //        “정확 크기의 배열을 미리 만들어 넘기는” 미세 최적화는
    //        오히려 느려질 수 있음이 반복적으로 관찰되었다.

    // Java 11+ 환경: 권장됨!
    // 정확 크기의 배열을 JDK가 한 번만 생성. 타입 안전·가독성·성능 모두 우수.
    //        String[] arr = list.toArray(String[]::new);

    // Java 8~10 환경:
    //        String[] arr = list.toArray(new String[0]);
  }
}
