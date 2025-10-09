// # 아이템 28. 배열보다는 리스트를 사용하라
// - 배열은 공변(함께 변한다는 의미)이다.
//   Sub가 Super의 하위 타입이라면, Sub[]는 Super[]의 하위 타입이다.
// - 제네릭은 불공변이다.
//   서로 다른 Type1, Type2에 대해, Type1<T>와 Type2<T>는 아무런 관계가 없다.
//

package effectivejava.ch05.item28.exam03;

// [주제] 배열에 제네릭을 적용하는 방법: 제네릭 적용 전

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Chooser {
  private final Object[] choiceArray;

  public Chooser(Collection choices) {
    this.choiceArray = choices.toArray();
  }

  public Object choose() {
    // 현재 스레드에 최적화된 난수 생성기를 준비한다.
    Random rnd = ThreadLocalRandom.current();

    // 난수에 해당하는 인덱스의 원소를 리턴한다.
    // (난수: 0 <= 난수 < choiceArray.length)
    return choiceArray[rnd.nextInt(choiceArray.length)];
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Chooser chooser = new Chooser(List.of("홍길동", "임꺽정", "장길산", "전우치", "김삿갓"));

    // 리턴 타입이 Object 이기 때문에 형변환이 필요하다.
    String choice = (String) chooser.choose();

    System.out.println(choice);
  }
}
