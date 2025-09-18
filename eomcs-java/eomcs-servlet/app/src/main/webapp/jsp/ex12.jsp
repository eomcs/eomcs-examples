<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex12</title>
</head>
<body>
<h1>JSP 액션 태그 - jsp:useBean (계속)</h1>
<%-- 보관소에 저장된 객체 꺼내기 --%>
<%
application.setAttribute("s1", "홍길동");
session.setAttribute("s2", "임꺽정");
request.setAttribute("s3", "안중근");
pageContext.setAttribute("s4", "윤봉길");
%>

<jsp:useBean id="s1" type="java.lang.String" scope="application"/>
<%-- 자바코드로 표현해보면,
  String s1 = (String)application.getAttribute("s1");
  if (s1 == null) {
    throw new Exception("..."); // 해당 이름으로 저장된 객체가 없다면 예외를 발생시킨다.
  }
 --%>
 
<jsp:useBean id="s2" type="java.lang.String" scope="session"/>
<%-- String s2 = (String)session.getAttribute("s2"); --%>

<jsp:useBean id="s3" type="java.lang.String" scope="request"/>
<%-- String s3 = (String)request.getAttribute("s3"); --%>

<jsp:useBean id="s4" type="java.lang.String" scope="page"/>
<%-- String s4 = (String)pageContext.getAttribute("s4"); --%>

<%=s1%><br>
<%=s2%><br>
<%=s3%><br>
<%=s4%><br>

<%-- 보관소에 없는 객체를 꺼내려 하면 예외 발생! --%>
<%-- 
<jsp:useBean id="s5" type="java.lang.String" scope="page"/>
--%>

</body>
</html>
<%--
jsp:useBean 문법:

<jsp:useBean id="레퍼런스명" type="레퍼런스타입" scope="보관소"/>
=> id
   - 보관소에서 값을 꺼낼 때 사용하는 이름이다.
   - 또한 꺼내 값을 저장하는 변수의 이름으로 사용된다.
=> type 
   - 보관소에서 꺼낸 객체의 타입을 지정할 때 사용한다.
=> scope
   - 값을 꺼내는 보관소를 가리킨다.
=> 예) 
태그: 
  <jsp:useBean id="s5" type="java.lang.String" scope="page"/>
자바코드: 
  String s5 = (String) pageContext.getAttribute("s5");
  if (s5 == null) {
    throw new Exception("객체 꺼내기 오류");
  }
 --%>






