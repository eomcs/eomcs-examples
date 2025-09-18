// 클라이언트와 입출력 테스트 - byte stream
package com.eomcs.net.ex03;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0110 {
  public static void main(String[] args) {
    Scanner keyboard = null;
    ServerSocket serverSocket = null;

    try {
      keyboard = new Scanner(System.in);
      serverSocket = new ServerSocket(8888);
      System.out.println("클라이언트의 연결을 기다리고 있음.");

      Socket socket = null;
      OutputStream out = null;
      InputStream in = null;

      try {
        // accept()
        // - 대기열에서 클라이언트 정보를 한 개 꺼내 소켓을 만들어 클라이언트와 연결한다.
        // - 만약 대기열에 클라이언트 정보가 없다면 클라이언트가 접속할 때까지 기다리다.
        socket = serverSocket.accept();
        System.out.println("대기열에서 클라이언트 정보를 꺼내 소켓을 생성하였음.");

        // 클라이언트와 데이터를 주고 받을 입출력 스트림 객체를 준비한다.
        // => 출력 스트림 객체를 준비하기
        out = socket.getOutputStream();

        // => 입력 스트림 객체를 준비하기
        in = socket.getInputStream();
        System.out.println("클라이언트와 통신할 입출력 스트림이 준비되었음.");

        // Client와 Server의 통신 규칙에 따라 순서대로 입출력 해야 한다.
        // 왜?
        // 입력은 blocking 모드로 작동하기 때문이다.
        // 즉 read()라는 메서드는 클라이언트가 보낸 데이터를 읽기 전까지 리턴하지 않는다.
        //
        // 클라이언트와 서버 간의 데이터를 주고 받는 통신 규칙을 "프로토콜(protocol)"이라 한다.
        // 클라이언트에서 한 줄의 문자열을 보내면
        // 서버는 한 줄의 문자열을 읽은 후에 응답해야 한다.

        System.out.print("데이터를 읽기 전에 잠깐!");
        keyboard.nextLine();

        System.out.println("클라이언트가 보낸 1바이트를 기다리고 있음!");
        // => 클라이언트가 1바이트를 보낼 때까지 리턴하지 않는다.
        int request = in.read(); // blocking 모드로 작동한다.

        System.out.println(request);

        // 서버가 데이터를 보내지 않으면 클라이언트의 read()는 리턴하지 않는다.
        // 이를 확인하기 위해 잠시 실행을 멈춘다.
        System.out.print("데이터를 보내기 전에 잠깐!");
        keyboard.nextLine();

        // => 클라이언트에게 받은 값을 보낸다.
        //    NIC 메모리에 데이터를 저장하고 클라이언트로 전송한다.
        //    전송이 완료되면 클라이언트가 읽든 말든 상관없이 리턴한다.
        out.write(request + 1);
        // out.flush();
        // byte stream 을 사용할 때는 바로 출력한다.
        // 따라서 flush()를 호출하지 않아도 된다.
        System.out.println("클라인트에게 데이터를 보냈음.");

      } catch (Exception e) {
        e.printStackTrace();

      } finally {
        try {out.close();} catch (Exception e) {}
        try {in.close();} catch (Exception e) {}
        try {socket.close();} catch (Exception e) {}
        System.out.println("클라이언트와의 연결을 끊었음.");
      }


    } catch (Exception e) {
      System.out.println("상세 예외 정보:");
      e.printStackTrace();

    } finally {
      System.out.println("키보드 자원 해제 및 서버 소켓 자원 해제!");
      keyboard.close();
      try { serverSocket.close();} catch (IOException e) {}
    }
    System.out.println("서버 종료!");
  }

}

