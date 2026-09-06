# 입력 폼 만들기

- 폼의 역할과 전송: `form`, `action`, `method`
- 입력 항목과 이름표: `input`, `label`, `id`, `name`, `value`
- 선택 항목: 라디오 버튼, 체크박스, `select`, `option`
- 여러 줄 입력: `textarea`
- 입력 그룹과 버튼: `fieldset`, `legend`, `button`
- 브라우저 기본 검증: `required`, `minlength`, `min`, `max` 등
- 이름·이메일·관심 분야·문의 내용을 입력하는 문의 페이지 작성

## 1. 입력 폼의 역할

### 사용자에게 입력받기

입력 폼은 이름, 검색어, 문의 내용 등 사용자의 입력을 모아 제출하는 영역이다. HTML은 입력 화면과 제출 방식을 정의하며, 실제 저장이나 이메일 발송은 서버 프로그램 등이 처리한다.

```text
사용자 입력
    ↓
브라우저의 입력값 검증
    ↓
form에 지정된 주소로 HTTP 요청
    ↓
서버가 요청 처리 후 응답
```

이번 실습은 Live Preview의 로컬 웹 서버에서 진행한다. **입력과 브라우저 기본 검증, 요청에 실리는 데이터까지 확인**한다. 실제 문의를 저장하거나 발송하는 서버 기능은 구현하지 않는다.

### 실습 파일 준비하기

```text
lab07/
├── exam01.html
├── exam02.html
├── exam03.html
├── exam04.html
├── exam05.html
├── result.html  # 요청 후 이동할 정적 페이지
└── index.html   # 종합 문의 폼
```

각 HTML 파일은 실습 단계에서 Emmet의 `!`로 기본 구조를 만들고 `lang="ko"`와 적절한 `title`을 지정한다. 아래 예제는 별도 안내가 없으면 `body` 안에 작성한다. VS Code에서는 `html-lab` 폴더를 열고 Live Preview로 접속한다.

## 2. 폼과 전송 방식: `form`

### 제출 주소와 메서드 지정하기

```html
<form action="result.html" method="get">
  <label for="keyword">검색어</label>
  <input type="text" id="keyword" name="keyword">
  <button type="submit">검색 요청 확인</button>
</form>
```

| 구성 | 역할 |
| --- | --- |
| `form` | 함께 제출할 입력 항목을 묶음 |
| `action="result.html"` | 제출할 URL 지정. 여기서는 현재 문서와 같은 폴더의 페이지 |
| `method="get"` | GET 방식으로 요청 |
| `name="keyword"` | 제출 데이터의 항목 이름 |
| `button type="submit"` | 폼 제출 버튼 |

`form` 안에 또 다른 `form`을 중첩하지 않는다. `action`의 상대 경로는 링크처럼 현재 문서 URL을 기준으로 해석된다. 이 교재에서는 `base` 요소를 사용하지 않는다.

### GET과 POST 비교하기

| 구분 | GET | POST |
| --- | --- | --- |
| 입력 데이터 위치 | URL의 쿼리 문자열 | HTTP 요청 본문 |
| 주된 사용 예 | 검색, 조회 조건 전달 | 등록, 수정 요청 등 |
| 주소창에서 입력값 확인 | 가능 | 일반적으로 본문의 입력값은 표시되지 않음 |
| 이번 실습 | 정적 결과 페이지로 이동하며 요청 확인 | 서버 처리 필요성을 설명하는 용도 |

```text
GET 요청의 예
/lab07/result.html?keyword=HTML
```

POST는 입력값을 주소창에 표시하지 않지만, 그 자체가 암호화 방식은 아니다. 실제 서비스에서 전송 내용을 보호하려면 HTTPS를 사용한다. 이번 GET 실습에는 실제 비밀번호 등 민감한 내용을 입력하지 않는다.

### 실습: 입력값을 URL에서 확인하기

1. `lab07/exam01.html`에 위 폼을 작성한다.
2. `lab07/result.html`의 기본 구조와 다음 본문을 작성한다.

```html
<h1>요청 확인 실습 페이지</h1>
<p>주소창과 개발자 도구에서 제출한 데이터를 확인하세요.</p>
<p>이 페이지는 문의를 저장하거나 입력값을 자동으로 표시하지 않습니다.</p>
```

3. Live Preview에서 `exam01.html`을 열고 `HTML`을 입력하여 제출한다.
4. 주소창에 `result.html?keyword=HTML`이 나타나는지 확인한다.
5. `result.html`은 정적 파일이므로 검색 결과를 만들거나 입력값을 화면에 출력하지 않는다는 점을 확인한다.

