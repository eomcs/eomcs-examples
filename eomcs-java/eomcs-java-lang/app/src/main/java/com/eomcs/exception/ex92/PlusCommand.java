package com.eomcs.exception.ex92;

import java.util.Scanner;

public class PlusCommand implements Command {

  Scanner keyboard;
  
  public PlusCommand(Scanner keyboard) {
    this.keyboard = keyboard;
  }
  
  @Override
  public void execute() {
    System.out.print("값1? ");
    int a = Integer.parseInt(keyboard.nextLine());
    
    System.out.print("값2? ");
    int b = Integer.parseInt(keyboard.nextLine());
    
    System.out.printf("%d + %d = %d\n", a, b, (a + b));
  }

}




