// # 아이템 28. 배열보다는 리스트를 사용하라
// - 배열은 공변(함께 변한다는 의미)이다.
//   Sub가 Super의 하위 타입이라면, Sub[]는 Super[]의 하위 타입이다.
// - 제네릭은 불공변이다.
//   서로 다른 Type1, Type2에 대해, Type1<T>와 Type2<T>는 아무런 관계가 없다.
//

package effectivejava.ch05.item28.exam05;

// [주제] 배열에 제네릭을 적용하는 방법: 배열 대신 리스트를 사용하기
// - 배열 대신 리스트를 사용하는 것이 더 낫다.
// - 배열 생성 오류나 비검사 경고가 발생하지 않는다.
// - 코드가 조금 복잡해지고 약간 느려질 수는 있지만,
//   타입 안전성과 상호운용성은 좋아진다.

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

public class Test {
  public static void main(String[] args) throws Exception {
    Chooser<String> chooser = new Chooser<>(List.of("홍길동", "임꺽정", "장길산", "전우치", "김삿갓"));
    String choice = chooser.choose();
    System.out.println(choice);
  }
}
