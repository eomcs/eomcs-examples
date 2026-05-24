package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood15 {

  // Bad: 너무 많은 정보 (Too Much Information)
  // - HTTP의 역사, RFC 상세 내용, 배경 지식이 주석에 가득하다.
  // - 코드를 이해하는 데 불필요한 내용이 대부분이다.
  // - 주석에다 흥미로운 역사나 관련 없는 정보를 장황하게 늘어놓지 마라.
  static class BadHttpClient {
    /*
     * HTTP was originally designed in 1989 by Tim Berners-Lee.
     * RFC 2616 defines HTTP/1.1, which is the most widely used version.
     * HTTP/2 was introduced in RFC 7540 to improve performance.
     * Historical background: HTTP evolved from the early web protocols...
     */
    void sendRequest() {
      System.out.println("HTTP 요청 전송");
    }
  }

  // Good: 메서드 이름만으로 충분할 때는 주석을 달지 않는다.
  // - sendRequest()라는 이름이 동작을 이미 설명한다.
  // - 코드 이해에 직접 필요한 정보만 최소한으로 남긴다.
  static class GoodHttpClient {
    void sendRequest() {
      System.out.println("HTTP 요청 전송");
    }
  }
}