## 3. 이름표와 입력 데이터: `label`, `id`, `name`, `value`

### 이름표를 입력 칸과 연결하기

1. `lab07/exam02.html`을 만들고 다음 폼을 작성한다.

```html
<form action="result.html" method="get">
  <label for="user-name">이름</label>
  <input type="text" id="user-name" name="userName" value="홍길동">
  <button type="submit">제출</button>
</form>
```

| 속성 | 의미 |
| --- | --- |
| `label`의 `for` | 연결할 입력 요소의 `id` |
| `input`의 `id` | 문서 안에서 요소를 식별하는 고유한 이름 |
| `input`의 `name` | 서버에 제출할 데이터의 이름 |
| `input`의 `value` | 이 텍스트 입력 칸의 초기 값 |

- `for`와 `id`를 정확히 맞추면 이름표를 클릭해 입력 칸으로 초점을 옮길 수 있다.
- 같은 문서 안에서 `id`를 중복하지 않는다.
- `name`과 `id`는 같아도 되지만 역할은 다르다.
- 일반적인 폼 제출에서 `name`이 없는 입력 칸은 해당 값을 제출하지 않는다.
- 사용자가 초기 값을 바꾸면 제출 시점의 현재 입력값이 전달된다.

사용자가 이름을 `김민수`로 바꾸었다면 데이터는 `userName=김민수`에 해당한다. URL에는 한글 등이 인코딩되어 표시될 수 있다.

### 입력 힌트: `placeholder`

1. `lab07/exam02.html`에서 '이름' 입력 항목 다음에 '이메일' 입력 항목을 추가한다.

```html
<label for="email">이메일</label>
<input type="email" id="email" name="email"
       placeholder="student@example.com">
```

`placeholder`는 빈 입력 칸에 표시할 예시나 힌트이다. 실제 입력값이 아니며, 입력을 시작하면 사라지므로 `label`을 대신하지 않는다.

## 4. 입력 유형: `input type`

### 내용에 맞는 입력 칸 선택하기

| `type` | 용도 | 주요 특징 |
| --- | --- | --- |
| `text` | 한 줄 텍스트 | 이름, 짧은 제목 등 |
| `email` | 이메일 주소 | 입력된 값의 이메일 형식 검증 |
| `password` | 비밀번호 | 화면에서 입력 문자를 가림 |
| `tel` | 전화번호 | 모바일에서 관련 키보드를 제공할 수 있음 |
| `number` | 수량 등 숫자 | `min`, `max`, `step`으로 범위·간격 지정 |
| `date` | 날짜 | 날짜 선택 UI를 제공할 수 있음 |
| `file` | 파일 선택 | 사용자가 업로드할 파일 선택 |
| `hidden` | 숨겨진 데이터 | 화면의 입력 칸 없이 값 전달 |

입력 UI는 브라우저와 운영체제에 따라 달라진다. `email`은 주소가 실제로 존재하는지 확인하지 않으며, `tel`은 자동으로 특정 전화번호 형식을 검증하지 않는다.

`password`는 화면 표시만 가린다. `hidden`도 개발자 도구로 확인·변경할 수 있으므로 비밀을 보관하거나 사용자의 조작을 막는 수단이 아니다.

```html
<form action="result.html" method="get">
  <p>
    <label for="name">이름</label>
    <input type="text" id="name" name="userName" autocomplete="name">
  </p>
  <p>
    <label for="email">이메일</label>
    <input type="email" id="email" name="email" autocomplete="email">
  </p>
  <p>
    <label for="count">참여 인원</label>
    <input type="number" id="count" name="count" min="1" max="5" step="1" value="1">
  </p>
  <p>
    <label for="visit-date">희망 날짜</label>
    <input type="date" id="visit-date" name="visitDate">
  </p>
  <button type="submit">입력값 확인</button>
</form>
```

`autocomplete`는 브라우저가 입력 항목의 목적을 이해하고 자동 완성을 제공하는 데 도움이 된다.

### 실습: 입력 유형 비교하기

1. `lab07/exam03.html`에 이름·이메일·인원·날짜 폼을 작성한다.
2. 각 입력 칸의 조작 방식과 모바일 키보드가 어떻게 달라질 수 있는지 관찰한다.
3. 인원에 범위 밖 값을 입력하거나 이메일에 `abc`를 입력하고 제출해 본다.
4. 올바른 값으로 수정한 뒤 URL에 전달된 항목을 확인한다.

