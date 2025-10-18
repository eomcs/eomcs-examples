// # 아이템 47. 반환 타입으로 스트림보다 컬렉션이 낫다
// [원소 시퀀스; 일련의 원소를 반환하는 메서드]
// - 목록을 다룰 때 기존 방식:
//   1) 기본: 컬렉션 인터페이스 사용
//   2) Collection 메서드를 구현할 수 없을 때: Iterable 사용
//   3) 기본 타입이거나 성능에 민감한 상황일 때: 배열 사용.
// [스트림 등장 후]
// - for-each 루프에 직접사용할 수 없는 불편함이 있다.
//   왜? Stream은 Iterable의 하위 타입이 아니기 때문이다.
// - 해결책? 어댑터 메서드를 만들어 Iterable 객체로 변환한 후 사용하면 된다.
//
package effectivejava.ch07.item47.exam02;

// [주제] for-each 루프에 Stream 사용하기: 어댑터 메서드 활용

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Test {
  public static void main(String[] args) throws Exception {
    List<String> names = List.of("Alice", "Bob", "Charlie");

    Stream<String> namesStream = names.stream();

    // [문제점] 메서드 레퍼런스를 for-each 루프에 직접 적용할 수 없다.
    // - 컴파일 오류 발생!
    //    for (String name : namesStream::iterator) {
    //      System.out.println(name);
    //    }

    // [해결책]
    // 방법 1: 메서드 레퍼런스를 캐스팅하여 Iterable 객체로 만든다.
    // - 컴파일러는 type casting을 통해
    //   메서드 레퍼런스가 Iterable 인터페이스의 추상 메서드인 iterator()를 구현한다고 인식한다.
    // - 즉 컴파일러는 내부적으로 다음과 같은 람다 객체를 만들어 사용한다.
    //     Iterable<String> namesIterable = () -> namesStream.iterator();
    //
    //    for (String name : ((Iterable<String>) namesStream::iterator)) {
    //      System.out.println(name);
    //    }
    // - 작동은 하지만, 코드가 너무 난잡해지고 직관성이 떨어진다.

    // 방법 2: 어댑터 기능을 하는 메서드를 만들어 사용한다.
    for (String name : iterableOf(namesStream)) {
      System.out.println(name);
    }
  }

  // Stream --> Iterable
  public static <T> Iterable<T> iterableOf(Stream<T> stream) {
    return stream::iterator;
  }

  // Iterable --> Stream
  public static <T> Stream<T> streamOf(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
