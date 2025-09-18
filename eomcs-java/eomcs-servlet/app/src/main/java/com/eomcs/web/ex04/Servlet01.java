// 클라이언트가 보낸 데이터 읽기 - GET 요청 데이터 읽기
package com.eomcs.web.ex04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex04/s1")
public class Servlet01 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // GET 요청
    // - 웹 브라우저에 URL을 입력한 후 엔터를 치면 GET 요청을 보낸다.
    // - 웹 페이지에서 링크를 클릭하면(자바스크립트 처리하지 않은 상태) GET 요청을 보낸다.
    // - 웹 페이지의 폼(method='GET' 일 때)에서 전송 버튼을 클릭하면 GET 요청을 보낸다.
    //
    // 테스트
    // - http://localhost:8080/web/ex04/test01.html 실행
    //

    // 웹 브라우저가 보낸 데이터 읽기
    // ServletRequest.getParameter("파라미터이름")
    //
    String name = req.getParameter("name");
    String age = req.getParameter("age");

    res.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.printf("이름=%s\n", name);
    out.printf("나이=%s\n", age);
  }
}

// 웹브라우저에서 웹서버의 자원을 요청하는 방법
// 1) 서블릿 클래스를 실행하고 싶을 때
// => 서블릿 클래스의 실제 위치:
//    톰캣배치폴더/wtpwebapps/eomcs-java-web/WEB-INF/classes/com/eomcs/web/ex04/Servlet01.class
// => 요청:
//    해당 서블릿을 서버에 등록할 때 사용한 URL을 지정해야 한다.
//    http://localhost:9999/eomcs-java-web/ex04/s1
//
// 2) HTML, CSS, JavaScript, JPEG 등 정적 파일을 받고 싶을 때
// => 정적 파일의 실제 위치:
//    톰캣배치폴더/wtpwebapps/eomcs-java-web/ex04/test01.html
// => 요청:
//    http://localhost:9999/eomcs-java-web/ex04/test01.html
//
// 3) /WEB-INF/ 폴더에 있는 정적 파일을 받고 싶을 때
// => 정적 파일의 실제 위치:
//    톰캣배치폴더/wtpwebapps/eomcs-java-web/WEB-INF/ex04/test01.html
// => 요청:
//    /WEB-INF 폴더 아래에 있는 파일은 클라이언트에서 요청할 수 없다!
//    웹 애플리케이션의 정보를 두는 폴더이기 때문이다.

// HTTP 요청 형식
// 
// method sp request-URI sp http_version CRLF
// *((general header | request header | entity header) CRLF)
// CRLF
// message-body
//
// GET 요청 HTTP 프로토콜 예)
// => GET 요청은 데이터를 request-URI에 붙여서 보낸다.
// => request-URI 
//    예) /java-web/ex04/s1?name=%ED%99%8D%EA%B8%B8%EB%8F%99&age=20
//    서블릿 URL : /java-web/ex04/s1
//    데이터(Query String) : ?name=%ED%99%8D%EA%B8%B8%EB%8F%99&age=20
// => 데이터 형식
//    이름=값&이름=값&이름=값
// => request-URI는 URI 규칙에 따라 인코딩되어야 한다.   
//
// URL 인코딩(=퍼센트 인코딩)
// - URI 작성 규칙에 따라 데이터를 변환하는 것.
// - 문법
//   원래 코드를 4비트 단위로 짤라서 문자로 간주하고 문자 코드로 인코딩 한다.
// 

