<%@page import="com.eomcs.web.vo.Member"%>
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
<h1>EL - 일반 객체에서 값 꺼내기</h1>
<%
Member member = new Member();
member.setNo(100);
member.setName("홍길동");
member.setEmail("hong@test.com");
member.setTel("1111-2222");

pageContext.setAttribute("member", member);
%>

${member.getNo()}<br>
${member.no}<br>
${member["no"]}<br>
${member['no']}<br>

</body>
</html>












