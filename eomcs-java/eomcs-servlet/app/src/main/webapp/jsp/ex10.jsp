<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" 
    isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex10</title>
</head>
<body>
<h1>JSP 빌트인 객체 - JSP에서 기본으로 사용할 수 있는 객체</h1>
<%
// JSP에서 기본으로 준비한 변수를 사용할 수 있다.
request.setAttribute("aaa", "okok");
session.setAttribute("bbb", "nono");
application.setAttribute("ccc", "haha");
out.println("okok");
%>
</body>
</html>
<%--
JSP 빌트인 객체
- JSP를 가지고 서블릿 클래스를 만들 때 _jspService() 메서드에서 기본으로 준비하는 객체
- JSP 엔진은 반드시 다음과 같은 이름으로 레퍼런스를 선언해야 한다.
  즉 서블릿 컨테이너(ex: 톰캣, jetty, resin 등)에 상관없이 이름이 같다.

1) request - HttpServletRequest => _jspService() 파라미터이다.
2) response - HttpServletResponse => _jspService() 파라미터이다.
3) pageContext - PageContext => _jspService()의 로컬 변수이다.
4) session - HttpSession => _jspService()의 로컬 변수이다.
5) application - ServletContext => _jspService()의 로컬 변수이다.
6) config - ServletConfig => _jspService()의 로컬 변수이다.
7) out - JspWriter => _jspService()의 로컬 변수이다.
8) page - 서블릿 객체를 가리킨다. 즉 this 이다. => _jspService()의 로컬 변수이다.
9) exception - Throwable => _jspService()의 로컬 변수이다.
   - 이 변수는 JSP 페이지가 <%@ page isErrorPage="true"%>로 설정되었을 때만 존재한다.
   - 주로 오류가 발생되었을 때 실행되는 JSP 페이지인 경우 위 설정을 붙인다. 
 --%>







