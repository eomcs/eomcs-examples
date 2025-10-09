// # 아이템 28. 배열보다는 리스트를 사용하라

package effectivejava.ch05.item31.exam05;

// [주제] 한정적 와일드카드 타입을 적용하기: Item28 예제에 적용 전 문제점 확인

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Chooser<T> {
  private final List<T> choiceList;

  public Chooser(Collection<T> choices) {
    this.choiceList = new ArrayList<>(choices);
  }

  public T choose() {
    Random rnd = ThreadLocalRandom.current();
    return choiceList.get(rnd.nextInt(choiceList.size()));
  }
}

public class Before {
  public static void main(String[] args) throws Exception {
    // Chooser 생성자는 Number 타입을 원소를 갖는 Collection만 파라미터로 받는다.
    // Number의 하위 타입을 원소로 갖는 Collection은 허용하지 않는다.
    List<Integer> integers = List.of(1, 2, 3);
    //    Chooser<Number> chooser = new Chooser<>(integers); // 컴파일 오류!

    List<Double> doubles = List.of(1.0, 2.0, 3.0);
    //    Chooser<Number> chooser2 = new Chooser<>(doubles); // 컴파일 오류!
  }
}
