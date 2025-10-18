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
package effectivejava.ch07.item47.exam04;

// [주제] 스트림 반환하기

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// 입력 리스트의 연속 부분리스트를 스트림으로 반환
class SubLists {
  public static <E> Stream<List<E>> of(List<E> list) {
    // 1) Stream.of(Collections.emptyList()): 빈 리스트 스트림을 반환한다.
    //   []
    //   - return: Stream<List<E>>
    // 2) prefixes(list): 접두사 부분리스트들의 스트림을 반환한다.
    //   [[A], [A,B], [A,B,C]]
    //   - return: Stream<List<E>>
    // 3) suffixes(...): 각 접두사 리스트에 대한 접미사 부분리스트들의 스트림을 반환한다.
    //   [A] -> [[A]]
    //   [A,B] -> [[A,B], [B]]
    //   [A,B,C] -> [[A,B,C], [B,C], [C]]
    //   - return: Stream<Stream<List<E>>>
    // 4) flatMap(): 중첩된 스트림을 단일 스트림으로 평탄화한다.
    //   [[A], [A,B], [A,B,C], [B], [B,C], [C]]
    //   - return: Stream<List<E>>
    // 5) concat(): 두 개의 스트림을 하나로 연결한다.
    //   [[], [A], [A,B], [A,B,C], [B], [B,C], [C]]
    return Stream.concat(
        Stream.of(Collections.emptyList()), prefixes(list).flatMap(SubLists::suffixes));
  }

  // 접두사 부분리스트들의 스트림을 반환
  // 예: [A,B,C] → [A], [A,B], [A,B,C]
  private static <E> Stream<List<E>> prefixes(List<E> list) {
    // IntStream.rangeClosed(1, list.size()):
    //   1 ~ list.size()
    return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
  }

  // 접미사 부분리스트들의 스트림을 반환
  // 예: [A,B,C] → [A,B,C], [B,C], [C]
  private static <E> Stream<List<E>> suffixes(List<E> list) {
    // IntStream.range(0, list.size()):
    //   0 ~ (list.size() - 1)
    return IntStream.range(0, list.size()).mapToObj(start -> list.subList(start, list.size()));
  }

  private static <E> Stream<List<E>> suffixes2(List<E> list) {
    return Stream.of(list);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    List<String> list = Arrays.asList(new String[] {"A", "B", "C"});
    SubLists.of(list).forEach(System.out::println);

    // [정리]
    // - 원소 시퀀스를 리턴하는 메서드를 작성할 때,
    //   스트림으로 처리하길 원하는 사용자와 반복으로 처리하길 원하는 사용자 모두를 고려하라.
    //   컬렉션을 반환할 수 있다면 그렇게 하라.
    // - 리턴 전부터 이미 원소들을 컬렉션에 담아 관리하고 있거나,
    //   컬렉션을 하나 더 만들어도 될 정도로 원소 개수가 적다면
    //   ArrayList 같은 표준 컬렉션에 담아 리턴하라.
    //   또는 전용 컬렉션을 구현할지 고려하라.
    // - 컬렉션을 반환하는 게 불가능하다면,
    //   Stream과 Iterable 중 더 자연스러운 것을 리턴하라.
    // - Stream 인터페이스가 Iterable의 하위 타입이 된다면 그때는 Stream을 리턴하면 된다.
  }
}
