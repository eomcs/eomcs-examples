package com.eomcs.quickstart.collection.exam09;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

// NavigableMap<K,V>:
// - java.util 패키지에 소속되어 있다.
// - SortedMap<K,V>를 확장한 인터페이스이다. (Java 21부터 SequencedMap도 확장)
// - 정렬된 키 순서를 기반으로 특정 키에 가장 가까운 항목을 탐색하는 메서드를 추가한다.
// - TreeMap이 대표적인 구현체이다.
//
// 인터페이스 계층:
//   Map → SortedMap → NavigableMap
//                      └── SequencedMap (Java 21)
//
// 탐색 메서드 (키 반환 vs Entry 반환 쌍으로 제공):
//   lowerKey(k)   / lowerEntry(k)   : k보다 작은 키 중 최댓값   (strictly less than)
//   floorKey(k)   / floorEntry(k)   : k 이하의 키 중 최댓값     (less than or equal)
//   ceilingKey(k) / ceilingEntry(k) : k 이상의 키 중 최솟값     (greater than or equal)
//   higherKey(k)  / higherEntry(k)  : k보다 큰 키 중 최솟값     (strictly greater than)
//   → 조건에 맞는 항목이 없으면 null 반환
//
// 제거 메서드:
//   pollFirstEntry() : 첫 번째(최솟값 키) 항목 제거 후 반환. 비어 있으면 null
//   pollLastEntry()  : 마지막(최댓값 키) 항목 제거 후 반환. 비어 있으면 null
//
// 범위 뷰 메서드 (inclusive 옵션으로 경계 포함 여부 지정):
//   headMap(toKey, inclusive)                           toKey 미만 (또는 이하)
//   tailMap(fromKey, inclusive)                         fromKey 초과 (또는 이상)
//   subMap(fromKey, fromInclusive, toKey, toInclusive)  범위 뷰
//
// 역순 뷰:
//   descendingMap()     : 역순으로 정렬된 NavigableMap 뷰 반환
//   descendingKeySet()  : 역순으로 정렬된 NavigableSet<K> 반환
//   navigableKeySet()   : 오름차순 NavigableSet<K> 반환
//

public class App5 {

