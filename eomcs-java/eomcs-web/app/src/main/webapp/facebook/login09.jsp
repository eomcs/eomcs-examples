<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="facebookUrl" value="https://graph.facebook.com/v9.0/me">
    <c:param name="access_token" value="${param.accessToken}"/>
    <c:param name="fields" value="id,name,email,gender"/>
</c:url>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:import url="${facebookUrl}"/>
</body>
</html>









