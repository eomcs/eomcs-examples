package com.eomcs.advanced.stream.exam01;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// 명령형 vs 선언형 - 검색(find/match)과 통계:
//
// anyMatch(Predicate)  : 조건을 만족하는 요소가 하나라도 있으면 true
// allMatch(Predicate)  : 모든 요소가 조건을 만족하면 true
// noneMatch(Predicate) : 조건을 만족하는 요소가 하나도 없으면 true
//
// findFirst()          : 스트림에서 첫 번째 요소를 Optional로 반환
// findAny()            : 스트림에서 임의의 요소를 Optional로 반환 (병렬 스트림에서 유용)
//
// Optional<T>:
//   - 값이 있을 수도, 없을 수도 있는 컨테이너 객체
//   - null 반환 대신 Optional을 사용하면 NullPointerException을 방지할 수 있다.
//   - isPresent() : 값이 있으면 true
//   - get()       : 값을 꺼낸다. 값이 없으면 NoSuchElementException 발생
//   - orElse(T)   : 값이 없으면 기본값을 반환
//
// count()   : 요소 개수 반환
// max(), min() : 최대·최솟값 반환 (Optional로 반환)
//

public class App4 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 6, 1, 8, 5, 10);
    List<String> names = Arrays.asList("alice", "bob", "charlie", "dave", "eve");

    // ─────────────────────────────────────────────────────────────
    // 예제 0. noneMatch - 조건을 만족하는 요소가 하나도 없는지 확인
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 0] 길이가 10 이상인 이름이 없는지 확인");

    // 명령형
    boolean noneFoundImperative = true;
    for (String name : names) {
      if (name.length() >= 10) {
        noneFoundImperative = false;
        break;
      }
    }
    System.out.println("  명령형: " + noneFoundImperative);

    // 선언형
    boolean noneFoundStream = names.stream()
        .noneMatch(name -> name.length() >= 10); // 조건을 만족하는 요소가 없으면 true
    System.out.println("  선언형: " + noneFoundStream);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 조건을 만족하는 요소가 있는지 확인 (anyMatch)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 10보다 큰 수가 있는지 확인");

    // 명령형: 플래그 변수를 두고 루프를 돌며 확인한다.
    boolean foundImperative = false;
    for (int n : numbers) {
      if (n > 10) {
        foundImperative = true;
        break; // 찾으면 조기 탈출 (직접 관리해야 한다)
      }
    }
    System.out.println("  명령형: " + foundImperative);

    // 선언형: anyMatch()가 조기 탈출을 내부적으로 처리한다.
    boolean foundStream = numbers.stream()
        .anyMatch(n -> n > 10); // 단락 평가(short-circuit): 조건 충족 시 즉시 종료
    System.out.println("  선언형: " + foundStream);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 모든 요소가 조건을 만족하는지 확인 (allMatch)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 모든 수가 0보다 큰지 확인");

    // 명령형
    boolean allPositiveImperative = true;
    for (int n : numbers) {
      if (n <= 0) {
        allPositiveImperative = false;
        break;
      }
    }
    System.out.println("  명령형: " + allPositiveImperative);

    // 선언형
    boolean allPositiveStream = numbers.stream()
        .allMatch(n -> n > 0);
    System.out.println("  선언형: " + allPositiveStream);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 조건을 만족하는 첫 번째 요소 찾기 (findFirst)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 5보다 큰 첫 번째 수 찾기");

    // 명령형: null을 반환할 수 있어 호출자가 null 체크를 해야 한다.
    Integer firstFoundImperative = null;
    for (int n : numbers) {
      if (n > 5) {
        firstFoundImperative = n;
        break;
      }
    }
    if (firstFoundImperative != null) {
      System.out.println("  명령형: " + firstFoundImperative);
    } else {
      System.out.println("  명령형: 없음");
    }

    // 선언형: findFirst()는 Optional로 반환해 null을 직접 다루지 않아도 된다.
    Optional<Integer> firstFoundStream = numbers.stream()
        .filter(n -> n > 5)
        .findFirst(); // 조건을 만족하는 첫 번째 요소를 Optional로 반환

    // orElse(): 값이 없을 때 기본값을 지정한다. null 체크 없이 안전하게 사용 가능.
    System.out.println("  선언형: " + firstFoundStream.orElse(-1));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 조건을 만족하는 요소의 개수 세기 (count)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 5보다 큰 수의 개수");

    // 명령형
    int countImperative = 0;
    for (int n : numbers) {
      if (n > 5) {
        countImperative++;
      }
    }
    System.out.println("  명령형: " + countImperative + "개");

    // 선언형
    long countStream = numbers.stream()
        .filter(n -> n > 5)
        .count(); // 최종 연산: 요소 개수 반환 (long 타입)
    System.out.println("  선언형: " + countStream + "개");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. 최댓값·최솟값 찾기 (max / min)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] 최댓값과 최솟값 찾기");

    // 명령형
    int maxImperative = numbers.get(0);
    int minImperative = numbers.get(0);
    for (int n : numbers) {
      if (n > maxImperative) maxImperative = n;
      if (n < minImperative) minImperative = n;
    }
    System.out.println("  명령형: 최대=" + maxImperative + ", 최소=" + minImperative);

    // 선언형: max()/min()은 Comparator를 받아 기준을 지정한다. Optional로 반환한다.
    int maxStream = numbers.stream()
        .max(Integer::compareTo) // 자연 순서 기준 최댓값
        .orElseThrow();          // 스트림이 비어 있으면 예외 발생

    int minStream = numbers.stream()
        .min(Integer::compareTo) // 자연 순서 기준 최솟값
        .orElseThrow();

    System.out.println("  선언형: 최대=" + maxStream + ", 최소=" + minStream);

    System.out.println();
    System.out.println("→ anyMatch/allMatch/noneMatch는 조기 종료(short-circuit)를 자동으로 처리한다.");
    System.out.println("→ findFirst()는 Optional을 반환해 null 처리 없이 안전하게 결과를 다룰 수 있다.");
    System.out.println("→ count(), max(), min()으로 별도 루프 없이 통계를 바로 구할 수 있다.");
  }
}
