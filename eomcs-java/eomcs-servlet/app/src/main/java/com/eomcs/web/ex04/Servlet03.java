// 클라이언트가 보낸 데이터 읽기 - 파일 업로드 처리하기
package com.eomcs.web.ex04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex04/s3")
public class Servlet03 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // POST 요청으로 파일 전송하기
    // - 파일을 첨부하여 서버에 전송한다.
    // - multipart/form-data 형식으로 데이터를 전송하지 않으면
    //   첨부 파일의 데이터는 받을 수 없다.
    //
    // 테스트
    // - http://localhost:8080/java-web/ex04/test03.html 실행
    //

    req.setCharacterEncoding("UTF-8");

    String age = req.getParameter("age");
    String name = req.getParameter("name");
    String photo = req.getParameter("photo");

    res.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.printf("이름=%s\n", name);
    out.printf("나이=%s\n", age);
    out.printf("사진=%s\n", photo);

    // GET 요청이나 일반 POST 요청을 한 경우에는
    // 파일이 이름만 넘어오고 파일 데이터는 넘어오지 않는다.
    //
    // 파일의 데이터를 전송하려면,
    // <form> 태그에 enctype 속성을 "multipart/form-data"로 설정해야 한다.
    //
    // 단 멀티파트 형식으로 데이터가 넘어온 경우에는
    // getParameter()로 그 값을 꺼낼 수 없다.
  }
}

// 1) GET 요청 예:
//
// GET /eomcs-java-web/ex04/s3?name=AB%EA%B0%80%EA%B0%81&age=20&photo=actors.jpg HTTP/1.1
// Host: 192.168.1.10:9999
// Pragma: no-cache
// Cache-Control: no-cache
// Upgrade-Insecure-Requests: 1
// User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like
// Gecko) Chrome/80.0.3987.149 Safari/537.36
// Accept:
// text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
// Referer: http://192.168.1.10:9999/eomcs-java-web/ex04/test03.html
// Accept-Encoding: gzip, deflate
// Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,cs;q=0.5
// Connection: keep-alive
//
// 2) 일반 POST 요청 예:
// form의 기본 데이터 전송 형식은 "application/x-www-form-urlencoded"이다.
// 즉 "이름=값&이름=값" 형태로 전송한다.
// 다음 요청 프로토콜에서 "Content-Type" 헤더를 확인해 보라!
//
// POST /eomcs-java-web/ex04/s3 HTTP/1.1
// Host: 192.168.1.10:9999
// Content-Length: 50
// Pragma: no-cache
// Cache-Control: no-cache
// Origin: http://192.168.1.10:9999
// Upgrade-Insecure-Requests: 1
// Content-Type: application/x-www-form-urlencoded
// User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like
// Gecko) Chrome/80.0.3987.149 Safari/537.36
// Accept:
// text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
// Referer: http://192.168.1.10:9999/eomcs-java-web/ex04/test03.html
// Accept-Encoding: gzip, deflate
// Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,cs;q=0.5
// Connection: keep-alive
//
// name=ABC%EA%B0%80%EA%B0%81&age=20&photo=actors.jpg
//
// 3) 멀티파트 POST 요청 예:
//
// POST /eomcs-java-web/ex04/s3 HTTP/1.1
// Host: 192.168.1.10:9999
// Content-Length: 248900
// Pragma: no-cache
// Cache-Control: no-cache
// Origin: http://192.168.1.10:9999
// Upgrade-Insecure-Requests: 1
// Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryT1G23U6fYMK0zZxx
// User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like
// Gecko) Chrome/80.0.3987.149 Safari/537.36
// Accept:
// text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
// Referer: http://192.168.1.10:9999/eomcs-java-web/ex04/test03.html
// Accept-Encoding: gzip, deflate
// Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,cs;q=0.5
// Connection: keep-alive
//
// ------WebKitFormBoundaryT1G23U6fYMK0zZxx
// Content-Disposition: form-data; name="name"
//
// AB가각
// ------WebKitFormBoundaryT1G23U6fYMK0zZxx
// Content-Disposition: form-data; name="age"
//
// 20
// ------WebKitFormBoundaryT1G23U6fYMK0zZxx
// Content-Disposition: form-data; name="photo"; filename="actors.jpg"
// Content-Type: image/jpeg
//
// 바이너리데이터...
// ------WebKitFormBoundaryT1G23U6fYMK0zZxx--

