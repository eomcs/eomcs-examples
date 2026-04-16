# Exam02 - Collection 인터페이스와 List, Set, Queue

## 개념

`Collection<E>`는 여러 객체(항목)를 하나로 묶어 관리하는 컬렉션 계층의 최상위 인터페이스이다.
`List`, `Set`, `Queue` 인터페이스가 `Collection`을 상속하며, `java.util` 패키지에 소속되어 있다.

### Collection 인터페이스 주요 메서드

| 메서드 | 설명 |
|---|---|
| `boolean add(E e)` | 항목 추가. 추가 성공 시 `true` 반환 |
| `boolean remove(Object o)` | 항목 제거. 제거 성공 시 `true` 반환 |
| `boolean contains(Object o)` | 항목 포함 여부 반환 |
| `int size()` | 항목 개수 반환 |
| `boolean isEmpty()` | 항목이 없으면 `true` 반환 |
| `void clear()` | 모든 항목 제거 |
| `Iterator<E> iterator()` | Iterator 객체 반환 (`Iterable` 상속) |
| `Object[] toArray()` | 항목을 배열로 변환하여 반환 |

### 하위 인터페이스

| 인터페이스 | 특징 | 주요 구현체 |
|---|---|---|
| `List<E>` | 순서 유지, 중복 허용, 인덱스로 접근 가능 | `ArrayList`, `LinkedList` |
| `Set<E>` | 순서 미보장, 중복 불허 | `HashSet`, `LinkedHashSet`, `TreeSet` |
| `Queue<E>` | FIFO(선입선출) 방식으로 항목을 꺼냄 | `LinkedList`, `ArrayDeque`, `PriorityQueue` |

## App - List 인터페이스 사용

```java
List<String> list = new ArrayList<>();

// add() - 순서 유지, 중복 허용
list.add("사과");
list.add("바나나");
list.add("딸기");
list.add("바나나"); // 중복 허용 → [사과, 바나나, 딸기, 바나나]

// get() / set() - 인덱스로 접근
list.get(1);              // "바나나"
list.set(1, "포도");      // 인덱스 1 교체. 이전 값("바나나") 반환

// add(index, element) - 특정 위치에 삽입
list.add(1, "망고");      // 인덱스 1에 삽입. 기존 항목은 뒤로 밀림

// remove(int index) - 인덱스로 제거
list.remove(0);           // 인덱스 0 제거. 제거된 항목 반환

// indexOf() / lastIndexOf()
list.indexOf("망고");     // 첫 번째 인덱스 반환. 없으면 -1
list.lastIndexOf("망고"); // 마지막 인덱스 반환. 없으면 -1

// subList() - 구간 추출
list.subList(1, 4);       // 인덱스 1 이상 4 미만의 부분 리스트 반환
```

- `List`는 `Collection`에 인덱스 기반 접근 메서드(`get`, `set`, `add(index)`, `remove(index)`)를 추가한 인터페이스이다.
- `remove(int index)`와 `remove(Object o)`는 오버로딩 관계이다. 정수를 넘길 때는 인덱스로 동작하고, 객체를 넘길 때는 값으로 첫 번째 일치 항목을 제거한다.
- `subList()`가 반환하는 리스트는 원본 리스트의 뷰(view)이므로, 원본을 수정하면 부분 리스트에도 반영된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam02.App
  ```

## App2 - Set 인터페이스 사용

```java
// HashSet - 순서 미보장, 중복 불허, O(1) 검색
Set<String> hashSet = new HashSet<>();
hashSet.add("바나나");
hashSet.add("사과");
hashSet.add("사과"); // 중복 → 무시. 크기: 2
hashSet.contains("사과"); // true
hashSet.remove("바나나");

// LinkedHashSet - 추가 순서 유지, 중복 불허
Set<String> linkedSet = new LinkedHashSet<>();
linkedSet.add("바나나");
linkedSet.add("사과");
linkedSet.add("딸기");
linkedSet.add("사과"); // 중복 → 무시
System.out.println(linkedSet); // [바나나, 사과, 딸기] - 추가 순서 유지

// TreeSet - 자연 순서(오름차순) 정렬, 중복 불허
Set<String> treeSet = new TreeSet<>();
treeSet.add("바나나");
treeSet.add("사과");
treeSet.add("딸기");
System.out.println(treeSet); // [딸기, 바나나, 사과] - 알파벳 오름차순

Set<Integer> numbers = new TreeSet<>();
numbers.add(30); numbers.add(10); numbers.add(20);
System.out.println(numbers); // [10, 20, 30]
```

- `Set`은 `Collection`을 상속하지만 인덱스 접근 메서드가 없다. `Collection`의 메서드만 사용한다.
- `HashSet`은 해시 기반으로 검색 속도가 O(1)이지만 순서를 보장하지 않는다.
- `LinkedHashSet`은 추가 순서를 유지해야 할 때 사용한다. `HashSet`보다 메모리를 약간 더 사용한다.
- `TreeSet`은 항목을 자연 순서(오름차순)로 정렬하여 저장한다. 검색 속도는 O(log n)이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam02.App2
  ```

## App3 - Queue 인터페이스 사용

```java
// LinkedList - FIFO Queue
Queue<String> queue = new LinkedList<>();
queue.offer("첫 번째");
queue.offer("두 번째");
queue.offer("세 번째");
queue.peek(); // "첫 번째" (제거하지 않고 반환)
queue.poll(); // "첫 번째" (꺼내고 제거)
queue.poll(); // 빈 큐이면 null 반환

// ArrayDeque - LinkedList보다 빠른 Queue 구현체
Queue<Integer> deque = new ArrayDeque<>();
deque.offer(100);
deque.offer(200);
deque.offer(300);
deque.peek(); // 100
deque.poll(); // 100 → deque: [200, 300]

// PriorityQueue - 우선순위(오름차순) 기반
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(30); pq.offer(10); pq.offer(20);
pq.peek(); // 10 (가장 작은 값)
pq.poll(); // 10
pq.poll(); // 20
pq.poll(); // 30
```

- `Queue`는 `Collection`에 `offer` / `poll` / `peek` 메서드를 추가한 인터페이스이다.
- `offer()`는 추가 실패 시 `false`를 반환하고, `add()`는 예외를 던진다. 큐 용량이 제한된 경우가 아니면 둘의 동작이 같다.
- `poll()`은 빈 큐에서 `null`을 반환하고, `remove()`는 `NoSuchElementException`을 던진다.
- `peek()`은 빈 큐에서 `null`을 반환하고, `element()`는 `NoSuchElementException`을 던진다.
- `PriorityQueue`는 추가 순서와 무관하게 자연 순서(오름차순)로 항목을 꺼낸다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam02.App3
  ```
