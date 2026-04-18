# Exam09 - Sequenced Collections (Java 21)

## 개념

Java 21(JEP 431)에서 도입된 Sequenced Collections는 요소에 명확한 순서(encounter order)가 있는 컬렉션을 일관된 인터페이스로 다루기 위해 추가된 세 가지 인터페이스이다.

### Sequenced Collections 인터페이스 계층

```
Collection
└── SequencedCollection          ← 순서 있는 컬렉션 (List, Deque)
    └── SequencedSet             ← 순서 있는 Set (LinkedHashSet, TreeSet)
        └── SortedSet
            └── NavigableSet    ← 근접 탐색 + 범위 뷰 (TreeSet)

Map
└── SequencedMap                 ← 순서 있는 Map (LinkedHashMap, TreeMap)
    └── SortedMap
        └── NavigableMap        ← 근접 탐색 + 범위 뷰 (TreeMap)
```

### 인터페이스 비교

| 인터페이스 | 확장 | 구현 클래스 | 특징 |
|---|---|---|---|
| `SequencedCollection<E>` | `Collection<E>` | `ArrayList`, `LinkedList`, `ArrayDeque` | 순서 있는 컬렉션의 공통 메서드 |
| `SequencedSet<E>` | `SequencedCollection<E>`, `Set<E>` | `LinkedHashSet`, `TreeSet` | 중복 없는 순서 있는 Set |
| `SequencedMap<K,V>` | `Map<K,V>` | `LinkedHashMap`, `TreeMap` | 순서 있는 Map |

### SequencedCollection 주요 메서드

| 메서드 | 설명 | 비어 있을 때 |
|---|---|---|
| `E getFirst()` | 첫 번째 요소 반환 | `NoSuchElementException` |
| `E getLast()` | 마지막 요소 반환 | `NoSuchElementException` |
| `void addFirst(E e)` | 맨 앞에 요소 추가 | — |
| `void addLast(E e)` | 맨 뒤에 요소 추가 | — |
| `E removeFirst()` | 첫 번째 요소 제거 후 반환 | `NoSuchElementException` |
| `E removeLast()` | 마지막 요소 제거 후 반환 | `NoSuchElementException` |
| `SequencedCollection<E> reversed()` | 역순 뷰 반환 (원본의 뷰) | — |

### SequencedMap 추가 메서드

| 메서드 | 설명 | 비어 있을 때 |
|---|---|---|
| `Map.Entry<K,V> firstEntry()` | 첫 번째 항목 반환 | `null` |
| `Map.Entry<K,V> lastEntry()` | 마지막 항목 반환 | `null` |
| `Map.Entry<K,V> pollFirstEntry()` | 첫 번째 항목 제거 후 반환 | `null` |
| `Map.Entry<K,V> pollLastEntry()` | 마지막 항목 제거 후 반환 | `null` |
| `void putFirst(K k, V v)` | 맨 앞에 항목 추가 | — |
| `void putLast(K k, V v)` | 맨 뒤에 항목 추가 | — |
| `SequencedMap<K,V> reversed()` | 역순 뷰 반환 (원본의 뷰) | — |
| `SequencedSet<K> sequencedKeySet()` | 순서 있는 키 Set 반환 | — |
| `SequencedCollection<V> sequencedValues()` | 순서 있는 값 컬렉션 반환 | — |
| `SequencedSet<Map.Entry<K,V>> sequencedEntrySet()` | 순서 있는 Entry Set 반환 | — |

### NavigableSet 주요 메서드

| 메서드 | 설명 | 결과 없을 때 |
|---|---|---|
| `E lower(E e)` | `e` 미만 최댓값 요소 반환 | `null` |
| `E floor(E e)` | `e` 이하 최댓값 요소 반환 (`e` 포함) | `null` |
| `E ceiling(E e)` | `e` 이상 최솟값 요소 반환 (`e` 포함) | `null` |
| `E higher(E e)` | `e` 초과 최솟값 요소 반환 | `null` |
| `E pollFirst()` | 첫 번째(최솟값) 요소 제거 후 반환 | `null` |
| `E pollLast()` | 마지막(최댓값) 요소 제거 후 반환 | `null` |
| `NavigableSet<E> headSet(E to, boolean inclusive)` | `to` 미만(또는 이하) 범위 뷰 | — |
| `NavigableSet<E> tailSet(E from, boolean inclusive)` | `from` 초과(또는 이상) 범위 뷰 | — |
| `NavigableSet<E> subSet(E from, boolean fi, E to, boolean ti)` | 범위 뷰 | — |
| `NavigableSet<E> descendingSet()` | 역순 뷰 반환 | — |
| `Iterator<E> descendingIterator()` | 역순 Iterator 반환 | — |

