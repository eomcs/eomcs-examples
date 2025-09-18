package com.eomcs.net.ex11.step13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

// 역할:
// - 소켓에 연결된 클라이언트 요청을 처리한다.
// - 해당 코드를 main 실행과 분리하여 실행한다.
// - HTTP 요청에 대해 HTTP 응답을 수행한다.
//
public class RequestProcessor extends Thread {
  Socket socket;

  public RequestProcessor(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try (Socket socket = this.socket;
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());) {

      // 웹브라우저가 보낸 첫 줄에는 데이터가 포함되어 있기 때문에 따로 추출한다.
      String requestLine = in.readLine();

      // 나머지 요청과 관련된 헤더 정보(부가 정보)는 현재는 사용할 일이 없기 때문에 버린다.
      while (true) {
        if (in.readLine().length() == 0) {
          break;
        }
      }

      sendHttpResponse(out, compute(requestLine)); 

    } catch (Exception e) {
      System.out.printf("클라이언트 요청 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private String compute(String request) {
    try {
      // 웹브라우저가 보낸 request line에서 데이터를 추출한다.
      //    예) "GET /plus?a=100&b=200 HTTP/1.1"
      String[] values = request.split(" ")[1].split("\\?"); // ["/plus", "a=100&b=200"]

      String op = getOperator(values[0]); // "/plus", "/multiple" 등

      String[] parameters = values[1].split("&"); // "a=100&b=200" ==> ["a=100", "b=200"]
      int a = 0;
      int b = 0;
      for (String parameter : parameters) {
        String[] kv = parameter.split("=");
        if (kv[0].equals("a")) { // "a=100"
          a = Integer.parseInt(kv[1]);
        } else if (kv[0].equals("b")) { // "b=200"
          b = Integer.parseInt(kv[1]);
        }
      }

      int result = 0;

      switch (op) {
        case "+": result = a + b; break;
        case "-": result = a - b; break;
        case "*": result = a * b; break;
        case "/": result = a / b; break;
        default:
          return "해당 연산자를 지원하지 않습니다.";
      }
      return String.format("결과는 %d %s %d = %d 입니다.", a, op, b, result);

    } catch (Exception e) {
      return String.format("계산 중 오류 발생! - %s", e.getMessage());
    }
  }

  private String getOperator(String name) {
    switch (name) {
      case "/plus": return "+";
      case "/minus": return "-";
      case "/multiple": return "*";
      case "/devide": return "/";
      default:
        return "?";
    }
  }

  private void sendHttpResponse(PrintStream out, String message) throws Exception {

    out.println("HTTP/1.1 200 OK");
    out.println("Content-Type: text/plain;charset=UTF-8");
    out.println();
    out.print(message);
    out.flush();
  }
}
