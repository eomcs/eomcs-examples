package com.eomcs.advanced.collection.exam09;

import java.util.NavigableSet;
import java.util.TreeSet;

// NavigableSet<E>:
// - java.util 패키지에 소속되어 있다.
// - SortedSet<E>를 확장한 인터페이스이다. (Java 21부터 SequencedSet도 확장)
// - 정렬된 순서를 기반으로 특정 값에 가장 가까운 요소를 탐색하는 메서드를 추가한다.
// - TreeSet이 대표적인 구현체이다.
//
// 인터페이스 계층:
//   Iterable → Collection → Set → SortedSet → NavigableSet
//                                               └── SequencedSet (Java 21)
//
// 탐색 메서드 비교:
//   lower(e)   : e보다 작은 요소 중 최댓값 반환 (strictly less than)
//   floor(e)   : e 이하의 요소 중 최댓값 반환   (less than or equal)
//   ceiling(e) : e 이상의 요소 중 최솟값 반환   (greater than or equal)
//   higher(e)  : e보다 큰 요소 중 최솟값 반환   (strictly greater than)
//   → 조건에 맞는 요소가 없으면 null 반환
//
// 범위 뷰 메서드 (inclusive 옵션으로 경계 포함 여부 지정):
//   headSet(toElement, inclusive)                   toElement 미만 (또는 이하)
//   tailSet(fromElement, inclusive)                 fromElement 초과 (또는 이상)
//   subSet(fromElement, fromInclusive, toElement, toInclusive)  범위 뷰
//
// 제거 메서드:
//   pollFirst() : 첫 번째(최솟값) 요소 제거 후 반환. 비어 있으면 null
//   pollLast()  : 마지막(최댓값) 요소 제거 후 반환. 비어 있으면 null
//
// 역순 뷰:
//   descendingSet()     : 역순으로 정렬된 NavigableSet 뷰 반환
//   descendingIterator(): 역순 Iterator 반환
//

public class App4 {

