# Exam03 - ArrayList와 LinkedList 비교

## 개념

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

### 구현체 선택 기준

- **`ArrayList`**: 인덱스 조회가 빈번하거나, 항목을 주로 끝에 추가/삭제하는 대부분의 일반적인 상황
- **`LinkedList`**: 맨 앞/중간 삽입·삭제가 매우 빈번하거나, `Queue`/`Deque`로 사용할 때

## App - 동작 방식 비교

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam03.App
  ```

## App2 - 성능 비교

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam03.App2
  ```
