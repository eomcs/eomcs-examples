# Exam08 - 수정 불가 컬렉션 (Unmodifiable Collection)

## 개념

수정 불가 컬렉션은 생성 후 항목을 추가·변경·삭제할 수 없는 컬렉션이다.
수정 메서드(`add`, `set`, `put`, `remove` 등)를 호출하면 `UnsupportedOperationException`이 발생한다.

### 수정 불가 컬렉션 생성 방법 비교

| 방법 | 도입 | 원본과의 관계 | `null` 허용 | 특징 |
|---|---|---|---|---|
| `Collections.unmodifiableXxx()` | Java 1.2 | 원본의 뷰 — 원본 수정 시 함께 변경 | 허용 | 기존 컬렉션을 읽기 전용으로 래핑 |
| `List.of()` / `Set.of()` / `Map.of()` | Java 9 | 독립 — 원본 없음 | 불허 | 리터럴로 불변 컬렉션 즉시 생성 |
| `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()` | Java 10 | 독립 — 원본 복사 | 불허 | 기존 컬렉션을 복사하여 불변 생성 |

### 불변 컬렉션 상황별 선택 기준

| 상황 | 권장 방법 |
|---|---|
| 코드에서 리터럴로 바로 초기화 | `List.of()` / `Set.of()` / `Map.of()` |
| 기존 컬렉션을 불변으로 복사 | `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()` |
| 기존 컬렉션의 읽기 전용 뷰만 필요 | `Collections.unmodifiableXxx()` |
| 방어적 복사 후 읽기 전용 반환 | `Collections.unmodifiableList(new ArrayList<>(src))` |

### Map.of() / Map.ofEntries() 선택

| 쌍의 수 | 방법 |
|---|---|
| 10쌍 이하 | `Map.of(k1, v1, k2, v2, ...)` |
| 11쌍 이상 | `Map.ofEntries(Map.entry(k, v), ...)` |

### 얕은 불변성(Shallow Immutability) 주의

불변 컬렉션은 **컬렉션 구조 자체(항목의 추가·삭제)만 보호**한다.
컬렉션에 저장된 객체 내부의 상태는 보호하지 않는다.

```java
List<List<String>> nested = List.of(innerList);
nested.add(...);         // UnsupportedOperationException ← 구조 보호
nested.get(0).add("x"); // 허용 ← 내부 List는 가변
```

## App - Collections.unmodifiableXxx() 사용

```java
// 1. unmodifiableList() - 기본 사용
List<String> original = new ArrayList<>(List.of("apple", "banana", "cherry"));
List<String> readOnly = Collections.unmodifiableList(original);

readOnly.get(0);          // "apple" - 읽기 허용
readOnly.size();          // 3       - 읽기 허용
readOnly.add("mango");    // UnsupportedOperationException
readOnly.set(0, "grape"); // UnsupportedOperationException
readOnly.remove(0);       // UnsupportedOperationException

// 2. unmodifiableSet() / unmodifiableMap()
Set<Integer> readOnlySet = Collections.unmodifiableSet(new HashSet<>(Set.of(1, 2, 3)));
readOnlySet.contains(2); // true
readOnlySet.add(4);      // UnsupportedOperationException

Map<String, Integer> readOnlyMap = Collections.unmodifiableMap(new HashMap<>(Map.of("apple", 1000)));
readOnlyMap.get("apple");        // 1000
readOnlyMap.put("cherry", 300);  // UnsupportedOperationException
readOnlyMap.remove("apple");     // UnsupportedOperationException

// 3. 원본 수정 시 래퍼에도 반영 (뷰 특성)
List<String> mutableList = new ArrayList<>(List.of("A", "B"));
List<String> viewList = Collections.unmodifiableList(mutableList);
mutableList.add("C");
System.out.println(viewList); // [A, B, C] ← 원본 수정이 함께 반영됨

// 4. 완전한 불변성 - 원본 참조를 차단
// 나쁜 예: 원본 참조가 남아 우회 수정 가능
List<String> leak = new ArrayList<>(List.of("X", "Y"));
List<String> notSafe = Collections.unmodifiableList(leak);
leak.add("Z");
System.out.println(notSafe); // [X, Y, Z] ← 우회 수정됨

// 좋은 예: 복사 후 래핑 → 원본 참조 차단
List<String> safe = Collections.unmodifiableList(new ArrayList<>(List.of("X", "Y")));
// 원본 참조가 없으므로 외부 수정 불가
System.out.println(safe); // [X, Y]
```

