// stateful 방식 - 다중 클라이언트의 요청 처리 시 문제점과 해결책
package com.eomcs.net.ex04.stateful3;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServer {

  // 클라이언트와 통신하는 부분을 별도의 스레드(실행흐름)로 분리하여
  // 독립적으로 실행하게 한다.
  static class RequestHandler extends Thread {

    Socket socket;

    public RequestHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      // main() 메서드 호출과 분리하여 별도로 실행할 코드가 있다면
      // 이 메서드에 둔다.
      try (Socket socket2 = socket;
          DataInputStream in = new DataInputStream(socket.getInputStream());
          PrintStream out = new PrintStream(socket.getOutputStream());) {

        // 작업 결과를 유지할 변수
        int result = 0;

        loop: while (true) {
          String op = in.readUTF();
          int a = in.readInt();

          switch (op) {
            case "+":
              result += a;
              break;
            case "-":
              result -= a;
              break;
            case "*":
              result *= a;
              break;
            case "/":
              result /= a;
              break;
            case "quit":
              break loop;
            default:
              out.println("해당 연산을 지원하지 않습니다.");
              continue;
          }

          out.printf("계산 결과: %d\n", result);
        }
        out.println("quit");

      } catch (Exception e) {
        System.out.println("클라이언트 요청 처리 중 오류 발생!");

      } finally {
        System.out.println("클라이언트 연결 종료!");
      }
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");

    ServerSocket ss = new ServerSocket(8888);

    while (true) {
      System.out.println("클라이언트의 연결을 기다림!");
      Socket socket = ss.accept();
      InetSocketAddress remoteAddr = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("클라이언트(%s:%d)가 연결되었음!\n", //
          remoteAddr.getAddress(), remoteAddr.getPort());

      // 연결된 클라이언트가 연결을 끊기 전까지는
      // 대기하고 있는 다른 클라이언트의 요청을 처리할 수 없다.
      // 이것이 스레드를 사용하기 전의 문제점이다.
      // 해결책?
      // 클라이언트와 대화하는 부분을 스레드로 분리하여 실행하라!
      RequestHandler requestHandler = new RequestHandler(socket);
      requestHandler.start();
      // 스레드를 실행하려면 start() 를 호출하라.
      // start() 내부에서 run()을 호출할 것이다.
      // start() 호출한 후에 즉시 리턴한다.
      System.out.printf("%s 클라이언트와의 대화를 별도의 스레드에서 담당합니다!\n", //
          remoteAddr.getAddress());
    }
    // ss.close();
  }

}


