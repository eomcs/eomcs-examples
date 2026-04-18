# Exam09 - 검색과 매칭

## 개념

### 매칭 메서드

| 메서드 | 반환 타입 | 설명 | 빈 스트림 |
|---|---|---|---|
| `anyMatch(Predicate<T>)` | `boolean` | 하나라도 조건을 만족하면 `true` | `false` |
| `allMatch(Predicate<T>)` | `boolean` | 모든 요소가 조건을 만족하면 `true` | `true` (공허한 참) |
| `noneMatch(Predicate<T>)` | `boolean` | 조건을 만족하는 요소가 없으면 `true` | `true` |

세 메서드 모두 **단락 평가(short-circuit)**를 수행한다. 결과가 확정되는 순간 이후 요소를 처리하지 않는다.

```
anyMatch:  조건 만족 요소 발견 즉시 true 반환 (이후 요소 무시)
allMatch:  조건 불만족 요소 발견 즉시 false 반환
noneMatch: 조건 만족 요소 발견 즉시 false 반환
```

### 빈 스트림의 동작

```java
List<Integer> empty = List.of();

empty.stream().anyMatch(n -> n > 0);  // false — "하나라도"가 없으므로
empty.stream().allMatch(n -> n > 0);  // true  — 반례(조건 불만족)가 없으므로 (공허한 참)
empty.stream().noneMatch(n -> n > 0); // true  — 조건을 만족하는 요소가 없으므로
```

`allMatch`가 `true`를 반환하는 이유: **공허한 참(vacuous truth)** — "모든 요소가 조건을 만족하지 않는 반례가 없다"는 명제가 참이다.

### 논리적 관계

```
noneMatch(p) == !anyMatch(p)
allMatch(p)  == !anyMatch(!p)
```

### 검색 메서드

| 메서드 | 반환 타입 | 설명 |
|---|---|---|
| `findFirst()` | `Optional<T>` | 스트림의 첫 번째 요소 반환. 병렬에서도 순서 보장 |
| `findAny()` | `Optional<T>` | 임의의 요소 반환. 병렬 스트림에서 더 빠를 수 있음 |

두 메서드 모두 **단락 평가**를 수행하며 `Optional<T>`를 반환해 null 없이 안전하게 결과를 처리할 수 있다.

### findFirst vs findAny

| 항목 | `findFirst()` | `findAny()` |
|---|---|---|
| 순차 스트림 | 항상 첫 번째 요소 반환 | 보통 첫 번째 요소 반환 |
| 병렬 스트림 | 순서 보장 (오버헤드 있음) | 순서 미보장 (더 빠름) |
| 사용 시점 | 순서가 중요할 때 | 성능이 중요할 때 |

### Optional 처리 패턴

| 메서드 | 설명 |
|---|---|
| `orElse(T)` | 값이 없으면 기본값 반환 |
| `orElseGet(Supplier<T>)` | 값이 없을 때만 Supplier 실행 (지연 평가) |
| `orElseThrow()` | 값이 없으면 예외 발생 |
| `ifPresent(Consumer<T>)` | 값이 있을 때만 실행 |
| `map(Function<T,R>)` | 값이 있으면 변환 |
| `ifPresentOrElse(Consumer, Runnable)` | 있을 때/없을 때 각각 처리 (Java 9+) |

### anyMatch vs filter + findFirst

```
anyMatch:          boolean이 필요할 때 — 존재 여부만 확인
filter + findFirst: Optional이 필요할 때 — 실제 요소가 필요할 때
```

**나쁜 패턴**: `anyMatch`로 확인 후 `findFirst`로 다시 조회 → 스트림을 두 번 순회

```java
// 나쁜 패턴: 스트림 두 번 순회
boolean exists = list.stream().anyMatch(predicate);
if (exists) {
  list.stream().filter(predicate).findFirst().ifPresent(...);
}

// 좋은 패턴: findFirst 하나로 존재 확인과 결과 조회를 동시에
list.stream().filter(predicate).findFirst().ifPresent(...);
```

### 활용 전략 요약

