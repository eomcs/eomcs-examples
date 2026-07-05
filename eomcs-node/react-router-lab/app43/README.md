# [데이터 모드] app43. 액션 데이터 접근하기: useActionData

## Accessing Action Data

액션은 데이터를 반환할 수 있는데, 이 데이터는 라우트 컴포넌트에서는 `useActionData` 로, fetcher를 사용할 때는 `fetcher.data` 로 접근할 수 있다.

```jsx
function Project() {
  let actionData = useActionData();
  return (
    <div>
      <h1>Project</h1>
      <Form method="post">
        <input type="text" name="title" />
        <button type="submit">Submit</button>
      </Form>
      {actionData ? (
        <p>{actionData.title} updated</p>
      ) : null}
    </div>
  );
}
```

## 라우트 설명

위 코드는 `action` 이 반환한 값을 컴포넌트에서 `useActionData()` 로 받아 제출 결과를 화면에 표시하는 예이다.

```tsx
let actionData = useActionData();
```

- `useActionData()` 는 같은 라우트에 정의된 `action` 함수가 마지막으로 반환한 값을 가져오는 훅이다.
- 앞서 "Defining Actions"에서 살펴본 `action` 이 `project` 객체를 반환했다면(예: `{ title: "새 제목" }`), 이 컴포넌트의 `actionData` 에는 바로 그 값이 담긴다.
- 아직 폼을 한 번도 제출하지 않았거나 페이지를 처음 열었을 때는 `actionData` 가 `undefined` 이다. `action` 이 실제로 호출되고 나서야 값이 채워진다.

```tsx
<Form method="post">
  <input type="text" name="title" />
  <button type="submit">Submit</button>
</Form>
{actionData ? (
  <p>{actionData.title} updated</p>
) : null}
```

- 사용자가 폼을 제출하면 이 라우트의 `action` 이 호출되고, 그 결과가 다시 `actionData` 로 전달되어 컴포넌트가 리렌더링된다.
- `actionData` 가 있을 때만(`actionData ?`) `"{title} updated"` 같은 안내 문구를 보여준다. 즉, "방금 제출이 성공적으로 처리되었다"는 피드백을 사용자에게 보여주는 용도로 활용한다.
- `loader` 의 결과를 받는 `useLoaderData()` 와 짝을 이루는 개념으로, `useLoaderData()` 는 "화면에 보여줄 데이터"를, `useActionData()` 는 "가장 최근 제출(action)의 결과"를 담당한다고 구분해서 이해하면 된다.

- fetcher를 사용하는 경우에는 `useActionData()` 대신 `fetcher.data` 로 같은 역할의 값을 얻는다. fetcher는 페이지 전체가 아니라 그 fetcher 자신이 호출한 액션의 결과만 담고 있다는 점이 다르다.

정리하면, `useActionData()` 는 방금 실행된 `action` 이 반환한 값을 컴포넌트에서 읽는 훅이며, 제출 성공 메시지나 유효성 검사 오류처럼 "이번 제출에 대한 결과"를 화면에 보여줄 때 사용한다.
