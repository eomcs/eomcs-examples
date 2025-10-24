// # 아이템 81. wait와 notify보다는 동시성 유틸리티를 애용하라
// - wait와 notify는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용하라.
//   wait와 notify를 직접 사용하는 것을 동시성 '어셈블리 언어'로 프로그래밍하는 것에 비유할 수 있다.
//   반면, java.util.concurrent 패키지는 고수준 언어에 비유할 수 있다.
// - 코드를 새로 작성한다면 wait와 notify를 쓸 이유가 거의 없다.
//   이들을 사용하는 레거시 코드를 유지보수해야 한다면,
//   wait는 항상 표준 관용구에 따라 while문 안에서 호출하도록 하라.
//   일반적으로 notify보다는 notifyAll을 사용해야 한다.
//   혹시라도 notify를 사용한다면 응답 불가 상태에 빠지지 않도록 각별히 주의하라.
//
// [java.util.concurrent 패키지의 동시성 유틸리티들]
// 1) 실행자 프레임워크
// 2) 동시성 컬렉션(concurrent collection)
// 3) 동기화 장치(synchronizer)

package effectivejava.ch11.item81.exam01;

// [주제] 동시성 컬렉션 - ConcurrentMap 사용법
// - List, Queue, Map 같은 표준 컬렉션 인터페이스에 동시성을 가미해 구현한 고성능 컬렉션이다.
// - 높은 동시성에 도달하기 위해 동기화를 각자의 내부에서 수행한다.
// - 동시성 컬렉션에서 동시성을 무력화하는 건 불가능하다.
//   - 외부에서 락을 추가로 사용하면 오히려 속도가 느려진다.
//   - 여러 메서드를 원자적으로 묶어 호출하는 일 역시 불가능하다.
//     대신 여러 기본 동작을 하나의 원자적 동작으로 묶는 '상태 의존적 수정' 메서드들이 추가되었다.
// - 상태 의존적 메서드:
//   예) putIfAbsent(key, value):
//      'key가 없을 때만 value를 넣는다'라는 동작을 원자적으로 수행한다.
//      기존 값이 있었다면 그 값을 반환하고, 없었다면 null을 반환한다.
//

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Test {

  private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

  // String.intern()과 동일한 기능을 하는 메서드
  public static String intern(String s) {
    String previousValue = map.putIfAbsent(s, s);
    return previousValue == null ? s : previousValue;
  }

  // 위의 intern()을 개선한 메서드
  public static String intern2(String s) {
    String result = map.get(s); // get()으로 검색하는 것이 더 빠르다.
    // - get()은 비차단(non-blocking) 읽기로 매우 빠르다.

    if (result == null) {
      result = map.putIfAbsent(s, s); // 필요할 때만 putIfAbsent() 호출하는 것이 더 빠르다.
      if (result == null) {
        result = s;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    // [정리]
    // - ConcurrentHashMap의 원자 연산(putIfAbsent, computeIfAbsent)으로
    //   '검사 후 동작'을 락 없이 안전하게 구현할 수 있다.
    // - get()은 비차단 읽기로 매우 빠르므로, 먼저 get()으로 조회 후
    //   필요할 때만 putIfAbsent()를 호출하면 경합을 줄이고 성능을 높일 수 있다.
    // - wait/notify로 직접 동기화 코드를 짜는 대신,
    //   고수준 동시성 유틸리티를 활용하면 단순하고 안전하며 확장성이 좋다.
    // - Collections.synchronizedMap 같은 동기화 컬렉션보다는
    //   ConcurrentHashMap 같은 동시성 컬렉션을 사용하라.
    //   동시성 애플리케이션의 성능이 극적으로 개선될 것이다.

  }
}
