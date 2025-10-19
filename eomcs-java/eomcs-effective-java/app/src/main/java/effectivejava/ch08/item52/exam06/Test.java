// # 아이템 52. 다중정의는 신중히 사용하라
// - 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
// - 일반적으로 매개변수가 같을 때는 다중정의를 피하는 게 좋다.
// - 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
//

package effectivejava.ch08.item52.exam06;

// [주제] 근본적으로 타입이 다르면 다중정의 메서드 선택에 혼란이 없다.

import java.io.Serializable;

public class Test {

  public static void main(String[] args) {
    // [근본적으로 타입이 달라서 헷갈리지 않는 경우]
    // - 클래스 타입과 배열 타입은 근본적으로 다르기 때문에 헷갈리지 않는다.
    // - 인터페이스 타입(Serializable과 Cloneable 제외)과 배열 타입도 근본적으로 다르기 때문에 헷갈리지 않는다.
    // - String과 Throwable처럼 상위/하위 관계가 아닌 두 클래스는
    //   '관련 없다(unrelated)'고 간주하므로 헷갈리지 않는다.
    //   즉 두 클래스의 공통 인스턴스가 존재할 수 없기 때문에 근본적으로 다르다.

    // 단 인터페이스 중에서 배열 타입과 혼란을 일으키는 경우:
    int[] array = {1, 2, 3};
    System.out.println(array instanceof Serializable); // true
    System.out.println(array instanceof Cloneable); // true
  }
}
