# Exam06 - Comparable과 Comparator 인터페이스

## 개념

Java에서 객체를 정렬하려면 비교 기준이 필요하다.
`Comparable`은 클래스 자체에 자연 순서를 내장하고, `Comparator`는 외부에서 비교 기준을 별도로 정의한다.

### Comparable vs Comparator

| 항목 | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| 패키지 | `java.lang` | `java.util` |
| 구현 위치 | 정렬 대상 클래스 내부 | 클래스 외부 (별도 객체) |
| 구현 메서드 | `int compareTo(T o)` | `int compare(T o1, T o2)` |
| 정렬 기준 수 | 하나 (자연 순서) | 여러 개 정의 가능 |
| 별칭 | 내부 비교자 | 외부 비교자 |
| 활용 | `Collections.sort(list)`, `new TreeSet<>()` | `Collections.sort(list, comp)`, `new TreeSet<>(comp)` |

### 반환값 규칙

두 인터페이스 모두 비교 결과를 정수로 반환하며 부호로 순서를 결정한다.

| 반환값 | 의미 | 정렬 결과 |
|---|---|---|
| 음수 | `this < other` (또는 `o1 < o2`) | 앞 항목이 먼저 |
| `0` | 동등 | 순서 변경 없음 |
| 양수 | `this > other` (또는 `o1 > o2`) | 뒤 항목이 먼저 |

### Comparator 팩토리 메서드 (Java 8+)

| 메서드 | 설명 |
|---|---|
| `Comparator.comparing(keyExtractor)` | 특정 필드 기준 오름차순 |
| `Comparator.comparingInt(keyExtractor)` | `int` 필드 기준 오름차순 (오토박싱 없음) |
| `comparator.reversed()` | 역순(내림차순)으로 전환 |
| `comparator.thenComparing(keyExtractor)` | 동일한 경우 추가 기준 적용 (다중 정렬) |
| `Comparator.naturalOrder()` | 자연 순서(`Comparable`) |
| `Comparator.reverseOrder()` | 자연 순서의 역순 |

### Comparable을 구현한 주요 표준 클래스

| 클래스 | 자연 순서 |
|---|---|
| `Integer`, `Long`, `Double` 등 래퍼 클래스 | 숫자 오름차순 |
| `String` | 사전순(lexicographic) |
| `LocalDate`, `LocalDateTime` | 날짜/시간 오름차순 |

## App - Comparable 사용

```java
// 점수 기준 자연 순서를 정의하는 Student 클래스
static class Student implements Comparable<Student> {
  String name;
  int score;

  @Override
  public int compareTo(Student other) {
    return this.score - other.score; // 점수 오름차순 (음수 → this가 앞)
  }
}

// 1. 표준 클래스의 자연 순서 - Collections.sort()
List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
Collections.sort(numbers); // [10, 20, 30, 40, 50]

List<String> words = new ArrayList<>(List.of("banana", "apple", "cherry", "mango"));
Collections.sort(words); // [apple, banana, cherry, mango]

// 2. compareTo() 반환값 직접 확인
Integer a = 10, b = 20;
a.compareTo(b); // -1 (10 < 20 → 음수)
b.compareTo(a); //  1 (20 > 10 → 양수)
a.compareTo(a); //  0 (동등)

// 3. 커스텀 클래스 정렬
List<Student> students = new ArrayList<>();
students.add(new Student("홍길동", 85));
students.add(new Student("임꺽정", 92));
students.add(new Student("유관순", 78));
students.add(new Student("이순신", 95));
students.add(new Student("장보고", 88));
Collections.sort(students); // Student.compareTo() 사용
// [유관순(78), 홍길동(85), 장보고(88), 임꺽정(92), 이순신(95)]

// 4. TreeSet - Comparable 기반 자동 정렬
TreeSet<Student> treeSet = new TreeSet<>();
treeSet.add(new Student("홍길동", 85));
treeSet.add(new Student("임꺽정", 92));
treeSet.add(new Student("유관순", 78));
treeSet.first(); // 유관순(78) - 최솟값
treeSet.last();  // 임꺽정(92) - 최댓값

// 5. Collections.min() / max()
Collections.min(students); // 최솟값(최저점)
Collections.max(students); // 최댓값(최고점)

// 6. Comparable 미구현 객체 → ClassCastException
TreeSet<Object> badSet = new TreeSet<>();
badSet.add(new Object());
badSet.add(new Object()); // 두 번째 추가 시 비교 시도 → ClassCastException
```

