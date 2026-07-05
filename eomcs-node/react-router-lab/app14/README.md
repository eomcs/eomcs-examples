# [선언형 모드] app14. useNavigate 훅

## useNavigate

이 훅을 사용하면 사용자의 상호작용 없이도 프로그래밍적으로 새 페이지로 이동시킬 수 있다.

일반적인 이동에는 `Link` 나 `NavLink` 를 쓰는 것이 가장 좋다. 이들은 키보드 이벤트, 접근성 레이블, "새 창에서 열기", 우클릭 컨텍스트 메뉴 등 더 나은 기본 사용자 경험을 제공한다.

`useNavigate` 사용은 사용자가 *상호작용하고 있지 않지만* 이동은 시켜야 하는 상황으로 한정하는 것이 좋다. 예를 들면 다음과 같다.

- 폼 제출이 완료된 후
- 비활동 상태가 지속되어 로그아웃시킬 때
- 퀴즈처럼 시간에 따라 진행되는 UI 등

```jsx
import { useNavigate } from "react-router";

export function LoginPage() {
  let navigate = useNavigate();

  return (
    <>
      <MyHeader />
      <MyLoginForm
        onSuccess={() => {
          navigate("/dashboard");
        }}
      />
      <MyFooter />
    </>
  );
}
```

## 라우트 설명

위 코드는 사용자의 클릭이 아니라, 로그인 성공이라는 이벤트에 반응해서 코드로 페이지를 이동시키는 예이다.

```tsx
let navigate = useNavigate();
```

- `useNavigate()` 훅은 호출하면 `navigate` 함수를 반환한다.
- 이 함수를 호출하면 `Link` 를 클릭한 것과 같은 효과로 다른 경로로 이동한다.
- `Link`, `NavLink` 는 사용자가 직접 클릭해야 동작하지만, `navigate()` 는 조건이나 이벤트 콜백 안에서 코드로 직접 호출할 수 있다.

```tsx
<MyLoginForm
  onSuccess={() => {
    navigate("/dashboard");
  }}
/>
```

- 로그인 폼에는 로그인이 성공했을 때 실행되는 `onSuccess` 콜백이 있다.
- 사용자는 링크를 클릭한 적이 없지만, 로그인 성공이라는 이벤트가 일어나면 `navigate("/dashboard")` 를 호출해서 `/dashboard` 경로로 이동시킨다.
- 이런 경우가 바로 `useNavigate` 를 쓰기에 적합한 상황이다. 사용자가 직접 링크를 클릭하는 것이 아니라, 폼 제출 완료 같은 "다른 이벤트의 결과"로 이동이 일어나야 하기 때문이다.

```tsx
export function LoginPage() {
  ...
  return (
    <>
      <MyHeader />
      <MyLoginForm onSuccess={...} />
      <MyFooter />
    </>
  );
}
```

- `LoginPage` 컴포넌트 자체에는 클릭 가능한 링크가 보이지 않는다. 오직 폼 제출 결과에 따라 자동으로 이동이 일어날 뿐이다.
- 만약 사용자가 직접 클릭해서 이동하는 상황이라면, `useNavigate` 대신 `Link` 나 `NavLink` 를 쓰는 것이 접근성과 사용성 측면에서 더 낫다.

정리하면, `useNavigate` 는 사용자의 직접적인 클릭이 아니라 폼 제출 완료, 자동 로그아웃, 타이머 종료처럼 "다른 이벤트에 반응해서" 코드로 페이지를 이동시켜야 할 때 사용하는 훅이다. 사용자가 직접 클릭하는 일반적인 이동에는 `Link`/`NavLink` 를 우선 사용한다.
