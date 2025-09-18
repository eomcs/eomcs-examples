<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex06 </title>
</head>
<%!
// 다음과 같이 상속 받은 메서드를 오버라이딩 할 수 있다.
public void jspInit() {
  System.out.println("ex06.jsp의 jspInit()");
}

public void jspDestroy() {
  System.out.println("ex06.jsp의 jspDestroy()");
}
%>
<body>
<h1>선언부(declaration element)</h1>

100,000,000 입금 = <%=calculate(100000000)%>
</body>

<%!
double interest = 0.025; // 인스턴스 변수

private String calculate(long money) { // 인스턴스 메서드 
  return String.format("%.2f", money + (money * interest));
}
%>

<%!
{ // 인스턴스 블록
  System.out.println("ex06 인스턴스 생성!");
}

static { // 스태틱 블록
  System.out.println("ex06 클래스 로딩");
}
%>
</html>
<%--
선언부
- 클래스에 멤버를 추가할 때 사용한다.
- jspInit()나 jspDestroy()와 같은 메서드를 오버라이딩 할 때도 사용할 수 있다.
- 선언하는 위치는 상관없다. 
- 문법:
    <%! 멤버 선언 %>
- 자바 코드:
  class JSP클래스 {
    
    멤버선언
    
    void _jspService() {
      ...
    }
  }
--%>








