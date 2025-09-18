<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL</title>
</head>
<body>
<h1>JSTL 개요</h1>
<%-- 
- JSTL(JSP Standard Tag Library)? 
  - JSP 확장 태그이다.
  - 기본으로 제공하지 않는다.
  - JSTL API를 구현한 외부 라이브러리를 가져와서 사용해야 한다.
- JSTL 라이브러리 가져오기
  - mvnrepository.com 에서 JSTL 검색하여 라이브러리를 정보를 알아낸다.
  - build.gradle 파일의 dependencies {} 블록에 추가한다.
  - 'gradle eclipse' 실행하여 이클립스 설정 파일을 갱신한다.
  - 이클립스 프로젝트를 리프래시 한다.
- JSTL 라이브러리 모듈
  - Core(c) : http://java.sun.com/jsp/jstl/core
  - XML(x) : http://java.sun.com/jsp/jstl/xml
  - I18N(fmt) : http://java.sun.com/jsp/jstl/fmt
  - Database(sql) : http://java.sun.com/jsp/jstl/sql
  - Functions(fn) : http://java.sun.com/jsp/jstl/functions
- JSP 페이지에서 JSTL 라이브러리의 모듈 사용하기
  - JSTL 모듈의 네임스페이스를 가져온다.
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  - JSTL 태그 사용
      <접두어명:태그명 속성="값" 속성="값"/> 

* I18N(Internationalization 의 약자)
=> 프로그램을 짤 때 여러 언어를 고려해서 코딩하는 것을 말한다.
=> 특히 화면에서 버튼에 제목이나 라벨을 출력할 때 특정 언어로 고정된 값을 출력하지 않고,
   외부 파일(예: label_ko_KR.properties)에서 읽어 온 값을 출력하도록 프로그래밍 하는 것.
   
* L10N(Localization 의 약자)
=> 특정 언어에 대해 라벨 텍스트를 담은 프로퍼티 파일(예: label_ko_KR.properties)을 
   작성하는 것을 말한다.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>오호라!!!</h2>

<%
out.println("<h2>오호라!!!</h2>");
%>

<%="<h2>오호라!!!</h2>"%>

${"<h2>오호라!!!</h2>"}

<c:out value="<h2>오호라!!!<h2>"/>

</body>
</html>












