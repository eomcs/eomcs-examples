# 난관 (Challenges)

> **동시성은 단순한 기능 추가가 아니라, 프로그램의 복잡도를 근본적으로 증가시키는 문제다**

- 단일 스레드에서는 “한 줄씩 순서대로 실행”이라는 가정이 가능하다
- 동시성에서는 이 가정이 깨진다
- 실행 순서가 예측 불가능해진다
- 작은 코드도 매우 복잡한 문제로 변한다

**핵심 의도:**

- 동시성 문제는 “코드 길이”가 아니라 “상호작용”에서 발생한다
- 특히 공유 상태(shared state)가 핵심 위험 요소다
- 동시성은 설계, 테스트, 디버깅을 모두 어렵게 만든다

## 예제 1: 공유 자원 문제 (Shared Resource Problem)

> **여러 스레드가 같은 데이터를 동시에 접근하면 문제가 발생한다**

```java
// Bad
public class Counter {

    private int count = 0;

    public void increment() {
        count++; // 원자적 연산이 아님
    }
}
```

- **문제: count++는 실제로는 3단계로 이루어진다**
  1. 읽기
  2. 증가
  3. 저장
- 여러 스레드가 동시에 실행하면 값이 깨짐

```java
// Good
public class Counter {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }
}
```

- 공유 상태는 반드시 보호해야 한다
- 하지만 동기화는 또 다른 문제를 만든다 (성능, 복잡도)

## 예제 2: 실행 순서 문제 (Non-determinism)

> **동시성에서는 실행 순서를 예측할 수 없다**

```java
// Bad
public class Example {

    private int value = 0;

    public void thread1() {
        value = 1;
    }

    public void thread2() {
        System.out.println(value);
    }
}
```

- thread2가 먼저 실행되면 0 출력
- 나중에 실행되면 1 출력
- 결과가 일정하지 않다
- **동시성 프로그램은 항상 같은 결과를 보장하지 않는다**

## 예제 3: 경쟁 조건 (Race Condition)

> **결과가 실행 타이밍에 따라 달라지는 문제**

```java
// Bad
public class Inventory {

    private int stock = 10;

    public void sell() {
        if (stock > 0) {
            stock--;
        }
    }
}
```

- 문제:
    - 두 스레드가 동시에 stock > 0 검사
    - 둘 다 true → 둘 다 감소
    - 결과: 재고 -1
- **조건 검사 + 변경은 반드시 원자적으로 처리해야 한다**

## 예제 4: 데드락 (Deadlock)

> **서로 자원을 기다리면서 영원히 멈추는 상태**

```java
// Bad
public class DeadlockExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println("A");
            }
        }
    }

    public void methodB() {
        synchronized (lock2) {
            synchronized (lock1) {
                System.out.println("B");
            }
        }
    }
}
```

- 문제:
    - Thread1: lock1 → lock2 대기
    - Thread2: lock2 → lock1 대기
    - 서로 기다리며 멈춤
- **락 순서를 일관되게 유지해야 한다**

## 예제 5: 테스트의 어려움

> **동시성 버그는 재현하기 어렵다**

```java
// Bad
public void addIfNotExists(List<String> list, String item) {
    if (!list.contains(item)) {
        list.add(item);
    }
}
```

- 문제:
    - 두 스레드가 동시에 실행하면 중복 추가 가능
    - 테스트에서는 잘 안 나타남
    - 운영에서만 터질 수 있음
- **동시성 버그는 타이밍 의존적**
- **테스트가 매우 어렵다**

## 예제 6: 성능 vs 안전성 트레이드오프

> **동기화를 하면 안전하지만, 성능이 떨어진다**

```java
// 안전하지만 느림
public synchronized void increment() {
    count++;
}
```

```java
// 빠르지만 안전하지 않음
public void increment() {
    count++;
}
```

- 문제:
    - 안전 vs 성능 선택 필요
    - 둘을 동시에 만족시키기 어렵다
- **공유 상태 최소화**
- **불변 객체 사용**
- **Lock-free 구조 고려**

## 예제 7: 설계 복잡도 증가

> **동시성은 설계를 비선형적으로 복잡하게 만든다**

```java
// Bad
public void process() {
    new Thread(() -> step1()).start();
    new Thread(() -> step2()).start();
    new Thread(() -> step3()).start();
}
```

- 문제:
    - 실행 순서 보장 없음
    - 데이터 공유 문제 발생 가능
    - 디버깅 어려움
- **코드가 짧아도 이해는 어려움**
- **동시성은 “숨은 복잡성”을 만든다**

## 나쁜 상황 vs 좋은 대응

| 문제    | 위험     | 대응                      |
| ----- | ------ | ----------------------- |
| 공유 상태 | 데이터 깨짐 | synchronized, immutable |
| 실행 순서 | 결과 불확정 | 명시적 제어                  |
| 경쟁 조건 | 버그 발생  | 원자성 보장                  |
| 데드락   | 시스템 멈춤 | 락 순서 통일                 |
| 테스트   | 재현 어려움 | 설계로 해결                  |
| 성능    | 느려짐    | 최소 동기화                  |

## 핵심 원칙

> **동시성의 핵심 도전은 “코드가 아니라, 동시에 실행되는 것들의 상호작용”이다**

**피해야 할 것:**

- 공유 데이터를 아무 제어 없이 사용하는 것
- 락을 아무 규칙 없이 사용하는 것
- 동시성 문제를 “운에 맡기는 것”
- 테스트만으로 동시성 문제를 잡으려는 것

**지켜야 할 것:**

- 공유 상태를 최소화한다
- 가능한 불변 객체를 사용한다
- 동기화 범위를 최소화한다
- 락 순서를 일관되게 유지한다
- 설계 단계에서 동시성 문제를 고려한다