// # 아이템 86. Serializable을 구현할지는 신중히 결정하라
// - Serializable은 구현한다고 선언하기는 아주 쉽지만, 그것은 눈속임일 뿐이다.
// - 한 클래스의 여러 버전이 상호작용할 일이 없고,
//   서버가 신뢰할 수 없는 데이터에 노출될 가능이 없는 등,
//   보호된 환경에서만 쓰일 클래스가 아니라면,
//   Serializable 구현은 아주 신중하게 이뤄져야 한다.
// - 상속할 수 있는 클래스라면 주의사항이 더욱 많아진다.
//
package effectivejava.ch12.item86.exam02;

// [주제] Serializable 구현의 문제점
// 1) 버그와 보안 구멍이 생길 위험이 높아진다.
//    - 객체는 생성자를 사용해 만드는 것이 기본이다.
//    - 직렬화는 언어의 기본 메커니즘을 우회해 객체 생성 기법이다.
//      역직렬화는 일반 생성자의 문제가 그대로 적용되는 '숨은 생성자'다.
//      즉 기본 역직렬화를 사용하면 불변식 깨짐과 허가되지 않은 접근에 쉽게 노출된다는 뜻이다.
// 2) 해당 클래스의 신버전을 릴리스할 때 테스트할 것이 늘어난다.
//    - 직렬화 가능 클래스가 수정되면 신버전 인스턴스를 직렬화한 후 구버전으로 역직렬화할 수 있는지,
//      그 반대도 가능한지를 검사해야 한다.
//      테스트 해야 할 양이 직렬화 가능 클래스의 수와 릴리스 횟수에 비례해 증가한다.
//
// [Serializable 구현 시 주의사항]
// - 클래스를 설계할 때 Serializable 구현 여부를 신중히 결정하라.
//   이득과 비용을 잘 저울질해야 한다.
//   역사적으로 값 클래스(BigInteger 등)와 컬렉션 클래스들은 Serializable을 구현해왔다.
//   스레드 풀처럼 '동작'하는 객체를 표현하는 클래스들은 Serializable을 구현하지 않았다.
// - 상속용으로 설계된 클래스는 대부분 Serializable을 구현하지 말라.
//   인터페이스도 대부분 Serializable을 확장하지 말라.
// - 상속용으로 설계된 클래스 중에서 부득이하게 Serializable을 구현한 경우,
//   Throwable: 서버가 RMI를 통해 클라이언트로 예외를 보내기 위해
//   Component: GUI를 전송하고 저장하고 복원하기 위해
// - 인스턴스 필드 값 중 불변식을 보장해야 할 게 있다면,
//   반드시 하위 클래스에서 finalize() 재정의 하지 못하도록
//   자신이 재정의 하면서 final로 선언하라.
//   이렇게 해두지 않으면 finalizer 공격을 당할 수 있다.
// - 인스턴스 필드 중 기본값(정수형은 0, 불리언은 false, 참조형은 null)으로
//   초기화되면 위배되는 불변식이 있다면 클래스에 readObject() 메서드를 추가하라.
//   상위 클래스에서 위배되는 불변식이 있다면, readObjectNoData() 메서드를 추가하라.

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DeserializeTest {

  public static void main(String[] args) {
    // 역직렬화하기
    // 테스트1: 직렬화한 클래스와 동일한 Person 클래스 사용하여 역직렬화 시도
    // 테스트2: Person 클래스에 새 필드 추가한 후 역직렬화 시도 (OK)
    // 테스트3: Person 클래스에 readObject 메서드 추가한 후 역직렬화 시도 (예외 발생!)
    try (ObjectInputStream out = new ObjectInputStream(new FileInputStream("person.data"))) {
      Person person = (Person) out.readObject();
      System.out.println("Deserialized Person: " + person);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Person object serialized successfully!");

    // [참고]
    // - 내부 클래스는 직렬화를 구현하지 말아야 한다.
    //   내부 클래스에는 바깥 인스턴스의 참조와 유효 범위 안의 지역 변수 값들을 저장하기 위해
    //   컴파일러가 생성하 필드들이 자동으로 추가된다.
    //   이 필드들이 클래스 정의에 어떻게 추가되는지도 명세서에 정의되어 있지 않다.
    //   내부 클래스에 대한 기본 직렬화 형태는 분명하지가 않다.
    // - 단 정적 멤버 클래스는 Serializable을 구현해도 된다.
  }
}
