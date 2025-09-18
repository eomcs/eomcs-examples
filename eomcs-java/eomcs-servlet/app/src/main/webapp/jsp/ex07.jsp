<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.net.Socket"
    import="java.net.ServerSocket"
    import="java.util.List,java.util.Map,java.util.Set"
    trimDirectiveWhitespaces="true"
    buffer="8kb"
    autoFlush="false"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex07</title>
</head>
<body>
<h1>지시문(directive element)</h1>
<%
for (int i = 0; i < 5000; i++) {
  out.print(". "); 
}
%>
<p>출력완료!</p>
</body>
</html>
<%--
지시문
1) page
  - 서블릿 실행과 관련하여 특정 기능을 설정한다.
2) include
  - 다른 파일의 내용을 복사해온다.
3) taglib
  - JSTL 등 외부에서 정의한 태그 정보를 가져온다.
  
page 지시문
1) language="java"
   - JSP 페이지에서 코드를 작성할 때 사용할 언어를 지정한다.
   - 즉 <% 코드 %>, <%= 표현식 %>, <%! 코드 %> 태그에 코드를 작성할 때 사용할 언어이다.
   - 원래는 다양한 언어를 사용할 경우를 고려해 설계되었지만,
     현재는 java 언어만 사용 가능하다.
   - 이 속성은 생략해도 된다.

2) contentType="text/html; charset=UTF-8"
   - 다음 자바 코드를 생성한다.
       response.setContentType("text/html; charset=UTF-8");  

3) pageEncoding="UTF-8"
   - JSP 파일의 인코딩을 설정한다.
   - JSP 파일을 저장할 때 UTF-8로 저장한다면 위와 같이 선언하라.
   - 생략한다면 에디터에 설정된 문자집합으로 인코딩할 것이다.

4) import="java.net.Socket"
   - 자바의 import 문을 생성한다.
   - 사용법
     import="java.net.Socket"
       => 자바 코드: 
          import java.net.Socket;
     import="java.net.Socket,java.net.ServerSocket,java.util.List"
       => 자바 코드:
          import java.net.Socket;
          import java.net.Serversocket;
          import java.util.List;
   - 한 개의 page 지시문에 여러 개의 import를 작성할 수 있다.
   - 여러 개의 page 지시문을 작성할 수 있다.

5) trimDirectiveWhitespaces="true"
   - 지시문 끝에 줄바꿈 코드를 무시하고 싶을 때 사용한다.
   
6) buffer="8kb"
   - 출력 버퍼의 크기를 변경할 때 사용한다. 
   - 지정하지 않으면 기본이 8kb 이다.
   - 출력 내용이 버퍼의 크기를 넘으면 예외가 발생한다.
     서블릿에서는 자동으로 출력하였다. 
     그러나 JSP는 예외가 발생한다.

7) autoFlush="true"
   - 출력 버퍼가 찼을 때 자동으로 출력한다.
   - 기본은 true 이다.
   - false로 설정하면 출력 버퍼가 찼을 때 예외가 발생한다.

8) errorPage="URL"
   - JSP를 실행하는 중에 오류가 발생했을 때 포워딩할 URL을 지정한다.

9) isErrorPage="true|false"
   - JSP 페이지가 예외를 처리하는 페이지인지 지정한다.
   - true로 설정하면, 포워딩할 때 받은 예외 객체를 사용할 수 있도록 
     Throwable 타입의 exception 빌트인 객체가 추가된다.
--%>








