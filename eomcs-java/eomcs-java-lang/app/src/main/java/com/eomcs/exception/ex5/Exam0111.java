// 예외 처리 전 - 2) finally {} 를 이용하여 자원 해제를 자동화 하기
package com.eomcs.exception.ex5;

import java.sql.Date;
import java.util.Scanner;

public class Exam0111 {

  static Board read() {

    Scanner keyScan = null;

    try {
      keyScan = new Scanner(System.in);

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

    } finally { 
      // 정상적으로 실행하든 예외가 발생하든지 간에 무조건 close()를 실행한다.
      keyScan.close();
      System.out.println("Scanner 자원을 해제시켰다.");
    }
  }

  public static void main(String[] args) {
    Board board = read();
    System.out.println("---------------------");
    System.out.printf("번호: %d\n", board.getNo());
    System.out.printf("제목: %s\n", board.getTitle());
    System.out.printf("내용: %s\n", board.getContent());
    System.out.printf("등록일: %s\n", board.getCreatedDate());
  }
}


