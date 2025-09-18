// 예외의 종류 - 애플리케이션 예외
package com.eomcs.exception.ex91;

public class Exam0310 {

  public static void main(String[] args) {

    try {
      int result = 100 / 0;
      System.out.printf("계산 결과 = %d\n", result);

    } catch (Throwable e) {
      // 예외에 대한 상세 정보 출력
      e.printStackTrace();
    }

  }
}

// 애플리케이션 예외
// - 애플리케이션 명령 처리 중 발생하는 예외이다.
// - java.lang.Exception 타입의 예외이다.
// - catch 블록에서 적절한 조치를 취한 후
//   프로그램을 계속 실행하게 할 수 있다.
//
// [java.lang.Exception]
// - 애플리케이션 예외가 발생할 때 전달되는 예외 객체이다.
// - 애플리케이션 예외 정보를 담고 있다.
// - 클래스 계층도
//   java.lang.Object
//      ㄴ java.lang.Throwable
//           ㄴ java.lang.Exception
// - 애플리케이션 예외 예:
//   ArrayIndexOutOfBoundsException, ClassCastException,
//   ClassNotFoundException, CloneNotSupportedException,
//   NullPointerException, IOException, SQLException,
//   FileNotFoundException, SocketException, MalformedURLException,
//   RuntimeException, ArithmeticException 등
//
//
// 예외 클래스 계층도
// [java.lang.Throwable]
//     ㄴ java.lang.Error : 시스템 예외
//     ㄴ java.lang.Exception : 애플리케이션 예외
//
















