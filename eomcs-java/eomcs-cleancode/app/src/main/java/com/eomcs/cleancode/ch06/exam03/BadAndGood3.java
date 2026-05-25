package com.eomcs.cleancode.ch06.exam03;

import java.io.File;
import java.io.IOException;

// 예제 3: 구조체 감추기 (Hiding Structure)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Options {
    private File scratchDirectory;
    Options(File scratchDirectory) { this.scratchDirectory = scratchDirectory; }
    public File getScratchDirectory() { return scratchDirectory; }
  }

  // Bad: 호출자가 내부 구조를 줄줄이 탐색한 뒤 직접 파일을 만든다.
  // - context가 options를 갖는다는 사실을 안다.
  // - options가 scratchDirectory를 갖는다는 사실을 안다.
  // - scratchDirectory에서 absolutePath를 꺼내야 한다는 사실을 안다.
  // - 그 경로로 File을 직접 생성해야 한다는 사실을 안다.
  // - 객체에게 일을 시키는 것이 아니라, 객체의 내부를 꺼내서 직접 처리한다.
  static class BadContext {
    private Options options;
    BadContext(Options options) { this.options = options; }
    public Options getOptions() { return options; }
  }

  static class BadClient {
    void createFile(BadContext context, String fileName) throws IOException {
      String path = context.getOptions()
                           .getScratchDirectory()
                           .getAbsolutePath();
      File file = new File(path, fileName);
      file.createNewFile();
    }
  }

  // Better: 호출 체인은 줄었지만 완전한 해결책이 아니다.
  // - context가 getter성 메서드를 계속 추가해야 한다.
  //   예: getScratchDirectoryAbsolutePath(), getTempDirectoryAbsolutePath() ...
  // - 메서드 폭발로 이어질 수 있다.
  static class BetterContext {
    private Options options;
    BetterContext(Options options) { this.options = options; }

    public String getScratchDirectoryAbsolutePath() {
      return options.getScratchDirectory().getAbsolutePath();
    }
  }

  static class BetterClient {
    void createFile(BetterContext context, String fileName) throws IOException {
      String path = context.getScratchDirectoryAbsolutePath();
      File file = new File(path, fileName);
      file.createNewFile();
    }
  }

  // Good: 호출자는 객체에게 일을 시키기만 한다.
  // - scratch directory의 구조를 전혀 알 필요가 없다.
  // - 파일 생성 책임이 Context 안으로 들어간다.
  // - 내부 구현이 바뀌어도 호출 코드는 변경되지 않는다.
  static class GoodContext {
    private Options options;
    GoodContext(Options options) { this.options = options; }

    public File createScratchFile(String fileName) throws IOException {
      File scratchDirectory = options.getScratchDirectory();
      File file = new File(scratchDirectory, fileName);
      file.createNewFile();
      return file;
    }
  }

  static class GoodClient {
    void createFile(GoodContext context, String fileName) throws IOException {
      context.createScratchFile(fileName);
    }
  }
}
