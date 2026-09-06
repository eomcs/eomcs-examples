# 표로 데이터 표현하기

- 표의 구조: `table`, `tr`, `th`, `td`
- 표 제목과 행 그룹: `caption`, `thead`, `tbody`, `tfoot`
- 제목 셀과 데이터의 관계: `scope`
- 셀 병합: `colspan`, `rowspan`
- 표의 의미와 CSS 스타일 구분
- 레이아웃용 표의 문제
- 주간 학습 시간표 페이지 작성

## 1. 표가 필요한 경우

### 행과 열로 관계 표현하기

표는 시간표, 가격 비교, 성적표처럼 **행과 열의 교차 관계로 이해하는 데이터**를 표현한다.

| 시간 | 월요일 | 화요일 |
| --- | --- | --- |
| 09:00~10:00 | HTML 기초 | 목록과 링크 |
| 10:00~11:00 | 텍스트 요소 | 이미지와 미디어 |

위 표에서 `월요일` 열과 `09:00~10:00` 행이 만나는 셀은 해당 요일과 시간의 수업을 나타낸다.

- 순서 없는 항목 나열은 `ul`, 순서 있는 절차는 `ol`로 표현한다.
- 용어와 설명의 대응 관계는 `dl`로 표현할 수 있다.
- 여러 항목을 같은 기준으로 비교하거나 두 방향의 관계를 읽어야 한다면 표가 적합하다.
- 화면을 여러 칸으로 나누고 싶다는 이유만으로 표를 사용하지 않는다.

## 2. 표의 기본 구조: `table`, `tr`, `th`, `td`

| 요소 | 역할 |
| --- | --- |
| `table` | 표 전체를 묶음 |
| `tr` | 하나의 행(table row)을 나타냄 |
| `th` | 제목 셀(table header)을 나타냄 |
| `td` | 데이터 셀(table data)을 나타냄 | 

### 실습: 과목 안내 표 작성하기

1. `lab06/exam01.html`을 생성하고 Emmet의 `!`로 기본 구조를 작성한다.
2. `lang`을 `ko`, 문서 제목을 `학습 과목 안내`로 수정한다.
3. `body`에 `h1`과 다음 표를 작성한다.

```html
<table>
  <tr>
    <th scope="col">과목</th>
    <th scope="col">학습 내용</th>
  </tr>
  <tr>
    <td>HTML</td>
    <td>문서의 구조와 의미</td>
  </tr>
  <tr>
    <td>CSS</td>
    <td>색상과 화면 배치</td>
  </tr>
</table>
```

- 첫 번째 `tr`은 열 제목을, 나머지 `tr`은 데이터를 담는다.
- 셀은 각 행의 왼쪽부터 순서대로 작성한다.
- `th`와 `td`는 `tr` 안에 작성한다. `table` 바로 안에 셀을 넣지 않는다.
- `th`는 기본적으로 굵게 표시될 수 있지만, 굵은 글자를 만들기 위한 요소가 아니다.
- `scope="col"`은 해당 제목 셀이 열 제목임을 나타낸다. 뒤에서 행 제목과 비교한다.

기본 스타일에서는 테두리가 보이지 않을 수 있다. 테두리 유무와 관계없이 `table`로 작성한 내용은 표이다.

## 3. 표 제목과 행 그룹: `caption`, `thead`, `tbody`, `tfoot`

`caption`은 표 전체의 제목이나 간단한 설명이다. `table`의 첫 번째 자식으로 하나 작성한다.

### 머리글·본문·바닥글 그룹

| 요소 | 역할 | 예 |
| --- | --- | --- |
| `thead` | 표의 머리글 행 그룹 | 열 제목 행 |
| `tbody` | 표의 본문 행 그룹 | 과목별 데이터 행 |
| `tfoot` | 표의 요약·합계 행 그룹 | 총 학습 시간 |

