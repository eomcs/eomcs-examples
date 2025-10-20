// # 아이템 63. 문자열 연결은 느리니 주의하라
// - 문자열 연결 연산자(+)는 편리하지만, 성능에 민감한 상황에서는 주의가 필요하다.
// - 문자열 연결 연산자로 문자열 n개를 잇는 시간은 n^2에 비례한다.
//   문자열은 불변이라서 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야 하므로
//   성능 저하는 피할 수 없는 결과다.
// - 성능을 포기하고 싶지 않다면 String 대신 StringBuilder를 사용하라.
//   문자 배열을 사용하거나 문자열을 연결하지 않고 하나씩 처리하는 방법도 있다.
//
package effectivejava.ch09.item63.exam01;

// [주제] 문자열 연결을 잘못 사용한 예

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {

  public static void main(String[] args) {
    File file = new File("words.txt"); // 같은 디렉터리에 있는 파일

    // 문자열 연결 연산자(+)를 사용한 예
    String content = "";
    try (Scanner scanner = new Scanner(file)) {
      long start = System.currentTimeMillis();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine(); // 한 줄 읽기
        content += line + "\n"; // 문자열 연결 연산자(+) 사용
      }
      long end = System.currentTimeMillis();
      System.out.printf("경과 시간: %dms\n", (end - start));

    } catch (FileNotFoundException e) {
      System.err.println("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
    }
    System.out.println("----------------------");

    // StringBuilder를 사용한 예
    StringBuilder content2 = new StringBuilder();
    try (Scanner scanner = new Scanner(file)) {
      long start = System.currentTimeMillis();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine(); // 한 줄 읽기
        content2.append(line).append("\n"); // StringBuilder 사용
      }
      long end = System.currentTimeMillis();
      System.out.printf("경과 시간: %dms\n", (end - start));

    } catch (FileNotFoundException e) {
      System.err.println("파일을 찾을 수 없습니다: " + file.getAbsolutePath());
    }

    // [정리]
    // - 문자열 연결은 + 연산자보다 StringBuilder가 훨씬 빠르다.
  }
}
