# [데이터 모드] app37. 라우트 객체: handle

## `handle`

라우트 핸들(`handle`)을 사용하면 앱에서 원하는 값을 라우트 매치(route match)에 자유롭게 추가할 수 있고, 이를 `useMatches` 로 읽어서 브레드크럼(breadcrumb) 같은 추상화를 만들 수 있다.

```jsx
createBrowserRouter([
  {
    path: "/app",
    handle: {
      breadcrumb: "App",
    },
  },
]);
```

참고:

- [`useMatches`](https://reactrouter.com/api/hooks/useMatches)

## 라우트 설명

위 코드는 라우트 객체에 `handle` 속성으로 임의의 메타데이터를 붙여두고, 이를 나중에 `useMatches()` 로 꺼내 쓰는 예이다.

```tsx
{
  path: "/app",
  handle: {
    breadcrumb: "App",
  },
},
```

- `handle` 은 리액트 라우터가 그 의미나 형태를 미리 정해두지 않은, 완전히 자유로운 속성이다. 객체, 문자열, 함수 등 원하는 무엇이든 담을 수 있다.
- 이 예제에서는 `{ breadcrumb: "App" }` 처럼, 이 라우트가 브레드크럼에서 어떤 이름으로 표시되어야 하는지를 담아두었다.
- `path`, `Component`, `loader` 처럼 리액트 라우터가 직접 해석해서 동작을 결정하는 속성들과 달리, `handle` 은 순전히 개발자가 정의하고 개발자가 읽어서 쓰는 값이다.

```tsx
// 다른 컴포넌트(예: 브레드크럼 컴포넌트)에서
import { useMatches } from "react-router";

function Breadcrumbs() {
  const matches = useMatches();
  const crumbs = matches
    .filter((match) => match.handle?.breadcrumb)
    .map((match) => match.handle.breadcrumb);

  return (
    <nav>
      {crumbs.map((crumb, i) => (
        <span key={i}>{crumb}</span>
      ))}
    </nav>
  );
}
```

- `useMatches()` 는 현재 URL과 매칭된 모든 라우트(부모부터 자식까지)의 정보를 배열로 반환한다. 각 항목에는 `id`, `pathname`, `data`(해당 라우트의 로더 데이터), 그리고 `handle` 이 포함되어 있다.
- 각 매치의 `match.handle` 에서 앞서 라우트 객체에 적어둔 `{ breadcrumb: "App" }` 값을 그대로 꺼낼 수 있다.
- 이런 방식으로, 중첩된 라우트 트리 전체를 순회하면서 "이 화면까지 오는 경로"를 브레드크럼으로 조립하거나, 탭 제목, 권한 정보 같은 라우트별 부가 정보를 표현하는 데 `handle` 을 활용할 수 있다.

정리하면, `handle` 은 라우트 객체에 자유 형식으로 부가 정보를 붙여두는 속성이며, `useMatches()` 로 현재 매칭된 라우트들의 `handle` 값을 모아서 브레드크럼처럼 라우트 트리 구조에 기반한 UI를 만드는 데 사용한다.
