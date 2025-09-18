// 상대편으로부터 연결 요청 받기 - 서버(server)
package com.eomcs.net.ex01;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {

  public static void main(String[] args) throws Exception {
    Scanner keyscan = new Scanner(System.in);

    System.out.println("서버 실행!");

    // 1) 다른 컴퓨터의 연결 요청을 기다린다.
    // - new ServerSocket(포트번호)
    // - 포트번호:
    //   - 호스트에서 실행 중인 서버 프로그램을 구분하는 번호이다.
    //   - 1024 ~ 49151 사이의 값 사용한다.
    //      - 1 ~ 1023 사이의 포트 번호는 특정 서버가 사용하기 위해 미리 예약된 번호다.
    //      - 가능한 이 범위의 포트 번호는 사용하지 않는 것이 좋다.
    //   - 유명 프로그램의 포트 번호도 가능한 사용하지 말라.
    //      - 예) Oracle DBMS(1521), MySQL DBMS(3306), 프록시 서버(8080) 등
    //   - 같은 컴퓨터에서 다른 프로그램이 이미 사용중인 포트 번호는 지정할 수 없다.
    //      - 포트 번호는 중복으로 사용될 수 없다.
    ServerSocket serverSocket = new ServerSocket(8888);
    System.out.println("ServerSocket 생성!");

    // 2) 연결을 기다리고 있는 클라이언트가 있다면 맨 먼저 접속한 클라이언트의 연결을 승인한다.
    // - 클라이언트가 서버에 접속을 요청하면 그 정보를 "대기열"이라고 불리는 목록으로 관리한다.
    // - accept()를 호출하면 대기열에서 순서대로 꺼내 해당 클라이언트와 연결된 소켓을 만든다.
    Socket socket = serverSocket.accept();
    System.out.println("클라이언트와 연결된 Socket 생성!");

    // 3) 소켓 객체를 통해 읽고 쓸 수 있도록 입출력 스트림을 얻는다.
    // - 연결된 클라이언트로 데이터를 보내고 받으려면 입출력 스트림을 꺼내야 한다.
    // - 소켓이 리턴해준 입출력 스트림에 적절한 데코레이터를 붙여서 사용한다.
    PrintStream out = new PrintStream(socket.getOutputStream());
    Scanner in = new Scanner(socket.getInputStream());
    System.out.println("데이터 송수신을 위한 입출력 스트림 준비!");

    //    keyscan.nextLine(); // 클라이언트가 보낸 문자열을 읽지 않고 대기함!

    // 4) 상대편이 보낸 문자열을 한 줄 읽는다.
    // => 상대편이 한 줄의 데이터를 보낼 때까지 리턴하지 않는다.
    // => 이런 메서드를 블로킹 메서드라 부른다.
    String str = in.nextLine();
    System.out.printf("상대편> %s\n", str);

    // 5) 상대편으로 문자열을 한 줄 보낸다.
    // => 클라이언트가 데이터를 받을 때까지 기다리지 않는다.
    //    NIC의 메모리에 데이터를 전달한 후 즉시 리턴한다.
    out.println("나는 홍길동입니다. 반갑습니다!");
    System.out.println("클라이언트로 데이터 보냈음!");

    // 6) 항상 입출력 도구는 사용 후 닫아야 한다.
    in.close();
    out.close();

    // 7) 네트워크 연결도 닫는다.
    socket.close(); // 클라이언트와 연결을 끊는다.
    serverSocket.close(); // 클라이언트의 연결 요청을 받지 않는다.

    keyscan.close();
  }

}


