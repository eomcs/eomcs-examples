# [선언형 모드] app04. 중첩 라우트

## Nested Routes

라우트는 부모 라우트 안에 중첩시킬 수 있다.

```jsx
<Routes>
  <Route path="dashboard" element={<Dashboard />}>
    <Route index element={<Home />} />
    <Route path="settings" element={<Settings />} />
  </Route>
</Routes>
```

부모의 경로(path)는 자식에게 자동으로 포함되므로, 위 설정은 `"/dashboard"` 와 `"/dashboard/settings"` 두 URL을 모두 만들어낸다.

자식 라우트는 부모 라우트 안의 `<Outlet/>` 위치에 렌더링된다.

```jsx
import { Outlet } from "react-router";

export default function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      {/* will either be <Home/> or <Settings/> */}
      <Outlet />
    </div>
  );
}
```

## 라우트 설명

위 코드는 부모 라우트 안에 자식 라우트를 선언하는 예이다.

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

- `/dashboard` 로 시작하는 URL과 `<Dashboard />` 컴포넌트를 연결한다.
- 이 라우트는 `Home`, `Settings` 라우트의 부모 라우트이다.
- 자식 라우트의 화면은 `<Dashboard />` 컴포넌트 안의 `<Outlet />` 위치에 렌더링된다.

```tsx
<Route index element={<Home />} />

// Home 컴포넌트
export function Home() {
  return <h2>Home</h2>;
}
```

- 부모 경로의 기본 화면을 지정한다.
- `/dashboard` → `<Dashboard />` 안의 `<Home />`

```tsx
<Route path="settings" element={<Settings />} />

// Settings 컴포넌트
export function Settings() {
  return <h2>Settings</h2>;
}
```

- 부모 경로 뒤에 `settings` 를 붙인 URL 경로와 `<Settings />` 컴포넌트를 연결한다.
- `/dashboard/settings` → `<Dashboard />` 안의 `<Settings />`

```tsx
<Outlet />
```

- `<Outlet />` 은 부모 컴포넌트 안에서 자식 라우트의 화면이 들어갈 자리이다.
- `/dashboard` 로 접속하면 `<Outlet />` 위치에 `<Home />` 이 렌더링된다.
- `/dashboard/settings` 로 접속하면 `<Outlet />` 위치에 `<Settings />` 가 렌더링된다.

즉, 이 구조는 `Dashboard` 라는 공통 화면을 유지하면서 URL에 따라 내부 콘텐츠만 `Home` 또는 `Settings` 로 바꾸는 방식이다.
