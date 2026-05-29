# 동기화하는 메서드 사이에 존재하는 의존성을 이해하라 (Beware Dependencies Between Synchronized Methods)

> **메서드 하나하나가 synchronized여도, 여러 메서드를 연속해서 호출하는 코드는 안전하지 않을 수 있다**

- synchronized가 개별 메서드는 보호하지만, 
- 같은 공유 객체의 여러 synchronized 메서드를 조합해서 쓰면 미묘한 동시성 버그가 생길 수 있다. 
- 그래서 가능하면 공유 객체에서 여러 메서드를 연속 호출하지 말라.

## 예제 1

```java
// Bad
public class NumberIterator {

    private final List<Integer> numbers = List.of(1, 2, 3);
    private int index = 0;

    public synchronized boolean hasNext() {
        return index < numbers.size();
    }

    public synchronized int next() {
        return numbers.get(index++);
    }
}
```

```java
// Bad - 클라이언트 코드
NumberIterator iterator = new NumberIterator();

while (iterator.hasNext()) {
    int number = iterator.next();
    System.out.println(number);
}
```

- hasNext()는 synchronized다
- next()도 synchronized다
- 하지만 hasNext()와 next() 사이 전체는 보호되지 않는다

문제는 다음 상황에서 발생한다.

```text
Thread A: hasNext() → true
Thread B: hasNext() → true
Thread B: next() → 마지막 값 반환
Thread A: next() → 더 이상 값 없음, 예외 발생 가능
```

- 각 메서드는 안전하지만, 두 메서드 사이의 관계는 안전하지 않다.

### 왜 문제가 되는가?

```java
while (iterator.hasNext()) {
    int number = iterator.next();
}
```

- 이 코드는 실제로는 하나의 논리적 작업이다.
    1. 다음 값이 있는지 확인한다
    2. 있으면 가져온다
- 하지만 락은 이렇게 잡힌다.
    - hasNext() 호출 중에만 lock
    - next() 호출 중에만 lock
- 즉, 중간에 다른 스레드가 끼어들 수 있다.
    - hasNext() lock 획득 → 해제
    - 다른 스레드 실행 가능:
        - next() lock 획득 → 실행

### 해결 1: Client-Based Locking

> **클라이언트가 공유 객체 전체 사용 구간을 직접 잠근다**

- 클라이언트가 서버 객체를 잠그고, 첫 메서드 호출부터 마지막 메서드 호출까지 같은 락 범위 안에 넣는다. 
- 하지만 이 방식은 모든 클라이언트가 같은 규칙을 지켜야 하므로 위험하고 DRY 위반이 될 수 있다.

```java
// Good but risky
NumberIterator iterator = new NumberIterator();

while (true) {
    int number;

    synchronized (iterator) {
        if (!iterator.hasNext()) {
            break;
        }

        number = iterator.next();
    }

    System.out.println(number);
}
```

- hasNext()와 next()가 하나의 락 범위에 들어간다
- 중간에 다른 스레드가 끼어들 수 없다
- 하지만 모든 클라이언트가 이 규칙을 알아야 한다
- 한 곳이라도 synchronized (iterator)를 빠뜨리면 다시 버그가 생긴다

### 해결 2: Server-Based Locking

> **공유 객체 내부에 안전한 단일 메서드를 만든다**

```java
// Good
public class NumberIterator {

    private final List<Integer> numbers = List.of(1, 2, 3);
    private int index = 0;

    public synchronized Integer nextOrNull() {
        if (index >= numbers.size()) {
            return null;
        }

        return numbers.get(index++);
    }
}
```

```java
// Good - 클라이언트
NumberIterator iterator = new NumberIterator();

while (true) {
    Integer number = iterator.nextOrNull();

    if (number == null) {
        break;
    }

    System.out.println(number);
}
```

- 확인과 가져오기를 하나의 synchronized 메서드로 묶었다
- 클라이언트는 여러 메서드를 조합하지 않는다
- 동시성 규칙이 서버 객체 내부에 있다
- 클라이언트 실수 가능성이 줄어든다

### 해결 3: Adapted Server

> **기존 클래스를 바꿀 수 없다면, 어댑터가 잠금 책임을 맡게 한다**

```java
public class SafeNumberIterator {

    private final NumberIterator iterator;

    public SafeNumberIterator(NumberIterator iterator) {
        this.iterator = iterator;
    }

    public Integer nextOrNull() {
        synchronized (iterator) {
            if (!iterator.hasNext()) {
                return null;
            }

            return iterator.next();
        }
    }
}
```

```java
SafeNumberIterator iterator =
        new SafeNumberIterator(new NumberIterator());

while (true) {
    Integer number = iterator.nextOrNull();

    if (number == null) {
        break;
    }

    System.out.println(number);
}
```

- 원래 NumberIterator를 수정하지 않아도 된다
- 잠금 규칙을 어댑터에 모은다
- 클라이언트는 안전한 메서드 하나만 호출한다

## 핵심 비교

| 방식                   | 설명                    | 단점                  |
| -------------------- | --------------------- | ------------------- |
| Client-Based Locking | 클라이언트가 직접 락을 건다       | 모든 클라이언트가 규칙을 알아야 함 |
| Server-Based Locking | 서버 객체가 안전한 단일 메서드를 제공 | 서버 클래스 수정 필요        |
| Adapted Server       | 어댑터가 락을 대신 관리         | 어댑터 추가 필요           |

## 핵심 원칙

**피해야 할 것:**

- synchronized 메서드 여러 개를 조합하면 안전하다고 착각하는 것
- hasNext() 후 next()처럼 상태 확인과 사용을 분리하는 것
- 클라이언트마다 락 규칙을 반복해서 작성하는 것

**지켜야 할 것:**

- 공유 객체에서는 가능하면 메서드 하나로 작업을 끝낸다
- 여러 메서드를 반드시 호출해야 한다면 같은 락 범위로 묶는다
- 가능하면 동시성 제어는 서버 객체나 어댑터 내부로 숨긴다

