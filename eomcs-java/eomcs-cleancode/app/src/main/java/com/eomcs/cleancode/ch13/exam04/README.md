# 라이브러리를 이해하라 (Know Your Library)

> **동시성 코드를 직접 만들기 전에, 표준 라이브러리가 이미 제공하는 안전한 도구를 먼저 알아야 한다**

- 직접 만든 스레드/락/컬렉션 코드는 실수하기 쉽다
- Java는 이미 `java.util.concurrent` 에 검증된 동시성 도구를 제공한다
- 특히 Java 5 이후에는 thread-safe collection, executor, atomic, lock 관련 클래스가 제공된다
- `java.util.concurrent`, `java.util.concurrent.atomic`, `java.util.concurrent.locks` 를 익숙하게 사용하라

**핵심 의도:**

- 직접 구현하지 말고 검증된 라이브러리를 사용한다
- 동시성 문제를 “수동 제어”가 아니라 “적절한 도구 선택”으로 줄인다
- 모든 클래스가 thread-safe라고 가정하지 않는다

## Thread-Safe Collections

> **여러 스레드가 함께 사용하는 컬렉션은 일반 컬렉션이 아니라 thread-safe 컬렉션을 사용하라**

- java.util.concurrent 패키지의 컬렉션들이 멀티스레드 상황에서 안전하고 성능도 좋다 
- 특히 ConcurrentHashMap은 대부분의 상황에서 HashMap보다 낫고, 
- 동시에 읽기와 쓰기를 허용하며, 
- 일반적으로 안전하지 않은 복합 연산을 지원한다

## 예제 1

```java
// Bad
public class UserSessionStore {

    private final Map<String, UserSession> sessions = new HashMap<>();

    public void add(String token, UserSession session) {
        sessions.put(token, session);
    }

    public UserSession find(String token) {
        return sessions.get(token);
    }

    public void remove(String token) {
        sessions.remove(token);
    }
}
```

- HashMap은 thread-safe하지 않다
- 여러 요청 스레드가 동시에 put, get, remove를 호출하면 문제가 생길 수 있다
- 데이터가 깨지거나 예측하기 어려운 버그가 발생할 수 있다

```java
// Good
public class UserSessionStore {

    private final ConcurrentMap<String, UserSession> sessions =
            new ConcurrentHashMap<>();

    public void add(String token, UserSession session) {
        sessions.put(token, session);
    }

    public UserSession find(String token) {
        return sessions.get(token);
    }

    public void remove(String token) {
        sessions.remove(token);
    }
}
```

- ConcurrentHashMap은 멀티스레드 환경에 맞게 설계되어 있다
- 여러 스레드가 동시에 읽고 쓸 수 있다
- 직접 synchronized를 붙이는 것보다 의도가 명확하다

## 예제 2

```java
// Bad
public class ViewCounter {

    private final Map<Long, Integer> views = new HashMap<>();

    public void increase(long postId) {
        Integer current = views.get(postId);

        if (current == null) {
            views.put(postId, 1);
            return;
        }

        views.put(postId, current + 1);
    }
}
```

- get() 후 put()은 하나의 원자적 연산이 아니다
- 두 스레드가 동시에 같은 값을 읽으면 증가가 누락될 수 있다
- HashMap 자체도 thread-safe하지 않다

```java
// Good
public class ViewCounter {

    private final ConcurrentMap<Long, Integer> views =
            new ConcurrentHashMap<>();

    public void increase(long postId) {
        views.merge(postId, 1, Integer::sum);
    }

    public int countOf(long postId) {
        return views.getOrDefault(postId, 0);
    }
}
```

- merge()는 복합 갱신을 안전하게 처리한다
- 조회 후 갱신 사이의 경쟁 조건을 줄인다
- **직접 락을 잡지 않아도 된다**

## 예제 3

```java
// Bad
public class JobQueue {

    private final Queue<Job> queue = new LinkedList<>();

    public void add(Job job) {
        queue.add(job);
    }

    public Job take() {
        return queue.poll();
    }
}
```

