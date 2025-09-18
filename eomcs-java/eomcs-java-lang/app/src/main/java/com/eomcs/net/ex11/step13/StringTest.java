package com.eomcs.net.ex11.step13;

public class StringTest {

  public static void main(String[] args) {
    String requestLine = "GET /plus?a=100&b=200 HTTP/1.1";
    String[] values = requestLine.split(" ")[1].split("\\?")[1].split("&");

    for (String value : values) {
      System.out.println(value);
    }
  }

}
