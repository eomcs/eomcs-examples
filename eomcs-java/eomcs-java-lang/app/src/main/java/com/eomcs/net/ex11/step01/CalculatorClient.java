// 계산기 클라이언트 만들기 - 1단계: 단순히 서버에 요청하고 응답을 받아 출력한다.
package com.eomcs.net.ex11.step01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class CalculatorClient {
  public static void main(String[] args) {

    try (Socket socket = new Socket("localhost", 8888);
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      String input = in.readLine();
      System.out.println(input);

      input = in.readLine();
      System.out.println(input);

      input = in.readLine();
      System.out.println(input);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}


