// URL(Uniform Resource Locator) - 포트번호 생략
package com.eomcs.net.ex07;

import java.net.URI;
import java.net.URL;

public class Exam02 {

  public static void main(String[] args) throws Exception {
    // 포트번호 명시
    // - http://서버주소:포트번호/자원경로?파라미터명=값&파라미터명=값&파라미터명=값 
    //    URL url = new URL("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=bitcamp");
    URL url = new URI("https://search.naver.com/search.naver").toURL();

    // URL 분석
    System.out.printf("프로토콜: %s\n", url.getProtocol());
    System.out.printf("서버주소: %s\n", url.getHost());

    System.out.printf("포트번호: %d\n", url.getPort());
    // 웹브라우저에서는 포트번호를 생략하면 80(HTTP), 443(HTTPS)번으로 간주한다.
    // 다만 getPort()의 리턴 값은 -1 이다.

    System.out.printf("자원경로: %s\n", url.getPath());
    System.out.printf("QueryString: %s\n", url.getQuery()); // 없으면 null 리턴

  }

}


