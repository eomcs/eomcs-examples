# Exam02 - Optional

## 개념

### Optional\<T\>란?

값이 있을 수도, 없을 수도 있는 **컨테이너 객체**다.
메서드가 `null`을 직접 반환하는 대신 `Optional`을 반환하면, 호출자가 `null` 체크를 강제받지 않고도 빈 값을 안전하게 처리할 수 있다.

```java
// null 반환 방식: 호출자가 null 체크를 해야 한다.
String name = findName(); // null일 수 있음
if (name != null) {
    System.out.println(name.toUpperCase());
}

// Optional 방식: null 체크 없이 체인으로 처리한다.
findNameOptional()
    .map(String::toUpperCase)
    .ifPresent(System.out::println);
```

### Optional 생성

| 메서드 | 설명 |
|---|---|
| `Optional.of(value)` | `null`이 아닌 값으로 생성한다. `null`을 전달하면 `NullPointerException` 발생 |
| `Optional.ofNullable(value)` | `null`일 수도 있는 값으로 생성한다. `null`이면 빈 `Optional` 반환 |
| `Optional.empty()` | 빈 `Optional`을 생성한다. 값이 없음을 명시적으로 표현할 때 사용 |

### 값 꺼내기

| 메서드 | 설명 |
|---|---|
| `get()` | 값을 꺼낸다. 값이 없으면 `NoSuchElementException` 발생. **단독 사용은 안티패턴** |
| `orElse(T)` | 값이 있으면 그 값, 없으면 지정한 기본값을 반환한다. **항상 인자를 평가한다** |
| `orElseGet(Supplier<T>)` | 값이 없을 때만 `Supplier`를 호출해 기본값을 생성한다. **지연 평가** |
| `orElseThrow()` | 값이 없으면 `NoSuchElementException`을 발생시킨다 |
| `orElseThrow(Supplier<E>)` | 값이 없으면 지정한 예외를 발생시킨다 |

### 변환과 필터링

| 메서드 | 설명 |
|---|---|
| `map(Function<T, U>)` | 값에 함수를 적용해 `Optional<U>`로 변환한다. 빈 값이면 아무것도 하지 않는다 |
| `flatMap(Function<T, Optional<U>>)` | 함수가 `Optional<U>`를 반환할 때 사용한다. 중첩 `Optional` 방지 |
| `filter(Predicate<T>)` | 조건을 만족하면 그대로, 아니면 빈 `Optional`을 반환한다 |

### 소비와 확인

| 메서드 | 설명 |
|---|---|
| `isPresent()` | 값이 있으면 `true` |
| `isEmpty()` | 값이 없으면 `true` (Java 11+) |
| `ifPresent(Consumer<T>)` | 값이 있을 때만 `Consumer`를 실행한다 |
| `ifPresentOrElse(Consumer, Runnable)` | 값이 있으면 `Consumer`, 없으면 `Runnable`을 실행한다 (Java 9+) |

### Optional과 Stream 연계 (Java 9+)

| 변환 방향 | 방법 | 설명 |
|---|---|---|
| `Optional` → `Stream` | `optional.stream()` | 값이 있으면 1개짜리 `Stream`, 없으면 빈 `Stream` |
| `Stream` → `Optional` | `stream.findFirst()` / `max()` / `min()` / `reduce(op)` | 결과가 없을 수 있으면 `Optional`로 반환 |

### map vs flatMap

```
map:     Optional<T>  --Function<T, U>----------->  Optional<U>
flatMap: Optional<T>  --Function<T, Optional<U>>-->  Optional<U>  (중첩 Optional 평탄화)
```

- `getEmail()` 반환 타입이 `String`이면 `map` 사용
- `getEmail()` 반환 타입이 `Optional<String>`이면 `flatMap` 사용 → `Optional<Optional<String>>`이 되지 않도록 평탄화

### orElse vs orElseGet

| 항목 | `orElse(T)` | `orElseGet(Supplier<T>)` |
|---|---|---|
| 인자 평가 시점 | **항상** 평가 (값이 있어도 실행됨) | 값이 **없을 때만** 실행 |
| 적합한 용도 | 리터럴·상수처럼 생성 비용이 없는 기본값 | DB 조회·객체 생성처럼 비용이 큰 기본값 |

### 안티패턴

