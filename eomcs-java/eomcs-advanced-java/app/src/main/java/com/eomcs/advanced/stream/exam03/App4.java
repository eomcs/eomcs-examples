package com.eomcs.advanced.stream.exam03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 스트림 소스 생성 (4) - 파일과 문자열:
//
// Files.lines(Path)
//   - 텍스트 파일의 각 줄을 Stream<String>으로 제공한다.
//   - 파일 전체를 메모리에 올리지 않고 지연(lazy) 처리하므로 대용량 파일에 효율적이다.
//   - try-with-resources로 스트림을 닫아야 한다. (파일 핸들 누수 방지)
//
// Files.walk(Path)
//   - 디렉토리 트리를 재귀적으로 탐색해 Stream<Path>를 반환한다.
//   - Files.walk(path, maxDepth)로 탐색 깊이를 제한할 수 있다.
//   - try-with-resources로 스트림을 닫아야 한다.
//
// Files.list(Path)
//   - 지정한 디렉토리의 직속 항목(파일+서브디렉토리)을 Stream<Path>로 반환한다.
//   - Files.walk()와 달리 재귀 탐색을 하지 않는다.
//
// String.chars()
//   - 문자열의 각 문자(char)를 IntStream으로 반환한다.
//   - 각 요소는 char의 유니코드 코드 포인트(int)이다.
//
// Pattern.splitAsStream(CharSequence)
//   - 정규식 패턴으로 문자열을 분할해 Stream<String>으로 반환한다.
//   - String.split()과 달리 배열 대신 스트림을 반환하므로 파이프라인에 바로 연결할 수 있다.
//
// Stream.concat(Stream<T>, Stream<T>)
//   - 두 스트림을 하나로 이어 붙인다.
//   - concat한 스트림을 닫으면 두 소스 스트림도 함께 닫힌다.
//

public class App4 {

  public static void main(String[] args) throws IOException {

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 문자열 → 문자 스트림 (String.chars)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] String.chars() - 문자 스트림");

    String sentence = "Hello, World!";

    // chars()는 각 char를 int(유니코드 코드 포인트)로 반환하는 IntStream이다.
    System.out.print("  문자 출력: ");
    sentence
        .chars()
        .mapToObj(c -> String.valueOf((char) c)) // int → char → String
        .forEach(c -> System.out.print(c + " "));
    System.out.println();

    // 대문자 개수 세기
    long upperCount =
        sentence
            .chars()
            .filter(Character::isUpperCase) // 대문자 필터
            .count();
    System.out.println("  대문자 개수: " + upperCount); // 2 (H, W)

    // 알파벳만 추출하여 소문자로 변환
    System.out.print("  알파벳만:   ");
    sentence
        .chars()
        .filter(Character::isLetter) // 알파벳만 통과
        .map(Character::toLowerCase) // 소문자 변환
        .mapToObj(c -> String.valueOf((char) c))
        .forEach(c -> System.out.print(c + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 문자 빈도 분석
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 문자 빈도 분석 - chars() + Collectors.groupingBy");

    String text = "programming";

    // 각 문자가 몇 번 등장하는지 집계한다.
    text
        .chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
        .entrySet()
        .stream()
        .sorted(java.util.Map.Entry.<Character, Long>comparingByValue().reversed()) // 빈도 내림차순
        .forEach(e -> System.out.println("  '" + e.getKey() + "': " + e.getValue() + "회"));

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. Pattern.splitAsStream() - 정규식으로 분할
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] Pattern.splitAsStream() - 정규식으로 분할");

    String csv = "사과,바나나,,포도, 딸기 ,블루베리";

    // String.split()은 배열을 반환한다. → 스트림 파이프라인에 연결하려면 Arrays.stream() 필요
    String[] arr = csv.split(",");
    System.out.println("  split 배열 크기: " + arr.length); // 6 (빈 문자열 포함)

    // Pattern.splitAsStream()은 스트림을 바로 반환한다. 파이프라인에 직접 연결할 수 있다.
    Pattern comma = Pattern.compile(",");
    List<String> fruits =
        comma
            .splitAsStream(csv)
            .map(String::trim) // 앞뒤 공백 제거
            .filter(s -> !s.isEmpty()) // 빈 문자열 제거
            .sorted() // 알파벳 순 정렬
            .toList();
    System.out.println("  splitAsStream: " + fruits);

    // 공백이나 탭으로 구분된 문자열 분할
    String words = "Java   is   a   powerful   language";
    System.out.print("  공백 분할: ");
    Pattern.compile("\\s+") // 하나 이상의 공백
        .splitAsStream(words)
        .forEach(w -> System.out.print(w + " "));
    System.out.println();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. Stream.concat() - 두 스트림 합치기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] Stream.concat() - 두 스트림 이어 붙이기");

    Stream<String> stream1 = Stream.of("사과", "바나나", "포도");
    Stream<String> stream2 = Stream.of("딸기", "블루베리", "키위");

    // concat: stream1의 요소가 먼저, stream2의 요소가 이어진다.
    List<String> combined = Stream.concat(stream1, stream2).sorted().toList();
    System.out.println("  합친 후 정렬: " + combined);

