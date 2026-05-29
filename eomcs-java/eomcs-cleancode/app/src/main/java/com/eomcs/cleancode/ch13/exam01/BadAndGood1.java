package com.eomcs.cleancode.ch13.exam01;

// 예제 1: 응답성 문제 - 동시성은 "빠르게 만드는 것"이 아니라 "기다리지 않게 만드는 것"
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Request {

    private final String body;

    Request(String body) {
      this.body = body;
    }

    String getBody() {
      return body;
    }
  }

  // Bad: 요청 처리가 순차적으로 진행되어 사용자가 기다려야 한다
  //   - 요청 하나 처리하는 데 3초
  //   - 사용자 경험이 나쁨
  //   - 다른 요청도 대기해야 함
  static class BadWebServer {

    public void handleRequest(Request request) {
      saveToDatabase(request);
      sendResponse("OK");
    }

    private void saveToDatabase(Request request) {
      try {
        Thread.sleep(3000); // DB 처리 지연
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    private void sendResponse(String message) {
      System.out.println("Response: " + message);
    }
  }

  // Good: DB 작업을 별도 스레드에서 처리하여 응답성을 높인다
  //   - 사용자 응답은 즉시 반환
  //   - DB 작업은 별도 스레드에서 처리
  //   - 시스템 응답성이 개선됨
  static class WebServer {

    public void handleRequest(Request request) {
      new Thread(() -> saveToDatabase(request)).start();
      sendResponse("OK");
    }

    private void saveToDatabase(Request request) {
      try {
        Thread.sleep(3000); // DB 처리 지연
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    private void sendResponse(String message) {
      System.out.println("Response: " + message);
    }
  }
}
