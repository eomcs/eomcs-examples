# [선언형 모드] app15. Route Params

## Route Params

라우트 파라미터(route params)는 동적 세그먼트에서 파싱된 값이다.

```jsx
<Route path="/concerts/:city" element={<City />} />
```

이 경우 `:city` 가 동적 세그먼트이다. 그 city에 해당하는 파싱된 값은 `useParams` 를 통해 얻을 수 있다.

```jsx
import { useParams } from "react-router";

function City() {
  let { city } = useParams();
  let data = useFakeDataLibrary(`/api/v2/cities/${city}`);
  // ...
}
```

## 라우트 설명

위 코드는 URL의 동적 세그먼트 값을 `useParams()` 로 꺼내서 사용하는 예이다.

```tsx
<Route path="/concerts/:city" element={<City />} />
```

- `:city` 는 동적 세그먼트이므로, `/concerts/` 뒤에 오는 값은 무엇이든 이 라우트에 매칭된다.
- 예를 들어 `/concerts/seoul` 로 접속하면 `city` 값은 `"seoul"` 이 된다.

```tsx
import { useParams } from "react-router";

function City() {
  let { city } = useParams();
  let data = useFakeDataLibrary(`/api/v2/cities/${city}`);
  // ...
}
```

- `useParams()` 는 현재 매칭된 라우트의 동적 세그먼트 값들을 객체로 반환한다.
- `let { city } = useParams();` 코드로 `:city` 자리에 들어온 실제 URL 값을 꺼낸다.
- 이렇게 꺼낸 `city` 값은 API 요청 경로를 만드는 등, 화면에 필요한 데이터를 가져오는 데 바로 활용할 수 있다(`/api/v2/cities/${city}`).
- 즉, "라우트 파라미터"란 라우트 경로에 선언한 동적 세그먼트(`:city`)와, 실제 접속한 URL이 매칭되면서 채워지는 구체적인 값(`"seoul"` 등)을 가리키는 용어이다.

정리하면, Route Params는 `<Route path=":이름">` 처럼 선언한 동적 세그먼트가 실제 URL과 매칭될 때 채워지는 값이며, 컴포넌트 안에서는 `useParams()` 훅으로 그 값을 읽어서 사용한다.