### NavigableMap 주요 메서드

| 메서드 | 설명 | 결과 없을 때 |
|---|---|---|
| `K lowerKey(K k)` / `Map.Entry<K,V> lowerEntry(K k)` | `k` 미만 최댓값 키/항목 | `null` |
| `K floorKey(K k)` / `Map.Entry<K,V> floorEntry(K k)` | `k` 이하 최댓값 키/항목 | `null` |
| `K ceilingKey(K k)` / `Map.Entry<K,V> ceilingEntry(K k)` | `k` 이상 최솟값 키/항목 | `null` |
| `K higherKey(K k)` / `Map.Entry<K,V> higherEntry(K k)` | `k` 초과 최솟값 키/항목 | `null` |
| `Map.Entry<K,V> pollFirstEntry()` | 첫 번째 항목 제거 후 반환 | `null` |
| `Map.Entry<K,V> pollLastEntry()` | 마지막 항목 제거 후 반환 | `null` |
| `NavigableMap<K,V> headMap(K to, boolean inclusive)` | `to` 미만(또는 이하) 범위 뷰 | — |
| `NavigableMap<K,V> tailMap(K from, boolean inclusive)` | `from` 초과(또는 이상) 범위 뷰 | — |
| `NavigableMap<K,V> subMap(K from, boolean fi, K to, boolean ti)` | 범위 뷰 | — |
| `NavigableMap<K,V> descendingMap()` | 역순 뷰 반환 | — |
| `NavigableSet<K> navigableKeySet()` | 오름차순 키 Set 반환 | — |
| `NavigableSet<K> descendingKeySet()` | 내림차순 키 Set 반환 | — |

### 탐색 메서드 경계값 포함 여부

| 메서드 | `e` 자신 포함 | 방향 |
|---|---|---|
| `lower(e)` / `lowerKey(k)` | 미포함 | e보다 작은 방향 |
| `floor(e)` / `floorKey(k)` | **포함** | e보다 작은 방향 |
| `ceiling(e)` / `ceilingKey(k)` | **포함** | e보다 큰 방향 |
| `higher(e)` / `higherKey(k)` | 미포함 | e보다 큰 방향 |

### 범위 뷰 기본 동작 (SortedSet / SortedMap 호환)

| 메서드 | NavigableXxx (inclusive 명시) | SortedXxx (기본값) |
|---|---|---|
| `headSet` / `headMap` | `inclusive` 지정 가능 | `to` 미포함 (`false`) |
| `tailSet` / `tailMap` | `inclusive` 지정 가능 | `from` 포함 (`true`) |
| `subSet` / `subMap` | 양쪽 `inclusive` 지정 가능 | `from` 포함, `to` 미포함 |

### reversed() 뷰 특성

`reversed()`는 원본의 역순 **뷰(view)**를 반환한다. 복사본이 아니므로 원본을 수정하면 뷰에도 반영된다.
뷰에서의 `addFirst()` / `putFirst()`는 원본의 맨 뒤에 추가하고, `addLast()` / `putLast()`는 원본의 맨 앞에 추가한다.

### Java 21 이전 vs 이후 비교

| 작업 | Java 21 이전 | Java 21 이후 |
|---|---|---|
| 첫 번째 요소 | `list.get(0)` | `list.getFirst()` |
| 마지막 요소 | `list.get(list.size() - 1)` | `list.getLast()` |
| 역순 순회 | `Collections.reverse(new ArrayList<>(list))` | `list.reversed()` |
| Map 첫 항목 | `map.entrySet().iterator().next()` | `map.firstEntry()` |

## App - SequencedCollection 사용 (List, Deque)

