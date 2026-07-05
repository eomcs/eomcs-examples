# [선언형 모드] app05. 레이아웃 라우트

## Layout Routes

`path` 가 *없는* 라우트는 자식들을 위한 새로운 중첩 구조를 만들지만, URL에는 어떤 세그먼트도 추가하지 않는다.

```jsx
<Routes>
  <Route element={<MarketingLayout />}>
    <Route index element={<MarketingHome />} />
    <Route path="contact" element={<Contact />} />
  </Route>

  <Route path="projects">
    <Route index element={<ProjectsHome />} />
    <Route element={<ProjectsLayout />}>
      <Route path=":pid" element={<Project />} />
      <Route path=":pid/edit" element={<EditProject />} />
    </Route>
  </Route>
</Routes>
```

## 라우트 설명

위 코드는 `path` 없이 `element` 만 선언한 라우트로 레이아웃 공유 구조를 만드는 예이다.

```tsx
<Route element={<MarketingLayout />}>...</Route>

// MarketingLayout 컴포넌트
export default function MarketingLayout() {
  return (
    <div>
      <header>Marketing</header>
      <Outlet />
    </div>
  );
}
```

- `path` 가 없으므로 URL에는 아무 세그먼트도 추가되지 않는다.
- 오직 자식 라우트들을 감싸는 레이아웃 역할만 한다.
- 이 라우트는 `MarketingHome`, `Contact` 라우트의 부모 라우트이다.

```tsx
<Route index element={<MarketingHome />} />

// MarketingHome 컴포넌트
export function MarketingHome() {
  return <h2>MarketingHome</h2>;
}
```

- 부모 경로의 기본 화면을 지정한다.
- `/` → `<MarketingLayout />` 안의 `<MarketingHome />`

```tsx
<Route path="contact" element={<Contact />} />

// Contact 컴포넌트
export function Contact() {
  return <h2>Contact</h2>;
}
```

- `path` 가 없는 부모(`MarketingLayout`) 아래에 있으므로, 이 라우트의 URL은 그대로 `contact` 이다.
- `/contact` → `<MarketingLayout />` 안의 `<Contact />`

```tsx
<Route path="projects">...</Route>
```

- `path` 는 있지만 `element` 가 없다. 레이아웃 화면 없이 자식들에게 `projects` 라는 URL 접두어만 붙여준다.
- 화면을 감싸지 않으므로 `<Outlet />` 도 필요 없다.

```tsx
<Route index element={<ProjectsHome />} />

// ProjectsHome 컴포넌트
export function ProjectsHome() {
  return <h2>ProjectsHome</h2>;
}
```

- `/projects` → `<ProjectsHome />`

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

- `path` 없이 `element` 만 있는 또 다른 레이아웃 라우트이다.
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
- `useParams()` 는 현재 URL에 들어 있는 파라미터 값을 객체로 반환한다. 그래서 `const { pid } = useParams();` 코드로 `:pid` 자리에 들어온 값을 꺼낼 수 있다.

```tsx
<Route path=":pid/edit" element={<EditProject />} />

// EditProject 컴포넌트
export function EditProject() {
  const { pid } = useParams();
  return <h2>EditProject {pid}</h2>;
}
```

- `/projects/:pid/edit` → `<ProjectsLayout />` 안의 `<EditProject />`
- `/projects/100/edit` 으로 접속하면 `useParams()` 가 반환한 객체에서 `pid` 값은 `"100"` 이다.
- `Project` 와 `EditProject` 는 서로 다른 경로에 연결되어 있지만, 둘 다 같은 URL 파라미터 이름인 `pid` 를 사용하므로 같은 방식으로 값을 읽을 수 있다.

정리하면, `path` 없이 `element` 만 있는 라우트는 URL을 늘리지 않고 여러 자식 라우트가 공통 레이아웃(헤더, 내비게이션 등)을 공유하도록 감싸는 용도로 쓰인다. 반대로 `path` 만 있고 `element` 가 없는 라우트(`projects`)는 화면을 감싸지 않고 URL 접두어만 자식에게 물려준다.