- `Collections.unmodifiableXxx()`는 원본 컬렉션의 **뷰(view)**를 반환한다. 원본 참조가 남아 있으면 우회 수정이 가능하므로 완전한 불변성을 보장하지 못한다.
- 완전한 불변성을 보장하려면 `new ArrayList<>(src)`처럼 복사본을 만들어 원본 참조를 차단하거나 Java 10+의 `List.copyOf()`를 사용한다.
- 읽기(`get`, `size`, `contains`, `iterator` 등)는 모두 허용된다. 수정 메서드를 호출하면 `UnsupportedOperationException`이 발생한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam08.App
  ```

## App2 - List.of() / Set.of() / Map.of() / copyOf() 사용

```java
// 1. List.of() - 불변 List
List<String> immutableList = List.of("apple", "banana", "cherry", "mango");
immutableList.get(1);              // "banana" - 읽기 허용
immutableList.contains("apple");   // true     - 읽기 허용
immutableList.add("grape");        // UnsupportedOperationException
immutableList.set(0, "grape");     // UnsupportedOperationException
List.of("a", null, "b");           // NullPointerException - null 불허

// 2. Set.of() - 불변 Set (순서 미보장)
Set<Integer> immutableSet = Set.of(10, 20, 30, 40, 50);
immutableSet.contains(30); // true
immutableSet.add(60);      // UnsupportedOperationException
Set.of(1, 2, 2, 3);        // IllegalArgumentException - 중복 불허
Set.of(1, null, 3);        // NullPointerException     - null 불허

// 3. Map.of() - 불변 Map (최대 10쌍, 순서 미보장)
Map<String, Integer> immutableMap = Map.of(
    "apple", 1000,
    "banana", 500,
    "cherry", 300
);
immutableMap.get("apple");       // 1000
immutableMap.put("mango", 700);  // UnsupportedOperationException
Map.of("a", 1, "a", 2);          // IllegalArgumentException - 중복 키 불허

// 4. Map.ofEntries() - 10쌍 초과 시 사용
Map<String, Integer> largeMap = Map.ofEntries(
    Map.entry("a", 1),
    Map.entry("b", 2),
    // ...
    Map.entry("k", 11) // 11쌍 이상 가능
);
largeMap.size(); // 11

// 5. List.copyOf() - 기존 컬렉션 복사 후 불변 생성 (Java 10+)
List<String> mutable = new ArrayList<>(List.of("X", "Y", "Z"));
List<String> copied = List.copyOf(mutable);
mutable.add("W");
System.out.println(copied); // [X, Y, Z] ← 원본 수정 영향 없음 (독립적)
copied.add("V");            // UnsupportedOperationException

// 6. Set.copyOf() / Map.copyOf() - 원본과 독립적인 불변 컬렉션
Set<String> copiedSet = Set.copyOf(mutableSet);    // 원본 수정 영향 없음
Map<String, Integer> copiedMap = Map.copyOf(mutableMap); // 원본 수정 영향 없음

// 7. List.of() vs Collections.unmodifiableList() 비교
List<String> base = new ArrayList<>(List.of("A", "B"));
List<String> unmod = Collections.unmodifiableList(base);
List<String> immut = List.of("A", "B");
base.add("C");
System.out.println(unmod); // [A, B, C] ← 원본 수정 반영 (뷰)
System.out.println(immut); // [A, B]    ← 영향 없음 (독립)
```

- `List.of()` / `Set.of()` / `Map.of()`는 원본이 없는 완전한 불변 컬렉션이다. `Collections.unmodifiableXxx()`와 달리 우회 수정이 불가능하다.
- `null` 요소·키·값을 허용하지 않으며, `Set.of()`와 `Map.of()`는 중복 요소·키도 `IllegalArgumentException`으로 거부한다.
- `Set.of()` / `Map.of()`의 순서는 실행마다 달라질 수 있으므로 순서에 의존하는 코드를 작성하면 안 된다.
- `List.copyOf()` / `Set.copyOf()` / `Map.copyOf()`는 기존 컬렉션의 요소를 복사하여 원본과 독립적인 불변 컬렉션을 생성한다. `null` 요소가 있으면 `NullPointerException`이 발생한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam08.App2
  ```

