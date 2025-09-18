// connection-oriented 클라이언트 - 연결 후 데이터 송수신
package com.eomcs.net.ex05;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client0110 {
  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("localhost", 8888); // 서버에 연결
    System.out.println("서버에 연결됨!");

    // 연결이 이루어진 후 데이터 입출력을 할 수 있다.
    Scanner in = new Scanner(socket.getInputStream());
    PrintStream out = new PrintStream(socket.getOutputStream());

    out.println("Hello!");
    System.out.println("데이터 보냄!");

    String str = in.nextLine();
    System.out.println("데이터 받음!");
    System.out.println(str);

    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결 끊음!");
  }
}


