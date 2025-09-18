// 대기열의 제한 확인하기
package com.eomcs.net.ex02;

import java.net.Socket;
import java.util.Scanner;

public class Client0210 {
  public static void main(String[] args) throws Exception {
    // 실행을 잠시 중단시키기 위해 사용
    Scanner keyScan = new Scanner(System.in);
    System.out.println("클라이언트 실행!");

    Socket socket = new Socket("localhost", 8888);
    System.out.println("서버에 연결됨!");
    // 서버의 대기열에 접속 순서대로 대기한다.
    // 서버에서 연결이 승인되면, 비로서 입출력을 할 수 있다.

    // 일단 멈춤!
    keyScan.nextLine();
    // 대기열의 크기에 따라 연결되는 클라이언트 수의 제한을 확인하기 위해
    // 여러 개의 클라이언트를 실행하라!

    // 자원해제
    socket.close();
    System.out.println("서버와의 연결을 끊었음.");

    keyScan.close();
  }
}


