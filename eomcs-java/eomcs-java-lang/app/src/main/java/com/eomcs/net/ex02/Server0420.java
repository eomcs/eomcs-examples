// 대기열과 연결 승인 테스트
package com.eomcs.net.ex02;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0420 {
  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    ServerSocket ss = new ServerSocket(8888, 2);
    System.out.println("서버 소켓 생성 완료!");
    System.out.println("클라이언트 연결을 기다리는 중...");

    // 테스트2) 여러 클라이언트가 대기열에 있을 때,
    // => accept()를 호출하는 순간 즉시 대기열에서 맨 앞의 클라이언트 정보를 꺼내 연결한다.
    // => Queue 방식으로 대기열을 관리하기 때문에 맨 뒤에 빈자리가 생긴다.
    // 
    // 3개의 클라이언트들이 들어올 때까지 기다린다.
    // => 3개 중에 2개는 대기열에 기다리고, 나머지 1 개는 대기열에 없는 상태다.
    keyboard.nextLine();

    // 대기열에 있는 2개 중에 한 개의 클라이언트를 승인하면,
    // => 대기열에 포함 안된 나머지 1개의 클라이언트가 대기열에 들어온다.
    Socket socket = ss.accept();
    System.out.println("대기 중인 클라이언트 중 한 개의 클라이언트에 대해 연결 승인!");

    keyboard.nextLine();

    socket.close();
    System.out.println("클라이언트와의 연결 해제!");

    ss.close();
    System.out.println("서버 종료!");

    keyboard.close();
  }
}