- 각 그룹 안에 `tr`을 작성하고, 그 안에 `th`나 `td`를 작성한다.
- 이번 실습에서는 `caption` → `thead` → `tbody` → `tfoot` 순서로 작성한다.
- `tfoot`은 합계 등 필요한 내용이 있을 때 사용한다. 모든 표에 넣을 필요는 없다.
- `thead` 안의 셀이 자동으로 `th`가 되는 것은 아니다. 셀의 역할에 맞게 태그를 선택한다.
- HTML에서 `tr`을 `table` 바로 아래에 작성하면 브라우저가 DOM에 `tbody`를 추가할 수 있다. 코드의 구조를 명확히 하려면 그룹을 직접 작성한다.

### 실습: 구조를 명시하기

1. `exam01.html`에 `caption`을 추가한다.
2. 제목 행은 `thead`, 데이터 행은 `tbody`로 묶는다.

```html
<table>
  <caption>웹 개발 학습 과목</caption>
  <thead>
    <tr>
      <th scope="col">과목</th>
      <th scope="col">학습 내용</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>HTML</td>
      <td>문서의 구조와 의미</td>
    </tr>
    <tr>
      <td>CSS</td>
      <td>색상과 화면 배치</td>
    </tr>
  </tbody>
</table>
```

페이지의 `h1`이 있더라도 표만의 제목을 `caption`으로 제공하면 어떤 데이터인지 파악하기 쉽다. `caption`은 표에 연결된 제목이며, 바로 앞에 일반 문단으로 제목을 쓰는 것과 의미가 다르다.

## 4. 제목 셀의 방향: `scope`

### 행 제목과 열 제목 구분하기

시간표의 `월요일`은 열 제목이고, `09:00~10:00`은 행 제목이다. 제목 셀에 `scope`를 지정하여 그 관계를 명확히 표현한다.

### 실습: 시간표 작성하기

1. `lab06/exam02.html`에 기본 구조와 다음 시간표를 작성한다.
2. 수요일 열을 추가한다. 제목 행뿐 아니라 모든 데이터 행에도 셀을 추가한다.
3. 각 수업이 어느 요일과 시간에 해당하는지 행·열 제목으로 설명한다.
4. 제목 셀을 `td`로 바꾸었다가 `th`로 복구하면서 의미와 기본 표시의 차이를 확인한다.

```html
<table>
  <caption>오전 학습 시간표</caption>
  <thead>
    <tr>
      <th scope="col">시간</th>
      <th scope="col">월요일</th>
      <th scope="col">화요일</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">09:00~10:00</th>
      <td>HTML 기초</td>
      <td>목록과 링크</td>
    </tr>
    <tr>
      <th scope="row">10:00~11:00</th>
      <td>텍스트 요소</td>
      <td>이미지와 미디어</td>
    </tr>
  </tbody>
</table>
```

- `scope="col"`: 해당 열의 제목임을 나타낸다.
- `scope="row"`: 해당 행의 제목임을 나타낸다.
- `scope`는 `th`에 작성하며 화면의 배치를 바꾸지 않는다.
- 제목과 데이터의 관계는 화면 낭독기가 표를 읽는 데 활용될 수 있다. 실제 읽기 방식은 보조 기술과 설정에 따라 다르다.
- 제목을 나타내려고 `<td><strong>월요일</strong></td>`로 작성하는 것보다 `th`를 사용한다.

복잡한 표에는 행·열 그룹을 위한 `rowgroup`, `colgroup`이나 `id`·`headers`를 이용한 연결이 필요할 수 있다. 이번 실습은 단순한 행·열 제목 구조를 사용한다.

## 5. 가로 셀 병합: `colspan`

`colspan`은 셀 하나가 차지할 열의 수를 지정한다. `th` 또는 `td`에 작성한다.

### 실습: 공통 일정 추가하기

