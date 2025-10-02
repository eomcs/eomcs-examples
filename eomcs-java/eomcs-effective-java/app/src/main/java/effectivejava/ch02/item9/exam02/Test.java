// # 아이템 9. try-finally 보다는 try-with-resources를 사용하라
// - 사용한 후 꼭 해제시켜야 하는 자원을 다룰 때는 try-finally 말고,
//   try-with-resources 문법을 사용하자.
// - 코드는 더 짧고 분명해지고, 만들어지는 예죄 정보도 훨씬 유용하다.
// - try-finally 는 실용적이지 못할 만큼 코드가 지저분해진다.

package effectivejava.ch02.item9.exam02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {

  // try-finally 문법을 사용하여 자원을 해제하는 예:
  // - 자원을 두 개 이상 다룰 때 코드가 너무 지저분해진다.
  static void copy(String src, String dst) throws IOException {
    InputStream in = new FileInputStream(src);
    try {
      OutputStream out = new FileOutputStream(dst);
      try {
        byte[] buf = new byte[1024];
        int n;
        while ((n = in.read(buf)) >= 0) out.write(buf, 0, n);
      } finally {
        out.close();
      }
    } finally {
      in.close();
    }
  }

  // try-with-resources 문법을 사용하여 자원을 해제하는 예:
  // - 자원을 두 개 이상 다룰 때도 코드가 깔끔하다
  static void copy2(String src, String dst) throws IOException {
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
      byte[] buf = new byte[1024];
      int n;
      while ((n = in.read(buf)) >= 0) out.write(buf, 0, n);
    }
  }

  public static void main(String[] args) throws Exception {
    // try-finally 코드
    copy("test.txt", "test2.txt");

    // try-with-resources 코드
    copy2("test.txt", "test3.txt");
  }
}