## 5. 파일 전송에 필요한 설정

1. `lab07/exam04.html`에 다음 폼을 작성한다.
2. 파일을 선택한 후 업로드 버튼을 클릭한다.
2. 브라우저의 개발자 도구에서 Network 탭을 선택한다.
3. 요청 URL을 선택한 후 Payload 영역에서 `attachment` 이름과 파일 이름, 파일 내용이 전송되는지 확인한다.

```html
<!-- /upload를 처리하는 서버 프로그램이 있을 때 사용하는 예 -->
<form action="result.html" method="post" enctype="multipart/form-data">
  <label for="attachment">첨부 파일</label>
  <input type="file" id="attachment" name="attachment">
  <button type="submit">업로드</button>
</form>
```

파일 내용을 전송하려면 일반적으로 POST와 `multipart/form-data`를 사용한다. 위 예제는 구조 설명용이며 Live Preview만으로 파일을 업로드·저장할 수 없다.

## 6. 하나 또는 여러 개 선택하기

### 라디오 버튼: `type="radio"`

같은 그룹에서 하나만 선택할 때 사용한다. **같은 `name`을 가진 라디오 버튼들이 하나의 그룹**이다.

```html
<fieldset>
  <legend>희망 수업 방식</legend>
  <input type="radio" id="online" name="classMode" value="online" checked>
  <label for="online">온라인</label>

  <input type="radio" id="offline" name="classMode" value="offline">
  <label for="offline">오프라인</label>
</fieldset>
```

- `checked`는 초기 선택 상태를 지정한다.
- 오프라인을 선택하면 `classMode=offline`이 제출된다.
- 사용자에게 보이는 이름표와 제출하는 `value`는 다를 수 있다.
- 같은 그룹이라도 각 `id`는 서로 다르게 작성한다.

### 체크박스: `type="checkbox"`

각 항목을 독립적으로 선택하거나 해제할 때 사용한다.

```html
<fieldset>
  <legend>관심 분야</legend>
  <input type="checkbox" id="html" name="interest" value="html">
  <label for="html">HTML</label>

  <input type="checkbox" id="css" name="interest" value="css">
  <label for="css">CSS</label>
</fieldset>
```

- 둘 다 선택하면 `interest=html&interest=css`처럼 같은 이름으로 여러 값이 전송될 수 있다.
- 선택하지 않은 체크박스와 라디오 버튼은 일반적으로 제출 데이터에 포함되지 않는다.
- `value`를 생략한 체크박스·라디오 버튼은 기본값 `on`을 사용할 수 있으므로 항목을 구분할 값을 명시한다.

### 실습: 선택값 확인하기

1. `lab07/exam05.html`에 GET 폼을 만들고 위 두 그룹과 제출 버튼을 넣는다.
2. 수업 방식은 하나만, 관심 분야는 여러 개 선택할 수 있는지 확인한다.
3. 체크박스를 모두 해제한 경우와 둘 다 선택한 경우의 쿼리 문자열을 비교한다.

## 7. 드롭다운과 여러 줄 입력: `select`, `textarea`

### 드롭다운 목록

```html
<label for="category">문의 유형 (필수)</label>
<select id="category" name="category" required>
  <option value="">선택하세요</option>
  <option value="course">수업 문의</option>
  <option value="practice">실습 문의</option>
  <option value="other">기타 문의</option>
</select>
```

- `select`가 선택 입력을, `option`이 각 항목을 나타낸다.
- 제출되는 이름은 `select`의 `name`, 값은 선택된 `option`의 `value`이다.
- 이 예제는 빈 값의 첫 항목을 선택한 상태에서 `required` 검증을 통과하지 못한다.
- `option`에 `selected`를 지정하면 초기 선택 항목을 정할 수 있다.
- `multiple`을 지정하면 여러 항목을 선택할 수 있지만 조작 방식이 달라진다. 이번에는 단일 선택을 사용한다.

### 여러 줄 텍스트

```html
<label for="message">문의 내용</label>
<textarea id="message" name="message" rows="5" cols="40"
          placeholder="질문과 오류 메시지를 작성하세요."></textarea>
```

- `textarea`는 여러 줄을 입력하는 요소이며 종료 태그를 작성한다.
- `rows`는 표시할 줄 수, `cols`는 평균 문자 너비를 기준으로 한 표시 폭이다. 입력 가능한 최대 글자 수가 아니다.
- 초기 내용은 `value` 속성이 아니라 시작·종료 태그 사이에 작성한다.

