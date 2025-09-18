package com.eomcs.net.ex11.step12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

// 역할:
// - 소켓에 연결된 클라이언트 요청을 처리한다.
// - 해당 코드를 main 실행과 분리하여 실행한다.
// 
public class RequestProcessor extends Thread {
  Socket socket;

  public RequestProcessor(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    // 이 메서드는 한 번 호출되면 재호출될 수 없다.
    // 따라서 한 스레드당 한 번만 호출될 수 있다.
    //
    // => main 실행과 분리하여 독립적으로 실행할 코드가 있다면 이 메서드 안에 둔다.
    //
    try (Socket socket = this.socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());) {

      // 클라이언트 접속에 대해 더이상 안내 메시지를 제공하지 않는다.

      // 한 번 접속에 한 번의 요청만 처리한다.
      sendResponse(out, compute(in.readLine())); 
    } catch (Exception e) {
      System.out.printf("클라이언트 요청 처리 중 오류 발생! - %s\n", e.getMessage());
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
          Thread.sleep(20000); // 일부러 응답을 지연시킨다. 
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
