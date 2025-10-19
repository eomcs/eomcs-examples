// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam03;

// [주제] instanceof 연산자로 런타임에서 타입 검사하기

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

  // 다중정의 메서드들을 하나로 합쳐서 런타임에 타입을 검사하도록 변경
  static String classify(Collection<?> c) {
    return c instanceof Set ? "집합" : c instanceof List ? "리스트" : "그 외";
  }

  public static void main(String[] args) {
    Collection<?>[] collections = {
      new HashSet<String>(), new ArrayList<BigInteger>(), new HashMap<String, String>().values()
    };

    for (Collection<?> c : collections) {
      System.out.println(classify(c));
    }

    // [정리]
    // - 헤갈릴 수 있는 다중정의 코드는 작성하지 않는 게 좋다.
    // - API 사용자가 매개변수를 넘기면서 어떤 다중정의 메서드가 호출될지를 모른다면 프로그램이 오동작하기 쉽다.
    // - 다중정의가 혼동을 일으키는 상황은 피해야 한다.
    // - 안전하고 보수적으로 가려면 매개변수 수가 같은 다중정의는 만들지 말자.
    // - 가변인수를 사용하는 메서드라면 다중정의를 아예 하지 말아야 한다.
    // - 다중정의하는 대신 메서드 이름을 다르게 지어주는 편이 나을 때도 있다.
    //   예) ObjectOutputStream의 writeBoolean(), writeInt(), writeDouble() 등

    // [생성자 다중정의]
    // - 생성자의 경우 두 번째 생성자부터는 무조건 다중정의가 된다.
    // - 정적 팩토리를 사용하여 다중정의를 피할 수 있다.

  }
}
