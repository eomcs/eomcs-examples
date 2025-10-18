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
package effectivejava.ch07.item46.exam04;

// [주제] 맵 수집기 사용 예 - 간단한 toMap() 사용 예

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

class Score {
  private final String name;
  private final int kor;
  private final int eng;
  private final int math;

  public Score(String name, int kor, int eng, int math) {
    this.name = name;
    this.kor = kor;
    this.eng = eng;
    this.math = math;
  }

  public String name() {
    return name;
  }

  public int kor() {
    return kor;
  }

  public int eng() {
    return eng;
  }

  public int sum() {
    return kor + eng + math;
  }

  public float avg() {
    return sum() / 3.0f;
  }

  @Override
  public String toString() {
    return String.format("Score{name='%s', kor=%d, eng=%d, math=%d}", name, kor, eng, math);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 학생의 이름과 Score 객체를 맵으로 만들기
    Stream<Score> scores =
        Stream.of(
            new Score("Alice", 90, 85, 88),
            new Score("Bob", 78, 82, 80),
            new Score("Charlie", 95, 90, 92));

    // toMap(keyMapper, valueMapper):
    // - 스트림 원소에 대해 key 매핑 함수를 통해 키를 얻고,
    //   value 매핑 함수를 통해 값을 얻어 맵을 만든다.
    Map<String, Score> scoreMap =
        scores.collect(
            toMap(
                Score::name, // keyMapper: 이름을 키로 사용
                score -> score // valueMapper: Score 객체를 값으로 사용
                ));

    System.out.println(scoreMap);
  }
}
