// # 아이템 85. 자바 직렬화의 대안을 찾으라
// - 직렬화는 위험하니 피해야 한다.
// - 시스템을 밑바닥부터 설계한다면 JSON이나 프로토콜 버퍼 같은 대안을 사용하자.
// - 신뢰할 수 없는 데이터는 역직렬화를 하지 말자.
// - 꼭 해야 한다면 객체 역직렬화 필터링을 사용하되,
//   이 마저도 모든 공격을 막아낼 수 없음을 기억하자.
// - 클래스가 직렬화를 지원하도록 만들지 말고,
//   꼭 그렇게 만들어야 한다면 정말 신경써서 작성해야 한다.
//
// [직렬화의 위험성]
// - 공격 범위가 너무 넓고 지속적으로 더 넓어져 방어하기 어렵다.
// - ObjectInputStream의 readObject():
//   - Serializable 인터페이스를 구현했다면
//     CLASSPATH 안의 거의 모든 타입의 객체를 만들어 낼 수 있는, 사실상 마법 같은 생성자다.
//   - 바이트 스트림을 역질력화하는 과정에서 이 메서드는 그 타입들 안의 모든 코드를 수행할 수 있다.
//     즉 그 타입들의 코드 전체가 공격 범위에 들어간다는 뜻이다.
// - 자바의 표준 라이브러리나 아파치 커먼즈 컬렉션 같은 서드파티 라이브러리는 물론
//   애플리케이션 자신의 클래스들도 공격 범위에 포함된다.
// - 관련한 모든 모범 사례를 따르고 모든 직렬화 가능 클래스들을 공격에 대비하도록 작성한다 해도,
//   애플리케이션은 여전히 취약할 수 있다.
// - 신뢰할 수 없는 스트림을 역직렬화하면
//   원격 코드 실행(remote code execution, RCE),
//   서비스 거부(denial-of-service, DoS)
//   등의 공격으로 이어질 수 있다.
//
package effectivejava.ch12.item85.exam02;

// [주제] 역직렬화의 위험을 피하는 방법
// - 신뢰할 수 없는 바이트 스트림을 역직렬화하는 일 자체가 스스로를 공격에 노출하는 행위다.
// - 직렬화 위험을 회피하는 가장 좋은 방법은 아무것도 역직렬화하지 않는 것이다.
// - 새로운 시스템에서 자바 직렬화를 써야 할 이유는 전혀 없다.
// - 자바의 공식 보안 코딩 지침에서는,
//   "신뢰할 수 없는 데이터의 역직렬화는 본질적으로 위험하므로 절대로 피해야 한다"라고 조언한다.
// - JSON과 프로토콜 버퍼(Protocol Buffers, Protobuf) 같은 대안을 사용하자.
//   또는 역직렬화 필터링(java.io.ObjectInputFilter)를 사용하라.
//
// [JSON]
// - 더글라스 크록퍼드가 브라우저와 서버의 통신용으로 설계했다.
// - 자바스크립트용으로 만들어짐.
// - 텍스트 기반이라 사람이 읽을 수 있다.
// - 오직 데이터를 표현하는 데만 쓴다.
// [Protobuf]
// - 구글이 서버 사이에 데이터를 교환하고 저장하기 위해 설계했다.
// - C++용으로 만들어짐.
// - 바이너리 표현이라 효율이 훨씬 높다. 사람이 읽을 수 있는 텍스트 표현(pbtxt)도 제공한다.
// - 문서를 위한 스키마(타입)를 제공하고, 올바로 쓰도록 강요한다.
// [객체 역직렬화 필터링]
// - 자바 9에 도입된 기능이다.
// - 데이터 스트림이 역직렬화되기 전에 필터를 설치하는 기능이다.
// - 허용할 클래스와 거부할 클래스를 지정할 수 있다.
//   - '기본 수용' 모드에서는 블랙리스트에 기록된 잠재적으로 위험한 클래스들을 거부한다.
//   - '기본 거부' 모드에서는 화이트리스트에 기록된 안전하다고 알려진 클래스들만 수용한다.
// - 블랙리스트 방식보다는 화이트리스트 방식을 추천한다.
//   - 블랙리스트 방식은 이미 알려진 위험으로부터만 보호할 수 있기 때문이다.
// - 화이트리스트를 자동으로 생성해주는 도구:
//   - 스왓(SWAT, Serial Whitelist Application Trainer)
// - 필터링 기능은 메모리를 과하게 사용하거나 객체 그래프가 너무 깊어지는 사태로부터도 보호해준다.
//   하지만, 직렬화 폭탄은 걸러내지 못한다.
//

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
class Writer {
  private final String name;
  private final String email;
}

@Data
@Builder
@Jacksonized
class Press {
  private final String name;
  private final String address;
  private final String tel;
  private final String homepage;
}

@Data
@Builder
@Jacksonized
class Book {
  private final String title;
  private final int price;
  private final String isbn;
  private final int pages;
  private final Writer writer;
  private final Press press;
}

public class Test {
  public static void main(String[] args) {
    // JSON을 이용하여 직렬화/역직렬화하기
    Writer writer = new Writer("조엘 온 소프트웨어", "test@test.com");
    Press press = new Press("에이콘출판", "서울시 강남구", "02-1234-5678", "www.acornpub.co.kr");
    Book book = new Book("실용주의 프로그래머", 30000, "978-89-7914-662-1", 352, writer, press);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // JSON 직렬화
      String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(book);
      System.out.println("Serialized JSON:");
      System.out.println(jsonString);

      // JSON 역직렬화
      Book deserializedBook = objectMapper.readValue(jsonString, Book.class);
      System.out.println("\nDeserialized Book Object:");
      System.out.println(deserializedBook);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
