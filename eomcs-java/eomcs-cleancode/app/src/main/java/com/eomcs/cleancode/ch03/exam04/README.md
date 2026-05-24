# Switch 문 (Switch Statements)

> **switch 문은 작게 만들기 어렵고, 변경에 취약하다 → 가능하면 숨기고, 한 번만 사용하라**

- switch는 자연스럽게 **여러 일을 하게 만든다**
- 새로운 조건(type)이 추가될 때마다 수정이 필요하다
- OCP(Open-Closed Principle)를 위반하기 쉽다

👉 해결 방향:
- switch를 **추상화 뒤로 숨긴다**
- 다형성(polymorphism)으로 대체한다

## 예제 1

```java
// Bad: 전형적인 switch 문제
public int calculatePay(Employee employee) {
    switch (employee.getType()) {
        case COMMISSIONED:
            return calculateCommissionedPay(employee);
        case HOURLY:
            return calculateHourlyPay(employee);
        case SALARIED:
            return calculateSalariedPay(employee);
        default:
            throw new IllegalArgumentException("Unknown type");
    }
}
```

- 변경에 취약
    - 새로운 타입 추가 시 → switch 수정 필요
    - 👉 기존 코드 수정 → 버그 위험 증가
- 동일 switch 반복 가능성
    - 다른 곳에서도 같은 switch 등장 가능
        ```java
        calculatePay()
        calculateBonus()
        calculateTax()
        ```
    - 👉 중복 + 유지보수 악몽
- SRP(Single Responsibility Principle) 위반
    - 하나의 함수가 여러 타입을 처리
- OCP(Open-Closed Principle) 위반
    - 확장 시 기존 코드 수정 필요

```java
// Bad: 여러 switch 중복
public int calculatePay(Employee e) {
    switch (e.getType()) { ... }
}

public int calculateBonus(Employee e) {
    switch (e.getType()) { ... }
}

public int calculateTax(Employee e) {
    switch (e.getType()) { ... }
}
```

- 동일한 분기 로직이 여러 군데 존재
- 타입 추가 시 모든 switch 수정 필요
- 👉 유지보수 비용 폭발

```java
// Good: 다형성으로 해결
abstract class Employee {
    abstract int calculatePay();
}

// 각 타입별 구현
class CommissionedEmployee extends Employee {
    int calculatePay() {
        return calculateCommissionedPay();
    }
}

class HourlyEmployee extends Employee {
    int calculatePay() {
        return calculateHourlyPay();
    }
}

class SalariedEmployee extends Employee {
    int calculatePay() {
        return calculateSalariedPay();
    }
}
```

```java
// 사용 예)
Employee employee = getEmployee();
int pay = employee.calculatePay();
```

-  switch 제거
    - 분기 대신 다형성 사용
-  변경에 강함
    - 새로운 타입 추가 시:
        ```java
        class InternEmployee extends Employee {
            int calculatePay() { ... }
        }
        ```
    - 👉 기존 코드 수정 없음
-  SRP 준수
    - 각 클래스는 자신의 로직만 책임짐

## 예제 2: switch를 완전히 없앨 수 없는 경우

```java
// Good: 팩토리에서만 사용
class EmployeeFactory {
    static Employee create(EmployeeType type) {
        switch (type) {
            case COMMISSIONED: return new CommissionedEmployee();
            case HOURLY: return new HourlyEmployee();
            case SALARIED: return new SalariedEmployee();
            default: throw new IllegalArgumentException();
        }
    }
}
```

- switch는 한 곳에만 존재
- 생성 책임에만 사용
- 이후에는 다형성으로 처리

## 나쁜 코드 vs 좋은 코드

| 구분   | 나쁜 코드       | 좋은 코드    |
| ---- | ----------- | -------- |
| 구조   | switch 반복   | 다형성      |
| 변경   | 기존 코드 수정 필요 | 확장만 하면 됨 |
| 책임   | 한 함수에 집중    | 클래스별 분리  |
| 유지보수 | 어려움         | 쉬움       |


## 핵심 원칙

❗ switch 문제:

- 확장에 취약
- 중복 발생
- SRP 위반
- OCP 위반

✅ 해결책:

- 다형성(polymorphism) 사용
- switch는 숨기기
- 가능하면 한 번만 사용 (팩토리)