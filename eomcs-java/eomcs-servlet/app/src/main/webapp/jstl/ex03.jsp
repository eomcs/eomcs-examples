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
<h1>JSTL - c:set</h1>
<pre>
- 보관소에 값을 저장한다.
</pre>
<%
//request.setAttribute("name1", "홍길동");
%>
<c:set scope="request" var="name1" value="홍길동2"/>
1: ${requestScope.name1}<br>
2: ${pageScope.name1}<br>
3: ${name1}<br> 
<hr>

<%--
scope을 생략하면 기본이 PageContext 이다.
 --%>
<c:set var="name2" value="임꺽정"/>
1: ${requestScope.name2}<br>
2: ${pageScope.name2}<br>
3: ${name2}<br>
<hr>

<%-- 
  시작 태그와 끝 태그 사이(content)에 값을 넣을 수 있다.
--%>
<c:set var="name3">유관순</c:set>
1: ${requestScope.name3}<br>
2: ${pageScope.name3}<br>
3: ${name3}<br>

<h2>객체의 프로퍼티 값 설정하기</h2>
<jsp:useBean id="m1" class="com.eomcs.web.vo.Member"/>
<%--
Member m1 = (Member) pageContext.getAttribute("m1");
if (m1 == null) {
  m1 = new Member();
  pageContext.setAttribute("m1", m1);
}
 --%>
<jsp:setProperty name="m1" property="no" value="100"/>
<%-- 
m1.setNo(100);
--%>

<c:set target="${pageScope.m1}" property="email" value="hong@test.com"/>
<%--
Object obj = pageContext.getAttribute("m1");
Method m = obj.getClass().getMethod("setEmail", String.class);
m.invoke(obj, "hong@test.com");

=> m1.setEmail("hong@test.com");
--%>

${pageScope.m1.no}<br>
${pageScope.m1.email}<br>
</body>
</html>












