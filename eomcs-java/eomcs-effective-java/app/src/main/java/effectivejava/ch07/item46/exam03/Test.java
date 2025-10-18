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
package effectivejava.ch07.item46.exam03;

// [주제] 스트림 파이프라인 구축 예

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Test {
  public static void main(String[] args) throws Exception {
    // 단어 빈도 수 세기 - 가장 많이 등장한 단어 10개 출력
    File file = new File("word-count.txt");
    Map<String, Long> freq;
    try (Stream<String> words = new Scanner(file).tokens()) {
      freq =
          words.collect(
              groupingBy(
                  word -> word.toLowerCase(), // 분류 기준으로 사용할 key 생성 classifier 함수
                  counting()) // 같은 key를 가진 원소 수를 세는 downstream 수집기
              );
      List<String> topTen =
          freq.keySet().stream()
              // 다음과 같이 Collectors의 멤버를 static import 하면 가독성이 좋다.
              .sorted(comparing(freq::get).reversed())
              .limit(10)
              .collect(toList());
      System.out.println(topTen);
    }

    // [해설]
    // compareing():
    //   - Comparator를 생성하는 정적 팩터리 메서드다.
    //   - freq::get을 사용해 key에 해당하는 빈도수를 얻어와 비교한다.
    // reversed():
    //   - Comparator를 역순으로 바꾼다.
  }
}
