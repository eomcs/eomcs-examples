// 예외 처리와 자원 해제 보장 - finally 블록
package com.eomcs.exception.ex91;

import java.util.Scanner;

public class Exam0510 {

  public static void main(String[] args) {

    Scanner keyboardScanner = null;

    // 키보드나 랜카드 같은 공용 자원은 
    // 사용 후 다른 쪽에서 쓸 수 있도록 해제해줘야 한다.
    // 한 사람이 사용하는 데스크톱 애플리케이션의 경우에는 자원 해제가 중요하지 않지만,
    // 여러 사용자가 동시에 사용하는 서버 애플리케이션의 경우에는 자원 해제가 중요하다.
    // 
    // 다음 예제는 365일 24시간 실행되는 서버 애플리케이션이라고 가정했을 때 
    // 자원 해제를 시키는 코드이다.
    try {
      keyboardScanner = new Scanner(System.in);

      System.out.print("이름? ");
      String name = keyboardScanner.nextLine();

      System.out.print("국어? ");
      int kor = keyboardScanner.nextInt();

      System.out.print("영어? ");
      int eng = keyboardScanner.nextInt();

      System.out.print("수학? ");
      int math = keyboardScanner.nextInt();

      int sum = kor + eng + math;
      float aver = sum / 3f;

      System.out.printf("%s : 합계 = %d, 평균 = %f\n",
          name, sum, aver);

    } catch (Throwable e) {
      System.out.println("입력 값이 옳지 않습니다.");

    } finally {
      // try {} 블록의 코드 실행을 성공적으로 완료한 후,
      // 또는
      // 예외가 발생하여 catch {} 블록을 실행한 후,
      // try ~ catch ~ 문을 나가기 전에
      // finally {} 블록은 반드시 실행된다.
      //
      // 그래서 finally {} 블록에는 보통
      // 사용 완료한 자원을 해제시키는 코드를 둔다.
      //
      if (keyboardScanner != null) {
        keyboardScanner.close();
      }
      System.out.println("키보드 입력 자원을 해제하였습니다.");
    }

  }
}

