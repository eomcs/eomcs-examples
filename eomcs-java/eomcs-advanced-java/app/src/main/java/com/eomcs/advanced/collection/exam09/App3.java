package com.eomcs.advanced.collection.exam09;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;
import java.util.TreeMap;

// SequencedMap<K,V> (Java 21):
// - java.util 패키지에 소속되어 있다.
// - Map<K,V>를 확장한 인터페이스이다.
// - 항목(entry)에 명확한 순서가 있는 Map을 표현한다.
// - 첫 번째·마지막 항목에 직접 접근하고, 역순 뷰와 순서가 있는 뷰를 얻는 메서드를 추가한다.
//
// SequencedMap 주요 메서드:
//   Map.Entry<K,V> firstEntry()      첫 번째 항목 반환 (없으면 null)
//   Map.Entry<K,V> lastEntry()       마지막 항목 반환 (없으면 null)
//   Map.Entry<K,V> pollFirstEntry()  첫 번째 항목 제거 후 반환 (없으면 null)
//   Map.Entry<K,V> pollLastEntry()   마지막 항목 제거 후 반환 (없으면 null)
//   void putFirst(K k, V v)          맨 앞에 항목 추가
//   void putLast(K k, V v)           맨 뒤에 항목 추가
//   SequencedMap<K,V> reversed()     역순 뷰 반환
//   SequencedSet<K> sequencedKeySet()         순서 있는 키 Set 반환
//   SequencedCollection<V> sequencedValues()  순서 있는 값 컬렉션 반환
//   SequencedSet<Map.Entry<K,V>> sequencedEntrySet() 순서 있는 Entry Set 반환
//
// SequencedMap을 구현하는 주요 클래스:
//   LinkedHashMap : 추가 순서 유지
//   TreeMap       : 키 기준 정렬 순서 유지
//

public class App3 {

