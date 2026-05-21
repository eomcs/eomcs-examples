# 한 개념에 한 단어를 사용하라(Pick One Word per Concept)

> **하나의 개념에는 하나의 단어만 사용하라**

같은 의미를 가진 동작에 대해 여러 단어를 섞어 쓰면 코드를 사용하는 사람이 혼란스러워진다.

## 예제 1

```java
// Bad
class UserRepository {
    User getUser(String id) { ... }
}

class OrderRepository {
    Order fetchOrder(String id) { ... }
}

class ProductRepository {
    Product retrieveProduct(String id) { ... }
}
```

- 위 코드는 모두 “ID로 객체를 가져온다”는 같은 개념을 표현한다.
- 하지만 메서드 이름이 각각 다르다.
    - getUser
    - fetchOrder
    - retrieveProduct
- 읽는 사람은 다음을 고민하게 된다.
    - get, fetch, retrieve는 서로 다른 의미인가?
- 실제로 차이가 없다면, 이름만 다르게 만든 것이다.

```java
// Good
class UserRepository {
    User findById(String id) { ... }
}

class OrderRepository {
    Order findById(String id) { ... }
}

class ProductRepository {
    Product findById(String id) { ... }
}
```

- 같은 개념에 같은 단어 사용
- API 사용자가 예측 가능
- IDE 자동완성에서도 일관성 향상
- 코드 탐색 비용 감소

## 예제 2

```java
// Bad
class UserService {
    void deleteUser(String id) { ... }
}

class OrderService {
    void removeOrder(String id) { ... }
}

class CartService {
    void eraseItem(String id) { ... }
}
```

- 모두 “삭제”라는 개념인데 단어가 다르다.
    - delete
    - remove
    - erase
- 이름이 다르면 읽는 사람은 의미 차이를 의심한다.

```java
// Good
class UserService {
    void deleteUser(String id) { ... }
}

class OrderService {
    void deleteOrder(String id) { ... }
}

class CartService {
    void deleteItem(String id) { ... }
}
```

- delete라는 하나의 단어로 삭제 개념을 통일했다.
- 따라서 코드를 읽는 사람은 다음처럼 이해할 수 있다.
    - *"이 시스템에서 삭제 동작은 delete로 표현하는구나."*

```java
// Good
class Cart {
    void addItem(Item item) { ... }
}

class NumberCalculator {
    int sum(int a, int b) { ... }
}
```

- 의미가 다르면 단어도 달라야 한다.
- 여기서 addItem과 sum은 서로 다른 개념이다.
    - addItem: 컬렉션에 항목 추가
    - sum: 숫자 계산
- 따라서 억지로 같은 단어를 쓰면 안 된다.

## 예제 3: 클래스 이름에서도 동일하게 적용

```java
// Bad
class UserManager { }
class OrderController { }
class PaymentHandler { }
```

- Manager, Controller, Handler가 각각 다른 의미인지 불명확하다.
- 실제로 모두 “업무 흐름을 처리하는 객체”인데 이름이 일관되지 않는다.

```java
// Good
class UserService { }
class OrderService { }
class PaymentService { }
```

- 같은 역할의 클래스에는 같은 접미사를 사용한다.

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드                         | 좋은 코드       |
| --- | ----------------------------- | ----------- |
| 조회  | get / fetch / retrieve 혼용     | findById 통일 |
| 삭제  | delete / remove / erase 혼용    | delete 통일   |
| 클래스 | Manager / Handler / Processor | Service 통일  |
| 특징  | 혼란, 해석 필요                     | 예측 가능, 직관적  |


## 핵심 원칙

피해야 할 것:

- 같은 개념에 여러 단어 사용
- get, fetch, retrieve 혼용
- delete, remove, erase 혼용
- 역할이 같은 클래스에 서로 다른 접미사 사용

지켜야 할 것:

- 하나의 개념에는 하나의 단어 사용
- 팀 전체에서 용어 사전 공유
- API 이름을 예측 가능하게 만들기
- 의미가 다를 때만 다른 단어 사용