| 안티패턴 | 이유 | 대안 |
|---|---|---|
| `isPresent()` + `get()` | `null` 체크와 다를 바 없다 | `map()` / `ifPresent()` / `orElse()` 사용 |
| 필드 타입으로 `Optional` 사용 | 직렬화 불가, 성능 저하 | 메서드 반환 타입으로만 사용 |
| 메서드 파라미터로 `Optional` 사용 | 호출자 코드가 복잡해짐 | 오버로딩 또는 기본값으로 대체 |
| `Optional<Optional<T>>` 중첩 | 처리 불편 | `flatMap()` 으로 평탄화 |

---

## App - Optional 생성과 값 꺼내기

`Optional.of()`, `ofNullable()`, `empty()`로 `Optional`을 생성하고,
`orElse()`, `orElseGet()`, `orElseThrow()`로 값을 꺼내는 방법을 비교한다.

```java
// [예제 1] Optional 생성
Optional<String> opt1 = Optional.of("hello");         // null 불가
Optional<String> opt2 = Optional.ofNullable(null);    // null → 빈 Optional
Optional<String> opt3 = Optional.ofNullable("world"); // null → 값 있는 Optional
Optional<String> opt4 = Optional.empty();             // 명시적 빈 Optional

opt1.isPresent(); // true
opt2.isPresent(); // false
opt4.isEmpty();   // true (Java 11+)

// [예제 2] orElse - 기본값 반환
Optional.of("Alice").orElse("기본값");  // "Alice"
Optional.<String>empty().orElse("기본값"); // "기본값"

// [예제 3] orElseGet vs orElse - Supplier 호출 시점 차이
Optional<String> empty = Optional.empty();

// orElse: 값의 존재 여부와 관계없이 인자 표현식을 항상 평가한다.
empty.orElse(createDefault());       // createDefault() 호출됨

// orElseGet: 값이 없을 때만 Supplier를 호출한다.
empty.orElseGet(() -> createDefault()); // 값 없으므로 호출됨

// 값이 있을 때의 차이
Optional<String> present = Optional.of("존재하는 값");
present.orElse(createDefault());           // createDefault() 여전히 호출됨!
present.orElseGet(() -> createDefault()); // 호출되지 않음 ← 비용이 클 때 유리

// [예제 4] orElseThrow - 값이 없으면 예외 발생
Optional.of("데이터").orElseThrow(() -> new IllegalStateException("없음")); // "데이터"

Optional.<String>empty().orElseThrow(() -> new IllegalStateException("없음")); // 예외 발생
```

- `Optional.of()`는 `null`을 전달하면 즉시 `NullPointerException`이 발생한다. `null`이 올 수 있으면 `ofNullable()`을 사용한다.
- `orElse()`는 값이 있어도 인자 표현식을 항상 평가한다. DB 조회나 객체 생성처럼 비용이 큰 기본값에는 `orElseGet()`을 사용한다.
- `orElseThrow()`는 값이 반드시 있어야 하는 상황에서 `null` 반환 대신 예외로 의도를 명확히 표현한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App
  ```

---

## App2 - map, flatMap, filter, ifPresent

`Optional` 안의 값을 변환·필터링하고 조건부로 실행하는 방법을 다룬다.

```java
// [예제 1] map - Optional 안의 값 변환
// null 체크 방식
String upper = (raw != null) ? raw.toUpperCase() : null;

// Optional.map: null 체크 없이 변환 로직만 작성한다.
Optional.of("alice").map(String::toUpperCase); // Optional["ALICE"]

// 빈 Optional에 map: 함수를 실행하지 않고 빈 Optional을 그대로 반환한다.
Optional.<String>empty().map(String::toUpperCase); // Optional.empty()

// [예제 2] map 체이닝 - 여러 변환을 이어 붙이기
// null 체크 방식: 각 단계마다 null을 확인해야 한다.
if (rawCity != null) {
    String trimmed = rawCity.trim();
    if (!trimmed.isEmpty()) {
        result = trimmed.toUpperCase();
    }
}

// Optional 체이닝: 각 변환이 독립된 단계로 표현된다.
Optional.of("  Seoul  ")
    .map(String::trim)         // "Seoul"
    .filter(s -> !s.isEmpty()) // 비어 있지 않으면 통과
    .map(String::toUpperCase); // "SEOUL"

// [예제 3] flatMap - Optional을 반환하는 메서드와 연결
// getEmail()의 반환 타입: Optional<String>

// map을 쓰면 Optional<Optional<String>>이 된다.
user.map(u -> u.getEmail());     // Optional<Optional<String>>

// flatMap은 자동으로 평탄화해 Optional<String>을 반환한다.
user.flatMap(u -> u.getEmail()); // Optional<String>

