// 계산기 서버 만들기 - 10단계: 여러 개의 클라이언트 접속 처리
// - 반복문을 이용하여 계속해서 클라이언트의 접속을 처리한다.
package com.eomcs.net.ex11.step10;

import java.net.ServerSocket;

public class CalculatorServer {
  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      RequestProcessor requestProcessor = new RequestProcessor();

      while (true) {
        requestProcessor.setSocket(serverSocket.accept());
        requestProcessor.service();
        // 현재 방식의 문제점?
        // - 현재 작업 중인 클라이언트와의 연결이 끝날 때까지 
        //   다른 클라이언트는 대기열에서 기다려야 한다.
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
