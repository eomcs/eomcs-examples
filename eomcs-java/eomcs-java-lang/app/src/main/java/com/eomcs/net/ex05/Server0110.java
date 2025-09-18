// connection-oriented 서버 - 연결 후 데이터 송수신
package com.eomcs.net.ex05;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// 연결지향(connection oriented)
// => 연결 후에 데이터를 송수신 하기 때문에 데이터 송수신에 대한 신뢰를 보장한다.
// => TCP 통신 방법이 전형적인 예이다.
//    예) FTP, Telnet, SMTP, POP3, HTTP 등 
// 
public class Server0110 {
  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");
    ServerSocket ss = new ServerSocket(8888);

    Socket socket = ss.accept();
    System.out.println("클라이언트와 통신할 소캣을 준비하였다!");

    Scanner in = new Scanner(socket.getInputStream());
    PrintStream out = new PrintStream(socket.getOutputStream());

    String str = in.nextLine();
    System.out.println("데이터 수신 완료!");

    out.println(str);
    System.out.println("데이터 송신 완료!");

    in.close();
    out.close();
    socket.close();
    System.out.println("클라이언트 연결 끊기!");

    ss.close();
  }
}





