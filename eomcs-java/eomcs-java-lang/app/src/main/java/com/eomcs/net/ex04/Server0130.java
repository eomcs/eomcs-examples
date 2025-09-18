// 통신 방식 - Stateful + 여러 클라이언트 요청 처리 + 서버 종료
package com.eomcs.net.ex04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0130 {
  public static void main(String[] args) {
    try (Scanner keyboard = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(8888)) {

      System.out.println("서버 실행!");

      loop: while (true) {
        // 한 클라이언트와 대화가 끝다면 다음 클라이언트와 대화를 한다.
        try (Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream())) {

          System.out.println("클라이언트가 연결되었음!");

          while (true) {
            String name = in.readLine();
            if (name.equalsIgnoreCase("quit")) { // 클라이언트와 연결 끊기
              out.println("Goodbye!");
              out.flush();
              break;
            } else if (name.equalsIgnoreCase("stop")) { // 서버 종료하기
              out.println("Goodbye!");
              out.flush();
              break loop;
            }

            out.printf("%s 님 반갑습니다!\n", name);
            out.flush();
          }
        } catch (Exception e) {
          System.out.println("클라이언트와 통신 도중 오류 발생!");
        }
        System.out.println("클라이언트와의 연결을 끊었음.");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

}

