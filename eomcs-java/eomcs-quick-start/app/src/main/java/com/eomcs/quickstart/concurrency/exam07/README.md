# Exam07 - ForkJoinPool

## 개념

`ForkJoinPool`은 큰 작업을 작은 작업으로 나누어 병렬로 처리한 뒤, 결과를 다시 합치는 분할 정복 방식의 스레드 풀이다.
`ExecutorService`의 구현체 중 하나지만, 일반 작업 큐 하나를 여러 스레드가 공유하는 방식보다 재귀적으로 쪼개지는 작업에 더 특화되어 있다.

```
큰 작업
 ├─ 작은 작업 A
 │   ├─ 더 작은 작업 A-1
 │   └─ 더 작은 작업 A-2
 └─ 작은 작업 B
     ├─ 더 작은 작업 B-1
     └─ 더 작은 작업 B-2

fork → 작은 작업을 큐에 넣음
join → 작은 작업의 결과를 기다림
```

### 핵심 개념

| 개념 | 설명 |
|---|---|
| fork | 현재 작업을 더 작은 작업으로 나누어 비동기 실행 가능하게 만든다 |
| join | fork한 작업이 끝날 때까지 기다린 뒤 결과를 가져온다 |
| threshold | 더 이상 나누지 않고 직접 처리할 작업 크기 |
| work-stealing | 쉬는 worker가 다른 worker의 큐에서 작업을 훔쳐 와 처리하는 방식 |

### 주요 타입

| 타입 | 설명 |
|---|---|
| `ForkJoinPool` | Fork/Join 작업을 실행하는 스레드 풀 |
| `ForkJoinTask<V>` | Fork/Join 작업의 공통 부모 클래스 |
| `RecursiveTask<V>` | 결과를 반환하는 재귀 작업 |
| `RecursiveAction` | 결과를 반환하지 않는 재귀 작업 |

### ExecutorService vs ForkJoinPool

| 방식 | 특징 | 적합한 상황 |
|---|---|---|
| `ExecutorService` | 제출된 독립 작업을 스레드 풀이 실행 | 요청 처리, 배치 작업, 일반 비동기 작업 |
| `ForkJoinPool` | 큰 작업을 작은 작업으로 나누고 합침 | 배열 계산, 검색, 정렬, 재귀적 분할 정복 |
| `parallelStream()` | 내부적으로 `ForkJoinPool.commonPool()` 사용 | 간단한 컬렉션 병렬 처리 |

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `RecursiveTask` - 배열 합계 계산 |
| `App2` | `RecursiveAction` - 배열 값을 직접 변경 |
| `App3` | Work-Stealing - `getStealCount()`로 동작 관찰 |
| `App4` | `commonPool`과 `parallelStream()` |

## App - RecursiveTask

`RecursiveTask<V>`는 결과를 반환하는 Fork/Join 작업이다.

```java
class SumTask extends RecursiveTask<Long> {
  @Override
  protected Long compute() {
    if (작업이 충분히 작다) {
      return 직접계산();
    }

    SumTask left = new SumTask(...);
    SumTask right = new SumTask(...);

    left.fork();
    long rightResult = right.compute();
    long leftResult = left.join();

    return leftResult + rightResult;
  }
}
```

- 작업 크기가 `threshold` 이하이면 직접 계산한다.
- 작업이 크면 작은 작업으로 나누고 결과를 합친다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam07.App
  ```

## App2 - RecursiveAction

`RecursiveAction`은 결과를 반환하지 않는 Fork/Join 작업이다.

```java
class DiscountTask extends RecursiveAction {
  @Override
  protected void compute() {
    if (작업이 충분히 작다) {
      직접변경();
      return;
    }

    invokeAll(left, right);
  }
}
```

- 배열을 직접 수정하거나 외부 저장소에 결과를 쓰는 작업에 적합하다.
- `invokeAll(left, right)`는 여러 하위 작업을 실행하고 완료될 때까지 기다린다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam07.App2
  ```

## App3 - Work-Stealing

`ForkJoinPool`의 worker 스레드는 각자 작업 큐를 가진다.
자기 큐에 할 일이 없어지면 다른 worker의 큐에서 작업을 훔쳐 와 처리한다.

```java
ForkJoinPool pool = new ForkJoinPool(4);
int result = pool.invoke(new FibonacciTask(35));

System.out.println(pool.getStealCount());
```

- `getStealCount()`는 worker가 다른 큐에서 훔쳐 온 작업 수의 추정치를 반환한다.
- steal count가 증가하면 worker들이 작업을 나누어 처리했다는 뜻이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam07.App3
  ```

## App4 - commonPool과 parallelStream

JVM에는 공용 ForkJoinPool인 `ForkJoinPool.commonPool()`이 있다.
`parallelStream()`은 기본적으로 이 공용 풀을 사용한다.
이때 `main` 같은 호출 스레드도 일부 작업을 직접 처리할 수 있지만,
`main`이 `commonPool`의 worker 스레드로 소속되는 것은 아니다.

```java
int sum =
    numbers.parallelStream()
        .mapToInt(n -> n * n)
        .sum();
```

- `commonPool`은 애플리케이션 전체에서 공유된다.
- `ForkJoinPool.commonPool().getParallelism()` 값은 공용 풀의 worker 병렬 처리 수준이며, `main` 스레드는 포함하지 않는다.
- `parallelStream()` 출력에 `main`이 보일 수 있다. 이는 호출 스레드가 결과를 기다리는 동안 일부 작업을 직접 처리했기 때문이다.
- 공용 풀이므로 직접 `shutdown()`하지 않는다.
- 오래 걸리는 블로킹 I/O 작업을 `parallelStream()`에 많이 넣으면 다른 병렬 작업도 영향을 받을 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam07.App4
  ```
