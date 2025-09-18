package com.eomcs.net;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);

    System.out.println("클라이언트 실행 중...");

    Socket socket = new Socket("192.168.0.231", 8888);
    System.out.println("서버에 연결되었음!");

    PrintStream out = new PrintStream(socket.getOutputStream());
    Scanner in = new Scanner(socket.getInputStream());

    while (true) {
      System.out.print("입력> ");
      String message = keyScan.nextLine();
      out.println(message);

      if (message.equals("quit")) {
        break;
      }
      //      System.out.println("서버에 메시지를 보냈음!");

      // 서버에서 응답이 올 때까지 리턴하지 않는다.
      String response = in.nextLine();
      System.out.printf("응답: %s\n", response);
    }

    out.close();
    in.close();
    socket.close();

    System.out.println("클라이언트 종료!");
    keyScan.close();
  }
}





