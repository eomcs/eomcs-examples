# app01. 리액트 시작하기

## 개요

### 전통적인 웹 애플리케이션(MPA; Multi-Page Application)

**서버 렌더링 방식**:

1. 사용자가 브라우저에서 URL에 접속하면 요청이 웹 서버로 전송된다. 
2. 서버는 HTML 생성 및 그와 연관된 CSS, 자바스크립트 파일을 응답한다.
3. 약간의 네트워크 지연 후에 사용자는 렌더링된 HTML을 보고 상호작용을 시작한다.
4. 페이지를 이동할 때마다 이 과정이 반복된다.

**특징**:

- 서버에서 HTML 생성(렌더링) → 브라우저는 페이지를 화면에 그리는 최소한의 역할만 담당
- 매 페이지 이동마다 → HTML 전체를 다시 받고 → 브라우저 전체 화면 새로 고침 

### SPA(Single Page Application)

**클라이언트 렌더링 방식**:

1. 사용자가 브라우저에서 URL에 접속하면 요청이 웹 서버로 전송된다.
2. 서버는 작은 HTML 파일과 상대적으로 큰 자바스크립트 파일을 응답한다.
3. 브라우저는 짧은 네트워크/렌더링 지연 후, 전달받은 자바스크립트 파일을 실행해서 HTML과 CSS로 애플리케이션 전체를 동적으로 렌더링한다.
4. 이후 페이지 전환은 새 파일을 위한 추가 서버 요청 없이, 처음에 로드된 자바스크립트가 새로운 페이지를 동적으로 렌더링한다.
 
**특징**:

- 브라우저에서 자바스크립트가 HTML 생성(렌더링) → 서버는 HTML을 생성하지 않음
- 매 페이지 이동마다 → HTML 전체를 다시 받지 않고 → 브라우저 화면 새로 고침 없이 동적으로 렌더링
- 페이지 이동이 매우 빠르며, 화면 깜박임이 적어 사용자 경험(UX)이 우수함
- 필요한 데이터만 API를 통해 서버에서 받아옴

**대표 기술**:

- 1세대(2005~2010): jQuery, Backbone.js, Sammy.js
  - DOM을 JavaScript로 직접 조작  
  - Ajax를 이용한 부분 화면 갱신
  - SPA의 초기 형태
  - 화면을 직접 수정
- 2세대(2010~2015): AngularJS, Ember.js, Knockout.js
  - 데이터가 변경되면 자동으로 화면이 변경
  - MVC
  - MVVM
  - 양방향 데이터 바인딩
- 3세대(2013~2018): React, Vue.js, Angular (2 이상)
  - React가 등장하면서 SPA의 패러다임이 크게 바뀜
  - 컴포넌트 기반
  - Virtual DOM
  - 단방향 데이터 흐름
  - 함수형 프로그래밍
  - 컴포넌트 기반 개발이 표준이 됨
- 4세대(2018~2023): Next.js, Nuxt, SvelteKit
  - SPA의 단점(SEO, 초기 로딩, 성능) 해결
  - SSR 등장
  - SSR + CSR 혼합
- 5세대(2023~2026): Next.js(App Router), Remix, TanStack Start, React Router 7 (Framework Mode)
  - 필요한 화면마다 CSR, SSR, SSG, RSC를 선택적으로 적용
  - 핵심 기술:
    - React Server Components(RSC)
    - Server Actions
    - Streaming Rendering
    - Partial Prerendering(PPR)
    - Edge Runtime

### 실무에서 가장 많이 사용하는 기술

| 목적        | 대표 기술                                |
| --------- | ------------------------------------ |
| SPA 라이브러리 | React 19                             |
| SPA 프레임워크 | Next.js 16(App Router)               |
| 상태 관리     | Zustand                              |
| 서버 상태 관리  | TanStack Query                       |
| 빌드 도구     | Vite                                 |
| 라우팅       | React Router 7 또는 Next.js App Router |


## 요구사항

- HTML, CSS, 자바스크립트에 대한 기본적인 이해
- API에 대한 친숙함
- 실습을 진행하기 위해 도구
  - VSCode(Visual Studio Code) IDE
  - 커맨드 라인 도구(터미널, 통합 터미널 등)
  - Node.js 15 이상

**Node 버전 확인**:

```shell
node --version
# vXX.YY.ZZ

npm --version
# vXX.YY.ZZ
```

