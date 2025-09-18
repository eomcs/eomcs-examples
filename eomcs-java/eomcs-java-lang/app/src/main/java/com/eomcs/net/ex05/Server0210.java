// connectionless 클라이언트 - 연결없이 데이터 수신
package com.eomcs.net.ex05;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

// 비연결(connectionless)
// => 연결없이 데이터를 송수신한다.
// => 상대편이 네트워크에 연결되어 있지 않다면, 그 데이터는 버려진다.
// => 그래서 전송 여부를 신뢰할 수 없다.
// => 실생활에서 "편지"와 같다.
// => 예) ping
// => DatagramSocket, DatagramPacket을 사용하여 처리한다.
public class Server0210 {
  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);

    System.out.println("서버 실행 중...");

    System.out.println("소켓 생성 전 잠깐!>");
    keyScan.nextLine();

    // 데이터 송수신을 담당할 소켓을 먼저 준비한다.
    // => 보내는 쪽이나 받는 쪽이나 같은 소켓 클래스를 사용한다.
    //    서버 소켓이 따로 없다.
    // => 받는 쪽에서는 소켓을 생성할 때 포트번호를 설정한다.
    DatagramSocket socket = new DatagramSocket(8888);

    // 받은 데이터를 저장할 버퍼 준비
    byte[] buf = new byte[8196];

    // 빈 패킷 준비
    DatagramPacket emptyPacket = new DatagramPacket(buf, buf.length);

    System.out.println("데이터를 읽기 전에 잠깐 멈춤>");
    keyScan.nextLine();

    // 빈 패킷을 사용하여 클라이언트가 보낸 데이터를 받는다.
    // => 데이터를 받을 때까지 리턴하지 않는다.
    socket.receive(emptyPacket);
    System.out.println("데이터를 받았음!");

    socket.close();
    keyScan.close();

    // 빈 패킷에 저장된 클라이언트가 보낸 데이터를 꺼낸다.
    // 패킷에 저장된 UTF-8로 인코딩된 바이트 배열을 가지고 String 객체(UTF-16)를 만든다.

    // 1) 패킷 객체에 보관된 바이트 배열을 꺼낸다.
    byte[] bytes = emptyPacket.getData();

    // getData()가 리턴한 배열은 DatagramPacket 을 만들 당시 넘겨준 배열이다.
    System.out.println(buf == bytes);

    // 2) 바이트 배열에 보관된 데이터의 개수를 알아낸다.
    int len = emptyPacket.getLength();

    // 3) 클라이언트에서 받은 바이트 배열을 가지고 String 객체를 생성한다.
    String message = new String(bytes, 0, len, "UTF-8");

    // 실무에서는 다음과 같이 로컬 변수를 사용하지 않고 직접 패킷 객체를 사용하는 방식으로 코딩한다.
    //    String message = new String(//
    //        emptyPacket.getData(), // ==> buf, 패킷에서 바이트 배열을 꺼낸다.
    //        0, // 버퍼에서 데이터를 꺼낼 때 0번째부터 꺼낸다.
    //        emptyPacket.getLength(), // 패킷에서 받은 바이트의 개수만큼 데이터를 꺼낸다.
    //        "UTF-8" // 바이트 배열로 인코딩된 문자표의 이름을 지정한다.
    //        );

    System.out.println(message);

  }
}


