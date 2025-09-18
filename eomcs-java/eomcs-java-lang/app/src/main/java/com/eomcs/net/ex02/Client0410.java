// 서버의 승인 테스트
package com.eomcs.net.ex02;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client0410 {
  public static void main(String[] args) throws Exception {
    // 실행을 잠시 중단시키기 위해 사용
    Scanner keyScan = new Scanner(System.in);
    System.out.println("클라이언트 실행!");

    Socket socket = new Socket();
    System.out.println("소켓 생성됨.");

    SocketAddress socketAddress = new InetSocketAddress("localhost", 8888);

    System.out.println("서버와 연결 중...");
    socket.connect(socketAddress, 10000);
    System.out.println("서버와 연결되었음!");

    keyScan.nextLine();

    socket.close();
    System.out.println("서버와의 연결을 끊었음.");

    keyScan.close();
  }
}