버전 정보가 출력되지 않으면 Node와 npm을 설치해야 한다. 이미 설치되어 있다면 최신 버전을 사용하고 있는지 확인한다.

## 리액트 프로젝트 준비

실습에서는 리액트 애플리케이션을 만들 때 **Vite를 사용**한다. Vite는 리액트 같은 현대적인 웹 프레임워크를 위한 최신 빌드 도구다. 합리적인 기본 설정을 제공하면서도 SVG, 린팅, TypeScript, 서버 사이드 렌더링 같은 특정 용도에 맞게 확장하기 쉽다.

**Vite?**

- 현대적인 프런트엔드 개발 도구이다.
- 프랑스어로 **"빠르다"** 는 뜻이다. '비트(veet)'로 발음.
- 이름 그대로 매우 빠른 개발 환경을 목표로 만들어졌다.
- 프로젝트 규모가 커질수록 다음의 문제가 발생했다.
  - 개발 서버 시작 시간이 오래 걸림
  - 소스 수정 후 다시 빌드하는 시간이 길어짐
  - Webpack 설정이 복잡함
- 이를 해결하기 위해 2020년 Evan You(Vue.js 개발자)가 Vite를 공개했다.
- React, Vue, Svelte 등의 프로젝트를 빠르게 개발하고 배포할 수 있도록 **개발 서버와 빌드 기능을 제공**한다.
- SPA를 만드는 프레임워크가 아니라, React나 Vue 프로젝트를 개발하고 빌드하는 도구다.

**Vite의 역할**:

- **개발 서버**: 리액트 애플리케이션을 로컬에서 실행할 수 있게 해준다(개발 환경).
- **번들러**: 프로덕션 배포를 위한 고도로 최적화된 파일을 생성한다(운영 환경).

**Vite의 장점**:

- 리액트 입문자에게 복잡한 툴링에 신경 쓰지 않고,
- 리액트 학습 자체에만 집중할 수 있게 해준다.

**Create React App(CRA)와 비교**:

CRA는 2016년 페이스북이 공개한 리액트 프로젝트 생성 도구다. Vite가 등장하기 전까지는 리액트 프로젝트를 만들 때 가장 많이 사용되었다. 하지만 현재는 Vite가 사실상 표준으로 자리 잡았다.

| 항목         | Create React App      | Vite               |
| ---------- | --------------------- | ------------------ |
| 개발 서버 시작   | 느림                    | 매우 빠름              |
| HMR        | 느림                    | 매우 빠름              |
| ES Modules | 제한적                   | 기본 지원              |
| 설정         | Webpack 중심            | 간단한 설정             |
| 현재(2026)   | 신규 프로젝트에서는 거의 사용되지 않음 | React 프로젝트의 사실상 표준 |



**Vite로 리액트 프로젝트를 만드는 두 가지 방법**:

- **온라인 템플릿 사용**: `React` 또는 `React + TypeScript`(수동으로 타입을 작성해야 하는 고급 사용자용) 중 선택할 수 있으며, 로컬 환경 구성 없이 온라인에서 바로 작업할 수 있다.
- **로컬에 Vite 설치(추천)**: 로컬 머신에 Vite로 리액트 프로젝트를 생성하고, 선호하는 IDE(예: VSCode)에서 작업하는 방식이다.

### React 프로젝트 생성

**1) 프로젝트 디렉토리 생성:**

```bash
mkdir app01
cd app01
```

**2-1) React + JavaScript 프로젝트:**

```bash
npm create vite@latest app01 -- --template react
```

**2-2) React + TypeScript 프로젝트:**

```bash
npm create vite@latest app01 -- --template react-ts
```

## 프로젝트 디렉토리 구조

```bash
app01
├── node_modules
├── eslint.config.js
├── index.html
├── package-lock.json
├── package.json
├── public
│   ├── favicon.svg
│   └── icons.svg
├── README.md
├── src
│   ├── App.css
│   ├── App.jsx
│   ├── assets
│   │   ├── hero.png
│   │   ├── react.svg
│   │   └── vite.svg
│   ├── index.css
│   └── main.jsx
└── vite.config.js
```

**`node_modules`**: 

- 프로젝트에서 사용하는 모든 패키지들이 설치되는 디렉토리이다.
- `npm install` 명령어를 실행하면 `package.json`에 명시된 패키지들이 설치된다.

