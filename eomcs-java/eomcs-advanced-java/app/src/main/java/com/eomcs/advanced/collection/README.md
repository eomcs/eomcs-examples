# Collection Framework

## 학습 목표

- `Iterable`과 `Iterator` 인터페이스의 역할을 이해하고, for-each 문이 동작하는 원리를 설명할 수 있다.
- `Collection`, `List`, `Set`, `Queue` 인터페이스의 계층 구조를 이해하고, 각 특성에 맞게 구현체를 선택할 수 있다.
- `ArrayList`와 `LinkedList`의 내부 구조 차이를 이해하고, 접근·삽입·삭제 성능을 비교하여 적합한 자료구조를 선택할 수 있다.
- `Vector`, `Stack`, `ArrayDeque`의 특성과 사용 방법을 이해하고, `Stack` 대신 `ArrayDeque`를 권장하는 이유를 설명할 수 있다.
- `Map` 인터페이스와 주요 구현체(`HashMap`, `Hashtable`, `Properties`, `TreeMap`, `LinkedHashMap`)의 특성과 차이를 이해하고 상황에 맞게 선택할 수 있다.
- `Comparable`과 `Comparator`를 구현하여 객체의 정렬 기준을 정의하고, 컬렉션과 배열에 적용할 수 있다.
- 동기화를 지원하는 컬렉션(`ConcurrentHashMap`, `CopyOnWriteArrayList` 등)의 필요성을 이해하고, 멀티스레드 환경에서 안전하게 활용할 수 있다.
- 수정 불가 컬렉션(Unmodifiable Collection)을 생성하는 방법을 이해하고, 방어적 프로그래밍에 적용할 수 있다.
- Java 21에서 도입된 `SequencedCollection` 인터페이스를 이해하고, 순서가 있는 컬렉션을 일관된 방식으로 다룰 수 있다.

---

## Exam01 - Iterable과 Iterator

### 개념

컬렉션의 항목을 순서대로 꺼내는 방법을 표준화하기 위해 두 가지 인터페이스를 사용한다.

| 인터페이스 | 패키지 | 메서드 | 설명 |
|---|---|---|---|
| `Iterable<T>` | `java.lang` | `Iterator<T> iterator()` | for-each 문 사용 가능 조건 |
| `Iterator<T>` | `java.util` | `boolean hasNext()` | 꺼낼 항목이 남아있으면 `true` 반환 |
| | | `T next()` | 다음 항목을 반환하고 커서를 이동 |
| | | `void remove()` | `next()`로 마지막 반환한 항목을 제거 (선택적 구현) |

- `Iterable<T>`는 `java.lang` 패키지에 소속되어 있어 `import` 없이 사용할 수 있다.
- 클래스가 `Iterable<T>`를 구현하면 for-each 문(향상된 for 문)으로 순회할 수 있다.
- for-each 문은 컴파일러가 내부적으로 `iterator()` / `hasNext()` / `next()` 호출하는 while 문으로 변환한다.

#### Iterator 디자인 패턴

Iterator 패턴은 컬렉션에서 항목을 꺼내는 방법을 표준화하는 디자인 패턴이다.

- **내부 구조 은닉**: 사용자는 컬렉션이 배열인지, 연결 리스트인지 알 필요 없이 동일한 방법(`hasNext()` / `next()`)으로 순회할 수 있다.
- **역할 분리**: `Iterable`은 "Iterator를 제공하는 컬렉션"의 역할을, `Iterator`는 "현재 순회 위치(커서)를 관리하는 객체"의 역할을 각각 담당한다.
- **독립적인 커서**: `iterator()`를 호출할 때마다 새로운 `Iterator` 객체가 생성되므로, 같은 컬렉션을 여러 곳에서 동시에 독립적으로 순회할 수 있다.

### App - Iterable과 Iterator 직접 구현

```java
class IntList implements Iterable<Integer> {

  private int[] data;
  private int size;

  public IntList(int capacity) {
    data = new int[capacity];
  }

  public void add(int value) {
    if (size < data.length) {
      data[size++] = value;
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < size;
      }

      @Override
      public Integer next() {
        return data[cursor++];
      }
    };
  }
}

public static void main(String[] args) {
  IntList list = new IntList(5);
  list.add(10);
  list.add(20);
  list.add(30);
  list.add(40);
  list.add(50);

  // Iterator를 직접 사용하여 순회
  Iterator<Integer> it = list.iterator();
  while (it.hasNext()) {
    int value = it.next();
    System.out.println(value);
  }

  // for-each 문으로 순회 (컴파일러가 위의 while 문으로 변환)
  for (int value : list) {
    System.out.println(value);
  }
}
```

- `IntList`는 `Iterable<Integer>`를 구현하므로 for-each 문으로 순회할 수 있다.
- `iterator()` 메서드는 익명 클래스로 `Iterator<Integer>`를 구현하여 반환한다.
- `cursor` 필드로 현재 순회 위치를 추적하며, `hasNext()`는 남은 항목 여부를, `next()`는 현재 항목을 반환한 뒤 커서를 이동한다.
- for-each 문은 `Iterator`를 직접 사용하는 while 문과 동일하게 동작한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam01.App
  ```

---

## Exam02 - Collection 인터페이스와 List, Set, Queue

### 개념

`Collection<E>`는 여러 객체(항목)를 하나로 묶어 관리하는 컬렉션 계층의 최상위 인터페이스이다.
`List`, `Set`, `Queue` 인터페이스가 `Collection`을 상속하며, `java.util` 패키지에 소속되어 있다.

#### Collection 인터페이스 주요 메서드

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

#### 하위 인터페이스

| 인터페이스 | 특징 | 주요 구현체 |
|---|---|---|
| `List<E>` | 순서 유지, 중복 허용, 인덱스로 접근 가능 | `ArrayList`, `LinkedList` |
| `Set<E>` | 순서 미보장, 중복 불허 | `HashSet`, `LinkedHashSet`, `TreeSet` |
| `Queue<E>` | FIFO(선입선출) 방식으로 항목을 꺼냄 | `LinkedList`, `ArrayDeque`, `PriorityQueue` |

### App - List 인터페이스 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam02.App
  ```

### App2 - Set 인터페이스 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam02.App2
  ```

### App3 - Queue 인터페이스 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam02.App3
  ```

---

## Exam03 - ArrayList와 LinkedList 비교

### 개념

`ArrayList`와 `LinkedList`는 모두 `List` 인터페이스를 구현하므로 사용 방법은 동일하지만, 내부 자료구조가 달라 연산별 성능 특성이 다르다.

| 구분 | `ArrayList` | `LinkedList` |
|---|---|---|
| 내부 구조 | 동적 배열(`Object[]`) | 이중 연결 리스트(doubly linked list) |
| 인덱스 접근 | O(1) — 배열 인덱스로 바로 접근 | O(n) — 앞에서부터 노드를 순회 |
| 끝에 추가 | O(1)* | O(1) |
| 중간 삽입/삭제 | O(n) — 뒤쪽 항목을 전부 이동 | O(n)** — 위치 탐색 후 참조만 변경 |
| 메모리 | 효율적 | 노드마다 이전/다음 참조 오버헤드 있음 |
| 추가 구현 인터페이스 | — | `Queue`, `Deque` |

\* 배열 확장이 필요하면 O(n), 평균적으로 O(1)  
\*\* 위치를 찾는 데 O(n), 참조 변경 자체는 O(1)

#### 구현체 선택 기준

- **`ArrayList`**: 인덱스 조회가 빈번하거나, 항목을 주로 끝에 추가/삭제하는 대부분의 일반적인 상황
- **`LinkedList`**: 맨 앞/중간 삽입·삭제가 매우 빈번하거나, `Queue`/`Deque`로 사용할 때

### App - 동작 방식 비교

```java
List<String> arrayList  = new ArrayList<>();
List<String> linkedList = new LinkedList<>();

// 기본 사용법은 동일 (List 인터페이스를 공통으로 구현)
arrayList.add("사과");  arrayList.add("바나나");  arrayList.add("딸기");
linkedList.add("사과"); linkedList.add("바나나"); linkedList.add("딸기");

// 인덱스 접근 - ArrayList는 O(1), LinkedList는 O(n)
arrayList.get(1);   // 배열 인덱스로 바로 반환
linkedList.get(1);  // head 노드부터 노드를 하나씩 따라감

// 중간 삽입 - 동작 방식이 다름
arrayList.add(1, "망고");   // 인덱스 1 이후 항목을 전부 오른쪽으로 이동
linkedList.add(1, "망고");  // 이전/다음 노드의 참조만 변경

// 중간 삭제 - 동작 방식이 다름
arrayList.remove(1);   // 인덱스 1 이후 항목을 전부 왼쪽으로 이동
linkedList.remove(1);  // 이전/다음 노드의 참조만 변경

// LinkedList는 Deque로도 사용 가능
LinkedList<String> deque = new LinkedList<>();
deque.addFirst("앞에 추가");
deque.addLast("뒤에 추가");
deque.peekFirst(); // 맨 앞 항목 조회 (제거 안 함)
deque.peekLast();  // 맨 뒤 항목 조회 (제거 안 함)
deque.pollFirst(); // 맨 앞 항목을 꺼내고 제거
```

- `ArrayList`와 `LinkedList`는 `List` 인터페이스를 공통으로 구현하므로 선언 타입을 `List`로 맞추면 구현체를 교체해도 코드를 수정할 필요가 없다.
- 중간 삽입/삭제는 두 구현체 모두 O(n)이지만, `ArrayList`는 배열 이동(메모리 복사) 비용이 발생하고 `LinkedList`는 위치 탐색 비용이 발생한다.
- `LinkedList`는 `Deque` 인터페이스를 구현하므로 `addFirst` / `addLast` / `pollFirst` / `pollLast` 등 양쪽 끝 조작 메서드를 사용할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam03.App
  ```

### App2 - 성능 비교

```java
static final int COUNT = 100_000;

