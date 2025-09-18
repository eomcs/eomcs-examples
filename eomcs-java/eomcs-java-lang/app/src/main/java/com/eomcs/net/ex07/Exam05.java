// URL(Uniform Resource Locator) - Windows OS의 로컬 파일 경로
package com.eomcs.net.ex07;

import java.net.URI;
import java.net.URL;

public class Exam05 {

  public static void main(String[] args) throws Exception {
    // 로컬 자원의 위치를 URL로 표현하는 방법
    //      file://자원의 경로
    // - 자원의 경로
    //      /드라이브명:/디렉토리 또는 파일 경로  (Windows)
    //      /루트디렉토리/디렉토리 또는 파일 경로 (Linux/macOS/Unix)
    URL url = new URI("file:///c:/Users/user/git/bitcamp-study/Hello.java").toURL();

    // URL 분석
    System.out.printf("프로토콜: %s\n", url.getProtocol());
    System.out.printf("서버주소: %s\n", url.getHost());
    System.out.printf("포트번호: %d\n", url.getPort());
    System.out.printf("자원경로: %s\n", url.getPath());

  }

}


