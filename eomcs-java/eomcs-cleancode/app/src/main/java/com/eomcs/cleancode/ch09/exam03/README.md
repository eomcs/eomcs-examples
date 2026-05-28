# 깔끔한 테스트 (Clean Tests)

> **테스트 코드는 읽기 쉬워야 한다.**

- 깨끗한 테스트의 핵심 품질은 가독성(readability)이다. 
- 테스트는 프로덕션 코드보다 더 직접적으로 “무엇을 기대하는지”를 보여줘야 한다.

또한 테스트는 보통 다음 세 단계로 읽히는 것이 좋다.

```text
BUILD   : 테스트 데이터 준비
OPERATE : 테스트 대상 실행
CHECK   : 결과 검증
```

**Domain-Specific Testing Language**:

- 테스트 코드 안에 테스트 전용 헬퍼 메서드를 만들어, 테스트가 도메인 문장처럼 읽히게 만드는 것이다.

**A Dual Standard:**

- 테스트 코드와 프로덕션 코드의 기준이 완전히 같지는 않다는 뜻이다. 
- 테스트 코드는 여전히 깨끗해야 하지만, 프로덕션 코드만큼 성능이나 메모리 효율을 엄격하게 요구하지는 않아도 된다. 
- 테스트 환경에서는 CPU나 메모리 효율보다 단순함과 표현력이 더 중요하다.

## 예제 1: Clean Tests

```java
// Bad - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void testOrder() {
        Product p = new Product("BOOK", 10000);
        Customer c = new Customer("kim@test.com", "VIP");
        Order o = new Order(c, p, 2);
        FakePaymentGateway pg = new FakePaymentGateway();
        FakeOrderRepository r = new FakeOrderRepository();
        OrderService s = new OrderService(r, pg);

        s.order(o);

        assertEquals(18000, o.getTotalPrice());
        assertEquals(OrderStatus.PAID, o.getStatus());
        assertEquals(1, r.count());
        assertTrue(pg.wasPaid());
    }
}
```

- 테스트 이름이 무엇을 검증하는지 명확하지 않다.
- p, c, o, pg, r, s 같은 변수명이 의미를 충분히 드러내지 않는다.
- 준비 코드가 길어서 핵심 검증이 잘 보이지 않는다.
- 할인, 결제, 저장을 한 테스트에서 모두 검증한다.
- 테스트가 문서처럼 읽히지 않는다.

```java
// Good - 테스트 코드
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void VIP_고객이_주문하면_할인된_금액으로_결제된다() {
        // BUILD: 테스트 데이터 준비
        Order order = vipOrder("BOOK", 10_000, 2);
        OrderService orderService = orderService();

        // OPERATE: 테스트 대상 실행
        orderService.order(order);

        // CHECK: 결과 검증
        assertEquals(18_000, order.getTotalPrice());
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    private Order vipOrder(String productName, int price, int quantity) {
        Customer vipCustomer = new Customer("kim@test.com", "VIP");
        Product product = new Product(productName, price);

        return new Order(vipCustomer, product, quantity);
    }

    private OrderService orderService() {
        return new OrderService(
            new FakeOrderRepository(),
            new FakePaymentGateway()
        );
    }
}
```

- 테스트 이름이 요구사항을 문장처럼 설명한다.
- 테스트가 준비 → 실행 → 검증 순서로 읽힌다.
- 세부 생성 코드는 헬퍼 메서드로 숨겼다.
- 테스트 본문에 핵심 의도만 남았다.
- 실패했을 때 어떤 요구사항이 깨졌는지 알기 쉽다.

## 예제 2: Domain-Specific Testing Language

```java
// Bad - 테스트 코드
@Test
void 온도가_너무_낮으면_히터와_송풍기와_저온경보가_켜진다() {
    Hardware hardware = new Hardware();
    EnvironmentController controller = new EnvironmentController(hardware);

    hardware.setTemperature(-10);
    controller.tick();

    assertTrue(hardware.isHeaterOn());
    assertTrue(hardware.isBlowerOn());
    assertFalse(hardware.isCoolerOn());
    assertFalse(hardware.isHighTempAlarmOn());
    assertTrue(hardware.isLowTempAlarmOn());
}
```

- 검증 코드가 길어서 기대 상태를 한눈에 보기 어렵다.
- assertTrue, assertFalse를 번갈아 읽어야 한다.
- 테스트의 관심사는 “너무 추우면 어떤 장치가 켜지는가”인데, 세부 장치 확인 코드가 과하게 드러난다.
- 비슷한 테스트가 많아지면 중복이 커진다.

