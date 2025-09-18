<%@page import="java.util.ArrayList"%>
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
<h1>EL - List 객체에서 값 꺼내기</h1>
<%
ArrayList<String> nameList = new ArrayList<>();
nameList.add("김구");
nameList.add("안중근");
nameList.add("윤봉길");

pageContext.setAttribute("names", nameList);
%>
${names[0]}<br>
${names[1]}<br>
${names[2]}<br>
${names[3]}<br>

<%-- 보관소가 아닌 로컬 변수는 EL에서 사용할 수 없다. --%>
${nameList[0]}<br>

</body>
</html>












