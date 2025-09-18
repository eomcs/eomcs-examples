// 통신 방식 - Stateless
package com.eomcs.net.ex04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server0210 {
  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {

      System.out.println("서버 실행!");


      loop: while (true) {
        // 클라이언트와 연결된 후 요청을 받아서 응답하면 바로 연결을 끊는다.
        try (Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream())) {

          System.out.println("클라이언트가 연결되었음!");

          String name = in.readLine();

          if (name.equals("stop")) {
            out.println("Goodbye!");
            out.flush();
            break loop;
          } else {
            out.printf("%s 님 반갑습니다!\n", name);
            out.flush();
          }
        } catch (Exception e) {
          System.out.println("클라이언트와 통신 중 오류 발생!");
        }
        System.out.println("클라이언트와의 연결을 끊었음.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

}