// 이메일이 없는 경우
Optional.of(new User("Charlie", null))
    .flatMap(u -> u.getEmail())
    .orElse("이메일 없음"); // "이메일 없음"

// [예제 4] filter - 조건 불만족 시 빈 Optional
Optional.of(85).filter(s -> s >= 60).map(s -> "합격").orElse("불합격"); // "합격"
Optional.of(40).filter(s -> s >= 60).map(s -> "합격").orElse("불합격"); // "불합격"

// [예제 5] ifPresent / ifPresentOrElse
// null 체크 방식
if (raw != null) { System.out.println(raw); }

// ifPresent: 값이 있을 때만 실행. null 체크 if를 대체한다.
Optional.of("안녕").ifPresent(System.out::println); // 실행됨
Optional.<String>empty().ifPresent(System.out::println); // 실행 안 됨

// ifPresentOrElse: 있을 때와 없을 때 각각 동작을 지정한다. (Java 9+)
Optional.of("안녕").ifPresentOrElse(
    m -> System.out.println("값 있음: " + m),
    ()  -> System.out.println("값 없음")
); // "값 있음: 안녕"
```

- `map()`의 함수가 `null`을 반환하면 결과는 `Optional.empty()`가 된다.
- `flatMap()`은 함수의 반환 타입이 `Optional<U>`일 때 사용한다. `map()`을 쓰면 `Optional<Optional<U>>`가 된다.
- `filter()`는 조건을 만족하지 않으면 빈 `Optional`을 반환한다. `if (x != null && condition)` 패턴을 대체한다.
- `ifPresent()`는 `if (x != null)` 패턴을 대체한다. 실행 결과를 반환하지 않는다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App2
  ```

---

## App3 - null 방어 코드 제거 패턴

`Optional`을 사용해 실제 코드에서 자주 나타나는 null 처리 패턴을 개선하는 방법을 다룬다.

```java
// [예제 1] 중첩 null 체크 → Optional 체인으로 교체
// null 체크 방식: 단계마다 null을 확인해야 한다. (null 체크 피라미드)
if (order != null) {
    Customer customer = order.getCustomer();
    if (customer != null) {
        Address address = customer.getAddress();
        if (address != null) {
            System.out.println(address.getCity());
            return;
        }
    }
}
System.out.println("알 수 없음");

// Optional 방식: 체인으로 평탄하게 표현된다. 각 단계에서 null이면 빈 Optional이 전파된다.
Optional.ofNullable(order)
    .map(Order::getCustomer)   // null이면 빈 Optional
    .map(Customer::getAddress) // null이면 빈 Optional
    .map(Address::getCity)     // null이면 빈 Optional
    .orElse("알 수 없음");

// [예제 2] 컬렉션에서 조건에 맞는 첫 번째 요소 찾기
// null 반환 방식: 호출자가 null을 확인해야 한다.
static String findFirstStartingWith(List<String> list, String prefix) {
    for (String item : list) {
        if (item.startsWith(prefix)) return item;
    }
    return null; // null 반환 → 호출자 부담
}

// Optional 반환 방식: 없으면 Optional.empty()를 반환해 호출자가 자연스럽게 처리한다.
static Optional<String> findFirstStartingWith(List<String> list, String prefix) {
    return list.stream()
        .filter(item -> item.startsWith(prefix))
        .findFirst();
}

// 호출부 비교
String r1 = findFirstStartingWithNull(items, "블");
if (r1 != null) { System.out.println(r1); } // null 체크 필요

findFirstStartingWith(items, "블").orElse("없음"); // null 체크 불필요

// [예제 3] 설정값 우선순위 폴백 체인
// null 체크 방식
String charset = userSetting != null ? userSetting
    : (envVariable != null ? envVariable : systemDefault);

// Optional 폴백 체인 (Java 9+): or()로 다음 Optional을 지정한다.
String charset = Optional.ofNullable(userSetting)    // 사용자 설정
    .or(() -> Optional.ofNullable(envVariable))       // 없으면 환경 변수
    .orElse(systemDefault);                           // 없으면 시스템 기본값

// [예제 4] 안티패턴 vs 올바른 패턴
// 안티패턴: isPresent() + get() 조합은 null 체크와 다를 바 없다.
if (maybe.isPresent()) {
    System.out.println(maybe.get()); // null 체크 if와 동일
}

// 올바른 패턴: orElse / map / ifPresent 중 하나를 선택한다.
maybe.ifPresent(v -> System.out.println(v));
maybe.orElse("없음");
maybe.map(String::toUpperCase).orElse("없음");
```

