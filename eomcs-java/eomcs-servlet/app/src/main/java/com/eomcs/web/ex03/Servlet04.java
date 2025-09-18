// 클라이언트로 출력하기 - 바이너리 데이터 출력하기
package com.eomcs.web.ex03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex03/s4")
public class Servlet04 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // photo.jpeg 파일의 실제 경로 알아내기
    // 1) 서블릿의 환경 정보를 다루는 객체를 먼저 얻는다.
    ServletContext ctx = req.getServletContext();

    // 2) ServletContext를 통해 웹 자원의 실제 경로를 알아낸다.
    // => getRealPath(현재 웹 애플리케이션의 파일 경로) : 실제 전체 경로를 리턴한다.
    String path = ctx.getRealPath("/photo.jpeg");
    System.out.println(path);

    FileInputStream in = new FileInputStream(path);

    // 바이너리를 출력할 때 MIME 타입을 지정해야 웹 브라우저가 제대로 출력할 수 있다.
    // => 웹 브라우저가 모르는 형식을 지정하면 웹 브라우저는 처리하지 못하기 때문에
    //    그냥 다운로드 대화상자를 띄운다.
    res.setContentType("image/jpeg");

    OutputStream out = res.getOutputStream();

    int b;
    while ((b = in.read()) != -1) {
      out.write(b);
    }

    out.flush(); // 버퍼 데코레이터에 보관된 데이터를 클라이언트로 방출한다.
    out.close();
    in.close();
  }
}


