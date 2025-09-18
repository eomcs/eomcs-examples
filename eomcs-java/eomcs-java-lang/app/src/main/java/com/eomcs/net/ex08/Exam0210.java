// URL 요청하기 - URLConnection 사용
package com.eomcs.net.ex08;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Exam0210 {

  public static void main(String[] args) throws Exception {

    // => URL 주소를 준비한다.
    URL url = new URI("https://sports.news.naver.com/index").toURL();

    // => URL 정보를 가지고 HTTP 요청을 수행할 객체를 얻는다.
    URLConnection con = url.openConnection();

    // => 웹서버와 연결한 후 HTTP 요청한다.
    con.connect();

    // URL.openStream()을 사용하는 것 보다 이점?
    // - 응답 헤더의 다양한 값을 추출할 수 있다.
    System.out.printf("Content-Type: %s\n", con.getContentType());
    System.out.printf("Content-Length: %d\n", con.getContentLength());
    System.out.printf("Content-Encoding: %s\n", con.getContentEncoding());

    // - 직접 헤더 이름을 사용해서 헤더 값을 추출할 수 있다.
    System.out.printf("Content-Type: %s\n", con.getHeaderField("Content-Type"));
    System.out.printf("Server: %s\n", con.getHeaderField("Server"));
    System.out.println();

    // => 웹서버의 응답 데이터를 읽어들일 도구를 리턴한다.
    InputStream in = con.getInputStream();

    // => 서버가 보낸 데이터를 한 줄씩 읽기 위해 데코레이터를 붙인다.
    BufferedReader in2 = new BufferedReader(new InputStreamReader(in));

    while (true) {
      String str = in2.readLine();
      if (str == null)
        break;

      System.out.println(str);
    }

    in2.close();
    in.close();
  }

}


