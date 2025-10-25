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
package effectivejava.ch12.item85.exam01;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

// [주제] 역직렬화 폭탄 - 이 스트림의 역직렬화는 영원히 계속된다.
// - 역직렬화 폭탄(deserialization bomb)이란,
//   역직렬화에 시간이 오래 걸리는 짧은 스트림을 말한다.
//   이런 스트림은 역직렬화하는 것만으로도 서비스 거부 공격에 쉽게 노출될 수 있다.
//
public class Test {

  static byte[] bomb() {
    Set<Object> root = new HashSet<>();
    Set<Object> s1 = root;
    Set<Object> s2 = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      Set<Object> t1 = new HashSet<>();
      Set<Object> t2 = new HashSet<>();
      t1.add("foo");
      s1.add(t1);
      s1.add(t2);
      s2.add(t1);
      s2.add(t2);
      s1 = t1;
      s2 = t2;
    }
    return serialize(root);
  }

  static byte[] serialize(Object obj) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(obj); // 객체를 직렬화한다.
      oos.flush();
      return baos.toByteArray();
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }

  static Object deserialize(byte[] bytes) {
    try (java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(bytes);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais)) {
      return ois.readObject(); // 객체를 역직렬화한다.
    } catch (java.io.IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    // 직렬화하기
    long start = System.nanoTime();
    byte[] bytes = bomb();
    long end = System.nanoTime();
    System.out.println("Serialization time: " + (end - start) + " ns");
    System.out.println("Bomb size: " + bytes.length + " bytes");

    // 역직렬화하기
    start = System.nanoTime();
    Object obj = deserialize(bytes);
    end = System.nanoTime();
    System.out.println("Deserialization time: " + (end - start) + " ns");

    // [역직렬화가 끝나지 않는 이유]
    // - HashSet 인스턴스를 역직렬화하려면 그 원소들의 해시코드를 계산해야 한다.
    // - 루트 HashSet에는 2개의 HashSet을 원소를 갖고 있다.
    //   각각의 HashSet은 또 다른 2개의 HashSet을 원소로 갖는다.
    // - 반복문에 의해 그 구조가 100단계까지 만들어진다.
    // - 역직렬화 과정에서 해시코드를 계산하려면,
    //   HashSet이 포함하고 있는 원소를 따라 내려가면서 hashCode() 메서드를 호출해야 한다.
    //   HashSet
    //     -> HashSet
    //       -> HashSet
    //       -> HashSet
    //     -> HashSet
    //       -> HashSet
    //       -> HashSet
    //   root(1) -> 1단계(2) -> 2단계(4) -> 3단계(8) -> ... -> n단계(2^n)
    //   = 2^(n+1) - 1
    // - 100단계까지 내려가려면 2^101 - 1번의 hashCode() 호출이 필요하다.
    //   2^101은 약 2경(2,535,301,200,456,999)이다.
    //

    // [정리]
    // - 이 객체 그래프는 201개의 HashSet 인스턴스로 구성된다.
    // - 그 각각은 3개 이하의 객체 참조를 갖는다.
    // - 스트림의 전체 크기는 5744바이트지만, 역질력화는 영원히 끝나지 않을 것이다.
    //
  }
}
