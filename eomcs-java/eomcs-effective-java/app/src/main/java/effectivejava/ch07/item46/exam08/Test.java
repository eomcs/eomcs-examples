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
package effectivejava.ch07.item46.exam08;

// [주제] 분류 수집기 사용 예 - groupingBy() 사용 예 II

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

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

    // groupingBy(classifier, downstream)
    // - 파라미터:
    //   분류 함수(classifier function): 작성자 이름으로 분류
    //   다운스트림(downstream): 분류된 요소들에 대해 추가로 수행할 수집기. 메시지 개수 세기.
    // - 리턴 값: 분류 함수의 결과를 key로, 메시지 개수를 value 로 하는 맵
    Map<String, Long> messagesOfWriter =
        messages.stream().collect(groupingBy(Message::writer, counting()));
    System.out.println(messagesOfWriter.getClass().getName()); // HashMap

    // 결과 출력
    messagesOfWriter.forEach(
        (writer, count) -> {
          System.out.println(writer + ": " + count);
        });
  }
}