// URI 작성 규칙
// - RFC 3986 규약에 따라 URL을 작성해야 한다.
// - URL 비예약어(unreserved)인 경우 그대로 사용할 수 있다.
//   예) 영어 대.소문자, -, _, ., ~
// - URL 문법에서 사용하기 위해 예약된 키워드 및 기타 언어의 문자를 데이터로 작성할 때는 URL 인코딩해야 한다.
//   예약어 => !, *, : , ; ,#, /, @ 등
//   기타 문자 => 한글, 한자, 일본어 등
//                즉 8비트 값이 음수일 경우 URL 인코딩 대상이 된다.
// 
// URL 인코딩 및 디코딩 과정
// [웹 브라우저]
// 1. 사용자가 웹 브라우저 주소 창에 입력한 값
//    1) 문자(사람 눈에 보이는 상태) 
//         => ?name=AB가각&age=20
//       코드(메모리에 저장된 상태) 
//         => 3F 6E 61 6D 65 3D 41 42 
//            B0 A1 
//            B0 A2 
//            26 61 67 65 3D 32 30
//       * Windows 인 경우 한글은 MS949 문자표에 따라 코드 값으로 저장된다.
//         그래서 한글 '가각' 은 B0 A1 B0 A2 로 저장된 것이다.
// 
// 2. 사용자가 입력한 값에서 URL 규약에 벗어나는 문자에 대해 URL 인코딩을 수행
//    1) MS949를 웹 페이지 기본 문자표(UTF-8)에 따라 변환(메모리에 저장된 상태)
//         => 3F 6E 61 6D 65 3D 41 42 
//            EA BO 80 
//            EA BO 81 
//            26 61 67 65 3D 32 30  
//    2) URL 인코딩 코드(메모리에 저장된 상태)  
//         => 3F 6E 61 6D 65 3D 41 42 
//            25 45 41 25 42 30 25 38 30 
//            25 45 41 25 42 30 25 38 31 
//            26 61 67 65 3D 32 30  
//         => URL 인코딩 대상
//            EA => 25 45 41  (사람 눈에 보이는 문자: %EA)
//            B0 => 25 42 30  (사람 눈에 보이는 문자: %B0)
//            80 => 25 38 30  (사람 눈에 보이는 문자: %80)
//            EA => 25 45 41  (사람 눈에 보이는 문자: %EA)
//            B0 => 25 42 30  (사람 눈에 보이는 문자: %B0)
//            81 => 25 38 31  (사람 눈에 보이는 문자: %81)
//            내부적으로 저장되는 코드 값을 사람 눈에 보이는 문자화시켰다.
//         => URL 인코딩 후 생성된 코드 값을 보라.
//            모든 코드가 7bit로 표현 가능한 US-ASCII 코드이다.
//            즉 정수 값으로 바꾼다면 모두 양수가 된다.
// 
//       문자(사람 눈에 보이는 상태) 
//         => ?name=AB%EA%B0%80%EA%B0%81&age=20
//         
// 
// [톰캣 서버]
// 3. 웹브라우저로부터 받은 데이터를 URL 디코딩
//    1) URL 디코딩 코드(메모리에 저장된 상태)
//         => 3F 6E 61 6D 65 3D 41 42 
//            EA BO 80    <---- 한글 '가'의 UTF-8 코드
//            EA BO 81    <---- 한글 '가'의 UTF-8 코드 
//            26 61 67 65 3D 32 30  
//         => URL 디코딩 대상
//            25 45 41(%EA) => EA
//            25 42 30(%BO) => B0
//            25 38 30(%80) => 80
//            25 45 41(%EA) => EA
//            25 42 30(%BO) => B0
//            25 38 31(%81) => 81
//
// 4. UTF-8 코드를 자바 String에서 사용할 UTF-16 코드 값으로 변환
//    (단, 톰캣 서버에 클라이언트가 보낸 데이터가 UTF-8이라고 설정되어 있어야 한다.
//     설정이 되어 있지 않으면 톰캣 서버는 클라이언트가 보낸 데이터를  ISO-8859-1 이라 간주하고 
//     UTF-16으로 변환할 것이다.)
//    1) UTF-16로 변환한 코드(메모리에 저장된 상태)
//         => 00 3F 00 6E 00 61 00 6D 00 65 00 3D 00 41 00 42 
//            AC 00    <---- 한글 '가'의 UTF-16 코드  
//            AC 01    <---- 한글 '각'의 UTF-16 코드 
//            00 26 00 61 00 67 00 65 00 3D 00 32 00 30  
//       문자(사람 눈에 보이는 상태) 
//         => ...?name=AB가각&age=20
//
// 5. 만약 톰캣 서버에 클라이언트가 보낸 데이터가 UTF-8 일 것이라고 설정되어 있지 않다면 
//    클라이언트가 보낸 데이터가 ISO-8859-1로 인코딩 되었다고 가정하고 다음과 같이 변환할 것이다.
//    1) UTF-16로 변환한 코드(메모리에 저장된 상태)
//         => 00 3F 00 6E 00 61 00 6D 00 65 00 3D 00 41 00 42 
//            00 EA 00 BO 00 80   <------ 화면에 출력하면 깨진 문자가 보일 것이다. 
//            00 EA 00 B0 00 81   <------ 화면에 출력하면 깨진 문자가 보일 것이다.
//            00 26 00 61 00 67 00 65 00 3D 00 32 00 30  
//       문자(사람 눈에 보이는 상태) 
//         => ...?name=AB가각&age=20
// 
//
// 톰캣 서버에 GET 요청으로 받은 데이터가 UTF-8 임을 설정하는 방법
// 톰캣 7 이하 버전에서는 conf/server.xml 파일에서 다음과 같이 설정해야 한다.
//    <Connector connectionTimeout="20000" port="8080" 
//               protocol="HTTP/1.1" redirectPort="8443"
//               URIEncoding="UTF-8"  <------ 이 속성을 추가하라!          
//     />
// 주의!
// => 톰캣 서버가 아닌 다른 서블릿 컨테이너는 사용 안내서를 확인해보라!
//
// HTTP GET 요청 예)
// ------------------------------------------------------------------------------
// GET /java-web/ex04/s1?name=AB%EA%B0%80%EA%B0%81&age=20 HTTP/1.1
// Host: localhost:8080
// Connection: keep-alive
// Pragma: no-cache
// Cache-Control: no-cache
// Upgrade-Insecure-Requests: 1
// User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, likeGecko) Chrome/73.0.3683.86 Safari/537.36
// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,
// Accept-Encoding: gzip, deflate, br
// Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6
// 빈 줄


// HTTP 응답 프로토콜
// => 형식
// status-line(HTTP프로토콜 상태코드 간단한문구) CRLF
// *(general header | response header | entity header) CRLF
// CRLF
// message-body
//
// => 예:
//
// HTTP 응답 예)
// ------------------------------------------------------------------------------
// HTTP/1.1 200 OK
// Content-Type: text/plain;charset=UTF-8
// Content-Length: 27
// Date: Thu, 28 Mar 2019 05:46:08 GMT
//     <== 빈 줄
// 이름=홍길동
// 나이=20
//
//
// URI (Uniform Resource Identifier)
// => 웹 자원의 위치를 가리키는 식별자
// => 종류
//    URL(Uniform Resource Locator)
//      scheme:[//[user:password@]host[:port]][/]path[?query string][#fragment]
//      예) http://localhost:8080/ex04/s1?name=홍길동&age=20
//
//    URN(Uniform Resource Name)
//      <URN> ::= "urn:" <NID> ":" <NSS>
//      예) urn:lex:eu:council:directive:2010-03-09;2010-19-UE
//


