// # 아이템 50. 적시에 방어적 복사본을 만들라
// - 늘 클라이언트가 클래스의 불변식을 깨뜨리지 못하도록 방어적으로 프로그래밍해야 한다.
// - 클래스가 클라이언트로 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면
//   그 요소는 반드시 방어적으로 복사해야 한다.
// - 복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할 일이 없음을 신뢰한다면,
//   방어적 복사를 수행하는 대신에
//   해당 구성요소를 수정했을 때의 책임이 클라이언트에게 있음을 문서에 명시하라.
//
package effectivejava.ch08.item50.exam03;

// [주제] 불변식을 깨드리지 못하도록 방어적 복사를 하는 예 II - 반환값 복사본 만들기

import java.util.Date;

final class Period {
  private final Date start;
  private final Date end;

  public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());

    if (this.start.compareTo(this.end) > 0) {
      throw new IllegalArgumentException(this.start + " after " + this.end);
    }
  }

  public Date start() {
    // 클라이언트가 가변 필드의 값을 바꾸지 못하도록 복사본을 반환한다.
    //    return new Date(start.getTime()); // OK!

    // 다음과 같이 clone()으로 복사본을 반환해도 된다.
    // Period가 가지고 있는 Date 클래스는 java.util.Date 클래스임이 확실하다.
    // 그래도 가능한 생성자나 정적 팩토리를 사용하여 인스턴스를 복사하는 것이 좋다.
    return (Date) start.clone(); // OK!
  }

  public Date end() {
    // 클라이언트가 가변 필드의 값을 바꾸지 못하도록 복사본을 반환한다.
    return new Date(end.getTime());

    // 다음과 같이 clone()으로 복사본을 반환해도 된다.
    // Period가 가지고 있는 Date 클래스는 java.util.Date 클래스임이 확실하다.
    // 그래도 가능한 생성자나 정적 팩토리를 사용하여 인스턴스를 복사하는 것이 좋다.
    //    return (Date) end.clone(); // OK!
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

    // 클라이언트가 반환값을 이용하여 불변식을 깨는 시도를 하더라도 막을 수 있다.
    p.end().setYear(78); // 1978년으로 변경
    System.out.println(p);

    // [정리]
    // - 메서드든 생성자든 클라이언트가 제공한 객체의 참조를 내부의 자료구조에 보관해야 할 때는
    //   항시 그 객체가 잠재적으로 변경될 수 있는지를 생각해야 한다.
    // - 변경될 수 있는 객체라면 그 객체가 클래스에 넘겨진 뒤 임의로 변경되어도
    //   그 클래스가 문제없이 동작할지를 따져보라.
    // - 가변인 내부 객체를 클라이언트에 반환할 때도 마찬가지다.
    //   안심할 수 없다면 방어적 복사본을 반환해야 한다.
    //   길이가 1 이상인 배열은 무조건 가변이다. 따라서 내부에서 사용하는 배열을 반환할 때는
    //   항상 방어적 복사를 수행해야 한다.
    //
    // "되도록 불변 객체들을 조합해 객체를 구성해야 방어적 복사를 할 일이 줄어든다!"
    //
  }
}