```html
<textarea id="memo" name="memo" rows="3">HTML 실습에 대해 질문합니다.</textarea>
```

태그 사이에 코드 정렬용 공백을 넣으면 초기 입력 내용에 포함될 수 있다. 빈 입력 칸을 만들 때는 위 첫 예제처럼 태그 사이를 비워 둔다.

### 실습: 문의 입력 칸 만들기

1. `lab07/exam06.html`에 GET 폼을 만들고 문의 유형과 문의 내용을 추가한다.
2. `label`의 `for`와 각 입력 요소의 `id`를 맞춘다.
3. 여러 줄을 입력하고 제출하여 데이터가 URL에 인코딩되어 전달되는지 확인한다.

## 8. 입력 그룹과 버튼

### 관련 입력 묶기: `fieldset`, `legend`

```html
<fieldset>
  <legend>문의자 정보</legend>
  <p>
    <label for="contact-name">이름</label>
    <input type="text" id="contact-name" name="userName">
  </p>
  <p>
    <label for="contact-email">이메일</label>
    <input type="email" id="contact-email" name="email">
  </p>
</fieldset>
```

`fieldset`은 관련 입력을 묶고, 첫 번째 자식으로 작성한 `legend`는 그룹의 제목을 제공한다. 라디오 버튼처럼 여러 선택지가 공통 질문을 가질 때 특히 유용하다. 테두리를 그리는 목적만으로 선택하지 않는다.

### 버튼의 동작 지정하기

| `type` | 동작 |
| --- | --- |
| `submit` | 폼 검증 후 제출 |
| `reset` | 입력을 초기 값과 초기 선택 상태로 복원 |
| `button` | 기본 제출 동작 없음. 보통 JavaScript와 연결 |

```html
<button type="submit">입력값 확인</button>
<button type="reset">처음 상태로</button>
<button type="button">일반 버튼</button>
```

- 일반적인 폼 안에서 `button`의 `type`을 생략하면 제출 버튼으로 동작할 수 있으므로 명시한다.
- `reset`은 무조건 빈칸으로 만드는 기능이 아니다. `value`, `checked`, `selected` 등의 초기 상태로 돌아간다.
- `type="button"`에 동작 코드를 연결하지 않으면 클릭해도 별도 기능이 실행되지 않는다.
- 실제 문의 화면에서 초기화 버튼은 작성 내용을 실수로 지울 수 있어 꼭 필요할 때만 제공한다.

### 실습: 초기 상태로 복원하기

1. `exam03.html`의 폼 안에 초기화 버튼을 추가한다.
2. 라디오 선택을 바꾸고 체크박스를 선택한 뒤 초기화한다.
3. `checked`로 지정했던 온라인 선택이 복원되는지 확인한다.

## 9. 브라우저의 기본 입력 검증

### HTML 속성으로 제약 지정하기

| 속성 | 의미 | 예 |
| --- | --- | --- |
| `required` | 필수 입력 또는 선택 | 이름, 문의 유형 |
| `minlength` | 최소 입력 길이 | 문의 내용 10자 이상 |
| `maxlength` | 최대 입력 길이 | 문의 내용 500자 이내 |
| `min`, `max` | 숫자·날짜 등의 범위 | 인원 1~5명 |
| `step` | 숫자 등의 유효한 간격 | 정수 단위의 인원 |
| `pattern` | 지원되는 텍스트 입력 유형의 정규식 패턴 | 정해진 코드 형식 |

```html
<form action="result.html" method="get">
  <p>
    <label for="required-name">이름 (필수)</label>
    <input type="text" id="required-name" name="userName" required>
  </p>
  <p>
    <label for="required-message">문의 내용 (필수, 10~500자)</label>
    <textarea id="required-message" name="message" rows="5"
              required minlength="10" maxlength="500"></textarea>
  </p>
  <button type="submit">입력값 확인</button>
</form>
```

- 검증에 실패하면 일반적인 제출 과정에서 브라우저가 이동을 막고 안내한다. 문구와 표시 방식은 브라우저에 따라 다르다.
- `minlength`만으로는 빈 입력을 필수 입력으로 만들지 않는다. 필요하면 `required`를 함께 작성한다.
- 공백만 입력한 텍스트도 단순한 `required` 검증을 통과할 수 있다. 내용의 타당성은 별도로 확인해야 한다.
- 체크박스에 `required`를 지정하면 그 체크박스 자체가 선택되어야 한다. 여러 체크박스에 지정하면 각각 필수가 되며, “하나 이상 선택” 규칙이 되지는 않는다.
- 라디오 그룹에 `required`를 적용하면 같은 이름의 그룹에서 하나를 선택하도록 요구할 수 있다.
- 폼에 `novalidate`를 지정하면 제출 시 브라우저 기본 검증을 생략한다. 이번 실습에서는 사용하지 않는다.

