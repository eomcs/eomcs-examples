// 계산기 서버 만들기 - 2단계: 응답의 종료 조건을 설정하기
// - 응답의 종료 조건을 설정하면 언제까지 읽어야 할 지 결정하기 쉽다.
// - 응답을 완료했다면 빈 줄을 보내 응답을 완료했음을 표시한다.
package com.eomcs.net.ex11.step02;

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

        out.println("[계산기 서비스]");
        out.println("계산기 서버에 오신 걸 환영합니다!");
        out.println("계산식을 입력하세요!");
        out.println("예) 23 + 7");
        out.println(); // 응답의 끝을 표시하는 빈 줄을 보낸다.
        out.flush();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
