package com.eomcs.web.ex05;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// MyHttpServlet 클래스를 사용하여 서블릿을 만드는 것도 편하지만,
// 여기에다가 HTTP 요청 방식에 따라 메서드를 구분해 놓는다면
// 서브 클래스를 만들기가 더 편리할 것이다.
//
@SuppressWarnings("serial")
public abstract class MyHttpServlet2 extends GenericServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    // 서블릿 컨테이너가 이 메서드를 호출하면

    // => 파라미터 값을 원래의 타입으로 변환한다.
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    // => 오버로딩한 service()를 호출한다.
    this.service(request, response);
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // HTTP 요청 방식에 따라 메서드를 분리하여 호출한다.
    String httpMethod = request.getMethod();

    switch (httpMethod) {
      case "GET":
        doGet(request, response);
        return;
      case "POST":
        doPost(request, response);
        return;
      case "PUT":
        doPut(request, response);
        return;
      case "HEAD":
        doHead(request, response);
        return;
      default:
        error(request, response);
    }
  }


  // 서브 클래스에서 오버라이딩 할 메서드라면 private으로 선언하지 말라.
  // => 서브 클래스에서 이 메서드를 오버라이딩 하지 않으면 오류를 출력하도록 한다.
  // => 즉 클라이언트의 이런 요청을 처리하라면 서브 클래스에서 그 요청 방식에 해당하는 메서드를
  // 오버라이딩 하면 된다.
  // => 클라이언트 요청이 들어오면
  // 서블릿 컨테이너
  // ==> service(ServletRequest,ServletResponse) 호출
  //     ==> service(HttpServletRequest,HttpServletResponse) 호출
  //         ==> doXxx(HttpServletRequest,HttpServletResponse) 호출
  //
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    error(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    error(request, response);
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    error(request, response);
  }

  protected void doHead(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    error(request, response);
  }

  private void error(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.println("해당 HTTP 요청을 처리할 수 없습니다.");
  }



}


