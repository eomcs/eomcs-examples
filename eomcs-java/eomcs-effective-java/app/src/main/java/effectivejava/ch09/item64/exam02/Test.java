// # 아이템 64. 객체는 인터페이스를 사용해 참조하라
// - 적합한 인터페이스만 있다면
//   매개변수뿐 아니라 반환값, 변수, 필드를 전부 인터페이스 타입으로 선언하라.
// - 적합한 인터페이스가 없다면
//   클래스의 계층구조 중 필요한 기능을 만족하는 가장 덜 구체적인 상위 클래스를 타입으로 사용하라.
//
package effectivejava.ch09.item64.exam02;

// [주제] 적합한 인터페이스가 없다면 클래스로 참조해야 한다.

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Test {
  public static void main(String[] args) throws Exception {
    // [적합한 인터페이스가 없는 경우]
    // 1) 값 클래스
    //    - 인터페이스가 별도로 존재하지 않는다.
    //    - 보통 final 이라 하위 클래스가 존재하지 않는다.
    //    - 매개변수, 변수, 필드, 반환 타입으로 사용해도 무방하다.
    String str = "안녕!";
    BigInteger bigInteger = new BigInteger("300");

    // 2) 클래스 기반으로 작성된 프레임워크가 제공하는 객체
    //    - 특정 구현 클래스보다는 기반 클래스(보통 추상클래스)를 사용해 참조하라.
    //    - 예) OutputStream 등 java.io 패키지의 여러 클래스가 이에 해당한다.
    InputStream in = new FileInputStream("words.txt");

    // 3) 인터페이스에 없는 특별한 메서드를 제공하는 클래스
    PriorityQueue<String> queue = new PriorityQueue<>();
    Comparator<?> comparator = queue.comparator(); // Queue 인터페이스에 없는 메서드 호출

    // [정리]
    // - 클래스 타입을 직접 사용하는 경우는 이런 추가 메서드를 꼭 사용해야 하는 경우로 최소화해야 한다.
    //   절대 남발하지 말아야 한다.
    // - 적합한 인터페이스가 없다면 클래스의 계층구조 중 필요한 기능을 만족하는
    //   가정 덜 구체적인 상위 클래스를 타입으로 사용하라.
  }
}
