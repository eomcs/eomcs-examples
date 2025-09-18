package com.eomcs.net.ex13.step01;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class MiniHttpServer {
  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
    server.createContext("/", (t) -> {
      String response = "This is the response.";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    });
    server.setExecutor(null);
    server.start();
    System.out.println("서버 시작!");
  }
}
