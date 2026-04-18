package com.eomcs.advanced.stream.exam04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 슬라이싱 - limit()과 skip():
//
// limit(long n)
//   - 스트림의 앞에서부터 최대 n개의 요소만 남긴다.
//   - n이 스트림 크기보다 크면 전체 요소를 반환한다.
//   - 단락 평가(short-circuit)를 지원한다. n개를 채우면 나머지 요소를 처리하지 않는다.
//   - 무한 스트림을 유한 스트림으로 만들 때 사용한다.
//
// skip(long n)
//   - 스트림의 앞에서부터 n개의 요소를 버린다.
//   - n이 스트림 크기보다 크면 빈 스트림을 반환한다.
//
// limit + skip 조합 → 페이지네이션:
//   페이지 번호(0부터 시작)와 페이지 크기로 원하는 구간을 잘라낼 수 있다.
//   skip(page * size).limit(size)
//
// 효율적인 순서:
//   filter() 뒤에 limit/skip을 배치하면 filter 통과 요소 기준으로 자른다.
//   limit() → filter() 순서와 결과가 다를 수 있으므로 순서에 주의한다.
//

public class App3 {

  public static void main(String[] args) {

    List<Integer> numbers = IntStream.rangeClosed(1, 20)
        .boxed()
        .collect(Collectors.toList()); // [1, 2, 3, ..., 20]

    // ─────────────────────────────────────────────────────────────
    // 예제 1. limit(n) - 앞에서 n개만 취하기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] limit(n) - 앞에서 n개만");

    System.out.print("  앞 5개:       ");
    numbers.stream()
        .limit(5)
        .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5
    System.out.println();

    // 짝수 중 앞 3개
    System.out.print("  짝수 앞 3개:  ");
    numbers.stream()
        .filter(n -> n % 2 == 0) // 2 4 6 8 10 12 14 16 18 20
        .limit(3)                 // 앞 3개만 → 2 4 6
        .forEach(n -> System.out.print(n + " ")); // 2 4 6
    System.out.println();

    // limit이 스트림보다 크면 전체 반환
    System.out.print("  limit(100):   ");
    numbers.stream()
        .limit(100) // 20개뿐이므로 전체 반환
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. skip(n) - 앞에서 n개 건너뛰기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] skip(n) - 앞에서 n개 건너뛰기");

    System.out.print("  앞 15개 skip: ");
    numbers.stream()
        .skip(15)   // 앞 15개(1~15) 건너뜀 → 16 17 18 19 20 남음
        .forEach(n -> System.out.print(n + " ")); // 16 17 18 19 20
    System.out.println();

    // 짝수에서 앞 3개 skip
    System.out.print("  짝수 3개 skip: ");
    numbers.stream()
        .filter(n -> n % 2 == 0) // 2 4 6 8 10 12 14 16 18 20
        .skip(3)                  // 앞 3개(2 4 6) skip → 8 10 12 ...
        .forEach(n -> System.out.print(n + " ")); // 8 10 12 14 16 18 20
    System.out.println();

    // skip이 스트림보다 크면 빈 스트림
    System.out.println("  skip(100) count: " + numbers.stream().skip(100).count()); // 0

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. limit + skip 조합 - 페이지네이션
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] limit + skip 조합 - 페이지네이션");

    // 페이지 크기: 5, 페이지 번호: 0부터 시작
    int pageSize = 5;

    // skip(page * pageSize).limit(pageSize) 패턴
    for (int page = 0; page < 4; page++) {
      List<Integer> pageData = numbers.stream()
          .skip((long) page * pageSize) // 앞 페이지 데이터 건너뜀
          .limit(pageSize)              // 현재 페이지 크기만큼 취함
          .toList();
      System.out.printf("  페이지 %d: %s%n", page, pageData);
    }
    // 페이지 0: [1, 2, 3, 4, 5]
    // 페이지 1: [6, 7, 8, 9, 10]
    // 페이지 2: [11, 12, 13, 14, 15]
    // 페이지 3: [16, 17, 18, 19, 20]

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. filter + limit - 조건을 만족하는 앞 N개
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] filter + limit - 조건을 만족하는 앞 N개");

    List<String> names = Arrays.asList(
        "Alice", "Amy", "Bob", "Anna", "Charlie", "Aaron", "Dave");

    // 'A'로 시작하는 이름 중 처음 2개
    System.out.print("  A로 시작 앞 2개: ");
    names.stream()
        .filter(name -> name.startsWith("A")) // Alice Amy Anna Aaron
        .limit(2)                             // 앞 2개만 → Alice Amy
        .forEach(name -> System.out.print(name + " ")); // Alice Amy
    System.out.println();

    // 'A'로 시작하는 이름에서 첫 1개 skip 후 2개 취하기
    System.out.print("  A로 시작 1 skip 후 2개: ");
    names.stream()
        .filter(name -> name.startsWith("A")) // Alice Amy Anna Aaron
        .skip(1)                              // 첫 번째(Alice) skip
        .limit(2)                             // 2개만 → Amy Anna
        .forEach(name -> System.out.print(name + " ")); // Amy Anna
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. limit + skip 순서에 따른 결과 차이
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] limit + skip 순서에 따른 결과 차이");

    // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    List<Integer> small = IntStream.rangeClosed(1, 10).boxed().toList();

    // limit(5) 후 skip(2): 앞 5개(1~5) 자른 뒤 2개(1,2) 건너뜀 → [3, 4, 5]
    System.out.print("  limit(5).skip(2):  ");
    small.stream()
        .limit(5) // [1, 2, 3, 4, 5]
        .skip(2)  // [3, 4, 5]
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // skip(2) 후 limit(5): 앞 2개(1,2) 건너뛴 뒤 5개 자름 → [3, 4, 5, 6, 7]
    System.out.print("  skip(2).limit(5):  ");
    small.stream()
        .skip(2)  // [3, 4, 5, 6, 7, 8, 9, 10]
        .limit(5) // [3, 4, 5, 6, 7]
        .forEach(n -> System.out.print(n + " "));
    System.out.println();
    // → limit(5).skip(2)과 skip(2).limit(5)은 다른 결과를 낸다.

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. 무한 스트림에서 limit으로 유한화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] 무한 스트림 + limit - 유한화");

    // 피보나치 수열에서 100보다 작은 앞 8개
    System.out.print("  피보나치 앞 8개: ");
    java.util.stream.Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
        .limit(8)
        .map(f -> f[0])
        .forEach(n -> System.out.print(n + " ")); // 0 1 1 2 3 5 8 13
    System.out.println();

    // 2의 거듭제곱 중 1000 이하인 값들
    System.out.print("  2^n ≤ 1000:      ");
    java.util.stream.Stream.iterate(1, n -> n * 2)
        .limit(20)                 // 충분히 크게 제한
        .filter(n -> n <= 1000)    // 1000 이하만
        .forEach(n -> System.out.print(n + " ")); // 1 2 4 8 16 32 64 128 256 512
    System.out.println();

    System.out.println();
    System.out.println("→ limit(n): 앞에서 n개만 취한다. 단락 평가로 나머지는 처리하지 않는다.");
    System.out.println("→ skip(n): 앞에서 n개를 버린다. filter 뒤에 오면 통과한 요소 기준으로 skip한다.");
    System.out.println("→ skip(page * size).limit(size) 패턴으로 페이지네이션을 구현할 수 있다.");
    System.out.println("→ limit과 skip의 순서가 바뀌면 결과가 달라지므로 순서에 주의한다.");
  }
}
