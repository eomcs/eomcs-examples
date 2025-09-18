// 계산기 클라이언트 만들기 - 4단계: 사용자로부터 계산식을 입력 받아서 서버에 전달한다.
package com.eomcs.net.ex11.step04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorClient {
  public static void main(String[] args) {

    try (
        Scanner keyboardScanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8888);
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      readResponse(in); // 서버의 인사말을 읽기

      while (true) {
        String input = keyboardScanner.nextLine();
        out.println(input);
        out.flush();
        readResponse(in); // 서버의 실행 결과를 출력
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  static void readResponse(BufferedReader in) throws Exception {
    while (true) {
      String input = in.readLine();
      if (input.length() == 0) {
        // 빈 줄을 읽었다면 읽기를 끝낸다.
        break;
      }
      System.out.println(input);
    }
  }
}


