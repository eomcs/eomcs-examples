# 동시성이 필요한 이유? (Why Concurrency?)

> **동시성은 프로그램을 더 빠르게 만들기 위한 것이 아니라, 구조를 분리하고 시스템을 더 잘 설계하기 위한 도구다**

- 대부분 사람들은 “동시성 = 성능 향상”이라고 생각한다
- 더 중요한 목적은
    - 관심사 분리 (Separation of Concerns)
    - 응답성 향상 (Responsiveness)
    - 구조 개선 (Better Design)

**핵심 의도:**

- 오래 걸리는 작업과 빠른 작업을 분리한다
- 사용자 응답을 블로킹하지 않는다
- 시스템을 더 유연하게 만든다

## 예제 1: 응답성 문제

> **동시성은 “빠르게 만드는 것”이 아니라 “기다리지 않게 만드는 것”**

```java
// Bad
public class WebServer {

    public void handleRequest(Request request) {
        saveToDatabase(request); // 오래 걸림
        sendResponse("OK");
    }

    private void saveToDatabase(Request request) {
        try {
            Thread.sleep(3000); // DB 처리 지연
        } catch (InterruptedException e) {
        }
    }
}
```

- 요청 하나 처리하는 데 3초
- 사용자 경험이 나쁨
- 다른 요청도 대기해야 함

```java
// Good
public class WebServer {

    public void handleRequest(Request request) {
        new Thread(() -> saveToDatabase(request)).start();
        sendResponse("OK");
    }
}
```

- 사용자 응답은 즉시 반환
- DB 작업은 별도 스레드에서 처리
- 시스템 응답성이 개선됨

## 예제 2: 관심사 분리

```java
// Bad
public class DataProcessor {

    public void process() {
        readData();
        transformData();
        saveData();
    }
}
```

- 모든 작업이 순차적으로 묶여 있다
- 단계별 독립성이 없다

```java
// Good
public class DataProcessor {

    public void process() {
        Thread reader = new Thread(this::readData);
        Thread transformer = new Thread(this::transformData);
        Thread saver = new Thread(this::saveData);

        reader.start();
        transformer.start();
        saver.start();
    }
}
```

- 각 단계가 독립적으로 실행된다
- 구조가 분리된다
- 시스템이 더 확장 가능해진다

## 예제 3: Myths and Misconceptions (오해와 착각)

> **동시성은 강력하지만, 대부분 사람들이 잘못 이해하고 있다**

### ❌ 오해 1: “동시성은 항상 성능을 향상시킨다”

**동시성은 공짜가 아니다:**

- 컨텍스트 스위칭 비용 존재
- 스레드 생성 비용 존재
- 락 경쟁(lock contention) 발생

```java
// Bad - 불필요한 동시성
public int sum(List<Integer> numbers) {
    return numbers.parallelStream()
            .mapToInt(i -> i)
            .sum();
}
```

- 작은 데이터에서는 오히려 느릴 수 있음
- 스레드 관리 비용이 더 큼

### ❌ 오해 2: “동시성은 항상 간단하다”

- 동기화 문제
- 경쟁 상태 (Race Condition)
- 데드락 (Deadlock)

```java
// Bad
public class Counter {

    private int count = 0;

    public void increment() {
        count++; // race condition 발생 가능
    }
}
```

- 여러 스레드가 동시에 접근하면 값이 깨진다

```java
// Good
public class Counter {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }
}
```

- 동기화가 필요하다
- 하지만 코드가 복잡해진다

### ❌ 오해 3: “동시성 버그는 드물다”

**동시성 버그는 발견하기 어렵고, 발생하면 치명적이다:**

- 재현하기 어렵다
- 특정 타이밍에서만 발생한다
- 디버깅이 매우 어렵다

```java
// Bad - 간헐적 버그
if (!list.contains(item)) {
    list.add(item);
}
```

- 두 스레드가 동시에 실행하면 중복 추가 가능

### ❌ 오해 4: “동시성을 사용하면 설계가 자동으로 좋아진다”

**동시성 버그는 발견하기 어렵고, 발생하면 치명적이다:**

- 잘못 쓰면 오히려 더 복잡해진다
- 책임 분리가 흐려질 수 있다

```java
// Bad
public void process() {
    new Thread(() -> step1()).start();
    new Thread(() -> step2()).start();
    new Thread(() -> step3()).start();
}
```

- 순서 보장이 없다
- 데이터 공유 문제 발생 가능
- 설계가 오히려 망가질 수 있음

## 동시성의 올바른 이해

동시성을 사용하는 이유:

- 응답성을 높이기 위해
- 기다림을 줄이기 위해
- 관심사를 분리하기 위해
- 시스템을 확장 가능하게 만들기 위해

## 오해 vs 현실

| 오해            | 현실           |
| ------------- | ------------ |
| 성능이 항상 좋아진다   | 오히려 느려질 수 있음 |
| 구현이 간단하다      | 매우 어렵다       |
| 버그가 적다        | 매우 찾기 어렵다    |
| 설계가 자동으로 좋아진다 | 더 복잡해질 수 있음  |

## 핵심 원칙

> **동시성은 성능 최적화 도구가 아니라, 시스템을 더 잘 구조화하기 위한 강력하지만 위험한 도구다**

**피해야 할 것:**

- 성능 향상만을 목적으로 무작정 동시성 도입
- 동기화 없이 공유 데이터 사용
- 실행 순서를 고려하지 않은 스레드 분리
- 작은 문제에 과도한 병렬 처리 적용

**지켜야 할 것:**

- 동시성의 목적을 “구조 분리”로 본다
- 비용(성능, 복잡성)을 항상 고려한다
- 공유 상태를 최소화한다
- 테스트와 설계를 더 엄격하게 한다