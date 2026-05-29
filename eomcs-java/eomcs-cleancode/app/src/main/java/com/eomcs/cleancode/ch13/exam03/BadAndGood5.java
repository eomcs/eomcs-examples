package com.eomcs.cleancode.ch13.exam03;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 예제 5: 더 현실적인 구조 - 각 스레드가 독립적인 작업 단위를 처리한다
public class BadAndGood5 {

  private BadAndGood5() {}

  // Bad: 모든 스레드가 공유 결과 컬렉션에 동시에 접근한다
  //   - results.add()가 여러 스레드에서 동시에 호출됨
  //   - 동기화 없이는 결과가 유실되거나 깨진다
  //   - 공유 상태를 보호하기 위해 복잡한 동기화가 필요하다
  static class BadFileProcessor {

    private final List<String> results = new ArrayList<>();

    public void processFiles(List<File> files) {
      for (File file : files) {
        new Thread(() -> {
          String result = read(file);
          results.add(result); // 여러 스레드에서 동시 접근
        }).start();
      }
    }

    public List<String> getResults() {
      return Collections.unmodifiableList(results);
    }

    private String read(File file) {
      return "content of " + file.getName();
    }
  }

  // Good: 각 스레드가 하나의 파일만 완전히 독립적으로 처리한다
  //   - 공유 상태 없음
  //   - 스레드 간 영향 없음
  //   - 스레드 수가 늘어도 복잡성이 증가하지 않는다
  static class FileProcessor {

    public void processFiles(List<File> files) {
      for (File file : files) {
        new Thread(() -> processFile(file)).start();
      }
    }

    private void processFile(File file) {
      String content = read(file);
      String result = transform(content);
      save(result);
    }

    private String read(File file) {
      return "content of " + file.getName();
    }

    private String transform(String content) {
      return content.toUpperCase();
    }

    private void save(String result) {
      System.out.println("saved: " + result);
    }
  }
}
