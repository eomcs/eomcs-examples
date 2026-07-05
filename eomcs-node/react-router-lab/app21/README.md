# [데이터 모드] app21. 기본 라우트 구성하기

## Configuring Routes

라우트는 `createBrowserRouter` 의 첫 번째 인자로 구성한다. 최소한 `path` 와 컴포넌트만 있으면 된다.

```jsx
// src/routes.ts
import { createBrowserRouter } from "react-router";
import App from "./App";
 
const router = createBrowserRouter([
  { path: "/", Component: App },
]);

export default router;
```

- 라우트 설정을 별도의 파일(`src/routes.ts`)로 분리하면, 라우트 설정이 길어지더라도 관리하기 편하다.

```jsx
// src/main.tsx
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { RouterProvider } from "react-router/dom";
import router from "./routes";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
```

## 코드 설명

이 예제는 데이터 모드에서 라우트 설정을 `src/routes.ts` 파일로 분리하는 가장 기본적인 구성이다.

```jsx
import { createBrowserRouter } from "react-router";
import App from "./App";
```

- `createBrowserRouter` 는 브라우저 URL을 기준으로 동작하는 라우터 객체를 생성하는 함수이다.
- `App` 은 `/` 경로에서 화면에 렌더링할 컴포넌트이다.

```jsx
const router = createBrowserRouter([
  { path: "/", Component: App },
]);
```

- `createBrowserRouter([...])` 의 첫 번째 인자는 라우트 객체 배열이다.
- `{ path: "/", Component: App }` 은 브라우저 주소가 `/` 일 때 `App` 컴포넌트를 렌더링하라는 뜻이다.
- 데이터 모드의 라우트 객체에서는 `element: <App />` 대신 `Component: App` 처럼 컴포넌트 자체를 넘길 수 있다.
- 라우트가 많아지면 이 배열에 객체를 추가해서 `/about`, `/dashboard` 같은 경로를 더 구성할 수 있다.

```jsx
export default router;
```

- 만든 라우터 객체를 다른 파일에서 사용할 수 있도록 기본 export 한다.
- 이렇게 라우터 설정을 별도 파일로 분리하면 `main.tsx` 는 앱을 렌더링하는 역할에 집중하고, `routes.ts` 는 라우트 구성을 관리하는 역할에 집중할 수 있다.

```jsx
import { RouterProvider } from "react-router/dom";
import router from "./routes";
```

- `RouterProvider` 는 라우터 객체를 React 애플리케이션에 연결하는 컴포넌트이다.
- `router` 는 `routes.ts` 에서 만든 라우터 객체이다.

```jsx
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
```

- `createRoot(...).render(...)` 는 React 애플리케이션을 HTML의 `#root` 요소에 렌더링한다.
- `<RouterProvider router={router} />` 는 현재 URL을 확인하고, `router` 에 등록된 라우트 중 매칭되는 컴포넌트를 화면에 렌더링한다.

정리하면, `routes.ts` 에서는 `createBrowserRouter()` 로 라우터 객체를 만들고, `main.tsx` 에서는 `<RouterProvider>` 로 그 라우터를 앱에 연결한다. 이 구조는 라우트가 늘어날수록 코드를 더 깔끔하게 관리하기 좋다.
