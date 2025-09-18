// 통신 방식 - Stateless
package com.eomcs.net.ex04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// stateless
// => 서버에 작업을 요청할 때 연결하고, 서버로부터 응답을 받으면 연결을 끊는다.
// => 즉 요청/응답을 한 번만 수행한다.
// => 단점:
//    - 요청할 때마다 매번 서버에 연결해야 하기 때문에 실행 시간이 많이 걸린다.
//    - 실행시간 = 연결하는데 걸린 시간 + 데이터 전송 시간 + 작업 처리 시간 + 데이터 수신 시간
//    - 서버와 연결할 때,
//      사용자 인증(authentication; 아이디, 암호 유효 여부 검사)이나
//      사용권한 확인(authorization; 사용자에게 허락된 작업 범위를 확인) 같은
//      작업을 반드시 수행하는 경우 연결하는데 더더욱 많은 시간이 걸린다.
// => 장점:
//    - 서버에 계속 연결된 상태가 아니기 때문에 서버 입장에서는 메모리를 많이 사용하지 않는다.
//    - 왜?
//      클라이언트와 연결을 계속 유지하지 않기 때문에
//      작업을 처리하는 동안만 연결정보를 유지한다.
//      그래서 같은 메모리라도 stateful 방식 보다는
//      더 많은 클라이언트의 요청을 처리할 수 있다.
// => 대표적인 예:
//    - HTTP 통신: 웹브라우저가 서버에 연결한 후 요청을 하고 서버가 응답한 후 연결을 끊는다.
//    - 메신저: 메신저 서버에 연결하고 메시지 전송 후 연결 끊는다.
//    - 메일 전송: 메일서버에 데이터 전송 후 연결 끊는다.
//    - 오프라인 예: 114 안내, 배달 등
//
// => 통신
// 클라이언트            서버
//     |------------------>| 연결
//     |------------------>| 데이터 수신
//     |<------------------| 데이터 전송
//     |--------X--------->| 끊기
//     |------------------>| 연결
//     |------------------>| 데이터 수신
//     |<------------------| 데이터 전송
//     |--------X--------->| 끊기
//     |------------------>| 연결
//     |------------------>| 데이터 수신
//     |<------------------| 데이터 전송
//     |--------X--------->| 끊기
// - 서버에 요청할 때 마다 연결하고 응답을 받으면 즉시 연결을 끊는다.
// - 따라서 비영속적인 단일 작업을 처리할 때 적합한 통신 방식이다.

public class Client0210 {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);

    while (true) {
      System.out.print("이름? ");
      String name = keyScan.nextLine();

      if (name.equalsIgnoreCase("quit")) {
        break;
      }

      // 요청할 때 마다 서버와 연결한다.
      try (Socket socket = new Socket("192.168.0.23", 8888);
          PrintWriter out = new PrintWriter(socket.getOutputStream());
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        System.out.println("서버와 연결되었음!");

        // 한 번 요청한다.
        out.println(name);
        out.flush();

        // 응답 받으면 서버와 연결을 끊는다.
        String str = in.readLine();
        System.out.println(str);

        System.out.println("서버와 연결 끊음!");

        if (name.equals("stop")) {
          break;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    keyScan.close();
  }
}


