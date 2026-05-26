package com.eomcs.cleancode.ch07.exam02;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 예제 2: 단계별 코드 작성 예 - copyFile
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: try-catch 없이 정상 흐름만 작성했다.
  // - 예외 발생 시 리소스가 닫히지 않아 누수가 생긴다.
  // - 코드가 "무조건 성공"을 가정하고 있다.
  @SuppressWarnings("java:S2095") // 의도적인 Bad 예제 — 리소스 미닫힘이 핵심 문제점
  static class BadFileCopier {
    public void copyFile(String src, String dest) throws IOException {
      FileInputStream input = new FileInputStream(src);
      FileOutputStream output = new FileOutputStream(dest);

      byte[] buffer = new byte[1024];
      int n;

      while ((n = input.read(buffer)) != -1) {
        output.write(buffer, 0, n);
      }

      input.close();
      output.close();
    }
  }

  // Step 1: try-catch-finally 틀을 먼저 잡는다.
  // - 아직 복사 로직은 없다.
  // - 먼저 성공 흐름 / 실패 흐름 / 정리 흐름의 자리를 확보한다.
  static class Step1FileCopier {
    @SuppressWarnings("java:S1172") // 골격 단계 — 파라미터는 다음 단계에서 사용된다
    public void copyFile(String src, String dest) {
      try {
        // 정상 처리 (다음 단계에서 채운다)
      } catch (Exception e) {
        // 오류 처리 (다음 단계에서 채운다)
      } finally {
        // 정리 작업 (다음 단계에서 채운다)
      }
    }
  }

  // Step 2: 리소스 변수를 finally에서 접근 가능하게 try 밖에 선언한다.
  // - input, output을 try 안에서만 선언하면 finally에서 닫을 수 없다.
  // - 리소스 생명주기를 try-catch-finally 전체로 확장한다.
  static class Step2FileCopier {
    @SuppressWarnings("java:S1172") // 골격 단계 — 파라미터는 다음 단계에서 사용된다
    public void copyFile(String src, String dest) {
      FileInputStream input = null;   // try 밖에 선언
      FileOutputStream output = null; // try 밖에 선언

      try {
        // 정상 처리 (다음 단계에서 채운다)
      } catch (Exception e) {
        // 오류 처리 (다음 단계에서 채운다)
      } finally {
        // 정리 작업: input, output을 여기서 닫는다 (다음 단계에서 채운다)
        System.out.println("close: " + input + ", " + output);
      }
    }
  }

  // Step 3~5: try에 정상 흐름, catch에 오류 처리, finally에 리소스 해제를 채운다.
  // - 예외가 발생해도 리소스 해제가 보장된다.
  // - try-with-resources 이전 시대의 표준 패턴이다.
  @SuppressWarnings({"java:S2093", "java:S2095"}) // 의도적인 고전 패턴 — 현대 Java는 try-with-resources 권장
  static class Step3To5FileCopier {
    public void copyFile(String src, String dest) {
      FileInputStream input = null;
      FileOutputStream output = null;

      try {
        input = new FileInputStream(src);
        output = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int n;

        while ((n = input.read(buffer)) != -1) {
          output.write(buffer, 0, n);
        }
      } catch (IOException e) {
        System.out.println("파일 처리 중 오류가 발생했습니다.");
      } finally {
        try {
          if (input != null) {
            input.close();
          }
          if (output != null) {
            output.close();
          }
        } catch (IOException e) {
          System.out.println("리소스 해제 중 오류가 발생했습니다.");
        }
      }
    }
  }

  // Step 6 (Good): Clean Code식으로 역할을 분리한다.
  // - copyFile()은 흐름 제어만 담당한다.
  // - 각 역할(열기, 복사, 오류 처리, 닫기)이 별도 메서드로 분리된다.
  // - 코드가 이야기처럼 읽힌다: openInput → openOutput → copy → handleError → close
  static class GoodFileCopier {
    public void copyFile(String src, String dest) {
      FileInputStream input = null;
      FileOutputStream output = null;

      try {
        input = openInput(src);
        output = openOutput(dest);
        copy(input, output);
      } catch (IOException e) {
        handleCopyError(e);
      } finally {
        close(input);
        close(output);
      }
    }

    private FileInputStream openInput(String src) throws FileNotFoundException {
      return new FileInputStream(src);
    }

    private FileOutputStream openOutput(String dest) throws FileNotFoundException {
      return new FileOutputStream(dest);
    }

    private void copy(InputStream input, OutputStream output) throws IOException {
      byte[] buffer = new byte[1024];
      int n;

      while ((n = input.read(buffer)) != -1) {
        output.write(buffer, 0, n);
      }
    }

    private void handleCopyError(IOException e) {
      System.out.println("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
    }

    private void close(Closeable resource) {
      try {
        if (resource != null) {
          resource.close();
        }
      } catch (IOException e) {
        System.out.println("리소스 해제 중 오류가 발생했습니다: " + e.getMessage());
      }
    }
  }
}
