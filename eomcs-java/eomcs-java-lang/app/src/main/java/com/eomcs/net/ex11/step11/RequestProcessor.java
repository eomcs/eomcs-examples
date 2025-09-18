package com.eomcs.net.ex11.step11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

// 역할:
// - 소켓에 연결된 클라이언트 요청을 처리한다.
public class RequestProcessor {
  Socket socket;

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public void service() throws Exception {
    try (Socket socket = this.socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());) {

      // 클라이언트 접속에 대해 더이상 안내 메시지를 제공하지 않는다.

      // 한 번 접속에 한 번의 요청만 처리한다.
      sendResponse(out, compute(in.readLine())); 
    }
  }

  private String compute(String request) {
    try {
      String[] values = request.split(" ");

      int a = Integer.parseInt(values[0]);
      String op = values[1];
      int b = Integer.parseInt(values[2]);
      int result = 0;

      switch (op) {
        case "+": result = a + b; break;
        case "-": result = a - b; break;
        case "*": result = a * b; break;
        case "/": 
          result = a / b; 
          // Stateless 방식의 통신이라 하더라도 결국 순차 처리 방식이기 때문에 
          // 현재 클라이언트 작업 처리가 늦어지면 
          // 다음 클라이언트는 무조건 기다려야 한다.
          // 이것을 보여주기 위해 다음과 같이 일부러 / 연산에 대해 
          // 시간을 지연시킬 것이다.
          Thread.sleep(10000);
          break;
        default:
          return String.format("%s 연산자를 지원하지 않습니다.", op);
      }
      return String.format("결과는 %d %s %d = %d 입니다.", a, op, b, result);

    } catch (Exception e) {
      return String.format("계산 중 오류 발생! - %s", e.getMessage());
    }
  }

  private void sendResponse(PrintStream out, String message) {
    out.println(message);
    out.println();
    out.flush();
  }
}
