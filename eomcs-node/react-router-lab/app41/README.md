# [데이터 모드] app41. 액션 호출하기: useSubmit

## Calling actions with useSubmit

`useSubmit` 을 사용하면 폼 데이터를 액션에 명령적으로(imperatively) 제출할 수 있다.

```jsx
import { useCallback } from "react";
import { useSubmit } from "react-router";
import { useFakeTimer } from "fake-lib";

function useQuizTimer() {
  let submit = useSubmit();

  let cb = useCallback(() => {
    submit(
      { quizTimedOut: true },
      { action: "/end-quiz", method: "post" },
    );
  }, []);

  let tenMinutes = 10 * 60 * 1000;
  useFakeTimer(tenMinutes, cb);
}
```

이 방식도 내비게이션을 일으키며, 브라우저 히스토리에 새 항목이 추가된다.

## 라우트 설명

위 코드는 사용자의 클릭 없이, 타이머가 끝났다는 이벤트에 반응해서 코드로 액션을 제출하는 예이다.

```tsx
let submit = useSubmit();
```

- `useSubmit()` 훅은 호출하면 `submit` 함수를 반환한다.
- 이 함수를 호출하면, 앞서 살펴본 `<Form>` 을 사용자가 직접 제출한 것과 똑같은 효과로 액션이 호출된다. 다만 `<Form>` 은 사용자가 버튼을 눌러야 하지만, `submit()` 은 코드 안에서 원하는 시점에 직접 호출할 수 있다.

```tsx
submit(
  { quizTimedOut: true },
  { action: "/end-quiz", method: "post" },
);
```

- 첫 번째 인자 `{ quizTimedOut: true }` 는 제출할 데이터이다. `<Form>` 의 `<input>` 값들을 모은 것과 같은 역할을 하며, 이 값은 액션의 `request.formData()` 로 읽을 수 있다.
- 두 번째 인자 `{ action: "/end-quiz", method: "post" }` 는 `<Form>` 의 `action`, `method` 속성과 같은 역할이다. 어떤 라우트의 액션을 호출할지, 어떤 HTTP 메서드로 호출할지를 지정한다.

```tsx
function useQuizTimer() {
  let submit = useSubmit();

  let cb = useCallback(() => {
    submit(...);
  }, []);

  let tenMinutes = 10 * 60 * 1000;
  useFakeTimer(tenMinutes, cb);
}
```

- `useQuizTimer` 는 퀴즈 제한 시간(10분)이 다 되면 `cb` 콜백을 실행하는 커스텀 훅이다.
- `cb` 콜백 안에서 `submit(...)` 을 호출하므로, 사용자가 아무것도 클릭하지 않아도 시간이 다 되는 순간 `/end-quiz` 라우트의 액션이 자동으로 호출된다.
- 이는 `useNavigate` 를 "사용자가 상호작용하지 않는 상황에서 코드로 이동시켜야 할 때" 사용했던 것과 비슷한 맥락이다. `useSubmit` 은 그 상황에서 "이동"이 아니라 "데이터 제출(액션 호출)"이 필요할 때 사용한다.

정리하면, `useSubmit` 은 `<Form>` 을 화면에 그리지 않고도, 타이머 종료·자동 저장처럼 사용자의 직접적인 제출 없이 코드로 액션을 호출해야 하는 상황에 사용하는 훅이다. `<Form>` 과 마찬가지로 내비게이션을 일으키며 브라우저 히스토리에 새 항목이 추가된다.