```java
// Good - 테스트 코드
@Test
void 온도가_너무_낮으면_히터와_송풍기와_저온경보가_켜진다() {
    wayTooCold();

    assertEquals("HBchL", hardwareState());
}

private void wayTooCold() {
    hardware.setTemperature(-10);
    controller.tick();
}

private String hardwareState() {
    String state = "";

    state += hardware.isHeaterOn() ? "H" : "h";
    state += hardware.isBlowerOn() ? "B" : "b";
    state += hardware.isCoolerOn() ? "C" : "c";
    state += hardware.isHighTempAlarmOn() ? "H" : "h";
    state += hardware.isLowTempAlarmOn() ? "L" : "l";

    return state;
}
```

이 예제에서 문자열 의미는 다음과 같다.

```text
H/h : Heater 켜짐/꺼짐
B/b : Blower 켜짐/꺼짐
C/c : Cooler 켜짐/꺼짐
H/h : HighTempAlarm 켜짐/꺼짐
L/l : LowTempAlarm 켜짐/꺼짐
```

대문자는 켜짐, 소문자는 꺼짐을 뜻하는 테스트 전용 표현을 만들어 테스트를 더 짧고 읽기 쉽게 만든다.

- wayTooCold()가 테스트 상황을 도메인 언어로 표현한다.
- hardwareState()가 복잡한 검증을 테스트 전용 언어로 바꾼다.
- 테스트 본문이 짧아진다.
- 여러 테스트에서 같은 표현을 재사용할 수 있다.
- 테스트가 “기계 조작 코드”가 아니라 “요구사항 문장”처럼 읽힌다.

## 예제 3: A Dual Standard

```java
// Bad - 테스트 헬퍼 코드
private String hardwareState() {
    StringBuilder state = new StringBuilder(5);

    if (hardware.isHeaterOn()) {
        state.append("H");
    } else {
        state.append("h");
    }

    if (hardware.isBlowerOn()) {
        state.append("B");
    } else {
        state.append("b");
    }

    if (hardware.isCoolerOn()) {
        state.append("C");
    } else {
        state.append("c");
    }

    if (hardware.isHighTempAlarmOn()) {
        state.append("H");
    } else {
        state.append("h");
    }

    if (hardware.isLowTempAlarmOn()) {
        state.append("L");
    } else {
        state.append("l");
    }

    return state.toString();
}
```

- 성능을 조금 고려했지만 코드가 길고 산만하다.
- 테스트 헬퍼인데도 읽는 비용이 크다.
- 테스트의 목적보다 구현 세부사항이 더 많이 보인다.
- 프로덕션 코드 기준을 기계적으로 적용해 테스트 가독성이 떨어졌다.

```java
// Good - 테스트 헬퍼 코드
private String hardwareState() {
    String state = "";

    state += hardware.isHeaterOn() ? "H" : "h";
    state += hardware.isBlowerOn() ? "B" : "b";
    state += hardware.isCoolerOn() ? "C" : "c";
    state += hardware.isHighTempAlarmOn() ? "H" : "h";
    state += hardware.isLowTempAlarmOn() ? "L" : "l";

    return state;
}
```

- 테스트 환경에서는 이 정도 문자열 결합 비용이 문제가 되지 않을 수 있다.
- 코드가 짧고 읽기 쉽다.
- 테스트의 표현력이 좋아진다.
- 성능보다 가독성을 우선했다.
- 단, 깨끗함을 포기한 것은 아니다.

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 테스트          | 좋은 테스트                 |
| ------ | --------------- | ---------------------- |
| 핵심 가치  | 동작만 확인          | 읽기 쉬움                  |
| 테스트 구조 | 준비/실행/검증이 섞임    | BUILD/OPERATE/CHECK 구분 |
| 표현 방식  | API 세부 호출 중심    | 도메인 언어 중심              |
| 헬퍼 메서드 | 단순 중복 제거        | 테스트 전용 언어 형성           |
| 성능 기준  | 프로덕션 기준을 그대로 적용 | 테스트 환경에 맞게 완화          |
| 청결 기준  | 대충 작성           | 여전히 단순하고 명확하게 작성       |


## 핵심 원칙

피해야 할 것:

- 테스트 코드를 단순 검증 스크립트처럼 작성하는 것
- 테스트 이름을 모호하게 짓는 것
- assertTrue, assertFalse가 길게 나열되어 의도를 흐리는 것
- 테스트 본문에 세부 설정과 검증 로직을 모두 노출하는 것
- 테스트 코드에 프로덕션 코드와 동일한 성능 기준을 기계적으로 적용하는 것

지켜야 할 것:

- 테스트는 읽기 쉬워야 한다.
- 테스트 본문은 준비, 실행, 검증 흐름으로 정리한다.
- 테스트 헬퍼 메서드로 도메인 특화 테스트 언어를 만든다.
- 테스트에서는 성능보다 단순함과 표현력을 우선할 수 있다.
- 단, 테스트 코드도 반드시 깨끗하고 명확해야 한다.