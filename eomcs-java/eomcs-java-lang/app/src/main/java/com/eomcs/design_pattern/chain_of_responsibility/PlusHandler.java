package com.eomcs.design_pattern.chain_of_responsibility;

public class PlusHandler extends AbstractHandler {
  @Override
  public void handle(int a, int b, String op) {
    if (op.equals("+")) {
      System.out.printf("%d + %d = %d\n", a, b, (a + b));
      return;
    } 
      
    next.handle(a, b, op);
  }
}