```java
// 1. ArrayList - SequencedCollection 메서드
List<String> list = new ArrayList<>(List.of("banana", "cherry", "mango"));
list.getFirst(); // "banana"
list.getLast();  // "mango"

list.addFirst("apple");     // [apple, banana, cherry, mango]
list.addLast("strawberry"); // [apple, banana, cherry, mango, strawberry]

list.removeFirst(); // "apple"  제거 후 반환
list.removeLast();  // "strawberry" 제거 후 반환

// 2. reversed() - 역순 뷰 (원본의 뷰)
List<String> original = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
List<String> reversed = original.reversed(); // [E, D, C, B, A]

original.add("F");              // 원본 수정
System.out.println(reversed);   // [F, E, D, C, B, A] ← 함께 반영

reversed.addFirst("Z");         // reversed 맨 앞 = 원본 맨 뒤
System.out.println(original);   // [..., Z]

// 3. SequencedCollection 타입으로 다형성
SequencedCollection<Integer> sc = new ArrayList<>(List.of(10, 20, 30, 40, 50));
sc.getFirst();   // 10
sc.getLast();    // 50
sc.addFirst(5);  // [5, 10, 20, 30, 40, 50]
sc.addLast(55);  // [5, 10, 20, 30, 40, 50, 55]
sc.reversed();   // [55, 50, 40, 30, 20, 10, 5]

// 4. ArrayDeque - SequencedCollection 구현
Deque<String> deque = new ArrayDeque<>(List.of("1st", "2nd", "3rd"));
deque.getFirst(); // "1st"
deque.getLast();  // "3rd"
deque.addFirst("0th"); // [0th, 1st, 2nd, 3rd]
deque.addLast("4th");  // [0th, 1st, 2nd, 3rd, 4th]
deque.reversed();      // [4th, 3rd, 2nd, 1st, 0th]

// 5. 빈 컬렉션 → NoSuchElementException
List<String> empty = new ArrayList<>();
empty.getFirst(); // NoSuchElementException
empty.getLast();  // NoSuchElementException

// 6. Java 21 이전 vs 이후
List<Integer> numbers = List.of(1, 2, 3, 4, 5);
numbers.get(0);               // 이전 방식: 첫 번째
numbers.get(numbers.size()-1);// 이전 방식: 마지막
numbers.getFirst();           // Java 21: 첫 번째
numbers.getLast();            // Java 21: 마지막
```

- `getFirst()` / `getLast()`는 컬렉션이 비어 있으면 `NoSuchElementException`을 발생시킨다. `get(0)`, `get(size()-1)`보다 의도가 명확하고 가독성이 높다.
- `addFirst()` / `addLast()`는 `ArrayList`에서는 O(n) / O(1), `LinkedList`와 `ArrayDeque`에서는 O(1)이다.
- `reversed()`는 복사본이 아닌 **역순 뷰**를 반환한다. 원본이 수정되면 뷰에도 즉시 반영된다. 역순 뷰에서의 `addFirst()`는 원본의 맨 뒤에 추가한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam09.App
  ```

## App2 - SequencedSet 사용 (LinkedHashSet, TreeSet)

```java
// 1. LinkedHashSet - 추가 순서 유지 + SequencedSet
LinkedHashSet<String> lhs = new LinkedHashSet<>();
lhs.add("banana"); lhs.add("cherry"); lhs.add("mango"); lhs.add("apple");
// [banana, cherry, mango, apple]
lhs.getFirst(); // "banana"
lhs.getLast();  // "apple"
lhs.addFirst("aaa"); // [aaa, banana, cherry, mango, apple]
lhs.addLast("zzz");  // [aaa, banana, cherry, mango, apple, zzz]
lhs.removeFirst();   // "aaa" 제거
lhs.removeLast();    // "zzz" 제거

// 2. addFirst() / addLast() - 이미 있는 요소는 위치 이동
LinkedHashSet<String> set = new LinkedHashSet<>(Set.of("A", "B", "C", "D"));
// 초기(Set.of 순서 불확정이므로 add로 구성): [A, B, C, D]
set.addFirst("C"); // 이미 있는 C → 맨 앞으로 이동: [C, A, B, D]
set.addLast("A");  // 이미 있는 A → 맨 뒤로 이동: [C, B, D, A]

// 3. reversed() - 역순 뷰
LinkedHashSet<Integer> nums = new LinkedHashSet<>(List.of(10, 30, 20, 50, 40));
nums.reversed(); // [40, 50, 20, 30, 10]
nums.add(60);    // 원본 수정
// reversed: [60, 40, 50, 20, 30, 10] ← 함께 반영

