# [데이터 모드] app38. 데이터 로딩

## Providing Data

라우트 컴포넌트에게 데이터는 라우트 로더(loader)로부터 제공된다.

```jsx
createBrowserRouter([
  {
    path: "/",
    loader: async () => {
      // return data from here
      return { records: await getSomeRecords() };
    },
    Component: MyRoute,
  },
]);
```

## Accessing Data

라우트 컴포넌트에서는 `useLoaderData` 로 그 데이터를 사용할 수 있다.

```jsx
import { useLoaderData } from "react-router";

function MyRoute() {
  const { records } = useLoaderData();
  return <div>{records.length}</div>;
}
```

사용자가 라우트 사이를 이동할 때, 라우트 컴포넌트가 렌더링되기 전에 로더가 먼저 호출된다.

## 라우트 설명

위 코드는 `loader` 가 데이터를 "제공(providing)"하고, 컴포넌트는 `useLoaderData()` 로 그 데이터에 "접근(accessing)"하는 두 단계를 보여주는 예이다.

```tsx
createBrowserRouter([
  {
    path: "/",
    loader: async () => {
      // return data from here
      return { records: await getSomeRecords() };
    },
    Component: MyRoute,
  },
]);
```

- `loader` 는 `async` 함수로 정의하며, 그 안에서 서버 API 호출 같은 비동기 작업(`await getSomeRecords()`)을 수행한다.
- `loader` 가 `return` 하는 값(`{ records: [...] }`)이 바로 이 라우트가 "제공하는 데이터"가 된다.
- 이 라우트로 이동하면, 리액트 라우터는 `MyRoute` 컴포넌트를 렌더링하기 전에 먼저 이 `loader` 를 호출해서 데이터가 준비되기를 기다린다.

```tsx
import { useLoaderData } from "react-router";

function MyRoute() {
  const { records } = useLoaderData();
  return <div>{records.length}</div>;
}
```

- `useLoaderData()` 는 같은 라우트의 `loader` 가 반환한 값을 그대로 돌려주는 훅이다.
- `loader` 가 `{ records: [...] }` 를 반환했으므로, `const { records } = useLoaderData();` 로 구조 분해해서 `records` 배열을 바로 사용할 수 있다.
- 컴포넌트 코드에는 데이터를 어떻게 가져왔는지에 대한 내용이 전혀 없다. 오직 "이미 준비되어 있는 데이터를 어떻게 화면에 보여줄지"만 신경 쓰면 된다.

- 사용자가 다른 라우트로 이동(navigate)할 때마다, 이 순서(① `loader` 실행 → ② 데이터 준비 완료 → ③ 컴포넌트 렌더링)가 매번 반복된다.

정리하면, 데이터 모드에서는 `loader` 가 데이터를 "제공"하는 역할을, `useLoaderData()` 가 컴포넌트 안에서 그 데이터에 "접근"하는 역할을 맡는다. 이 두 가지가 짝을 이루면서 컴포넌트 안에 직접 `fetch`/`useEffect` 를 쓰지 않고도 데이터 로딩을 처리할 수 있게 해준다.