List<Integer> arrayList  = new ArrayList<>();
List<Integer> linkedList = new LinkedList<>();

// 끝에 추가: 두 구현체 모두 빠름
for (int i = 0; i < COUNT; i++) arrayList.add(i);
for (int i = 0; i < COUNT; i++) linkedList.add(i);

// 인덱스 접근: ArrayList가 압도적으로 빠름
for (int i = 0; i < COUNT; i++) arrayList.get(i);   // O(1) × COUNT
for (int i = 0; i < COUNT; i++) linkedList.get(i);  // O(n) × COUNT

// 맨 앞 삽입 (1,000회): LinkedList가 훨씬 빠름
for (int i = 0; i < 1_000; i++) arrayList.add(0, i);   // 매번 전체 항목 이동
for (int i = 0; i < 1_000; i++) linkedList.add(0, i);  // head 참조만 변경

// 맨 앞 삭제 (1,000회): LinkedList가 훨씬 빠름
for (int i = 0; i < 1_000; i++) arrayList.remove(0);   // 매번 전체 항목 이동
for (int i = 0; i < 1_000; i++) linkedList.remove(0);  // head 참조만 변경
```

- **인덱스 접근**: `ArrayList`가 압도적으로 빠르다. `LinkedList`는 `get(i)` 호출마다 head부터 i번 노드를 순회하므로 항목 수가 늘어날수록 급격히 느려진다.
- **끝에 추가**: 두 구현체의 차이가 거의 없다. `ArrayList`는 배열이 꽉 찰 때만 확장 비용이 발생한다.
- **맨 앞 삽입/삭제**: `LinkedList`가 압도적으로 빠르다. `ArrayList`는 매번 모든 항목을 한 칸씩 이동해야 하지만, `LinkedList`는 head 노드의 참조만 변경하면 된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam03.App2
  ```

---

## Exam04 - Vector와 Stack, ArrayDeque

### 개념

`Vector`와 `Stack`은 Java 1.0부터 제공된 레거시 컬렉션 클래스이다.
현재는 하위 호환성을 위해 유지되고 있으며, 신규 코드에서는 각각 `ArrayList`와 `ArrayDeque`로 대체하는 것을 권장한다.

#### Vector

| 구분 | `Vector` | `ArrayList` |
|---|---|---|
| 도입 시기 | Java 1.0 | Java 1.2 |
| 내부 구조 | 동적 배열(`Object[]`) | 동적 배열(`Object[]`) |
| 동기화 | 모든 메서드 `synchronized` | 동기화 없음 |
| 단일 스레드 성능 | 느림 (동기화 비용) | 빠름 |
| 배열 확장 비율 | 현재 크기의 2배 | 현재 크기의 1.5배 |
| 레거시 메서드 | `addElement`, `elementAt`, `removeElement` 등 | 없음 |

#### Stack

| 구분 | 설명 |
|---|---|
| 상속 구조 | `Iterable` → `Collection` → `List` → `Vector` → `Stack` |
| 동작 방식 | LIFO(Last-In-First-Out, 후입선출) |
| 문제점 | `Vector` 상속으로 스택 의도에 맞지 않는 인덱스 접근 메서드까지 노출 |
| 권장 대안 | `ArrayDeque` (`Deque` 인터페이스 구현) |

#### ArrayDeque

| 구분 | 설명 |
|---|---|
| 도입 시기 | Java 1.6 |
| 내부 구조 | 순환 배열(circular array) |
| 동기화 | 없음 (단일 스레드에서 빠름) |
| `null` 저장 | 불허 (`NullPointerException`) |
| 스택 대안 | `Stack` 대신 사용 권장 |
| 큐 대안 | `LinkedList` 대신 사용 권장 |

#### Deque 인터페이스 주요 메서드

| 메서드 | 설명 |
|---|---|
| `void push(E e)` | 맨 앞에 추가. `addFirst()`와 동일. 스택의 push |
| `E pop()` | 맨 앞에서 꺼내고 제거. 비어있으면 `NoSuchElementException` |
| `E peek()` | 맨 앞 항목 조회. 비어있으면 `null` |
| `void addFirst(E e)` | 맨 앞에 추가 |
| `void addLast(E e)` | 맨 뒤에 추가. `add()` / `offer()`와 동일 |
| `E pollFirst()` | 맨 앞에서 꺼내고 제거. 비어있으면 `null` |
| `E pollLast()` | 맨 뒤에서 꺼내고 제거. 비어있으면 `null` |
| `E peekFirst()` | 맨 앞 항목 조회. 비어있으면 `null` |
| `E peekLast()` | 맨 뒤 항목 조회. 비어있으면 `null` |

#### Stack 고유 메서드

| 메서드 | 설명 |
|---|---|
| `E push(E item)` | 항목을 스택의 맨 위에 추가하고 반환 |
| `E pop()` | 맨 위 항목을 꺼내고 제거. 비어있으면 `EmptyStackException` |
| `E peek()` | 맨 위 항목을 제거하지 않고 반환. 비어있으면 `EmptyStackException` |
| `boolean empty()` | 스택이 비어있으면 `true` 반환 |
| `int search(Object o)` | 맨 위에서부터의 거리(1-based) 반환. 없으면 `-1` |

### App - Vector 사용

```java
// 기본 사용법 - ArrayList와 동일
Vector<String> vector = new Vector<>();
vector.add("사과");
vector.add("바나나");
vector.add("딸기");

vector.get(1);          // "바나나"
vector.set(1, "포도");  // 인덱스 1 교체

// 레거시 메서드 (사용 비권장)
vector.addElement("망고");          // add()와 동일
vector.elementAt(0);               // get()과 동일
vector.firstElement();             // get(0)과 동일
vector.lastElement();              // get(size-1)과 동일
vector.removeElement("망고");       // remove(Object)와 동일

// 초기 용량 지정 - 꽉 차면 2배로 확장
Vector<Integer> v2 = new Vector<>(5); // 초기 용량 5
for (int i = 1; i <= 5; i++) v2.add(i * 10);
v2.add(60);              // 용량 초과 → 내부 배열을 2배(10)로 확장
v2.capacity();           // 10

// List 타입으로 선언하면 ArrayList와 교체 가능
List<String> list = new Vector<>();
```

- `Vector`는 `List` 인터페이스를 구현하므로 `ArrayList`와 사용 방법이 동일하다. 선언 타입을 `List`로 맞추면 구현체를 교체해도 코드를 수정할 필요가 없다.
- `addElement` / `elementAt` / `removeElement` 등의 레거시 메서드는 `List` 인터페이스 메서드와 기능이 같으므로 사용하지 않는다.
- 용량이 꽉 차면 현재 크기의 2배로 배열을 확장한다. `ArrayList`(1.5배)보다 확장 비율이 커서 메모리 낭비가 더 클 수 있다.
- 멀티스레드 환경에서 스레드 안전한 리스트가 필요하다면 `Vector` 대신 `Collections.synchronizedList()` 또는 `CopyOnWriteArrayList`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam04.App
  ```

### App2 - Stack 사용

```java
Stack<String> stack = new Stack<>();

// push() / pop() / peek() - LIFO 동작
stack.push("첫 번째");
stack.push("두 번째");
stack.push("세 번째");  // top

stack.peek(); // "세 번째" (제거하지 않고 반환)
stack.pop();  // "세 번째" (꺼내고 제거)
stack.pop();  // "두 번째"
stack.pop();  // "첫 번째"

// empty() / EmptyStackException
stack.empty(); // true
stack.pop();   // 빈 스택 → EmptyStackException 발생

// search() - 맨 위에서부터 1-based 거리
stack.push("A"); stack.push("B"); stack.push("C"); stack.push("B");
// 스택 상태 (오른쪽이 top): [A, B, C, B]
stack.search("B"); // 1 (맨 위의 B)
stack.search("C"); // 2
stack.search("A"); // 4
stack.search("Z"); // -1 (없음)

// 권장 대안: ArrayDeque를 스택으로 사용
Deque<String> dequeStack = new ArrayDeque<>();
dequeStack.push("첫 번째"); // addFirst()와 동일
dequeStack.push("두 번째");
dequeStack.push("세 번째");
dequeStack.peek(); // "세 번째"
dequeStack.pop();  // "세 번째"
```

- `Stack`은 `Vector`를 상속하므로 `get(index)` 같은 인덱스 접근 메서드도 노출된다. 스택의 의도(맨 위에서만 꺼내기)에 맞지 않으므로 사용하지 않아야 한다.
- `pop()` / `peek()`은 빈 스택에서 `EmptyStackException`을 던진다. `ArrayDeque`의 `poll()` / `peek()`는 `null`을 반환한다.
- `search()`는 맨 위에서부터 1-based 거리를 반환한다. 중복 항목이 있으면 가장 위에 있는 항목의 거리를 반환한다.
- Java 공식 문서는 스택이 필요할 때 `Stack` 대신 `ArrayDeque` 사용을 권장한다. `ArrayDeque`는 동기화 비용이 없어 더 빠르고, `Deque` 인터페이스를 통해 스택에 적합한 메서드만 노출된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam04.App2
  ```

### App3 - ArrayDeque 사용

