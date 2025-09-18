// 계산기 서버 만들기 - 11단계: Stateful 방식을 Stateless 방식으로 전환
// - 클라이언트 한 번 접속에 한 번만 요청만 처리한다.
// 
package com.eomcs.net.ex11.step11;

import java.net.ServerSocket;

public class CalculatorServer {
  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      RequestProcessor requestProcessor = new RequestProcessor();

      while (true) {
        requestProcessor.setSocket(serverSocket.accept());
        requestProcessor.service();
        // Stateless 방식도 완벽한 것은 아니다.
        // 클라이언트의 특정한 요청을 처리하는 동안에는 
        // 다른 클라이언트의 요청을 처리하지 못하는 것은 마찬가지이다.
        // 예)
        // - 더하기를 실행할 때 5초 정도 실행을 지연시켜보자.
        // - 그리고 클라이언트의 실행을 테스트 해보라.
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