1. `lab06/exam03.html`에 다음 예제를 작성한다.
2. 점심시간 행의 배치를 확인한다.
3. 수요일을 추가하면 점심시간의 `colspan`을 얼마로 바꿔야 하는지 계산하고 수정한다.

```html
<table>
  <caption>행사 일정</caption>
  <thead>
    <tr>
      <th scope="col">시간</th>
      <th scope="col">월요일</th>
      <th scope="col">화요일</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">09:00~10:00</th>
      <td>HTML 기초</td>
      <td>목록과 링크</td>
    </tr>
    <tr>
      <th scope="row">12:00~13:00</th>
      <td colspan="2">점심시간</td>
    </tr>
  </tbody>
</table>
```

- `colspan="2"`인 점심시간 셀이 월요일과 화요일의 두 열을 차지한다.
- 점심시간 행에는 셀 태그가 두 개지만, 차지하는 열은 `1 + 2 = 3`개이다.
- 병합으로 차지한 자리에 빈 `td`를 추가하지 않는다. 추가하면 원하지 않는 열이 생길 수 있다.
- `colspan`은 열 수를 뜻하며 픽셀 너비가 아니다.

## 6. 세로 셀 병합: `rowspan`

`rowspan`은 셀 하나가 차지할 행의 수를 지정한다. 병합이 시작되는 행에서 셀을 작성한다.

### 실습: 세로 병합 확인하기

1. `lab06/exam04.html`에 다음 예제를 작성한다.
2. 두 번째 행에 장소 셀이 없어도 표의 열이 맞는 이유를 설명한다.
3. 장소 병합을 제거하고 각 행에 `제1실습실`을 반복해서 작성한다.
4. 병합한 표와 반복한 표 중 내용을 이해하기 쉬운 방식을 비교한다.

```html
<table>
  <caption>실습실 사용 일정</caption>
  <thead>
    <tr>
      <th scope="col">시간</th>
      <th scope="col">활동</th>
      <th scope="col">장소</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">09:00~10:00</th>
      <td>HTML 설명</td>
      <td rowspan="2">제1실습실</td>
    </tr>
    <tr>
      <th scope="row">10:00~11:00</th>
      <td>HTML 실습</td>
    </tr>
  </tbody>
</table>
```

- `rowspan="2"`는 현재 행을 포함한 두 행에 걸쳐 셀을 표시한다.
- 두 번째 행의 장소 자리는 이미 위의 셀이 차지하므로 별도의 `td`를 작성하지 않는다.
- 행 그룹을 가로질러 병합하지 않는다. 예를 들어 `thead`의 셀을 `tbody`까지 늘리지 않는다.
- 병합이 많아지면 표를 읽고 수정하기 어려워진다. 같은 값을 반복해서 쓰는 단순한 표가 더 적합한 경우도 있다.

## 7. 합계 표현: `tfoot`

### 실습: 학습 시간 합계 작성하기

1. `lab06/exam05.html`에 위 표를 작성한다.
2. JavaScript 학습 2시간을 추가하고 합계를 7시간으로 수정한다.
3. `tfoot`과 페이지의 `footer`가 서로 다른 요소임을 설명한다. `tfoot`은 표의 행 그룹이다.

```html
<table>
  <caption>과목별 학습 시간</caption>
  <thead>
    <tr>
      <th scope="col">과목</th>
      <th scope="col">시간</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">HTML</th>
      <td>3시간</td>
    </tr>
    <tr>
      <th scope="row">CSS</th>
      <td>2시간</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <th scope="row">합계</th>
      <td>5시간</td>
    </tr>
  </tfoot>
</table>
```

`tfoot`은 합계라는 역할의 행을 묶지만 계산을 수행하지는 않는다. 학습 시간을 바꾸면 합계도 직접 수정해야 한다. 자동 계산은 JavaScript나 서버 프로그램 등으로 구현한다.

## 8. 표의 구조와 스타일 구분하기

### 테두리와 간격은 CSS로 지정하기

