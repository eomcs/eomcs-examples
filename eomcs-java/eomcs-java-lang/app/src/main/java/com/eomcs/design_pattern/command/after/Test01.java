package com.eomcs.design_pattern.command.after;

import java.util.HashMap;
import java.util.Scanner;

public class Test01 {

  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    
    // Command 패턴
    // => 명령어를 처리하는 각 메서드를 클래스로 정의한 후 사용한다. 
    // => 일관된 사용을 위해 인터페이스로 호출 규칙을 정의한다.
    // => 나중에 명령어가 추가되면 그 명령어를 처리할 클래스를 추가하면 된다.
    // 
    HashMap<String,Command> commandMap = new HashMap<>(); 
    commandMap.put("/board/add", new BoardAddCommand());
    commandMap.put("/board/detail", new BoardDetailCommand());
    commandMap.put("/board/list", new BoardListCommand());
    commandMap.put("/board/update", new BoardUpdateCommand());
    commandMap.put("/board/delete", new BoardDeleteCommand());
    
    commandMap.put("/member/add", new MemberAddCommand());
    commandMap.put("/member/detail", new MemberDetailCommand());
    commandMap.put("/member/list", new MemberListCommand());
    commandMap.put("/member/update", new MemberUpdateCommand());
    commandMap.put("/member/delete", new MemberDeleteCommand());
    
    commandMap.put("hello", new HelloCommand());
    
    while (true) {
      System.out.print("명령> ");
      String input = keyboard.nextLine();
      
      // 사용자가 입력한 명령어를 가지고 그 명령을 처리할 객체를 찾는다.
      Command command = commandMap.get(input);
      
      if (command != null) {
        // 명령어 사용 규칙에 따라 메서드를 호출한다.
        command.execute();
        
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