- `Comparable`을 구현하면 `Collections.sort()`, `Arrays.sort()`, `TreeSet`, `TreeMap` 등 Java 정렬 API를 별도 비교자 없이 바로 사용할 수 있다.
- `compareTo()`는 `this - other`처럼 뺄셈으로 구현할 수 있지만, 오버플로우 위험이 있을 때는 `Integer.compare(this.score, other.score)`를 사용한다.
- `TreeSet`은 항목 추가 시마다 `compareTo()`로 위치를 결정한다. `Comparable`을 구현하지 않은 클래스를 두 번 이상 추가하면 `ClassCastException`이 발생한다.
- `Collections.min()` / `max()`도 내부적으로 `compareTo()`를 사용한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam06.App
  ```

## App2 - Comparator 사용

```java
static class Student {
  String name;
  int score;
  String grade; // "A", "B", "C"
}

List<Student> students = new ArrayList<>();
students.add(new Student("홍길동", 86, "B"));
students.add(new Student("임꺽정", 92, "A"));
students.add(new Student("유관순", 78, "C"));
students.add(new Student("이순신", 95, "A"));
students.add(new Student("장보고", 85, "B"));
students.add(new Student("강감찬", 91, "A"));

// 1. 익명 클래스로 Comparator 구현
Comparator<Student> byScore = new Comparator<Student>() {
  @Override
  public int compare(Student o1, Student o2) {
    return o1.score - o2.score; // 점수 오름차순
  }
};
Collections.sort(list, byScore);
// [유관순(78/C), 장보고(85/B), 홍길동(86/B), 강감찬(91/A), 임꺽정(92/A), 이순신(95/A)]

// 2. 람다로 Comparator 구현
Comparator<Student> byName = (o1, o2) -> o1.name.compareTo(o2.name);
list.sort(byName); // 이름 사전순

// 3. Comparator.comparingInt() - 점수 오름차순
list.sort(Comparator.comparingInt(s -> s.score));

// 4. reversed() - 점수 내림차순
list.sort(Comparator.comparingInt((Student s) -> s.score).reversed());
// [이순신(95/A), 임꺽정(92/A), 강감찬(91/A), 홍길동(86/B), 장보고(85/B), 유관순(78/C)]

// 5. thenComparing() - 복합 정렬 (점수 내림차순, 동점 시 이름 사전순)
list.sort(
    Comparator.comparingInt((Student s) -> s.score)
              .reversed()
              .thenComparing(s -> s.name));

// 6. reverseOrder() - 자연 순서의 역순
List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
numbers.sort(Comparator.reverseOrder()); // [50, 40, 30, 20, 10]

// 7. TreeSet에 Comparator 전달 - Comparable 없이도 정렬 가능
TreeSet<Student> treeSet = new TreeSet<>(
    Comparator.comparingInt((Student s) -> s.score)
              .reversed()
              .thenComparing(s -> s.name));
treeSet.addAll(students);

// 8. TreeMap에 Comparator 전달 - 키 내림차순 정렬
TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
treeMap.put("banana", 500);
treeMap.put("apple", 1000);
treeMap.put("mango", 700);
treeMap.put("cherry", 300);
// {mango=700, cherry=300, banana=500, apple=1000} - 내림차순

// 9. 복합 기준 - 등급 오름차순, 동일 등급 내 점수 내림차순
list.sort(
    Comparator.comparing((Student s) -> s.grade)
              .thenComparing(Comparator.comparingInt((Student s) -> s.score).reversed()));
// [이순신(95/A), 임꺽정(92/A), 강감찬(91/A), 홍길동(86/B), 장보고(85/B), 유관순(78/C)]
```

- `Comparator`는 `Comparable`을 구현하지 않은 클래스도 정렬할 수 있고, 상황에 따라 다양한 기준을 외부에서 정의할 수 있다.
- `Comparator.comparingInt()`는 `comparingDouble()` / `comparingLong()`과 같이 기본 타입 특화 버전이 있다. 오토박싱 없이 비교하므로 `comparing()`보다 효율적이다.
- `reversed()` 이후 `thenComparing()`을 연결할 때 타입 추론이 실패할 수 있으므로 `(Student s) -> s.score`처럼 타입을 명시해야 한다.
- `TreeSet` / `TreeMap` 생성자에 `Comparator`를 전달하면 해당 기준으로 자동 정렬된다. `Comparable`이 없어도 동작하지만, `compare()`가 `0`을 반환하면 동일 항목으로 간주하여 중복이 제거된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam06.App2
  ```