- `Optional.ofNullable().map().map()...` 체인을 사용하면 단계별 `null` 체크 피라미드를 선형 코드로 교체할 수 있다.
- 메서드 반환 타입을 `null` 대신 `Optional`로 바꾸면 호출자가 빈 값 처리를 잊지 않도록 API가 강제한다.
- `Optional.or()` (Java 9+)는 폴백 체인을 간결하게 표현한다.
- `isPresent()` + `get()` 조합은 안티패턴이다. `map()`, `ifPresent()`, `orElse()` 중 하나로 대체한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App3
  ```

---

## App4 - Optional과 Stream의 통합

`Optional`을 `Stream`과 연계하고, `Map` 조회·`reduce`·도메인 객체 리스트 처리에 응용한다.

```java
// [예제 1] Optional 리스트에서 값만 추출
List<Optional<String>> optionals = Arrays.asList(
    Optional.of("사과"), Optional.empty(), Optional.of("바나나"),
    Optional.empty(), Optional.of("포도")
);

// 기존 방식: isPresent()로 필터링 후 get()으로 꺼낸다.
optionals.stream()
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList()); // [사과, 바나나, 포도]

// Optional.stream() 방식 (Java 9+): flatMap과 함께 사용해 빈 값을 자동 제거한다.
optionals.stream()
    .flatMap(Optional::stream)    // 값 있으면 1개짜리 Stream, 없으면 빈 Stream
    .collect(Collectors.toList()); // [사과, 바나나, 포도]

// [예제 2] reduce - 항등원 없는 버전은 Optional 반환
List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4);

// reduce(BinaryOperator): 초기값 없이 누적한다.
// 빈 스트림에서 결과가 없을 수 있으므로 Optional<T>를 반환한다.
Optional<Integer> max = numbers.stream()
    .reduce((a, b) -> a >= b ? a : b); // Optional[9]

Optional<Integer> emptyMax = Arrays.<Integer>asList().stream()
    .reduce((a, b) -> a >= b ? a : b); // Optional.empty()

emptyMax.orElse(-1); // -1 (기본값)

// [예제 3] Map 조회에 Optional 적용
Map<String, String> capitals = Map.of("한국", "서울", "일본", "도쿄");

// null 반환 방식: null 체크 필요
String c = capitals.get("독일"); // null
if (c != null) { System.out.println(c); }

// Optional 방식
Optional.ofNullable(capitals.get("독일")).orElse("알 수 없음"); // "알 수 없음"

// Map.getOrDefault()와 비교:
capitals.getOrDefault("독일", "알 수 없음"); // 단순 기본값에 편리
// Optional은 추가 변환(map/filter)이 필요할 때 더 유연하다.

// [예제 4] Optional 필드를 가진 객체 리스트 처리
List<Product> products = Arrays.asList(
    new Product("노트북", Optional.of(1_200_000)),
    new Product("마우스", Optional.of(35_000)),
    new Product("모니터", Optional.empty()),   // 가격 미정
    new Product("키보드", Optional.of(85_000)),
    new Product("웹캠",   Optional.empty())    // 가격 미정
);

// 가격이 있는 상품만 합산
int total = products.stream()
    .flatMap(p -> p.getPrice().stream())  // 가격 없으면 빈 Stream, 있으면 1개짜리 Stream
    .mapToInt(Integer::intValue)
    .sum(); // 1,320,000

// 가격이 없는 상품 이름 목록
List<String> noPriceNames = products.stream()
    .filter(p -> p.getPrice().isEmpty())
    .map(Product::getName)
    .collect(Collectors.toList()); // [모니터, 웹캠]

// 가장 비싼 상품 이름 (가격이 있는 것 중)
Optional<String> mostExpensive = products.stream()
    .filter(p -> p.getPrice().isPresent())
    .max((a, b) -> a.getPrice().get() - b.getPrice().get())
    .map(Product::getName); // Optional["노트북"]
```

- `Optional.stream()` (Java 9+)은 `Optional` 리스트에서 값만 추출할 때 `flatMap`과 조합해 빈 값을 자동으로 제거한다.
- `reduce(BinaryOperator)` (항등원 없는 버전)는 빈 스트림을 안전하게 처리하기 위해 `Optional`을 반환한다.
- `Map.get()`은 `Optional.ofNullable()`로 감싸면 `null` 체크 없이 체인으로 처리할 수 있다.
- 단순 기본값이면 `Map.getOrDefault()`가 간결하고, 추가 변환이 필요하면 `Optional` 체인이 더 유연하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.stream.exam02.App4
  ```
