# [데이터 모드] app35. 라우트 객체: shouldRevalidate

## `shouldRevalidate`

로더 데이터는 내비게이션이나 폼 제출 같은 특정 이벤트가 일어난 후 자동으로 재검증(revalidate)된다.

이 훅(함수)을 사용하면 기본 재검증 동작을 그대로 쓸지, 원하는 대로 바꿀지 선택할 수 있다. 기본 동작은 불필요하게 로더를 호출하지 않도록 세심하게 설계되어 있다.

라우트 로더는 다음 경우에 재검증된다.

- 자기 자신의 라우트 파라미터가 바뀔 때
- URL 검색 파라미터(search params)에 어떤 변화라도 있을 때
- `action` 이 호출되어 에러가 아닌 상태 코드를 반환한 후

이 함수를 정의하면 기본 동작을 완전히 벗어나서, 내비게이션과 폼 제출에 대해 로더 데이터를 언제 재검증할지 직접 제어할 수 있다.

```jsx
import type { ShouldRevalidateFunctionArgs } from "react-router";

function shouldRevalidate(
  arg: ShouldRevalidateFunctionArgs,
) {
  return true; // false
}

createBrowserRouter([
  {
    path: "/",
    shouldRevalidate: shouldRevalidate,
    Component: MyRoute,
  },
]);
```

[`ShouldRevalidateFunctionArgs` 참고 문서 ↗](https://api.reactrouter.com/v7/interfaces/react-router.ShouldRevalidateFunctionArgs.html)

[프레임워크 모드(Framework Mode)](https://reactrouter.com/start/modes)에서는 기본 동작이 다르다는 점에 유의한다.

## 라우트 설명

위 코드는 라우트 객체에 `shouldRevalidate` 함수를 지정해서, 로더 재검증 여부를 직접 결정하는 예이다.

```tsx
function shouldRevalidate(
  arg: ShouldRevalidateFunctionArgs,
) {
  return true; // false
}
```

- `shouldRevalidate` 함수는 재검증이 필요한 이벤트가 일어날 때마다 호출되며, `true` 를 반환하면 해당 라우트의 `loader` 를 다시 실행하고, `false` 를 반환하면 실행하지 않는다.
- 인자로 받는 `arg`(`ShouldRevalidateFunctionArgs`)에는 이전 URL, 다음 URL, 액션 실행 결과 등 재검증 여부를 판단하는 데 필요한 정보가 담겨 있다.
- 예제 코드는 항상 `true` 를 반환하므로 사실상 기본 동작과 크게 다르지 않지만, 실제로는 이 안에서 조건을 판단해 `true`/`false` 를 다르게 반환하는 방식으로 사용한다(주석의 `// false` 는 "여기서 `false` 를 반환할 수도 있다"는 뜻이다).

```tsx
createBrowserRouter([
  {
    path: "/",
    shouldRevalidate: shouldRevalidate,
    Component: MyRoute,
  },
]);
```

- 라우트 객체의 `shouldRevalidate` 속성에 위 함수를 연결하면, 이 라우트는 더 이상 "자동" 재검증 규칙(파라미터 변경, 검색 파라미터 변경, 액션 성공 후)을 따르지 않고, 오직 이 함수가 반환하는 값에 따라서만 재검증 여부가 결정된다.
- 예를 들어, 자주 바뀌지 않는 데이터를 다루는 라우트라면 `shouldRevalidate` 에서 특정 조건일 때만 `true` 를 반환하도록 만들어서, 불필요한 네트워크 요청을 줄일 수 있다.

정리하면, `shouldRevalidate` 는 "언제 이 라우트의 `loader` 를 다시 호출할 것인가"를 직접 제어하는 함수이며, 기본적으로 리액트 라우터가 알아서 처리해 주는 재검증 규칙을 세밀하게 커스터마이징하고 싶을 때 사용한다.
