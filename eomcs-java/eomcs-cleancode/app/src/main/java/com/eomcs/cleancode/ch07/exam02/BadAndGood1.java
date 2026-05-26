package com.eomcs.cleancode.ch07.exam02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// 예제 1: try-catch-finally 문부터 작성하라 - readFile
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: 예외 처리가 전혀 없다.
  // - 파일이 없으면 FileNotFoundException으로 프로그램이 깨진다.
  // - 읽기 중 오류가 나면 close()가 호출되지 않아 리소스가 누수된다.
  // - "무조건 성공"을 가정하고 정상 흐름만 작성했다.
  static class BadFileReader {
    public void readFile(String path) throws IOException {
      FileInputStream input = new FileInputStream(path);

      byte[] data = new byte[input.available()];
      input.read(data);

      System.out.println(new String(data));

      input.close();
    }
  }

  // Good: 오류 처리 구조를 먼저 설계한 뒤 정상 흐름을 채운다.
  // - 예외 상황별로 대응이 명확하다.
  // - 실패 상황이 코드에 드러난다.
  // - 프로그램이 안정적으로 동작한다.
  static class GoodFileReader {
    public void readFile(String path) {
      try {
        FileInputStream input = new FileInputStream(path);
        byte[] data = new byte[input.available()];
        input.read(data);
        System.out.println(new String(data));
        input.close();
      } catch (FileNotFoundException e) {
        System.out.println("파일을 찾을 수 없음");
      } catch (IOException e) {
        System.out.println("파일 읽기 오류");
      }
    }
  }
}
