# 불필요한 맥락을 추가하지 마라(Don’t Add Gratuitous Context)

> **의미를 전달하기 위해 필요한 맥락은 추가하되, 불필요한 반복은 제거하라**

- "Add Meaningful Context"의 반대 개념
- 맥락이 부족하면 문제지만, **과도하면 오히려 가독성을 해친다**

👉 핵심은 **적절한 수준의 맥락**


개발자가 흔히 저지르는 실수:

- 클래스 이름을 변수 이름에 반복
- 프로젝트 이름을 모든 요소에 포함
- 불필요한 접두어 남용

👉 결과:
- 코드가 장황해짐
- 핵심 의미가 흐려짐

## 예제 1

```java
// Bad
class GSDAccountAddress {
    String GSDAccountFirstName;
    String GSDAccountLastName;
    String GSDAccountStreet;
    String GSDAccountCity;
}
```

- GSDAccount가 모든 곳에 반복됨
- 이미 클래스 이름에 포함된 정보를 또 표현
- 읽는 사람 입장:
    - 너무 길고 복잡함
    - 핵심 정보가 묻힘

```java
// Good
class Address {
    String firstName;
    String lastName;
    String street;
    String city;
}
```

- 클래스 이름이 이미 맥락 제공 (Address)
- 변수는 필요한 정보만 표현
- 중복 제거 → 가독성 향상

## 예제 2

```java
// Bad
class User {
    String userName;
    String userEmail;
}
```

- User 클래스 안에서 user 반복
- 불필요한 중복

```java
// Good
class User {
    String name;
    String email;
}
```

- 클래스가 이미 User라는 맥락 제공
- 변수는 핵심 정보만 표현

## 예제 3

```java
// Bad
class GSDUserService {
    void GSDProcessUser(GSDUser user) { ... }
}
```

- GSD 반복 → 불필요
- 프로젝트 이름은 코드 구조로 이미 표현됨

```java
// Good
class UserService {
    void processUser(User user) { ... }
}
```

- 불필요한 접두어 제거
- 핵심 의미만 남김

## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드              | 문제     | 좋은 코드       |
| --- | ------------------ | ------ | ----------- |
| 클래스 | GSDAccountAddress  | 중복, 장황 | Address     |
| 변수  | userName (in User) | 반복     | name        |
| 접두어 | GSDUserService     | 불필요    | UserService |

### Add Context vs Don’t Add Context

| 상황    | 원칙                           |
| ----- | ---------------------------- |
| 의미 부족 | Add Meaningful Context       |
| 의미 과다 | Don’t Add Gratuitous Context |

## 핵심 원칙

피해야 할 것:

- 클래스 이름 반복
- 프로젝트 이름 반복
- 의미 없는 접두어/접미어
- 과도하게 긴 이름

지켜야 할 것:

- 맥락은 한 번만 제공
- 중복된 정보 제거
- 핵심 의미만 유지