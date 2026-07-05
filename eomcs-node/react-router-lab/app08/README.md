# [선언형 모드] app08. 동적 세그먼트

## Dynamic Segments

경로 세그먼트가 `:` 로 시작하면 "동적 세그먼트(dynamic segment)"가 된다. 라우트가 URL과 매칭되면, 이 동적 세그먼트는 URL에서 파싱되어 `useParams` 같은 라우터 API에 `params` 로 전달된다.

```jsx
<Route path="teams/:teamId" element={<Team />} />
```

```jsx
import { useParams } from "react-router";

export default function Team() {
  let params = useParams();
  // params.teamId
}
```

하나의 라우트 경로에 여러 개의 동적 세그먼트를 둘 수도 있다.

```jsx
<Route
  path="/c/:categoryId/p/:productId"
  element={<Product />}
/>
```

```jsx
import { useParams } from "react-router";

export default function CategoryProduct() {
  let { categoryId, productId } = useParams();
  // ...
}
```

한 경로 안에 있는 모든 동적 세그먼트의 이름은 서로 달라야 한다. 그렇지 않으면 `params` 객체가 채워질 때, 나중 동적 세그먼트 값이 앞의 값을 덮어써 버린다.

## 라우트 설명

위 코드는 `:` 로 시작하는 경로 세그먼트로 URL의 일부 값을 파라미터로 받아오는 예이다.

```tsx
<Route path="teams/:teamId" element={<Team />} />

// Team 컴포넌트
import { useParams } from "react-router";

export default function Team() {
  const { teamId } = useParams();
  return <h2>Team {teamId}</h2>;
}
```

- `:teamId` 부분은 고정된 문자열이 아니라 어떤 값이든 매칭되는 동적 세그먼트이다.
- 예를 들어 `/teams/100` 으로 접속하면 `teamId` 값은 `"100"` 이 되고, `/teams/react-router` 로 접속하면 `teamId` 값은 `"react-router"` 가 된다.
- `useParams()` 는 현재 URL에 들어 있는 동적 세그먼트 값들을 객체로 반환한다.

```tsx
<Route
  path="/c/:categoryId/p/:productId"
  element={<Product />}
/>

// Product 컴포넌트
import { useParams } from "react-router";

export default function Product() {
  const { categoryId, productId } = useParams();
  return (
    <h2>
      Category {categoryId} / Product {productId}
    </h2>
  );
}
```

- 한 경로에 `:categoryId`, `:productId` 두 개의 동적 세그먼트를 둘 수도 있다.
- `/c/shoes/p/42` 로 접속하면 `categoryId` 는 `"shoes"`, `productId` 는 `"42"` 가 된다.
- 이렇게 여러 동적 세그먼트를 쓸 때는 이름이 서로 겹치지 않도록 주의해야 한다. 같은 이름을 두 번 쓰면 나중 값이 앞의 값을 덮어써서 원하는 값을 읽지 못한다.

정리하면, 동적 세그먼트(`:이름`)는 URL 경로의 일부를 변수처럼 받아서 컴포넌트 안에서 `useParams()` 로 읽어 쓰기 위한 문법이다.
