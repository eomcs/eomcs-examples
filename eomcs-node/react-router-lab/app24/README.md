# [데이터 모드] app24. 중첩 라우트

## Nested Routes

라우트는 `children` 속성을 통해 부모 라우트 안에 중첩시킬 수 있다.

```jsx
createBrowserRouter([
  {
    path: "/dashboard",
    Component: Dashboard,
    children: [
      { index: true, Component: Home },
      { path: "settings", Component: Settings },
    ],
  },
]);
```

부모의 경로(path)는 자식에게 자동으로 포함되므로, 위 설정은 `"/dashboard"` 와 `"/dashboard/settings"` 두 URL을 모두 만들어낸다.

자식 라우트는 부모 라우트 안의 `<Outlet/>` 위치에 렌더링된다.

```jsx
import { Outlet } from "react-router";

export default function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      {/* will either be <Home> or <Settings> */}
      <Outlet />
    </div>
  );
}
```

## 라우트 설명

위 코드는 라우트 객체의 `children` 배열로 중첩 라우트를 구성하는 예이다.

```tsx
{
  path: "/dashboard",
  Component: Dashboard,
  children: [
    { index: true, Component: Home },
    { path: "settings", Component: Settings },
  ],
}
```

- `path: "/dashboard"` 와 `Component: Dashboard` 는 `/dashboard` 경로와 `<Dashboard />` 컴포넌트를 연결한다.
- `children` 배열 안의 라우트들은 이 부모 라우트에 중첩된 자식 라우트이다.
- `{ index: true, Component: Home }` 은 부모 경로(`/dashboard`)에서 기본으로 보여줄 화면을 지정하는 인덱스 라우트이다.
- `{ path: "settings", Component: Settings }` 는 부모 경로 뒤에 `settings` 를 붙인 `/dashboard/settings` 경로를 만든다.
- 즉, 자식 라우트의 `path` 는 부모의 `path` 뒤에 자동으로 이어 붙는다.

```tsx
import { Outlet } from "react-router";

export default function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      {/* will either be <Home> or <Settings> */}
      <Outlet />
    </div>
  );
}
```

- `Dashboard` 컴포넌트는 공통 레이아웃(제목 `<h1>Dashboard</h1>` 등)을 렌더링하면서, 그 안에 `<Outlet />` 을 둔다.
- `/dashboard` 로 접속하면 `<Outlet />` 자리에 `<Home />` 이, `/dashboard/settings` 로 접속하면 `<Outlet />` 자리에 `<Settings />` 가 렌더링된다.
- 이 구조는 선언형 모드에서 `<Route path="dashboard" element={<Dashboard />}>` 안에 자식 `<Route>` 를 중첩시키는 것과 동일한 개념이며, 데이터 모드에서는 JSX 중첩 대신 `children` 배열 중첩으로 표현한다는 점만 다르다.

정리하면, 데이터 모드의 중첩 라우트는 부모 라우트 객체의 `children` 배열에 자식 라우트 객체를 나열해서 구성하며, 자식 라우트의 화면은 부모 컴포넌트 안의 `<Outlet />` 위치에 렌더링된다.
