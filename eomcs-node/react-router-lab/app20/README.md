# [데이터 모드] app20. React Router 프로젝트 준비

이번 실습에서는 Vite + React + TypeScript + React Router 기반으로 리액트 프로젝트를 구성하는 방법을 배운다.

## React + TypeScript + Vite 프로젝트 생성

```bash
npm create vite@latest app20 -- --template react-ts
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
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
import App from "./App";
 
 
const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
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

## 코드 설명

이 예제는 React Router를 데이터 모드(Data Mode)로 사용하는 가장 작은 형태의 코드이다. 데이터 모드에서는 `<BrowserRouter>` 로 JSX를 감싸는 대신, 라우터 객체를 먼저 만들고 그 객체를 `<RouterProvider>` 에 전달한다.

```typescript
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
```

- `createBrowserRouter` 는 브라우저의 주소 표시줄을 기준으로 동작하는 라우터 객체를 만든다.
- `RouterProvider` 는 만들어진 라우터 객체를 React 애플리케이션에 연결한다.
- 선언 모드에서는 `<BrowserRouter>`, `<Routes>`, `<Route>` 를 JSX로 직접 렌더링했지만, 데이터 모드에서는 라우트 정보를 객체 배열로 선언한다.

```typescript
const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
]);
```

- `createBrowserRouter([...])` 에 전달한 배열은 라우트 목록이다.
- `path: "/"` 는 브라우저 주소가 `/` 일 때 이 라우트가 매칭된다는 뜻이다.
- `element: <App />` 는 해당 경로에서 렌더링할 React 엘리먼트이다.
- 즉, `/` 로 접속하면 `<App />` 컴포넌트가 화면에 출력된다.

```typescript
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
```

- `createRoot(...).render(...)` 는 React 애플리케이션을 HTML의 `#root` 요소에 렌더링한다.
- `<RouterProvider router={router} />` 는 앞에서 만든 `router` 객체를 앱 전체에 제공한다.
- 이 컴포넌트가 있어야 React Router가 현재 URL을 읽고, 매칭되는 라우트의 `element` 를 화면에 렌더링할 수 있다.

```jsx
function App() {
  return (
    <div>
      <h1>Hello React Router!</h1>
    </div>
  );
}
```

- `App` 컴포넌트는 `/` 경로에서 보여줄 화면이다.
- 현재 예제에서는 라우터가 정상적으로 연결되었는지 확인하기 위해 간단한 제목만 출력한다.

정리하면, 데이터 모드의 최소 구성은 `createBrowserRouter()` 로 라우터 객체를 만들고, `<RouterProvider>` 로 그 라우터를 React 앱에 연결하는 것이다. 이후 라우트 객체에 `loader`, `action`, `errorElement`, 중첩 라우트 등을 추가하면서 데이터 로딩과 폼 처리 기능을 확장할 수 있다.
