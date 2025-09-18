package com.eomcs.lang.ex99;

public class Test1 {

  public static void main(String[] args) throws Exception {
    java.io.InputStream in = System.in;
    java.util.Scanner keyIn = new java.util.Scanner(in);
    String str = keyIn.nextLine();
    int b = in.read();
    System.out.println("===> " + str);
    keyIn.close();
  }

}
