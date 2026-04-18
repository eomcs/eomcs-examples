package com.eomcs.advanced.stream.exam01;

import java.util.Arrays;
import java.util.List;

// 명령형 vs 선언형 - 변환(map)과 집계(reduce):
//
// map(Function<T, R>)
//   - 각 요소를 다른 값으로 변환한다.
//   - 명령형의 "for + 임시 변수에 변환값 저장" 패턴에 대응한다.
//
// mapToInt / mapToLong / mapToDouble
//   - 기본형(primitive) 스트림으로 변환한다.
//   - sum(), average(), max(), min() 같은 수치 집계를 바로 호출할 수 있다.
//
// reduce(identity, BinaryOperator)
//   - 모든 요소를 하나의 값으로 합친다.
//   - 명령형의 "for + 누적 변수(acc)" 패턴에 대응한다.
//

public class App2 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<String> names = Arrays.asList("alice", "bob", "charlie", "dave", "eve");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 각 숫자를 제곱으로 변환하여 출력
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 각 숫자를 제곱으로 변환하여 출력");

    // 명령형: 변환 결과를 직접 출력하려면 루프 안에서 계산해야 한다.
    System.out.print("  명령형: ");
    for (int n : numbers) {
      System.out.print((n * n) + " "); // 변환과 출력이 한 곳에 섞여 있다.
    }
    System.out.println();

    // 선언형: map()이 변환, forEach()가 출력을 각각 담당한다. 역할이 분리된다.
    System.out.print("  선언형: ");
    numbers.stream()
        .map(n -> n * n) // 중간 연산: 각 요소를 제곱으로 변환
        .forEach(n -> System.out.print(n + " ")); // 최종 연산: 출력
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 문자열을 대문자로 변환하여 출력
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 이름을 대문자로 변환하여 출력");

    // 명령형
    System.out.print("  명령형: ");
    for (String name : names) {
      System.out.print(name.toUpperCase() + " ");
    }
    System.out.println();

    // 선언형
    System.out.print("  선언형: ");
    names.stream()
        .map(String::toUpperCase) // 메서드 레퍼런스로 더 간결하게 표현
        .forEach(name -> System.out.print(name + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 모든 숫자의 합 계산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 모든 숫자의 합 계산");

    // 명령형: 누적 변수(sum)를 선언하고 매 반복마다 더한다.
    int sum = 0;
    for (int n : numbers) {
      sum += n; // 누적 변수를 직접 관리해야 한다.
    }
    System.out.println("  명령형: " + sum);

    // 선언형: mapToInt()로 기본형 스트림으로 변환한 뒤 sum()을 바로 호출한다.
    int streamSum =
        numbers.stream()
            .mapToInt(Integer::intValue) // IntStream으로 변환 (또는 mapToInt(n -> n))
            .sum(); // 최종 연산: 합계 반환
    System.out.println("  선언형: " + streamSum);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 필터링 + 변환 + 합계 조합
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 짝수만 골라 제곱한 뒤 합계 계산");

    // 명령형: 세 단계(필터→변환→집계)가 루프 하나 안에 뒤섞인다.
    int imperativeResult = 0;
    for (int n : numbers) {
      if (n % 2 == 0) { // 필터
        int squared = n * n; // 변환
        imperativeResult += squared; // 집계
      }
    }
    System.out.println("  명령형: " + imperativeResult);

    // 선언형: 각 단계가 독립된 연산으로 분리되어 읽기 쉽다.
    int streamResult =
        numbers.stream()
            .filter(n -> n % 2 == 0) // 단계 1: 짝수 필터
            .mapToInt(n -> n * n) // 단계 2: 제곱 변환 (IntStream으로 전환)
            .sum(); // 단계 3: 합계
    System.out.println("  선언형: " + streamResult);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. reduce() - 직접 누적 로직을 정의할 때
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] reduce()로 곱셈 누적 (1×2×3×4×5)");

    List<Integer> small = Arrays.asList(1, 2, 3, 4, 5);

    // 명령형: 누적 변수를 직접 관리
    int product = 1;
    for (int n : small) {
      product *= n;
    }
    System.out.println("  명령형: " + product);

    // 선언형: reduce(초기값, 누적 함수)
    // - 초기값 1에서 시작해 각 요소를 곱한다.
    int streamProduct = small.stream().reduce(1, (acc, n) -> acc * n);
    System.out.println("  선언형: " + streamProduct);

    System.out.println();
    System.out.println("→ map()은 변환, reduce()·sum()은 집계를 담당하며 각 역할이 분리된다.");
    System.out.println("→ mapToInt()를 사용하면 sum(), average(), max(), min()을 바로 쓸 수 있다.");
  }
}
