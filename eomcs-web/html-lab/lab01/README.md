# 웹과 HTML 시작하기

- VS Code 및 Live Preview 확장 설치
- 브라우저와 웹 서버의 역할
- URL, HTML의 역할
- `<!DOCTYPE html>`, `html`, `head`, `body`, `title`
- 문자 인코딩 

## 1. Git Client 설치

- [Git 공식 사이트](https://git-scm.com/)에서 다운로드 및 설치
- 주요 기능
  - Git 저장소에서 소스 가져오기
  - Git 저장소에 소스 업로드

설치 후, 터미널 또는 명령 프롬프트에서 설치 확인

```bash
git --version
```

## 2. Git 저장소에서 실습 소스 가져오기

```bash
git clone https://github.com/eomcs/eomcs-examples.git
```

## 3. VS Code 설치

- [VS Code 공식 사이트](https://code.visualstudio.com/)에서 다운로드 및 설치
- D2Coding 폰트 설치 및 설정
  - [D2Coding 폰트](https://github.com/naver/d2-coding-font)에서 최신 버전 다운로드 및 압축 해제
  - `D2CodingAll/` 안의 .ttc 파일을 더블 클릭하여 설치
  - VS Code를 재시작 한다.
  - VS Code 메뉴 `Code → 기본 설정 → 설정`에서 `editor.fontFamily` 검색하여 입력란 맨 앞에 `D2Coding`추가

## 4. 한글 언어 팩 확장 설치

- VS Code에서 `Korean Language Pack for Visual Studio Code` 확장 설치
  - 제작자: Microsoft
- 주요 기능
  - VS Code 메뉴, 메시지, 도움말 등을 한글로 표시

## 5. Live Preview 확장 설치

- VS Code에서 `Live Preview` 확장 설치
  - 확장 ID: `ms-vscode.live-server`
  - 제작자: Microsoft
- 주요 기능
  - HTML, CSS 변경 즉시 편집기에서 미리보기 가능
  - 브라우저에서 HTML 파일을 실시간으로 확인 가능

## 6. 실습 프로젝트 폴더 열기

- VS Code에서 `파일 → 폴더 열기` 메뉴 선택
- `eomcs-examples/eomcs-web/html-lab` 폴더 선택

## 7. 첫 번째 HTML 페이지 작성, 미리 보기와 브라우저에서 확인

`lab01/exam01.html` 파일 생성 후 다음 코드 입력:

```html
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>나의 첫 번째 HTML</title>
</head>
<body>
  <h1>나의 첫 번째 HTML</h1>
  <p>HTML 문서입니다.</p>
</body>
</html>
```

exam01.html 컨텍스트 메뉴에서 `미리 보기 표시` 선택

## 8. 웹 브라우저와 웹 서버 구동 원리

### 요청과 응답

1. 외부 브라우저에서 연다.
2. URL `http://127.0.0.1:3000/lab01/exam01.html`을 입력한 후 엔터를 누른다.
3. 브라우저의 개발자 도구를 열고 **Network(네트워크)** 탭을 선택한다.
4. 페이지를 새로 고친 뒤 `exam01.html` 요청을 선택한다.
5. 요청 URL, 응답 상태 코드, 응답으로 전달된 HTML 내용을 확인한다.
6. 주소의 파일 이름을 존재하지 않는 `missing.html`로 바꾸어 요청하고, `404` 응답을 확인한다.

HTML을 수정하면 Live Preview가 변경 사항을 반영하여 미리 보기를 갱신한다. 일반적인 웹 서버가 모두 이러한 자동 갱신 기능을 제공하는 것은 아니다.

### 웹 브라우저와 웹 서버의 역할

- **웹 브라우저**: 웹 서버에 필요한 자료를 요청하고, 전달받은 HTML을 해석하여 화면에 표시하는 프로그램
  - 예: Chrome, Edge, Firefox, Safari
- **웹 서버**: 브라우저의 요청을 받아 HTML, CSS, 이미지 등의 자료를 응답하는 프로그램
  - 웹 서버 프로그램이 실행되는 컴퓨터를 웹 서버라고 부르기도 한다.
- **HTTP**: 브라우저와 웹 서버가 요청과 응답을 주고받을 때 사용하는 통신 규칙
  - HTTPS는 HTTP 통신을 암호화하여 보호한다.

이번 실습에서는 Live Preview가 로컬 웹 서버를 실행한다. 따라서 별도의 웹 서버 프로그램을 설치하지 않아도 된다. 브라우저와 웹 서버는 서로 다른 컴퓨터에서 실행될 수도 있고, 이번 실습처럼 같은 컴퓨터에서 실행될 수도 있다.

### 웹 페이지가 표시되는 과정

```text
웹 브라우저                         웹 서버(Live Preview)
    |                                      |
    |  ① 요청: /lab01/exam01.html         |
    | -----------------------------------> |
    |                                      | ② 요청한 파일 찾기
    |  ③ 응답: 상태 코드와 HTML 내용      |
    | <----------------------------------- |
    |                                      |
    |  ④ HTML을 해석하여 화면에 표시      |
```

1. 사용자가 브라우저 주소창에 웹 페이지의 URL을 입력한다.
2. 브라우저가 URL에 지정된 웹 서버에 접속하여 해당 경로의 자료를 요청한다.
3. 웹 서버가 요청한 자료를 찾아 응답한다.
   - 파일을 정상적으로 제공하면 `200 OK` 등의 상태 코드와 파일 내용을 보낸다.
   - 요청한 자료를 찾을 수 없으면 `404 Not Found` 상태 코드를 보낸다.
4. 브라우저가 전달받은 HTML을 해석하여 제목, 문단 등을 화면에 표시한다. 이 과정을 **렌더링**이라고 한다.
5. HTML에 외부 CSS, JavaScript, 이미지 등이 연결되어 있으면 브라우저가 필요한 자료를 추가로 요청한다.

웹 서버가 완성된 화면을 보내는 것이 아니라, 브라우저가 전달받은 자료를 바탕으로 화면을 구성한다.

### CLI 기반 요청/응답

```bash
curl --verbose http://localhost:3000/lab01/exam01.html
```

## 9. URL

URL은 웹에서 자료의 위치를 나타내는 주소이다. 게시글 목록 페이지의 주소가 다음과 같다고 가정하자.

```text
http://127.0.0.1:3000/board/list?page=1&perPage=10#title
```

| 구성 요소 | 예 | 의미 |
| --- | --- | --- |
| 스킴 | `http` | 자료에 접근할 때 사용할 통신 방식 |
| 호스트 | `127.0.0.1` | 접속할 컴퓨터의 주소. 여기서는 브라우저가 실행 중인 자기 컴퓨터 |
| 포트 | `3000` | 해당 컴퓨터에서 접속할 서버의 통신 창구 번호 |
| 경로 | `/board/list` | 서버에 요청할 자료나 기능의 경로. 이 예에서는 게시글 목록을 나타냄 |
| 쿼리 문자열 | `page=1&perPage=10` | `?` 뒤에 작성하는 추가 정보. 이 예에서는 페이지 번호와 페이지당 게시글 수를 전달함 |
| 프래그먼트 | `title` | `#` 뒤에 작성하는 문서 내부 위치. 일반적인 HTML 문서에서는 `id="title"`인 요소를 가리킴 |

- 쿼리 문자열은 보통 `이름=값` 형식으로 작성하고, 여러 항목은 `&`로 구분한다.
  - `page=1`: 첫 번째 페이지를 요청한다는 의미로 사용할 수 있다.
  - `perPage=10`: 한 페이지에 게시글 10개를 요청한다는 의미로 사용할 수 있다.
  - 이러한 이름과 의미는 서버 프로그램에서 정한다. URL에 적는 것만으로 페이지 나누기 기능이 구현되는 것은 아니다.
- 프래그먼트는 HTTP 요청에 포함되지 않고 브라우저에서 처리한다. 이 예에서 서버에 전달하는 요청 경로와 쿼리는 `/board/list?page=1&perPage=10`이다.
- `/board/list`는 실제 파일 이름일 필요가 없다. 서버 프로그램이 이 경로에 대한 요청을 처리하여 HTML 등을 만들어 응답할 수도 있다.
- 위 주소는 URL의 구조를 설명하기 위한 예이다. Live Preview만 실행한 상태에서 게시글 목록 기능이 제공되는 것은 아니다.

### `127.0.0.1` vs `localhost`
- `127.0.0.1`는 로컬 컴퓨터를 가리키는 주소이다.
- `localhost`는 `127.0.0.1`를 가리키는 도메인 이름이다. 즉, `http://localhost:3000/board/list`와 `http://127.0.0.1:3000/board/list`는 같은 주소이다.
- 포트 번호는 서버 설정과 사용 중인 포트에 따라 달라질 수 있다. **Live Preview 미리 보기에 표시된 실제 주소**를 사용한다.

### `http://` vs `file://`

| 구분 | 파일 직접 열기 | 웹 서버를 통해 열기 |
| --- | --- | --- |
| 주소 시작 | `file://` | `http://` 또는 `https://` |
| 자료를 가져오는 방법 | 브라우저가 로컬 컴퓨터의 파일을 직접 읽음 | 브라우저가 서버에 요청하고 응답을 받음 |
| 웹 서버 필요 여부 | 필요 없음 | 필요함 |

간단한 HTML은 파일을 직접 열어도 볼 수 있다. 이번 교육에서는 웹의 요청·응답 과정을 경험할 수 있도록 Live Preview의 웹 서버를 통해 접속한다.

## 10. HTTP 프로토콜

이번에는 curl이 HTTP 클라이언트 역할을 하고, Live Preview가 웹 서버 역할을 한다. `--verbose` 옵션은 연결 과정과 요청·응답 헤더를 출력하며, 응답 본문인 HTML도 터미널에서 확인할 수 있다.

### curl 실행

curl 실행:

```bash
$ curl --verbose http://localhost:3000/lab01/exam01.html
```

curl 실행 결과:

```text
* Host localhost:3000 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:3000...
* connect to ::1 port 3000 from ::1 port 64886 failed: Connection refused
*   Trying 127.0.0.1:3000...
* Connected to localhost (127.0.0.1) port 3000
> GET /lab01/exam01.html HTTP/1.1
> Host: localhost:3000
> User-Agent: curl/8.7.1
> Accept: */*
>
* Request completely sent off
< HTTP/1.1 200 OK
< Content-Type: text/html; charset=UTF-8
< Content-Length: 285
< Cross-Origin-Resource-Policy: cross-origin
< Accept-Ranges: bytes
< Date: Sat, 05 Sep 2026 07:41:15 GMT
< Connection: keep-alive
< Keep-Alive: timeout=5
<
<!DOCTYPE html>
<html lang="ko">
<head><script type="text/javascript" src="/___vscode_livepreview_injected_script"></script>
  <meta charset="UTF-8">
  <title>나의 첫 번째 HTML</title>
</head>
<body>
  <h1>나의 첫 번째 HTML</h1>
  <p>HTML 문서입니다.</p>
</body>
* Connection #0 to host localhost left intact
</html>
```

### 출력 기호 구분하기

| 표시 | 의미 | HTTP 메시지에 포함되는가? |
| --- | --- | --- |
| `$` | 터미널 프롬프트. 뒤에 실행할 명령을 입력함 | 아니요 |
| `*` | curl이 출력하는 연결·전송 상태 정보 | 아니요 |
| `>` | 서버로 보낸 요청 시작 줄과 헤더를 표시 | 기호 자체는 포함되지 않음 |
| `<` | 서버에서 받은 응답 상태 줄과 헤더를 표시 | 기호 자체는 포함되지 않음 |
| `<!DOCTYPE html>`부터의 HTML | 서버가 보낸 응답 본문 | 예 |

HTML 태그의 `<`는 본문의 일부이다. curl이 헤더 앞에 붙이는 `< ` 표시와 구분한다.

### 1) 서버 연결

```text
* Host localhost:3000 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:3000...
* connect to ::1 port 3000 from ::1 port 64886 failed: Connection refused
*   Trying 127.0.0.1:3000...
* Connected to localhost (127.0.0.1) port 3000
```

### 2) HTTP 요청

```http
GET /lab01/exam01.html HTTP/1.1
Host: localhost:3000
User-Agent: curl/8.7.1
Accept: */*

```

HTTP/1.1 메시지는 **시작 줄 → 헤더 → 빈 줄 → 본문(있는 경우)** 순서로 구성된다. 시작 줄과 각 헤더 줄의 끝에는 `CRLF`(`\r\n`)가 사용되며, 빈 줄이 헤더의 끝을 알린다. [HTTP/1.1 메시지 형식](https://www.rfc-editor.org/rfc/rfc9112.html#section-2.1)

| 구분 | 실행 결과 | 설명 |
| --- | --- | --- |
| 요청 줄 | `GET /lab01/exam01.html HTTP/1.1` | 메서드, 요청 대상, HTTP 버전을 공백으로 구분 |
| 메서드 | `GET` | 지정한 자료를 가져오도록 요청 |
| 요청 대상 | `/lab01/exam01.html` | 서버에서 가져올 자료의 경로 |
| HTTP 버전 | `HTTP/1.1` | 요청 메시지가 사용하는 HTTP 버전 |
| Host 헤더 | `Host: localhost:3000` | 요청 대상 호스트와 포트. HTTP/1.1 요청에 필수 |
| User-Agent 헤더 | `User-Agent: curl/8.7.1` | 요청한 클라이언트의 종류와 버전 |
| Accept 헤더 | `Accept: */*` | 모든 미디어 유형의 응답을 받을 수 있음을 알림 |
| 빈 줄 | 출력에서 `>`만 있는 줄 | 요청 헤더의 끝 |

이 요청에는 본문이 없다. `* Request completely sent off`는 curl이 요청 전송을 마쳤다는 진단 정보이다.

### 3) HTTP 응답

응답은 **상태 줄 → 응답 헤더 → 빈 줄 → 응답 본문**으로 구성되어 있다.

```http
HTTP/1.1 200 OK
Content-Type: text/html; charset=UTF-8
Content-Length: 285
Cross-Origin-Resource-Policy: cross-origin
Accept-Ranges: bytes
Date: Sat, 05 Sep 2026 07:41:15 GMT
Connection: keep-alive
Keep-Alive: timeout=5

```

위의 빈 줄 다음에 실행 결과의 HTML 본문이 이어진다.

| 구분 | 실행 결과 | 설명 |
| --- | --- | --- |
| 상태 줄 | `HTTP/1.1 200 OK` | HTTP 버전, 상태 코드, 상태 설명으로 구성. `200`은 요청 처리 성공을 뜻함 |
| Content-Type | `text/html; charset=UTF-8` | 본문이 HTML이며 문자 인코딩은 UTF-8임 |
| Content-Length | `285` | 응답 본문의 길이가 285바이트임을 알림. 헤더는 제외하며, 글자 수가 아님 |
| Cross-Origin-Resource-Policy | `cross-origin` | 다른 출처에서 이 자료를 `no-cors` 방식으로 불러오는 것을 CORP 정책상 허용함 |
| Accept-Ranges | `bytes` | 바이트 단위의 범위 요청을 지원함을 알림. 이번 요청은 범위 요청이 아님 |
| Date | `Sat, 05 Sep 2026 07:41:15 GMT` | 서버가 응답 메시지를 생성한 시각. 파일 수정 시각이 아님 |
| Connection | `keep-alive` | 응답 후에도 연결을 유지하여 후속 요청에 재사용할 수 있음을 알림 |
| Keep-Alive | `timeout=5` | 유휴 연결을 유지하는 제한 시간을 5초로 안내 |
| 빈 줄 | 출력에서 `<`만 있는 줄 | 응답 헤더의 끝이자 본문의 시작을 구분 |

`Cross-Origin-Resource-Policy`는 브라우저의 리소스 로딩 정책이다. 이 헤더가 다른 출처의 JavaScript에 응답 내용을 자유롭게 읽을 권한까지 주는 것은 아니다. [CORP 헤더 설명](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Cross-Origin-Resource-Policy)

### 4) 응답 본문

- `<!DOCTYPE html>`부터 `</html>`까지가 서버가 보낸 HTML 본문이다.
- `<script ... src="/___vscode_livepreview_injected_script"></script>`는 Live Preview가 미리 보기 기능을 위해 삽입한 스크립트이다. 따라서 응답 HTML은 직접 작성한 파일 내용과 다를 수 있다.
- 브라우저는 HTML을 해석하여 화면을 구성하고 연결된 스크립트 등을 추가로 요청한다.
- 이 curl 명령은 받은 HTML을 출력한다. HTML을 화면으로 렌더링하거나, 그 안의 스크립트를 자동으로 요청·실행하지 않는다.

### 5) 전송 완료와 연결 유지

```text
* Connection #0 to host localhost left intact
```

curl이 응답 수신을 마쳤으며, 해당 연결을 즉시 닫지 않고 재사용 가능한 상태로 남겼다는 진단 정보이다. 이 명령처럼 요청 하나를 처리하고 curl 프로그램이 종료되면 연결도 정리된다.

실행 결과에서는 이 문장이 `</body>`와 `</html>` 사이에 보인다. curl은 본문을 **표준 출력(stdout)**으로, 진단 정보를 **표준 오류(stderr)**로 출력하므로 터미널에서 두 출력이 섞여 보일 수 있다. 이 문장은 HTML 본문이나 HTTP 응답 헤더에 포함되지 않는다.

두 출력을 분리해서 확인하려면 다음과 같이 실행한다.

```bash
curl --verbose http://localhost:3000/lab01/exam01.html -o response.html 2> curl.log
```

- `response.html`: 서버가 보낸 HTML 본문
- `curl.log`: 연결 과정과 요청·응답 헤더 등 curl의 상세 로그

curl 출력 옵션은 [curl 공식 설명](https://curl.se/docs/httpscripting.html)에서 확인할 수 있다.
