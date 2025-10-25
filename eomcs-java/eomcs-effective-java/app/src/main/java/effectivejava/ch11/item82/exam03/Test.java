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

package effectivejava.ch11.item82.exam03;

// [주제] 스레드 안전성 문서화 예

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.concurrent.*;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 불변 사용자 클래스
 *
 * <h3>스레드 안전성</h3>
 *
 * <p>이 클래스는 불변이므로 자동으로 스레드 안전합니다.
 *
 * @implNote 모든 필드는 final이며, 생성 후 변경 불가능합니다.
 */
@Immutable // 정적 분석 도구를 위한 애너테이션
final class User {
  private final String id;
  private final String name;

  /**
   * 사용자 객체를 생성합니다.
   *
   * @param id 사용자 ID
   * @param name 사용자 이름
   */
  public User(String id, String name) {
    this.id = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
  }

  /** 사용자 ID를 반환합니다 */
  public String getId() {
    return id;
  }

  /** 사용자 이름을 반환합니다 */
  public String getName() {
    return name;
  }
}

/**
 * 스레드 안전한 사용자 캐시
 *
 * <h3>스레드 안전성</h3>
 *
 * <p>이 클래스는 무조건적으로 스레드 안전합니다. 모든 public 메서드는 여러 스레드에서 동시에 호출해도 안전합니다.
 *
 * <p><b>동기화 정책:</b>
 *
 * <ul>
 *   <li>{@code cache} - 내부적으로 ConcurrentHashMap 사용
 *   <li>{@code stats} - {@code this}로 보호됨
 * </ul>
 *
 * @implNote 내부적으로 ConcurrentHashMap을 사용하여 락 경합을 최소화합니다.
 * @see ConcurrentHashMap
 * @since 1.0
 */
@ThreadSafe // 정적 분석 도구를 위한 애너테이션
class UserCache {

  /** 사용자 데이터 캐시 ConcurrentHashMap이 내부적으로 동기화 제공 */
  private final ConcurrentHashMap<String, User> cache = new ConcurrentHashMap<>();

  /** 캐시 통계 정보 {@code this} 락으로 보호됨 */
  @GuardedBy("this") // 정적 분석 도구를 위한 애너테이션
  private int hitCount;

  /**
   * 사용자를 캐시에 추가합니다.
   *
   * <p><b>스레드 안전성:</b> 이 메서드는 스레드 안전합니다.
   *
   * @param id 사용자 ID
   * @param user 사용자 객체
   * @throws NullPointerException id 또는 user가 null인 경우
   */
  public void put(String id, User user) {
    cache.put(Objects.requireNonNull(id), Objects.requireNonNull(user));
  }

  /**
   * 캐시에서 사용자를 조회합니다.
   *
   * <p><b>스레드 안전성:</b> 이 메서드는 스레드 안전합니다.
   *
   * @param id 사용자 ID
   * @return 사용자 객체, 없으면 null
   */
  public User get(String id) {
    User user = cache.get(id);
    if (user != null) {
      incrementHitCount();
    }
    return user;
  }

  /**
   * 캐시 히트 카운트를 증가시킵니다.
   *
   * <p><b>동기화:</b> {@code this}로 동기화됨
   */
  private synchronized void incrementHitCount() {
    hitCount++;
  }

  /**
   * 캐시 히트 수를 반환합니다.
   *
   * <p><b>동기화:</b> {@code this}로 동기화됨
   *
   * @return 캐시 히트 수
   */
  public synchronized int getHitCount() {
    return hitCount;
  }
}

/**
 * 스레드 안전한 카운터 클래스입니다.
 *
 * <p><b>스레드 안전성:</b> 이 클래스는 무조건적으로 스레드 안전합니다. 여러 스레드에서 동시에 호출해도 안전합니다.
 *
 * @implNote 모든 public 메서드는 내부적으로 동기화됩니다.
 */
@ThreadSafe
class Counter {

  /**
   * 현재 카운트 값
   *
   * <p><b>동기화:</b> {@code this}로 보호됩니다.
   */
  @GuardedBy("this")
  private int count;

  /**
   * 카운터를 증가시킵니다.
   *
   * <p>이 메서드는 스레드 안전합니다.
   */
  public synchronized void increment() {
    count++;
  }
}

public class Test {
  public static void main(String[] args) {}
}