셀의 경계를 확인하려면 `head` 안에 다음 `style`을 추가한다. 이번 CSS는 표 구조를 관찰하기 위한 보조 설정이다.

```html
<style>
  table {
    border-collapse: collapse;
  }

  th, td {
    border: 1px solid #777;
    padding: 8px;
  }

  th {
    background-color: #eee;
  }

  caption {
    font-weight: bold;
    margin-bottom: 8px;
  }
</style>
```

| CSS | 역할 |
| --- | --- |
| `border-collapse: collapse` | 인접한 셀 테두리를 합쳐 표시 |
| `border` | 테두리의 두께·모양·색상 지정 |
| `padding` | 셀 내용과 테두리 사이의 안쪽 여백 지정 |
| `background-color` | 배경색 지정 |

예전 예제에서 볼 수 있는 `border`, `cellpadding`, `cellspacing` 등의 HTML 표현용 속성 대신 CSS로 모양을 지정한다. 테두리를 없애더라도 표의 의미는 유지된다.

### 작은 화면에서 표 확인하기

열이 많은 표는 좁은 화면을 넘칠 수 있다. 필요한 경우 표를 감싼 영역에서 가로 스크롤을 제공한다.

```html
<div style="overflow-x: auto;">
  <table>
    <!-- 표 제목과 행을 작성한다. -->
  </table>
</div>
```

이 코드는 배치용 래퍼의 예이다. `div`는 스크롤 영역을 만드는 용도이며, 데이터의 의미는 안의 `table`이 표현한다.

## 9. 페이지 배치에 표를 사용하지 않기

### 데이터 표와 페이지 레이아웃

과거에는 페이지를 머리말·메뉴·본문으로 나누기 위해 표를 사용하기도 했다. 새로운 페이지는 내용의 역할에 맞는 HTML과 CSS로 배치한다.

```html
<!-- 배치를 위해 표를 사용하는 예: 이번 실습에서는 사용하지 않는다. -->
<table>
  <tr>
    <td>사이트 이름</td>
    <td>주요 메뉴</td>
  </tr>
  <tr>
    <td>보충 자료</td>
    <td>본문 내용</td>
  </tr>
</table>
```

- 머리말과 본문은 비교할 행·열 데이터가 아니다.
- 화면 낭독기에서 불필요한 표 구조가 전달될 수 있다.
- 작은 화면에 맞춰 배치를 바꾸거나 읽는 순서를 관리하기 어려워질 수 있다.

앞 단원에서 배운 의미 있는 요소로 구조를 작성하고, 위치는 CSS의 Flexbox나 Grid 등으로 지정한다.

```html
<header>
  <h1>홍길동의 학습 기록</h1>
</header>
<main>
  <h2>이번 주 학습 계획</h2>
  <p>매일 배운 내용을 실습합니다.</p>
</main>
<aside>
  <h2>보충 자료</h2>
  <p>지난 실습 기록을 참고하세요.</p>
</aside>
```

`main` 안에서 학습 시간표라는 데이터를 나타낼 때는 `table`을 사용하는 것이 적절하다.

## 10. 종합 실습: 주간 학습 시간표 페이지

### 1) 파일과 내용 준비하기

1. `lab06/index.html`을 생성한다.
2. `lab05`에서 배운 `header`, `nav`, `main`, `footer`로 페이지를 구성한다.
3. 시간과 요일의 관계를 표로 표현한다.
4. 점심시간에 가로 병합을 적용한다. 세로 병합은 앞의 별도 예제로 연습하고, 종합 시간표는 단순한 구조로 유지한다.

### 2) 완성 예제

