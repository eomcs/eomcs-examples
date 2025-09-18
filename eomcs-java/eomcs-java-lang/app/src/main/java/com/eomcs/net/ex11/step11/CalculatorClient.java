// 계산기 클라이언트 만들기 - 11단계: Stateful 방식을 Stateless 방식으로 전환
// - 요청할 때 연결해서 요청이 끝나면 즉시 연결을 끊는다.
// - Stateless 방식은 요청 때마다 연결하기 때문에 요청을 처리하는 데 연결 시간이 추가되는 문제가 있다.
// - 즉 한 번에 한 클라이언트의 요청을 처리하기 때문에 
//   특정한 클라이언트에 서버가 묶이는 현상이 덜하다.
// - 서버 입장에서는 클라이언트의 대기 시간을 줄일 수 있다.
// - 클라이언트 입장에서도 다른 클라이언트가 서버를 독점하는 것을 피할 수 있다.
//  
package com.eomcs.net.ex11.step11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorClient {
  public static void main(String[] args) {
    Scanner keyboardScanner = new Scanner(System.in);

    while (true) {

      // 요청 때 마다 연결하기 때문에 서버의 인사말은 더이상 출력하지 않는다.
      String input = prompt(keyboardScanner);
      if (input == null) {
        continue;
      } else if (input.equalsIgnoreCase("quit")) {
        // quit 명령을 입력할 경우 서버에 접속할 필요가 없이 즉시 클라이언트를 종료한다.
        break;
      }

      try (Socket socket = new Socket("localhost", 8888);
          PrintStream out = new PrintStream(socket.getOutputStream());
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        sendRequest(out, input); // 서버에 요청을 보내기
        receiveResponse(in); // 서버의 실행 결과를 받기

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    keyboardScanner.close();
  }

  static String prompt(Scanner keyboardScanner) {
    System.out.print("계산식> ");
    String input = keyboardScanner.nextLine();

    if (input.equalsIgnoreCase("quit")) {
      return input;

    } else if (input.split(" ").length != 3) { // 사용자가 입력한 값을 검증
      System.out.println("입력 형식이 올바르지 않습니다. 예) 23 + 5");
      return null;
    }

    return input;
  }

  static void sendRequest(PrintStream out, String message) throws Exception {
    out.println(message);
    out.flush();
  }

  static void receiveResponse(BufferedReader in) throws Exception {
    while (true) {
      String input = in.readLine();
      if (input.length() == 0) {
        // 빈 줄을 읽었다면 읽기를 끝낸다.
        break;
      }
      System.out.println(input);
    }
  }
}


