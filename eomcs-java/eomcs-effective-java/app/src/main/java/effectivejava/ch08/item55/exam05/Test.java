// # 아이템 55. 옵셔널 반환은 신중히 하라
// - 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬하는 경우,
//   옵셔널을 반환하는 것이 유용할 수 있다.
// - 옵셔널 반환에는 성능 저하가 뛰따르니,
//   성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다.
// - 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
//

package effectivejava.ch08.item55.exam05;

// [주제] Optional<T> 리턴 값 다루기 II - 옵셔널 스트림

import java.util.Optional;
import java.util.stream.Stream;

public class Test {

  static Stream<Optional<String>> streamOfOptionals() {
    return Stream.of(Optional.empty(), Optional.of("자바"), Optional.empty(), Optional.of("코틀린"));
  }

  public static void main(String[] args) {
    // Stream<Optional<String>> -> Stream<String> 변환하여 값을 출력하기

    // 방법 1: filter + map
    streamOfOptionals() // Stream<Optional<String>> 리턴
        .filter(Optional::isPresent) // 값을 가진 옵셔널만 필터링
        .map(Optional::get) // 옵셔널에서 값 추출: Stream<String>
        .forEach(System.out::println);
    System.out.println("-----------------------------");

    // 방법 2: Optional --> Stream 변환 + flatMap
    streamOfOptionals().flatMap(Optional::stream).forEach(System.out::println);
  }
}