// 4. SequencedSet 타입으로 다형성
SequencedSet<String> ss1 = new LinkedHashSet<>(List.of("banana", "apple", "cherry"));
ss1.getFirst(); // "banana" (추가 순서 기준)

SequencedSet<String> ss2 = new TreeSet<>(List.of("banana", "apple", "cherry"));
ss2.getFirst(); // "apple" (정렬 순서 기준)

// 5. TreeSet - 정렬 순서 + SequencedSet
TreeSet<Integer> treeSet = new TreeSet<>(List.of(50, 20, 80, 10, 60));
// [10, 20, 50, 60, 80]
treeSet.getFirst();    // 10 (최솟값)
treeSet.getLast();     // 80 (최댓값)
treeSet.removeFirst(); // 10 제거 (최솟값 제거)
treeSet.removeLast();  // 80 제거 (최댓값 제거)
treeSet.reversed();    // [60, 50, 20]

// 6. TreeSet + Comparator(역순) + reversed()
TreeSet<Integer> descSet = new TreeSet<>(Comparator.reverseOrder());
// 추가 후: [80, 50, 20, 10]
descSet.getFirst(); // 80 (정렬 기준 첫 번째)
descSet.getLast();  // 10
descSet.reversed(); // [10, 20, 50, 80]
```

- `LinkedHashSet.addFirst()` / `addLast()`에 이미 존재하는 요소를 전달하면 값이 제거되고 지정한 위치로 재삽입된다. 기존 Set의 동작과 다르므로 주의한다.
- `TreeSet.getFirst()` / `getLast()`는 기존의 `first()` / `last()` 메서드와 동일하지만 `SequencedSet` 타입 변수를 통해서도 호출할 수 있어 다형성이 향상된다.
- `TreeSet.removeFirst()`는 기존의 `pollFirst()`와 달리 비어 있으면 `NoSuchElementException`을 발생시킨다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam09.App2
  ```

## App3 - SequencedMap 사용 (LinkedHashMap, TreeMap)

```java
// 1. LinkedHashMap - SequencedMap 메서드
LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();
lhm.put("banana", 500); lhm.put("cherry", 300);
lhm.put("mango", 700);  lhm.put("apple", 1000);

lhm.firstEntry(); // banana=500
lhm.lastEntry();  // apple=1000

lhm.putFirst("aaa", 0);  // {aaa=0, banana=500, ...}
lhm.putLast("zzz", 999); // {..., apple=1000, zzz=999}

lhm.pollFirstEntry(); // aaa=0  제거 후 반환
lhm.pollLastEntry();  // zzz=999 제거 후 반환

// 2. reversed() - 역순 뷰
LinkedHashMap<String, Integer> original = new LinkedHashMap<>();
// {A=1, B=2, C=3, D=4}
original.reversed(); // {D=4, C=3, B=2, A=1}

original.put("E", 5);          // 원본 수정
original.reversed();           // {E=5, D=4, C=3, B=2, A=1} ← 함께 반영

SequencedMap<String, Integer> rev = original.reversed();
rev.putFirst("Z", 26);         // reversed 맨 앞 = 원본 맨 뒤
// original: {A=1, B=2, C=3, D=4, E=5, Z=26}

// 3. sequencedKeySet() / sequencedValues() / sequencedEntrySet()
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
// {banana=500, apple=1000, cherry=300}
map.sequencedKeySet();           // [banana, apple, cherry]
map.sequencedValues();           // [500, 1000, 300]
map.sequencedEntrySet();         // [banana=500, apple=1000, cherry=300]
map.sequencedKeySet().reversed();// [cherry, apple, banana] - 역순 키 순회
map.sequencedValues().reversed();// [300, 1000, 500]        - 역순 값 순회

// 4. SequencedMap 타입으로 다형성
SequencedMap<String, Integer> sm1 = new LinkedHashMap<>(); // 추가 순서
sm1.firstEntry(); // 추가 순서 기준 첫 번째

SequencedMap<String, Integer> sm2 = new TreeMap<>();       // 정렬 순서
sm2.firstEntry(); // 정렬 순서 기준 첫 번째 (최솟값 키)

// 5. TreeMap - SequencedMap 메서드
TreeMap<String, Integer> treeMap = new TreeMap<>();
// 추가 후 정렬: {apple=1000, banana=500, cherry=300, mango=700, strawberry=800}
treeMap.firstEntry();    // apple=1000 (최솟값 키)
treeMap.lastEntry();     // strawberry=800 (최댓값 키)
treeMap.pollFirstEntry();// apple=1000 제거 후 반환
treeMap.reversed();      // {mango=700, cherry=300, banana=500}

// 6. putFirst() / putLast() - 순서 제어 및 이미 있는 키 위치 이동
LinkedHashMap<String, Integer> orderMap = new LinkedHashMap<>();
// {B=2, C=3, D=4}
orderMap.putFirst("A", 1); // {A=1, B=2, C=3, D=4}
orderMap.putLast("E", 5);  // {A=1, B=2, C=3, D=4, E=5}
orderMap.putFirst("C", 30);// {C=30, A=1, B=2, D=4, E=5} - C가 맨 앞으로 이동하며 값 갱신

// 7. 빈 Map - null 반환 (예외 없음)
LinkedHashMap<String, Integer> empty = new LinkedHashMap<>();
empty.firstEntry();     // null (NoSuchElementException 아님)
empty.lastEntry();      // null
empty.pollFirstEntry(); // null
```

