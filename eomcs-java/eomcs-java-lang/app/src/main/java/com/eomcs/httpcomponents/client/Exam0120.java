// Apache HttpComponents 사용법 : HttpClient5 - GET 요청하기
package com.eomcs.httpcomponents.client;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet get = new HttpGet("https://www.daum.net");
    CloseableHttpResponse response = httpClient.execute(get);
    HttpEntity entity = response.getEntity();

    if (entity != null) {
      System.out.printf("응답 데이터 크기 => %d\n", entity.getContentLength());
      System.out.printf("응답 데이터의 타입 => %s\n", entity.getContentType());
      System.out.println("---------------------------------");

      // HttpEntity에서 응답 데이터를 꺼낼 때 도우미 클래스를 사용하면 편하다.
      System.out.println(EntityUtils.toString(entity));
    }
  }
}