브라우저 검증은 사용자 입력을 돕는 기능이다. 사용자가 우회할 수 있으므로 실제 서비스의 서버에서도 입력값을 검증해야 한다.

### 실습: 실패와 성공 비교하기

1. `lab07/exam07.html`에 위 폼을 작성한다.
2. 비워 둔 채 제출하여 필수 입력 안내를 확인한다.
3. 문의 내용을 직접 10자 미만으로 입력한 뒤 제출한다.
4. 10자 이상으로 수정하여 결과 페이지로 이동하는지 확인한다.

## 10. 제출되는 항목과 요청 확인하기

### `disabled`와 `readonly`

| 구분 | `disabled` | `readonly` |
| --- | --- | --- |
| 사용자 수정 | 불가능 | 불가능 |
| 일반적인 키보드 초점 | 받지 않음 | 받을 수 있음 |
| 제출 데이터 포함 | 제외 | 이름이 있으면 포함 |
| 대표 적용 대상 | 입력 칸, 선택 목록, 버튼 등 | 텍스트 입력 칸, `textarea` 등 |

```html
<input type="text" name="course" value="HTML" readonly>
<input type="text" name="internalCode" value="LAB07" disabled>
<button type="submit">제출</button>
```

위 코드는 속성 비교용 일부 코드이다. 실제 입력 화면에는 이름표를 함께 제공한다. `readonly`는 체크박스나 `select`를 읽기 전용으로 만드는 속성이 아니다.

### 실습: 

1. `lab07/exam08.html`에 빈 폼을 만들고 위 코드를 작성한다.
2. `readonly` 입력 칸은 키보드 초점을 받을 수 있는지 확인한다.
3. `disabled` 입력 칸은 키보드 초점을 받을 수 있는지 확인한다.
4. 제출 버튼을 클릭한 뒤 쿼리 스트링에 `course`와 `internalCode`가 포함되는지 확인한다.

### Network에서 데이터 확인하기

1. 외부 브라우저에서 폼을 열고 개발자 도구의 **Network(네트워크)** 탭을 연다.
2. 값을 입력하고 제출한다. 페이지 이동 후 로그가 사라지면 **Preserve log(로그 보존)**를 켜고 다시 시도한다.
3. `result.html`로 보낸 요청을 선택한다.
4. Headers에서 Request URL과 Request Method를 확인한다.
5. Payload 또는 쿼리 매개변수 영역에서 데이터의 이름과 값을 확인한다. 도구의 항목 이름은 브라우저마다 다를 수 있다.

```text
/lab07/result.html?userName=Kim&interest=html&interest=css
```

같은 이름의 값이 여러 번 나타나는 것은 체크박스 다중 선택처럼 정상적인 전송 방식일 수 있다. 한글, 공백, 줄바꿈 등이 URL에서 다른 표기로 보이는 것은 인코딩 때문이며 원래 입력값과 함께 비교한다.

POST를 시험하면 데이터 위치가 요청 본문으로 달라진다. 그러나 Live Preview의 정적 결과 페이지가 POST를 처리하는 것은 아니므로 서버 응답은 실패할 수 있다. 실제 등록 동작을 확인하려면 처리용 서버를 준비해야 한다.

## 11. 종합 실습: 문의 페이지 작성하기

### 1) 페이지 구성

`lab07/index.html`을 생성한다. 앞 단원처럼 `header`, `nav`, `main`, `footer`로 페이지를 구성하고 `main`에 문의 폼을 작성한다.

- 문의자 정보: 이름, 이메일
- 관심 분야: 여러 개 선택 가능
- 문의 정보: 유형, 내용
- 제출 주소: 같은 폴더의 `result.html`
- 제출 방식: 실습 관찰을 위한 GET

실제 문의는 보내지 않는다. 실습용 이름과 이메일로 테스트한다.

### 2) 완성 예제

