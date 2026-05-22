# 의미 있는 맥락을 추가하라(Add Meaningful Context)

> **이름이 단독으로 의미를 가지지 못하면, 맥락(Context)을 제공하라**

- 변수 이름은 항상 충분한 정보를 담지 못할 수 있다
- 이때는 **구조(클래스, 객체)**를 통해 의미를 보완해야 한다

👉 이름 + 맥락 = 완전한 의미

## 예제 1

```java
// Bad
String firstName;
String lastName;
String street;
String city;
String state;
String zipCode;
```

- 각각의 변수는 의미가 있음
- 하지만 서로 어떤 관계인지 알 수 없음

읽는 사람의 의문:

- 이 정보는 무엇을 위한 것인가?
- 사용자 정보인가?
- 배송지인가?
- 청구지인가?

👉 맥락이 부족하다

```java
// Good
class Address {
    String firstName;
    String lastName;
    String street;
    String city;
    String state;
    String zipCode;
}
```

- Address라는 클래스가 맥락을 제공
- 변수들이 하나의 개념으로 묶임
- 이제 읽는 사람은 바로 이해: *"아, 주소 정보구나"*

## 예제 2

```java
// Bad
save("Bernard", "Um", "Seoul", "Gangnam", "12345");
```

- 인자의 의미를 알 수 없음
- 순서를 기억해야 함
- 완전히 맥락이 없는 코드

```java
// Good
Address address = new Address();
address.firstName = "Bernard";
address.lastName = "Um";
address.city = "Seoul";
address.state = "Gangnam";
address.zipCode = "12345";

save(address);
```

- 각 값의 의미가 명확
- 구조가 맥락을 제공
- 실수 가능성 감소

## 예제 3

```java
// Bad
int x, y, w, h;
```

- x, y, w, h → 의미 추측 필요
- 좌표인지 크기인지 불명확

```java
// Good
class Rectangle {
    int x;
    int y;
    int width;
    int height;
}
```

- Rectangle이라는 맥락 제공
- 변수 의미가 자연스럽게 연결됨

## 예제 4: 부분적인 맥락 추가

```java
// Bad
String firstName;
String lastName;
```

```java
// Good
String userFirstName;
String userLastName;
```

- 최소한의 맥락 제공
- 하지만 완전한 해결은 아님
- 클래스로 묶는 것이 더 좋은 방법

## 예제 5

```java
// Bad
String number;
String verb;
String pluralModifier;
```

- number, verb, pluralModifier → 의미 추측 필요

```java
// Good
class GuessStatistics {
    private String number;
    private String verb;
    private String pluralModifier;
}
```

- GuessStatistics라는 맥락 제공
- 변수 의미가 자연스럽게 연결됨

## 나쁜 코드 vs 좋은 코드

| 구분 | 나쁜 코드           | 문제     | 좋은 코드         |
| -- | --------------- | ------ | ------------- |
| 변수 | firstName, city | 맥락 없음  | Address 클래스   |
| 함수 | save("a", "b")  | 의미 불명확 | save(address) |
| 좌표 | x, y, w, h      | 추측 필요  | Rectangle 클래스 |

## 핵심 원칙

피해야 할 것:

- 맥락 없는 변수 나열
- 의미를 추측해야 하는 코드
- 인자 순서에 의존하는 코드

지켜야 할 것:

- 관련 데이터는 하나의 객체로 묶기
- 클래스/구조로 의미 제공
- 이름 + 구조로 의도 표현