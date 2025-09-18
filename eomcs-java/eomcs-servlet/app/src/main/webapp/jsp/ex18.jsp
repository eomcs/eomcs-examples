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

<%-- for ( : ) 문을 제대로 사용하려면 목록에 보관된 객체의 타입이 무엇인지 
     제네릭으로 지정해야 한다.
     그러나 class에서는 제네릭을 지정할 수 없다.
     해결책?
     - type과 class를 함께써라!
     - type으로 제네릭을 표현하고 class로 객체를 생성할 클래스를 지정하라! 
     --%>
<jsp:useBean id="list"
    type="java.util.List<String>"
    class="java.util.ArrayList" scope="page"/>
<%-- 자바코드로 표현해보면,
  java.util.List<String> list = 
     (java.util.List<String>) pageContext.getAttribute("list");
  if (list == null) {
    list = new java.util.ArrayList();
    pageContext.setAttribute("list", list);
  }
 --%>

<%
// type 속성에서 제레릭으로 String을 지정했기 때문에 항목의 타입을 String으로 선언할 수 있다.
//list.add(new Integer()); // 제네릭에 지정된 타입과 달라서 컴파일 오류!
list.add("홍길동");
list.add("임꺽정");
list.add("유관순");
list.add("안중근");

// ArrayList 에 제네릭을 적용하지 않으면 
// 다음과 같이 어떤 타입의 객체라도 저장할 수 있다.
// => 그러나 jsp:useBean 태그에서 type 속성을 설정하면 제네릭을 사용할 수 있다.
//list.add(new Integer(100));
//list.add(new com.eomcs.web.vo.Board());
 
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






