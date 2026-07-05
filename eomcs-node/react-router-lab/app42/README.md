# [데이터 모드] app42. 액션 호출하기: useFetcher

## Calling actions with a fetcher

fetcher를 사용하면 내비게이션을 일으키지 않고(브라우저 히스토리에 새 항목을 남기지 않고) 액션(그리고 로더)에 데이터를 제출할 수 있다.

```jsx
import { useFetcher } from "react-router";

function Task() {
  let fetcher = useFetcher();
  let busy = fetcher.state !== "idle";

  return (
    <fetcher.Form method="post" action="/update-task/123">
      <input type="text" name="title" />
      <button type="submit">
        {busy ? "Saving..." : "Save"}
      </button>
    </fetcher.Form>
  );
}
```

fetcher는 명령적으로 호출하는 `submit` 메서드도 가지고 있다.

```jsx
fetcher.submit(
  { title: "New Title" },
  { action: "/update-task/123", method: "post" },
);
```

더 자세한 내용은 [Using Fetchers 가이드](https://reactrouter.com/how-to/fetchers)를 참고한다.

## 라우트 설명

위 코드는 페이지 이동 없이, 목록 화면 등에 있는 개별 항목을 그 자리에서 저장하는 예이다.

```tsx
let fetcher = useFetcher();
let busy = fetcher.state !== "idle";
```

- `useFetcher()` 는 fetcher 인스턴스를 반환한다. `<Form>`, `useSubmit` 이 페이지 전체의 내비게이션과 연결되어 있는 것과 달리, fetcher는 현재 화면에 머문 채로 독립적으로 데이터를 주고받을 수 있다.
- `fetcher.state` 는 `"idle"`(대기 중), `"submitting"`(제출 중), `"loading"`(제출 후 데이터 재검증 중) 같은 값을 가진다. `busy` 변수로 "지금 저장 중인지"를 판단해서 버튼 문구를 바꾸는 데 사용한다.

```tsx
<fetcher.Form method="post" action="/update-task/123">
  <input type="text" name="title" />
  <button type="submit">
    {busy ? "Saving..." : "Save"}
  </button>
</fetcher.Form>
```

- `fetcher.Form` 은 일반 `<Form>` 과 사용법이 거의 같지만(`method`, `action` 속성 등), 제출해도 페이지 이동(내비게이션)이 일어나지 않는다는 점이 다르다.
- `/update-task/123` 라우트의 `action` 이 호출되어 제목이 저장되는 동안, 화면은 그대로 유지되면서 버튼 문구만 `"Saving..."` 으로 바뀐다.
- 목록 화면에서 각 항목마다 이런 `Task` 컴포넌트를 여러 개 렌더링해도, 각 fetcher는 서로 독립적으로 동작하므로 한 항목을 저장한다고 다른 항목이나 전체 페이지에 영향을 주지 않는다.

```tsx
fetcher.submit(
  { title: "New Title" },
  { action: "/update-task/123", method: "post" },
);
```

- `fetcher.Form` 대신, `useSubmit` 의 `submit` 함수처럼 명령적으로 호출할 수 있는 `fetcher.submit` 도 제공된다.
- 사용법은 `useSubmit` 과 거의 같지만(제출할 데이터, `{ action, method }` 옵션), 이 제출 역시 내비게이션을 일으키지 않는다는 점이 다르다.

정리하면, fetcher(`useFetcher`)는 `<Form>`/`useSubmit` 과 같은 방식으로 액션을 호출하면서도 페이지 이동 없이 현재 화면에 머무르고 싶을 때 사용한다. 목록의 개별 항목 저장, 좋아요 토글처럼 "화면 전체는 그대로 두고 일부만 서버와 동기화"하는 상황에 적합하다.
