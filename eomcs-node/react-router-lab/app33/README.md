# [데이터 모드] app33. 라우트 객체: loader

## `loader`

라우트 로더(loader)는 라우트 컴포넌트가 렌더링되기 전에 데이터를 제공한다.

```jsx
import {
  useLoaderData,
  createBrowserRouter,
} from "react-router";

createBrowserRouter([
  {
    path: "/",
    loader: loader,
    Component: MyRoute,
  },
]);

async function loader({ params }) {
  return { message: "Hello, world!" };
}

function MyRoute() {
  let data = useLoaderData();
  return <h1>{data.message}</h1>;
}
```

참고:

- [`loader` params 문서](https://api.reactrouter.com/v7/interfaces/react-router.LoaderFunctionArgs)

## 라우트 설명

위 코드는 라우트 객체에 `loader` 함수를 지정해서, 컴포넌트가 그려지기 전에 필요한 데이터를 먼저 준비하는 예이다.

```tsx
createBrowserRouter([
  {
    path: "/",
    loader: loader,
    Component: MyRoute,
  },
]);
```

- `loader` 속성에는 데이터를 가져오는 함수를 지정한다. 이 라우트로 이동할 때, 리액트 라우터가 `Component` 를 렌더링하기 전에 이 `loader` 함수를 자동으로 호출해 준다.
- 함수 이름은 자유롭게 지을 수 있지만(여기서는 `loader`), 라우트 객체의 `loader` 속성에 연결해 주어야 실제로 호출된다.

```tsx
async function loader({ params }) {
  return { message: "Hello, world!" };
}
```

- `loader` 함수는 `{ params }` 를 포함한 객체를 인자로 받는다. `params` 에는 동적 세그먼트로 매칭된 URL 값들이 들어 있다(이 예제에서는 동적 세그먼트가 없으므로 비어 있다).
- `loader` 는 `async` 함수이므로, 내부에서 `fetch` 등으로 서버에서 데이터를 가져오는 비동기 작업을 자연스럽게 처리할 수 있다.
- 반환한 값(`{ message: "Hello, world!" }`)이 곧 이 라우트의 "로더 데이터"가 된다.

```tsx
function MyRoute() {
  let data = useLoaderData();
  return <h1>{data.message}</h1>;
}
```

- 컴포넌트 안에서는 `useLoaderData()` 훅으로 같은 라우트의 `loader` 가 반환한 값을 그대로 받아온다.
- `data.message` 로 `loader` 가 반환했던 `message` 값을 꺼내 화면에 표시한다.
- 컴포넌트 자체에는 데이터를 가져오는 로직(`fetch`, `useEffect` 등)이 전혀 없다는 점에 주목한다. 데이터를 가져오는 책임은 `loader` 에, 화면을 그리는 책임은 컴포넌트에 나누어져 있다.

정리하면, `loader` 는 라우트가 매칭될 때 컴포넌트보다 먼저 실행되어 필요한 데이터를 가져오는 함수이고, 컴포넌트에서는 `useLoaderData()` 로 그 결과를 즉시 사용할 수 있다. 이 구조 덕분에 데이터 로딩 코드와 화면 렌더링 코드를 깔끔하게 분리할 수 있다.
