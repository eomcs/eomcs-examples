# 클래스 이름(Class Names)

> **클래스 이름은 “무엇인가(What)”를 나타내는 명사여야 한다**

- 클래스는 객체의 개념과 역할을 표현한다
- 이름만 보고도 **무슨 책임을 가지는지 알 수 있어야 한다**

    
## 예제 1: 모호한 이름

```java
// Bad
class Manager { }
class Processor { }
class Data { }
class Info { }
```

- Manager → 무엇을 관리하는가?
- Processor → 무엇을 처리하는가?
- Data, Info → 너무 일반적

👉 책임이 전혀 드러나지 않음

```java
// Good
class Customer { }
class Order { }
class Invoice { }
class Address { }
```

- 도메인 개념을 그대로 표현
- 객체의 역할이 명확함
- 읽는 즉시 이해 가능

## 예제 2: 책임이 드러나지 않는 클래스

```java
// Bad
class UserManager {
    void save(User user) { }
    void delete(User user) { }
    void sendEmail(User user) { }
}
```

- 너무 많은 책임을 가짐
- "Manager"는 의미가 없음
- 클래스 역할이 불명확

```java
// Good
class UserRepository {
    void save(User user) { }
    void delete(User user) { }
}

class EmailService {
    void sendEmail(User user) { }
}
```

- 역할별로 클래스 분리
- 이름이 책임을 설명

## 예제 3: 불필요한 일반 단어 포함

```java
// Bad
class AccountData { }
class AccountInfo { }
class AccountObject { }
```

- Data, Info, Object는 의미 없음
- 이름만 다르고 역할은 불명확

```java
// Good
class Account { }
class AccountSummary { }
class AccountDetails { }
```

- 실제 의미를 반영
- 차이를 명확히 표현

## 예제 4: 클래스 이름에 동사 사용

```java
// Bad
class SaveUser { }
class CalculatePrice { }
class ProcessOrder { }
```

- 클래스가 아니라 "행동"처럼 보임
- 역할이 혼동됨

```java
// Good
class UserSaver { }
class PriceCalculator { }
class OrderProcessor { }
```

- 명사형으로 변경
- 객체의 역할 강조

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 이름     | 문제     | 좋은 이름          |
| --- | --------- | ------ | -------------- |
| 일반  | Manager   | 역할 불명확 | UserService    |
| 데이터 | Data      | 의미 없음  | Customer       |
| 처리  | Processor | 모호함    | OrderProcessor |
| 정보  | Info      | 불명확    | AccountDetails |


## 핵심 원칙

피해야 할 것:

- Manager
- Processor
- Data
- Info
- Object

👉 너무 일반적이고 의미 없음

지켜야 할 것:

- 명사 또는 명사구
- 도메인 개념을 반영
- 역할이 명확
- 하나의 책임을 암시