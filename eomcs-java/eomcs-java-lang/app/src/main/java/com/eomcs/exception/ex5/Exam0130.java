// 예외 처리 - 2) 예외에 대해 의미를 부여하기 - 사용자 정의 예외 만들고 사용하기
package com.eomcs.exception.ex5;

import java.sql.Date;
import java.util.Scanner;

public class Exam0130 {
  // 실무에서는 개발자에게 예외의 의미를 직관적으로 전달하기 위해
  // RuntimeException 같은 평범한, 의미가 모호한 이름의 클래스를 사용하지 않고
  // 대신에 기존 예외를 상속 받아 의미있는 이름으로 서브 클래스를 정의한 다음에
  // 그 예외 클래스를 던지도록 프로그래밍 한다.
  //
  static Board read() throws BoardException {
    try (Scanner keyScan = new Scanner(System.in)) {
      Board board = new Board();

      System.out.print("번호> ");
      board.setNo(Integer.parseInt(keyScan.nextLine()));

      System.out.print("제목> ");
      board.setTitle(keyScan.nextLine());

      System.out.print("내용> ");
      board.setContent(keyScan.nextLine());

      System.out.print("등록일> ");
      board.setCreatedDate(Date.valueOf(keyScan.nextLine()));

      return board;
    } catch (Exception 원본오류) {
      // 예외가 발생되면 원본 예외를 그대로 던지지 말고,
      // 예외의 의미를 직관적으로 파악할 수 있도록 별도의 예외 객체를 만들어 던진다.
      // 즉 게시물 관리 작업을 하다가 오류가 발생했음을 직관적으로 알게 한다.
      // 어떤 방법?
      // => 게시물 예외를 직관적으로 알 수 있는 클래스를 만든다.
      // => 그 클래스가 BoardException 이다.
      //
      throw new BoardException("게시물 입력 도중 오류 발생!", 원본오류);
    }
  }

  public static void main(String[] args) {
    try {
      Board board = read();
      // read() 메서드의 선언부를 보면 BoardException 던진다고 되어 있다.
      //

      System.out.println("---------------------");
      System.out.printf("번호: %d\n", board.getNo());
      System.out.printf("제목: %s\n", board.getTitle());
      System.out.printf("내용: %s\n", board.getContent());
      System.out.printf("등록일: %s\n", board.getCreatedDate());
      java.util.LinkedList l;
    } catch (Exception ex) {
      ex.printStackTrace();
      // 예외 내용이 출력된 것을 보면,
      // BoardException 클래스 이름이 나온다.
      // 자세한 내용을 파악하기 전에
      // 대략적으로 어떤 작업을 하다가 오류가 발생했는지 빠르게 파악할 수 있어 좋다.
    }
  }
}


