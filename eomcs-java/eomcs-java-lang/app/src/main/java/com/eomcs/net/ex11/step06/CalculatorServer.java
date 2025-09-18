// 계산기 서버 만들기 - 6단계: 클라이언트가 보내온 계산식을 실행하여 결과를 리턴한다.
package com.eomcs.net.ex11.step06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      try (Socket socket = serverSocket.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintStream out = new PrintStream(socket.getOutputStream());) {

        sendIntroMessage(out);

        while (true) {
          String request = in.readLine();
          String message = compute(request);
          sendResponse(out, message); // 클라리언트에게 응답한다.
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static String compute(String request) {
    String[] values = request.split(" ");

    int a = Integer.parseInt(values[0]);
    String op = values[1];
    int b = Integer.parseInt(values[2]);
    int result = 0;

    switch (op) {
      case "+": result = a + b; break;
      case "-": result = a - b; break;
      case "*": result = a * b; break;
      case "/": result = a / b; break;
      default:
        return String.format("%s 연산자를 지원하지 않습니다.", op);
    }
    return String.format("결과는 %d %s %d = %d 입니다.", a, op, b, result);
  }

  static void sendResponse(PrintStream out, String message) {
    out.println(message);
    out.println();
    out.flush();
  }


  static void sendIntroMessage(PrintStream out) throws Exception {
    out.println("[비트캠프 계산기]");
    out.println("계산기 서버에 오신 걸 환영합니다!");
    out.println("계산식을 입력하세요!");
    out.println("예) 23 + 7");
    out.println(); // 응답의 끝을 표시하는 빈 줄을 보낸다.
    out.flush();
  }
}
