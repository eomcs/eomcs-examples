// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam01;

// [주제] 다중정의(overloading) 메서드를 호출할 때 주의할 점

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

  static String classify(Set<?> s) {
    return "집합";
  }

  static String classify(List<?> list) {
    return "리스트";
  }

  static String classify(Collection<?> c) {
    return "그 외";
  }

  public static void main(String[] args) {
    Collection<?>[] collections = {
      new HashSet<String>(), new ArrayList<BigInteger>(), new HashMap<String, String>().values()
    };

    for (Collection<?> c : collections) {
      System.out.println(classify(c));
    }

    // [출력 결과]
    // 그 외
    // 그 외
    // 그 외

    // [이유]
    // - 다중정의 된 메서드 중 어느 메서드를 호출할지는 컴파일타임에 결정된다.
    // - 컴파일러는 매개변수의 정적 타입(컴파일 시점의 타입)을 기준으로
    //   어떤 메서드를 호출할지 결정한다.
    // - 위 예제에서 for-each문의 변수 c의 정적 타입은 Collection<?>이다.
    // - 따라서 classify(Collection<?>) 메서드가 호출된다.
    // - 다중정의 메서드를 사용할 때는
    //   매개변수의 정적 타입이 어떤지 항상 염두에 두어야 한다.
  }
}
