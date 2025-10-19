// # 아이템 50. 적시에 방어적 복사본을 만들라
// - 늘 클라이언트가 클래스의 불변식을 깨뜨리지 못하도록 방어적으로 프로그래밍해야 한다.
// - 클래스가 클라이언트로 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면
//   그 요소는 반드시 방어적으로 복사해야 한다.
// - 복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할 일이 없음을 신뢰한다면,
//   방어적 복사를 수행하는 대신에
//   해당 구성요소를 수정했을 때의 책임이 클라이언트에게 있음을 문서에 명시하라.
//
package effectivejava.ch08.item50.exam04;

// [주제] Date 대신 불변 객체인 Instant를 사용하여 불변식을 지키는 예

import java.time.Instant;
import java.util.Objects;

final class Period {
  // Instant 같은 불변 객체를 사용하면 방어적 복사를 하지 않아도 된다.
  // LocalDateTime, ZonedDateTime 등도 불변 객체이므로 방어적 복사가 불필요하다.
  private final Instant start;
  private final Instant end;

  public Period(Instant start, Instant end) {
    this.start = Objects.requireNonNull(start, "start must not be null");
    this.end = Objects.requireNonNull(end, "end must not be null");

    if (this.start.isAfter(this.end)) {
      throw new IllegalArgumentException(this.start + " after " + this.end);
    }
  }

  public Instant start() {
    return start; // Instant는 불변이므로 복사 불필요
  }

  public Instant end() {
    return end; // Instant는 불변이므로 복사 불필요
  }

  @Override
  public String toString() {
    return start + " - " + end;
  }
}

public class Test {
  public static void main(String[] args) {
    Instant start = Instant.now();
    Instant end = start.plusSeconds(3600); // 1시간 후
    Period p = new Period(start, end);
    System.out.println(p);

    // 원본 변수 재할당으로 변경을 시도해도 Period에는 영향 없음
    end = end.minusSeconds(60 * 60 * 24 * 365); // 다른 Instant로 재할당
    System.out.println(p);

    // 반환값을 이용해 변경한 것처럼 보여도 Instant는 불변이므로 Period는 영향 없음
    Instant returnedEnd = p.end().minusSeconds(10_000);
    System.out.println(p);

    // [정리]
    // - 방어적 복사에는 성능 저하가 따르고, 또 항상 쓸 수 있는 것도 아니다.
    // - 같은 패키지에 속하는 클래스끼리 사용하는 경우처럼
    //   호출자가 컴포넌트 내부를 수정하지 않으리라 확신하면 방어적 복사를 생략할 수 있다.
    //   이러한 상황이라도 호출자에서 매개변수나 반환값을 수정하지 말아야 한다는 점을 문서에 명시하라.
    // - 방어적 복사를 생략해도 되는 상황은 해당 클래스와 그 클라이언트가 상호 신뢰할 수 있을 때,
    //   혹은 불변식이 깨지더라도 그 영향이 오직 호출한 클라이언트로 국한될 때로 한정해야 한다.

  }
}
