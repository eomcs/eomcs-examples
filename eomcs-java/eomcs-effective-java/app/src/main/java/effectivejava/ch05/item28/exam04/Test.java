// # 아이템 28. 배열보다는 리스트를 사용하라
// - 배열은 공변(함께 변한다는 의미)이다.
//   Sub가 Super의 하위 타입이라면, Sub[]는 Super[]의 하위 타입이다.
// - 제네릭은 불공변이다.
//   서로 다른 Type1, Type2에 대해, Type1<T>와 Type2<T>는 아무런 관계가 없다.
//

package effectivejava.ch05.item28.exam04;

// [주제] 배열에 제네릭을 적용하는 방법: 제네릭 적용 후

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Chooser<T> {
  private final T[] choiceArray;

  public Chooser(Collection<T> choices) {
    //    // 1) 컴파일 오류 발생
    //    //   error: incompatible types: Object[] cannot be converted to T[]
    //    // - toArray()의 리턴 타입은 Object[] 이다.
    //    // - 그런데 choiceArray는 T[] 타입이다.
    //    // - Object[]는 T[]의 하위 타입이 아니다. 그래서 컴파일 오류가 발생한다.
    //    this.choiceArray = choices.toArray();

    // 2) 컴파일 경고 발생
    //   warning: [unchecked] unchecked cast
    // - toArray()의 리턴 타입은 Object[] 이다.
    // - 그런데 Object[]를 T[]로 형변환 시도한다.
    // - T의 타입이 결정되지 않았기 때문에 컴파일러는 형변환의 안전성을 보장하지 못한다.
    // - 그래서 컴파일 경고가 발생하는 것이다.
    // - 물론 @SuppressWarnings("unchecked")를 붙여서 컴파일 경고를 제거할 수도 있다.
    // - 하지만 더 나은 방법은 경고의 원인을 제거하는 것이다.
    this.choiceArray = (T[]) choices.toArray();
  }

  public T choose() {
    // 현재 스레드에 최적화된 난수 생성기를 준비한다.
    Random rnd = ThreadLocalRandom.current();

    // 난수에 해당하는 인덱스의 원소를 리턴한다.
    // (난수: 0 <= 난수 < choiceArray.length)
    return choiceArray[rnd.nextInt(choiceArray.length)];
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Chooser<String> chooser = new Chooser<>(List.of("홍길동", "임꺽정", "장길산", "전우치", "김삿갓"));

    // 컴파일 시 리턴 타입을 String으로 자동 형변한하도록 처리한다.
    // 그래서 따로 형변환할 필요가 없다.
    // 이것이 제네릭을 쓰는 이유이다.
    String choice = chooser.choose();

    System.out.println(choice);
  }
}
