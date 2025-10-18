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
package effectivejava.ch07.item46.exam01;

// [주제] 스트림 패러다임을 이해하지 못한 채 API만 사용한 사례

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Test {
  public static void main(String[] args) throws Exception {
    // 단어 빈도 수 세기
    // - 다음은 스트림을 가장한 반복적 코드다.
    // - 스트림 API의 이점을 살리지 못하여 같은 기능의 반복적 코드보다 조금 더 길고, 읽기 어렵고, 유지보수에 좋지 않다.
    File file = new File("word-count.txt");
    Map<String, Long> freq = new HashMap<>();
    try (Stream<String> words = new Scanner(file).tokens()) {
      words.forEach(
          word -> {
            // 람다에서 외부 freq 맵의 상태를 변경하고 있다. 잘못된 사용법!
            freq.merge(word.toLowerCase(), 1L, Long::sum);
          });
    }
    System.out.println(freq);

    // [정리]
    // - forEach() 연산은 종단 연산 중 기능이 가장 적고 가장 '덜' 스트림답다.
    // - 병렬화할 수도 없다.
    // - forEach() 연산은 스트림 계산 결과를 보고할 때만 사용하고, 계산하는 데는 사용하지 말라.
    //
  }
}