    // 여러 스트림 합치기: concat을 중첩하거나 Stream.of(streams).flatMap(s -> s) 사용
    Stream<String> a = Stream.of("A1", "A2");
    Stream<String> b = Stream.of("B1", "B2");
    Stream<String> c = Stream.of("C1", "C2");

    List<String> merged =
        Stream.of(a, b, c)
            .flatMap(s -> s) // 각 스트림을 평탄화
            .toList();
    System.out.println("  3개 합치기:   " + merged);

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 5. Files.lines() - 파일의 각 줄을 스트림으로
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 5] Files.lines() - 파일 줄 스트림");

    // 예제용 임시 파일 생성
    Path tempFile = Files.createTempFile("stream-exam03", ".txt");
    Files.writeString(
        tempFile,
        """
        Java Stream API
        Optional and flatMap
        # 주석 줄
        Functional Programming
        # 또 다른 주석
        Declarative Style
        """);

    // Files.lines()는 파일 핸들을 사용하므로 try-with-resources로 반드시 닫는다.
    try (Stream<String> lines = Files.lines(tempFile)) {
      System.out.println("  전체 줄 수: " + lines.count());
    }

    // 주석(#으로 시작)을 제외하고, 대문자로 변환하여 출력
    try (Stream<String> lines = Files.lines(tempFile)) {
      System.out.println("  주석 제외 대문자:");
      lines
          .filter(line -> !line.startsWith("#")) // 주석 줄 제거
          .map(String::toUpperCase)
          .forEach(line -> System.out.println("    " + line));
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 6. Files.list() - 디렉토리 직속 항목 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 6] Files.list() - 디렉토리 항목 스트림");

    Path tempDir = Files.createTempDirectory("stream-exam03-dir");
    Files.createFile(tempDir.resolve("gamma.txt"));
    Files.createFile(tempDir.resolve("beta.java"));
    Files.createDirectory(tempDir.resolve("subdir"));
    Files.createFile(tempDir.resolve("alpha.txt"));

    // Files.list(): 현재 디렉토리의 직속 항목만 반환 (재귀 탐색 없음)
    try (Stream<Path> entries = Files.list(tempDir)) {
      List<String> names =
          entries
              .map(p -> p.getFileName().toString()) // 파일 이름만 추출
              .sorted()
              .toList();
      System.out.println("  디렉토리 항목: " + names);
    }

    // .txt 파일만 필터링
    try (Stream<Path> entries = Files.list(tempDir)) {
      List<String> txtFiles =
          entries
              .filter(p -> p.toString().endsWith(".txt"))
              .map(p -> p.getFileName().toString())
              .sorted()
              .toList();
      System.out.println("  .txt 파일:     " + txtFiles);
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 7. Files.walk() - 디렉토리 트리 재귀 탐색
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 7] Files.walk() - 디렉토리 트리 탐색");

    Path subdir = tempDir.resolve("subdir");
    Files.createFile(subdir.resolve("delta.txt"));
    Files.createFile(subdir.resolve("epsilon.java"));

    // Files.walk(): 재귀적으로 모든 하위 항목을 Stream<Path>로 반환한다.
    try (Stream<Path> tree = Files.walk(tempDir)) {
      System.out.println("  전체 파일/디렉토리:");
      tree.filter(Files::isRegularFile) // 파일만 (디렉토리 제외)
          .map(p -> tempDir.relativize(p).toString()) // 상대 경로로 변환
          .sorted()
          .forEach(name -> System.out.println("    " + name));
    }

    // 깊이 제한: walk(path, maxDepth) - 최대 탐색 깊이 지정
    try (Stream<Path> shallow = Files.walk(tempDir, 1)) { // 직속 항목만 (depth=1)
      long count = shallow.filter(Files::isRegularFile).count();
      System.out.println("  depth=1 파일 수: " + count); // subdir 아래는 미포함
    }

    // 임시 파일/디렉토리 정리
    Files.deleteIfExists(subdir.resolve("delta.txt"));
    Files.deleteIfExists(subdir.resolve("epsilon.java"));
    Files.deleteIfExists(tempDir.resolve("alpha.txt"));
    Files.deleteIfExists(tempDir.resolve("beta.java"));
    Files.deleteIfExists(tempDir.resolve("gamma.txt"));
    Files.deleteIfExists(subdir);
    Files.deleteIfExists(tempDir);
    Files.deleteIfExists(tempFile);

    System.out.println();
    System.out.println("→ Files.lines()는 대용량 파일을 지연(lazy) 처리하므로 메모리 효율적이다. try-with-resources 필수.");
    System.out.println("→ Files.list()는 직속 항목만, Files.walk()는 재귀적으로 하위 항목 전체를 스트림으로 제공한다.");
    System.out.println("→ Pattern.splitAsStream()은 split() 결과를 배열 없이 스트림 파이프라인에 바로 연결한다.");
    System.out.println(
        "→ String.chars()는 각 문자를 int(코드 포인트)로 제공한다. mapToObj()로 char/String으로 변환한다.");
  }
}
