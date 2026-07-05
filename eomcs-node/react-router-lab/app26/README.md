# [데이터 모드] app26. 인덱스 라우트

## Index Routes

인덱스 라우트는 `path` 없이 라우트 객체에 `index: true` 를 설정해서 정의한다.

```jsx
{ index: true, Component: Home }
```

인덱스 라우트는 부모의 URL에서 부모의 [Outlet](https://api.reactrouter.com/v7/functions/react-router.Outlet.html)에 렌더링된다(기본 자식 라우트와 비슷하다).

```jsx
import { createBrowserRouter } from "react-router";

createBrowserRouter([
  // renders at "/"
  { index: true, Component: Home },
  {
    Component: Dashboard,
    path: "/dashboard",
    children: [
      // renders at "/dashboard"
      { index: true, Component: DashboardHome },
      { path: "settings", Component: DashboardSettings },
    ],
  },
]);
```

인덱스 라우트는 자식 라우트를 가질 수 없다는 점에 유의한다.

## 라우트 설명

위 코드는 라우트 객체에 `index: true` 를 설정해서 부모 경로의 기본 화면을 지정하는 예이다.

```tsx
// renders at "/"
{ index: true, Component: Home },
```

- 최상위(루트) 라우트 배열에 있는 이 인덱스 라우트는, 별도의 부모 라우트가 없으므로 `/` 경로에서 렌더링된다.
- `index: true` 라우트는 `path` 를 가질 수 없다. 부모(또는 최상위)와 같은 URL에서 "기본으로 보여줄 화면"이라는 의미이기 때문이다.

```tsx
{
  Component: Dashboard,
  path: "/dashboard",
  children: [
    // renders at "/dashboard"
    { index: true, Component: DashboardHome },
    { path: "settings", Component: DashboardSettings },
  ],
},
```

- `/dashboard` 경로에 `Dashboard` 컴포넌트를 연결하고, 그 안에 `children` 으로 인덱스 라우트(`DashboardHome`)와 일반 자식 라우트(`DashboardSettings`)를 두었다.
- `{ index: true, Component: DashboardHome }` 은 `/dashboard` 로 접속했을 때 `Dashboard` 안의 `<Outlet />` 자리에 렌더링되는 기본 화면이다.
- `{ path: "settings", Component: DashboardSettings }` 은 `/dashboard/settings` 경로에 연결된다.
- 즉, 같은 `index: true` 속성이라도 어느 부모 라우트의 `children` 안에 있는지에 따라 서로 다른 URL의 기본 화면이 된다(최상위의 `Home` 은 `/`, `Dashboard` 안의 `DashboardHome` 은 `/dashboard`).

정리하면, 데이터 모드에서 인덱스 라우트는 `{ index: true, Component: ... }` 형태의 객체로 정의하며, `path` 와 자식(`children`)을 가질 수 없다는 제약이 있다. 부모(또는 최상위) 라우트와 동일한 URL에서 보여줄 기본 화면을 지정하는 용도로 쓰인다.
