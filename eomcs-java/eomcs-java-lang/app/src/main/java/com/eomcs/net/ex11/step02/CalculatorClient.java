// 계산기 클라이언트 만들기 - 2단계: 응답의 종료 조건을 설정하기
// - 응답의 종료 조건을 설정하면 언제까지 읽어야 할 지 결정하기 쉽다.
// - 빈 줄을 받을 때까지 응답을 읽어 출력한다.
package com.eomcs.net.ex11.step02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class CalculatorClient {
  public static void main(String[] args) {

    try (Socket socket = new Socket("localhost", 8888);
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      while (true) {
        String input = in.readLine();
        if (input.length() == 0) {
          // 빈 줄을 읽었다면 읽기를 끝낸다.
          break;
        }
        System.out.println(input);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}