- `SequencedMap`의 `firstEntry()` / `lastEntry()` / `pollFirstEntry()` / `pollLastEntry()`는 빈 Map일 때 `NoSuchElementException` 대신 `null`을 반환한다. `SequencedCollection`의 `getFirst()` / `getLast()`가 예외를 발생시키는 것과 다르다.
- `sequencedKeySet()` / `sequencedValues()` / `sequencedEntrySet()`은 기존 `keySet()` / `values()` / `entrySet()`과 달리 `SequencedCollection` 타입을 반환하므로 `getFirst()`, `getLast()`, `reversed()` 등을 바로 사용할 수 있다.
- `LinkedHashMap.putFirst()` / `putLast()`에 이미 존재하는 키를 전달하면 해당 항목을 지정한 위치로 이동하고 값도 갱신한다.
- `TreeMap`에서 `putFirst()` / `putLast()`는 정렬 순서를 위반하는 키를 삽입하려 하면 `IllegalArgumentException`이 발생한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam09.App3
  ```

## App4 - NavigableSet 사용

```java
NavigableSet<Integer> set = new TreeSet<>();
// [10, 20, 30, 40, 50]

// 1. 탐색 메서드 - lower / floor / ceiling / higher
set.lower(30);   // 20  (30 미만 최댓값)
set.lower(10);   // null (10 미만 없음)
set.lower(25);   // 20  (25 미만 최댓값)

set.floor(30);   // 30  (30 이하 최댓값 = 자신)
set.floor(25);   // 20  (25 이하 최댓값)
set.floor(5);    // null (5 이하 없음)

set.ceiling(30); // 30  (30 이상 최솟값 = 자신)
set.ceiling(25); // 30  (25 이상 최솟값)
set.ceiling(55); // null (55 이상 없음)

set.higher(30);  // 40  (30 초과 최솟값)
set.higher(50);  // null (50 초과 없음)
set.higher(25);  // 30  (25 초과 최솟값)

// 2. 경계값 포함 여부 비교
// e=30(정확히 존재): lower=20, floor=30, ceiling=30, higher=40
// e=25(사이값):      lower=20, floor=20, ceiling=30, higher=30

// 3. pollFirst() / pollLast() - 제거 후 반환 (빈 Set이면 null)
words.pollFirst(); // "apple" (최솟값 제거)
words.pollLast();  // "strawberry" (최댓값 제거)
new TreeSet<>().pollFirst(); // null (예외 없음)

// 4. headSet(toElement, inclusive) - 범위 뷰
ns.headSet(30, false); // [10, 20]     (30 미포함)
ns.headSet(30, true);  // [10, 20, 30] (30 포함)
ns.headSet(30);        // [10, 20]     (SortedSet 기본: 미포함)

// 5. tailSet(fromElement, inclusive) - 범위 뷰
ns.tailSet(30, false); // [40, 50]     (30 미포함)
ns.tailSet(30, true);  // [30, 40, 50] (30 포함)
ns.tailSet(30);        // [30, 40, 50] (SortedSet 기본: 포함)

