// # 아이템 82. 스레드 안전성 수준을 문서화하라
// - 모든 클래스가 자신의 스레드 안전성 정보를 명확히 문서화해야 한다.
// - 정확한 언어로 명확히 설명하거나 스레드 안전성 애너테이션을 사용할 수 있다.
// - synchronized 한정자는 문서화와 관련이 없다.
//   메서드 선언에 synchronized 한정자를 선언할지는 구현 이슈일 뿐 API에 속하지 않는다.
// - 조건부 스레드 안전 클래스는 메서드를 어떤 순서로 호출할 때 외부 동기화가 요구되고,
//   그때 어떤 락을 얻어야 하는지도 알려줘야 한다.
// - 무조건적인 스레드 안전 클래스를 작성할 때는 synchronized 메서드가 아닌
//   비공개 lock 객체를 사용하라.
//   이렇게 해야 클라이언트나 하위 클래스에서 공기화 메커니즘을 깨뜨리는 걸 예방할 수 있고,
//   필요하다면 다음에 더 정교한 동시성을 제어 메커니즘으로 재구현할 여지가 생긴다.
// - 멀티스레드 환경에서도 API를 안전하게 사용하게 하려면
//   클래스가 지원하는 스레드 안정성 수준을 정확히 명시해야 한다.

package effectivejava.ch11.item82.exam04;

import java.util.List;
import javax.annotation.concurrent.ThreadSafe;

// [주제] 조건부 스레드 안전한 클래스의 문서화 예

/**
 * 이 클래스의 스레드 안전성 수준: 조건부 스레드 안전
 *
 * <p>개별 메서드는 스레드 안전하지만, 연속된 호출은 외부 동기화 필요
 *
 * @implNote 내부적으로 ConcurrentHashMap 사용
 */
@ThreadSafe
public class Test {
  /**
   * 스레드 안전하지만, 반복 중에는 외부 동기화 필요
   *
   * <pre>{@code
   * List<String> list = Test.synchronizedList(new ArrayList<>());
   * synchronized (list) {
   *     for (String s : list) {
   *         // 안전하게 반복
   *     }
   * }
   * }</pre>
   *
   * 이대로 따르지 않으면 동작을 예측할 수 없다.
   */
  public static <T> List<T> synchronizedList(List<T> list) {
    return null;
  }

  public static void main(String[] args) {
    // [정리]
    // - 클래스의 스레드 안전성은 보통 클래스의 문서화 주석에 기재한다.
    //   다만 독특한 특성의 메서드라면 synchronizedList() 처럼 메서드의 주석에 기재한다.
    // - 열거 타입은 굳이 불변이라고 쓰지 않아도 된다.
    // - 반환 타입만으로는 명확히 알 수 없는 정적 팩토리라면, synchronizedList()처럼
    //   자신이 반환하는 객체의 스레드 안전성을 반드시 문서화 해야 한다.
  }
}
