// # 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
// - 싱글턴(Singleton): 인스턴스를 오직 하나만 생성할 수 있는 클래스
// - 적용 예:
//   - 함수와 같은 무상태(stateless) 객체
//   - 설계상 유일해야 하는 시스템 컴포넌트

package effectivejava.ch02.item3.exam04;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 방법1: public static final 필드 방식
// - 직렬화 기능 추가 + readResolve()
class Elvis implements Serializable {
  public static final Elvis INSTANCE = new Elvis();

  private Elvis() {}

  public void leaveTheBuilding() {
    System.out.println("Whoa baby, I'm outta here!");
  }

  private Object readResolve() {
    // 역직렬화할 때 자동 호출된다.
    // 이 메서드의 리턴 값이 역직렬화 결과로 리턴 된다.
    return INSTANCE;
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
      // readResolve() 메서드가 호출된다.
      deserializedElvis1 = (Elvis) ois.readObject();
    }

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedElvis))) {
      // readResolve() 메서드가 호출된다.
      deserializedElvis2 = (Elvis) ois.readObject();
    }

    System.out.println(elvis == deserializedElvis1); // true
    System.out.println(elvis == deserializedElvis2); // true
    System.out.println(deserializedElvis1 == deserializedElvis2); // true

    // [결론]
    // - 역직렬화 할 때 새로운 인스턴스 생성을 막으려면, readResolve() 메서드를 정의하라.
  }
}
