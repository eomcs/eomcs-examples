// # 아이템 86. Serializable을 구현할지는 신중히 결정하라
// - Serializable은 구현한다고 선언하기는 아주 쉽지만, 그것은 눈속임일 뿐이다.
// - 한 클래스의 여러 버전이 상호작용할 일이 없고,
//   서버가 신뢰할 수 없는 데이터에 노출될 가능이 없는 등,
//   보호된 환경에서만 쓰일 클래스가 아니라면,
//   Serializable 구현은 아주 신중하게 이뤄져야 한다.
// - 상속할 수 있는 클래스라면 주의사항이 더욱 많아진다.
//
package effectivejava.ch12.item86.exam01;

// [주제] Serializable을 구현하면 릴리스한 뒤에는 수정하기 어렵다.
// - 직렬화된 바이트 스트림 인코딩도 하나의 공개 API가 된다.
//   이 클래스가 널리 퍼진다면 그 직렬화 형태로 영원히 지원해야 한다.
// - 기본 직렬화 형태에서는 클래스의 private과 package-private 인스턴스 필드들마저
//   API로 공개하는 꼴이 된다.
//   즉 캡슐화가 깨진다.
//   필드로의 접근을 최대한 막아 정보를 은닉하라는 조언도 무력화된다.
// - 직렬화를 수행한 후 클래스 내부 구현을 손보면 원래의 직렬화 형태와 달라지게 된다.
//   한쪽은 구버전 인스턴스를 직렬화하고 다른 쪽은 신버전 인스턴스를 역직렬화하려 할 때
//   문제가 발생할 수 있다.

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SerializeTest {

  public static void main(String[] args) {
    // 직렬화하기
    Person person = new Person("John Doe", 30);
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.data"))) {
      out.writeObject(person);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Person object serialized successfully!");
  }
}
