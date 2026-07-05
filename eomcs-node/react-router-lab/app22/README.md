# [데이터 모드] app22. 복잡한 라우트 구성하기

## 복잡한 구조의 라우트 구성

```jsx
// src/routes.ts
createBrowserRouter([
  {
    path: "/",
    Component: Root,
    children: [
      { index: true, Component: Home },
      { path: "about", Component: About },
      {
        path: "auth",
        Component: AuthLayout,
        children: [
          { path: "login", Component: Login },
          { path: "register", Component: Register },
        ],
      },
      {
        path: "concerts",
        children: [
          { index: true, Component: ConcertsHome },
          { path: ":city", Component: ConcertsCity },
          { path: "trending", Component: ConcertsTrending },
        ],
      },
    ],
  },
]);
```

## 라우트 설명

위 코드는 선언형 모드의 `<Routes>`/`<Route>` JSX 대신, 일반 타입스크립트 객체 배열로 라우트를 구성하는 데이터 모드(Data Mode)의 방식을 보여주는 예이다.

```tsx
const router = createBrowserRouter([
  { path: "/", Component: Root },
]);
```

- `createBrowserRouter()` 는 라우트 설정 배열을 받아서 `router` 객체를 만든다.
- 이 `router` 는 이후 `<RouterProvider router={router} />` 로 앱에 연결한다(데이터 모드는 `<BrowserRouter>` 대신 `<RouterProvider>` 를 사용한다).
- `{ path: "/", Component: Root }` 는 `/` 경로와 `Root` 컴포넌트를 연결하는 가장 단순한 라우트 객체이다.
- 선언형 모드의 `<Route path="/" element={<Root />} />` 와 같은 역할을 하지만, JSX가 아니라 타입스크립트 객체(`{ path, Component }`)로 표현한다는 점이 다르다.

```tsx
{
  path: "/",
  Component: Root,
  children: [
    { index: true, Component: Home },
    { path: "about", Component: About },
    ...
  ],
}
```

- `children` 배열에 자식 라우트 객체들을 나열해서 중첩 라우트를 구성한다.
- `{ index: true, Component: Home }` 은 부모 경로(`/`)의 기본 화면을 지정하는 인덱스 라우트이다.
- `{ path: "about", Component: About }` 은 `/about` 경로에 연결된다.

```tsx
{
  path: "auth",
  Component: AuthLayout,
  children: [
    { path: "login", Component: Login },
    { path: "register", Component: Register },
  ],
},
```

- `auth` 경로에 `AuthLayout` 컴포넌트를 연결하고, 그 안에 `login`, `register` 두 자식 라우트를 중첩시켰다.
- `/auth/login`, `/auth/register` 두 URL이 만들어지며, 두 화면 모두 `AuthLayout` 안의 `<Outlet />` 위치에 렌더링된다.

```tsx
{
  path: "concerts",
  children: [
    { index: true, Component: ConcertsHome },
    { path: ":city", Component: ConcertsCity },
    { path: "trending", Component: ConcertsTrending },
  ],
},
```

- `concerts` 라우트는 `Component` 없이 `path` 와 `children` 만 있다. 화면을 감싸지 않고 `concerts/` 라는 URL 접두어만 자식들에게 물려준다.
- `:city` 는 동적 세그먼트이므로, `/concerts/seoul` 처럼 임의의 값이 매칭된다.
- 결과적으로 `/concerts`, `/concerts/:city`, `/concerts/trending` 세 개의 URL이 만들어진다.

정리하면, 데이터 모드에서는 JSX 대신 `{ path, Component, children, index }` 같은 속성을 가진 일반 객체로 라우트 트리를 구성하고, 이 객체 배열을 `createBrowserRouter()` 에 전달해서 라우터를 만든다.
