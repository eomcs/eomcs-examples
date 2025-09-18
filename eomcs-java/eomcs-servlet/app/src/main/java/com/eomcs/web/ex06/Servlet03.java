// 서블릿 초기화 파라미터 - 애노테이션으로 설정하기
package com.eomcs.web.ex06;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

// 서블릿이 사용할 값을 DD 설정으로 지정할 수 있다.
//
//@WebServlet(
//    value = "/ex06/s3",
//    loadOnStartup = 1,
//    initParams = {
//        @WebInitParam(name = "jdbc.driver", value = "org.mariadb.jdbc.Driver"),
//        @WebInitParam(name = "jdbc.url", value = "jdbc:mariadb://localhost/studydb"),
//        @WebInitParam(name = "jdbc.username", value = "study"),
//        @WebInitParam(name = "jdbc.password", value = "1111")})
@SuppressWarnings("serial")
public class Servlet03 extends HttpServlet {

  //  ServletConfig config;
  //
  //  @Override
  //  public void init(ServletConfig config) throws ServletException {
  //    // 서블릿 객체가 생성될 때 뭔가 준비하는 작업을 해야 한다면,
  //    // 보통 이 메서드를 오버라이딩 할 것이다.
  //    //
  //    // 이 메서드가 호출될 때 넘어오는 값(config)은 나중에 사용할 수 있도록
  //    // 보통 인스턴스 필드에 보관해 둔다.
  //    // 즉 getServletConfig()가 호출될 때 리턴하도록 다음과 같이 코드를 작성한다.
  //    this.config = config;
  //
  //    // 그런데 init()를 오버라이딩 할 때 마다 이렇게
  //    // config 객체를 인스턴스 필드에 저장하도록 코딩하는 것은 매우 번거롭다.
  //    //
  //    // 이런 불편함을 없애기 위해서 GenericServlet은
  //    // 미리 이 메서드에 해당 코드를 작성해 두었다.
  //    // 그리고 추가적으로 파라미터 값을 받지 않는 init()를 호출하도록
  //    // 구현하였다.
  //    //
  //    // 결론?
  //    // => 그러니 개발자는 서블릿 객체가 생성될 때 뭔가 작업을 수행하고 싶다면,
  //    //    이 메서드를 직접 오버라이딩 하지 말고,
  //    //    이 메서드가 호출하는 다른 init()를 오버라이딩 하라!
  //    //
  //    //
  //
  //  }


  @Override
  public void init() throws ServletException {
    System.out.println("/ex06/s3 ==> init()");
    // 이 객체가 생성될 때 DB에 연결한다고 가정하자!
    // DB에 연결하려면 JDBC Driver 이름과 JDBC URL, 사용자 아이디, 암호를
    // 알아야 한다.
    //
    // 그런데 다음과 같이 자바 소스 코드에 그 값을 직접 작성하면,
    // 나중에 DB 연결 정보가 바뀌었을 때
    // 이 소스를 변경하고 다시 컴파일 해야 하는 번거로움이 있다.
    // => 소스에 변할 수 있는 값을 작성하는 것은 바람직하지 않다.
    // => 보통 이렇게 값을 직접 작성하는 것을 "하드(hard) 코딩"이라 부른다.
    // String jdbcDriver = "org.mariadb.jdbc.Driver";
    // String jdbcUrl = "jdbc:mariadb://localhost:3306/studydb";
    // String username = "study";
    // String password = "1111";

    // 위의 코드처럼 언제든 변경될 수 있는 값을
    // 소스코드에 직접 작성하는 방식은 좋지 않다.
    // 해결책?
    // => 이런 변경 값들은 외부에 두는 것이 관리에 편하다.
    // => 값이 바뀌더라도 소스를 변경할 필요가 없다.
    // => 보통 DD 파일(web.xml)에 둔다.
    // => 다만 이 예제에서는 애노테이션으로도 설정할 수 있다는 것을
    //    보여주기 위해 서블릿 상단에 애노테이션으로 설정하였다.
    // => 예)
    //    @WebInitParam(name = "jdbc.driver", value = "org.mariadb.jdbc.Driver")
    // => 애노테이션에 설정된 값을 꺼내기 위해서는 ServletConfig 객체가 필요하다.
    //
    ServletConfig config = this.getServletConfig();

    // 이렇게 @WebInitParam()으로 설정된 값을
    // "서블릿 초기화 파라미터"라 부른다.
    String jdbcDriver = config.getInitParameter("jdbc.driver");
    String jdbcUrl = config.getInitParameter("jdbc.url");
    String username = config.getInitParameter("jdbc.username");
    String password = config.getInitParameter("jdbc.password");

    System.out.println(jdbcDriver);
    System.out.println(jdbcUrl);
    System.out.println(username);
    System.out.println(password);

  }
}

