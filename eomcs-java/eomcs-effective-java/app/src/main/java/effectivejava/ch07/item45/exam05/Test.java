// # 아이템 45. 스트림은 주의해서 사용하라
// [핵심 개념]
// - 스트림(stream): 데이터 원소의 유한 혹은 무한 시퀀스를 뜻한다.
// - 스트림 파이프라인: 이 원소들로 수행하는 연산 단계를 표현하는 개념이다.
// - 스트림 원소 제공지: 컬렉션, 배열, 파일, 정규표현식 패턴 매처, 난수 생성기, 다른 스트림 등
// - 스트림 원소 타입: 객체 레퍼런스, 기본 타입(int, long, double)
//
// [스트림 파이프라인]
// - 소스 스트림에서 시작해 종단 연산으로 끝난다.
// - 중간 연산(intermediate operation)
//   - 시작과 종단 사이에 하나 이상의 있을 수 있다.
//   - 스트림을 어떠한 방식으로 변환(transform)한다.
//   - 각 원소에 함수를 적용하거나 특정 조건을 만족 못하는 원소를 걸러낼 수 있다.
// - 중간 연산의 결과물
//   - 변환된 원소 타입은 변환 전 원소 타입과 같을 수도 있고 다를 수도 있다.
// - 종단 연산(terminal operation)
//   - 마지막 중간 연산이 내놓은 스트림에 최후의 연산을 가한다.
//   - 원소를 정렬해 컬렉션에 담거나, 특정 원소 하나를 선택하거나, 모든 원소를 출력하는 식이다.
//   - 종단 연산이 없으면, 아무 일도 하지 않는다. 빼먹는 일이 절대 없도록 하자.
//
// [지연 평가(lazy evaluation)]
// - 스트림의 파이프라인은 지연 평가된다.
// - 종단 연산이 호출될 때 이뤄지며, 종단 연산에 쓰이지 않는 데이터 원소는 계산에 쓰이지 않는다.
// - 이러한 지연 평가가 무한 스트림을 다룰 수 있게 해주는 열쇠다.
//
// [스트림 API]
// - 스트림 API는 다량의 데이터 처리 작업을 돕는 API이다.
// - 메서드 연쇄를 지원하는 플루언트 API(fluent API)이다.
// - 파이프라인 하나를 구성하는 모든 호출을 연결하여 단 하나의 표현식으로 완성할 수 있다.
// - 파이프라인 여러 개를 연결해 표현식 하나로 만들 수도 있다.
//
// [병렬 스트림(parallel stream)]
// - 기본적으로 스트림 파이프라인은 순차적으로 수행된다.
// - 병렬로 실행하려면 파이프라인을 구성하는 스트림 중 하나에서 parallel() 메서드를 호출하면 된다.
//   그러나 효과를 볼 수 있는 상황이 많지 않다.
//
// 스트림을 제대로 사용하면 프로그램이 짧고 깔끔해지지만,
// 잘못 사용하면 읽기 어렵고 유지보수도 힘들어진다.

package effectivejava.ch07.item45.exam05;

// [주제] 사전 하나를 훑어 원소 수가 많은 아나그램 그룹들을 출력하기 - 적절하게 스트림 사용하기

import static java.util.stream.Collectors.groupingBy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Test {

  private static String alphabetize(String s) {
    char[] a = s.toCharArray();
    java.util.Arrays.sort(a);
    return new String(a);
  }

  public static void main(String[] args) {
    Path dictionary = Paths.get("words.txt");
    int minGroupSize = 4;

    try (Stream<String> words = Files.lines(dictionary)) {
      words
          .collect(groupingBy(word -> alphabetize(word))) // 단어들을 아나그램 그룹으로 묶는다.
          // 위 코드는 중간 연산은 없고 종단 연산에서는 모든 단어를 수집해 맵으로 모은다.
          .values()
          .stream()
          // 위 코드는 맵의 반환값을 가지고 스트림을 연다.
          .filter(group -> group.size() >= minGroupSize)
          .forEach(group -> System.out.println(group.size() + ": " + group));
      // 위 코드는 스트림에서 필터링을 수행하고, 종단 연산인 forEach는 살아남은 리스트를 출력한다.

    } catch (Exception e) {
      System.out.println("파일 오류: " + e);
      return;
    }

    // [주의]
    // - 람다에서는 타입 이름을 자주 생략하므로 파라미터 이름을 잘 지어야 스트림 파이프라인의 가독성이 유지된다.
    // - alphabetize()를 따로 뺀 것은 가독성을 높이기 위한 것이다.
    //   이렇게 세부 구현을 주 프로그램 로직 밖으로 빼내 메서드로 만든 후 적절한 이름을 지어주면
    //   코드의 가독성을 높여준다. 이런 메서드를 '도우미 메서드'라 한다.
    // - 도우미 메서드를 적절히 활용하는 것의 중요성은
    //   일반 반복 코드에서보다는 스트림 파이프라인에서 훨씬 크다.
    //   파이프라인에서는 타입 정보가 명시되지 않거나 임시 변수를 자주 사용하기 때문이다.
  }
}
