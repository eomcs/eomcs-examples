# [데이터 모드] app40. 액션 호출하기: Form

## Calling Actions

액션은 라우트의 경로(path)와 "post" 메서드를 참조해서, `<Form>` 을 통해 선언적으로, 또는 `useSubmit`(혹은 `<fetcher.Form>`, `fetcher.submit`)을 통해 명령적으로 호출할 수 있다.

### Calling actions with a Form

```jsx
import { Form } from "react-router";

function SomeComponent() {
  return (
    <Form action="/projects/123" method="post">
      <input type="text" name="title" />
      <button type="submit">Submit</button>
    </Form>
  );
}
```

이 방식은 내비게이션을 일으키며, 브라우저 히스토리에 새 항목이 추가된다.

## 라우트 설명

위 코드는 리액트 라우터가 제공하는 `Form` 컴포넌트로 액션을 호출하는 예이다.

```tsx
import { Form } from "react-router";
```

- 이 `Form` 은 HTML의 기본 `<form>` 이 아니라, 리액트 라우터가 제공하는 컴포넌트이다. 일반 HTML 폼처럼 보이고 동작하지만, 제출 시 브라우저가 페이지를 새로고침하는 대신 리액트 라우터가 제출을 가로채서 처리한다.

```tsx
<Form action="/projects/123" method="post">
  <input type="text" name="title" />
  <button type="submit">Submit</button>
</Form>
```

- `action="/projects/123"` 은 이 폼을 제출했을 때 어떤 라우트의 `action` 함수를 호출할지 지정한다. 여기서는 `/projects/:projectId` 라우트에서 `projectId` 가 `123` 인 경우에 해당한다.
- `method="post"` 는 이 제출이 데이터 변경(mutation)임을 나타낸다. 데이터 모드에서 `action` 은 `POST` 요청에 대응해서 호출된다.
- `<input type="text" name="title" />` 처럼 입력 필드의 `name` 속성이, `action` 함수 안에서 `request.formData().get("title")` 로 읽는 값과 짝을 이룬다.
- 제출 버튼(`<button type="submit">`)을 누르면, 별도의 이벤트 핸들러를 작성하지 않아도 `action="/projects/123"` 으로 지정한 라우트의 `action` 함수가 자동으로 호출된다.

- `<Form>` 으로 액션을 호출하면 "내비게이션(navigation)"이 일어난다. 즉, 브라우저 히스토리에 새로운 항목이 쌓이고(뒤로가기 버튼으로 돌아갈 수 있는 지점이 하나 늘어나고), 화면도 일반적인 페이지 이동처럼 갱신된다.
- 이는 뒤에서 다룰 `useSubmit` 이나 `fetcher` 를 사용한 방식과 대비된다. 그 방식들은 내비게이션 없이(히스토리에 새 항목을 남기지 않고) 액션을 호출할 수 있다.

정리하면, `<Form action="..." method="post">` 는 별도의 자바스크립트 이벤트 핸들러 없이도, 지정한 라우트의 `action` 을 호출하고 내비게이션까지 함께 일으키는 가장 선언적인 방법이다.
