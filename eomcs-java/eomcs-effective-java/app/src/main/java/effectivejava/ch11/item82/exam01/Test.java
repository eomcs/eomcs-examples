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

package effectivejava.ch11.item82.exam01;

// [주제] 스레드 안전성 클래스와 JSR-305 애너테이션
// 1) 불변(immutable) 클래스
//    - 이 클래스의 인스턴스는 마치 상수와 같아서 외부 동기화도 필요 없다.
//    - 예) String, Integer, Long, BigInteger 등
// 2) 무조건적 스레드 안전(unconditionally thread-safe) 클래스
//    - 이 클래스의 인스턴스는 수정될 수 있으나, 내부에서 충실히 동기화하여
//      별도의 외부 동기화 없이 동시에 사용해도 안전하다.
//    - 예) AtomicLong, ConcurrentHashMap 등
// 3) 조건부 스레드 안전(conditionally thread-safe) 클래스
//    - 무조건적 스레드 안전과 같으나, 일부 메서드는 동시에 사용하려면 외부 동기화가 필요하다.
//    - 예) Collections.synchronized 래퍼 메서드가 반환한 컬렉션들이 여기에 속한다.
//         이 컬렉션들이 반환한 반복자(iterator)는 외부에서 동기화해야 한다.
// 4) 스레드 안전하지 않음(not thread-safe) 클래스
//    - 이 클래스의 인스턴스는 수정될 수 있다. 동시에 사용하려면 각각의 혹은 일련의 메서드 호출을
//      클라이언트가 선택한 외부 동기화 메커니즘으로 감싸야 한다.
//    - 예) ArrayList, HashMap 같은 기본 컬렉션
// 5) 스레드 적대적(thread-hostile) 클래스
//    - 이 클래스는 모든 메서드 호출을 외부 동기화로 감싸더라도 멀티스레드 환경에서 안전하지 않다.
//      이 수준의 클래스는 일반적으로 정적 데이터를 아무 동기화 없이 수정한다.
//    - 일반적으로 문제를 고쳐 재배포하거나 사용 자제(deprecated) API로 지정한다.
//
// [JSR-305 스레드 안전성 애너테이션] javax.annotation.concurrent 패키지
// - @Immutable: 불변 클래스에 붙인다.
// - @ThreadSafe: 무조건적 스레드 안전과 조건부 스레드 안전 클래스에 붙인다.
// - @NotThreadSafe: 스레드 안전하지 않은 클래스에 붙인다.
// - @GuardedBy("lock"): 특정 락이 보호하는 필드나 메서드에 붙인다.

// [애너테이션의 목적]
// - 정적 분석 도구와 IDE, 개발자를 위한 것이다.
//   - 코드 분석 도구: SpotBugs, ErrorProne 등
//   - IDE: 코드 분석, 경고, 제안 제공 등에 활용
//   - 개발자: 소스 코드의 의도를 명확히 전달하는 데 도움
// - 런타임 애너테이션이 아닌 "컴파일 타임 애너테이션"이며,
//   javadoc 생성 시 API 문서에 포함되지 않는다.
//   따라서 스레드 안전성을 문서화하려면 javadoc 주석을 명시적으로 작성해야 한다.
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.concurrent.*;

/**
 * 스레드 안전성 수준: 불변
 *
 * <p>이 클래스는 불변이므로 자동으로 스레드 안전합니다.
 */
@Immutable
final class ImmutablePoint {
  private final int x;
  private final int y;

  public ImmutablePoint(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

/**
 * 스레드 안전성 수준: 무조건적 스레드 안전
 *
 * <p>내부적으로 충분한 동기화가 되어 있어 외부 동기화 불필요
 */
@ThreadSafe
class SafeCache<K, V> {
  private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

  public V get(K key) {
    return cache.get(key);
  }

  public void put(K key, V value) {
    cache.put(key, value);
  }
}

/**
 * 스레드 안전성 수준: 조건부 스레드 안전
 *
 * <p>일부 메서드는 외부 동기화 필요
 */
@ThreadSafe
class ConditionalSafeMap {
  private final Map<String, String> map = Collections.synchronizedMap(new HashMap<>());

  /**
   * 이 메서드 호출 시 외부 동기화 필요:
   *
   * <pre>{@code
   * synchronized (map) {
   *     for (Map.Entry<String, String> e : map.entrySet()) {
   *         // ...
   *     }
   * }
   * }</pre>
   */
  public Set<Entry<String, String>> entrySet() {
    return map.entrySet();
  }
}

/**
 * 스레드 안전성 수준: 스레드 안전하지 않음
 *
 * <p>외부에서 동기화 필요
 */
@NotThreadSafe
class UnsafeCounter {
  private int count;

  public void increment() {
    count++; // 동기화 없음!
  }
}

/**
 * 스레드 안전성 수준: 스레드 적대적
 *
 * <p>외부 동기화로도 안전하게 만들 수 없음 (사용 금지)
 */
@NotThreadSafe
class HostileClass {
  // 내부적으로 정적 변수를 비동기적으로 수정하는 등
  // 절대 사용하면 안 되는 코드
}

@ThreadSafe
class GuardedResource {
  private final Object lock = new Object();

  @GuardedBy("lock") // lock으로 보호됨을 명시
  private List<String> items = new ArrayList<>();

  public void addItem(String item) {
    synchronized (lock) {
      items.add(item);
    }
  }
}

public class Test {
  public static void main(String[] args) {}
}
