# [데이터 모드] app28. 동적 세그먼트

## Dynamic Segments

경로 세그먼트가 `:` 로 시작하면 "동적 세그먼트(dynamic segment)"가 된다. 라우트가 URL과 매칭되면, 이 동적 세그먼트는 URL에서 파싱되어 다른 라우터 API에 `params` 로 전달된다.

```jsx
{
  path: "teams/:teamId",
  loader: async ({ params }) => {
    // params are available in loaders/actions
    let team = await fetchTeam(params.teamId);
    return { name: team.name };
  },
  Component: Team,
}
```

```jsx
import { useParams } from "react-router";

function Team() {
  // params are available in components through useParams
  let params = useParams();
  // ...
}
```

하나의 라우트 경로에 여러 개의 동적 세그먼트를 둘 수도 있다.

```jsx
{
  path: "c/:categoryId/p/:productId";
}
```

## 라우트 설명

위 코드는 동적 세그먼트로 매칭된 URL 값을 `loader` 와 컴포넌트 양쪽에서 각각 읽는 방법을 보여주는 예이다.

```tsx
{
  path: "teams/:teamId",
  loader: async ({ params }) => {
    // params are available in loaders/actions
    let team = await fetchTeam(params.teamId);
    return { name: team.name };
  },
  Component: Team,
}
```

- `:teamId` 는 동적 세그먼트이므로, `teams/` 뒤에 오는 값은 무엇이든 이 라우트에 매칭된다. 예를 들어 `/teams/100` 으로 접속하면 `teamId` 값은 `"100"` 이 된다.
- `loader` 함수는 `{ params }` 객체를 인자로 받으며, `params.teamId` 로 URL에 들어온 동적 세그먼트 값을 읽을 수 있다.
- 즉, 동적 세그먼트 값은 컴포넌트뿐 아니라 `loader`(그리고 `action`)에서도 똑같이 `params` 를 통해 사용할 수 있다.

```tsx
import { useParams } from "react-router";

function Team() {
  // params are available in components through useParams
  let params = useParams();
  // ...
}
```

- 컴포넌트 안에서는 `useParams()` 훅으로 같은 동적 세그먼트 값을 읽는다.
- `loader` 에서는 함수 인자로, 컴포넌트에서는 훅으로 값을 받는다는 방식만 다를 뿐, 같은 URL 매칭 결과(`params`)를 공유한다.

```tsx
{
  path: "c/:categoryId/p/:productId";
}
```

- 한 경로에 `:categoryId`, `:productId` 두 개의 동적 세그먼트를 둘 수도 있다.
- 예를 들어 `/c/shoes/p/42` 로 접속하면 `params.categoryId` 는 `"shoes"`, `params.productId` 는 `"42"` 가 된다.
- 여러 동적 세그먼트를 쓸 때는 이름이 서로 겹치지 않도록 주의해야 한다(같은 이름을 두 번 쓰면 나중 값이 앞의 값을 덮어쓴다).

정리하면, 동적 세그먼트(`:이름`)로 매칭된 값은 `params` 객체에 담기며, `loader`/`action` 함수에서는 인자로 전달받은 `{ params }` 로, 컴포넌트에서는 `useParams()` 훅으로 동일하게 읽어 쓸 수 있다.
