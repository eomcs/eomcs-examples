// # 아이템 46. 스트림에서는 부작용 없는 함수를 사용하라
// - 스트림은 그저 또 하나의 API가 아닌, 함수형 프로그래밍에 기초한 패러다임(paradigm)이다.
// - 스트림이 제공하는 표현력, 속도, 상황에 따라서는 병령성까지 끌어내려면
//   API는 말할 것도 없고 이 패러다임까지 함께 받아들여야 한다.
// [스트림 패러다임]
// - 계산을 일련의 변환으로 재구성하는 것.
//   각 변환 단계는 가능한 한 "이전 단계의 결과를 받아 처리하는 순수 함수"여야 한다.
// - "순수 함수"란 오직 입력만이 결과에 영향을 주는 함수를 말한다.
//   다른 가변 상태를 참조하지 않고, 함수 스스로도 다른 상태를 변경하지 않는다.
// - 이렇게 하려면 중간 단계든 종단 단계든 스트림 연산에 건네는 함수 객체는 모두 부작용이 없어야 한다.
//
package effectivejava.ch07.item46.exam02;

// [주제] 스트림 패러다임을 제대로 이해하고 API를 적절하게 사용한 사례

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Test {
  public static void main(String[] args) throws Exception {
    // 단어 빈도 수 세기
    File file = new File("word-count.txt");
    Map<String, Long> freq;
    try (Stream<String> words = new Scanner(file).tokens()) {
      freq =
          words.collect(
              groupingBy(
                  word -> word.toLowerCase(), // 분류 기준으로 사용할 key 생성 classifier 함수
                  counting()) // 같은 key를 가진 원소 수를 세는 downstream 수집기
              );
    }
    System.out.println(freq);

    // [정리]
    // - 위 코드는 수집기(collector)를 사용하고 있다.
    //   java.util.stream.Collectors 클래스에는 groupingBy() 같은 유용한 메서드를 40개 이상 제공한다.
    // - "축소(reduction) 전략"을 캡슐화한 블랙박스 객체이다.
    //   축소는 스트림 원소들을 객체 하나에 취합한다는 뜻이다.
    // - 수집기가 생성하는 객체는 일반적으로 컬렉션이며,
    //   그래서 "컬렉터(collector)"라는 이름을 쓴다.
    // - 수집기를 사용하면 스트림의 원소를 손쉽게 컬렉션으로 모을 수 있다.
    // - 수집기 종류
    //   - toList(): List 컬렉션을 반환
    //   - toSet(): Set 컬렉션을 반환
    //   - toCollection(collectionFactory): 프로그래머가 지정한 컬렉션 타입을 반환
  }
}
