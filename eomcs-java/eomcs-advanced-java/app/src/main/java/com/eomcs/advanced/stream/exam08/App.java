package com.eomcs.advanced.stream.exam08;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// 매핑 (1:N, Java 16+) - mapMulti():
//
// mapMulti(BiConsumer<T, Consumer<R>>)
//   - Java 16에서 추가된 중간 연산이다.
//   - 각 요소를 0개 이상의 요소로 변환한다. (1:N 매핑)
//   - flatMap()과 같은 목적이지만 방식이 다르다.
//
// flatMap vs mapMulti:
//   flatMap:    각 요소를 Stream<R>으로 변환 → Stream API가 합쳐줌
//   mapMulti:   각 요소에서 Consumer<R>로 직접 결과를 밀어 넣음
//
// mapMulti의 장점:
//   - 중간 Stream 객체를 생성하지 않아 오버헤드가 적다.
//   - 조건부로 0개, 1개, N개를 자유롭게 밀어 넣을 수 있다.
//   - 반복 로직이 복잡할 때 더 읽기 쉽다.
//
// 기본형 특화:
//   mapMultiToInt(BiConsumer<T, IntConsumer>)    → IntStream
//   mapMultiToLong(BiConsumer<T, LongConsumer>)  → LongStream
//   mapMultiToDouble(BiConsumer<T, DoubleConsumer>) → DoubleStream
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(-3, -1, 0, 2, 4, 5, 7, -2, 8);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. mapMulti 기본 - flatMap과 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] mapMulti vs flatMap - 양수를 두 배로 확장");

    // flatMap 방식: 각 요소를 Stream으로 변환
    System.out.print("  flatMap:   ");
    numbers.stream()
        .flatMap(n -> n > 0 ? Stream.of(n, n * 10) : Stream.empty())
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // mapMulti 방식: Consumer로 직접 밀어 넣음
    System.out.print("  mapMulti:  ");
    numbers.stream()
        .<Integer>mapMulti((n, downstream) -> {
          if (n > 0) {
            downstream.accept(n);      // 원래 값
            downstream.accept(n * 10); // 10배 값
          }
          // n <= 0이면 아무것도 밀어 넣지 않음 → 해당 요소 제거 효과
        })
        .forEach(n -> System.out.print(n + " "));
    System.out.println();
    // 결과: 2 20 4 40 5 50 7 70 8 80

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 요소 수 조절 - 0개, 1개, N개
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 요소 수 조절 - 0개 / 1개 / N개");

    List<String> words = Arrays.asList("a", "hello", "hi", "b", "stream", "ok");

    // 길이가 1이면 제거(0개), 2이면 유지(1개), 3 이상이면 원본+대문자(2개)
    System.out.println("  결과:");
    words.stream()
        .<String>mapMulti((word, downstream) -> {
          if (word.length() == 1) {
            // 0개: 아무것도 밀어 넣지 않음 (필터링 효과)
          } else if (word.length() == 2) {
            downstream.accept(word);                      // 1개: 유지
          } else {
            downstream.accept(word);                      // 2개: 원본
            downstream.accept(word.toUpperCase());        //      대문자
          }
        })
        .forEach(s -> System.out.println("    " + s));
    // hi, hello, HELLO, stream, STREAM, ok

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. mapMultiToInt - 중간 Stream 없이 IntStream으로
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] mapMultiToInt - 박싱 없이 IntStream으로 변환");

    List<String> sentences = Arrays.asList("hello world", "java stream api", "map multi");

    // 각 문장을 단어로 분리하고 단어 길이를 IntStream으로
    int totalLength = sentences.stream()
        .mapMultiToInt((sentence, downstream) -> {
          for (String word : sentence.split(" ")) {
            downstream.accept(word.length()); // 단어 길이를 IntConsumer로 밀어 넣음
          }
        })
        .sum();

    System.out.println("  문장: " + sentences);
    System.out.println("  모든 단어 길이 합계: " + totalLength); // 5+5+4+6+3+3+5 = 31

    sentences.stream()
        .mapMultiToInt((sentence, downstream) -> {
          for (String word : sentence.split(" ")) {
            downstream.accept(word.length());
          }
        })
        .forEach(len -> System.out.print("  " + len));
    System.out.println();

    System.out.println();
    System.out.println("→ mapMulti()는 flatMap()과 같은 1:N 변환이지만 중간 Stream을 생성하지 않는다.");
    System.out.println("→ Consumer에 밀어 넣는 횟수로 결과 요소 수를 자유롭게 조절한다(0개 포함).");
    System.out.println("→ mapMultiToInt/Long/Double로 박싱 없이 기본형 스트림으로 변환할 수 있다.");
  }
}
