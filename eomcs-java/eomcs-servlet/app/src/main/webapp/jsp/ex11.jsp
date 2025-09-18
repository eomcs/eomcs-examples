<%@page import="java.sql.Date"%>
<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ page import="com.eomcs.web.vo.Board"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex11</title>
</head>
<body>
<h1>JSP 액션 태그 - jsp:useBean, jsp:setProperty</h1>
<%-- bitcamp.vo.Board 객체 생성하기 --%>
<jsp:useBean id="b1" class="com.eomcs.web.vo.Board" scope="page"/>
<%-- 
위 태그의 자바 코드:
com.eomcs.web.vo.Board b1 = (com.eomcs.web.vo.Board) pageContext.getAttribute("b1");
if (b1 == null) {
  b1 = new com.eomcs.web.vo.Board();
  pageContext.setAttribute("b1", b1);
}
--%>


<%-- scope을 생략하면 기본이 page(PageContext)이다. --%>
<jsp:useBean id="b2" class="com.eomcs.web.vo.Board"/>

<jsp:useBean id="b3" class="com.eomcs.web.vo.Board"/>

<%-- 객체의 setter 메서드를 호출하기 --%>
<jsp:setProperty name="b3" property="no" value="100"/>
<jsp:setProperty name="b3" property="contents" value="내용입니다."/>
<jsp:setProperty name="b3" property="viewCount" value="88"/>
<%-- 단, 자바 객체의 프로퍼티 타입이 자바 원시 타입과 문자열인 경우 가능하다.
     다른 타입이라면 따로 처리해야 한다. --%>
<%-- 
<jsp:setProperty name="b3" property="createdDate" value="2019-4-8"/>
--%>

<%=b1%> => out.print(b1);<br>  
<%=b2%> => out.print(b2);<br>
<%=b3%> => out.print(b3);<br> 
<%=b3.toString()%> => out.print(b3.toString());<br>

</body>
</html>
<%--
JSP 액션 태그
=> JSP에서 기본으로 제공하는 JSP 전용 태그
=> 따로 taglib를 사용하여 라이브러리를 선언할 필요가 없다.
=> JSP에서 기본으로 제공하기 때문에 그대로 사용하면 된다.
=> 네임스페이스 이름은 jsp 이다.
   <jsp:태그명 ..../>

jsp:useBean
=> JSP에서 사용할 객체를 생성할 때 사용할 수 있다.
=> 또는 보관소(ServletContext, HttpSession, ServletRequest, PageContext)에 
   저장된 객체를 꺼낼 때도 사용한다.
=> 사용법
     <jsp:useBean scope="보관소명" id="객체명" class="클래스명"/>   
=> 주요 속성
   scope
     - 객체를 꺼내거나 생성된 객체를 저장할 보관소 이름
     - 다음 4개의 값 중 한 개를 지정할 수 있다. 값을 지정하지 않으면 기본이 "page" 이다.
       application(ServletContext), session(HttpSession),
       request(ServletRequest), page(PageContext)
   id 
     - 객체를 꺼내거나 저장할 때 사용할 이름
   class
     - 보관소에서 객체를 찾을 수 없을 때 생성할 객체의 클래스명
     - 반드시 패키지 이름을 포함해 클래스명(fully-qualified name; FQName)을 지정해야 한다.
       <%@ page import="..."%> 를 선언해도 소용없다.
     - 객체를 꺼내는 경우 레퍼런스의 타입으로도 사용된다.
     - 객체를 생성할 때도 사용할 수 있기 때문에 반드시 콘크리트(concrete) 클래스명이어야 한다.
       추상 클래스와 인터페이스는 객체를 생성할 수 없기 때문에 안된다.
 --%>







