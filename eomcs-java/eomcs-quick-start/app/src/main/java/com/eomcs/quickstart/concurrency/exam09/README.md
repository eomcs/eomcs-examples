# Exam09 - Spring WebFlux 비동기 예외 처리

## 개념

Spring WebFlux는 요청 처리 결과를 `Mono` 또는 `Flux`로 표현한다.
동기 코드에서는 예외가 `throw`로 즉시 전달되지만, WebFlux의 비동기 파이프라인에서는 예외가 `error signal`로 전달된다.

```
요청 수신
  → Mono/Flux 파이프라인 구성
  → WebFlux가 구독
  → 값이 나오면 onNext/onComplete
  → 실패하면 onError
```

따라서 WebFlux에서 비동기 예외를 다룰 때는 `try-catch`보다 Reactor 연산자를 사용한다.

### 주요 예외 처리 연산자

| 연산자 | 설명 |
|---|---|
| `doOnError()` | 예외를 관찰하고 로그를 남김. 복구하지 않음 |
| `onErrorReturn()` | 예외 발생 시 고정된 대체 값을 반환 |
| `onErrorResume()` | 예외 발생 시 다른 `Mono`/`Flux`로 복구 |
| `onErrorMap()` | 예외를 다른 예외로 변환 |
| `timeout()` | 지정 시간 안에 결과가 없으면 예외 발생 |
| `retryWhen()` | 실패한 작업을 정책에 따라 재시도 |

### try-catch와 다른 점

```java
Mono<Order> mono = service.findOrder(id);
```

위 코드는 아직 실행된 것이 아니다.
`Mono`는 작업 설명서에 가깝고, 실제 실행은 구독 시점에 일어난다.
그래서 다음 코드는 파이프라인 내부의 비동기 예외를 잡지 못한다.

```java
try {
  return service.findOrder(id);
} catch (Exception e) {
  return Mono.just(fallback);
}
```

대신 파이프라인에 예외 처리 연산자를 붙여야 한다.

```java
return service.findOrder(id)
    .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(fallback));
```

### App / App2 / App3 / App4 비교

| 클래스 | 핵심 주제 |
|---|---|
| `App` | `Mono`에서 `doOnError()`와 `onErrorResume()`으로 복구 |
| `App2` | 도메인 예외를 `ResponseStatusException`으로 매핑 |
| `App3` | `timeout()`, `retryWhen()`, fallback 처리 |
| `App4` | `Flux`에서 항목별 예외 처리 |

## App - Mono 기본 예외 처리

```java
Mono<Order> result =
    findOrder(id)
        .doOnError(ex -> log(ex))
        .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(fallback));
```

- `doOnError()`는 로그처럼 부수 효과를 남길 때 사용한다.
- `doOnError()`는 예외를 복구하지 않는다.
- `onErrorResume()`은 실패한 파이프라인을 다른 `Mono`로 대체한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam09.App
  ```

## App2 - HTTP 예외 매핑

```java
return service.getMember(id)
    .onErrorMap(
        UnauthorizedMemberException.class,
        ex -> new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex));
```

- 서비스 계층의 도메인 예외를 WebFlux가 이해할 수 있는 HTTP 예외로 바꾼다.
- 실제 WebFlux 서버에서는 `ResponseStatusException`이 HTTP 상태 코드로 변환된다.
- `onErrorMap()`은 예외를 변환할 뿐 정상 값으로 복구하지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam09.App2
  ```

## App3 - timeout / retry / fallback

```java
return remoteCall()
    .timeout(Duration.ofMillis(500))
    .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(200)))
    .onErrorResume(ex -> Mono.just(fallback));
```

- `timeout()`은 지정 시간 안에 결과가 없으면 실패로 처리한다.
- `retryWhen()`은 실패한 작업을 다시 구독한다.
- 재시도까지 실패하면 `onErrorResume()`으로 대체 결과를 만들 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam09.App3
  ```

## App4 - Flux 항목별 예외 처리

```java
Flux.fromIterable(orderIds)
    .flatMap(orderId ->
        createDelivery(orderId)
            .onErrorResume(ex -> Mono.just(DeliveryResult.failed(orderId))))
```

- `Flux` 바깥쪽에서만 예외를 처리하면 한 항목의 실패가 전체 스트림 실패로 이어질 수 있다.
- 항목마다 독립적으로 처리해야 한다면 `flatMap()` 내부에서 `onErrorResume()`을 적용한다.
- 이 방식은 여러 요청, 여러 메시지, 여러 DB row를 처리할 때 유용하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.concurrency.exam09.App4
  ```
