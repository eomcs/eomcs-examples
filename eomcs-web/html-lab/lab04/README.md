# 이미지와 미디어

- 이미지 삽입: `img`, `src`, `alt`
- 이미지 경로와 크기: `width`, `height`
- 이미지와 설명 묶기: `figure`, `figcaption`
- 이미지를 이용한 하이퍼링크
- 소리와 동영상: `audio`, `video`, `source`, `track`
- 이미지와 미디어를 추가한 소개 페이지 작성 및 경로 오류 수정

## 1. 실습 파일 준비하기

### 폴더 구성

앞 단원에서 배운 상대 경로를 사용하여 HTML 문서에서 이미지와 미디어 파일을 불러온다. 다음과 같이 파일을 준비한다.

```text
lab04/
├── exam01.html
├── exam02.html
├── exam03.html
├── exam04.html
├── exam05.html
├── index.html
├── images/
│   ├── profile.jpg      # 소개 사진
│   ├── landscape.jpg    # 풍경 사진
│   └── poster.jpg       # 동영상 재생 전 표시할 이미지
└── media/
    ├── greeting.mp3     # 짧은 인사말 녹음
    ├── introduction.mp4 # 짧은 소개 동영상
    └── introduction-ko.vtt # 동영상 자막
```

- 위 파일 이름은 예시이다. 이미지와 미디어는 실습용으로 적절한 외부 링크를 사용한다.
- 파일 이름의 확장자를 바꾸는 것만으로 파일 형식이 변환되지는 않는다.
- 예제의 이미지 크기는 예시 값이다. 준비한 이미지의 실제 가로·세로 비율에 맞게 조정한다.
- HTML 파일은 각 실습에서 Emmet의 `!`로 생성하고 `lang="ko"`와 적절한 `title`을 지정한다.
- VS Code에서 `html-lab` 폴더를 연 상태로 Live Preview를 실행한다.

## 2. 이미지 삽입: `img`

### 이미지 파일 연결하기

`img`는 HTML 문서에 이미지를 삽입하는 요소이다. 자식 내용과 종료 태그가 없는 빈 요소이므로 `</img>`를 작성하지 않는다.

```html
<img src="images/profile.jpg" alt="홍길동의 소개 사진" width="300">
```

| 속성 | 의미 |
| --- | --- |
| `src` | 표시할 이미지의 URL |
| `alt` | 이미지의 내용이나 목적을 대신 전달하는 텍스트 |
| `width` | 이미지의 표시 너비. HTML 속성에는 단위 없이 숫자로 지정 |
| `height` | 이미지의 표시 높이. HTML 속성에는 단위 없이 숫자로 지정 |

브라우저는 HTML을 읽다가 `img`를 만나면 필요한 이미지 자료를 요청하여 표시한다. `src`는 이미지를 HTML 파일 안에 복사해 넣는 기능이 아니라 이미지의 위치를 연결하는 속성이다.

### 실습: 소개 사진 표시하기

1. `lab04/exam01.html`의 기본 구조를 작성한다.
2. `body`에 다음 코드를 넣는다.

```html
<h1>홍길동의 자기소개</h1>
<img src="images/profile.jpg" alt="홍길동의 소개 사진" width="300">
<p>안녕하세요. 웹 개발을 배우고 있는 홍길동입니다.</p>
```

3. Live Preview에서 이미지가 표시되는지 확인한다.
4. `src`의 파일 이름과 실제 파일 이름을 비교한다.
5. 사진을 다른 이미지로 바꾸고 `alt`도 그 내용에 맞게 수정한다.

## 3. 대체 텍스트: `alt`

### 이미지의 목적을 텍스트로 전달하기

`alt`는 이미지를 볼 수 없을 때 그 목적이나 정보를 전달한다. 화면 낭독기가 읽을 수 있으며, 이미지 로딩 실패 시 브라우저가 대신 표시할 수도 있다.

```html
<img src="images/landscape.jpg"
     alt="해 질 무렵 주황빛 하늘 아래 펼쳐진 바다"
     width="400">
```

- `사진`, `이미지`처럼 정보가 부족한 표현보다 해당 문맥에서 필요한 내용을 작성한다.
- 파일 이름을 그대로 적기보다 이미지가 전달하는 정보를 설명한다.
- 위 설명은 실제 이미지가 해당 풍경일 때의 예이다. 준비한 사진과 일치하도록 바꾼다.
- `alt`는 일반적으로 마우스를 올렸을 때 표시하는 툴팁이 아니다.
- 복잡한 그래프처럼 짧은 문장으로 설명하기 어려운 정보는 본문에도 자세히 제공한다.

