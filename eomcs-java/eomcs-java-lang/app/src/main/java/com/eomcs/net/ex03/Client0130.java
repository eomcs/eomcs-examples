// 서버와 입출력 테스트 - byte stream : Data 주고 받기
package com.eomcs.net.ex03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client0130 {
  public static void main(String[] args) {

    try (Scanner keyScan = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8888);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      System.out.println("서버와 연결되었고, 입출력 스트림도 준비되었음!");

      // 서버에 데이터를 보내기 전에 잠깐 멈춤!
      System.out.print(">");
      keyScan.nextLine();

      // 서버에 int 값을 전송한다.
      out.writeInt(1567891234); // 랜카드의 메모리로 출력한다. 전송이 완료될 때까지 기다리는 것이 아니다.
      // out.flush();
      // byte stream 을 사용할 때는 바로 출력한다.
      // 따라서 flush()를 호출하지 않아도 된다.
      System.out.println("서버에 데이터를 보냈음!");

      // 서버에서 보낸 int 값을 읽는다.
      System.out.println("서버의 응답을 기다리고 있음!");
      int value = in.readInt(); // 수신된 데이터가 메모리에 저장될 때까지 기다린다. 그래서 블로킹한다.
      System.out.println(value);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


