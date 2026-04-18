package com.eomcs.advanced.stream.exam01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// 명령형 vs 선언형 - 수집(collect)과 정렬(sorted):
//
// collect(Collectors.toList())
//   - 스트림의 결과 요소를 새 List로 모은다.
//   - 명령형의 "새 리스트 생성 → for 루프에서 add()" 패턴에 대응한다.
//
// sorted()
//   - 요소를 자연 순서(오름차순)로 정렬한다.
//   - sorted(Comparator)를 사용하면 정렬 기준을 직접 지정할 수 있다.
//
// 파이프라인 조합:
//   filter → sorted → map → collect 처럼 연산을 자유롭게 이어 붙일 수 있다.
//   명령형에서는 각 단계마다 임시 컬렉션을 만들어야 했지만,
//   스트림은 파이프라인 전체를 한 흐름으로 처리한다.
//

public class App3 {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2, 7, 4, 6, 10);
    List<String> names = Arrays.asList("charlie", "alice", "eve", "bob", "dave");

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 짝수만 새 리스트로 수집
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 짝수만 새 리스트로 수집");

    // 명령형: 새 리스트를 직접 생성하고 add()로 하나씩 넣는다.
    List<Integer> evenImperative = new ArrayList<>();
    for (int n : numbers) {
      if (n % 2 == 0) {
        evenImperative.add(n); // 결과 컨테이너를 직접 관리해야 한다.
      }
    }
    System.out.println("  명령형: " + evenImperative);

    // 선언형: collect()가 요소를 새 List로 모아 준다. 컨테이너를 직접 관리하지 않아도 된다.
    List<Integer> evenStream = numbers.stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList()); // 최종 연산: 결과를 List로 수집
    System.out.println("  선언형: " + evenStream);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 숫자를 오름차순 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 숫자를 오름차순 정렬하여 새 리스트로 수집");

    // 명령형: 원본을 복사한 뒤 Collections.sort()로 정렬한다.
    List<Integer> sortedImperative = new ArrayList<>(numbers); // 원본 보호를 위해 복사
    Collections.sort(sortedImperative);                        // 원본 리스트를 직접 수정
    System.out.println("  명령형: " + sortedImperative);

    // 선언형: sorted()는 원본을 변경하지 않고 정렬된 스트림을 만든다.
    List<Integer> sortedStream = numbers.stream()
        .sorted()                      // 중간 연산: 자연 순서(오름차순) 정렬
        .collect(Collectors.toList()); // 최종 연산: 새 List로 수집
    System.out.println("  선언형: " + sortedStream);
    System.out.println("  원본 유지: " + numbers); // 원본은 변경되지 않는다.

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. 문자열을 알파벳 순으로 정렬
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] 이름을 알파벳 순으로 정렬하여 수집");

    // 명령형
    List<String> sortedNamesImperative = new ArrayList<>(names);
    Collections.sort(sortedNamesImperative);
    System.out.println("  명령형: " + sortedNamesImperative);

    // 선언형
    List<String> sortedNamesStream = names.stream()
        .sorted()
        .collect(Collectors.toList());
    System.out.println("  선언형: " + sortedNamesStream);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. 필터링 + 정렬 + 변환 + 수집 조합
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] 5 이상인 수를 내림차순 정렬 후 제곱하여 수집");

    // 명령형: 각 단계마다 임시 컬렉션이 필요하다.
    List<Integer> step1 = new ArrayList<>();
    for (int n : numbers) {
      if (n >= 5) step1.add(n);        // 1단계: 필터
    }
    Collections.sort(step1, Collections.reverseOrder()); // 2단계: 내림차순 정렬
    List<Integer> imperativeResult = new ArrayList<>();
    for (int n : step1) {
      imperativeResult.add(n * n);     // 3단계: 변환
    }
    System.out.println("  명령형: " + imperativeResult);

    // 선언형: 임시 컬렉션 없이 파이프라인 한 줄로 처리된다.
    List<Integer> streamResult = numbers.stream()
        .filter(n -> n >= 5)                          // 1단계: 필터
        .sorted(Collections.reverseOrder())           // 2단계: 내림차순 정렬
        .map(n -> n * n)                              // 3단계: 변환
        .collect(Collectors.toList());                // 수집
    System.out.println("  선언형: " + streamResult);

    System.out.println();
    System.out.println("→ collect()는 스트림의 결과를 List, Set, Map 등 원하는 형태로 모아 준다.");
    System.out.println("→ sorted()는 원본을 변경하지 않고, 정렬된 새 스트림을 만든다.");
    System.out.println("→ 파이프라인을 이어 붙여도 임시 컬렉션이 필요 없어 코드가 간결해진다.");
  }
}