  public static void main(String[] args) {

    // 1. 기본 탐색 메서드 - lowerKey / floorKey / ceilingKey / higherKey
    System.out.println("[탐색 메서드 - lowerKey / floorKey / ceilingKey / higherKey]");
    NavigableMap<Integer, String> map = new TreeMap<>();
    map.put(10, "ten");
    map.put(20, "twenty");
    map.put(30, "thirty");
    map.put(40, "forty");
    map.put(50, "fifty");
    System.out.println("map: " + map);

    // lowerKey(k): k 미만 최댓값 키
    System.out.println("lowerKey(30):   " + map.lowerKey(30));   // 20
    System.out.println("lowerKey(10):   " + map.lowerKey(10));   // null
    System.out.println("lowerKey(25):   " + map.lowerKey(25));   // 20

    // floorKey(k): k 이하 최댓값 키
    System.out.println("floorKey(30):   " + map.floorKey(30));   // 30 (자신 포함)
    System.out.println("floorKey(25):   " + map.floorKey(25));   // 20
    System.out.println("floorKey(5):    " + map.floorKey(5));    // null

    // ceilingKey(k): k 이상 최솟값 키
    System.out.println("ceilingKey(30): " + map.ceilingKey(30)); // 30 (자신 포함)
    System.out.println("ceilingKey(25): " + map.ceilingKey(25)); // 30
    System.out.println("ceilingKey(55): " + map.ceilingKey(55)); // null

    // higherKey(k): k 초과 최솟값 키
    System.out.println("higherKey(30):  " + map.higherKey(30));  // 40
    System.out.println("higherKey(50):  " + map.higherKey(50));  // null
    System.out.println("higherKey(25):  " + map.higherKey(25));  // 30

    // 2. Entry 반환 탐색 메서드 - lowerEntry / floorEntry / ceilingEntry / higherEntry
    System.out.println("\n[탐색 메서드 - Entry 반환]");
    Map.Entry<Integer, String> le = map.lowerEntry(30);
    Map.Entry<Integer, String> fe = map.floorEntry(30);
    Map.Entry<Integer, String> ce = map.ceilingEntry(25);
    Map.Entry<Integer, String> he = map.higherEntry(30);
    System.out.println("lowerEntry(30):   " + le.getKey() + "=" + le.getValue());   // 20=twenty
    System.out.println("floorEntry(30):   " + fe.getKey() + "=" + fe.getValue());   // 30=thirty
    System.out.println("ceilingEntry(25): " + ce.getKey() + "=" + ce.getValue());   // 30=thirty
    System.out.println("higherEntry(30):  " + he.getKey() + "=" + he.getValue());   // 40=forty

    // 3. 4가지 탐색 메서드 비교 - 경계값 포함 여부
    System.out.println("\n[탐색 메서드 비교 - 경계값 포함 여부]");
    System.out.println("k=30 → lower: " + map.lowerKey(30) + ", floor: " + map.floorKey(30)
        + ", ceiling: " + map.ceilingKey(30) + ", higher: " + map.higherKey(30));
    System.out.println("k=25 → lower: " + map.lowerKey(25) + ", floor: " + map.floorKey(25)
        + ", ceiling: " + map.ceilingKey(25) + ", higher: " + map.higherKey(25));

    // 4. pollFirstEntry() / pollLastEntry() - 제거 후 반환
    System.out.println("\n[pollFirstEntry() / pollLastEntry() - 제거 후 반환]");
    NavigableMap<String, Integer> scores = new TreeMap<>();
    scores.put("alice", 90);
    scores.put("bob", 75);
    scores.put("carol", 85);
    scores.put("dave", 95);
    scores.put("eve", 80);
    System.out.println("scores: " + scores);

    Map.Entry<String, Integer> polledFirst = scores.pollFirstEntry();
    Map.Entry<String, Integer> polledLast  = scores.pollLastEntry();
    System.out.println("pollFirstEntry(): " + polledFirst.getKey() + "=" + polledFirst.getValue()); // alice
    System.out.println("pollLastEntry():  " + polledLast.getKey()  + "=" + polledLast.getValue());  // eve
    System.out.println("제거 후: " + scores);

    // 빈 Map이면 null 반환 (예외 없음)
    NavigableMap<String, Integer> empty = new TreeMap<>();
    System.out.println("빈 Map pollFirstEntry(): " + empty.pollFirstEntry()); // null

    // 5. headMap(toKey, inclusive) - 범위 뷰
    System.out.println("\n[headMap(toKey, inclusive) - 범위 뷰]");
    NavigableMap<Integer, String> nm = new TreeMap<>(map);
    System.out.println("nm: " + nm); // {10=ten, 20=twenty, 30=thirty, 40=forty, 50=fifty}

    System.out.println("headMap(30, false): " + nm.headMap(30, false)); // {10, 20}       (30 미포함)
    System.out.println("headMap(30, true):  " + nm.headMap(30, true));  // {10, 20, 30}   (30 포함)

    // SortedMap.headMap(toKey)는 false(미포함)가 기본
    System.out.println("headMap(30) [SortedMap]: " + nm.headMap(30));   // {10, 20}

    // 6. tailMap(fromKey, inclusive) - 범위 뷰
    System.out.println("\n[tailMap(fromKey, inclusive) - 범위 뷰]");
    System.out.println("tailMap(30, false): " + nm.tailMap(30, false)); // {40, 50}       (30 미포함)
    System.out.println("tailMap(30, true):  " + nm.tailMap(30, true));  // {30, 40, 50}   (30 포함)

    // SortedMap.tailMap(fromKey)는 true(포함)가 기본
    System.out.println("tailMap(30) [SortedMap]: " + nm.tailMap(30));   // {30, 40, 50}

    // 7. subMap(from, fromInclusive, to, toInclusive) - 범위 뷰
    System.out.println("\n[subMap() - 범위 뷰]");
    System.out.println("subMap(20, true,  40, true):  " + nm.subMap(20, true,  40, true));  // {20,30,40}
    System.out.println("subMap(20, true,  40, false): " + nm.subMap(20, true,  40, false)); // {20,30}
    System.out.println("subMap(20, false, 40, true):  " + nm.subMap(20, false, 40, true));  // {30,40}
    System.out.println("subMap(20, false, 40, false): " + nm.subMap(20, false, 40, false)); // {30}

    // SortedMap.subMap(from, to)는 from 포함, to 미포함이 기본
    System.out.println("subMap(20, 40) [SortedMap]:   " + nm.subMap(20, 40));               // {20,30}

    // 8. 범위 뷰는 원본의 뷰 - 수정 시 원본에도 반영
    System.out.println("\n[범위 뷰 - 원본과 연동]");
    NavigableMap<Integer, String> src = new TreeMap<>();
    for (int i = 1; i <= 10; i++) src.put(i * 10, "val" + i);
    System.out.println("src: " + src);

    NavigableMap<Integer, String> view = src.subMap(30, true, 70, true);
    System.out.println("subMap(30~70): " + view); // {30, 40, 50, 60, 70}

    view.put(45, "val4.5");   // 뷰에서 추가 → 원본에도 반영
    System.out.println("view.put(45) 후 src: " + src);

    src.remove(50);           // 원본에서 제거 → 뷰에도 반영
    System.out.println("src.remove(50) 후 view: " + view);

    // 범위 밖 키를 뷰에 추가하면 IllegalArgumentException
    try {
      view.put(80, "out of range");
    } catch (IllegalArgumentException e) {
      System.out.println("범위 밖 put(80) → IllegalArgumentException");
    }

    // 9. descendingMap() - 역순 NavigableMap 뷰
    System.out.println("\n[descendingMap() - 역순 뷰]");
    NavigableMap<Integer, String> asc = new TreeMap<>(map);
    System.out.println("오름차순: " + asc);

    NavigableMap<Integer, String> desc = asc.descendingMap();
    System.out.println("내림차순: " + desc);

    // descendingMap()도 원본의 뷰
    asc.put(60, "sixty");
    System.out.println("원본 put(60) 후 desc: " + desc); // 60이 맨 앞에 반영

    // descendingMap()에서도 NavigableMap 메서드 사용 가능
    System.out.println("desc.lowerKey(40):   " + desc.lowerKey(40));   // 50 (내림차순 기준 40보다 앞)
    System.out.println("desc.higherKey(40):  " + desc.higherKey(40));  // 30 (내림차순 기준 40보다 뒤)
    System.out.println("desc.pollFirstEntry(): " + desc.pollFirstEntry()); // 60=sixty (내림차순 첫 번째)
    System.out.println("desc.pollLastEntry():  " + desc.pollLastEntry());  // 10=ten   (내림차순 마지막)
    System.out.println("제거 후 desc: " + desc);

    // 10. navigableKeySet() / descendingKeySet()
    System.out.println("\n[navigableKeySet() / descendingKeySet()]");
    NavigableMap<String, Integer> gradeMap = new TreeMap<>();
    gradeMap.put("alice", 90);
    gradeMap.put("bob", 75);
    gradeMap.put("carol", 85);
    gradeMap.put("dave", 95);
    System.out.println("navigableKeySet():  " + gradeMap.navigableKeySet());  // 오름차순 키
    System.out.println("descendingKeySet(): " + gradeMap.descendingKeySet()); // 내림차순 키

    // navigableKeySet()은 NavigableSet이므로 탐색 메서드 사용 가능
    System.out.println("키 floor(\"c\"): " + gradeMap.navigableKeySet().floor("c")); // carol
    System.out.println("키 higher(\"b\"): " + gradeMap.navigableKeySet().higher("b")); // bob보다 큰 다음 키

    // NavigableSet vs NavigableMap 탐색 비교
    System.out.println("\n[NavigableSet vs NavigableMap 탐색 비교]");
    //   NavigableSet: lower/floor/ceiling/higher → 요소(E) 반환
    //   NavigableMap: lowerKey/floorKey/...      → 키(K) 반환
    //                 lowerEntry/floorEntry/...  → Map.Entry<K,V> 반환
    System.out.println("floorKey(\"c\"):   " + gradeMap.floorKey("c"));    // carol (키만)
    System.out.println("floorEntry(\"c\"): " + gradeMap.floorEntry("c"));  // carol=85 (키+값)
  }
}
