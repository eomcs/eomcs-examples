# [데이터 모드] app23. 라우트 객체

## Route Objects

라우트 객체는 `path` 와 컴포넌트뿐만 아니라, 데이터 로딩(loader)이나 액션(action) 같은 동작까지 정의한다. 자세한 내용은 [Route Object 가이드](https://reactrouter.com/start/data/route-object)에서 다루며, 여기서는 `loader` 의 간단한 예를 살펴본다.

```jsx
import {
  createBrowserRouter,
  useLoaderData,
} from "react-router";

createBrowserRouter([
  {
    path: "/teams/:teamId",
    loader: async ({ params }) => {
      let team = await fetchTeam(params.teamId);
      return { name: team.name };
    },
    Component: Team,
  },
]);

function Team() {
  let data = useLoaderData();
  return <h1>{data.name}</h1>;
}
```

## 라우트 설명

위 코드는 라우트 객체에 `loader` 함수를 추가해서, 화면을 그리기 전에 데이터를 미리 가져오는 예이다.

```tsx
{
  path: "/teams/:teamId",
  loader: async ({ params }) => {
    let team = await fetchTeam(params.teamId);
    return { name: team.name };
  },
  Component: Team,
}
```

- `path: "/teams/:teamId"` 는 이 라우트가 매칭되는 URL 패턴이다. `:teamId` 는 동적 세그먼트이다.
- **`loader` 는 이 라우트로 이동할 때 리액트 라우터가 자동으로 호출해 주는 비동기 함수이다. 컴포넌트가 렌더링되기 전에 실행된다.**
- `loader` 함수는 `{ params }` 객체를 인자로 받는데, 여기서 `params.teamId` 로 URL에 들어온 동적 세그먼트 값을 읽을 수 있다.
- `loader` 안에서 `fetchTeam(params.teamId)` 로 실제 데이터를 가져온 뒤, `{ name: team.name }` 형태로 필요한 값만 정리해서 반환한다.
- `Component: Team` 은 이 라우트가 매칭되었을 때 렌더링할 컴포넌트이다.

```tsx
function Team() {
  let data = useLoaderData();
  return <h1>{data.name}</h1>;
}
```

- **`useLoaderData()` 훅을 호출하면, 같은 라우트에 정의된 `loader` 함수가 반환한 값을 그대로 받아올 수 있다.**
- 즉, `loader` 가 반환한 `{ name: team.name }` 객체가 `data` 에 그대로 담기고, `data.name` 으로 팀 이름을 화면에 표시한다.
- 컴포넌트 안에서 직접 `useEffect` 와 `fetch` 를 사용해 데이터를 가져오는 대신, **라우트 자체에 데이터 로딩 책임을 위임하는 것이 데이터 모드(Data Mode)의 핵심 아이디어이다.**

정리하면, 라우트 객체의 `loader` 는 "이 라우트로 이동하기 전에 실행해서 필요한 데이터를 준비하는 함수"이고, 컴포넌트에서는 `useLoaderData()` 로 그 결과를 바로 꺼내 쓸 수 있다.
