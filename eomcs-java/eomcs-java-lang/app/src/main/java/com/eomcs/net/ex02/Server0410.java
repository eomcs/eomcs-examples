// 대기열에서 기다리고 있는 클라이언트와 연결하기
package com.eomcs.net.ex02;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0410 {
  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    ServerSocket ss = new ServerSocket(8888, 2);
    System.out.println("서버 소켓 생성 완료!");
    System.out.println("클라이언트 연결을 기다리는 중...");

    // 테스트1) 대기열에 클라이언트가 없을 때,
    // => accept()는 블로킹 상태에 놓인다.
    //    즉 리턴하지 않는다.
    // 

    // 큐(queue)에 대기중인 클라이언트 중 첫 번째 클라이언트를 꺼내서 연결을 승인한다.
    // => 클라이언트가 서버에 연결을 요청하면, 서버는 대기열에 추가한다.
    // => 서버소켓에서 연결을 승인하면 클라이언트와 통신할 수 있는 소켓을 리턴한다.
    // => 대기열에 기다리고 있는 클라이언트가 없으면 접속할 때까지 기다린다.
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