```html
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>홍길동의 주간 학습 시간표</title>
  <style>
    table {
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #777;
      padding: 8px;
    }
    th {
      background-color: #eee;
    }
    caption {
      font-weight: bold;
      margin-bottom: 8px;
    }
  </style>
</head>
<body>
  <header>
    <h1>홍길동의 주간 학습 시간표</h1>
    <nav aria-label="주요 메뉴">
      <ul>
        <li><a href="../lab05/index.html">학습 소개</a></li>
        <li><a href="index.html">주간 시간표</a></li>
        <li><a href="../lab03/contact.html">문의 안내</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <p>월요일부터 수요일까지의 학습 계획입니다. 점심시간에는 수업을 진행하지 않습니다.</p>
    <div style="overflow-x: auto;">
      <table>
        <caption>주간 학습 계획 — 월요일~수요일</caption>
        <thead>
          <tr>
            <th scope="col">시간</th>
            <th scope="col">월요일</th>
            <th scope="col">화요일</th>
            <th scope="col">수요일</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th scope="row">09:00~10:00</th>
            <td>HTML 기본 구조</td>
            <td>이미지와 미디어</td>
            <td>표의 기본 구조</td>
          </tr>
          <tr>
            <th scope="row">10:00~11:00</th>
            <td>텍스트 요소</td>
            <td>시맨틱 HTML</td>
            <td>제목 셀과 데이터 셀</td>
          </tr>
          <tr>
            <th scope="row">11:00~12:00</th>
            <td>목록과 링크</td>
            <td>소개 페이지 재구성</td>
            <td>셀 병합</td>
          </tr>
          <tr>
            <th scope="row">12:00~13:00</th>
            <td colspan="3">점심시간</td>
          </tr>
          <tr>
            <th scope="row">13:00~14:00</th>
            <td>링크 연결 실습</td>
            <td>미디어 동작 확인</td>
            <td>시간표 완성 및 점검</td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <th scope="row">학습 시간 합계</th>
            <td>4시간</td>
            <td>4시간</td>
            <td>4시간</td>
          </tr>
        </tfoot>
      </table>
    </div>
  </main>

  <footer>
    <p><small>&copy; 2026 홍길동. HTML 수업 실습 자료입니다.</small></p>
  </footer>
</body>
</html>
```

### 3) 표의 구조 읽기

```text
 table
 ├── caption: 주간 학습 계획
 ├── thead
 │   └── tr: 시간·요일의 열 제목
 ├── tbody
 │   ├── tr: 09:00~10:00
 │   ├── tr: 10:00~11:00
 │   ├── tr: 11:00~12:00
 │   ├── tr: 12:00~13:00 (점심시간 가로 병합)
 │   └── tr: 13:00~14:00
 └── tfoot
     └── tr: 요일별 학습 시간 합계
```

점심시간 행은 시간 제목 한 칸과 병합한 데이터 세 칸으로 전체 네 열을 채운다. 합계는 점심시간을 제외한 하루 4시간이다.

### 4) 자신의 학습 계획으로 수정하기

1. 과목 이름을 자신의 학습 계획으로 바꾼다.
2. 목요일 열을 추가한다. `thead`, 일반 데이터 행, `tfoot`에 각각 셀을 추가한다.
3. 점심시간의 `colspan`을 4로 수정한다.
4. 학습 시간대를 추가했다면 합계를 다시 계산한다.

### 5) 동작과 구조 점검하기

- `caption`만 읽어도 표의 내용을 예상할 수 있는지 확인한다.
- 열 제목과 행 제목이 `th`이고 `scope` 방향이 올바른지 확인한다.
- 병합을 고려했을 때 모든 행이 같은 열 격자에 맞는지 확인한다.
- `thead`, `tbody`, `tfoot`의 역할에 맞게 행이 들어 있는지 확인한다.
- 개발자 도구에서 셀이 잘못 중첩되지 않았는지 확인한다.
- 브라우저 창을 좁혀 긴 내용과 가로 스크롤의 동작을 확인한다.
- 학습 소개와 문의 안내 링크가 실제 파일로 연결되는지 확인한다.
- CSS를 제거해도 데이터의 제목과 관계를 읽을 수 있는지 확인한다.