// 6. subSet(from, fromInclusive, to, toInclusive) - 범위 뷰
ns.subSet(20, true,  40, true);  // [20, 30, 40]
ns.subSet(20, true,  40, false); // [20, 30]
ns.subSet(20, false, 40, true);  // [30, 40]
ns.subSet(20, false, 40, false); // [30]
ns.subSet(20, 40);               // [20, 30] (SortedSet 기본: from 포함, to 미포함)

// 7. 범위 뷰 - 원본과 연동
NavigableSet<Integer> view = src.subSet(3, true, 7, true); // [3, 4, 5, 6, 7]
view.remove(5);      // 뷰 수정 → 원본에도 반영
src.remove(4);       // 원본 수정 → 뷰에도 반영

// 8. descendingSet() - 역순 NavigableSet 뷰
NavigableSet<Integer> desc = asc.descendingSet();
// [50, 40, 30, 20, 10] - 원본의 역순 뷰

asc.add(60);          // 원본 수정 → desc에도 반영
desc.lower(40);       // 50  (내림차순 기준: 40보다 앞)
desc.higher(40);      // 30  (내림차순 기준: 40보다 뒤)
desc.pollFirst();     // 60  (내림차순 첫 번째 = 최댓값)
desc.pollLast();      // 10  (내림차순 마지막 = 최솟값)
```

- `lower` / `higher`는 기준값 자신을 포함하지 않고, `floor` / `ceiling`은 기준값 자신도 포함한다.
- 탐색 메서드는 조건에 맞는 요소가 없으면 예외 없이 `null`을 반환한다.
- `pollFirst()` / `pollLast()`도 빈 Set이면 `null`을 반환한다. `removeFirst()` / `removeLast()`(`SequencedSet`)은 비어 있으면 `NoSuchElementException`을 발생시키므로 구분해서 사용한다.
- `headSet(to)` / `tailSet(from)` / `subSet(from, to)`는 `SortedSet`에서 상속된 메서드로 경계 포함 여부가 고정되어 있다. `NavigableSet`의 `inclusive` 파라미터를 추가한 버전을 사용하면 경계를 자유롭게 지정할 수 있다.
- 범위 뷰는 원본의 **뷰(view)**이다. 뷰를 수정하면 원본에도 반영되고, 원본을 수정하면 뷰에도 반영된다. 범위 밖의 요소를 뷰에 추가하려 하면 `IllegalArgumentException`이 발생한다.
- `descendingSet()`에서도 `lower` / `higher` / `floor` / `ceiling`을 사용할 수 있다. 단, **내림차순 기준**으로 적용되므로 `lower(40)`은 "40보다 내림차순으로 앞에 있는 값(=더 큰 값)"을 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam09.App4
  ```

## App5 - NavigableMap 사용

