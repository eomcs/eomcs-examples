<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL</title>
</head>
<body>
<h1>JSTL - c:choose</h1>
<pre>
- 다중 조건문을 만든다.
- 자바의 switch와 유사하다.
</pre>

<c:set var="name" value="홍길동"/>
<c:set var="age" value="76"/>
<%--
pageContext.setAttribute("name", "홍길동");
pageContext.setAttribute("age", "16");
--%>

<c:choose>
    <c:when test="${age < 19}">
        <p>미성년입니다.</p>
    </c:when>
    <c:when test="${age >= 19 and age < 65}">
        <p>성년입니다.</p>
    </c:when>
    <c:otherwise>
        <p>노인입니다.</p>
    </c:otherwise>
</c:choose>


</body>
</html>