```java
// 1. 스택(LIFO)으로 사용 - Stack 클래스의 권장 대안
Deque<String> stack = new ArrayDeque<>();
stack.push("첫 번째"); // addFirst()와 동일 - 맨 앞에 추가
stack.push("두 번째");
stack.push("세 번째"); // [세 번째, 두 번째, 첫 번째]

stack.peek(); // "세 번째" (제거 안 함)
stack.pop();  // "세 번째"
stack.pop();  // "두 번째"
stack.pop();  // "첫 번째"
stack.peek(); // null (빈 덱 - 예외 없음)

// 2. 큐(FIFO)로 사용 - LinkedList보다 빠른 Queue 구현체
Deque<String> queue = new ArrayDeque<>();
queue.addLast("첫 번째"); // offer()와 동일 - 맨 뒤에 추가
queue.addLast("두 번째");
queue.addLast("세 번째"); // [첫 번째, 두 번째, 세 번째]

queue.peekFirst();  // "첫 번째" (제거 안 함)
queue.pollFirst();  // "첫 번째"
queue.pollFirst();  // "두 번째"
queue.pollFirst();  // null (빈 덱 - 예외 없음)

// 3. 양방향 덱(Deque)으로 사용
Deque<Integer> deque = new ArrayDeque<>();
deque.addFirst(1); // [1]
deque.addLast(2);  // [1, 2]
deque.addFirst(0); // [0, 1, 2]
deque.addLast(3);  // [0, 1, 2, 3]

deque.peekFirst(); // 0
deque.peekLast();  // 3
deque.pollFirst(); // 0 → [1, 2, 3]
deque.pollLast();  // 3 → [1, 2]

// 4. null 저장 불가
Deque<String> d = new ArrayDeque<>();
d.push(null); // NullPointerException 발생
```

- `ArrayDeque`는 내부적으로 순환 배열(circular array)을 사용하므로 양쪽 끝에서의 추가·삭제가 모두 O(1)이다.
- 스택으로 사용할 때는 `push` / `pop` / `peek`을, 큐로 사용할 때는 `addLast` / `pollFirst`(또는 `offer` / `poll`)를 사용한다.
- `peek()` / `pollFirst()` / `pollLast()`는 빈 덱에서 `null`을 반환한다. 예외를 받으려면 `removeFirst()` / `removeLast()`를 사용한다.
- `null`을 저장할 수 없다. `null` 추가 시 `NullPointerException`이 발생한다.
- `Stack`(레거시) 대신 스택으로, `LinkedList` 대신 큐로 사용하는 것을 권장한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam04.App3
  ```

---

## Exam05 - Map 인터페이스와 HashMap, Hashtable, Properties, TreeMap, LinkedHashMap 클래스

### 개념

`Map<K,V>`는 키(key)와 값(value)의 쌍으로 항목을 관리하는 인터페이스이다.
`Collection` 계층과 독립된 별도 계층이며, 키는 중복될 수 없다.

#### Map 구현체 비교

| 구현체 | 도입 | 순서 | 동기화 | `null` 키 | `null` 값 | 검색 성능 |
|---|---|---|---|---|---|---|
| `HashMap` | Java 1.2 | 미보장 | 없음 | 허용 | 허용 | O(1) |
| `Hashtable` | Java 1.0 | 미보장 | 전체 메서드 | 불허 | 불허 | O(1) |
| `LinkedHashMap` | Java 1.4 | 추가 순서 유지 (기본) / 접근 순서 (`accessOrder=true`) | 없음 | 허용 | 허용 | O(1) |
| `TreeMap` | Java 1.2 | 키 기준 정렬 | 없음 | 불허 | 허용 | O(log n) |
| `Properties` | Java 1.0 | 미보장 | 전체 메서드 | 불허 | 불허 | O(1) |

#### Map 인터페이스 주요 메서드

| 메서드 | 설명 |
|---|---|
| `V put(K key, V value)` | 키-값 쌍 추가. 기존 키면 값 교체 후 이전 값 반환 |
| `V get(Object key)` | 키에 대응하는 값 반환. 없으면 `null` |
| `V getOrDefault(Object key, V d)` | 키가 없으면 기본값 `d` 반환 |
| `V putIfAbsent(K key, V value)` | 키가 없을 때만 추가. 기존 값 반환(없으면 `null`) |
| `V remove(Object key)` | 키-값 쌍 제거. 제거된 값 반환 |
| `boolean containsKey(Object key)` | 키 존재 여부 반환 |
| `boolean containsValue(Object value)` | 값 존재 여부 반환 |
| `int size()` | 항목 개수 반환 |
| `boolean isEmpty()` | 항목이 없으면 `true` 반환 |
| `void clear()` | 모든 항목 제거 |
| `Set<K> keySet()` | 모든 키를 `Set`으로 반환 |
| `Collection<V> values()` | 모든 값을 `Collection`으로 반환 |
| `Set<Map.Entry<K,V>> entrySet()` | 모든 키-값 쌍을 `Entry` Set으로 반환 |

#### TreeMap 추가 메서드 (SortedMap / NavigableMap)

| 메서드 | 설명 |
|---|---|
| `K firstKey()` | 가장 작은 키 반환 |
| `K lastKey()` | 가장 큰 키 반환 |
| `Map.Entry<K,V> firstEntry()` | 가장 작은 키의 Entry 반환 |
| `Map.Entry<K,V> lastEntry()` | 가장 큰 키의 Entry 반환 |
| `Map.Entry<K,V> floorEntry(K key)` | `key` 이하의 가장 큰 키의 Entry 반환 |
| `Map.Entry<K,V> ceilingEntry(K key)` | `key` 이상의 가장 작은 키의 Entry 반환 |
| `SortedMap<K,V> headMap(K toKey)` | `toKey` 미만의 항목 반환 |
| `SortedMap<K,V> tailMap(K fromKey)` | `fromKey` 이상의 항목 반환 |
| `SortedMap<K,V> subMap(K from, K to)` | `from` 이상 `to` 미만의 항목 반환 |
| `NavigableMap<K,V> descendingMap()` | 역순으로 정렬된 Map 반환 |

#### LinkedHashMap 동작 모드

`LinkedHashMap`은 `HashMap`을 상속하면서 내부적으로 이중 연결 리스트를 추가로 유지하여 항목 순서를 보장한다.

| 모드 | 생성 방법 | 동작 |
|---|---|---|
| 추가 순서 (기본) | `new LinkedHashMap<>()` | 항목을 추가한 순서를 유지. 중복 키로 `put()`하면 값만 교체되고 순서는 유지. `remove()` 후 재추가하면 맨 뒤로 이동 |
| 접근 순서 | `new LinkedHashMap<>(capacity, loadFactor, true)` | `get()` / `put()`으로 접근한 항목이 맨 뒤로 이동. 맨 앞 항목이 가장 오래 전에 접근된 항목 |

`removeEldestEntry()`를 재정의하면 항목 수가 일정 크기를 초과할 때 가장 오래된 항목을 자동으로 제거한다.
접근 순서 모드와 결합하면 LRU(Least Recently Used) 캐시를 간단히 구현할 수 있다.

```java
final int CAPACITY = 3;
LinkedHashMap<String, Integer> lruCache =
    new LinkedHashMap<>(CAPACITY, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > CAPACITY; // 최대 용량 초과 시 맨 앞(가장 오래된) 항목 자동 제거
      }
    };
```

#### Properties 주요 메서드

| 메서드 | 설명 |
|---|---|
| `Object setProperty(String key, String value)` | 키-값 설정. 이전 값 반환 |
| `String getProperty(String key)` | 키에 대응하는 값 반환. 없으면 `null` |
| `String getProperty(String key, String d)` | 키가 없으면 기본값 `d` 반환 |
| `Set<String> stringPropertyNames()` | 모든 키를 `Set<String>`으로 반환 |
| `void store(OutputStream out, String comment)` | `.properties` 형식으로 저장 |
| `void load(InputStream in)` | `.properties` 파일 로드 |

### App - Map 인터페이스와 HashMap 사용

```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 1000);
map.put("banana", 500);
map.put("strawberry", 800);

map.get("apple");                  // 1000
map.get("grape");                  // null (없는 키)
map.getOrDefault("grape", 0);      // 0 (기본값)

// 중복 키 - 값 교체, 이전 값 반환
Integer old = map.put("apple", 1200); // old == 1000

// putIfAbsent - 키가 없을 때만 추가
map.putIfAbsent("apple", 9999); // 이미 있으므로 무시
map.putIfAbsent("mango", 700);  // 없으므로 추가

map.containsKey("banana");  // true
map.containsValue(500);     // true
map.remove("banana");       // 제거 후 제거된 값(500) 반환

// keySet(): 키만 순회
for (String key : map.keySet()) {
  System.out.println(key + " → " + map.get(key));
}

// values(): 값만 순회
for (int value : map.values()) {
  System.out.println(value);
}

// entrySet(): 키-값 쌍 순회 (가장 효율적)
for (Map.Entry<String, Integer> entry : map.entrySet()) {
  System.out.println(entry.getKey() + " → " + entry.getValue());
}

// null 키/값 모두 허용
map.put(null, 0);
map.put("empty", null);
```

- `HashMap`은 해시 테이블 기반으로 O(1) 검색을 제공하지만 항목 순서를 보장하지 않는다.
- 키의 `hashCode()`와 `equals()`를 이용해 저장·검색하므로, 커스텀 객체를 키로 사용할 때는 두 메서드를 반드시 재정의해야 한다.
- `keySet()`으로 순회할 때 `get(key)`를 추가 호출하면 조회가 두 번 발생한다. `entrySet()`을 사용하면 한 번에 꺼낼 수 있어 더 효율적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App
  ```

### App2 - Hashtable 사용

```java
Hashtable<String, Integer> table = new Hashtable<>();
table.put("apple", 1000);
table.put("banana", 500);

// null 키/값 모두 불허
table.put(null, 0);       // NullPointerException
table.put("key", null);   // NullPointerException

// 레거시 메서드 (사용 비권장)
table.contains(500);              // containsValue()와 동일
table.keys().nextElement();       // keySet()과 유사
table.elements().nextElement();   // values()와 유사

// Map 타입으로 선언하면 HashMap과 교체 가능
Map<String, Integer> hashMap   = new HashMap<>();
Map<String, Integer> hashTable = new Hashtable<>();
hashMap.put(null, 0);    // HashMap: 허용
hashTable.put(null, 0);  // Hashtable: NullPointerException
```