### 장식 이미지

주변 텍스트에 이미 같은 정보가 있거나 단순한 장식이라면 빈 대체 텍스트를 지정할 수 있다.

```html
<!-- 내용 전달 없이 장식 목적으로만 사용하는 경우 -->
<img src="images/landscape.jpg" alt="" width="400">
```

`alt=""`는 대체 설명이 필요 없는 이미지임을 명시한다. `alt`를 생략하는 것과 다르다. 사진 종류만 보고 결정하지 말고 해당 페이지에서의 역할을 기준으로 판단한다.

### 실습: 대체 텍스트 확인하기

1. `exam01.html`의 `alt`를 실제 이미지에 맞는 설명으로 수정한다.
2. `src`를 존재하지 않는 `images/missing.jpg`로 잠시 바꾼다.
3. 이미지가 표시되지 않을 때 브라우저가 보여 주는 내용을 확인한다. 표시 방식은 브라우저와 지정 크기에 따라 다를 수 있다.
4. 경로를 원래대로 복구한다.

## 4. 이미지 경로와 표시 크기

### 상대 경로 확인하기

현재 페이지가 `http://localhost:3000/lab04/exam01.html`이고 서버 루트가 `html-lab`이라고 가정한다.

| `src` 값 | 요청 대상 |
| --- | --- |
| `images/profile.jpg` | `/lab04/images/profile.jpg` |
| `./images/profile.jpg` | `/lab04/images/profile.jpg` |
| `/lab04/images/profile.jpg` | `/lab04/images/profile.jpg` |
| `../images/profile.jpg` | `/images/profile.jpg` |

이 폴더 구성에서는 `../images/profile.jpg`가 준비한 사진의 경로와 다르다. 상대 경로는 CSS의 위치나 터미널의 작업 폴더가 아닌 현재 HTML 문서의 URL을 기준으로 해석된다. 여기서는 `base` 요소를 사용하지 않는다.

### 원본 크기와 표시 크기

```html
<img src="images/profile.jpg" alt="홍길동의 소개 사진" width="200">
<img src="images/profile.jpg" alt="홍길동의 소개 사진" width="400">
```

- 두 요소는 같은 원본 파일을 서로 다른 너비로 표시한다.
- 별도의 CSS가 없을 때 너비만 지정하면 높이는 이미지의 비율에 따라 정해진다.
- `width="300"`은 300 CSS 픽셀을 뜻한다. `width="300px"`처럼 단위를 붙이지 않는다.
- 화면에서 작게 표시해도 원본 파일의 용량이 줄어드는 것은 아니다.
- 작은 원본을 크게 확대하면 흐릿하게 보일 수 있다.

### 너비와 높이를 함께 지정하기

원본이 가로 1200, 세로 800인 이미지라면 같은 비율로 다음처럼 지정할 수 있다.

```html
<img src="images/landscape.jpg"
     alt="여행 중 촬영한 바다 풍경"
     width="600" height="400">
```

너비와 높이는 브라우저가 이미지가 로딩되기 전 공간을 잡는 데도 도움이 된다. 원본과 다른 비율을 강제로 지정하면 이미지가 찌그러질 수 있다.

작은 화면에서 이미지가 넘치지 않도록 하려면 CSS를 함께 사용할 수 있다.

```html
<img src="images/landscape.jpg"
     alt="여행 중 촬영한 바다 풍경"
     width="600" height="400"
     style="max-width: 100%; height: auto;">
```

- `max-width: 100%`: 부모 영역보다 넓어지지 않도록 제한한다.
- `height: auto`: 너비 변화에 맞춰 높이를 비율에 따라 조정한다.
- viewport 설정만으로 큰 이미지가 자동으로 화면 너비에 맞춰지는 것은 아니다.

### 실습: 크기와 비율 비교하기

1. `lab04/exam02.html`에 같은 이미지를 두 번 넣고 너비를 각각 200과 400으로 지정한다.
2. 이미지의 원본 파일은 같은데 화면 크기가 달라지는지 확인한다.
3. 너비와 높이를 원본과 다른 비율로 지정하여 왜곡을 관찰한 뒤 올바른 비율로 복구한다.
4. `max-width: 100%; height: auto;`를 적용하고 브라우저 창을 좁혀 본다.

## 5. 이미지와 설명 묶기: `figure`, `figcaption`

### 하나의 자료와 설명 구성하기

