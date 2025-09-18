// 계산기 서버 만들기 - 9단계: 리팩토링
// - 클라이언트의 요청을 처리하는 메서드를 별도의 클래스로 분리하기
package com.eomcs.net.ex11.step09;

import java.net.ServerSocket;

public class CalculatorServer {
  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      //      Socket socket = serverSocket.accept();
      //      RequestProcessor requestProcessor = new RequestProcessor(socket);
      //      requestProcessor.service();

      new RequestProcessor(serverSocket.accept()).service();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
