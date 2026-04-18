package com.eomcs.advanced.stream.exam09;

import java.util.Arrays;
import java.util.List;

// 매칭 - anyMatch / allMatch / noneMatch:
//
// anyMatch(Predicate<T>)
//   - 조건을 만족하는 요소가 하나라도 있으면 true를 반환한다.
//   - 단락 평가(short-circuit): 조건을 만족하는 요소를 찾으면 즉시 종료한다.
//   - 빈 스트림: false 반환.
//
// allMatch(Predicate<T>)
//   - 모든 요소가 조건을 만족하면 true를 반환한다.
//   - 단락 평가: 조건을 만족하지 않는 요소를 찾으면 즉시 종료한다.
//   - 빈 스트림: true 반환 (vacuous truth, 공허한 참).
//
// noneMatch(Predicate<T>)
//   - 조건을 만족하는 요소가 하나도 없으면 true를 반환한다.
//   - 단락 평가: 조건을 만족하는 요소를 찾으면 즉시 종료한다.
//   - 빈 스트림: true 반환.
//
// 단락 평가(short-circuit):
//   결과가 확정되는 순간 이후 요소를 처리하지 않는다.
//   명령형의 "플래그 변수 + break" 패턴을 자동으로 처리한다.
//
// 빈 스트림 동거:
//   anyMatch:  false  ("하나라도" 있어야 true → 없으므로 false)
//   allMatch:  true   ("모두"가 만족해야 true → 반례가 없으므로 true)
//   noneMatch: true   ("하나도 없어야" true → 없으므로 true)
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<String>  names   = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. anyMatch - 하나라도 조건을 만족하는가?
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] anyMatch - 하나라도 만족하는가?");

    // 명령형: 플래그 변수 + break
    boolean foundImperative = false;
    for (int n : numbers) {
      if (n > 5) {
        foundImperative = true;
        break; // 찾으면 즉시 탈출 (직접 관리)
      }
    }
    System.out.println("  명령형 (5 초과 있나?):  " + foundImperative); // true

    // 선언형: 단락 평가를 자동으로 처리
    boolean anyGt5  = numbers.stream().anyMatch(n -> n > 5);
    boolean anyGt10 = numbers.stream().anyMatch(n -> n > 10);
    System.out.println("  anyMatch(n > 5):   " + anyGt5);  // true
    System.out.println("  anyMatch(n > 10):  " + anyGt10); // false

    // 문자열 조건
    boolean anyLong = names.stream().anyMatch(name -> name.length() > 5);
    System.out.println("  이름 길이 5 초과:  " + anyLong); // true (Charlie)

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. allMatch - 모든 요소가 조건을 만족하는가?
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] allMatch - 모두 만족하는가?");

    // 명령형: 플래그 변수 + break
    boolean allPositiveImperative = true;
    for (int n : numbers) {
      if (n <= 0) {
        allPositiveImperative = false;
        break;
      }
    }
    System.out.println("  명령형 (모두 양수?): " + allPositiveImperative); // true

    // 선언형
    boolean allPositive = numbers.stream().allMatch(n -> n > 0);
    boolean allEven     = numbers.stream().allMatch(n -> n % 2 == 0);
    boolean allShort    = names.stream().allMatch(name -> name.length() <= 8);
    System.out.println("  allMatch(n > 0):    " + allPositive); // true
    System.out.println("  allMatch(모두 짝수): " + allEven);    // false
    System.out.println("  allMatch(길이 ≤ 8): " + allShort);   // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. noneMatch - 조건을 만족하는 요소가 하나도 없는가?
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] noneMatch - 하나도 없는가?");

    // 명령형
    boolean noneNegImperative = true;
    for (int n : numbers) {
      if (n < 0) {
        noneNegImperative = false;
        break;
      }
    }
    System.out.println("  명령형 (음수 없나?):   " + noneNegImperative); // true

    // 선언형
    boolean noneNeg  = numbers.stream().noneMatch(n -> n < 0);
    boolean noneGt10 = numbers.stream().noneMatch(n -> n > 10);
    boolean noneGt5  = numbers.stream().noneMatch(n -> n > 5); // 6,7,8,9,10 있으므로 false
    System.out.println("  noneMatch(n < 0):  " + noneNeg);  // true
    System.out.println("  noneMatch(n > 10): " + noneGt10); // true
    System.out.println("  noneMatch(n > 5):  " + noneGt5);  // false

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 세 메서드의 논리적 관계
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] anyMatch / allMatch / noneMatch 논리 관계");

    // noneMatch(p) == !anyMatch(p)
    boolean any  = numbers.stream().anyMatch(n -> n > 5);
    boolean none = numbers.stream().noneMatch(n -> n > 5);
    System.out.println("  anyMatch(n>5):           " + any);        // true
    System.out.println("  noneMatch(n>5):          " + none);       // false
    System.out.println("  noneMatch == !anyMatch:  " + (none == !any)); // true

    // allMatch(p) == !anyMatch(!p)
    boolean all     = numbers.stream().allMatch(n -> n > 0);
    boolean anyNotP = numbers.stream().anyMatch(n -> !(n > 0)); // 양수가 아닌 것이 있나?
    System.out.println("  allMatch(n>0):           " + all);        // true
    System.out.println("  allMatch == !anyMatch(!p): " + (all == !anyNotP)); // true

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 빈 스트림에서의 동작
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] 빈 스트림에서의 동작");

    List<Integer> empty = List.of();

    boolean emptyAny  = empty.stream().anyMatch(n -> n > 0);
    boolean emptyAll  = empty.stream().allMatch(n -> n > 0);
    boolean emptyNone = empty.stream().noneMatch(n -> n > 0);

    System.out.println("  빈 스트림 anyMatch:  " + emptyAny);  // false
    System.out.println("  빈 스트림 allMatch:  " + emptyAll);  // true  (공허한 참)
    System.out.println("  빈 스트림 noneMatch: " + emptyNone); // true
    // allMatch가 true인 이유: "모든 요소가 조건을 만족하지 않는 반례가 없다" → true
    // 이를 공허한 참(vacuous truth)이라 한다.

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 단락 평가 확인 - 처리 횟수 추적
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 단락 평가 - 처리 횟수 확인");

    int[] anyCount  = {0};
    int[] allCount  = {0};
    int[] noneCount = {0};

    // anyMatch: 조건 만족 시 즉시 종료 → 6에서 찾으면 이후는 검사 안 함
    numbers.stream().anyMatch(n -> {
      anyCount[0]++;
      return n > 5; // 6이 처음으로 조건 만족 → 6번째 요소에서 종료
    });

    // allMatch: 조건 불만족 시 즉시 종료 → 첫 짝수(2)에서 종료
    numbers.stream().allMatch(n -> {
      allCount[0]++;
      return n % 2 != 0; // 2가 처음으로 조건 불만족 → 2번째 요소에서 종료
    });

    // noneMatch: 조건 만족 시 즉시 종료 → 6에서 찾으면 이후는 검사 안 함
    numbers.stream().noneMatch(n -> {
      noneCount[0]++;
      return n > 5;
    });

    System.out.println("  anyMatch(n>5)  - 검사 횟수: " + anyCount[0]  + "번 (6번째에서 종료)");
    System.out.println("  allMatch(홀수) - 검사 횟수: " + allCount[0]  + "번 (2번째에서 종료)");
    System.out.println("  noneMatch(n>5) - 검사 횟수: " + noneCount[0] + "번 (6번째에서 종료)");
    // 모두 10번(전체)을 검사하지 않고 조기 종료한다.

    System.out.println();
    System.out.println("→ anyMatch/allMatch/noneMatch는 단락 평가(short-circuit)로 결과 확정 시 즉시 종료한다.");
    System.out.println("→ 빈 스트림: anyMatch=false, allMatch=true(공허한 참), noneMatch=true.");
    System.out.println("→ noneMatch(p) == !anyMatch(p), allMatch(p) == !anyMatch(!p) 관계가 성립한다.");
  }
}