## App3 - 방어적 복사 패턴 사용

```java
// 나쁜 예: 방어적 복사 없는 클래스
class UnsafeCart {
  private List<String> items;
  UnsafeCart(List<String> items) { this.items = items; } // 외부 참조 저장
  List<String> getItems()        { return items; }        // 내부 참조 반환
}

List<String> external = new ArrayList<>(List.of("apple", "banana"));
UnsafeCart unsafeCart = new UnsafeCart(external);
external.add("cherry");              // 외부에서 원본 수정
unsafeCart.getItems();               // [apple, banana, cherry] ← 내부 상태 변경됨
unsafeCart.getItems().add("grape");  // getter로 내부 직접 수정 가능

// 좋은 예: 방어적 복사를 적용한 클래스
class SafeCart {
  private final List<String> items;
  SafeCart(List<String> items) { this.items = List.copyOf(items); } // 복사 후 불변 저장
  List<String> getItems()      { return items; }                     // 불변이므로 안전
}

SafeCart safeCart = new SafeCart(external);
external.add("cherry");               // 외부 수정
safeCart.getItems();                  // [apple, banana] ← 내부 상태 보호됨
safeCart.getItems().add("grape");     // UnsupportedOperationException

// 읽기 전용 뷰 반환 - 복사본의 읽기 전용 뷰를 반환하면 원본도 보호
List<String> internalData = new ArrayList<>(List.of("X", "Y", "Z"));
List<String> view = Collections.unmodifiableList(new ArrayList<>(internalData));
internalData.add("W");
System.out.println(view);  // [X, Y, Z] ← 복사본이므로 영향 없음
view.add("V");             // UnsupportedOperationException

// 중첩 컬렉션의 얕은 불변성 주의
List<String> inner1 = new ArrayList<>(List.of("a", "b"));
List<List<String>> nested = List.of(inner1, new ArrayList<>(List.of("c", "d")));
nested.add(new ArrayList<>()); // UnsupportedOperationException ← 구조 보호
inner1.add("z");               // 허용 ← 내부 List는 가변
System.out.println(nested);    // [[a, b, z], [c, d]]

// 상수 컬렉션 정의 패턴
final List<String> BAD_CONSTANT = new ArrayList<>(List.of("READ", "WRITE"));
BAD_CONSTANT.add("ADMIN"); // final이지만 내용 수정 가능 ← 나쁜 예

final List<String> GOOD_CONSTANT = List.of("READ", "WRITE", "DELETE");
GOOD_CONSTANT.add("ADMIN"); // UnsupportedOperationException ← 좋은 예

// Map.copyOf() 얕은 불변성 확인
Map<String, List<String>> immutableConfig = Map.copyOf(config);
immutableConfig.put("db", new ArrayList<>()); // UnsupportedOperationException
immutableConfig.get("servers").add("host3");  // 허용 ← 값인 List는 가변
```

- 방어적 복사는 **생성자(입력)**와 **getter(출력)** 두 곳에 모두 적용해야 완전한 보호가 이루어진다. 생성자에서만 복사하면 getter로 받은 참조를 통해 내부 수정이 가능하다.
- `List.copyOf()`는 `null` 요소가 포함된 컬렉션을 복사하면 `NullPointerException`이 발생한다. `null`이 포함될 수 있다면 `Collections.unmodifiableList(new ArrayList<>(src))`를 사용한다.
- 불변 컬렉션은 **얕은 불변성(shallow immutability)**만 보장한다. 컬렉션 구조(항목 추가·삭제)는 보호하지만, 컬렉션에 저장된 객체 내부의 상태는 보호하지 않는다.
- `final` 키워드는 변수가 다른 객체를 참조하지 못하게 막을 뿐, 참조하는 컬렉션의 내용 수정은 막지 않는다. 상수 컬렉션은 반드시 `List.of()` 같은 불변 컬렉션으로 선언해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.collection.exam08.App3
  ```