- `Hashtable`은 모든 메서드가 `synchronized`되어 있어 멀티스레드 환경에서 안전하지만, 단일 스레드에서는 동기화 비용으로 `HashMap`보다 느리다.
- `contains()` / `keys()` / `elements()` 등의 레거시 메서드는 `containsValue()` / `keySet()` / `values()`와 동일하므로 사용하지 않는다.
- 멀티스레드 환경에서는 `Hashtable` 대신 `ConcurrentHashMap`을 사용한다. `ConcurrentHashMap`은 전체를 잠그지 않고 버킷 단위로 잠그므로 더 효율적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App2
  ```

### App3 - Properties 사용

```java
Properties props = new Properties();
props.setProperty("db.host", "localhost");
props.setProperty("db.port", "3306");
props.setProperty("db.name", "mydb");

props.getProperty("db.host");           // "localhost"
props.getProperty("db.timeout", "30"); // 없는 키 → 기본값 "30"

// 모든 키 순회
for (String key : props.stringPropertyNames()) {
  System.out.println(key + " = " + props.getProperty(key));
}

// .properties 파일로 저장
try (FileOutputStream fos = new FileOutputStream("db.properties")) {
  props.store(fos, "Database Configuration"); // 두 번째 인수는 주석
}

// .properties 파일에서 로드
Properties loaded = new Properties();
try (FileInputStream fis = new FileInputStream("db.properties")) {
  loaded.load(fis);
}

// 기본값 Properties 상속
Properties defaults = new Properties();
defaults.setProperty("timeout", "30");
defaults.setProperty("encoding", "UTF-8");

Properties config = new Properties(defaults); // defaults를 기본값으로 사용
config.setProperty("timeout", "60");          // defaults 값 재정의
config.getProperty("timeout");   // "60" (재정의된 값)
config.getProperty("encoding");  // "UTF-8" (defaults에서 상속)

// 시스템 프로퍼티
Properties sysProps = System.getProperties();
sysProps.getProperty("os.name");       // "Mac OS X"
sysProps.getProperty("java.version");  // "21.0.8"
sysProps.getProperty("user.home");     // 사용자 홈 디렉터리
```

- `Properties`는 `Hashtable`을 상속하므로 키와 값으로 `null`을 허용하지 않는다.
- `setProperty` / `getProperty`는 `String` 타입만 다루므로, `put` / `get`(Object 타입) 대신 이 메서드를 사용한다.
- 생성자에 다른 `Properties`를 전달하면 기본값 체계를 구성할 수 있다. 키를 찾지 못하면 기본값 `Properties`에서 재검색한다.
- `store()`로 저장된 파일에는 `#` 주석과 타임스탬프가 자동으로 추가된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App3
  ```

### App4 - TreeMap 사용

```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("banana", 500);
map.put("apple", 1000);
map.put("mango", 700);
map.put("strawberry", 800);
map.put("cherry", 300);
// 키 알파벳 오름차순: {apple=1000, banana=500, cherry=300, mango=700, strawberry=800}

map.firstKey();   // "apple"
map.lastKey();    // "strawberry"
map.firstEntry(); // apple=1000
map.lastEntry();  // strawberry=800

// floorEntry(key): key 이하의 가장 큰 키의 Entry
map.floorEntry("c");  // banana=500 ("c" 이하: apple, banana → 최대 banana)
map.floorEntry("b");  // apple=1000 ("b" 이하: apple → 최대 apple)

// ceilingEntry(key): key 이상의 가장 작은 키의 Entry
map.ceilingEntry("c"); // cherry=300 ("c" 이상: cherry, mango, ... → 최소 cherry)
map.ceilingEntry("d"); // mango=700  ("d" 이상: mango, strawberry → 최소 mango)

// 범위 뷰 (원본의 뷰이므로 원본 수정 시 함께 반영)
map.headMap("mango");          // mango 미만:  {apple, banana, cherry}
map.tailMap("mango");          // mango 이상:  {mango, strawberry}
map.subMap("cherry", "mango"); // cherry 이상 mango 미만: {cherry}

map.descendingMap(); // 역순: {strawberry=800, mango=700, cherry=300, banana=500, apple=1000}

// 정수 키 - 자연 순서(오름차순)
TreeMap<Integer, String> scoreMap = new TreeMap<>();
scoreMap.put(85, "홍길동");
scoreMap.put(92, "임꺽정");
scoreMap.put(78, "유관순");
scoreMap.put(95, "이순신");
// {78=유관순, 85=홍길동, 92=임꺽정, 95=이순신}
scoreMap.firstEntry(); // 78=유관순 (최저점)
scoreMap.lastEntry();  // 95=이순신 (최고점)

// null 키 불허
map.put(null, 0); // NullPointerException
```

- `TreeMap`은 레드-블랙 트리(Red-Black Tree) 기반으로, 항목을 추가할 때마다 키를 자연 순서로 정렬하여 저장한다.
- 검색·삽입·삭제 모두 O(log n)이므로 `HashMap`(O(1))보다 느리다. 정렬된 Map이 필요할 때만 사용한다.
- `floorEntry(key)`는 "key 이하의 최대 키", `ceilingEntry(key)`는 "key 이상의 최소 키"를 반환한다. 문자열 비교는 사전순(lexicographic)이므로 `"b" < "banana"`, `"c" < "cherry"`임에 주의한다.
- `headMap` / `tailMap` / `subMap`이 반환하는 Map은 원본의 뷰(view)이다. 원본을 수정하면 뷰에도 반영된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App4
  ```

### App5 - LinkedHashMap 사용

```java
// 1. 추가 순서 유지 (기본 동작)
Map<String, Integer> linked = new LinkedHashMap<>();
linked.put("banana", 500);
linked.put("apple", 1000);
linked.put("mango", 700);
linked.put("strawberry", 800);
System.out.println(linked); // {banana=500, apple=1000, mango=700, strawberry=800} - 추가 순서 유지

// 중복 키 - 값만 교체, 순서 유지
linked.put("apple", 1200); // apple은 여전히 두 번째 위치
// remove 후 재추가 - 맨 뒤로 이동
linked.remove("apple");
linked.put("apple", 1000); // apple이 맨 뒤로 이동

// 2. 접근 순서 모드 (accessOrder = true)
// 생성자: LinkedHashMap(initialCapacity, loadFactor, accessOrder)
LinkedHashMap<String, Integer> accessMap =
    new LinkedHashMap<>(16, 0.75f, true);
accessMap.put("A", 1);
accessMap.put("B", 2);
accessMap.put("C", 3);
accessMap.put("D", 4);
// 초기 상태: {A=1, B=2, C=3, D=4}

accessMap.get("B");     // B 접근 → 맨 뒤로 이동: {A, C, D, B}
accessMap.get("A");     // A 접근 → 맨 뒤로 이동: {C, D, B, A}
accessMap.put("C", 30); // C 접근 → 맨 뒤로 이동: {D, B, A, C}

// 3. LRU 캐시 구현 - accessOrder + removeEldestEntry() 재정의
final int CAPACITY = 3;
LinkedHashMap<String, Integer> lruCache =
    new LinkedHashMap<>(CAPACITY, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > CAPACITY; // 최대 용량 초과 시 가장 오래된 항목 자동 제거
      }
    };

lruCache.put("page1", 1);
lruCache.put("page2", 2);
lruCache.put("page3", 3);
// {page1, page2, page3}

lruCache.get("page1");    // page1 최근 접근 → 맨 뒤로: {page2, page3, page1}
lruCache.put("page4", 4); // 용량 초과 → 가장 오래된 page2 제거: {page3, page1, page4}
lruCache.put("page5", 5); // 용량 초과 → 가장 오래된 page3 제거: {page1, page4, page5}
```

- `LinkedHashMap`은 `HashMap`을 상속하며, 내부적으로 이중 연결 리스트를 추가로 유지하여 순서를 보장한다.
- 기본 모드(추가 순서)에서 중복 키로 `put()`하면 값만 교체되고 순서는 유지된다. `remove()` 후 재추가하면 맨 뒤로 이동한다.
- 접근 순서 모드(`accessOrder=true`)에서는 `get()` / `put()`으로 접근한 항목이 맨 뒤로 이동한다. 맨 앞 항목이 가장 오래 전에 접근한 항목이다.
- `removeEldestEntry()`를 재정의하면 항목 수가 일정 크기를 초과할 때 자동으로 가장 오래된 항목을 제거할 수 있다. 접근 순서 모드와 결합하면 LRU(Least Recently Used) 캐시를 구현할 수 있다.
- `HashMap`보다 메모리를 약간 더 사용하지만, 검색·삽입·삭제 성능은 동일하게 O(1)이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App5
  ```

---

## Exam06 - Comparable과 Comparator 인터페이스

### 개념

Java에서 객체를 정렬하려면 비교 기준이 필요하다.
`Comparable`은 클래스 자체에 자연 순서를 내장하고, `Comparator`는 외부에서 비교 기준을 별도로 정의한다.

#### Comparable vs Comparator

| 항목 | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| 패키지 | `java.lang` | `java.util` |
| 구현 위치 | 정렬 대상 클래스 내부 | 클래스 외부 (별도 객체) |
| 구현 메서드 | `int compareTo(T o)` | `int compare(T o1, T o2)` |
| 정렬 기준 수 | 하나 (자연 순서) | 여러 개 정의 가능 |
| 별칭 | 내부 비교자 | 외부 비교자 |
| 활용 | `Collections.sort(list)`, `new TreeSet<>()` | `Collections.sort(list, comp)`, `new TreeSet<>(comp)` |

#### 반환값 규칙

두 인터페이스 모두 비교 결과를 정수로 반환하며 부호로 순서를 결정한다.

| 반환값 | 의미 | 정렬 결과 |
|---|---|---|
| 음수 | `this < other` (또는 `o1 < o2`) | 앞 항목이 먼저 |
| `0` | 동등 | 순서 변경 없음 |
| 양수 | `this > other` (또는 `o1 > o2`) | 뒤 항목이 먼저 |

#### Comparator 팩토리 메서드 (Java 8+)

| 메서드 | 설명 |
|---|---|
| `Comparator.comparing(keyExtractor)` | 특정 필드 기준 오름차순 |
| `Comparator.comparingInt(keyExtractor)` | `int` 필드 기준 오름차순 (오토박싱 없음) |
| `comparator.reversed()` | 역순(내림차순)으로 전환 |
| `comparator.thenComparing(keyExtractor)` | 동일한 경우 추가 기준 적용 (다중 정렬) |
| `Comparator.naturalOrder()` | 자연 순서(`Comparable`) |
| `Comparator.reverseOrder()` | 자연 순서의 역순 |

#### Comparable을 구현한 주요 표준 클래스

| 클래스 | 자연 순서 |
|---|---|
| `Integer`, `Long`, `Double` 등 래퍼 클래스 | 숫자 오름차순 |
| `String` | 사전순(lexicographic) |
| `LocalDate`, `LocalDateTime` | 날짜/시간 오름차순 |

### App - Comparable 사용

```java
// 점수 기준 자연 순서를 정의하는 Student 클래스
static class Student implements Comparable<Student> {
  String name;
  int score;

