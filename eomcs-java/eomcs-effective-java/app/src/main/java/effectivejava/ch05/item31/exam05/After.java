// # 아이템 28. 배열보다는 리스트를 사용하라

package effectivejava.ch05.item31.exam05;

// [주제] 한정적 와일드카드 타입을 적용하기: Item28 예제에 적용 한 후 문제점 해결

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Chooser2<T> {
  private final List<T> choiceList;

  // 파라미터 선언에 한정적 와일드카드 타입을 사용하기
  public Chooser2(Collection<? extends T> choices) {
    this.choiceList = new ArrayList<>(choices);
  }

  public T choose() {
    Random rnd = ThreadLocalRandom.current();
    return choiceList.get(rnd.nextInt(choiceList.size()));
  }
}

public class After {
  public static void main(String[] args) throws Exception {
    // Chooser2 생성자는 Number 타입이나 그 하위 타입을 원소로 갖는 Collection을 파라미터로 받는다.
    // List<Integer>는 Collection<? extends Number>의 조건에 부합하므로 컴파일 된다.
    List<Integer> integers = List.of(1, 2, 3);
    Chooser2<Number> chooser = new Chooser2<>(integers);

    // List<Double>은 Collection<? extends Number>의 조건에 부합하므로 컴파일 된다.
    List<Double> doubles = List.of(1.0, 2.0, 3.0);
    Chooser2<Number> chooser2 = new Chooser2<>(doubles);
  }
}
