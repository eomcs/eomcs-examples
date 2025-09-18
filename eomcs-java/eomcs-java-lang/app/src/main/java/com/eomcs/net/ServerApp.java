package com.eomcs.net;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);

    System.out.println("서버 실행 중...");

    ServerSocket serverSocket = new ServerSocket(8888);

    Socket socket = serverSocket.accept();
    System.out.println("클라이언트와 연결됨!");

    Scanner in = new Scanner(socket.getInputStream());
    PrintStream out = new PrintStream(socket.getOutputStream());

    while (true) {
      // 클라이언트가 보낸 문자열을 한 줄 읽을 때까지 리턴하지 않는다.
      String message = in.nextLine();
      System.out.println(message);
      if (message.equals("quit")) {
        break;
      }

      System.out.print("입력> ");
      String str = keyScan.nextLine();
      out.println(str);
    }


    socket.close();
    serverSocket.close();

    System.out.println("서버 종료!");
    keyScan.close();
  }

}
