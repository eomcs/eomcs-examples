// # 아이템 7. 다 쓴 객체 참조를 해제하라
// - 다 쓴 참조(obsoletre reference)를 해제하지 않으면 가비지 컬렉터가 객체를 회수하지 못한다.
//   "다 쓴 참조"란 더 이상 사용하지 않는 객체를 가리키는 참조를 말한다.
// - 다 쓴 참조를 살려두면, 그 객체가 참조하는 다른 객체들도 살아남게 된다.
// - 이것은 장기간 멈춤없이 실행하는 서버 애플리케이션에서 메모리 누수(memory leak)를 일으키는 원인이 된다.
// - 다 쓴 참조를 해제하는 방법은 참조를 null로 설정하는 것이다.

package effectivejava.ch02.item7.exam02;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

// HashMap과 WeakHashMap 비교
public class Test {

  public static void main(String[] args) throws Exception {
    Map<Object, String> hashMap = new HashMap<>();
    Map<Object, String> weakHashMap = new WeakHashMap<>();

    Object key1 = new Object();
    Object key2 = new Object();

    // HashMap:
    // - 키와 값 모두 강한 참조로 보관된다.
    // - 외부에서 키 객체를 더 이상 사용하지 않아도, 맵에 담겨 있으면 GC로 회수되지 않는다.
    hashMap.put(key1, "HashMap Value");

    // WeakHashMap:
    // - 키는 WeakReference로 감싼다.
    // - 외부에서 키를 참조하지 않으면, GC 수행할 때 자동으로 제거된다.
    // - 캐시, 메타데이터 저장 등에 적합하다.
    weakHashMap.put(key2, "WeakHashMap Value");

    System.out.println("Before GC:");
    System.out.println("HashMap: " + hashMap);
    System.out.println("WeakHashMap: " + weakHashMap);

    // 키를 더 이상 참조하지 않음
    key1 = null; // 외부 참조를 끊더라도, HashMap은 여전히 객체를 참조하고 있어 GC되지 않는다.
    key2 = null; // 외부 참조를 끊으면, WeakHashMap은 더 이상 객체를 참조하지 않아 GC된다.

    // GC 요청
    System.gc();

    // 잠깐 대기 (GC 동작 기회 부여)
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    System.out.println("\nAfter GC:");
    System.out.println("HashMap: " + hashMap);
    System.out.println("WeakHashMap: " + weakHashMap);

    // [결론]
    // - 캐시(cache)나 리스너/콜백 관리 기능을 구현할 때,
    //   - 다 쓴 참조에 대해 해당 value를 자동으로 제거할 수 있도록 WeakHashMap을 사용하자.
    //   - 단, WeakHashMap은 키가 약한 참조이므로,
    //     키 객체를 외부에서 강한 참조로 유지하지 않으면,
    //     맵에서 값이 사라질 수 있음을 유의하자.
    //   - 일반적인 용도로 key/value 쌍을 저장할 때는 HashMap을 사용해야 한다.
  }
}
