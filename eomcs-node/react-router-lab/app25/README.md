# [데이터 모드] app25. 레이아웃 라우트

## Layout Routes

라우트에서 `path` 를 생략하면, URL에 어떤 세그먼트도 추가하지 않으면서 자식들을 위한 새로운 [중첩 라우트](#nested-routes)를 만든다.

```jsx
createBrowserRouter([
  {
    // no path on this parent route, just the component
    Component: MarketingLayout,
    children: [
      { index: true, Component: Home },
      { path: "contact", Component: Contact },
    ],
  },

  {
    path: "projects",
    children: [
      { index: true, Component: ProjectsHome },
      {
        // again, no path, just a component for the layout
        Component: ProjectLayout,
        children: [
          { path: ":pid", Component: Project },
          { path: ":pid/edit", Component: EditProject },
        ],
      },
    ],
  },
]);
```

다음 사항에 유의한다.

- `Home` 과 `Contact` 는 `MarketingLayout` 의 아웃렛(outlet)에 렌더링된다.
- `Project` 와 `EditProject` 는 `ProjectLayout` 의 아웃렛에 렌더링되지만, `ProjectsHome` 은 그렇지 않다.

## 라우트 설명

위 코드는 `path` 없이 `Component` 만 있는 라우트 객체로 레이아웃 공유 구조를 만드는 예이다.

```tsx
{
  // no path on this parent route, just the component
  Component: MarketingLayout,
  children: [
    { index: true, Component: Home },
    { path: "contact", Component: Contact },
  ],
},
```

- 이 라우트 객체에는 `path` 가 없다. 그래서 URL에는 아무 세그먼트도 추가되지 않고, 오직 자식 라우트들을 감싸는 레이아웃 역할만 한다.
- `/` → `MarketingLayout` 안의 `<Outlet />` 위치에 `Home` 이 렌더링된다(인덱스 라우트이므로 부모와 같은 URL).
- `/contact` → `MarketingLayout` 안의 `<Outlet />` 위치에 `Contact` 가 렌더링된다.

```tsx
{
  path: "projects",
  children: [
    { index: true, Component: ProjectsHome },
    {
      // again, no path, just a component for the layout
      Component: ProjectLayout,
      children: [
        { path: ":pid", Component: Project },
        { path: ":pid/edit", Component: EditProject },
      ],
    },
  ],
},
```

- `projects` 라우트는 `path` 만 있고 `Component` 가 없다. 화면을 감싸지 않고 `projects` 라는 URL 접두어만 자식들에게 물려준다.
- `{ index: true, Component: ProjectsHome }` 은 `/projects` 에서 보여줄 기본 화면이다. 이 라우트는 `ProjectLayout` 의 자식이 아니라 `projects` 라우트 바로 아래에 있으므로, `ProjectLayout` 의 아웃렛에는 렌더링되지 않는다.
- 그 아래 있는, `path` 없이 `Component: ProjectLayout` 만 있는 라우트는 또 다른 레이아웃 라우트이다. `:pid`, `:pid/edit` 두 자식을 감싼다.
- `/projects/:pid` → `ProjectLayout` 안의 `<Outlet />` 위치에 `Project` 가 렌더링된다.
- `/projects/:pid/edit` → `ProjectLayout` 안의 `<Outlet />` 위치에 `EditProject` 가 렌더링된다.

정리하면, `path` 없이 `Component` 만 있는 라우트 객체는 URL을 늘리지 않으면서 자식 라우트들이 공통 레이아웃을 공유하도록 감싸는 용도로 쓰인다. 이 특성은 최상위 라우트뿐 아니라, 위 예시의 `ProjectLayout` 처럼 다른 라우트의 자식으로 중첩된 곳에서도 똑같이 적용된다.
