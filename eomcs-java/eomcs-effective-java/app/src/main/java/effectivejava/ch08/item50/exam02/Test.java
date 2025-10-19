// # 아이템 50. 적시에 방어적 복사본을 만들라
// - 늘 클라이언트가 클래스의 불변식을 깨뜨리지 못하도록 방어적으로 프로그래밍해야 한다.
// - 클래스가 클라이언트로 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면
//   그 요소는 반드시 방어적으로 복사해야 한다.
// - 복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할 일이 없음을 신뢰한다면,
//   방어적 복사를 수행하는 대신에
//   해당 구성요소를 수정했을 때의 책임이 클라이언트에게 있음을 문서에 명시하라.
//
package effectivejava.ch08.item50.exam02;

// [주제] 불변식을 깨드리지 못하도록 방어적 복사를 하는 예 - 매개변수 복사본 만들기

import java.util.Date;

final class Period {
  private final Date start;
  private final Date end;

  /**
   * @param start 시작일
   * @param end 종료일; 시작일 보다 뒤여야 한다.
   * @throws IllegalArgumentException 시작일이 종료일보다 늦을 때 예외 발생
   * @throws NullPointerException 시작일이나 종료일이 null일 때 예외 발생
   */
  public Period(Date start, Date end) {
    // 클라이언트로부터 가변 객체를 받았을 경우 복사하여 저장한다.
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());

    // 복사하는 중간에 다른 스레드에 의해 값이 바뀔 수 있으므로 복사한 후에 불변식 검사를 수행한다.
    // 즉 "검사시점(time-of-check)/사용시점(time-of-use) 공격" 줄여서 "TOCTOU 공격"을 방어해야 한다.
    if (this.start.compareTo(this.end) > 0) {
      throw new IllegalArgumentException(this.start + " after " + this.end);
    }
  }

  public Date start() {
    return start;
  }

  public Date end() {
    return end;
  }

  public String toString() {
    return start + " - " + end;
  }
}

public class Test {
  public static void main(String[] args) {
    Date start = new Date();
    Date end = new Date();
    Period p = new Period(start, end);
    System.out.println(p);

    // 클라이언트가 불변식을 깨는 시도를 하더라도 막을 수 있다.
    end.setYear(78); // 1978년으로 변경
    System.out.println(p);

    // [Date을 복사할 때 clone()을 사용하지 말라]
    // - Date은 final 이 아니다. 따라서 서브클래스가 clone()을 재정의할 수 있다.
    // - 클라이언트가 Date을 상속받아 악의적인 clone()을 구현할 수 있다.
    // - clone()이 재정의되면 방어적 복사가 무용지물이 된다.

    // [정리]
    // - 매개변수가 제3자에 의해 확장할 수 있는 타입이라면,
    //   방어적 복사본을 만들 때 clone()을 사용해서는 안된다!

    // [반환값을 이용한 공격]
    p.end().setYear(78); // 1978년으로 변경
    System.out.println(p);
    // 다음 예제에서 해결책 제시!
  }
}
