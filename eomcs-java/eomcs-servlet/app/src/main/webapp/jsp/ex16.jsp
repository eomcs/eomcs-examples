<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex16</title>
</head>
<body>
<h1>JSP 액션 태그 - jsp:useBean (계속)</h1>

<%-- type 속성 대신에 class 속성을 사용하면 
     id로 지정한 객체를 찾지 못했을 때 해당 객체를 만들고,
     그 id로 보관소에 저장한다. 
     단 class 속성에는 generic 문법을 사용할 수 없다.
     또한 보관소에 객체가 없을 때 생성해야 하기 때문에 
     class 속성에는 인터페이스를 설정할 수 없다. --%>
<jsp:useBean id="list" class="java.util.ArrayList" scope="page"/>
<%-- 자바코드로 표현해보면,
  java.util.ArrayList list = 
     (java.util.ArrayList) pageContext.getAttribute("list");
  if (list == null) {
    list = new java.util.ArrayList();
    pageContext.setAttribute("list", list);
  }
 --%>

<%
// 제네릭 문법을 사용하지 못했기 때문에 list의 타입은 java.util.ArrayList이다.
// 따라서 for 문에서 항목의 타입을 지정할 때 String으로 지정할 수 없다.
out.println(list.size() + "<br>");
for (Object n : list) {
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






