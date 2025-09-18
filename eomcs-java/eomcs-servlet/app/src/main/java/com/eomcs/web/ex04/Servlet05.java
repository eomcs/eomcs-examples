// 멀티파트 파일 업로드 처리하기 - Servlet 3.0의 기본 라이브러리 사용
package com.eomcs.web.ex04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

// 멀티파트 형식의 데이터를 처리할 서블릿으로 선언하라.
// 1) DD 파일(web.xml)에 설정하기
// <servlet>
//   <servlet-name>ex04.Servlet05</servlet-name>
//   <servlet-class>com.eomcs.web.ex04.Servlet05</servlet-class>
//   <multipart-config>
//     <max-file-size>10000000</max-file-size>
//   </multipart-config>
// </servlet>
//
// 2) 애노테이션으로 설정하기
//
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/ex04/s5")
public class Servlet05 extends GenericServlet {

  private static final long serialVersionUID = 1L;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    this.uploadDir = this.getServletContext().getRealPath("/upload");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // 테스트
    // - http://localhost:8080/java-web/ex04/test05.html 실행
    //

    // Servlet 3.0의 멀티파트 처리 기능을 이용할 때는
    // 원래 하던대로 클라이언트가 보낸 데이터의 인코딩을 지정하라.
    req.setCharacterEncoding("UTF-8");

    // 파라미터로 받은 ServletRequest를 원래의 타입으로 변환하라.
    HttpServletRequest httpReq = (HttpServletRequest) req;

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("<html>");
    out.println("<head><title>servlet04</title></head>");
    out.println("<body><h1>파일 업로드 결과</h1>");

    // 일반 폼 데이터를 꺼낼 때는 원래 하던 방식대로 값을 꺼낸다.
    out.printf("이름=%s<br>\n", httpReq.getParameter("name"));
    out.printf("나이=%s<br>\n", httpReq.getParameter("age"));

    // 파일 데이터는 getPart()를 이용한다.
    Part photoPart = httpReq.getPart("photo");
    if (photoPart.getSize() > 0) {
      // 파일을 선택해서 업로드 했다면,
      String filename = UUID.randomUUID().toString();
      photoPart.write(this.uploadDir + "/" + filename);
      out.printf("원래 파일명: %s<br>\n", photoPart.getSubmittedFileName());
      out.printf("사진=%s<br>\n", filename);
      out.printf("<img src='../upload/%s' height='50'><br>\n", filename);
      out.printf("<img src='../upload/%s'><br>\n", filename);
    }
    out.println("</body></html>");
  }
}