- `figure`는 본문에서 하나의 독립적인 자료로 다룰 수 있는 이미지, 도표, 코드 등을 묶는다.
- `figcaption`은 해당 자료의 제목이나 설명을 나타낸다.
- `figcaption`은 `figure`의 첫 번째 또는 마지막 자식으로 하나만 작성한다.

```html
<figure>
  <img src="images/landscape.jpg"
       alt="주황빛 하늘과 잔잔한 바다가 보이는 해변"
       width="400">
  <figcaption>여행 중 촬영한 해 질 무렵의 해변</figcaption>
</figure>
```

### `alt`와 `figcaption`의 차이

| 구분 | `alt` | `figcaption` |
| --- | --- | --- |
| 작성 위치 | `img`의 속성 | `figure` 안의 요소 |
| 역할 | 이미지의 목적과 정보를 대신 전달 | 자료에 대한 설명이나 제목 제공 |
| 일반적인 화면 표시 | 이미지가 정상 표시되면 별도 본문으로 보이지 않음 | 이미지와 함께 텍스트로 표시 |

두 내용을 무조건 동일하게 반복하기보다, 이미지의 시각적 정보와 촬영 맥락 등 서로 필요한 내용을 나누어 작성한다. 모든 이미지를 반드시 `figure`로 감쌀 필요는 없다.

### 실습: 사진과 설명 작성하기

1. `lab04/exam03.html`의 `body`에 제목과 위 예제를 작성한다.
2. `alt`는 사진의 내용을, `figcaption`은 촬영 상황을 설명하도록 수정한다.
3. `figcaption`을 `figure`의 맨 앞으로 옮겨 표시 위치를 비교한다.

## 6. 이미지에 하이퍼링크 연결하기

### 이미지를 링크의 내용으로 사용하기

앞 단원에서 배운 `a` 안에 `img`를 넣으면 이미지를 선택하여 다른 자료로 이동할 수 있다.

```html
<a href="../lab03/index.html">
  <img src="images/profile.jpg" alt="홍길동의 소개 페이지로 이동" width="150">
</a>
```

- `a`의 `href`는 클릭했을 때 이동할 주소이다.
- `img`의 `src`는 현재 화면에 표시할 이미지의 주소이다.
- 이미지가 링크의 유일한 내용이면 `alt`가 링크의 목적도 알 수 있게 작성한다.
- 예제의 소개 페이지는 앞 단원의 종합 실습에서 만든 파일이다.

원본 사진을 여는 링크도 만들 수 있다.

```html
<a href="images/landscape.jpg">
  <img src="images/landscape.jpg" alt="바다 풍경 원본 이미지 보기" width="200">
</a>
```

### 실습: 사진 선택으로 이동하기

1. `exam03.html`의 이미지에 원본 파일로 연결하는 `a`를 추가한다.
2. 사진을 선택하여 주소창이 이미지 URL로 바뀌는지 확인한다.
3. 브라우저의 뒤로 가기로 돌아온다.
4. `Tab`과 `Enter` 키로도 링크를 사용할 수 있는지 확인한다.

## 7. 소리 재생: `audio`

### 재생 컨트롤 제공하기

```html
<audio src="media/greeting.mp3" controls>
  이 브라우저는 audio 요소를 지원하지 않습니다.
</audio>
```

`audio`는 소리 자료를 재생하는 요소이다. `img`와 달리 종료 태그를 작성한다. `controls`를 지정하면 브라우저가 재생·일시 정지·볼륨 등의 조작 기능을 제공한다.

| 속성 | 역할 |
| --- | --- |
| `src` | 소리 파일의 URL |
| `controls` | 재생 컨트롤 표시 |
| `loop` | 재생이 끝나면 반복 |
| `muted` | 처음에 음소거 상태로 설정 |
| `autoplay` | 준비되면 자동 재생하도록 요청 |
| `preload` | 미리 불러올 데이터에 대한 힌트: `none`, `metadata`, `auto` |

`controls`, `loop`, `muted`, `autoplay`는 불리언 속성이다. 예를 들어 `autoplay="false"`도 속성이 존재하므로 자동 재생을 요청한다. 사용하지 않으려면 속성을 제거한다.

자동 재생은 브라우저 정책과 사용자 설정에 따라 차단될 수 있다. 이번 실습에서는 `controls`로 사용자가 직접 재생하도록 한다. `preload`는 힌트이므로 실제 다운로드 방식은 브라우저가 결정한다.

### `source`로 재생 후보 제공하기

```html
<audio controls preload="metadata">
  <source src="media/greeting.mp3" type="audio/mpeg">
  이 브라우저는 audio 요소를 지원하지 않습니다.
</audio>
<p><a href="media/greeting.mp3">인사말 오디오 파일 열기</a></p>
```

