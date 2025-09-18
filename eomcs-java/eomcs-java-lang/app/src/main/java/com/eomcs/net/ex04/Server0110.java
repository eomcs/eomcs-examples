// 통신 방식 - Stateful
package com.eomcs.net.ex04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0110 {
  public static void main(String[] args) {
    try (Scanner keyboard = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(8888)) {

      System.out.println("서버 실행!");

      try (Socket socket = serverSocket.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(socket.getOutputStream())) {

        System.out.println("클라이언트가 연결되었음!");

        // stateful 방식은 클라이언트와 데이터를 주고 받는 동안 
        // 그 클라이언트의 작업 상태를 유지할 수 있다.
        // 예를 들어 다음과 같이 몇 번 요청했는지 요청 횟수를 유지할 수 있다.
        int count = 0;

        while (true) {
          String name = in.readLine();
          if (name.equalsIgnoreCase("quit")) {
            out.println("Goodbye!");
            out.flush();
            break;
          }
          count++;
          out.printf("%s 님 반갑습니다!(%d)\n", name, count);
          out.flush();
        }
      }
      System.out.println("클라이언트와의 연결을 끊었음.");

    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

}

