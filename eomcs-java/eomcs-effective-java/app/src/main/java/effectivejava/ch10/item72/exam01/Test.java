// # 아이템 72. 표준 예외를 사용하라
// - 숙련된 프로그래머는 그렇지 못한 프로그래머보다 더 많은 코드를 재사용한다.
// - 예외 또한 똑같다. 표준 예외를 재사용하면 얻는 게 많다.
//   프로그래머가 만든 API를 다른 사람이 익히고 사용하기 쉬워진다.
//   많은 프로그래머에게 이미 익숙해진 규약을 그대로 따르기 때문이다.
//   낯선 예외를 사용하지 않게 되어 코드를 읽기 쉽다.
// - 예외 클래스가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.
// - Exception, RuntimeException, Throwable, Error 같은 최상위 예외 클래스는 재사용하지 말라.
//   이 예외들은 다른 예외들의 상위 클래스이므로, 즉 여러 성격의 예외들을 포괄하는 클래스이므로
//   안정적으로 테스트할 수 없다.
//
// [표준 예외 재사용]
// - 상황에 부합하다면 항상 표준 예외를 재사용하라!
//   이때 API 문서를 참고해 그 예외가 어떤 상황에서 던져지는지 꼭 확인하라.
// - 예외의 이름뿐 아니라 예외가 던져지는 맥락도 부합할 때만 재사용하라.
// - 더 많은 정보를 제공하길 원한다면, 표준 예외를 확장해도 좋다.
//   단, 예외는 직렬화할 수 있다는 사실을 기억하라.
//   직렬화는 부담이 따른다. 가능한 예외를 새로 많들지 않는 것이 좋다.
//
package effectivejava.ch10.item72.exam01;

// [주제] 재사용되는 주요 예외
// 1) IllegalArgumentException
//    - 호출자가 허용하지 않은 값을 아규먼트로 넘길 때
//      예) 반복 횟수를 음수로 넘김
// 2) IllegalStateException
//    - 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때
//      예) 제대로 초기화되지 않은 객체를 사용
// 3) NullPointerException
//    - null 값을 허용하지 않는 메서드에 null을 넘길 때
// 4) IndexOutOfBoundsException
//    - 어떤 시퀀스의 허용 범위를 넘는 값을 넘길 때
// 5) ConcurrentModificationException
//    - 단일 스레드에서 사용하려고 설계한 객체를 여러 스레드가 동시에 수정하려고 할 때
//      외부 동기화 방식으로 사용하려고 설계한 객체도 마찬가지다.
//    - 동시 수정을 확실히 검출할 수 있는 안정된 방법은 없으니, 단지 문제가 생길 가능성을 알려주는 역할을 한다.
// 6) UnsupportedOperationException
//    - 클라이언트가 요청한 동작을 대상 객체가 지원하지 않을 때
//    - 보통 구현하려는 인터페이스의 메서드 일부를 구현할 수 없을 때 사용한다.
//      예) 원소 추가만 가능한 List 구현체에 remove 메서드를 호출
//
// [참고] IllegalArgumentException vs IllegalStateException
// - 아규먼트 값이 무엇이었든 어차피 실해했을 거라면, IllegalStateException을 던져라.
// - 그렇지 않다면 IllegalArgumentException을 던져라.
//
//

public class Test {

  public static void main(String[] args) throws Exception {}
}
