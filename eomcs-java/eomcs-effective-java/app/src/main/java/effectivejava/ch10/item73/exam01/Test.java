// # 아이템 73. 추상화 수준에 맞는 예외를 던져라
// - 메서드가 저수준 예외를 처리하지 않고 바깥으로 전파해버릴 때,
//   수행하려는 일과 관련 없어 보이는 예외가 튀어나와서 프로그래머를 당황스럽게 만든다.
//   당황을 넘어, 내부 구현 방식을 드러내어 윗 레벨 API를 오염시킨다.
// - 상위 계층에서는 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꿔 던져야 한다.
//   이를 "예외 번역(exception translation)"이라고 부른다.
// - 예외 번역과 함께 예외 연쇄를 이용하면,
//   상위 계층에는 맥락에 어울리는 고수준 예외를 던지면서, 근본 원인도 함께 알려주어 오류를 분석하기에 좋다.
//
package effectivejava.ch10.item73.exam01;

// [주제] 추상화 수준에 맞는 예외 던지기
//

import java.util.AbstractSequentialList;

public class Test {

  // 1) 예외 번역의 예
  // - AbstractSequentialList의 get 메서드를 구현한 코드를 확인해 보라.
  AbstractSequentialList list;

  // 예:
  //  public E get(int index) {
  //    try {
  //      return listIterator(index).next();
  //    } catch (NoSuchElementException exc) { <-- 저수준 예외
  //      throw new IndexOutOfBoundsException("Index: "+index); <-- 추상화 수준에 맞는 예외
  //    }
  //  }

  // 2) 예외 연쇄의 예
  // - 문제의 근본 원인인 저수준 예외를 고수준 예외에 실어 보내는 방식이다.
  static int divide(int x, int y) throws Exception {
    try {
      return x / y;
    } catch (ArithmeticException e) { // 저수준 예외
      throw new IllegalArgumentException(e); // 고수준 예외
    }
  }

  // 3) 예외 연쇄용 생성자
  // - 고수준 예외의 생성자는 예외 연쇄용으로 설계된 상위 생성자에 이 원인을 건네주어,
  //   최종적으로 Throwable(cause) 생성자까지 건네지게 한다.
  // - IllegalArgumentException 클래스의 생성자 코드를 확인해 보라.
  // 예:
  //  public IllegalArgumentException(Throwable cause) {
  //    super(cause);
  //  }

  public static void main(String[] args) throws Exception {
    // [정리]
    // - 대부분의 표준 예외는 예외 연쇄용 생성자를 갖추고 있다.
    // - Throwable의 initCause() 메서드를 이용해 예외 연쇄를 구현할 수도 있다.
    //   문제의 원일을 꺼낼 때는 getCause() 메서드를 사용한다.
    // - 무턱대고 예외를 전파하는 것보다야 예외 번역이 우수한 방법이지만,
    //   그렇다고 남용해서는 곤란하다.
    //   가능하면 저수준 메서드가 반드시 성공하도록 하여 아래 계층에서는 예외가 발생하지 않도록 하는 것이 좋다.
    //   어떻게?
    //   상위 계층 메서드의 파라미터 값을 하위 계층 메서드로 건네기 전에 미리 검사하는 방법으로 처리할 수 있다.
    //   또 다른 방법은?
    //   상위 계층에서 그 예외를 조용히 처리하여 문제를 API 호출자에게 전파하지 않는 것이다.
    //   단, 발생한 예외는 java.util.logging 같은 적절한 로깅 기능을 활용하여 기록해두면 좋다.
    //   그렇게 해두면 클라이언트 코드와 사용자에게 문제를 전파하지 않으면서도
    //   프로그래머가 로그를 분석해 추가 조치를 취할 수 있게 해준다.
    //
    //
  }
}
