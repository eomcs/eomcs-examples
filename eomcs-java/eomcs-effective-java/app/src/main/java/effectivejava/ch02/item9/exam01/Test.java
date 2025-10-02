// # 아이템 9. try-finally 보다는 try-with-resources를 사용하라
// - 사용한 후 꼭 해제시켜야 하는 자원을 다룰 때는 try-finally 말고,
//   try-with-resources 문법을 사용하자.
// - 코드는 더 짧고 분명해지고, 만들어지는 예죄 정보도 훨씬 유용하다.
// - try-finally 는 실용적이지 못할 만큼 코드가 지저분해진다.

package effectivejava.ch02.item9.exam01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {

  // try-finally 문법을 사용하여 자원을 해제하는 예:
  static String firstLineOfFile(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
      // 테스트를 위해 br 객체를 null로 만들어 보자.
      br = null;

      return br.readLine(); // NullPointerException 발생!
    } finally {
      // reader 객체에 문제가 발생했다면,
      // - read()에서 예외가 발생할 것이고, finally 블록이 실행될 것이다.
      // - br 객체 때문에, close()에서도 예외가 발생할 것이다.
      // - 결국 read()에서 발생한 예외는 사라지고, close()에서 발생한 예외가 호출자에게 전달된다.
      // - 즉, 진짜 문제의 원인을 알 수 없게 된다.
      br.close(); // NullPointerException 발생!
    }
  }

  // try-with-resources 문법을 사용하여 자원을 해제하는 예:
  static String firstLineOfFile2(String path) throws Exception {
    File f = new File(path);
    try (Scanner scanner = new Scanner(new java.io.FileReader(path))) {
      return scanner.nextLine();
    }
    // - try-finally 문법보다 코드가 더 간결하고 읽기 쉽다.
    // - 문제를 진단하기도 훨씬 좋다.
  }

  public static void main(String[] args) throws Exception {

    // try-finally 코드에서 예외가 발생했을 때
    // - readLine()에서 발생한 예외는 사라지고, close()에서 발생한 예외가 호출자에게 전달된다.
    // - 즉, 진짜 문제의 원인을 알 수 없게 된다.
    //    System.out.println(firstLineOfFile("test.txt"));

    // try-with-resources 코드에서 예외가 발생했을 때
    // - readLine()에서 발생한 예외가 호출자에게 전달된다.
    // - 자동으로 호출되는 close()에서 예외가 발생하더라도
    //   readLine()에서 발생한 예외가 호출자에게 전달된다.
    System.out.println(firstLineOfFile2("test.txt"));
  }
}
