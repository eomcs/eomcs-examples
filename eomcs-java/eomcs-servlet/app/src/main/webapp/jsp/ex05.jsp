<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] names = {"홍길동", "임꺽정", "유관순"};
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex05</title>
</head>
<body>
<h1>표현식(expression element)</h1>
<ul>
<%for (String name : names) {%>
<li><%=name%></li>
<%}%>
</ul>
</body>
</html>
<%--
용어 정리
1) 문장(statement)
   if () {...}
   else {...}
   for () {...}
   int a = 100;
   
2) 표현식(expression)
   - 문장 중에 값을 리턴하는 문장
   a * b        <---- 곱하기 결과 리턴
   (..)? a : b  <---- 조건에 따라 a 또는 b 값 리턴
   plus(10, 20) <---- plus()의 실행 값 리턴
   
   out.println("okok")  <---- 이 문장은 값을 리턴하지 않는다. 따라서 표현식이 아니다. 
 
표현식(expression element)
문법:
  <%= 표현식 %>
자바 코드:
  out.print(표현식)
  또는
  out.write(표현식)
--%>
