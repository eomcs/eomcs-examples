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
package effectivejava.ch07.item47.exam01;

// [주제] for-each 루프에 Stream 사용하기: 문제점 확인 및 해결하기

import java.util.List;
import java.util.stream.Stream;

public class Test {
  public static void main(String[] args) throws Exception {
    List<String> names = List.of("Alice", "Bob", "Charlie");

    Stream<String> namesStream = names.stream();

    // [문제점 확인]
    // - 컴파일 오류: for-each 루프는 Iterable이나 배열에서만 동작한다.
    //    for (String name : namesStream) {
    //      System.out.println(name);
    //    }

    // [해결책]
    // 방법 1: 익명 클래스로 Iterable 객체 만들기
    //    Iterable<String> namesIterable =
    //        new Iterable<String>() {
    //          @Override
    //          public Iterator<String> iterator() {
    //            return namesStream.iterator();
    //          }
    //        };

    // 방법 2: 람다 표현식으로 Iterable 객체 만들기
    //    Iterable<String> namesIterable = () -> namesStream.iterator();

    // 방법 3: 메서드 레퍼런스로 Iterable 객체 만들기
    Iterable<String> namesIterable = namesStream::iterator;

    for (String name : namesIterable) {
      System.out.println(name);
    }
  }
}
