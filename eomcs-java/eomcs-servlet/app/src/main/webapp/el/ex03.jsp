<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL</title>
</head>
<body>
<h1>EL - 보관소에 값 꺼내기 II</h1>
<pre>
- 보관소의 이름을 지정하지 않으면 다음 순서로 값을 찾는다.
    pageScope ==> requestScope ==> sessionScope ==> applicationScope
    
- 보관소에 저장된 값을 찾지 못하면 빈 문자열을 리턴한다.
</pre> 
<%
// 세션이나 서블릿 컨텍스트 보관소에 저장된 데이터를 초기화시킨다.
session.removeAttribute("name");
application.removeAttribute("name");

//pageContext.setAttribute("name", "홍길동");
//request.setAttribute("name", "임꺽정");
//session.setAttribute("name", "유관순");
//application.setAttribute("name", "안중근");
%>

<h2>보관소에서 값 꺼내기 : ${name}</h2> 
PageContext 보관소 : ${pageScope.name}<br>
ServletRequest 보관소 : ${requestScope.name}<br>
HttpSession  보관소 : ${sessionScope.name}<br>
ServletContext 보관소 : ${applicationScope.name}<br>
</body>
</html>












