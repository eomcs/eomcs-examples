package com.eomcs.cleancode.ch04.exam04;

import java.net.SocketException;

public class BadAndGood6 {

  static class Response {
    void add(String msg) {}
    void closeAll() {}
  }

  static class ErrorResponder {
    static String makeExceptionString(Exception e) { return e.getMessage(); }
  }

  // Bad: 있으나 마나 한 주석 (Noise Comments)
  // - "// The user's name"은 필드명 name이 이미 말하는 내용이다.
  // - "/** default constructor */"도 생성자라는 사실을 반복할 뿐이다.
  // - "normal. someone stopped the request."는 정상 상황을 설명하지만 동작을 개선하지 않는다.
  // - "Give me a break!"는 프로그래머의 감정 표현이며 아무런 정보도 없다.
  static class BadAnnualDateRule {
    // The user's name
    private String name;

    /**
     * default constructor
     */
    BadAnnualDateRule() {}

    String getName() { return name; }
  }

  static class BadSender {
    Response response = new Response();

    void doSending() throws Exception {}

    void startSending() {
      try {
        doSending();
      } catch (SocketException e) {
        // normal. someone stopped the request.
      } catch (Exception e) {
        try {
          response.add(ErrorResponder.makeExceptionString(e));
          response.closeAll();
        } catch (Exception e1) {
          //Give me a break!
        }
      }
    }
  }

  // Good: 잡음 주석을 제거하고 코드를 정리한다.
  // - 필드와 생성자 앞의 당연한 주석을 모두 제거했다.
  // - 중첩된 try-catch를 메서드로 추출하여 잡음 주석이 필요 없어졌다.
  static class GoodAnnualDateRule {
    private String name;

    GoodAnnualDateRule() {}

    String getName() { return name; }
  }

  static class GoodSender {
    Response response = new Response();

    void doSending() throws Exception {}

    void startSending() {
      try {
        doSending();
      } catch (SocketException e) {
        // 클라이언트가 요청을 취소한 정상적인 상황이다.
      } catch (Exception e) {
        addExceptionAndCloseResponse(e);
      }
    }

    private void addExceptionAndCloseResponse(Exception e) {
      try {
        response.add(ErrorResponder.makeExceptionString(e));
        response.closeAll();
      } catch (Exception ignored) {}
    }
  }
}
