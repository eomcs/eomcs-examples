// HTTP 클라이언트 만들기
package com.eomcs.net.ex06;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

// HTTP 요청 프로토콜
// ---------------------------------
// GET [자원주소] HTTP/1.1 (CRLF)
// Host: [서버주소] (CRLF)
// (CRLF)
// ---------------------------------
//
// 프로토콜(protocol)?
// => 클라이언트/서버 간의 통신 규칙.
// => 데이터를 주고 받는 규칙.
//
public class HttpClient {
  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("www.rpm9.com", 80);
    PrintStream out = new PrintStream(socket.getOutputStream());
    Scanner in = new Scanner(socket.getInputStream());

    // HTTP 요청 프로토콜에 따라 서버에 데이터 전송
    // => macOS에서 JVM을 실행할 때, println()은 문자열 뒤에 0a(LF) 코드만 붙인다.
    // => 이를 해결하려면, 다음과 같이 명확하게 CRLF 코드를 붙여라.
    // 
    out.print("GET /news/article.html?id=20240112090002 HTTP/1.1\r\n");
    out.print("Host: www.rpm9.com\r\n");
    out.print("\r\n");
    out.flush();

    // HTTP 응답 프로토콜에 따라 서버가 보낸 데이터를 수신
    while (true) {
      try {
        System.out.println(in.nextLine());
      } catch (Exception e) {
        break;
      }
    }

    out.close();
    in.close();
    socket.close();
  }
}

// HTTP 요청 예)
// ---------------------------------------------------------------------------------
// GET /devide?a=400&b=300 HTTP/1.1
// Host: localhost
// Connection: keep-alive
// Cache-Control: max-age=0
// sec-ch-ua: "\\Not;A\"Brand";v="99", "Google Chrome";v="85", "Chromium";v="85"
// sec-ch-ua-mobile: ?0
// Upgrade-Insecure-Requests: 1
// User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36
// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
// Sec-Fetch-Site: none
// Sec-Fetch-Mode: navigate
// Sec-Fetch-User: ?1
// Sec-Fetch-Dest: document
// Accept-Encoding: gzip, deflate, br
// Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
// (빈 줄)
