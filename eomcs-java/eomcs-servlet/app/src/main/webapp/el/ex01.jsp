<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL</title>
</head>
<body> 
<h1>EL 표기법</h1>
<%-- 
- EL(Expression Language)은 dot(.)와 대괄호([]) 등을 사용하여 객체의 프로퍼티나,
  리스트, 셋, 맵 객체의 값을 꺼내고 설정하는 문법이다.
  특히 값을 꺼낼 때는 OGNL 표기법을 사용한다.
  
- OGNL(Object Graph Navigation Language)?
  - Java 언어에서 객체 그래프를 탐색하고 조작하기 위한 표현 언어다. 
  - 즉 객체의 프로퍼티 값을 가리킬 때 사용하는 문법이다.
    파일의 경로처럼 객체에 포함된 객체를 탐색하여 값을 쉽게 조회할 수 있다.

- 객체 그래프(Object Graph)
  - 객체들 간에 관계를 나타내는 그래프 구조다.
  - 객체 그래프는 복잡한 데이터 구조를 표현하고 탐색하는 데 유용하며, 
    객체 간의 다양한 관계를 표현할 수 있다.
  - 객체 그래프를 사용하면 데이터를 계층적으로 구조화하고 관리할 수 있으며, 
    객체 간의 연결성을 효율적으로 관리할 수 있다.
  
- 문법
    ${ 객체명.프로퍼티명.프로퍼티명.프로퍼티명 }
    ${ 객체명["프로퍼티명"]["프로퍼티명"]["프로퍼티명"] }
    
- EL에서 사용할 수 있는 빌트인 객체?
    pageContext 
      - JSP의 PageContext 객체
    servletContext 
      - ${ pageContext.servletContext.프로퍼티명 }
        자바코드 => pageContext.getServletContext().get프로퍼티()
    session 
      - ${ pageContext.session.프로퍼티명 }
        예) $ { pageContext.session.name }
        => pageContext.getSession().getName();
                    
    request 
      - ${ pageContext.request.프로퍼티명 }
    response
    
    param 
      - ${ param.파라미터명 }
        => request.getParameter("파라미터명");
    paramValues 
      - ${ paramValues.파라미터명 }
        => request.getParameterValues("파라미터명");
    header 
      - ${ header.헤더명 }
        => request.getHeader("헤더명");
    headerValues 
      - ${ headerValues.헤더명 }
        => request.getHeaders("헤더명");
    cookie 
      - ${ cookie.쿠키명 }
      - ${ cookie.쿠키명.name }
      - ${ cookie.쿠키명.value }
    initParam 
      - ${ initParam.파라미터명 }
    
    => 보관소에서 값을 꺼내는 문법
    pageScope 
      - ${ pageScope.객체이름 }
        => pageContext.getAttribute("객체이름");
    requestScope 
      - ${ requestScope.객체이름 }
        => request.getAttribute("객체이름");
    sessionScope 
      - ${ sessionScope.객체이름 }
        => session.getAttribute("객체이름");
        예) ${ sessionScope.name }
        => session.getAttribute("name");
    applicationScope 
      - ${ applicationScope.객체이름 }
        => application.getAttribute("객체이름");
--%>

</body>
</html>