  public static void main(String[] args) {

    // 1. LinkedHashMap - SequencedMap 메서드
    System.out.println("[LinkedHashMap - SequencedMap 메서드]");
    LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();
    lhm.put("banana", 500);
    lhm.put("cherry", 300);
    lhm.put("mango", 700);
    lhm.put("apple", 1000);
    System.out.println("lhm: " + lhm);

    System.out.println("firstEntry(): " + lhm.firstEntry()); // banana=500
    System.out.println("lastEntry():  " + lhm.lastEntry());  // apple=1000

    lhm.putFirst("aaa", 0);  // 맨 앞에 추가
    lhm.putLast("zzz", 999); // 맨 뒤에 추가
    System.out.println("putFirst(aaa), putLast(zzz): " + lhm);

    Map.Entry<String, Integer> first = lhm.pollFirstEntry(); // 첫 번째 제거
    Map.Entry<String, Integer> last  = lhm.pollLastEntry();  // 마지막 제거
    System.out.println("pollFirstEntry(): " + first.getKey() + "=" + first.getValue());
    System.out.println("pollLastEntry():  " + last.getKey()  + "=" + last.getValue());
    System.out.println("제거 후 lhm: " + lhm);

    // 2. reversed() - 역순 뷰
    System.out.println("\n[reversed() - 역순 뷰]");
    LinkedHashMap<String, Integer> original = new LinkedHashMap<>();
    original.put("A", 1);
    original.put("B", 2);
    original.put("C", 3);
    original.put("D", 4);
    System.out.println("original:   " + original);           // {A=1, B=2, C=3, D=4}
    System.out.println("reversed(): " + original.reversed()); // {D=4, C=3, B=2, A=1}

    // reversed()는 원본의 뷰 - 원본 수정 시 함께 반영
    original.put("E", 5);
    System.out.println("원본 put(E) 후 reversed: " + original.reversed()); // E=5가 맨 앞

    // reversed() 뷰에서 putFirst/putLast는 원본의 반대 방향
    SequencedMap<String, Integer> rev = original.reversed();
    rev.putFirst("Z", 26); // reversed의 맨 앞 = 원본의 맨 뒤
    System.out.println("reversed.putFirst(Z) 후 original: " + original);

    // 3. sequencedKeySet() / sequencedValues() / sequencedEntrySet()
    System.out.println("\n[sequencedKeySet() / sequencedValues() / sequencedEntrySet()]");
    LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
    map.put("banana", 500);
    map.put("apple", 1000);
    map.put("cherry", 300);

    System.out.println("sequencedKeySet():   " + map.sequencedKeySet());
    System.out.println("sequencedValues():   " + map.sequencedValues());
    System.out.println("sequencedEntrySet(): " + map.sequencedEntrySet());

    // sequencedKeySet().reversed() - 역순 키 순회
    System.out.println("역순 키: " + map.sequencedKeySet().reversed());
    // sequencedValues().reversed() - 역순 값 순회
    System.out.println("역순 값: " + map.sequencedValues().reversed());

    // 4. SequencedMap 타입으로 다형성
    System.out.println("\n[SequencedMap 타입으로 다형성 - LinkedHashMap / TreeMap]");
    SequencedMap<String, Integer> sm1 = new LinkedHashMap<>();
    sm1.put("banana", 500);
    sm1.put("apple", 1000);
    sm1.put("cherry", 300);
    System.out.println("LinkedHashMap firstEntry(): " + sm1.firstEntry()); // banana (추가 순서)
    System.out.println("LinkedHashMap lastEntry():  " + sm1.lastEntry());  // cherry

    SequencedMap<String, Integer> sm2 = new TreeMap<>();
    sm2.put("banana", 500);
    sm2.put("apple", 1000);
    sm2.put("cherry", 300);
    System.out.println("TreeMap     firstEntry(): " + sm2.firstEntry()); // apple (정렬 순서)
    System.out.println("TreeMap     lastEntry():  " + sm2.lastEntry());  // cherry

    // 5. TreeMap - SequencedMap 메서드
    System.out.println("\n[TreeMap - SequencedMap 메서드]");
    TreeMap<String, Integer> treeMap = new TreeMap<>();
    treeMap.put("banana", 500);
    treeMap.put("apple", 1000);
    treeMap.put("mango", 700);
    treeMap.put("cherry", 300);
    treeMap.put("strawberry", 800);
    System.out.println("treeMap (정렬): " + treeMap);

    System.out.println("firstEntry(): " + treeMap.firstEntry()); // apple=1000 (최솟값 키)
    System.out.println("lastEntry():  " + treeMap.lastEntry());  // strawberry=800 (최댓값 키)

    Map.Entry<String, Integer> polled = treeMap.pollFirstEntry();
    System.out.println("pollFirstEntry(): " + polled.getKey() + "=" + polled.getValue());
    System.out.println("pollLastEntry():  " + treeMap.pollLastEntry().getKey());
    System.out.println("제거 후 treeMap: " + treeMap);
    System.out.println("reversed(): " + treeMap.reversed());

    // 6. putFirst() / putLast() - LinkedHashMap에서의 순서 제어
    System.out.println("\n[putFirst() / putLast() - 순서 제어]");
    LinkedHashMap<String, Integer> orderMap = new LinkedHashMap<>();
    orderMap.put("B", 2);
    orderMap.put("C", 3);
    orderMap.put("D", 4);
    System.out.println("초기: " + orderMap); // {B=2, C=3, D=4}

    orderMap.putFirst("A", 1); // 맨 앞에 삽입
    System.out.println("putFirst(A): " + orderMap); // {A=1, B=2, C=3, D=4}

    orderMap.putLast("E", 5); // 맨 뒤에 삽입
    System.out.println("putLast(E): " + orderMap); // {A=1, B=2, C=3, D=4, E=5}

    // 이미 있는 키를 putFirst/putLast하면 위치 이동
    orderMap.putFirst("C", 30); // C를 맨 앞으로 이동하며 값도 갱신
    System.out.println("putFirst(C,30): " + orderMap); // {C=30, A=1, B=2, D=4, E=5}

    // 7. 빈 Map의 firstEntry() / lastEntry() - null 반환 (예외 없음)
    System.out.println("\n[빈 Map - firstEntry() / lastEntry() → null 반환]");
    LinkedHashMap<String, Integer> empty = new LinkedHashMap<>();
    System.out.println("firstEntry(): " + empty.firstEntry()); // null
    System.out.println("lastEntry():  " + empty.lastEntry());  // null
    System.out.println("pollFirstEntry(): " + empty.pollFirstEntry()); // null
  }
}