**`package.json`**:

- 프로젝트의 설정 파일이다.
- 프로젝트 이름/버전/설명, 라이선스, 스크립트 명령어, 프로젝트에서 사용하는 패키지들의 목록과 버전 등을 정의한다.

**`package-lock.json`**:

- `package.json`에 명시된 패키지들의 정확한 버전과 설치 경로를 기록한다.
- 모든 개발자가 동일한 버전의 패키지를 설치하도록 보장한다.

**`vite.config.js`**:

- Vite의 설정 파일이다.
- React 플러그인 등록, 개발 서버 설정, Proxy 설정, Alias 설정 등 다양한 설정을 할 수 있다.

**`index.html`**:

- React 애플리케이션의 진입점이다.
- 브라우저가 처음 로드할 때 가장 먼저 읽는 HTML 파일이다.

**`public`**:

- 정적 파일을 저장하는 디렉토리이다.
- `index.html`에서 참조하는 이미지, 아이콘, 폰트 등과 같은 정적 파일들을 이 디렉토리에 넣는다.
- Vite는 `public` 디렉토리 안의 파일들을 빌드 시 그대로 복사한다.

**`src`**:

- React 애플리케이션의 소스 코드가 들어 있는 디렉토리이다.
- `main.jsx`: React 애플리케이션의 진입점이다. ReactDOM.render()를 호출하여 React 컴포넌트를 브라우저에 렌더링한다.
- `App.jsx`: React 애플리케이션의 루트 컴포넌트이다. 다른 컴포넌트들을 포함하고, 애플리케이션의 전체 구조를 정의한다.
- `App.css`: `App.jsx`에서 사용하는 스타일을 정의한다.  
- `index.css`: 애플리케이션 전체에 적용되는 전역 스타일을 정의한다.
- `assets`: 애플리케이션에서 사용하는 이미지, 아이콘, 폰트 등과 같은 정적 자원을 저장하는 디렉토리이다.

**`eslint.config.js`**:

- ESLint 설정 파일이다.
- ESLint는 자바스크립트 코드의 문법과 스타일을 검사하고, 일관된 코딩 스타일을 유지하도록 도와주는 도구이다.

## 개발 서버 실행

```shell
npm install       # 노드 모듈 설치 
npm run dev       # 개발 서버 실행
```

- 커맨드 라인에 프로젝트가 실행 중인 URL이 출력된다. 
- 브라우저에서 해당 URL로 접속해 리액트 프로젝트가 제대로 표시되는지 확인한다.
- `package.json` 파일을 확인해서 최신 리액트 버전을 사용하고 있는지 확인한다. 


### 리액트 실행 과정

```text
[서버] npm run dev
        │
        ▼
[서버] Vite 개발 서버 실행
        │
        ▼
[브라우저] http://localhost:5173 접속
        │
        ▼
[브라우저] index.html 요청
        │
        ▼
[Vite 개발 서버] index.html 응답
        │
        ▼
[브라우저] index.html 읽음
        │
        ▼
[브라우저] /src/main.jsx 요청
        │
        ▼
[Vite 개발 서버] main.jsx를 브라우저용 JavaScript로 변환 후 응답
        │
        ▼
[브라우저] main.jsx 실행 → main.jsx에서 App.jsx를 임포트
        │
        ▼
[브라우저] App.jsx 요청
        │
        ▼
[Vite 개발 서버] App.jsx를 브라우저용 JavaScript로 변환 후 응답
        │
        ▼
[브라우저] 리액트 실행 → 리액트가 <App /> 컴포넌트를 실행
        │
        ▼
[브라우저] 리액트가 DOM을 생성하여 <div id="root"></div> 안에 삽입
        │
        ▼
[브라우저] 화면 출력
```

## npm 스크립트

`package.json` 파일의 `scripts` 항목에는 프로젝트에서 자주 사용하는 명령어를 정의할 수 있다. 예를 들어, `npm run dev` 명령어는 `vite` 명령어를 실행하도록 설정되어 있다. 이 외에도 빌드, 테스트, 린트 등 다양한 명령어를 정의할 수 있다.

```json
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "lint": "eslint .",
    "preview": "vite preview"
  },
```

```bash
# 개발 서버 실행
npm run dev

# 린트 검사
npm run lint

# 프로덕션 빌드
npm run build
```