  @Override
  public int compareTo(Student other) {
    return this.score - other.score; // 점수 오름차순 (음수 → this가 앞)
  }
}

// 1. 표준 클래스의 자연 순서 - Collections.sort()
List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
Collections.sort(numbers); // [10, 20, 30, 40, 50]

List<String> words = new ArrayList<>(List.of("banana", "apple", "cherry", "mango"));
Collections.sort(words); // [apple, banana, cherry, mango]

// 2. compareTo() 반환값 직접 확인
Integer a = 10, b = 20;
a.compareTo(b); // -1 (10 < 20 → 음수)
b.compareTo(a); //  1 (20 > 10 → 양수)
a.compareTo(a); //  0 (동등)

// 3. 커스텀 클래스 정렬
List<Student> students = new ArrayList<>();
students.add(new Student("홍길동", 85));
students.add(new Student("임꺽정", 92));
students.add(new Student("유관순", 78));
students.add(new Student("이순신", 95));
students.add(new Student("장보고", 88));
Collections.sort(students); // Student.compareTo() 사용
// [유관순(78), 홍길동(85), 장보고(88), 임꺽정(92), 이순신(95)]

// 4. TreeSet - Comparable 기반 자동 정렬
TreeSet<Student> treeSet = new TreeSet<>();
treeSet.add(new Student("홍길동", 85));
treeSet.add(new Student("임꺽정", 92));
treeSet.add(new Student("유관순", 78));
treeSet.first(); // 유관순(78) - 최솟값
treeSet.last();  // 임꺽정(92) - 최댓값

// 5. Collections.min() / max()
Collections.min(students); // 최솟값(최저점)
Collections.max(students); // 최댓값(최고점)

// 6. Comparable 미구현 객체 → ClassCastException
TreeSet<Object> badSet = new TreeSet<>();
badSet.add(new Object());
badSet.add(new Object()); // 두 번째 추가 시 비교 시도 → ClassCastException
```

- `Comparable`을 구현하면 `Collections.sort()`, `Arrays.sort()`, `TreeSet`, `TreeMap` 등 Java 정렬 API를 별도 비교자 없이 바로 사용할 수 있다.
- `compareTo()`는 `this - other`처럼 뺄셈으로 구현할 수 있지만, 오버플로우 위험이 있을 때는 `Integer.compare(this.score, other.score)`를 사용한다.
- `TreeSet`은 항목 추가 시마다 `compareTo()`로 위치를 결정한다. `Comparable`을 구현하지 않은 클래스를 두 번 이상 추가하면 `ClassCastException`이 발생한다.
- `Collections.min()` / `max()`도 내부적으로 `compareTo()`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam06.App
  ```

### App2 - Comparator 사용

```java
static class Student {
  String name;
  int score;
  String grade; // "A", "B", "C"
}

List<Student> students = new ArrayList<>();
students.add(new Student("홍길동", 86, "B"));
students.add(new Student("임꺽정", 92, "A"));
students.add(new Student("유관순", 78, "C"));
students.add(new Student("이순신", 95, "A"));
students.add(new Student("장보고", 85, "B"));
students.add(new Student("강감찬", 91, "A"));

// 1. 익명 클래스로 Comparator 구현
Comparator<Student> byScore = new Comparator<Student>() {
  @Override
  public int compare(Student o1, Student o2) {
    return o1.score - o2.score; // 점수 오름차순
  }
};
Collections.sort(list, byScore);
// [유관순(78/C), 장보고(85/B), 홍길동(86/B), 강감찬(91/A), 임꺽정(92/A), 이순신(95/A)]

// 2. 람다로 Comparator 구현
Comparator<Student> byName = (o1, o2) -> o1.name.compareTo(o2.name);
list.sort(byName); // 이름 사전순

// 3. Comparator.comparingInt() - 점수 오름차순
list.sort(Comparator.comparingInt(s -> s.score));

// 4. reversed() - 점수 내림차순
list.sort(Comparator.comparingInt((Student s) -> s.score).reversed());
// [이순신(95/A), 임꺽정(92/A), 강감찬(91/A), 홍길동(86/B), 장보고(85/B), 유관순(78/C)]

// 5. thenComparing() - 복합 정렬 (점수 내림차순, 동점 시 이름 사전순)
list.sort(
    Comparator.comparingInt((Student s) -> s.score)
              .reversed()
              .thenComparing(s -> s.name));

// 6. reverseOrder() - 자연 순서의 역순
List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
numbers.sort(Comparator.reverseOrder()); // [50, 40, 30, 20, 10]

// 7. TreeSet에 Comparator 전달 - Comparable 없이도 정렬 가능
TreeSet<Student> treeSet = new TreeSet<>(
    Comparator.comparingInt((Student s) -> s.score)
              .reversed()
              .thenComparing(s -> s.name));
treeSet.addAll(students);

// 8. TreeMap에 Comparator 전달 - 키 내림차순 정렬
TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
treeMap.put("banana", 500);
treeMap.put("apple", 1000);
treeMap.put("mango", 700);
treeMap.put("cherry", 300);
// {mango=700, cherry=300, banana=500, apple=1000} - 내림차순

// 9. 복합 기준 - 등급 오름차순, 동일 등급 내 점수 내림차순
list.sort(
    Comparator.comparing((Student s) -> s.grade)
              .thenComparing(Comparator.comparingInt((Student s) -> s.score).reversed()));
// [이순신(95/A), 임꺽정(92/A), 강감찬(91/A), 홍길동(86/B), 장보고(85/B), 유관순(78/C)]
```

- `Comparator`는 `Comparable`을 구현하지 않은 클래스도 정렬할 수 있고, 상황에 따라 다양한 기준을 외부에서 정의할 수 있다.
- `Comparator.comparingInt()`는 `comparingDouble()` / `comparingLong()`과 같이 기본 타입 특화 버전이 있다. 오토박싱 없이 비교하므로 `comparing()`보다 효율적이다.
- `reversed()` 이후 `thenComparing()`을 연결할 때 타입 추론이 실패할 수 있으므로 `(Student s) -> s.score`처럼 타입을 명시해야 한다.
- `TreeSet` / `TreeMap` 생성자에 `Comparator`를 전달하면 해당 기준으로 자동 정렬된다. `Comparable`이 없어도 동작하지만, `compare()`가 `0`을 반환하면 동일 항목으로 간주하여 중복이 제거된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam06.App2
  ```

---

## Exam07 - 동기화를 지원하는 컬렉션

### 개념

멀티스레드 환경에서 일반 컬렉션(`ArrayList`, `HashMap` 등)은 동시 접근 시 데이터 손실·손상이 발생한다.
Java는 스레드 안전한 컬렉션을 세 가지 방식으로 제공한다.

#### 동기화 컬렉션 비교

| 구분 | 클래스 / 메서드 | 동기화 방식 | 특징 |
|---|---|---|---|
| 동기화 래퍼 | `Collections.synchronizedXxx()` | 메서드 전체 락 | 기존 컬렉션을 래핑. 복합 연산은 추가 `synchronized` 필요 |
| 동시성 Map | `ConcurrentHashMap` | 버킷 단위 락 | 높은 동시성. 원자적 복합 연산 메서드 제공 |
| 쓰기 시 복사 | `CopyOnWriteArrayList` / `CopyOnWriteArraySet` | 쓰기 시 배열 복사 | 읽기 무락. 순회 중 수정 안전. 쓰기 비용 O(n) |
| 블로킹 큐 | `LinkedBlockingQueue` / `ArrayBlockingQueue` | 내부 락 + 조건 변수 | 생산자-소비자 패턴. `put()` / `take()` 블로킹 |

#### Collections.synchronizedXxx() 주요 팩토리 메서드

| 메서드 | 설명 |
|---|---|
| `Collections.synchronizedList(List<T>)` | 동기화된 List 반환 |
| `Collections.synchronizedSet(Set<T>)` | 동기화된 Set 반환 |
| `Collections.synchronizedMap(Map<K,V>)` | 동기화된 Map 반환 |
| `Collections.synchronizedSortedMap(SortedMap<K,V>)` | 동기화된 SortedMap 반환 |

#### ConcurrentHashMap 원자적 복합 연산 메서드

| 메서드 | 설명 |
|---|---|
| `putIfAbsent(key, value)` | 키가 없을 때만 추가. 이전 값 반환 |
| `computeIfAbsent(key, fn)` | 키가 없을 때 함수로 값 생성 후 추가 |
| `computeIfPresent(key, fn)` | 키가 있을 때만 함수로 값 갱신 |
| `compute(key, fn)` | 키 유무와 관계없이 함수로 값 계산·저장 |
| `merge(key, value, fn)` | 키가 없으면 `value` 저장, 있으면 함수로 병합 |

#### BlockingQueue 메서드 동작 방식

| 동작 | 예외 발생 | 특수값 반환 | 블로킹 | 타임아웃 |
|---|---|---|---|---|
| 삽입 | `add(e)` | `offer(e)` | `put(e)` | `offer(e, t, unit)` |
| 제거 | `remove()` | `poll()` | `take()` | `poll(t, unit)` |
| 검사 | `element()` | `peek()` | — | — |

- `put(e)` : 큐가 가득 차면 공간이 생길 때까지 무한 대기
- `take()` : 큐가 비어 있으면 항목이 생길 때까지 무한 대기
- `offer(e)` : 가득 찬 경우 즉시 `false` 반환 (블로킹 없음)
- `poll()` : 비어 있는 경우 즉시 `null` 반환 (블로킹 없음)

#### BlockingQueue 주요 구현체

| 구현체 | 내부 구조 | 용량 | 특징 |
|---|---|---|---|
| `LinkedBlockingQueue` | 연결 리스트 | 미지정 시 무제한 | 범용. 용량 지정 가능 |
| `ArrayBlockingQueue` | 배열 | 생성 시 필수 지정 | 공정성(`fairness`) 옵션 제공 |
| `PriorityBlockingQueue` | 힙 | 무제한 | 우선순위 기반 정렬 |
| `SynchronousQueue` | 없음 | 0 | `put()`과 `take()`가 직접 만날 때만 전달 |

### App - Collections.synchronizedXxx() 사용

```java
// 1. synchronizedList() / synchronizedMap() - 기본 사용
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
syncList.add(1);
syncList.add(2);
syncList.add(3); // 개별 메서드 호출은 스레드 안전

Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
syncMap.put("apple", 1000);
syncMap.put("banana", 500);