| 질문 | 사용 메서드 |
|---|---|
| "~가 있는가?" | `anyMatch` |
| "~를 모두 충족하는가?" | `allMatch` |
| "~가 하나도 없는가?" | `noneMatch` |
| "~를 만족하는 첫 번째는?" | `filter + findFirst` |
| "정렬 후 최솟값/최댓값?" | `sorted + findFirst` 또는 `min` / `max` |
| "결과가 없어도 괜찮다" | `orElse` / `ifPresent` |
| "결과가 반드시 있어야 한다" | `orElseThrow` |

---

## App - anyMatch / allMatch / noneMatch

단락 평가, 빈 스트림 동작, 세 메서드의 논리 관계를 다룬다.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

// [예제 1] anyMatch - 하나라도 조건을 만족하는가?
boolean anyGt5  = numbers.stream().anyMatch(n -> n > 5);   // true
boolean anyGt10 = numbers.stream().anyMatch(n -> n > 10);  // false
boolean anyLong = names.stream().anyMatch(name -> name.length() > 5); // true (Charlie)

// [예제 2] allMatch - 모든 요소가 조건을 만족하는가?
boolean allPositive = numbers.stream().allMatch(n -> n > 0);        // true
boolean allEven     = numbers.stream().allMatch(n -> n % 2 == 0);   // false
boolean allShort    = names.stream().allMatch(name -> name.length() <= 8); // true

// [예제 3] noneMatch - 조건을 만족하는 요소가 하나도 없는가?
boolean noneNeg  = numbers.stream().noneMatch(n -> n < 0);   // true
boolean noneGt10 = numbers.stream().noneMatch(n -> n > 10);  // true
boolean noneGt5  = numbers.stream().noneMatch(n -> n > 5);   // false

// [예제 4] 논리 관계
// noneMatch(p) == !anyMatch(p)
// allMatch(p)  == !anyMatch(!p)

// [예제 5] 빈 스트림
List<Integer> empty = List.of();
empty.stream().anyMatch(n -> n > 0);  // false
empty.stream().allMatch(n -> n > 0);  // true  (공허한 참)
empty.stream().noneMatch(n -> n > 0); // true

// [예제 6] 단락 평가 - 처리 횟수 추적
int[] anyCount = {0};
numbers.stream().anyMatch(n -> {
  anyCount[0]++;
  return n > 5;
});
// anyCount[0] == 6 (6번째 요소 '6'에서 종료)
```

- `int[] count = {0}` 패턴: 람다 안에서 외부 카운터를 변경하기 위해 배열을 사용한다. 람다는 effectively final 변수만 캡처할 수 있으므로 `int count`는 불가능하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App
  ```

---

## App2 - findFirst / findAny

`findFirst`, `findAny`, Optional 처리 패턴, 병렬 스트림 비교를 다룬다.

```java
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
List<String>  names   = Arrays.asList("Charlie", "Alice", "Eve", "Bob", "Dave");

// [예제 1] findFirst - 조건을 만족하는 첫 번째 요소
Optional<Integer> firstGt5   = numbers.stream().filter(n -> n > 5).findFirst(); // Optional[7]
Optional<Integer> firstGt100 = numbers.stream().filter(n -> n > 100).findFirst(); // Optional.empty

firstGt5.orElse(-1);   // 7
firstGt100.orElse(-1); // -1 (기본값)

// [예제 2] sorted + findFirst - 최솟값 패턴
Optional<Integer> minEven = numbers.stream()
    .filter(n -> n % 2 == 0) // 2 4 6 8 10
    .sorted()                 // 오름차순
    .findFirst();             // Optional[2]

Optional<String> firstName = names.stream()
    .sorted()      // Alice Bob Charlie Dave Eve
    .findFirst();  // Optional[Alice]

// [예제 3] findAny - 순차 스트림 (findFirst와 결과 유사)
Optional<Integer> anyGt5 = numbers.stream().filter(n -> n > 5).findAny(); // 보통 7

// 병렬 스트림에서는 순서 미보장
Optional<Integer> anyGt5Parallel = numbers.parallelStream()
    .filter(n -> n > 5).findAny(); // 7,9,6,8,10 중 하나 (비결정적)

// [예제 4] findFirst vs findAny - 병렬 스트림
List<Integer> ordered = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

ordered.parallelStream().filter(n -> n % 2 == 0).findFirst(); // 항상 2 (첫 번째 짝수)
ordered.parallelStream().filter(n -> n % 2 == 0).findAny();  // 비결정적 (더 빠름)

// [예제 5] Optional 처리 패턴
Optional<String> found = names.stream().filter(name -> name.startsWith("C")).findFirst();

found.orElse("없음");                          // Charlie
found.orElseGet(() -> "기본 이름");            // Charlie (지연 실행)
found.orElseThrow();                           // Charlie (없으면 예외)
found.ifPresent(name -> System.out.println(name)); // Charlie
found.map(String::length).orElse(0);          // 7
found.ifPresentOrElse(
    name -> System.out.println("있음: " + name),
    ()   -> System.out.println("없음")
);

// [예제 6] 빈 스트림
Optional<Integer> emptyFirst = List.<Integer>of().stream().findFirst(); // Optional.empty
emptyFirst.isPresent(); // false
emptyFirst.isEmpty();   // true (Java 11+)
```

