# [선언형 모드] app02. 기본 라우트 구성하기

## 라우트를 구성하는 방법

`<Routes>` 와 `<Route>` 를 렌더링해서 URL 세그먼트와 화면(UI)을 연결하는 방식으로 라우트를 구성한다.

### Routes 엘리먼트:

`<Routes>` 는 여러 개의 `<Route>` 를 감싸는 라우트 컨테이너이다. 현재 브라우저 주소와 자식 `<Route>` 들의 `path` 를 비교한 뒤, 가장 알맞은 라우트를 선택해서 렌더링한다.

`<Routes>` 안에는 보통 하나 이상의 `<Route>` 를 배치한다. URL이 바뀌면 `<Routes>` 가 다시 매칭을 수행하고, 매칭된 `<Route>` 의 화면을 보여준다.

### Route 엘리먼트:

`<Route>` 는 특정 URL 경로와 렌더링할 화면을 연결한다. `path` 속성에는 URL 경로를 지정하고, `element` 속성에는 해당 경로에서 보여줄 React 엘리먼트를 지정한다.

예를 들어 `<Route path="/" element={<App />} />` 는 브라우저 주소가 `/` 일 때 `<App />` 컴포넌트를 화면에 출력하라는 뜻이다.

`<Route>` 는 중첩해서 사용할 수도 있다. 중첩 라우트를 사용하면 공통 레이아웃 아래에 여러 하위 화면을 배치하거나, `/concerts/seoul` 처럼 계층적인 URL 구조를 표현할 수 있다.

### main.tsx

```jsx
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import App from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
```

## 라우트를 지정하면?

URL의 자원 경로와 일치할 때만 해당 컴포넌트가 렌더링된다. 

다음 URL을 입력한 후 출력 화면을 확인하라.

- `http://localhost:5173/` → "Hello React Router!" 
- `http://localhost:5173/hello` → 화면에 아무것도 나타나지 않음
- `http://localhost:5173/anything` → 화면에 아무것도 나타나지 않음
