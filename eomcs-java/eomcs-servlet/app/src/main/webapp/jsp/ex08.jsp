<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex08</title>
</head>
<body>
<h1>지시문(directive element) - include</h1>
<%@ include file="./ex08_header.txt"%>
<p>테스트</p>
<%@ include file="./ex08_footer.txt" %>
</body>
</html>
<%-- 
include 지시문
1) file="JSP에 포함시킬 파일 경로"
   - 지정한 파일을 JSP로 포함시킨 후에 자바 서블릿 클래스를 생성한다.
     자동 생성된 자바 서블릿 클래스의 소스를 확인해보라!
   - 따라서 일반 텍스트 파일이면 된다. JSP 파일일 필요가 없다.
   - RequestDispatcher의 include()와 다르다.
   - 비록 JSP 파일이 아니더라도 다음을 선언하여 해당 파일의 문자집합을 지정해야 한다. 
       <%@ page pageEncoding="UTF-8"%>
     JSP 엔진이 해당 파일의 내용을 가져올 때 pageEncoding에 지정된 문자집합으로
     내용을 인식한다.
     또한 JSP 엔진은 <%@ page ...%>는 참고만 할 뿐 가져오지는 않는다. 
     
--%>








