# 인코딩을 피하라(Avoid Encodings)

> **이름에 불필요한 추가 정보(타입, 범위 등)를 넣지 마라**

- 과거에는 타입이나 범위를 이름에 포함시키는 관습이 있었음
- 하지만 현대 언어와 IDE에서는 불필요하고 오히려 가독성을 해침

요즘 나오는 프로그래밍 언어는,

- 훨씬 많은 타입을 지원한다.
- 컴파일러가 타입을 기억하고 강제한다.
- 클래스와 함수는 점차 작아지는 추세다.
- 변수를 선언한 위치와 사용하는 위치가 멀지 않다.
- 자바 프로그래머는 변수 이름에 타입을 인코딩할 필요가 없다.
- 객체는 강한 타입이며,
- IDE는 코드를 컴파일하지 않고도 타입 오류를 감지할 정도로 발전했다.

## 인코딩이란 무엇인가?

이름에 다음과 같은 정보를 넣는 것:

- 타입 정보 (`str`, `i`, `b`)
- 멤버 변수 여부 (`m_`)
- 인터페이스 여부 (`I`)
- 구현 세부사항

👉 이런 정보는 **이름이 아니라 코드 구조가 표현해야 한다**


## 예제 1: 헝가리안 표기법 (Hungarian Notation)

```java
// Bad
String strName;
int iAge;
boolean bIsActive;
```

- 타입이 이름에 포함됨 (str, i, b)
- 타입 변경 시 이름도 변경해야 함
- 가독성 저하

```java
// Good
String name;
int age;
boolean isActive;
```

- 타입은 이미 코드에 존재한다
- 이름은 의미만 표현해야 한다

## 예제 2: 멤버 변수 접두어

```java
// Bad
class User {
    private String m_name;
    private int m_age;
}
```

- `m_` → `member`라는 의미지만 불필요
- IDE에서 이미 구분 가능

```java
// Good
class User {
    private String name;
    private int age;
}
```

## 예제 3: 인터페이스 접두어

```java
// Bad
interface IShape {
    double area();
}
```

- I는 인터페이스라는 사실을 중복 표현
- 구현체 이름이 어색해짐 (ShapeImpl 등)


```java
// Good
interface Shape {
    double area();
}

class Circle implements Shape {
    public double area() {
        return Math.PI * radius * radius;
    }
}
```

## 예제 4: 타입 인코딩

```java
// Bad
List<User> userList;
Set<User> userSet;
```

- List, Set을 이름에 반복
- 구현 변경 시 이름도 변경 필요

```java
// Good
List<User> users;
Set<User> uniqueUsers;
```

- 중요한 것은 구조가 아니라 의미

## 예제 5: 복합 인코딩

```java
// 복합 인코딩
String m_strUserName;
```

- `m_` → 멤버
- `str` → 타입
- `UserName` → 의미

👉 하나의 이름에 불필요한 정보가 과도하게 포함

```java
// Good
String userName;
```

## 나쁜 코드 vs 좋은 코드

| 구분    | 나쁜 코드         | 문제      | 좋은 코드    |
| ----- | ------------- | ------- | -------- |
| 타입    | strName       | 타입 중복   | name     |
| 멤버    | m_name        | 불필요     | name     |
| 인터페이스 | IShape        | 중복 정보   | Shape    |
| 복합    | m_strUserName | 과도한 인코딩 | userName |

## 핵심 원칙

피해야 할 것:

- 타입을 이름에 포함
- 범위 정보 (m_, g_ 등)
- 인터페이스 접두어 (I)
- 구현 세부사항

지켜야 할 것:

- 이름은 의미만 표현
- 타입은 코드가 표현
- 구조로 역할을 드러낼 것