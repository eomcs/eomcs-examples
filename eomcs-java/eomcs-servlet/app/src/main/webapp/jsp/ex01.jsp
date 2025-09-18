<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex01</title>
</head>
<body>
<h1>JSP 구동 원리</h1>
<pre>
1) 웹브라우저 ==> 서블릿 컨테이너 
   - JSP 실행 요청
     예) http://localhost:8080/java-web/jsp/ex01.jsp
2) 서블릿 컨테이너가 실행
   2.1 JSP의 서블릿 객체를 찾는다
   2.2 있으면,
     2.2.1 그 서블릿 객체를 호출한다. service() ---> _jspService()
   2.3 없으면,
     2.3.1 JSP 엔진을 이용하여 JSP 파일을 가지고 서블릿 자바 소스 파일을 생성한다.
     2.3.2 자바 컴파일러를 이용하여 소스 파일을 컴파일 한다.
     2.3.3 서블릿 객체를 생성한다. - init() 호출 ---> jspInit()
     2.3.4 그 서블릿 객체를 호출한다. - service() 호출 ---> _jspService()
   2.4 JSP 파일이 변경된 상태라면,
     2.4.1 다시 "2.3"을 반복한다.
3) 서블릿 컨테이너 ==> 웹브라우저 
   - 서블릿 실행 결과를 응답

JSP 파일을 가지고 생성한 서블릿 소스(.java)와 클래스 파일(.class)의 위치
- org.eclipse.wst.server.core/tmpx/work/...

정리!
- JSP 파일은 Python이나 PHP 처럼 직접 그 스크립트가 인터프리팅 되는 것이 아니다.
- JSP 엔진의 역할은 JSP 파일을 분석하여 서블릿 클래스를 생성하는 것이다.
- 즉 JSP 파일이 직접 실행되지 않는다!

JSP 
- 자바 서블릿 클래스를 만드는 재료로 사용된다.
- 그래서 서블릿 클래스를 만드는 "틀"이라 해서 "템플릿(template)"이라 부른다.
- JSP를 템플릿 기술이라 부르기도 한다.

JSP 공부법
- JSP를 작성할 때 사용하는 특정 태그들이 어떤 자바 코드를 생성하는지 이해하는 것이 중요하다.

JSP 실행을 요청하기
- JSP 파일이 있는 위치를 지정한다.
  예) http://localhost:8080/java-web/jsp/ex01.jsp

JSP를 변경한 후 실행을 요청하기
- 그냥 JSP 파일이 있는 위치를 지정하면 된다.
- 위에서 설명한대로 JSP 구동 원리에 따라 동작된다.

JSP 엔진이 서블릿을 만들 때 지켜야할 규칙:
- JSP 파일을 가지고 서블릿을 만들 때 HttpJspPage를 구현해야 한다.
- 클래스 계층도
  Servlet
    - init(ServletConfig):void
    - destroy():void
    - service(ServletRequest, ServletResponse):void
    - getServletInfo():String
    - getServletConfig():ServletConfig
    +---> JspPage
      - jspInit():void
      - jspDestroy():void
      +---> HttpJspPage
        - _jspService(HttpServletRequest, HttpServletResponse):void
        
</pre>
</body>
</html>






