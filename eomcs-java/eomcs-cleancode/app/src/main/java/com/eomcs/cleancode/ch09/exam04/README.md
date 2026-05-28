# 테스트에 assert 하나 (One Assert per Test)

> **테스트 하나는 하나의 개념만 검증해야 한다.**

- assert 개수가 꼭 1개여야 한다는 뜻이 아니다.
- 테스트 하나가 여러 요구사항을 동시에 검증하지 않도록 하라는 의미다. 
- “하나의 assert” 원칙은 좋은 지침이지만, 
- 더 나은 규칙은 테스트당 하나의 개념만 테스트하는 것이다.

## 예제 1

```java
// Bad - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void 사용자_정보를_검증한다() {
        User user = new User("kim", "kim@example.com", 20);

        assertEquals("kim", user.getName());
        assertEquals("kim@example.com", user.getEmail());
        assertEquals(20, user.getAge());
    }
}
```

- 이름, 이메일, 나이를 한 테스트에서 모두 검증한다.
- 실패했을 때 어떤 개념이 깨졌는지 테스트 이름만으로 알기 어렵다.
- 테스트 이름이 너무 포괄적이다.
- 하나의 테스트가 여러 이유로 실패할 수 있다.

```java
// Good - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void 사용자는_이름을_가진다() {
        User user = new User("kim", "kim@example.com", 20);

        assertEquals("kim", user.getName());
    }

    @Test
    void 사용자는_이메일을_가진다() {
        User user = new User("kim", "kim@example.com", 20);

        assertEquals("kim@example.com", user.getEmail());
    }

    @Test
    void 사용자는_나이를_가진다() {
        User user = new User("kim", "kim@example.com", 20);

        assertEquals(20, user.getAge());
    }
}
```

- 테스트 이름이 하나의 기대를 명확히 말한다.
- 실패 원인이 즉시 드러난다.
- 각 테스트가 하나의 개념에 집중한다.
- 테스트가 문서처럼 읽힌다.

## 예제 2

```java
// Bad - 테스트 코드
@Test
void 주문을_처리한다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(20_000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
    assertTrue(order.isSaved());
    assertTrue(order.isNotificationSent());
}
```

- 총액 계산, 결제 상태, 저장 여부, 알림 발송을 모두 검증한다.
- 테스트가 여러 책임을 가진다.
- 실패 시 어떤 요구사항이 깨졌는지 파악하기 어렵다.
- 기능이 변경될 때 테스트가 자주 깨진다.

```java
// Good - 테스트 코드
@Test
void 주문하면_총액을_계산한다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(20_000, order.getTotalPrice());
}

@Test
void 주문하면_결제완료_상태가_된다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(OrderStatus.PAID, order.getStatus());
}

@Test
void 주문하면_알림을_보낸다() {
    FakeNotificationSender notificationSender = new FakeNotificationSender();
    OrderService service = new OrderService(notificationSender);
    Order order = new Order("BOOK", 2, 10_000);

    service.place(order);

    assertTrue(notificationSender.wasSent());
}
```

- 테스트 하나가 하나의 동작만 검증한다.
- 테스트 이름이 요구사항을 직접 표현한다.
- 실패 원인이 분명하다.
- 변경 영향이 특정 테스트에만 머문다.

## 예제 3: 여러 assert가 허용되는 경우

```java
// Bad - 억지로 assert 하나에 모든 것을 합침
@Test
void 좌표를_이동한다() {
    Point point = new Point(1, 2);

    Point moved = point.move(3, 4);

    assertEquals("4,6", moved.getX() + "," + moved.getY());
}
```

- assert는 하나지만 표현이 부자연스럽다.
- 실패했을 때 x가 문제인지 y가 문제인지 알기 어렵다.
- 테스트 가독성이 나쁘다.
- “One Assert”를 기계적으로 적용했다.

```java
// Good - 하나의 개념을 검증하는 여러 assert
@Test
void 좌표를_이동한다() {
    Point point = new Point(1, 2);

    Point moved = point.move(3, 4);

    assertEquals(4, moved.getX());
    assertEquals(6, moved.getY());
}
```

- x, y 두 assert가 있지만 “좌표 이동”이라는 하나의 개념을 검증한다.
- 실패 지점이 더 명확하다.
- 테스트가 자연스럽게 읽힌다.
- 중요한 것은 assert 개수보다 테스트 개념의 단일성이다.

## 예제 4: Single Concept per Test

```java
// Bad - 여러 개념을 한 테스트에 넣음
@Test
void 회원가입_검증() {
    UserService service = new UserService();

    assertThrows(InvalidEmailException.class, () -> {
        service.register("bad-email", "password123");
    });

    assertThrows(WeakPasswordException.class, () -> {
        service.register("kim@example.com", "123");
    });

    User user = service.register("kim@example.com", "password123");

    assertEquals("kim@example.com", user.getEmail());
}
```

- 이메일 검증, 비밀번호 검증, 정상 가입을 한 테스트에서 모두 다룬다.
- 테스트 이름이 너무 넓다.
- 테스트가 여러 이유로 실패할 수 있다.
- 하나의 요구사항 문서로 읽히지 않는다.

```java
// Good - 테스트 코드
@Test
void 이메일_형식이_잘못되면_예외를_던진다() {
    UserService service = new UserService();

    assertThrows(InvalidEmailException.class, () -> {
        service.register("bad-email", "password123");
    });
}

@Test
void 비밀번호가_약하면_예외를_던진다() {
    UserService service = new UserService();

    assertThrows(WeakPasswordException.class, () -> {
        service.register("kim@example.com", "123");
    });
}

@Test
void 올바른_정보로_회원가입하면_사용자를_생성한다() {
    UserService service = new UserService();

    User user = service.register("kim@example.com", "password123");

    assertEquals("kim@example.com", user.getEmail());
}
```

- 테스트 하나가 하나의 개념만 설명한다.
- 실패 원인이 명확하다.
- 테스트 이름이 요구사항 목록처럼 읽힌다.
- 테스트 유지보수가 쉬워진다.

## 나쁜 코드 vs 좋은 코드

| 구분        | 나쁜 테스트           | 좋은 테스트       |
| --------- | ---------------- | ------------ |
| 기준        | assert 개수만 줄이려 함 | 하나의 개념에 집중   |
| 테스트 이름    | 포괄적              | 구체적 요구사항     |
| 실패 원인     | 여러 가능성           | 하나로 좁혀짐      |
| assert 사용 | 많거나 억지로 합침       | 필요한 만큼 자연스럽게 |
| 유지보수      | 변경에 취약           | 변경 영향이 작음    |
| 가독성       | 테스트 의도 파악 어려움    | 문서처럼 읽힘      |

## 핵심 원칙

피해야 할 것:

- 여러 개념을 한 테스트에서 검증하는 것
- 테스트 이름을 포괄적으로 짓는 것
- assert 하나를 만들기 위해 값을 억지로 합치는 것
- 하나의 테스트가 여러 이유로 실패하게 만드는 것
- 준비, 실행, 검증이 너무 많은 요구사항을 포함하는 것

지켜야 할 것:

- 가능하면 테스트 하나에 하나의 assert를 둔다.
- 더 중요한 기준은 테스트 하나에 하나의 개념만 두는 것이다.
- 여러 assert가 같은 개념을 설명한다면 허용할 수 있다.
- 테스트 이름은 검증하는 개념을 문장처럼 표현한다.
- 테스트 실패 이유가 명확하게 드러나도록 작게 나눈다.