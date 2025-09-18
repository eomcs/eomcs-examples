<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex19</title>
</head>
<body>
<h1>JSP 액션 태그 - jsp:include</h1>

<jsp:include page="ex19_header.jsp"/>
<%-- 자바 코드:
RequestDispatcher rd = request.getRequestDispatcher("/ex19_header.jsp");
rd.include(request, response):
--%>

<p>테스트</p>

<jsp:include page="ex19_footer.jsp"/>
</body>
</html>
<%--
jsp:include
=> 다른 페이지로 실행을 위임한 후 그 실행 결과를 포함시킬 때 사용한다.
   실행이 끝난 후 제어권이 되돌아 온다.
=> 따라서 page 속성에 지정하는 URL은 서블릿/JSP 이어야 한다.
=> RequestDispatcher.include() 코드를 생성한다.

jsp:forward
=> 다른 페이지로 실행을 위임할 때 사용한다.
   제어권이 넘어가면 되돌아 오지 않는다.
=> 따라서 page 속성에 지정하는 URL은 서블릿/JSP 이어야 한다.
=> RequestDispatcher.forward() 코드를 생성한다.

 --%>






