// URL(Uniform Resource Locator) - 부가 데이터 지정
package com.eomcs.net.ex07;

import java.net.URI;
import java.net.URL;

public class Exam04 {

  public static void main(String[] args) throws Exception {
    // 서버에 보내는 파라미터를 표시
    // - http://서버주소:포트/자원경로?파라미터명=값&파라미터명=값&파라미터명=값 
    URL url = new URI("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=bitcamp").toURL();

    // URL 분석
    System.out.printf("프로토콜: %s\n", url.getProtocol());
    System.out.printf("서버주소: %s\n", url.getHost());
    System.out.printf("포트번호: %d\n", url.getPort());
    System.out.printf("자원경로: %s\n", url.getPath());

    System.out.printf("쿼리스트링: %s\n", url.getQuery());// 없으면 null 리턴
    // Query String
    // - 자원의 경로(예: /index.php) 다음(물음표 ? 다음)에 오는 파라미터
    // - 형식: 파라미터명=값&파라미터명=값&파라미터명=값
    // - 예) sm=top_hty&fbm=1&ie=utf8&query=비트캠프

  }

}