- `findFirst()`는 순서를 보장한다. 병렬 스트림에서도 첫 번째 요소를 반환하지만 오버헤드가 있다.
- `findAny()`는 병렬 스트림에서 순서를 신경 쓰지 않아 더 빠를 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App2
  ```

---

## App3 - 실전 패턴 (User 도메인)

존재 여부 확인, 유효성 검사, 사용자 검색, anyMatch vs findFirst 역할 분리를 다룬다.

```java
List<User> users = Arrays.asList(
    new User(1, "alice",   "alice@example.com",  true,  28),
    new User(2, "bob",     "bob@example.com",    false, 35),
    new User(3, "charlie", "charlie@example.com", true, 22),
    new User(4, "dave",    "dave@example.com",   true,  41),
    new User(5, "eve",     "",                   false, 19) // 이메일 없음
);

// [예제 1] anyMatch - 존재 여부 확인
boolean hasActiveUser  = users.stream().anyMatch(User::isActive);        // true
boolean hasAdult30Plus = users.stream().anyMatch(u -> u.getAge() >= 30); // true
boolean hasNoEmail     = users.stream().anyMatch(u -> u.getEmail().isBlank()); // true

// [예제 2] allMatch - 유효성 전체 검사
boolean allHaveNames  = users.stream()
    .allMatch(u -> u.getName() != null && !u.getName().isBlank()); // true
boolean allHaveEmails = users.stream()
    .allMatch(u -> !u.getEmail().isBlank()); // false (eve 이메일 없음)
boolean allAdults     = users.stream()
    .allMatch(u -> u.getAge() >= 18); // true

// [예제 3] noneMatch - 금지 조건 검사
boolean noMinor       = users.stream().noneMatch(u -> u.getAge() < 18); // true
boolean noNullName    = users.stream().noneMatch(u -> u.getName() == null); // true

// [예제 4] filter + findFirst - 특정 사용자 검색
Optional<User> byId = users.stream().filter(u -> u.getId() == 3).findFirst();
byId.ifPresent(u -> System.out.printf("%s (%d세)", u.getName(), u.getAge())); // charlie (22세)

// 이름으로 검색 (대소문자 무시)
users.stream()
    .filter(u -> "ALICE".equalsIgnoreCase(u.getName()))
    .findFirst()
    .map(User::getEmail)
    .ifPresent(System.out::println);

// 활성 + 30세 이상인 첫 번째 사용자
users.stream()
    .filter(User::isActive)
    .filter(u -> u.getAge() >= 30)
    .findFirst()
    .ifPresent(u -> System.out.printf("%s (%d세)", u.getName(), u.getAge())); // dave (41세)

// [예제 5] anyMatch vs findFirst 역할 분리
// 나쁜 패턴: 스트림 두 번 순회
boolean exists = users.stream().anyMatch(u -> u.getAge() > 40);
if (exists) {
  users.stream().filter(u -> u.getAge() > 40).findFirst()
      .ifPresent(u -> System.out.println(u.getName()));
}

// 좋은 패턴: findFirst 하나로
users.stream().filter(u -> u.getAge() > 40).findFirst()
    .ifPresent(u -> System.out.println(u.getName())); // dave

// boolean만 필요하면 anyMatch
boolean hasOldUser = users.stream().anyMatch(u -> u.getAge() > 40); // true

