package com.eomcs.advanced.stream.exam02;

import java.util.Optional;

// Optional 기초 - 생성과 값 꺼내기:
//
// Optional<T>란?
//   - 값이 있을 수도, 없을 수도 있는 컨테이너 객체다.
//   - null을 직접 반환하는 대신 Optional을 반환하면 NullPointerException을 방지할 수 있다.
//   - Java 8에서 도입되었으며, 주로 메서드의 반환 타입으로 사용한다.
//
// Optional 생성:
//   Optional.of(value)      - null이 아닌 값으로 Optional 생성. null이면 NullPointerException 발생.
//   Optional.ofNullable(v)  - null일 수도 있는 값으로 Optional 생성. null이면 빈 Optional 반환.
//   Optional.empty()        - 빈 Optional 생성. 값이 없음을 명시적으로 표현할 때 사용.
//
// 값 꺼내기:
//   get()            - 값을 꺼낸다. 값이 없으면 NoSuchElementException 발생. 단독 사용은 위험하다.
//   orElse(T)        - 값이 있으면 그 값을, 없으면 지정한 기본값을 반환한다.
//   orElseGet(Supplier<T>)  - 값이 없을 때만 Supplier를 호출해 기본값을 생성한다.
//                            - orElse는 항상 인자를 평가하지만, orElseGet은 필요할 때만 평가한다.
//   orElseThrow()    - 값이 없으면 NoSuchElementException을 던진다.
//   orElseThrow(Supplier<E>) - 값이 없으면 지정한 예외를 던진다.
//
// 값 확인:
//   isPresent()      - 값이 있으면 true
//   isEmpty()        - 값이 없으면 true (Java 11+)
//

public class App {

  public static void main(String[] args) {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. Optional 생성 - of / ofNullable / empty
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] Optional 생성");

    Optional<String> opt1 = Optional.of("hello"); // null이 아닌 값
    Optional<String> opt2 = Optional.ofNullable(null); // null 허용 → 빈 Optional
    Optional<String> opt3 = Optional.ofNullable("world"); // null 허용 → 값 있는 Optional
    Optional<String> opt4 = Optional.empty(); // 명시적으로 빈 Optional

    System.out.println("  of(\"hello\")        isPresent: " + opt1.isPresent()); // true
    System.out.println("  ofNullable(null)   isPresent: " + opt2.isPresent()); // false
    System.out.println("  ofNullable(\"world\")  isPresent: " + opt3.isPresent()); // true
    System.out.println("  empty()            isPresent: " + opt4.isPresent()); // false
    System.out.println("  empty()            isEmpty:   " + opt4.isEmpty()); // true (Java 11+)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. orElse - 값이 없을 때 기본값 반환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] orElse - 값이 없을 때 기본값 반환");

    Optional<String> present = Optional.of("Alice");
    Optional<String> empty = Optional.empty();

    // 값이 있으면 그 값, 없으면 "기본값" 반환
    System.out.println("  값 있음: " + present.orElse("기본값")); // Alice
    System.out.println("  값 없음: " + empty.orElse("기본값")); // 기본값

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. orElseGet - 값이 없을 때만 Supplier 호출
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] orElseGet vs orElse - Supplier 호출 시점 차이");

    Optional<String> opt = Optional.empty();

    // orElse: 값의 존재 여부와 관계없이 인자 표현식을 항상 평가한다.
    String r1 = opt.orElse(createDefault("orElse")); // "createDefault" 항상 호출됨

    // orElseGet: 값이 없을 때만 Supplier를 호출한다. 비용이 큰 기본값 생성에 유리하다.
    String r2 = opt.orElseGet(() -> createDefault("orElseGet")); // 값 없으므로 호출됨

    System.out.println("  orElse    결과: " + r1);
    System.out.println("  orElseGet 결과: " + r2);

    // 값이 있을 때의 차이
    Optional<String> optPresent = Optional.of("존재하는 값");
    System.out.println();
    System.out.println("  [값이 있을 때]");
    String r3 = optPresent.orElse(createDefault("orElse")); // 여전히 호출됨!
    String r4 = optPresent.orElseGet(() -> createDefault("orElseGet")); // 호출되지 않음
    System.out.println("  orElse    결과: " + r3);
    System.out.println("  orElseGet 결과: " + r4);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. orElseThrow - 값이 없으면 예외 발생
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] orElseThrow - 값이 없으면 예외 발생");

    Optional<String> hasValue = Optional.of("데이터");

    // 값이 있으면 정상 반환
    String value = hasValue.orElseThrow(() -> new IllegalStateException("값이 없습니다"));
    System.out.println("  값 있음: " + value);

    // 값이 없으면 예외 발생
    Optional<String> noValue = Optional.empty();
    try {
      noValue.orElseThrow(() -> new IllegalStateException("값이 없습니다"));
    } catch (IllegalStateException e) {
      System.out.println("  값 없음 → 예외: " + e.getMessage());
    }

    System.out.println();
    System.out.println("→ Optional.of()는 null 불가, ofNullable()은 null 허용, empty()는 명시적 빈 값.");
    System.out.println("→ orElseGet()은 값이 없을 때만 Supplier를 실행하므로 비용이 큰 기본값에 적합하다.");
    System.out.println("→ orElseThrow()는 값이 반드시 있어야 하는 상황에서 null 대신 예외로 의도를 명확히 한다.");
  }

  // 호출 여부를 추적하기 위한 헬퍼 메서드
  static String createDefault(String caller) {
    System.out.println("    ※ createDefault() 호출됨 ← " + caller);
    return "기본값";
  }
}
