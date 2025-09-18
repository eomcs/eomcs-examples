// 예외 처리 후 마무리 작업 - finally 블록
package com.eomcs.exception.ex3;

import java.io.IOException;
import java.sql.SQLException;

public class Exam0510 {

  static void m(int i) throws Exception, RuntimeException, SQLException, IOException {
    if (i == 0)
      throw new Exception();
    else if (i == 1)
      throw new RuntimeException();
    else if (i == 2)
      throw new SQLException();
    else if (i == 3)
      throw new IOException();
  }

  public static void main(String[] args) {
    try {
      System.out.println("try");
      m(1);
      return;
    } catch (RuntimeException | SQLException | IOException e) {
      System.out.println("catch 1");

    } catch (Exception e) {
      System.out.println("catch 2");

    } finally {
      // 정상적으로 실행하든, 아니면 예외가 발생하여 catch 블록을 실행하든
      // finally 블록은 무조건 실행한다.
      // 즉 try ~ catch ~ 블록을 나가기 전에 반드시 실행한다.
      // 그래서 이 블록에는
      // try 에서 사용한 자원을 해제시키는 코드를 주로 둔다.
      // => 자원 해제 코드를 둔다.
      // => 자원? 파일, DB 커넥션, 소켓 커넥션, 대량의 메모리 등
      //
      System.out.println("finally");
    }

    System.out.println("안녕히 가세요!");
  }

}
