// # 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
// - 싱글턴(Singleton): 인스턴스를 오직 하나만 생성할 수 있는 클래스
// - 적용 예:
//   - 함수와 같은 무상태(stateless) 객체
//   - 설계상 유일해야 하는 시스템 컴포넌트

package effectivejava.ch02.item3.exam05;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// 방법3: 열거 타입 방식의 싱글턴(권장 방식)
enum Elvis {
  INSTANCE;

  public void leaveTheBuilding() {
    System.out.println("Whoa baby, I'm outta here!");
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Elvis elvis = Elvis.INSTANCE;

    // 직렬화(serialization)
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(elvis);
    }
    byte[] serializedElvis = baos.toByteArray();

    // 역직렬화(deserialization) 문제 해결
    Elvis deserializedElvis1, deserializedElvis2;

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedElvis))) {
      deserializedElvis1 = (Elvis) ois.readObject();
    }

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedElvis))) {
      deserializedElvis2 = (Elvis) ois.readObject();
    }

    System.out.println(elvis == deserializedElvis1); // true
    System.out.println(elvis == deserializedElvis2); // true
    System.out.println(deserializedElvis1 == deserializedElvis2); // true

    // [결론]
    // - public static final 필드 방식과 유사하지만 더 간결하다.
    // - 추가 노력 없이 직렬화 역직렬화 문제를 해결한다.
    // - 단, 열거 타입은 확장할 수 없다.
    //   Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.
  }
}
