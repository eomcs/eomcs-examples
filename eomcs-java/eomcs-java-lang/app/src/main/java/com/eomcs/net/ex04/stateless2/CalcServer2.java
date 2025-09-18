// stateless 방식에서 클라이언트를 구분하고 작업 결과를 유지하는 방법
package com.eomcs.net.ex04.stateless2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CalcServer2 {

  // 각 클라이언트의 작업 결과를 보관할 맵 객체
  // => Map<clientID, result>
  static Map<Long, Integer> resultMap = new HashMap<>();

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");

    ServerSocket ss = new ServerSocket(8888);

    while (true) {
      Socket socket = ss.accept();
      System.out.println("클라이언트 요청 처리!");
      try {
        processRequest(socket);
      } catch (Exception e) {
        System.out.println("클라이언트 요청 처리 중 오류 발생!");
        System.out.println("다음 클라이언트의 요청을 처리합니다.");
      }
    }
    // ss.close();
  }

  static void processRequest(Socket socket) throws Exception {
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

      // 클라이언트를 위한 기존 값 꺼내기
      Integer obj = resultMap.get(clientId);
      int result = 0;

      if (obj != null) {
        System.out.printf("%d 기존 고객 요청 처리!\n", clientId);
        result = obj; // auto-unboxing
      } else {
        // 맵에 해당 클라이언트 ID로 저장된 값이 없다는 것은
        // 한 번도 서버에 접속한 적이 없다는 의미다. 
        // 따라서 새 클라이언트 아이디를 발급한다.
        // => 예제를 간단히 하기 위해 현재 실행 시점의 밀리초를 사용한다.
        clientId = System.currentTimeMillis();
        System.out.printf("%d 신규 고객 요청 처리!\n", clientId);
      }

      String message = null;
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
        default:
          message = "해당 연산을 지원하지 않습니다.";
      }

      // 계산 결과를 resultMap에 보관한다.
      resultMap.put(clientId, result);

      // 클라이언트로 응답할 때 항상 클라이언트 아이디와 결과를 출력한다.
      // => 클라이언트 아이디 출력
      out.writeLong(clientId);

      // => 계산 결과 출력
      if (message == null) {
        message = String.format("계산 결과: %d", result);
      }
      out.writeUTF(message);
      out.flush();

    }
  }
}


