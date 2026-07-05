# app07. 리액트 DOM

## 개요

앞선 실습에서 컴포넌트의 **선언/인스턴스화**를 배웠고, `List`, `Search` 컴포넌트를 통해 실제로 확인해봤다. 그런데 맨 처음부터 사용해온 `App` 컴포넌트는 어디에서 **인스턴스화**되는 걸까? `App` 컴포넌트도 분명 어딘가에서 인스턴스화되어야, `App`과 그 하위 컴포넌트들이 화면에 렌더링될 수 있다.

`src/main.jsx` 파일을 열어보면 `<App />` 엘리먼트로 `App` 컴포넌트가 인스턴스화되는 부분을 확인할 수 있다.

## 실제 프로젝트 파일 확인하기

**`src/main.jsx`**

```jsx
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
);
```

**`index.html`**

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>app07</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.jsx"></script>
  </body>
</html>
```

## 실습

### 실습 1. `index.html`에서 `root` 엘리먼트 찾기

`index.html`에서 `id="root"`인 `<div>` 엘리먼트를 찾아보자. 바로 이 지점이 리액트가 순수 HTML 세계 안으로 자기 자신을 끼워 넣는(hook into) 지점이다. 리액트 애플리케이션 전체가 이 `<div id="root"></div>` 안에서 시작(bootstrap)된다.

### 실습 2. `main.jsx`에서 `createRoot().render()` 살펴보기

`main.jsx` 상단에는 두 개의 라이브러리가 임포트되어 있다.

| 라이브러리 | 역할 |
| --- | --- |
| `react` | 리액트 개발자가 일상적으로 사용하는 핵심 라이브러리(컴포넌트, 훅 등) |
| `react-dom` | 리액트를 순수 HTML 세계와 연결하는 역할. 보통 애플리케이션 전체에서 **딱 한 번**만 사용됨 |

동작 순서는 다음과 같다.

1. `document.getElementById('root')`로 `index.html`에 있던 `<div id="root">` 엘리먼트를 가져온다.
2. `createRoot(...)`에 그 엘리먼트를 넘겨서 리액트를 인스턴스화할 **루트(root) 객체**를 만든다.
3. 루트 객체의 `render()` 메서드에 JSX를 인자로 넘긴다. 이 JSX가 보통 애플리케이션의 진입점 컴포넌트(**루트 컴포넌트**)이며, 대개는 `App` 컴포넌트의 인스턴스(`<App />`)다.

진입점 컴포넌트가 꼭 `App`이어야 하는 것은 아니다. 예를 들어 아래처럼 `App` 없이 바로 JSX를 렌더링할 수도 있다.

```jsx
import { createRoot } from 'react-dom/client';

const title = 'React';

createRoot(document.getElementById('root')).render(
  <h1>Hello {title}</h1>
);
```

## 정리

- **React DOM**은 리액트를 HTML을 사용하는 웹사이트에 통합하는 데 필요한 모든 것을 담당한다.
- 처음부터 새로 만든 리액트 애플리케이션에는 보통 `createRoot()` 호출이 **하나**만 있다. (다만 리액트 이전 기술을 쓰던 레거시 애플리케이션의 일부만 리액트로 전환한 경우에는 여러 번 호출될 수도 있다.)
- 앞서 살펴본 SPA의 원리(작은 HTML 파일 + 큰 자바스크립트 파일)가 여기서 그대로 맞아떨어진다. `index.html`은 자바스크립트 파일을 요청하고 리액트가 자기 자신을 끼워 넣을 HTML 엘리먼트(`#root`)를 준비하는 역할만 하고, 실제 화면(HTML)을 그리는 일은 컴파일·번들된 `main.jsx`/`App.jsx`(자바스크립트)가 담당한다.
- 이 지점부터 리액트는 필요한 함수 컴포넌트들을 호출하며 컴포넌트 계층 구조 전체를 렌더링해나간다.
