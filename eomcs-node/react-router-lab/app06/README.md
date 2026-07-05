# [선언형 모드] app06. 인덱스 라우트

## Index Routes

인덱스 라우트는 부모의 URL에서 부모의 `<Outlet/>` 위치에 렌더링된다(기본 자식 라우트와 비슷하다). `index` 속성으로 설정한다.

```jsx
<Routes>
  <Route path="/" element={<Root />}>
    {/* renders into the outlet in <Root> at "/" */}
    <Route index element={<Home />} />

    <Route path="dashboard" element={<Dashboard />}>
      {/* renders into the outlet in <Dashboard> at "/dashboard" */}
      <Route index element={<DashboardHome />} />
      <Route path="settings" element={<Settings />} />
    </Route>
  </Route>
</Routes>
```

인덱스 라우트는 자식 라우트를 가질 수 없다는 점에 유의한다. 만약 그런 동작이 필요하다면, 아마도 [레이아웃 라우트](https://reactrouter.com/start/declarative/routing#layout-routes)를 원하는 것이다.

## 라우트 설명

위 코드는 부모 경로의 기본 화면을 `index` 속성으로 지정하는 예이다.

```tsx
<Route path="/" element={<Root />}>...</Route>

// Root 컴포넌트
export default function Root() {
  return (
    <div>
      <h1>Root</h1>
      <Outlet />
    </div>
  );
}
```

- `/` 로 시작하는 URL과 `<Root />` 컴포넌트를 연결한다.
- 이 라우트는 `Home`, `Dashboard` 라우트의 부모 라우트이다.

```tsx
<Route index element={<Home />} />

// Home 컴포넌트
export function Home() {
  return <h2>Home</h2>;
}
```

- `index` 속성이 붙은 라우트는 부모 경로와 똑같은 URL에서, 부모 컴포넌트의 `<Outlet />` 자리에 렌더링되는 "기본 화면"이다.
- `/` → `<Root />` 안의 `<Outlet />` 위치에 `<Home />` 이 렌더링된다.
- 인덱스 라우트는 `path` 를 가질 수 없고, 자식 라우트도 가질 수 없다.

```tsx
<Route path="dashboard" element={<Dashboard />}>...</Route>

// Dashboard 컴포넌트
export default function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      <Outlet />
    </div>
  );
}
```

- `/dashboard` → `<Root />` 안의 `<Outlet />` 위치에 `<Dashboard />` 가 렌더링된다.
- 이 라우트도 자신만의 인덱스 라우트(`DashboardHome`)와 자식 라우트(`Settings`)를 가진 부모 라우트이다.

```tsx
<Route index element={<DashboardHome />} />

// DashboardHome 컴포넌트
export function DashboardHome() {
  return <h2>DashboardHome</h2>;
}
```

- `/dashboard` → `<Dashboard />` 안의 `<Outlet />` 위치에 `<DashboardHome />` 이 렌더링된다.
- 즉, 같은 이름의 `index` 속성이라도 어느 부모 라우트 아래에 있는지에 따라 다른 URL의 기본 화면이 된다.

```tsx
<Route path="settings" element={<Settings />} />

// Settings 컴포넌트
export function Settings() {
  return <h2>Settings</h2>;
}
```

- `/dashboard/settings` → `<Dashboard />` 안의 `<Outlet />` 위치에 `<Settings />` 가 렌더링된다.

정리하면, 인덱스 라우트(`index` 속성이 붙은 라우트)는 부모 라우트와 동일한 URL에서 표시할 "기본 화면"을 지정하는 용도로 쓰인다. `path` 를 따로 적지 않고, 자식 라우트도 가질 수 없다는 점이 레이아웃 라우트와 다르다.
