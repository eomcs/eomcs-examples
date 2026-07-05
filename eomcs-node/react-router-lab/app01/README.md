# [선언형 모드] app01. React Router 프로젝트 준비

이번 실습에서는 Vite + React + TypeScript + React Router 기반으로 리액트 프로젝트를 구성하는 방법을 배운다.

## React + TypeScript + Vite 프로젝트 생성

```bash
npm create vite@latest app01 -- --template react-ts
```

## React Router 설치

```bash
npm i react-router
```

## 애플리케이션에 라우터 적용

```typescript
// src/main.tsx
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router";
import App from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>
);
```

## App 컴포넌트 작성

```jsx
// src/App.tsx
function App() {
  return (
    <div>
      <h1>Hello React Router!</h1>
    </div>
  );
}

export default App;
```

## 라우트를 지정하지 않으면?

URL의 자원 경로와 상관없이 항상 App 컴포넌트가 렌더링된다. 

다음 URL을 입력한 후 출력 화면을 확인하라.

- `http://localhost:5173/`
- `http://localhost:5173/hello`
- `http://localhost:5173/anything`

어떤 경로를 요청하더라도 "Hello React Router!" 라는 문구가 화면에 나타난다.