  public static void main(String[] args) {

    // 1. 기본 탐색 메서드 - lower / floor / ceiling / higher
    System.out.println("[탐색 메서드 - lower / floor / ceiling / higher]");
    NavigableSet<Integer> set = new TreeSet<>();
    set.add(10);
    set.add(20);
    set.add(30);
    set.add(40);
    set.add(50);
    System.out.println("set: " + set); // [10, 20, 30, 40, 50]

    // lower(e): e보다 작은 요소 중 최댓값 (e 미포함)
    System.out.println("lower(30):  " + set.lower(30));   // 20  (30 미만 최댓값)
    System.out.println("lower(10):  " + set.lower(10));   // null (10 미만 없음)
    System.out.println("lower(25):  " + set.lower(25));   // 20  (25 미만 최댓값)

    // floor(e): e 이하의 요소 중 최댓값 (e 포함)
    System.out.println("floor(30):  " + set.floor(30));   // 30  (30 이하 최댓값 = 30)
    System.out.println("floor(25):  " + set.floor(25));   // 20  (25 이하 최댓값)
    System.out.println("floor(5):   " + set.floor(5));    // null (5 이하 없음)

    // ceiling(e): e 이상의 요소 중 최솟값 (e 포함)
    System.out.println("ceiling(30): " + set.ceiling(30)); // 30  (30 이상 최솟값 = 30)
    System.out.println("ceiling(25): " + set.ceiling(25)); // 30  (25 이상 최솟값)
    System.out.println("ceiling(55): " + set.ceiling(55)); // null (55 이상 없음)

    // higher(e): e보다 큰 요소 중 최솟값 (e 미포함)
    System.out.println("higher(30): " + set.higher(30));  // 40  (30 초과 최솟값)
    System.out.println("higher(50): " + set.higher(50));  // null (50 초과 없음)
    System.out.println("higher(25): " + set.higher(25));  // 30  (25 초과 최솟값)

    // 2. 탐색 메서드 비교 - floor vs lower, ceiling vs higher
    System.out.println("\n[탐색 메서드 비교 - 경계값 포함 여부]");
    //         lower   floor  ceiling  higher
    // e=30:   20      30     30       40       ← floor/ceiling은 e 자체도 포함
    // e=25:   20      20     30       30       ← lower/higher은 e 제외
    System.out.println("e=30 → lower: " + set.lower(30) + ", floor: " + set.floor(30)
        + ", ceiling: " + set.ceiling(30) + ", higher: " + set.higher(30));
    System.out.println("e=25 → lower: " + set.lower(25) + ", floor: " + set.floor(25)
        + ", ceiling: " + set.ceiling(25) + ", higher: " + set.higher(25));

    // 3. pollFirst() / pollLast() - 제거 후 반환
    System.out.println("\n[pollFirst() / pollLast() - 제거 후 반환]");
    NavigableSet<String> words = new TreeSet<>();
    words.add("banana");
    words.add("apple");
    words.add("cherry");
    words.add("mango");
    words.add("strawberry");
    System.out.println("words: " + words);

    System.out.println("pollFirst(): " + words.pollFirst()); // apple (최솟값 제거)
    System.out.println("pollLast():  " + words.pollLast());  // strawberry (최댓값 제거)
    System.out.println("제거 후: " + words);

    // 빈 Set이면 null 반환 (예외 없음)
    NavigableSet<String> empty = new TreeSet<>();
    System.out.println("빈 Set pollFirst(): " + empty.pollFirst()); // null

    // 4. headSet(toElement, inclusive) - 범위 뷰
    System.out.println("\n[headSet(toElement, inclusive) - 범위 뷰]");
    NavigableSet<Integer> ns = new TreeSet<>();
    for (int i = 10; i <= 50; i += 10) ns.add(i);
    System.out.println("ns: " + ns); // [10, 20, 30, 40, 50]

    System.out.println("headSet(30, false): " + ns.headSet(30, false)); // [10, 20]       (30 미포함)
    System.out.println("headSet(30, true):  " + ns.headSet(30, true));  // [10, 20, 30]   (30 포함)

    // SortedSet.headSet(toElement)는 false(미포함)가 기본
    System.out.println("headSet(30) [SortedSet]: " + ns.headSet(30));   // [10, 20]

    // 5. tailSet(fromElement, inclusive) - 범위 뷰
    System.out.println("\n[tailSet(fromElement, inclusive) - 범위 뷰]");
    System.out.println("tailSet(30, false): " + ns.tailSet(30, false)); // [40, 50]       (30 미포함)
    System.out.println("tailSet(30, true):  " + ns.tailSet(30, true));  // [30, 40, 50]   (30 포함)

    // SortedSet.tailSet(fromElement)는 true(포함)가 기본
    System.out.println("tailSet(30) [SortedSet]: " + ns.tailSet(30));   // [30, 40, 50]

    // 6. subSet(from, fromInclusive, to, toInclusive) - 범위 뷰
    System.out.println("\n[subSet() - 범위 뷰]");
    System.out.println("subSet(20, true,  40, true):  " + ns.subSet(20, true,  40, true));  // [20, 30, 40]
    System.out.println("subSet(20, true,  40, false): " + ns.subSet(20, true,  40, false)); // [20, 30]
    System.out.println("subSet(20, false, 40, true):  " + ns.subSet(20, false, 40, true));  // [30, 40]
    System.out.println("subSet(20, false, 40, false): " + ns.subSet(20, false, 40, false)); // [30]

    // SortedSet.subSet(from, to)는 from 포함, to 미포함이 기본
    System.out.println("subSet(20, 40) [SortedSet]:   " + ns.subSet(20, 40));               // [20, 30]

    // 7. 범위 뷰는 원본의 뷰 - 수정 시 원본에도 반영
    System.out.println("\n[범위 뷰 - 원본과 연동]");
    NavigableSet<Integer> src = new TreeSet<>();
    for (int i = 1; i <= 10; i++) src.add(i);
    System.out.println("src: " + src);

    NavigableSet<Integer> view = src.subSet(3, true, 7, true); // [3, 4, 5, 6, 7]
    System.out.println("subSet(3~7): " + view);

    view.add(5);  // 이미 있음 → 변화 없음
    view.remove(5);         // 뷰에서 제거 → 원본에도 반영
    System.out.println("view.remove(5) 후 src: " + src); // 5가 제거됨

    src.add(6);   // 원본 수정 → 뷰에도 반영 (이미 있으므로 변화 없음)
    src.remove(4); // 원본 수정 → 뷰에도 반영
    System.out.println("src.remove(4) 후 view: " + view);

    // 8. descendingSet() - 역순 NavigableSet 뷰
    System.out.println("\n[descendingSet() - 역순 뷰]");
    NavigableSet<Integer> asc = new TreeSet<>();
    asc.add(30); asc.add(10); asc.add(50); asc.add(20); asc.add(40);
    System.out.println("오름차순: " + asc);

    NavigableSet<Integer> desc = asc.descendingSet();
    System.out.println("내림차순: " + desc);

    // descendingSet()도 원본의 뷰
    asc.add(60);
    System.out.println("원본 add(60) 후 desc: " + desc); // 60이 맨 앞에 반영

    // descendingSet()에서도 NavigableSet 메서드 사용 가능
    System.out.println("desc.lower(40):   " + desc.lower(40));   // 50  (내림차순 기준으로 40보다 앞)
    System.out.println("desc.higher(40):  " + desc.higher(40));  // 30  (내림차순 기준으로 40보다 뒤)
    System.out.println("desc.pollFirst(): " + desc.pollFirst());  // 60  (내림차순 첫 번째 = 최댓값)
    System.out.println("desc.pollLast():  " + desc.pollLast());   // 10  (내림차순 마지막 = 최솟값)
    System.out.println("제거 후 desc: " + desc);
  }
}
