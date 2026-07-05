# [선언형 모드] app07. 라우트 접두사

## Route Prefixes

`element` 속성이 *없는* `<Route path>` 는 부모 레이아웃을 만들지 않으면서, 자식 라우트들에게 경로 접두어(prefix)만 붙여준다.

```jsx
<Route path="projects">
  <Route index element={<ProjectsHome />} />
  <Route element={<ProjectsLayout />}>
    <Route path=":pid" element={<Project />} />
    <Route path=":pid/edit" element={<EditProject />} />
  </Route>
</Route>
```

## 라우트 설명

위 코드는 `element` 없이 `path` 만 선언한 라우트로 자식들에게 URL 접두어만 물려주는 예이다.

```tsx
<Route path="projects">...</Route>
```

- `path` 는 있지만 `element` 가 없다. 그래서 화면을 렌더링하거나 감싸지 않고, 자식 라우트들에게 `projects` 라는 URL 접두어만 붙여준다.
- 레이아웃 라우트(`element` 만 있고 `path` 없음)와 반대되는 경우이다.
- 화면을 감싸지 않으므로 `<Outlet />` 도 필요 없다.

```tsx
<Route index element={<ProjectsHome />} />

// ProjectsHome 컴포넌트
export function ProjectsHome() {
  return <h2>ProjectsHome</h2>;
}
```

- `/projects` → `<ProjectsHome />`
- 부모(`projects`)가 화면을 렌더링하지 않으므로, 이 인덱스 라우트가 `/projects` 접속 시 보여줄 화면이 된다.

```tsx
<Route element={<ProjectsLayout />}>...</Route>

// ProjectsLayout 컴포넌트
export default function ProjectsLayout() {
  return (
    <div>
      <nav>Projects Nav</nav>
      <Outlet />
    </div>
  );
}
```

- `path` 없이 `element` 만 있는 레이아웃 라우트이다.
- `projects` 접두어 아래에서 `Project`, `EditProject` 라우트를 감싸는 레이아웃 역할을 한다.

```tsx
<Route path=":pid" element={<Project />} />

// Project 컴포넌트
export function Project() {
  const { pid } = useParams();
  return <h2>Project {pid}</h2>;
}
```

- `/projects/:pid` → `<ProjectsLayout />` 안의 `<Project />`
- `:pid` 는 URL 파라미터 이름이다. 예를 들어 `/projects/100` 으로 접속하면 `pid` 값은 `"100"` 이 된다.

```tsx
<Route path=":pid/edit" element={<EditProject />} />

// EditProject 컴포넌트
export function EditProject() {
  const { pid } = useParams();
  return <h2>EditProject {pid}</h2>;
}
```

- `/projects/:pid/edit` → `<ProjectsLayout />` 안의 `<EditProject />`

정리하면, `path` 만 있고 `element` 가 없는 라우트는 화면을 감싸지 않은 채 자식 라우트들에게 공통 URL 접두어만 부여하는 용도로 쓰인다. 반대로 `element` 만 있고 `path` 가 없는 라우트는 URL을 늘리지 않으면서 공통 레이아웃을 공유시키는 용도로 쓰인다.
