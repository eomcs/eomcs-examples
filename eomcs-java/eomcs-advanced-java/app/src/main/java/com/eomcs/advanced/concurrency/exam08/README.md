# Exam08 - CompletableFuture

## 개념

`CompletableFuture`는 비동기 작업의 결과를 표현하고, 그 결과를 바탕으로 다음 작업을 이어 붙일 수 있게 해 주는 도구다.
기존 `Future`는 결과를 받으려면 `get()`으로 기다려야 했지만, `CompletableFuture`는 결과 변환, 후속 작업, 여러 작업 조합, 예외 처리를 체인으로 구성할 수 있다.

```
비동기 작업 시작
  → 결과 변환
  → 다른 비동기 작업과 조합
  → 예외 처리
  → 최종 결과 사용
```

### Future vs CompletableFuture

| 방식 | 특징 |
|---|---|
| `Future` | 결과를 나중에 받을 수 있지만, 후속 작업 연결이 불편하다 |
| `CompletableFuture` | 결과를 변환하고 여러 비동기 작업을 조합할 수 있다 |

### 주요 생성 메서드

| 메서드 | 설명 |
|---|---|
| `runAsync(Runnable)` | 결과 없는 작업을 비동기로 실행 |
| `supplyAsync(Supplier<T>)` | 결과를 만드는 작업을 비동기로 실행 |
| `completedFuture(value)` | 이미 완료된 `CompletableFuture` 생성 |

별도 `Executor`를 지정하지 않으면 `ForkJoinPool.commonPool()`에서 실행된다.
블로킹 I/O가 많거나 실행 정책을 분리해야 한다면 직접 만든 `ExecutorService`를 넘기는 편이 좋다.

### 주요 후속 작업 메서드

| 메서드 | 설명 |
|---|---|
| `thenApply()` | 이전 결과를 받아 새 결과로 변환 |
| `thenAccept()` | 이전 결과를 소비하고 결과를 반환하지 않음 |
| `thenRun()` | 이전 결과를 받지 않고 다음 작업 실행 |
| `thenCompose()` | 비동기 작업을 이어 붙여 중첩 future를 평평하게 만듦 |
| `thenCombine()` | 독립적인 두 future 결과를 합침 |
| `allOf()` | 여러 future가 모두 끝날 때까지 대기 |
| `anyOf()` | 여러 future 중 하나라도 끝나면 완료 |

### 예외 처리 메서드

| 메서드 | 설명 |
|---|---|
| `exceptionally()` | 예외 발생 시 대체 결과 반환 |
| `handle()` | 성공 결과와 예외를 모두 받아 새 결과 반환 |
| `whenComplete()` | 성공 결과나 예외를 관찰. 결과 자체는 바꾸지 않음 |

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `runAsync()`, `supplyAsync()`, `join()` 기본 |
| `App2` | `thenApply()`, `thenAccept()`, `thenRun()` 체이닝 |
| `App3` | `thenCombine()`, `allOf()`, `anyOf()` 작업 조합 |
| `App4` | `exceptionally()`, `handle()`, `whenComplete()` 예외 처리 |

## App - runAsync / supplyAsync

```java
CompletableFuture<Void> f1 =
    CompletableFuture.runAsync(() -> {
      // 결과 없는 비동기 작업
    });

CompletableFuture<Integer> f2 =
    CompletableFuture.supplyAsync(() -> {
      return 100;
    });

f1.join();
Integer result = f2.join();
```

- `runAsync()`는 결과가 없는 작업에 사용한다.
- `supplyAsync()`는 결과를 만드는 작업에 사용한다.
- `join()`은 작업 완료까지 기다리고 결과를 반환한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App
  ```

## App2 - 결과 변환과 체이닝

```java
CompletableFuture<Void> future =
    CompletableFuture.supplyAsync(() -> findMember())
        .thenApply(member -> applyDiscount(member))
        .thenAccept(amount -> System.out.println(amount))
        .thenRun(() -> System.out.println("done"));
```

- `thenApply()`는 이전 결과를 받아 다른 값으로 변환한다.
- `thenAccept()`는 이전 결과를 소비하고 값을 반환하지 않는다.
- `thenRun()`은 이전 결과를 받지 않고 다음 작업만 실행한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App2
  ```

## App3 - 여러 작업 조합

```java
CompletableFuture<Integer> total =
    productPrice.thenCombine(deliveryFee, Integer::sum);
```

- `thenCombine()`은 서로 독립적인 두 작업 결과를 합친다.

```java
CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
all.join();
```

- `allOf()`는 여러 작업이 모두 끝날 때까지 기다린다.
- `allOf()` 자체는 개별 결과를 모아 주지 않으므로 각 future에서 `join()`으로 꺼낸다.

```java
Object fastest = CompletableFuture.anyOf(f1, f2, f3).join();
```

- `anyOf()`는 여러 작업 중 하나라도 먼저 끝나면 완료된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App3
  ```

## App4 - 예외 처리

```java
CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> loadPrice())
        .exceptionally(ex -> 0);
```

- `exceptionally()`는 예외가 발생했을 때 대체 결과를 만든다.

```java
CompletableFuture<String> future =
    CompletableFuture.supplyAsync(() -> loadStock())
        .handle((stock, ex) -> ex != null ? "실패" : "재고: " + stock);
```

- `handle()`은 성공과 실패를 모두 처리하고 새 결과를 만든다.
- `whenComplete()`는 결과나 예외를 기록할 때 사용한다. 결과 자체를 복구하지는 않는다.
- `join()`은 예외를 `CompletionException`으로 감싸서 던진다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.concurrency.exam08.App4
  ```
