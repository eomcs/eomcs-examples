# Exam07 - 동기화를 지원하는 컬렉션

## 개념

멀티스레드 환경에서 일반 컬렉션(`ArrayList`, `HashMap` 등)은 동시 접근 시 데이터 손실·손상이 발생한다.
Java는 스레드 안전한 컬렉션을 세 가지 방식으로 제공한다.

### 동기화 컬렉션 비교

| 구분 | 클래스 / 메서드 | 동기화 방식 | 특징 |
|---|---|---|---|
| 동기화 래퍼 | `Collections.synchronizedXxx()` | 메서드 전체 락 | 기존 컬렉션을 래핑. 복합 연산은 추가 `synchronized` 필요 |
| 동시성 Map | `ConcurrentHashMap` | 버킷 단위 락 | 높은 동시성. 원자적 복합 연산 메서드 제공 |
| 쓰기 시 복사 | `CopyOnWriteArrayList` / `CopyOnWriteArraySet` | 쓰기 시 배열 복사 | 읽기 무락. 순회 중 수정 안전. 쓰기 비용 O(n) |
| 블로킹 큐 | `LinkedBlockingQueue` / `ArrayBlockingQueue` | 내부 락 + 조건 변수 | 생산자-소비자 패턴. `put()` / `take()` 블로킹 |

### Collections.synchronizedXxx() 주요 팩토리 메서드

| 메서드 | 설명 |
|---|---|
| `Collections.synchronizedList(List<T>)` | 동기화된 List 반환 |
| `Collections.synchronizedSet(Set<T>)` | 동기화된 Set 반환 |
| `Collections.synchronizedMap(Map<K,V>)` | 동기화된 Map 반환 |
| `Collections.synchronizedSortedMap(SortedMap<K,V>)` | 동기화된 SortedMap 반환 |

### ConcurrentHashMap 원자적 복합 연산 메서드

| 메서드 | 설명 |
|---|---|
| `putIfAbsent(key, value)` | 키가 없을 때만 추가. 이전 값 반환 |
| `computeIfAbsent(key, fn)` | 키가 없을 때 함수로 값 생성 후 추가 |
| `computeIfPresent(key, fn)` | 키가 있을 때만 함수로 값 갱신 |
| `compute(key, fn)` | 키 유무와 관계없이 함수로 값 계산·저장 |
| `merge(key, value, fn)` | 키가 없으면 `value` 저장, 있으면 함수로 병합 |

### BlockingQueue 메서드 동작 방식

| 동작 | 예외 발생 | 특수값 반환 | 블로킹 | 타임아웃 |
|---|---|---|---|---|
| 삽입 | `add(e)` | `offer(e)` | `put(e)` | `offer(e, t, unit)` |
| 제거 | `remove()` | `poll()` | `take()` | `poll(t, unit)` |
| 검사 | `element()` | `peek()` | — | — |

- `put(e)` : 큐가 가득 차면 공간이 생길 때까지 무한 대기
- `take()` : 큐가 비어 있으면 항목이 생길 때까지 무한 대기
- `offer(e)` : 가득 찬 경우 즉시 `false` 반환 (블로킹 없음)
- `poll()` : 비어 있는 경우 즉시 `null` 반환 (블로킹 없음)

### BlockingQueue 주요 구현체

| 구현체 | 내부 구조 | 용량 | 특징 |
|---|---|---|---|
| `LinkedBlockingQueue` | 연결 리스트 | 미지정 시 무제한 | 범용. 용량 지정 가능 |
| `ArrayBlockingQueue` | 배열 | 생성 시 필수 지정 | 공정성(`fairness`) 옵션 제공 |
| `PriorityBlockingQueue` | 힙 | 무제한 | 우선순위 기반 정렬 |
| `SynchronousQueue` | 없음 | 0 | `put()`과 `take()`가 직접 만날 때만 전달 |

## App - Collections.synchronizedXxx() 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam07.App
  ```

## App2 - ConcurrentHashMap 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam07.App2
  ```

## App3 - CopyOnWriteArrayList / CopyOnWriteArraySet 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam07.App3
  ```

## App4 - BlockingQueue 사용

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
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam07.App4
  ```
