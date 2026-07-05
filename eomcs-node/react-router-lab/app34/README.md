# [데이터 모드] app34. 라우트 객체: action

## `action`

라우트 액션(action)은 서버 측 데이터 변경(mutation)을 처리하며, `<Form>`, `useFetcher`, `useSubmit` 을 통해 호출되면 페이지에 있는 모든 로더 데이터를 자동으로 재검증(revalidate)한다.

```jsx
import {
  createBrowserRouter,
  useLoaderData,
  useActionData,
  Form,
} from "react-router";
import { TodoList } from "~/components/TodoList";

createBrowserRouter([
  {
    path: "/items",
    action: action,
    loader: loader,
    Component: Items,
  },
]);

async function action({ request }) {
  const data = await request.formData();
  const todo = await fakeDb.addItem({
    title: data.get("title"),
  });
  return { ok: true };
}

// this data will be revalidated after the action completes...
async function loader() {
  const items = await fakeDb.getItems();
  return { items };
}

// ...so that the list here is updated automatically
export default function Items() {
  let data = useLoaderData();
  return (
    <div>
      <List items={data.items} />
      <Form method="post" navigate={false}>
        <input type="text" name="title" />
        <button type="submit">Create Todo</button>
      </Form>
    </div>
  );
}
```

## 라우트 설명

위 코드는 폼 제출로 데이터를 추가(`action`)하고, 그 결과에 맞춰 목록 데이터(`loader`)가 자동으로 다시 로딩되는 예이다.

```tsx
createBrowserRouter([
  {
    path: "/items",
    action: action,
    loader: loader,
    Component: Items,
  },
]);
```

- 하나의 라우트 객체에 `action` 과 `loader` 를 함께 지정할 수 있다.
- `loader` 는 화면에 보여줄 데이터를 "읽어오는" 역할, `action` 은 폼 제출 등으로 데이터를 "바꾸는" 역할을 맡는다.

```tsx
async function action({ request }) {
  const data = await request.formData();
  const todo = await fakeDb.addItem({
    title: data.get("title"),
  });
  return { ok: true };
}
```

- `action` 함수는 `{ request }` 를 인자로 받는다. `request` 는 표준 웹 `Request` 객체이다.
- `request.formData()` 로 폼에 담겨 전송된 값을 읽는다. `data.get("title")` 은 폼의 `name="title"` 입력값을 가져온다.
- 이렇게 읽은 값으로 `fakeDb.addItem(...)` 을 호출해서 실제 데이터(할 일 항목)를 추가한다.
- 처리 결과로 `{ ok: true }` 를 반환하며, 이 값은 컴포넌트에서 `useActionData()` 로 받아볼 수 있다(이 예제에서는 임포트만 해두고 실제로는 사용하지 않았다).

```tsx
// this data will be revalidated after the action completes...
async function loader() {
  const items = await fakeDb.getItems();
  return { items };
}
```

- `loader` 는 현재 저장된 항목 목록을 가져와 반환한다.
- 주석에서 설명하듯, `action` 이 성공적으로 끝나면(에러가 아닌 상태 코드를 반환하면) 리액트 라우터는 같은 페이지의 `loader` 들을 자동으로 다시 호출해서 데이터를 최신 상태로 갱신한다. 이 동작을 "재검증(revalidation)"이라고 부른다.

```tsx
export default function Items() {
  let data = useLoaderData();
  return (
    <div>
      <List items={data.items} />
      <Form method="post" navigate={false}>
        <input type="text" name="title" />
        <button type="submit">Create Todo</button>
      </Form>
    </div>
  );
}
```

- `useLoaderData()` 로 현재 항목 목록(`data.items`)을 받아 `<List />` 로 렌더링한다.
- `<Form method="post" navigate={false}>` 는 리액트 라우터가 제공하는 `Form` 컴포넌트로, 제출하면 이 라우트의 `action` 이 호출된다. `navigate={false}` 를 주면 제출 후에도 페이지 이동(URL 변경) 없이 현재 화면에 머무른다.
- 사용자가 "Create Todo" 버튼을 누르면: ① `action` 이 실행되어 새 항목이 추가되고 → ② `loader` 가 자동으로 재실행되어 최신 목록을 가져오고 → ③ 화면의 `<List items={data.items} />` 가 새 항목을 포함해서 다시 그려진다. 이 과정에서 개발자가 직접 상태를 갱신하는 코드를 작성할 필요가 없다.

정리하면, `action` 은 `<Form>`/`useFetcher`/`useSubmit` 을 통한 데이터 변경을 처리하는 함수이며, `action` 이 성공적으로 끝나면 같은 페이지의 `loader` 데이터가 자동으로 재검증되어 화면이 최신 상태로 갱신된다.
