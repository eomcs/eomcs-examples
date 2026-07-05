# [데이터 모드] app27. 라우트 접두사

## Prefix Route

`path` 만 있고 컴포넌트가 없는 라우트는 경로 접두어(prefix)를 공유하는 라우트 그룹을 만든다.

```jsx
createBrowserRouter([
  {
    // no component, just a path
    path: "/projects",
    children: [
      { index: true, Component: ProjectsHome },
      { path: ":pid", Component: Project },
      { path: ":pid/edit", Component: EditProject },
    ],
  },
]);
```

이 설정은 레이아웃 컴포넌트 없이 `/projects`, `/projects/:pid`, `/projects/:pid/edit` 라우트를 만든다.

## 라우트 설명

위 코드는 `Component` 없이 `path` 와 `children` 만 있는 라우트 객체로 자식들에게 URL 접두어만 물려주는 예이다.

```tsx
{
  // no component, just a path
  path: "/projects",
  children: [
    { index: true, Component: ProjectsHome },
    { path: ":pid", Component: Project },
    { path: ":pid/edit", Component: EditProject },
  ],
},
```

- `path: "/projects"` 는 있지만 `Component` 가 없다. 그래서 화면을 렌더링하거나 감싸지 않고, `children` 에 있는 자식 라우트들에게 `/projects` 라는 URL 접두어만 붙여준다.
- 화면을 감싸지 않으므로 `<Outlet />` 을 가진 레이아웃 컴포넌트도 필요 없다.
- `{ index: true, Component: ProjectsHome }` 은 `/projects` 접속 시 보여줄 기본 화면이다. 부모가 화면을 렌더링하지 않으므로, 최상위 라우터의 `<Outlet />` (또는 그 상위 레이아웃의 `<Outlet />`) 위치에 바로 `ProjectsHome` 이 렌더링된다.
- `{ path: ":pid", Component: Project }` 는 `/projects/:pid` 경로에 연결된다. `:pid` 는 동적 세그먼트이다.
- `{ path: ":pid/edit", Component: EditProject }` 는 `/projects/:pid/edit` 경로에 연결된다.

정리하면, `Component` 없이 `path` 만 있는 라우트 객체는 화면을 감싸지 않고 자식 라우트들에게 공통 URL 접두어만 부여하는 용도로 쓰인다. 반대로 앞서 살펴본 레이아웃 라우트(`Component` 만 있고 `path` 없음)는 URL을 늘리지 않으면서 공통 레이아웃을 공유시키는 용도로 쓰인다.
