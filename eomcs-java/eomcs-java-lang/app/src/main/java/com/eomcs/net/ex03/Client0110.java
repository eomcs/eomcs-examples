// 서버와 입출력 테스트 - byte stream
package com.eomcs.net.ex03;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client0110 {
  public static void main(String[] args) {
    Scanner keyScan = null;
    Socket socket = null;
    OutputStream out = null;
    InputStream in = null;

    try {
      keyScan = new Scanner(System.in);
      socket = new Socket("localhost", 8888);
      System.out.println("서버와 연결되었음!");

      // 서버랑 데이터를 주고 받을 수 있도록 입출력 스트림 객체를 준비한다.
      // => 출력 스트림 객체를 준비하기
      out = socket.getOutputStream();

      // => 입력 스트림 객체를 준비하기
      in = socket.getInputStream();
      System.out.println("소켓을 통해 입출력 스트림을 준비하였음!");

      System.out.print("서버에 데이터를 보내기 전에 잠깐!>");
      keyScan.nextLine();

      // 서버에게 데이터 보내기
      // - 보통 클라이언트 쪽에서 먼저 서버로 데이터를 보낸다.
      // - 실제 write()는 소켓의 내부 버퍼로 출력한다.
      //   따라서 write() 호출 후 즉시 리턴될 것이다.
      // - 즉 상대편에서 읽을 때까지 기다리는 것이 아니다.
      // - 보낼 데이터를 랜카드의 메모리에 올려 놓으면 바로 리턴한다.
      out.write(100);
      // out.flush();
      // byte stream 을 사용할 때는 바로 출력한다.
      // 따라서 flush()를 호출하지 않아도 된다.
      System.out.println("서버에 데이터를 보냈음!");

      System.out.print("서버가 보낸 데이터를 읽기 전에 멈춤!>");
      keyScan.nextLine();

      // 서버의 응답을 받는다.
      // - 서버가 응답을 할 때까지 리턴하지 않는다.
      // 즉 blocking 모드로 작동한다.
      int response = in.read();
      System.out.println(response);

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      try {out.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
      try {socket.close();} catch (Exception e) {}
    }

    keyScan.close();
  }
}