- `source`는 파일의 위치와 미디어 유형을 제공하는 빈 요소이다.
- 다른 형식의 파일도 실제로 준비했다면 `source`를 추가하여 여러 후보를 제공할 수 있다. 재생 목록이 아니라 같은 내용의 대체 형식이다.
- `type`은 브라우저가 지원 가능 여부를 판단하는 데 도움이 된다. 파일 형식을 변환하지는 않는다.
- `audio` 안의 안내 문장은 요소 자체를 지원하지 않는 브라우저를 위한 것이다. 파일 경로 오류가 발생했다고 반드시 표시되는 것은 아니다.

### 실습: 인사말 듣기

1. `lab04/exam04.html`에 제목과 위 오디오 예제를 작성한다.
2. 직접 재생·일시 정지하고 볼륨을 조절한다.
3. 소리를 듣기 어려운 사용자도 내용을 알 수 있도록 실제 녹음 내용을 본문에 작성한다.

```html
<h2>인사말 대본</h2>
<p>안녕하세요. 홍길동입니다. HTML로 나의 소개 페이지를 만들고 있습니다.</p>
```

## 8. 동영상 재생: `video`

### 동영상과 미리 보기 이미지

```html
<video controls width="640" preload="metadata"
       poster="images/poster.jpg"
       style="max-width: 100%; height: auto;">
  <source src="media/introduction.mp4" type="video/mp4">
  이 브라우저는 video 요소를 지원하지 않습니다.
</video>
<p><a href="media/introduction.mp4">소개 동영상 파일 열기</a></p>
```

- `video`는 동영상을 재생하는 요소이다. `controls`, `loop`, `muted`, `autoplay`, `preload`는 `audio`와 공통으로 사용할 수 있다.
- `width`, `height`는 표시 크기를 지정한다.
- `poster`는 동영상 재생 전에 표시할 이미지의 URL이다.
- MP4는 컨테이너 형식이다. 확장자가 같아도 내부 영상·음성 코덱이 브라우저에서 지원되지 않으면 재생에 실패할 수 있다.
- 모바일에서 페이지 안의 재생을 요청할 때는 `playsinline` 속성을 사용할 수 있다. 실제 동작은 브라우저 환경에 따라 달라진다.

### 자막 연결하기: `track`

`track`은 동영상에 자막 등의 시간 기반 텍스트를 연결하는 빈 요소이다. 다음 요소를 `video` 안의 `source` 다음에 추가한다.

```html
<track src="media/introduction-ko.vtt"
       kind="captions" srclang="ko" label="한국어" default>
```

| 속성 | 의미 |
| --- | --- |
| `src` | 자막 파일 URL |
| `kind="captions"` | 대사와 이해에 필요한 소리 정보를 제공 |
| `srclang="ko"` | 자막 언어가 한국어임을 지정 |
| `label="한국어"` | 자막 선택 메뉴에 표시할 이름 |
| `default` | 사용자 선호 설정 등이 없을 때 기본으로 사용할 트랙 지정 |

번역 자막처럼 대사 중심의 자막에는 `kind="subtitles"`를 사용할 수 있다. `default`를 지정해도 사용자 설정에 따라 표시 여부가 달라질 수 있다.

`media/introduction-ko.vtt`를 UTF-8 텍스트 파일로 만들고 다음처럼 작성한다. 시간과 내용은 실제 영상에 맞춘다.

```text
WEBVTT

00:00:00.000 --> 00:00:03.000
안녕하세요. 홍길동입니다.

00:00:03.000 --> 00:00:06.000
오늘은 HTML로 만든 소개 페이지를 보여 드리겠습니다.
```

`WEBVTT` 다음과 자막 항목 사이에는 빈 줄을 둔다. 자막 외에 화면에서만 전달되는 중요한 정보가 있다면 본문 설명이나 음성 설명도 제공한다.

### 실습: 소개 동영상과 자막 재생하기

1. `lab04/exam05.html`에 동영상 예제를 작성한다.
2. `track`을 연결하고 실제 영상 내용에 맞게 VTT 파일을 작성한다.
3. Live Preview의 HTTP 주소로 접속하여 재생 전 포스터와 재생 컨트롤을 확인한다.
4. 브라우저의 자막 선택 메뉴에서 한국어를 선택하고 시간에 맞게 표시되는지 확인한다.
5. 창의 너비를 줄여 동영상이 부모 영역을 넘치지 않는지 확인한다.

## 9. 이미지와 미디어가 표시되지 않을 때

