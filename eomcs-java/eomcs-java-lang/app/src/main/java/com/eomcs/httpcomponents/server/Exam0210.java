// Apache HttpComponents 사용법 : 동적 콘텐트를 출력하는 웹서버 만들기
package com.eomcs.httpcomponents.server;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ConnectionClosedException;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ExceptionListener;
import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.util.TimeValue;

public class Exam0210 {
  public static void main(final String[] args) throws Exception {

    final SocketConfig socketConfig = SocketConfig.custom() //
        .setSoTimeout(15, TimeUnit.SECONDS)//
        .setTcpNoDelay(true) //
        .build();

    final ServerApp server = ServerBootstrap.bootstrap()//
        .setListenerPort(9999) // 웹서버 포트 번호 설정
        .setSocketConfig(socketConfig) // 기본 소켓 동작 설정
        .setSslContext(null) // SSL 설정
        .setExceptionListener(new MyExceptionListener()) // 예외 처리자 설정
        .register("*", new MyRequestHandler()) // 요청 처리자 설정
        .create(); // 웹서버 객체 생성

    // 웹서버를 시작시킨다.
    server.start();

    // 웹서버를 종료시키는 스레드를 등록한다.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        server.close(CloseMode.GRACEFUL);
      }
    });

    System.out.println("서버 시작(9999)!");

    server.awaitTermination(TimeValue.MAX_VALUE);
  }

  // 웹서버에서 예외가 발생했을 때 그 예외를 처리하는 일을 한다.
  static class MyExceptionListener implements ExceptionListener {
    @Override
    public void onError(final Exception ex) {
      ex.printStackTrace();
    }

    @Override
    public void onError(final HttpConnection conn, final Exception ex) {
      if (ex instanceof SocketTimeoutException) {
        System.err.println("Connection timed out");
      } else if (ex instanceof ConnectionClosedException) {
        System.err.println(ex.getMessage());
      } else {
        ex.printStackTrace();
      }
    }
  }

  static class MyRequestHandler implements HttpRequestHandler {

    @Override
    public void handle(final ClassicHttpRequest request, // 클라이언트 요청처리 도구
        final ClassicHttpResponse response, // 클라이언트 응답처리 도구
        final HttpContext context // HTTP 설정 도구
    ) throws HttpException, IOException {

      response.setCode(HttpStatus.SC_OK);
      final StringEntity body = new StringEntity(//
          "<html><body><h1>안녕하세요!</h1></body></html>", //
          ContentType.create("text/html", Charset.forName("UTF-8")));
      response.setEntity(body);
    }

  }

}