// 2. 비동기화 ArrayList - 멀티스레드 동시 쓰기 시 데이터 손실
List<Integer> unsafeList = new ArrayList<>();
// 5개 스레드가 각 100개씩 추가 → 기대 500, 실제 458 (race condition)

// 3. synchronizedList() - 멀티스레드 동시 쓰기 안전
List<Integer> safeList = Collections.synchronizedList(new ArrayList<>());
// 5개 스레드가 각 100개씩 추가 → 기대 500, 실제 500

// 4. 복합 연산 - synchronized 블록으로 원자성 보장
// get() + put() 는 두 번의 메서드 호출 → 래퍼만으로는 원자성 미보장
Map<String, Integer> countMap = Collections.synchronizedMap(new HashMap<>());
countMap.put("count", 0);
synchronized (countMap) {                             // 복합 연산을 하나의 락으로 묶음
  countMap.put("count", countMap.get("count") + 1);
}

// 5. 순회 - synchronized 블록으로 감싸야 안전
List<String> iterList = Collections.synchronizedList(new ArrayList<>());
iterList.add("apple");
iterList.add("banana");
synchronized (iterList) {       // 순회 전체를 동기화
  for (String item : iterList) {
    System.out.println(item);
  }
}
```

- `Collections.synchronizedXxx()`는 기존 컬렉션의 모든 메서드를 `synchronized`로 감싼 래퍼를 반환한다. 원본 컬렉션을 직접 참조하면 동기화가 우회되므로 반드시 반환된 래퍼만 사용해야 한다.
- 개별 메서드(`add`, `get`, `put` 등) 호출은 스레드 안전하지만, `get` 후 `put`하는 복합 연산은 두 메서드 사이에 다른 스레드가 끼어들 수 있으므로 `synchronized` 블록으로 직접 감싸야 한다.
- 순회(`for-each`, `iterator`) 도중 다른 스레드가 컬렉션을 수정하면 `ConcurrentModificationException`이 발생할 수 있다. 순회 전체를 래퍼 객체를 락으로 하는 `synchronized` 블록으로 감싸야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam07.App
  ```

### App2 - ConcurrentHashMap 사용

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("apple", 1000);
map.put("banana", 500);
map.put("mango", 700);

// putIfAbsent() - 원자적 조건부 추가
map.putIfAbsent("banana", 9999); // 이미 있음 → 무시, 이전 값(500) 반환
map.putIfAbsent("cherry", 300);  // 없음 → 추가, null 반환

// computeIfAbsent() - 키가 없을 때 함수로 값 생성
map.computeIfAbsent("strawberry", key -> key.length() * 100); // 없음 → 1000 저장
map.computeIfAbsent("apple", key -> 9999);                    // 있음 → 무시

// computeIfPresent() - 키가 있을 때만 값 갱신
map.computeIfPresent("apple", (key, val) -> val + 200); // 있음 → 1200
map.computeIfPresent("grape", (key, val) -> val + 200); // 없음 → 무시

// compute() - 키 유무와 관계없이 계산
map.compute("mango", (key, val) -> val == null ? 100 : val + 100); // 800
map.compute("grape", (key, val) -> val == null ? 100 : val + 100); // 신규 100

// merge() - 단어 카운팅 패턴 (없으면 저장, 있으면 병합)
ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
for (String word : new String[]{"java", "python", "java", "go", "java", "python"}) {
  wordCount.merge(word, 1, Integer::sum);
}
// {python=2, java=3, go=1}

// 멀티스레드 동시 카운팅 - merge()로 원자적 증가
ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
// 10개 스레드 × 각 100회 증가 → 기대 1000, 실제 1000

// 순회 중 수정 - ConcurrentModificationException 없음
for (Map.Entry<String, Integer> entry : map.entrySet()) {
  map.put("D", 4); // 순회 중 수정해도 예외 없음
}
```

- `ConcurrentHashMap`은 Map 전체를 잠그는 `Hashtable`과 달리 **버킷 단위로 잠근다**. 서로 다른 버킷에 접근하는 스레드는 동시에 실행되므로 `Hashtable`보다 훨씬 높은 동시성을 제공한다.
- `putIfAbsent`, `computeIfAbsent`, `compute`, `merge` 등은 읽기-판단-쓰기를 하나의 원자적 연산으로 수행하므로 별도 `synchronized` 블록 없이 복합 연산을 안전하게 처리할 수 있다.
- `merge(key, 1, Integer::sum)`은 단어 카운팅처럼 "없으면 초기값, 있으면 누적" 패턴을 간결하게 표현한다.
- 순회 도중 다른 스레드가 수정해도 `ConcurrentModificationException`이 발생하지 않는다. 단, 순회 시작 이후의 수정이 현재 순회에 반영될 수도 있고 반영되지 않을 수도 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam07.App2
  ```

### App3 - CopyOnWriteArrayList / CopyOnWriteArraySet 사용

```java
// 1. CopyOnWriteArrayList - 기본 사용
CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
cowList.add("apple");
cowList.add("banana");
cowList.add("cherry");
cowList.get(1);           // "banana"
cowList.set(1, "mango");  // 쓰기 시 내부 배열 전체 복사 후 수정
cowList.remove("cherry");

// 2. 순회 중 수정 - ConcurrentModificationException 없음 (스냅샷 순회)
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(List.of(1, 2, 3, 4, 5));
for (Integer item : list) {
  list.add(item + 10); // 순회 중 수정해도 예외 없음
}
// 순회는 시작 시점의 스냅샷 [1,2,3,4,5]를 순회
// 순회 후 list: [1, 2, 3, 4, 5, 11, 12, 13, 14, 15]

// 3. 일반 ArrayList - 순회 중 수정 시 예외 발생
List<Integer> arrayList = new ArrayList<>(List.of(1, 2, 3, 4, 5));
for (Integer item : arrayList) {
  arrayList.add(item + 10); // ConcurrentModificationException 발생!
}

// 4. iterator() - 스냅샷 기반 순회
CopyOnWriteArrayList<String> snapList = new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
Iterator<String> it = snapList.iterator(); // 스냅샷 [A, B, C] 생성
snapList.add("D");                          // iterator 생성 이후 수정
while (it.hasNext()) {
  System.out.print(it.next() + " ");       // A B C (D는 미반영)
}
// 현재 snapList: [A, B, C, D]

// 5. 리스너 목록 패턴 - 읽기 중 리스너 추가/제거 안전
CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();
listeners.add("Listener-1");
listeners.add("Listener-2");
listeners.add("Listener-3");
// 이벤트 스레드가 순회하는 동안 다른 스레드가 리스너 추가/제거해도 안전

// 6. CopyOnWriteArraySet - 중복 없는 스냅샷 Set
CopyOnWriteArraySet<String> cowSet = new CopyOnWriteArraySet<>();
cowSet.add("apple");
cowSet.add("banana");
cowSet.add("apple"); // 중복 → 무시
cowSet.add("cherry");
// [apple, banana, cherry] - 추가 순서 유지, 중복 제거
```

- `CopyOnWriteArrayList`는 쓰기 연산(`add`/`set`/`remove`) 시마다 내부 배열 전체를 복사한다. 쓰기 비용이 O(n)이므로 쓰기가 빈번한 환경에는 부적합하다.
- 읽기(`get`/`iterator`)는 락 없이 현재 스냅샷을 사용하므로 매우 빠르다. **읽기가 압도적으로 많고 쓰기가 드문 환경**에 최적이다. 이벤트 리스너 목록, 설정 값 캐시 등이 대표적인 사용 사례이다.
- `iterator()`는 생성 시점의 배열 스냅샷을 순회한다. 순회 도중 원본이 수정되어도 `ConcurrentModificationException`이 발생하지 않으며, 순회 결과에도 영향을 주지 않는다.
- `CopyOnWriteArraySet`은 내부적으로 `CopyOnWriteArrayList`를 사용하며, 중복을 허용하지 않고 추가 순서를 유지한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam07.App3
  ```

### App4 - BlockingQueue 사용

```java
// 1. LinkedBlockingQueue - 기본 사용 (용량 무제한)
BlockingQueue<String> lbq = new LinkedBlockingQueue<>();
lbq.put("item-1");
lbq.put("item-2");
lbq.put("item-3");
lbq.take();   // "item-1" (FIFO, 비어 있으면 무한 대기)
lbq.peek();   // "item-2" (제거 안 함)
lbq.poll();   // "item-2" (비어 있으면 null 즉시 반환)

