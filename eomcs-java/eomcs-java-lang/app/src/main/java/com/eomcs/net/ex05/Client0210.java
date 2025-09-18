// connectionless 클라이언트 - 연결없이 데이터 송신
package com.eomcs.net.ex05;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Connectionless
// => 서버와 연결없이 데이터를 보내고 받을 수 있다.
// => DatagramSocket, DatagramPacket을 사용하여 처리한다.
// => 예) 편지, ping 등
// => 응용) 모니터링 프로그램에서 많이 사용한다.
//
public class Client0210 {
  public static void main(String[] args) throws Exception {
    // connectionless 방식으로 통신을 수행할 소켓 생성
    // - 클라이언트 쪽은 포트 번호를 지정하지 않는다.
    // - 물론 OS가 자동으로 부여할 것이다.
    DatagramSocket socket = new DatagramSocket();

    // 보낼 데이터를 바이트 배열로 준비
    //    String message = new String("Hello"); // Heap에 String 객체 생성
    //    String message = "Hello"; // constant pool에 String 객체 생성
    //    byte[] bytes = message.getBytes("UTF-8");
    byte[] bytes = "Hello".getBytes("UTF-8");

    // 보낼 데이터를 패킷에 담는다.
    // => 패킷 = 데이터 + 데이터크기 + 받는이의 주소 + 받는이의 포트번호
    DatagramPacket packet = new DatagramPacket(
        bytes, // 데이터가 저장된 바이트 배열
        bytes.length, // 전송할 데이터 개수
        InetAddress.getByName("localhost"), // 데이터를 받을 상대편 주소
        8888 // 포트번호
        );

    // 데이터 전송
    socket.send(packet);
    System.out.println("데이터 전송 완료!");

    // 자원해제
    socket.close();

    // 상대편이 네트웍에 연결되었는지 따지지 않고 무조건 데이터를 보낸다.
    // 만약 상대편이 연결되어 있지 않다면, 보낸 데이터는 그 쪽 네트웍에서 버려진다.
    // => 데이터 송수신을 보장하지 않는다.
  }
}