```java
NavigableMap<Integer, String> map = new TreeMap<>();
// {10=ten, 20=twenty, 30=thirty, 40=forty, 50=fifty}

// 1. 키 반환 탐색 메서드
map.lowerKey(30);    // 20   (30 미만 최댓값 키)
map.lowerKey(10);    // null
map.floorKey(30);    // 30   (30 이하 최댓값 키 = 자신)
map.floorKey(25);    // 20
map.ceilingKey(30);  // 30   (30 이상 최솟값 키 = 자신)
map.ceilingKey(25);  // 30
map.higherKey(30);   // 40   (30 초과 최솟값 키)
map.higherKey(50);   // null

// 2. Entry 반환 탐색 메서드 - 키와 값을 함께 반환
map.lowerEntry(30);   // 20=twenty
map.floorEntry(30);   // 30=thirty
map.ceilingEntry(25); // 30=thirty
map.higherEntry(30);  // 40=forty

// 3. 경계값 포함 여부 비교
// k=30: lower=20, floor=30, ceiling=30, higher=40
// k=25: lower=20, floor=20, ceiling=30, higher=30

// 4. pollFirstEntry() / pollLastEntry() - 제거 후 반환 (빈 Map이면 null)
scores.pollFirstEntry(); // alice=90 (알파벳 첫 번째)
scores.pollLastEntry();  // eve=80   (알파벳 마지막)
new TreeMap<>().pollFirstEntry(); // null (예외 없음)

// 5. headMap(toKey, inclusive) - 범위 뷰
nm.headMap(30, false); // {10=ten, 20=twenty}           (30 미포함)
nm.headMap(30, true);  // {10=ten, 20=twenty, 30=thirty}(30 포함)
nm.headMap(30);        // {10=ten, 20=twenty}           (SortedMap 기본: 미포함)

// 6. tailMap(fromKey, inclusive) - 범위 뷰
nm.tailMap(30, false); // {40=forty, 50=fifty}          (30 미포함)
nm.tailMap(30, true);  // {30=thirty, 40=forty, 50=fifty}(30 포함)
nm.tailMap(30);        // {30=thirty, 40=forty, 50=fifty}(SortedMap 기본: 포함)

// 7. subMap(from, fromInclusive, to, toInclusive) - 범위 뷰
nm.subMap(20, true,  40, true);  // {20=twenty, 30=thirty, 40=forty}
nm.subMap(20, true,  40, false); // {20=twenty, 30=thirty}
nm.subMap(20, false, 40, true);  // {30=thirty, 40=forty}
nm.subMap(20, false, 40, false); // {30=thirty}
nm.subMap(20, 40);               // {20=twenty, 30=thirty} (SortedMap 기본)

// 8. 범위 뷰 - 원본과 연동
NavigableMap<Integer, String> view = src.subMap(30, true, 70, true);
view.put(45, "val4.5"); // 뷰 추가 → 원본에도 반영
src.remove(50);         // 원본 제거 → 뷰에도 반영
view.put(80, "x");      // 범위 밖 → IllegalArgumentException

// 9. descendingMap() - 역순 NavigableMap 뷰
NavigableMap<Integer, String> desc = asc.descendingMap();
// {50=fifty, 40=forty, 30=thirty, 20=twenty, 10=ten}

asc.put(60, "sixty");    // 원본 수정 → desc에도 반영
desc.lowerKey(40);       // 50  (내림차순 기준: 40보다 앞)
desc.higherKey(40);      // 30  (내림차순 기준: 40보다 뒤)
desc.pollFirstEntry();   // 60=sixty (내림차순 첫 번째)
desc.pollLastEntry();    // 10=ten   (내림차순 마지막)

// 10. navigableKeySet() / descendingKeySet()
gradeMap.navigableKeySet();  // [alice, bob, carol, dave]  (오름차순 NavigableSet)
gradeMap.descendingKeySet(); // [dave, carol, bob, alice]  (내림차순 NavigableSet)

// navigableKeySet()은 NavigableSet이므로 탐색 메서드 사용 가능
gradeMap.navigableKeySet().floor("c");  // "bob"  (c 이하 최대 키)
gradeMap.navigableKeySet().higher("b"); // "bob"  (b 초과 최소 키)

// NavigableSet vs NavigableMap 탐색 비교
gradeMap.floorKey("c");    // "bob"     (키만 반환)
gradeMap.floorEntry("c");  // bob=75    (키+값 반환)
```

- `NavigableMap`은 `NavigableSet`과 탐색 메서드 이름이 대응된다. 차이점은 탐색 결과가 **키 반환**(`lowerKey` 등)과 **Entry 반환**(`lowerEntry` 등) 두 버전으로 제공된다는 점이다.
- `pollFirstEntry()` / `pollLastEntry()`는 빈 Map이면 `null`을 반환한다. `SequencedMap`의 `firstEntry()` / `lastEntry()`도 빈 Map이면 `null`을 반환한다. 이 점에서 `NoSuchElementException`을 발생시키는 `SequencedCollection.getFirst()` / `getLast()`와 다르다.
- 범위 뷰(`headMap` / `tailMap` / `subMap`)는 원본의 뷰이다. 뷰 범위 밖의 키로 `put()`하면 `IllegalArgumentException`이 발생한다.
- `navigableKeySet()`은 `NavigableSet<K>`를 반환하므로 `lower` / `floor` / `ceiling` / `higher` 등 근접 탐색을 키 집합에 바로 적용할 수 있다.
- `descendingMap()`에서 `lowerKey(k)`는 내림차순 기준으로 `k`보다 앞에 있는 값, 즉 **더 큰 값**을 반환한다. `descendingSet()`의 `lower(e)`와 동일한 방향이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam09.App5
  ```