// 2. ArrayBlockingQueue - 고정 용량
BlockingQueue<Integer> abq = new ArrayBlockingQueue<>(3); // 최대 3개
abq.put(1);
abq.put(2);
abq.put(3);
abq.offer(4);               // false (가득 참 → 즉시 반환)
abq.offer(4, 100, TimeUnit.MILLISECONDS); // false (100ms 대기 후 타임아웃)

// 3. 생산자-소비자 패턴 - put() / take() 블로킹
BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);

Thread producer = new Thread(() -> {
  for (int i = 1; i <= 5; i++) {
    queue.put("item-" + i); // 큐가 가득 차면 소비자가 꺼낼 때까지 대기
  }
  queue.put("DONE");        // 종료 신호
});

Thread consumer = new Thread(() -> {
  while (true) {
    String item = queue.take(); // 큐가 비어 있으면 생산자가 넣을 때까지 대기
    if ("DONE".equals(item)) break;
    process(item);
  }
});

// 4. 다중 생산자 - 단일 소비자 / poll(timeout)으로 종료 판단
Integer item;
while ((item = sharedQueue.poll(200, TimeUnit.MILLISECONDS)) != null) {
  process(item); // 200ms 동안 항목이 없으면 null 반환 → 루프 종료
}

// 5. ArrayBlockingQueue 공정성(fairness=true)
// 대기 중인 스레드에 FIFO 순서로 접근 기회 부여 (기본값 false: 순서 미보장)
BlockingQueue<String> fairQueue = new ArrayBlockingQueue<>(5, true);
```

- `put()` / `take()`는 큐가 가득 찼거나 비어 있을 때 **현재 스레드를 블로킹**한다. 이를 이용하면 생산자가 소비자 처리 속도에 맞춰 자동으로 속도가 제어되는(back-pressure) 생산자-소비자 패턴을 구현할 수 있다.
- `offer()` / `poll()`은 즉시 반환하므로 실패 시 다른 처리를 해야 하는 경우에 사용한다. 타임아웃 버전(`offer(e, t, unit)` / `poll(t, unit)`)은 지정 시간 동안만 대기한다.
- 소비자 종료 신호 처리 방법은 두 가지이다. `put("DONE")`처럼 **종료 신호 항목**을 큐에 넣어 `take()`로 받는 방법과, `poll(timeout)`을 사용해 **일정 시간 동안 항목이 없으면 소비자 스스로 종료**하는 방법이 있다.
- `ArrayBlockingQueue`는 `fairness=true`로 생성하면 대기 중인 스레드에게 FIFO 순서로 접근 기회를 부여한다. 공정성은 기아(starvation)를 방지하지만 처리량(throughput)이 낮아질 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam07.App4
  ```

---

## Exam08 - 수정 불가 컬렉션 (Unmodifiable Collection)

### 개념

수정 불가 컬렉션은 생성 후 항목을 추가·변경·삭제할 수 없는 컬렉션이다.
수정 메서드(`add`, `set`, `put`, `remove` 등)를 호출하면 `UnsupportedOperationException`이 발생한다.

#### 수정 불가 컬렉션 생성 방법 비교

| 방법 | 도입 | 원본과의 관계 | `null` 허용 | 특징 |
|---|---|---|---|---|
| `Collections.unmodifiableXxx()` | Java 1.2 | 원본의 뷰 — 원본 수정 시 함께 변경 | 허용 | 기존 컬렉션을 읽기 전용으로 래핑 |
| `List.of()` / `Set.of()` / `Map.of()` | Java 9 | 독립 — 원본 없음 | 불허 | 리터럴로 불변 컬렉션 즉시 생성 |
| `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()` | Java 10 | 독립 — 원본 복사 | 불허 | 기존 컬렉션을 복사하여 불변 생성 |

#### 불변 컬렉션 상황별 선택 기준

| 상황 | 권장 방법 |
|---|---|
| 코드에서 리터럴로 바로 초기화 | `List.of()` / `Set.of()` / `Map.of()` |
| 기존 컬렉션을 불변으로 복사 | `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()` |
| 기존 컬렉션의 읽기 전용 뷰만 필요 | `Collections.unmodifiableXxx()` |
| 방어적 복사 후 읽기 전용 반환 | `Collections.unmodifiableList(new ArrayList<>(src))` |

#### Map.of() / Map.ofEntries() 선택

| 쌍의 수 | 방법 |
|---|---|
| 10쌍 이하 | `Map.of(k1, v1, k2, v2, ...)` |
| 11쌍 이상 | `Map.ofEntries(Map.entry(k, v), ...)` |

#### 얕은 불변성(Shallow Immutability) 주의

불변 컬렉션은 **컬렉션 구조 자체(항목의 추가·삭제)만 보호**한다.
컬렉션에 저장된 객체 내부의 상태는 보호하지 않는다.

```java
List<List<String>> nested = List.of(innerList);
nested.add(...);         // UnsupportedOperationException ← 구조 보호
nested.get(0).add("x"); // 허용 ← 내부 List는 가변
```

### App - Collections.unmodifiableXxx() 사용

```java
// 1. unmodifiableList() - 기본 사용
List<String> original = new ArrayList<>(List.of("apple", "banana", "cherry"));
List<String> readOnly = Collections.unmodifiableList(original);

readOnly.get(0);          // "apple" - 읽기 허용
readOnly.size();          // 3       - 읽기 허용
readOnly.add("mango");    // UnsupportedOperationException
readOnly.set(0, "grape"); // UnsupportedOperationException
readOnly.remove(0);       // UnsupportedOperationException

// 2. unmodifiableSet() / unmodifiableMap()
Set<Integer> readOnlySet = Collections.unmodifiableSet(new HashSet<>(Set.of(1, 2, 3)));
readOnlySet.contains(2); // true
readOnlySet.add(4);      // UnsupportedOperationException

Map<String, Integer> readOnlyMap = Collections.unmodifiableMap(new HashMap<>(Map.of("apple", 1000)));
readOnlyMap.get("apple");        // 1000
readOnlyMap.put("cherry", 300);  // UnsupportedOperationException
readOnlyMap.remove("apple");     // UnsupportedOperationException

// 3. 원본 수정 시 래퍼에도 반영 (뷰 특성)
List<String> mutableList = new ArrayList<>(List.of("A", "B"));
List<String> viewList = Collections.unmodifiableList(mutableList);
mutableList.add("C");
System.out.println(viewList); // [A, B, C] ← 원본 수정이 함께 반영됨

// 4. 완전한 불변성 - 원본 참조를 차단
// 나쁜 예: 원본 참조가 남아 우회 수정 가능
List<String> leak = new ArrayList<>(List.of("X", "Y"));
List<String> notSafe = Collections.unmodifiableList(leak);
leak.add("Z");
System.out.println(notSafe); // [X, Y, Z] ← 우회 수정됨

// 좋은 예: 복사 후 래핑 → 원본 참조 차단
List<String> safe = Collections.unmodifiableList(new ArrayList<>(List.of("X", "Y")));
// 원본 참조가 없으므로 외부 수정 불가
System.out.println(safe); // [X, Y]
```

- `Collections.unmodifiableXxx()`는 원본 컬렉션의 **뷰(view)**를 반환한다. 원본 참조가 남아 있으면 우회 수정이 가능하므로 완전한 불변성을 보장하지 못한다.
- 완전한 불변성을 보장하려면 `new ArrayList<>(src)`처럼 복사본을 만들어 원본 참조를 차단하거나 Java 10+의 `List.copyOf()`를 사용한다.
- 읽기(`get`, `size`, `contains`, `iterator` 등)는 모두 허용된다. 수정 메서드를 호출하면 `UnsupportedOperationException`이 발생한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam08.App
  ```

### App2 - List.of() / Set.of() / Map.of() / copyOf() 사용

```java
// 1. List.of() - 불변 List
List<String> immutableList = List.of("apple", "banana", "cherry", "mango");
immutableList.get(1);              // "banana" - 읽기 허용
immutableList.contains("apple");   // true     - 읽기 허용
immutableList.add("grape");        // UnsupportedOperationException
immutableList.set(0, "grape");     // UnsupportedOperationException
List.of("a", null, "b");           // NullPointerException - null 불허

// 2. Set.of() - 불변 Set (순서 미보장)
Set<Integer> immutableSet = Set.of(10, 20, 30, 40, 50);
immutableSet.contains(30); // true
immutableSet.add(60);      // UnsupportedOperationException
Set.of(1, 2, 2, 3);        // IllegalArgumentException - 중복 불허
Set.of(1, null, 3);        // NullPointerException     - null 불허

// 3. Map.of() - 불변 Map (최대 10쌍, 순서 미보장)
Map<String, Integer> immutableMap = Map.of(
    "apple", 1000,
    "banana", 500,
    "cherry", 300
);
immutableMap.get("apple");       // 1000
immutableMap.put("mango", 700);  // UnsupportedOperationException
Map.of("a", 1, "a", 2);          // IllegalArgumentException - 중복 키 불허

// 4. Map.ofEntries() - 10쌍 초과 시 사용
Map<String, Integer> largeMap = Map.ofEntries(
    Map.entry("a", 1),
    Map.entry("b", 2),
    // ...
    Map.entry("k", 11) // 11쌍 이상 가능
);
largeMap.size(); // 11