- LinkedList 기반 Queue는 thread-safe하지 않다
- 생산자 스레드와 소비자 스레드가 동시에 접근하면 안전하지 않다
- 큐가 비었을 때 기다리는 기능도 없다

```java
// Good
public class JobQueue {

    private final BlockingQueue<Job> queue =
            new LinkedBlockingQueue<>();

    public void add(Job job) {
        queue.add(job);
    }

    public Job take() throws InterruptedException {
        return queue.take();
    }
}
```

- BlockingQueue는 생산자-소비자 구조에 적합하다
- 큐가 비어 있으면 take()가 기다린다
- **직접 wait() / notify()를 작성하지 않아도 된다**

## 예제 4

```java
// Bad
public class RecentSearches {

    private final List<String> keywords = new ArrayList<>();

    public void add(String keyword) {
        keywords.add(keyword);
    }

    public List<String> all() {
        return keywords;
    }
}
```

- ArrayList는 thread-safe하지 않다
- 내부 리스트를 그대로 반환하면 외부에서 마음대로 수정할 수 있다
- 여러 스레드가 동시에 순회/수정하면 문제가 생긴다

```java
// Good
public class RecentSearches {

    private final List<String> keywords =
            new CopyOnWriteArrayList<>();

    public void add(String keyword) {
        keywords.add(keyword);
    }

    public List<String> all() {
        return List.copyOf(keywords);
    }
}
```

- CopyOnWriteArrayList는 읽기가 많고 쓰기가 적은 경우 유용하다
- 순회 중 수정 문제를 줄일 수 있다
- 반환할 때도 복사본을 주어 내부 상태를 보호한다

## 예제 5: 직접 동기화 vs 라이브러리 사용

```java
// 가능은 하지만 장황함
public class SafeStore {

    private final Map<String, String> values = new HashMap<>();

    public synchronized void put(String key, String value) {
        values.put(key, value);
    }

    public synchronized String get(String key) {
        return values.get(key);
    }
}
```

```java
// 더 명확함
public class SafeStore {

    private final ConcurrentMap<String, String> values =
            new ConcurrentHashMap<>();

    public void put(String key, String value) {
        values.put(key, value);
    }

    public String get(String key) {
        return values.get(key);
    }
}
```

- 첫 번째 코드는 모든 접근을 하나의 락으로 막는다
- 두 번째 코드는 컬렉션 자체가 동시성에 맞게 설계되어 있다
- 의도도 더 분명하다

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드                | 좋은 코드                      |
| ------ | -------------------- | -------------------------- |
| Map    | `HashMap` 공유         | `ConcurrentHashMap`        |
| Queue  | `LinkedList` 공유      | `BlockingQueue`            |
| List   | `ArrayList` 공유       | `CopyOnWriteArrayList`     |
| 복합 연산  | `get` 후 `put`        | `merge`, `computeIfAbsent` |
| 동시성 제어 | 직접 `synchronized` 남발 | 표준 concurrent 컬렉션 사용       |


## 핵심 원칙

> **동시성 문제를 해결하는 가장 강력한 방법은 “공유를 줄이고, 책임을 분리하는 것**

**피해야 할 것:**

- HashMap, ArrayList, LinkedList를 여러 스레드가 공유하는 것
- get() 후 put() 같은 복합 연산을 안전하다고 착각하는 것
- 직접 만든 락과 큐로 동시성 문제를 해결하려는 것
- 모든 Java 라이브러리 클래스가 thread-safe하다고 가정하는 것

**지켜야 할 것:**

- java.util.concurrent 패키지를 익힌다
- 공유 컬렉션에는 thread-safe 컬렉션을 사용한다
- 생산자-소비자 구조에는 BlockingQueue를 고려한다
- 복합 갱신에는 merge, computeIfAbsent 같은 원자적 메서드를 사용한다
- 동시성 코드는 직접 만들기보다 검증된 라이브러리로 단순화한다

