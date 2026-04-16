# Exam04 - Vector와 Stack, ArrayDeque

## 개념

`Vector`와 `Stack`은 Java 1.0부터 제공된 레거시 컬렉션 클래스이다.
현재는 하위 호환성을 위해 유지되고 있으며, 신규 코드에서는 각각 `ArrayList`와 `ArrayDeque`로 대체하는 것을 권장한다.

### Vector

| 구분 | `Vector` | `ArrayList` |
|---|---|---|
| 도입 시기 | Java 1.0 | Java 1.2 |
| 내부 구조 | 동적 배열(`Object[]`) | 동적 배열(`Object[]`) |
| 동기화 | 모든 메서드 `synchronized` | 동기화 없음 |
| 단일 스레드 성능 | 느림 (동기화 비용) | 빠름 |
| 배열 확장 비율 | 현재 크기의 2배 | 현재 크기의 1.5배 |
| 레거시 메서드 | `addElement`, `elementAt`, `removeElement` 등 | 없음 |

### Stack

| 구분 | 설명 |
|---|---|
| 상속 구조 | `Iterable` → `Collection` → `List` → `Vector` → `Stack` |
| 동작 방식 | LIFO(Last-In-First-Out, 후입선출) |
| 문제점 | `Vector` 상속으로 스택 의도에 맞지 않는 인덱스 접근 메서드까지 노출 |
| 권장 대안 | `ArrayDeque` (`Deque` 인터페이스 구현) |

### ArrayDeque

| 구분 | 설명 |
|---|---|
| 도입 시기 | Java 1.6 |
| 내부 구조 | 순환 배열(circular array) |
| 동기화 | 없음 (단일 스레드에서 빠름) |
| `null` 저장 | 불허 (`NullPointerException`) |
| 스택 대안 | `Stack` 대신 사용 권장 |
| 큐 대안 | `LinkedList` 대신 사용 권장 |

### Deque 인터페이스 주요 메서드

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

### Stack 고유 메서드

| 메서드 | 설명 |
|---|---|
| `E push(E item)` | 항목을 스택의 맨 위에 추가하고 반환 |
| `E pop()` | 맨 위 항목을 꺼내고 제거. 비어있으면 `EmptyStackException` |
| `E peek()` | 맨 위 항목을 제거하지 않고 반환. 비어있으면 `EmptyStackException` |
| `boolean empty()` | 스택이 비어있으면 `true` 반환 |
| `int search(Object o)` | 맨 위에서부터의 거리(1-based) 반환. 없으면 `-1` |

## App - Vector 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam04.App
  ```

## App2 - Stack 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam04.App2
  ```

## App3 - ArrayDeque 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam04.App3
  ```