// 5. List.copyOf() - 기존 컬렉션 복사 후 불변 생성 (Java 10+)
List<String> mutable = new ArrayList<>(List.of("X", "Y", "Z"));
List<String> copied = List.copyOf(mutable);
mutable.add("W");
System.out.println(copied); // [X, Y, Z] ← 원본 수정 영향 없음 (독립적)
copied.add("V");            // UnsupportedOperationException

// 6. Set.copyOf() / Map.copyOf() - 원본과 독립적인 불변 컬렉션
Set<String> copiedSet = Set.copyOf(mutableSet);    // 원본 수정 영향 없음
Map<String, Integer> copiedMap = Map.copyOf(mutableMap); // 원본 수정 영향 없음

// 7. List.of() vs Collections.unmodifiableList() 비교
List<String> base = new ArrayList<>(List.of("A", "B"));
List<String> unmod = Collections.unmodifiableList(base);
List<String> immut = List.of("A", "B");
base.add("C");
System.out.println(unmod); // [A, B, C] ← 원본 수정 반영 (뷰)
System.out.println(immut); // [A, B]    ← 영향 없음 (독립)
```

- `List.of()` / `Set.of()` / `Map.of()`는 원본이 없는 완전한 불변 컬렉션이다. `Collections.unmodifiableXxx()`와 달리 우회 수정이 불가능하다.
- `null` 요소·키·값을 허용하지 않으며, `Set.of()`와 `Map.of()`는 중복 요소·키도 `IllegalArgumentException`으로 거부한다.
- `Set.of()` / `Map.of()`의 순서는 실행마다 달라질 수 있으므로 순서에 의존하는 코드를 작성하면 안 된다.
- `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()`는 기존 컬렉션의 요소를 복사하여 원본과 독립적인 불변 컬렉션을 생성한다. `null` 요소가 있으면 `NullPointerException`이 발생한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam08.App2
  ```

### App3 - 방어적 복사 패턴 사용

```java
// 나쁜 예: 방어적 복사 없는 클래스
class UnsafeCart {
  private List<String> items;
  UnsafeCart(List<String> items) { this.items = items; } // 외부 참조 저장
  List<String> getItems()        { return items; }        // 내부 참조 반환
}

List<String> external = new ArrayList<>(List.of("apple", "banana"));
UnsafeCart unsafeCart = new UnsafeCart(external);
external.add("cherry");              // 외부에서 원본 수정
unsafeCart.getItems();               // [apple, banana, cherry] ← 내부 상태 변경됨
unsafeCart.getItems().add("grape");  // getter로 내부 직접 수정 가능

// 좋은 예: 방어적 복사를 적용한 클래스
class SafeCart {
  private final List<String> items;
  SafeCart(List<String> items) { this.items = List.copyOf(items); } // 복사 후 불변 저장
  List<String> getItems()      { return items; }                     // 불변이므로 안전
}

SafeCart safeCart = new SafeCart(external);
external.add("cherry");               // 외부 수정
safeCart.getItems();                  // [apple, banana] ← 내부 상태 보호됨
safeCart.getItems().add("grape");     // UnsupportedOperationException

// 읽기 전용 뷰 반환 - 복사본의 읽기 전용 뷰를 반환하면 원본도 보호
List<String> internalData = new ArrayList<>(List.of("X", "Y", "Z"));
List<String> view = Collections.unmodifiableList(new ArrayList<>(internalData));
internalData.add("W");
System.out.println(view);  // [X, Y, Z] ← 복사본이므로 영향 없음
view.add("V");             // UnsupportedOperationException

// 중첩 컬렉션의 얕은 불변성 주의
List<String> inner1 = new ArrayList<>(List.of("a", "b"));
List<List<String>> nested = List.of(inner1, new ArrayList<>(List.of("c", "d")));
nested.add(new ArrayList<>()); // UnsupportedOperationException ← 구조 보호
inner1.add("z");               // 허용 ← 내부 List는 가변
System.out.println(nested);    // [[a, b, z], [c, d]]

// 상수 컬렉션 정의 패턴
final List<String> BAD_CONSTANT = new ArrayList<>(List.of("READ", "WRITE"));
BAD_CONSTANT.add("ADMIN"); // final이지만 내용 수정 가능 ← 나쁜 예

final List<String> GOOD_CONSTANT = List.of("READ", "WRITE", "DELETE");
GOOD_CONSTANT.add("ADMIN"); // UnsupportedOperationException ← 좋은 예

// Map.copyOf() 얕은 불변성 확인
Map<String, List<String>> immutableConfig = Map.copyOf(config);
immutableConfig.put("db", new ArrayList<>()); // UnsupportedOperationException
immutableConfig.get("servers").add("host3");  // 허용 ← 값인 List는 가변
```

- 방어적 복사는 **생성자(입력)**와 **getter(출력)** 두 곳에 모두 적용해야 완전한 보호가 이루어진다. 생성자에서만 복사하면 getter로 받은 참조를 통해 내부 수정이 가능하다.
- `List.copyOf()`는 `null` 요소가 포함된 컬렉션을 복사하면 `NullPointerException`이 발생한다. `null`이 포함될 수 있다면 `Collections.unmodifiableList(new ArrayList<>(src))`를 사용한다.
- 불변 컬렉션은 **얕은 불변성(shallow immutability)**만 보장한다. 컬렉션 구조(항목 추가·삭제)는 보호하지만, 컬렉션에 저장된 객체 내부의 상태는 보호하지 않는다.
- `final` 키워드는 변수가 다른 객체를 참조하지 못하게 막을 뿐, 참조하는 컬렉션의 내용 수정은 막지 않는다. 상수 컬렉션은 반드시 `List.of()` 같은 불변 컬렉션으로 선언해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam08.App3
  ```

---

## Exam09 - Sequenced Collections (Java 21)

### 개념

Java 21(JEP 431)에서 도입된 Sequenced Collections는 요소에 명확한 순서(encounter order)가 있는 컬렉션을 일관된 인터페이스로 다루기 위해 추가된 세 가지 인터페이스이다.

#### Sequenced Collections 인터페이스 계층

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

#### 인터페이스 비교

| 인터페이스 | 확장 | 구현 클래스 | 특징 |
|---|---|---|---|
| `SequencedCollection<E>` | `Collection<E>` | `ArrayList`, `LinkedList`, `ArrayDeque` | 순서 있는 컬렉션의 공통 메서드 |
| `SequencedSet<E>` | `SequencedCollection<E>`, `Set<E>` | `LinkedHashSet`, `TreeSet` | 중복 없는 순서 있는 Set |
| `SequencedMap<K,V>` | `Map<K,V>` | `LinkedHashMap`, `TreeMap` | 순서 있는 Map |

#### SequencedCollection 주요 메서드

| 메서드 | 설명 | 비어 있을 때 |
|---|---|---|
| `E getFirst()` | 첫 번째 요소 반환 | `NoSuchElementException` |
| `E getLast()` | 마지막 요소 반환 | `NoSuchElementException` |
| `void addFirst(E e)` | 맨 앞에 요소 추가 | — |
| `void addLast(E e)` | 맨 뒤에 요소 추가 | — |
| `E removeFirst()` | 첫 번째 요소 제거 후 반환 | `NoSuchElementException` |
| `E removeLast()` | 마지막 요소 제거 후 반환 | `NoSuchElementException` |
| `SequencedCollection<E> reversed()` | 역순 뷰 반환 (원본의 뷰) | — |

#### SequencedMap 추가 메서드

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

#### NavigableSet 주요 메서드

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

#### NavigableMap 주요 메서드

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

#### 탐색 메서드 경계값 포함 여부

| 메서드 | `e` 자신 포함 | 방향 |
|---|---|---|
| `lower(e)` / `lowerKey(k)` | 미포함 | e보다 작은 방향 |
| `floor(e)` / `floorKey(k)` | **포함** | e보다 작은 방향 |
| `ceiling(e)` / `ceilingKey(k)` | **포함** | e보다 큰 방향 |
| `higher(e)` / `higherKey(k)` | 미포함 | e보다 큰 방향 |

#### 범위 뷰 기본 동작 (SortedSet / SortedMap 호환)

| 메서드 | NavigableXxx (inclusive 명시) | SortedXxx (기본값) |
|---|---|---|
| `headSet` / `headMap` | `inclusive` 지정 가능 | `to` 미포함 (`false`) |
| `tailSet` / `tailMap` | `inclusive` 지정 가능 | `from` 포함 (`true`) |
| `subSet` / `subMap` | 양쪽 `inclusive` 지정 가능 | `from` 포함, `to` 미포함 |

#### reversed() 뷰 특성

`reversed()`는 원본의 역순 **뷰(view)**를 반환한다. 복사본이 아니므로 원본을 수정하면 뷰에도 반영된다.
뷰에서의 `addFirst()` / `putFirst()`는 원본의 맨 뒤에 추가하고, `addLast()` / `putLast()`는 원본의 맨 앞에 추가한다.

#### Java 21 이전 vs 이후 비교

| 작업 | Java 21 이전 | Java 21 이후 |
|---|---|---|
| 첫 번째 요소 | `list.get(0)` | `list.getFirst()` |
| 마지막 요소 | `list.get(list.size() - 1)` | `list.getLast()` |
| 역순 순회 | `Collections.reverse(new ArrayList<>(list))` | `list.reversed()` |
| Map 첫 항목 | `map.entrySet().iterator().next()` | `map.firstEntry()` |

### App - SequencedCollection 사용 (List, Deque)

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

### App2 - SequencedSet 사용 (LinkedHashSet, TreeSet)

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

### App3 - SequencedMap 사용 (LinkedHashMap, TreeMap)

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

### App4 - NavigableSet 사용

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

### App5 - NavigableMap 사용

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
