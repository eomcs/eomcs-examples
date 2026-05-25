# 자료 추상화 (Data Abstraction)


> **자료를 그대로 노출하지 말고, 자료를 다루는 추상적인 방식만 드러내라.**

Data Abstraction의 핵심은 단순히 필드를 `private`으로 만들고 getter/setter를 붙이는 것이 아니다.

중요한 것은 다음이다.

> **사용자가 내부 구현을 몰라도, 의미 있는 방식으로 데이터를 다룰 수 있게 하는 것**

## 예제 1

```java
// Bad: 구현을 그대로 노출
public class Point {
    public double x;
    public double y;
}
```

이 코드는 점이 내부적으로 어떻게 표현되는지 그대로 드러낸다.

- x, y 좌표를 직접 노출한다
- 직교 좌표계라는 구현 방식이 외부에 드러난다
- 나중에 극좌표계로 바꾸기 어렵다

```java
// Bad or Good? getter/setter를 붙인다고 추상화가 되는 것은 아니다
public class Point {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
```

- 필드는 private이지만, 사실상 x, y라는 내부 구현을 그대로 공개하고 있다.
- 즉, getter/setter는 캡슐화처럼 보일 수 있지만, 반드시 추상화는 아니다.
- 변수 사이에 함수라는 계층을 넣는다고 구현이 저절로 감춰지는 것은 아니다.
- **구현을 감추려면 추상화가 필요하다.**

```java
// Good: 추상화된 Point
public interface Point {
    double getX();
    double getY();

    double getR();
    double getTheta();

    void setCartesian(double x, double y);
    void setPolar(double r, double theta);
}
```

이 인터페이스는 사용자가 점을 다음 두 방식으로 다룰 수 있게 한다.

- 직교 좌표: x, y
- 극좌표: r, theta

하지만 내부 구현이 실제로 무엇인지는 알 수 없다.

- 내부가 x, y일 수도 있고
- r, theta 일 수도 있고
- 전혀 다른 방식일 수도 있다

구현을 숨기는 것은 단순히 함수 계층을 추가하는 것이 아니라, 

- **자료의 본질을 표현하는 추상 인터페이스를 제공하는 것**이다.
- 자료 구조 이상을 표현한다.

## 예제 2

```java
// Bad: 구체적인 연료 정보 노출
public interface Vehicle {
    double getFuelTankCapacityInGallons();
    double getGallonsOfGasoline();
}
```

이 인터페이스는 차량의 연료 상태를 너무 구체적으로 드러낸다.

- 연료 탱크 용량
- 현재 휘발유 갤런 수
- 단위가 Gallons라는 사실
- 내부 데이터가 연료량 중심이라는 사실

즉, 사용자에게 내부 표현 방식을 강요한다.

```java
// Good: 추상적인 연료 상태 표현
public interface Vehicle {
    double getPercentFuelRemaining();
}
```

사용자는 내부 구현을 알 필요가 없다.

- 갤런으로 저장하는지
- 리터로 저장하는지
- 센서 값으로 계산하는지
- 전기차 배터리 잔량으로 계산하는지

알 필요 없이 “남은 연료 비율”만 알면 된다. **즉, 자료를 세세하게 공개하기보다는 추상적인 개념으로 표현하는 것이 좋다.**

- 구체적인 연료량보다, percentage라는 추상적 표현이 더 낫다.
- 구체적인 accessor는 내부 변수를 노출하는 것처럼 보이지만, 추상적 표현은 데이터의 형태를 감춘다.

추상적 개념으로 표현한다는 것은, 

- 인터페이스나 조회/설정 함수만으로 추상화가 이뤄지지 않는다.
- 객체가 포함하는 자료를 표현할 가장 좋은 방법을 생각해야 한다.
- 아무 생각 없이 조회/설정 함수를 추가하는 방법이 가장 나쁘다.

## 예제 3

```java
// Bad
public class User {
    public String firstName;
    public String lastName;
    public int birthYear;
}
```

```java
// 사용 예: 외부 코드가 내부 데이터를 직접 조합한다.
String fullName = user.firstName + " " + user.lastName;
int age = 2026 - user.birthYear;
```

- 이름 조합 규칙이 외부로 퍼진다
- 나이 계산 규칙이 외부로 퍼진다
- 내부 데이터 변경 시 외부 코드도 변경된다

```java
// Good
public class User {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
```

```java
// 사용 예: 외부 코드는 더 이상 내부 필드를 조합하지 않는다.
String name = user.getDisplayName();
int age = user.getAge();
```
- User는 내부 자료를 숨기고, 외부에는 의미 있는 추상화만 제공한다.

## 나쁜 코드 vs 좋은 코드

| 구분     | 나쁜 코드                    | 좋은 코드                       |
| ------ | ------------------------ | --------------------------- |
| 데이터 표현 | 내부 필드 노출                 | 의미 있는 동작 제공                 |
| 캡슐화    | 약함                       | 강함                          |
| 변경 가능성 | 내부 변경이 외부에 영향            | 내부 변경을 숨김                   |
| 예      | `getGallonsOfGasoline()` | `getPercentFuelRemaining()` |

## 핵심 원칙

> **자료 추상화는 필드를 숨기는 것이 아니라, 내부 자료의 의미를 추상적인 동작으로 표현하는 것이다.**

피해야 할 것:

- public 필드
- 무분별한 getter/setter
- 내부 구현을 드러내는 이름
- 외부 코드가 데이터를 조합하게 만드는 구조

지켜야 할 것:

- 내부 표현을 숨기기
- 의미 있는 추상 인터페이스 제공
- 데이터의 “형태”보다 “의미”를 드러내기
- 사용자가 내부 구현을 몰라도 사용할 수 있게 만들기