```html
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>HTML 수업 문의 실습</title>
</head>
<body>
  <header>
    <h1>HTML 수업 문의 실습</h1>
    <nav aria-label="주요 메뉴">
      <ul>
        <li><a href="../lab05/index.html">학습 소개</a></li>
        <li><a href="../lab06/index.html">학습 시간표</a></li>
        <li><a href="index.html">문의 실습</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <p>필수 항목을 입력하고 제출 데이터의 구성을 확인하세요.</p>
    <p>실습용 폼입니다. 문의를 저장하거나 이메일을 발송하지 않습니다.</p>

    <form action="result.html" method="get">
      <fieldset>
        <legend>문의자 정보</legend>
        <p>
          <label for="user-name">이름 (필수)</label>
          <input type="text" id="user-name" name="userName"
                 autocomplete="name" required maxlength="40">
        </p>
        <p>
          <label for="user-email">이메일 (필수)</label>
          <input type="email" id="user-email" name="email"
                 autocomplete="email" placeholder="student@example.com" required>
        </p>
      </fieldset>

      <fieldset>
        <legend>관심 분야 (선택, 여러 개 선택 가능)</legend>
        <input type="checkbox" id="interest-html" name="interest" value="html">
        <label for="interest-html">HTML</label>
        <input type="checkbox" id="interest-css" name="interest" value="css">
        <label for="interest-css">CSS</label>
        <input type="checkbox" id="interest-js" name="interest" value="javascript">
        <label for="interest-js">JavaScript</label>
      </fieldset>

      <fieldset>
        <legend>문의 정보</legend>
        <p>
          <label for="category">문의 유형 (필수)</label>
          <select id="category" name="category" required>
            <option value="">선택하세요</option>
            <option value="course">수업 문의</option>
            <option value="practice">실습 문의</option>
            <option value="other">기타 문의</option>
          </select>
        </p>
        <p>
          <label for="message">문의 내용 (필수)</label><br>
          <textarea id="message" name="message" rows="6" cols="40"
                    required minlength="10" maxlength="500"
                    aria-describedby="message-help"></textarea>
        </p>
        <p id="message-help">10~500자로 작성하세요. 실습 파일 이름과 질문 내용을 포함해 주세요.</p>
      </fieldset>

      <p><button type="submit">입력값 확인</button></p>
    </form>
  </main>

  <footer>
    <p><small>&copy; 2026 홍길동. HTML 수업 실습 자료입니다.</small></p>
  </footer>
</body>
</html>
```

`aria-describedby="message-help"`는 문의 입력 칸과 설명 문단을 연결한다. 화면 낭독기가 입력 칸의 부가 설명을 제공하는 데 활용할 수 있다. `label`은 입력의 이름, 설명 문단은 작성 조건을 전달한다.

### 3) 결과 페이지 정리하기

앞서 작성한 `result.html`의 본문을 다음처럼 수정하면 종합 폼으로 돌아올 수 있다.

```html
<h1>요청 확인 실습 페이지</h1>
<p>주소창과 개발자 도구에서 입력 데이터가 전달되었는지 확인하세요.</p>
<p>이 페이지는 입력값을 자동으로 표시하거나 문의를 저장하지 않습니다.</p>
<p><a href="index.html">문의 실습으로 돌아가기</a></p>
```

### 4) 폼 구조 읽기

```text
form: result.html에 GET 요청
├── fieldset: 문의자 정보
│   ├── 이름: userName
│   └── 이메일: email
├── fieldset: 관심 분야
│   └── 체크박스 3개: interest
├── fieldset: 문의 정보
│   ├── 문의 유형: category
│   └── 문의 내용: message
└── button: 제출
```

### 5) 동작과 구조 점검하기

- 각 `label`의 `for`가 실제 입력 요소의 `id`와 일치하는지 확인한다.
- 같은 문서에 중복된 `id`가 없는지 확인한다.
- 제출할 입력 항목에 `name`이 있는지 확인한다.
- 필수 입력과 이메일 형식, 문의 내용 길이를 잘못 입력하여 검증을 확인한다.
- 관심 분야를 여러 개 선택하고 같은 이름의 값이 반복 전송되는지 확인한다.
- 관심 분야를 선택하지 않아도 다른 항목이 올바르면 제출되는지 확인한다.
- 마우스 없이 Tab, Shift+Tab, 방향키, Space 등을 사용하여 입력과 선택이 가능한지 확인한다.
- 주소창과 Network에서 요청 메서드와 데이터의 이름·값을 확인한다.
- 결과 페이지가 보이는 것과 서버에 문의가 저장되는 것은 다른 동작임을 설명한다.
