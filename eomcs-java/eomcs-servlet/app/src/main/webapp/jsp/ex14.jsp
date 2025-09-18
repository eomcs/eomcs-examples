<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex14</title>
</head>
<body>
<h1>JSP 액션 태그 - jsp:useBean (계속)</h1>
<%-- 보관소에 저장된 객체 꺼내기 --%>
<%
ArrayList<String> names = new ArrayList<>();
names.add("홍길동");
names.add("임꺽정");
names.add("안중근");
names.add("윤봉길");

pageContext.setAttribute("list", names);
%>

<jsp:useBean id="list" type="java.util.List<String>" scope="page"/>
<%-- type에는 인터페이스를 지정할 수 있다.
  java.util.List<String> list = 
    (java.util.List<String>) pageContext.getAttribute("list");
 --%>

<%
for (String n : list) {
  out.println(n + "<br>");
}
%>

</body>
</html>
<%--
jsp:useBean (계속)
1) type 속성 
   - 보관소에서 꺼낸 객체의 타입을 지정할 때 사용한다.

 --%>






