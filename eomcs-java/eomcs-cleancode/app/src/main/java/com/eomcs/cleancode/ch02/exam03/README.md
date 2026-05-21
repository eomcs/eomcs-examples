# 의미있게 구분하라(Make Meaningful Distinctions)

> **이름이 다르면 의미도 달라야 한다**

- 단순히 이름을 다르게 짓는 것은 의미가 없다
- **실제 차이를 표현하는 이름**이어야 한다

개발자들이 흔히 하는 실수:

- 이름 충돌을 피하기 위해 단어만 추가
- 의미 없는 접미사/접두사 사용
- 실제 차이를 설명하지 않는 이름

👉 결과:

- 코드는 읽히지만 이해되지 않는다

## 예제 1: 숫자 시리즈의 변수 이름

```java
// Bad: 의도를 전혀 알 수 없다
public static void copyChars1(char[] a1, char[] a2) {
    for (int i = 0; i < a1.length; i++) {
        a2[i] = a1[i];
    }
}

// Good: 역할을 드러내는 이름을 사용해야 한다.
public static void copyChars2(char[] source, char[] destination) {
    for (int i = 0; i < source.length; i++) {
        destination[i] = source[i];
    }
}
```

## 예제 2: 의미를 구분할 수 없는 클래스 이름

```java
// Bad: 이름이 달라도 의미 구분이 불가능하다.
class Product {}
class ProductData {}   // Data가 뭘 의미하나?
class ProductInfo {}   // Info는 Data와 어떻게 다른가?

// Good: 역할을 드러내는 이름을 사용한다.
class Product {}            // 도메인 객체
class ProductRepository {}  // 영속성 담당
class ProductDto {}         // 전송용 객체
```

## 예제 3: 같은 의미의 함수 이름

```java
// Bad: 어느 함수를 호출해야 하는지 알 수 없다.
getActiveAccount();
getActiveAccounts();
getActiveAccountInfo();

// Good: 읽는 사람이 차이를 알 수 있는 이름을 사용해야 한다.
getActiveAccount();          // 단일 계정 조회
getAllActiveAccounts();       // 전체 활성 계정 목록 조회
```

## 예제 4: 관사/형용사로 구분한 변수 이름

```java
// Bad: 불필요한 관사/형용사를 붙인 이름
String theMessage;
Customer aCustomer;
Customer theCustomer;
// → the, a는 의미를 추가하지 않는다. 단순히 이름만 다르게 만든 것

// Good: 관사/형용사 제거한 이름
String message;
Customer customer;
```

## 예제 5: 속성의 의미 차이를 표현하지 못하는 이름

```java
// Bad: 속성의 의미 차이를 표현하지 못하는 문자열 이름은 혼란을 초래한다
String name;
String nameString;

// Good: 속성의 의미 차이를 표현하는 이름을 사용해야 한다.
String username;
String displayName;
```


## 나쁜 코드 vs 좋은 코드

| 구분  | 나쁜 코드              | 문제     | 좋은 코드                            |
| --- | ------------------ | ------ | -------------------------------- |
| 메서드 | getData, getInfo   | 차이 불명확 | getUser, getUserCount            |
| 변수  | product1, product2 | 의미 없음  | primaryProduct, secondaryProduct |
| 객체  | user, userData     | 모호함    | user, userDto                    |
| 문자열 | name, nameString   | 중복 의미  | username, displayName            |

## 핵심 원칙

피해야 할 것:

- 단순히 이름만 다르게 만들기
- Info, Data, Object 같은 일반적인 단어 남용
- 숫자나 관사로 구분

지켜야 할 것:

- 차이를 이름에 반영할 것
- 역할 기반으로 이름을 지을 것
- 도메인 의미를 사용할 것