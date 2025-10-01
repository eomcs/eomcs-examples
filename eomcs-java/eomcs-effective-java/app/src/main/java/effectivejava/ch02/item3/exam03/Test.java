// # 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
// - 싱글턴(Singleton): 인스턴스를 오직 하나만 생성할 수 있는 클래스
// - 적용 예:
//   - 함수와 같은 무상태(stateless) 객체
//   - 설계상 유일해야 하는 시스템 컴포넌트

package effectivejava.ch02.item3.exam03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 방법1: public static final 필드 방식
// - 직렬화 기능 추가
class Elvis implements Serializable {
  public static final Elvis INSTANCE = new Elvis();

  private Elvis() {}

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

    // 역직렬화(deserialization)
    Elvis deserializedElvis1, deserializedElvis2;

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedElvis))) {
      deserializedElvis1 = (Elvis) ois.readObject();
    }

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedElvis))) {
      deserializedElvis2 = (Elvis) ois.readObject();
    }

    System.out.println(elvis == deserializedElvis1); // false
    System.out.println(elvis == deserializedElvis2); // false
    System.out.println(deserializedElvis1 == deserializedElvis2); // false

    // [문제점]
    // - 직렬화 할 때마다 새 인스턴스가 만들어진다.
  }
}
