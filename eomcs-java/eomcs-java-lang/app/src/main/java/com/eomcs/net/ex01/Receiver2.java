// 서버 + 키보드 입력
package com.eomcs.net.ex01;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver2 {

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행!");

    Scanner keyScan = new Scanner(System.in);

    ServerSocket serverSocket = new ServerSocket(8888);
    Socket socket = serverSocket.accept();

    PrintStream out = new PrintStream(socket.getOutputStream());
    Scanner in = new Scanner(socket.getInputStream());

    // 클라이언트가 보낸 문자열을 수신한다.
    String str = in.nextLine();
    System.out.println(str);

    // 키보드 입력을 받아서 클라이언트로 송신한다.
    System.out.print("입력> ");
    str = keyScan.nextLine();
    out.println(str);

    in.close();
    out.close();
    socket.close();
    serverSocket.close();
    keyScan.close();
  }

}