// [예제 6] 활성 사용자 이메일 검증
List<String> activeEmails = users.stream()
    .filter(User::isActive)
    .map(User::getEmail)
    .filter(email -> !email.isBlank())
    .toList();

boolean allExampleDomain = activeEmails.stream()
    .allMatch(email -> email.endsWith("@example.com")); // true

String newEmail = "alice@example.com";
boolean isDuplicate = users.stream()
    .anyMatch(u -> newEmail.equals(u.getEmail())); // true
```

- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App3
  ```

---

## App4 - 복합 예제 (Product/CartItem 도메인)

재고 확인, 가격 범위 검색, 장바구니 주문 검증, 추천 상품 검색을 다룬다.

```java
List<Product> products = Arrays.asList(
    new Product(1, "노트북",    "전자기기", 1_200_000, 5),
    new Product(2, "마우스",    "전자기기",    35_000, 0), // 재고 없음
    new Product(3, "모니터",    "전자기기",   450_000, 3),
    new Product(4, "Java 입문", "도서",       28_000, 12),
    ...
);

// [예제 1] 재고/상태 확인
boolean hasStock       = products.stream().anyMatch(p -> p.getStock() > 0); // true
boolean allElecCheap   = products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .allMatch(p -> p.getPrice() <= 100_000); // false (노트북, 모니터)
boolean noOutOfStockBook = products.stream()
    .filter(p -> "도서".equals(p.getCategory()))
    .noneMatch(p -> p.getStock() == 0);      // true
boolean hasOutOfStock  = products.stream().anyMatch(p -> p.getStock() == 0); // true

// [예제 2] filter + findFirst - 상품 검색
// ID로 단일 상품 검색 (Repository.findById 패턴)
products.stream().filter(p -> p.getId() == 3).findFirst()
    .ifPresent(p -> System.out.printf("%s / %,d원", p.getName(), p.getPrice())); // 모니터

// 카테고리에서 재고 있는 최저가 (min 활용)
products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .filter(p -> p.getStock() > 0)
    .min(Comparator.comparingInt(Product::getPrice)); // 키보드 (마우스는 재고 없음)

// 키워드 포함 검색
products.stream()
    .filter(p -> p.getName().contains("Java"))
    .findFirst()
    .ifPresentOrElse(
        p  -> System.out.printf("'%s' 검색 결과: %s", "Java", p.getName()),
        () -> System.out.printf("'%s' 검색 결과 없음", "Java")
    );

// [예제 3] 가격 범위 검색
int minPrice = 30_000, maxPrice = 100_000;

boolean hasPriceRange = products.stream()
    .anyMatch(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice); // true

products.stream()
    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
    .filter(p -> p.getStock() > 0)
    .findFirst(); // 알고리즘 (35,000원, 재고 8)

// [예제 4] allMatch - 장바구니 주문 가능 여부 검증
List<CartItem> cart = Arrays.asList(
    new CartItem(1, 2), // 노트북 2개 (재고 5개)
    new CartItem(4, 5), // Java 입문 5개 (재고 12개)
    new CartItem(6, 1)  // 의자 1개 (재고 2개)
);

boolean canOrder = cart.stream()
    .allMatch(item -> products.stream()
        .filter(p -> p.getId() == item.getProductId())
        .findFirst()
        .map(p -> p.getStock() >= item.getQty())
        .orElse(false)); // true

// [예제 5] 추천 상품 - 재고 있는 최저가
products.stream()
    .filter(p -> "전자기기".equals(p.getCategory()))
    .filter(p -> p.getStock() > 0)
    .min(Comparator.comparingInt(Product::getPrice))
    .ifPresentOrElse(
        p  -> System.out.printf("%s (%,d원, 재고 %d개)", p.getName(), p.getPrice(), p.getStock()),
        () -> System.out.println("추천 상품 없음")
    );
```

- `min(Comparator)`은 Comparator를 받아 최솟값을 `Optional<T>`로 반환한다. `max(Comparator)`은 최댓값을 반환한다.
- `allMatch` 내부에서 중첩 스트림으로 `findFirst`를 호출하는 패턴은 실전 장바구니 검증 코드에서 자주 사용된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam09.App4
  ```