### 요청 주소부터 확인하기

1. 브라우저의 개발자 도구에서 **Network(네트워크)** 탭을 연다.
2. 페이지를 새로 고친다. 오디오·동영상은 필요하면 재생 버튼도 누른다.
3. 이미지나 미디어 파일의 요청 URL과 상태 코드를 확인한다.
4. 요청 URL의 경로를 실제 파일 위치와 비교한다.

| 증상 | 확인할 내용 |
| --- | --- |
| `404 Not Found` | 파일 이름, 폴더 위치, 확장자, 대소문자, 상대 경로 |
| 연결 거부 | Live Preview 서버 실행 여부와 포트 번호 |
| `200 OK`인데 표시·재생 실패 | 응답이 실제 이미지·미디어인지, 파일 손상과 형식·코덱 지원 여부 |
| 자막이 표시되지 않음 | VTT 경로, 파일 형식, 시간 구간, 자막 메뉴 설정 |
| 이미지가 찌그러짐 | 표시 너비·높이와 원본 비율 |

미디어는 일부 구간만 요청할 수 있으므로 `206 Partial Content`도 정상 응답일 수 있다. 캐시를 확인하는 `304 Not Modified` 역시 오류가 아니다.

### 실습: 잘못된 경로 수정하기

1. `exam01.html`의 `images/profile.jpg`를 `image/profile.jpg`로 변경한다.
2. 새로 고친 뒤 Network에서 실패한 요청 주소와 상태를 확인한다.
3. 실제 폴더 이름이 `images`인지 확인하고 코드를 복구한다.
4. 정상적으로 이미지가 표시되는지 다시 확인한다.

## 10. 종합 실습: 이미지와 미디어를 추가한 소개 페이지

### 1) 기본 구조와 메뉴 작성하기

`lab04/index.html`의 기본 구조를 만들고 문서 언어를 `ko`, 제목을 `홍길동의 미디어 소개`로 지정한다. `body`에 다음 코드를 작성한다.

```html
<h1>홍길동의 미디어 소개</h1>
<ul>
  <li><a href="#photo">소개 사진</a></li>
  <li><a href="#audio">인사말 듣기</a></li>
  <li><a href="#video">소개 영상</a></li>
  <li><a href="../lab03/index.html">이전 소개 페이지</a></li>
</ul>
```

### 2) 설명이 있는 사진 추가하기

```html
<h2 id="photo">소개 사진</h2>
<figure>
  <img src="images/profile.jpg" alt="홍길동의 소개 사진"
       width="300" style="max-width: 100%; height: auto;">
  <figcaption>웹 개발을 배우고 있는 홍길동입니다.</figcaption>
</figure>
```

실제 사진과 페이지 목적에 맞춰 대체 텍스트와 설명을 조정한다.

### 3) 인사말과 대본 추가하기

```html
<h2 id="audio">인사말 듣기</h2>
<audio controls preload="metadata">
  <source src="media/greeting.mp3" type="audio/mpeg">
  이 브라우저는 audio 요소를 지원하지 않습니다.
</audio>
<p>안녕하세요. 홍길동입니다. HTML로 나의 소개 페이지를 만들고 있습니다.</p>
```

본문은 실제 녹음의 내용을 전달하도록 수정한다.

### 4) 영상과 자막 추가하기

```html
<h2 id="video">소개 영상</h2>
<video controls playsinline width="640" poster="images/poster.jpg"
       preload="metadata" style="max-width: 100%; height: auto;">
  <source src="media/introduction.mp4" type="video/mp4">
  <track src="media/introduction-ko.vtt"
         kind="captions" srclang="ko" label="한국어" default>
  이 브라우저는 video 요소를 지원하지 않습니다.
</video>
<p><a href="media/introduction.mp4">소개 영상 파일 열기</a></p>
```

### 5) 동작 점검하기

- 모든 이미지와 미디어 경로가 준비한 파일과 일치하는지 확인한다.
- 이미지의 대체 텍스트가 실제 내용과 목적에 맞는지 확인한다.
- 사진이 찌그러지거나 작은 화면 밖으로 넘치지 않는지 확인한다.
- 오디오·동영상을 직접 재생하고 일시 정지할 수 있는지 확인한다.
- 녹음 내용은 대본으로, 영상의 대사와 필요한 소리 정보는 자막으로도 확인할 수 있는지 점검한다.
- 목차 링크와 이전 소개 페이지 링크가 동작하는지 확인한다.
- Network에서 미디어 요청에 실패한 항목이 있는지 확인하고 경로를 수정한다.
