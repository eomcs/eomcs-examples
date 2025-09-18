package com.eomcs.design_pattern.command.before;

import java.util.Scanner;

public class Test01 {

  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    CommandHandler handler = new CommandHandler();
    
    while (true) {
      System.out.print("명령> ");
      String input = keyboard.nextLine();
      
      
      if ("/board/add".equals(input)) {
        handler.addBoard();
      } else if ("/board/detail".equals(input)) {
        handler.detailBoard();
      } else if ("/board/list".equals(input)) {
        handler.listBoard();
      } else if ("/board/update".equals(input)) {
        handler.updateBoard();
      } else if ("/board/delete".equals(input)) {
        handler.deleteBoard();
      } else if ("/member/add".equals(input)) {
        handler.addMember();
      } else if ("/member/detail".equals(input)) {
        handler.detailMember();
      } else if ("/member/list".equals(input)) {
        handler.listMember();
      } else if ("/member/update".equals(input)) {
        handler.updateMember();
      } else if ("/member/delete".equals(input)) {
        handler.deleteMember();
      } else if ("quit".equals(input)) {
        break;
      } else {
        System.out.println("처리할 수 없는 명령입니다.");
      }
      System.out.println();
    }
    keyboard.close();
  }

}







