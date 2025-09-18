// 상대편에 연결을 요청하기 - 클라이언트(client)
package com.eomcs.net.ex01;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

  public static void main(String[] args) throws Exception {
    Scanner keyscan = new Scanner(System.in);

    System.out.println("클라이언트 실행!");

    // 1) 다른 컴퓨터와 네트워크로 연결한다.
    // => 서버와 연결되면 Socket 객체가 생성된다.
    // => 서버와 연결될 때까지 리턴하지 않는다.
    // => 서버에 연결할 수 없으면 예외가 발생한다.
    //    - 기본으로 설정된 타임아웃 시간까지 연결되지 않으면 예외가 발생한다.
    //
    // new Socket(원격 호스트의 IP 주소/도메인이름, 원격 호스트 프로그램의 포트번호)
    //    - 로컬 호스트(애플리케이션을 실행하는 현재 컴퓨터)일 경우: 127.0.0.1 또는 localhost
    Socket socket = new Socket("127.0.0.1", 8888); // 서버의 대기열에 등록된다면 리턴한다.
    System.out.println("서버와 연결된 Socket 생성!");

    // 2) 소켓 객체를 통해 읽고 쓸 수 있도록 입출력 스트림을 얻는다.
    PrintStream out = new PrintStream(socket.getOutputStream());
    Scanner in = new Scanner(socket.getInputStream());
    System.out.println("서버와 데이터를 송수신할 스트림 준비!");

    // 3) 상대편으로 문자열을 한 줄 보낸다.
    // => 보낸 데이터를 NIC의 메모리에 임시 보관된다.
    // => 임시 보관된 데이터가 상대편으로 완전히 보내졌는지 따지지 않고 즉시 리턴한다.
    out.println("임꺽정입니다. 안녕하세요!");
    System.out.println("서버에 데이터 보냈음!");

    keyscan.nextLine(); // 서버가 보낸 문자열을 읽지 않고 대기함!

    // 4) 상대편에서 보낸 문자열을 한 줄 읽는다.
    // => 상대편이 한 줄 데이터를 보낼 때까지 리턴하지 않는다.
    // => 이런 메서드를 블로킹 메서드라 부른다.
    String str = in.nextLine();
    System.out.println(str);

    // 5) 항상 입출력 도구는 사용 후 닫아야 한다.
    in.close();
    out.close();

    // 6) 네트워크 연결도 닫는다.
    socket.close();

    keyscan.close();
  }

}


