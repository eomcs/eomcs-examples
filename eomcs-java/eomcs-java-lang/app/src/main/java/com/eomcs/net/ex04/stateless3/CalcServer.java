// stateless 에서 다중 클라이언트 요청 처리하기
package com.eomcs.net.ex04.stateless3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CalcServer {

  // 각 클라이언트의 작업 결과를 보관할 맵 객체
  // => Map<clientID, result>
  static Map<Long, Integer> resultMap = new HashMap<>();


  // 클라이언트와 대화하는 부분을 별도의 코드로 분리하여 실행한다.
  static class RequestHandler extends Thread {

    Socket socket;

    public RequestHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      // main 스레드와는 별개로 실행해야 하는 코드가 있다면
      // 이 메서드에 둔다.
      try (Socket socket2 = socket;
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

        // 클라이언트를 구분하기 위한 아이디
        // => 0: 아직 클라이언트 아이디가 없다는 의미
        // => x: 서버가 클라이언트에게 아이디를 부여했다는 의미
        long clientId = in.readLong();

        // 연산자와 값을 입력 받는다.
        String op = in.readUTF();
        int value = in.readInt();

        // 클라이언트는 위한 기존 값 꺼내기
        Integer obj = resultMap.get(clientId);
        int result = 0;

        if (obj != null) {
          System.out.printf("%d 기존 고객 요청 처리!\n", clientId);
          result = obj; // auto-unboxing
        } else {
          // 해당 클라이언트가 방문한적이 없다면, 새 클라이언트 아이디를 발급한다.
          clientId = System.currentTimeMillis();
          System.out.printf("%d 신규 고객 요청 처리!\n", clientId);
        }

        switch (op) {
          case "+":
            result += value;
            break;
          case "-":
            result -= value;
            break;
          case "*":
            result *= value;
            break;
          case "/":
            Thread.sleep(30000);
            result /= value;
            break;
        }
        // 클라이언트로 응답할 때 항상 클라이언트 아이디와 결과를 출력한다.
        // => 클라이언트 아이디 출력
        out.writeLong(clientId);

        // => 계산 결과 출력
        out.writeInt(result);

        out.flush();

        // 계산 결과를 다시 resultMap에 보관한다.
        resultMap.put(clientId, result);

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

      // 독립적으로 수행할 코드를 갖고 있는 스레드 객체를 생성한다.
      RequestHandler requestHandler = new RequestHandler(socket);

      // 그리고 작업을 실행시킨다.
      // => 스레드를 실행시킨 후 바로 리턴한다.
      requestHandler.start();
      System.out.printf("%s 클라이언트 요청을 스레드에게 맡겼습니다!\n", //
          remoteAddr.getAddress());

    }
    // ss.close();
  }
}


