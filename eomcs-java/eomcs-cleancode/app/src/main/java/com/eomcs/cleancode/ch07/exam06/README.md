# 정상 흐름을 정의하라 (Define the Normal Flow)

> **예외 처리를 줄여서 정상 흐름을 더 자연스럽게 읽히게 만들라.**

- 예외 처리가 유용하지만, 예외가 정상적인 업무 규칙까지 차지하면 코드가 복잡해질 수 있다. 
- 이때는 특수 사례 객체(Special Case Object)를 만들어 예외적인 상황을 정상 흐름 안으로 흡수할 수 있다.

## 예제 1

```java
// Bad - 함수 정의
public class ExpenseReport {

    public MealExpense getMealExpense(Employee employee) {
        MealExpense mealExpense = repository.findMealExpense(employee);

        if (mealExpense == null) {
            throw new MealExpenseNotFoundException();
        }

        return mealExpense;
    }
}
```

```java
// Bad - 함수 사용
try {
    MealExpense mealExpense = expenseReport.getMealExpense(employee);
    total += mealExpense.getTotal();
} catch (MealExpenseNotFoundException e) {
    total += employee.getMealPerDiem();
}
```

- 식비 내역이 없는 상황이 실제 오류처럼 처리된다.
- 정상 업무 규칙이 catch 블록 안에 숨어 있다.
- 코드를 읽을 때 “식비 합산”보다 “예외 처리”가 먼저 보인다.
- 호출자가 예외 상황과 기본 식비 정책을 모두 알아야 한다.

```java
// Good - 함수 정의
public class ExpenseReport {

    public MealExpense getMealExpense(Employee employee) {
        MealExpense mealExpense = repository.findMealExpense(employee);

        if (mealExpense == null) {
            return new PerDiemMealExpense(employee);
        }

        return mealExpense;
    }
}
```

```java
public interface MealExpense {
    int getTotal();
}

public class ActualMealExpense implements MealExpense {
    private final int total;

    public ActualMealExpense(int total) {
        this.total = total;
    }

    @Override
    public int getTotal() {
        return total;
    }
}

public class PerDiemMealExpense implements MealExpense {
    private final Employee employee;

    public PerDiemMealExpense(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int getTotal() {
        return employee.getMealPerDiem();
    }
}
```

```java
// Good - 함수 사용
MealExpense mealExpense = expenseReport.getMealExpense(employee);
total += mealExpense.getTotal();
```

- 호출 코드는 정상 흐름만 표현한다.
- “식비 내역이 없으면 기본 식비를 사용한다”는 규칙이 객체 안에 들어간다.
- try-catch 없이도 동일한 업무 처리가 가능하다.
- 호출자는 실제 식비인지 기본 식비인지 구분하지 않아도 된다.

## 예제 2

```java
// Bad - 함수 정의
public DiscountPolicy findDiscountPolicy(Customer customer) {
    DiscountPolicy policy = discountRepository.findByCustomer(customer);

    if (policy == null) {
        throw new DiscountPolicyNotFoundException();
    }

    return policy;
}
```

```java
// Bad - 함수 사용
try {
    DiscountPolicy policy = findDiscountPolicy(customer);
    price = policy.apply(price);
} catch (DiscountPolicyNotFoundException e) {
    price = price; // 할인 없음
}
```

- 할인 정책이 없는 것은 오류라기보다 정상적인 경우일 수 있다.
- catch 블록에 비즈니스 규칙이 들어간다.
- 호출 코드가 불필요하게 복잡하다.

```java
// Good - 함수 정의
public DiscountPolicy findDiscountPolicy(Customer customer) {
    DiscountPolicy policy = discountRepository.findByCustomer(customer);

    if (policy == null) {
        return new NoDiscountPolicy();
    }

    return policy;
}
```

```java
public interface DiscountPolicy {
    int apply(int price);
}

public class RateDiscountPolicy implements DiscountPolicy {
    private final double rate;

    public RateDiscountPolicy(double rate) {
        this.rate = rate;
    }

    @Override
    public int apply(int price) {
        return (int) (price * (1 - rate));
    }
}

public class NoDiscountPolicy implements DiscountPolicy {

    @Override
    public int apply(int price) {
        return price;
    }
}
```

```java
// Good - 함수 사용
DiscountPolicy policy = findDiscountPolicy(customer);
price = policy.apply(price);
```

- 할인 없음도 하나의 정상 정책으로 표현된다.
- 호출자는 예외 처리를 하지 않아도 된다.
- NoDiscountPolicy가 특수 사례 객체 역할을 한다.
- 코드가 다형성 기반으로 단순해진다.

## 예제 3

```java
// Bad - 함수 정의
public ShippingFee getShippingFee(Order order) {
    ShippingFee fee = shippingRepository.findFee(order);

    if (fee == null) {
        throw new ShippingFeeNotFoundException();
    }

    return fee;
}
```

```java
// Bad - 함수 사용
try {
    ShippingFee fee = getShippingFee(order);
    total += fee.amount();
} catch (ShippingFeeNotFoundException e) {
    total += 0;
}
```

- 배송비가 없는 경우를 예외로 처리한다.
- catch 블록에 “무료 배송” 정책이 숨어 있다.
- 정상 계산 흐름이 끊긴다.

```java
// Good - 함수 정의
public ShippingFee getShippingFee(Order order) {
    ShippingFee fee = shippingRepository.findFee(order);

    if (fee == null) {
        return new FreeShippingFee();
    }

    return fee;
}
```

```java
public interface ShippingFee {
    int amount();
}

public class DefaultShippingFee implements ShippingFee {
    private final int amount;

    public DefaultShippingFee(int amount) {
        this.amount = amount;
    }

    @Override
    public int amount() {
        return amount;
    }
}

public class FreeShippingFee implements ShippingFee {

    @Override
    public int amount() {
        return 0;
    }
}
```

```java
// Good - 함수 사용
ShippingFee fee = getShippingFee(order);
total += fee.amount();
```

- 무료 배송을 예외가 아니라 정상 객체로 표현한다.
- 호출 코드는 단순한 계산 흐름만 가진다.
- 예외 처리 없이 비즈니스 규칙을 표현할 수 있다.
- 특수 상황이 객체 내부로 캡슐화된다.



## 나쁜 코드 vs 좋은 코드

| 구분       | 나쁜 코드             | 좋은 코드                |
| -------- | ----------------- | -------------------- |
| 특수 상황 처리 | 예외로 처리            | 특수 사례 객체로 처리         |
| 호출 코드    | `try-catch` 필요    | 일반 객체처럼 사용           |
| 업무 규칙 위치 | `catch` 블록에 숨어 있음 | 객체 내부에 캡슐화           |
| 가독성      | 예외 흐름 때문에 끊김      | 정상 흐름이 자연스럽게 읽힘      |
| 대표 패턴    | 예외 기반 분기          | Special Case Pattern |

## 핵심 원칙

피해야 할 것:

- 정상적인 업무 상황을 예외로 처리하는 것
- catch 블록에 비즈니스 규칙을 넣는 것
- 호출자가 특수 상황을 매번 직접 처리하게 하는 것
- “없음”을 곧바로 오류로 간주하는 것

지켜야 할 것:

- 예외는 진짜 오류 상황에 사용한다.
- 정상적인 특수 상황은 객체로 표현한다.
- 호출 코드는 정상 흐름 중심으로 유지한다.
- 기본값, 없음, 무료, 미적용 같은 경우는 Special Case Object로 다룬다.
- 호출자가 특수 상황을 몰라도 사용할 수 있게 만든다.