package com.eomcs.design_pattern.command.after;

public class HelloCommand implements Command {

  @Override
  public void execute() {
    System.out.println("안녕하세요!");
    
  }

}
