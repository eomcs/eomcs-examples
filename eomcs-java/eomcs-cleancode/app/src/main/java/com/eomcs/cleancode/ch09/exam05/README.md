# F.I.R.S.T (Fast, Independent, Repeatable, Self-validating, Timely)

> **좋은 테스트는 다음 다섯 가지 조건을 만족해야 한다.**

```text
F : Fast         (빠르게 실행된다)
I : Independent  (독립적이다)
R : Repeatable   (반복 가능하다)
S : Self-Validating (스스로 검증한다)
T : Timely       (적시에 작성된다)
```

이 다섯 가지는 단순한 체크리스트가 아니라,

- 테스트가 유지보수 가능하고 신뢰할 수 있으려면 반드시 지켜야 하는 조건이다.

## 예제 1: F — Fast (빠르게 실행)

> 테스트는 매우 빠르게 실행되어야 한다.

```java
@Test
void 사용자_조회() throws InterruptedException {
    Thread.sleep(3000); // 느린 테스트

    User user = userService.findUser(1L);

    assertEquals("kim", user.getName());
}
```

- 테스트 실행이 느려진다
- 개발자가 테스트 실행을 꺼리게 된다
- 피드백 속도가 떨어진다

```java
@Test
void 사용자_조회() {
    UserRepository fakeRepository = new FakeUserRepository();
    UserService service = new UserService(fakeRepository);

    User user = service.findUser(1L);

    assertEquals("kim", user.getName());
}
```

- 외부 시스템(DB, 네트워크) 제거
- 빠른 실행
- 자주 실행 가능

## 예제 2: I — Independent (독립적)

> 테스트는 서로 의존하면 안 된다.

```java
@Test
void 사용자_생성() {
    userService.create("kim");
}

@Test
void 사용자_조회() {
    User user = userService.find("kim"); // 이전 테스트에 의존

    assertEquals("kim", user.getName());
}
```

- 실행 순서에 의존
- 하나 실패하면 연쇄 실패
- 디버깅 어려움

```java
@Test
void 사용자_조회() {
    userService.create("kim");

    User user = userService.find("kim");

    assertEquals("kim", user.getName());
}
```

- 각 테스트가 독립적으로 실행 가능
- 순서에 영향 없음

## 예제 3: R — Repeatable (반복 가능)

> 어떤 환경에서도 동일한 결과를 내야 한다.

```java
@Test
void 현재_시간_테스트() {
    LocalDateTime now = LocalDateTime.now();

    assertEquals(10, now.getHour()); // 실행 시간에 따라 실패
}
```

- 실행 시간에 따라 결과 달라짐
- 환경 의존적

```java
@Test
void 현재_시간_테스트() {
    Clock fixedClock = Clock.fixed(
        Instant.parse("2024-01-01T10:00:00Z"),
        ZoneOffset.UTC
    );

    TimeService service = new TimeService(fixedClock);

    assertEquals(10, service.getCurrentHour());
}
```

- 시간 의존성 제거
- 항상 동일한 결과

## 예제 4: S — Self-Validating (스스로 검증)

> 테스트는 결과를 자동으로 판단해야 한다.

```java
@Test
void 사용자_출력() {
    User user = new User("kim");

    System.out.println(user.getName()); // 사람이 확인해야 함
}
```

- 사람이 결과를 판단해야 한다
- 자동화 테스트가 아니다

```java
@Test
void 사용자_이름을_검증한다() {
    User user = new User("kim");

    assertEquals("kim", user.getName());
}
```

- 테스트가 스스로 성공/실패 판단
- 자동화 가능

## 예제 5: T — Timely (적시에 작성)

> 테스트는 프로덕션 코드보다 먼저 작성되어야 한다.

```java
// 이미 구현된 코드
public int add(int a, int b) {
    return a + b;
}

// 나중에 테스트 작성
@Test
void addTest() {
    assertEquals(5, add(2, 3));
}
```

- 테스트가 설계를 이끌지 못함
- 이미 만들어진 코드 검증만 수행

```java
// Good: TDD 방식으로 테스트 먼저 작성
@Test
void 두_수를_더한다() {
    Calculator calculator = new Calculator();

    int result = calculator.add(2, 3);

    assertEquals(5, result);
}
```

```java
// 이후 작성
public int add(int a, int b) {
    return a + b;
}
```

- 테스트가 설계를 이끈다
- 불필요한 코드 방지
- 요구사항이 테스트로 표현됨

## 나쁜 코드 vs 좋은 코드

| 요소              | 나쁜 테스트         | 좋은 테스트               |
| --------------- | -------------- | -------------------- |
| Fast            | 느림 (DB, sleep) | 빠름 (Fake, In-memory) |
| Independent     | 서로 의존          | 독립 실행                |
| Repeatable      | 환경 의존          | 항상 동일 결과             |
| Self-Validating | 사람이 확인         | assert로 자동 검증        |
| Timely          | 나중에 작성         | 먼저 작성                |

## 핵심 원칙

피해야 할 것:

- 느린 테스트 (DB, 네트워크 직접 호출)
- 테스트 간 의존성
- 시간, 환경, 랜덤 값에 의존하는 테스트
- System.out.println으로 결과 확인하는 테스트
- 프로덕션 코드 이후에 테스트 작성

지켜야 할 것:

- 테스트는 매우 빠르게 실행되도록 한다
- 테스트는 항상 독립적으로 실행 가능해야 한다
- 테스트는 어떤 환경에서도 동일한 결과를 가져야 한다
- 테스트는 assert로 자동 검증되게 만든다
- 테스트는 가능한 한 먼저 작성한다 (TDD)