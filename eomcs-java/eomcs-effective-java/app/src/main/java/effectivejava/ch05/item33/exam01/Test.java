// # 아이템 33. 타입 안전 이종 컨테이너를 고려하라
// [일반적인 제네릭 컨테이너]
// - 일반적인 제네릭 컨테이너는 동일한 타입의 원소만 저장할 수 있다.
// - 컬렉션
//   - Set<E>: 원소의 타입을 뜻하는 한 개의 타입 파라미터를 갖는다.
//   - Map<K,V>: 키와 값의 타입을 뜻하는 두 개의 타입 파라미터를 갖는다.
// - 단일원소 컨테이너
//   - ThreadLocal<T>: 스레드별로 다른 값을 저장하는 컨테이너
//   - AtomicReference<T>: 원자적으로 참조를 교체하는 컨테이너
//
// [타입 안전 이종 컨테이너(heterogeneous container)]
// - 서로 다른 타입의 객체를 저장할 수 있는 컨테이너
// - Class<T> 객체를 키(key)로 사용해 값을 저장하고 검색하는 컨테이너
// - 이 패턴을 사용하면 컴파일 시점과 런타임에서 타입 안전성을 보장할 수 있다.

package effectivejava.ch05.item33.exam01;

// [주제] 일반적인 제네릭 컨테이너의 한계 확인

import java.util.HashMap;
import java.util.Map;

public class Test {

  public static void main(String[] args) throws Exception {
    // 1) 모든 원소가 같은 타입이다.
    Map<String, Integer> map = new HashMap<>();
    map.put("age", 25);
    map.put("height", 180); // 같은 타입(Integer)만 가능

    // 2) 서로 다른 타입을 저장하려면 Object 타입을 사용해야 한다.
    Map<String, Object> objMap = new HashMap<>();
    objMap.put("age", 25); // Integer
    objMap.put("name", "John"); // String
    objMap.put("admin", true); // Boolean

    // 이 방식은 타입 안전하지 않다.
    // - 컴파일 시점에 타입 검사를 할 수 없다.
    // - 타입 캐스팅을 잘못하면 런타임 오류가 발생한다.
    String age = (String) objMap.get("age"); // ClassCastException 런타임 오류 발생!
  }
}
