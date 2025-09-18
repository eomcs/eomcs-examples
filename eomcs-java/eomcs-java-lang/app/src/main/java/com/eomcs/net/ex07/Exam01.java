// URL(Uniform Resource Locator) - URL을 다루는 클래스
package com.eomcs.net.ex07;

import java.net.URI;
import java.net.URL;

public class Exam01 {

  public static void main(String[] args) throws Exception {
    // 웹 상에서 자원의 위치를 표현하는 방법
    // => [프로토콜]://서버주소:포트번호/자원의경로?파라미터명=값&파라미터명=값
    // - 프로토콜: http(80), https(443), ftp(21/20) 등
    // - 서버주소: IP 주소(192.168.0.1), 도메인명(www.bitcamp.co.kr)
    // - 포트번호: 80 또는 443(생략할 수 있다), 8080(프록시 서버) 등
    // - 자원의경로: /index.html, /board/list.jsp 등
    // - 서버에 보내는 파라미터(Query String): 파라미터명=값&파라미터명=값
    //
    // 자원
    // - 정적 자원(static)
    //   - 요청할 때 마다 결과 콘텐트가 변경되지 않는 자원. 즉 파일을 가리킨다.
    //   - 예) HTML, GIF, JPEG, PNG, CSS, JavaScript, TXT 등의 파일
    // - 동적 자원(dynamic)
    //   - 요청할 때 마다 결과 콘텐트가 변할 수 있는 자원. 
    //   - 메일 조회, 게시물 변경, 주문 등의 웹 프로그램을 가리킨다.
    //   - 예) index.php, index.jsp, /board/list 등
    //
    // version: Java19까지 
    //    URL url = new URL("https://search.naver.com:443/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=bitcamp");

    // version: Java20부터
    URL url = new URI("https://search.naver.com:443/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=bitcamp").toURL();

    // URL 분석
    System.out.printf("프로토콜: %s\n", url.getProtocol());
    System.out.printf("서버주소: %s\n", url.getHost());
    System.out.printf("포트번호: %d\n", url.getPort()); // 지정하지 않으면 -1 리턴. 실제 접속할 때는 기본 포트번호 사용.
    System.out.printf("자원경로: %s\n", url.getPath());
    System.out.printf("서버에 보내는 파라미터: %s\n", url.getQuery());
  }

}


