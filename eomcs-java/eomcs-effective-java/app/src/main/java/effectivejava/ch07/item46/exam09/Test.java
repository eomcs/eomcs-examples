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
package effectivejava.ch07.item46.exam09;

// [주제] 분류 수집기 사용 예 - groupingBy() 사용 예 III

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Message {
  private final int no;
  private final String writer;
  private final String message;

  public Message(int no, String writer, String message) {
    this.no = no;
    this.writer = writer;
    this.message = message;
  }

  public int no() {
    return no;
  }

  public String message() {
    return message;
  }

  public String writer() {
    return writer;
  }

  @Override
  public String toString() {
    return String.format("Message{no=%d, writer='%s', message='%s'}", no, writer, message);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 작성자의 글을 카운트한 맵 만들기
    List<Message> messages =
        List.of(
            new Message(1, "Alice", "Hello!"),
            new Message(2, "Bob", "Hi!"),
            new Message(3, "Alice", "How are you?"),
            new Message(4, "Bob", "I'm fine, thanks."),
            new Message(5, "Charlie", "Good morning!"),
            new Message(6, "Alice", "See you later."));

    // groupingBy(classifier, mapFactory, downstream)
    // - 파라미터:
    //   분류 함수(classifier function): 작성자 이름으로 분류
    //   맵 팩토리(map factory): 생략 가능(기본 HashMap 생성)
    //   다운스트림(downstream): 분류된 요소들에 대해 추가로 수행할 수집기. 메시지 개수 세기.
    // - 리턴 값: 분류 함수의 결과를 key로, 메시지 개수를 value 로 하는 맵
    Map<String, Long> messagesOfWriter =
        messages.stream().collect(groupingBy(Message::writer, TreeMap::new, counting()));
    System.out.println(messagesOfWriter.getClass().getName()); // TreeMap

    // 결과 출력
    messagesOfWriter.forEach(
        (writer, count) -> {
          System.out.println(writer + ": " + count);
        });

    // [정리]
    // -------------------------------------------------------------------
    // [Collectors의 '바깥 전용 수집기' 생성 메서드]
    // collect() 메서드에 직접 전달되는 "최상위 수집기"를 의미한다.
    // 1) 컨테이너 형 수집기
    //   - toList(): 스트림 요소를 리스트로 수집
    //   - toSet(): 스트림 요소를 세트로 수집
    //   - toMap(): 스트림 요소를 맵으로 수집
    // 2) 분류 및 그룹화
    //   - groupingBy(): 스트림 요소를 분류하여 맵으로 수집
    //   - partitioningBy(): 스트림 요소를 프레디케이트(조건; true/false)로 분할하여 맵으로 수집
    // 3) 커스텀 수집기
    //   - toCollection(): 스트림 요소를 커스텀 컬렉션으로 수집
    //     다운스트림 수집기로도 사용된다.
    //
    // [Collectors의 '다운스트림 전용 수집기' 생성 메서드]
    // 다른 수집기의 두 번째 인자로 전달되어 "그룹별로", "파티션별로", "분류 결과별로"
    // 하위 집계를 수행하는 보조 수집기를 의미한다.
    // 1) 변환(transform) 관련 수집기:
    //   - 다운스트림으로 넘기기 전 변형
    //   - filtering(): 다운스트림 수집기 전에 필터 조건 적용
    //   - mapping(): 다운스트림 수집기 앞단에서 변환 수행
    //   - flatMapping(): mapper가 반환한 스트림을 평탄화 후 다운스트림 수집기로 전달
    //
    // [ Collectors의 '바깥/다운스트림 공용 수집기' 생성 메서드]
    // '바깥 수집기' 및 '다운스트림 수집기' 양쪽 모두에 사용될 수 있는 수집기를 의미한다.
    // 1) 집계(aggregation) 관련 수집기:
    //   - 수, 합, 평균, 요약 통계
    //   - counting(): 요소 개수 세기
    //   - summingInt()/summingLong()/summingDouble(): 합계 계산
    //   - averagingInt()/averagingLong()/averagingDouble(): 평균 계산
    // 2) 통계(statistics) 관련 수집기:
    //   - 통계 요약(count, sum, avg, min, max 모두 포함)
    //   - summarizingInt()/summarizingLong()/summarizingDouble():
    // 3) 최대/최소 관련 수집기:
    //   - maxBy()/minBy(): 최대값/최소값 찾기
    // 4) 문자열 조합
    //   - joining(): 스트림 요소(문자열)를 하나의 문자열로 결합
    // 5) 축약(reduction) 관련 수집기:
    //   - 하나의 값으로 결합하거나 후처리
    //   - reducing(...): 그룹 내 모든 요소를 하나의 값으로 결합(reduce)
    //   - collectingAndThen(): 다운스트림 수집기 결과에 후처리 함수 적용
    //   - teeing(): 두 개의 다운스트림 수집기를 병렬로 실행하고 결과를 합침
    // 6) 커스텀 수집기
    //   - toCollection(): 스트림 요소를 커스텀 컬렉션으로 수집
    //     주로 바깥 수집기로 사용된다.

  }
}
