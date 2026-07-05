# [데이터 모드] app39. 액션 정의하기: action

## Defining Actions

데이터 변경(mutation)은 라우트 객체의 `action` 속성에 정의하는 라우트 액션(Route actions)으로 처리한다. 액션이 완료되면, 페이지에 있는 모든 로더 데이터가 재검증(revalidate)되어, 별도의 코드를 작성하지 않아도 UI가 데이터와 항상 일치된 상태로 유지된다.

```jsx
import { createBrowserRouter } from "react-router";
import { someApi } from "./api";

let router = createBrowserRouter([
  {
    path: "/projects/:projectId",
    Component: Project,
    action: async ({ request }) => {
      let formData = await request.formData();
      let title = formData.get("title");
      let project = await someApi.updateProject({ title });
      return project;
    },
  },
]);
```

## 라우트 설명

위 코드는 라우트 객체의 `action` 속성으로, 프로젝트 제목을 수정하는 데이터 변경 로직을 정의하는 예이다.

```tsx
{
  path: "/projects/:projectId",
  Component: Project,
  action: async ({ request }) => {
    let formData = await request.formData();
    let title = formData.get("title");
    let project = await someApi.updateProject({ title });
    return project;
  },
},
```

- `action` 은 `loader` 와 마찬가지로 `async` 함수이며, `{ request }` 를 인자로 받는다. `request` 는 표준 웹 `Request` 객체이다.
- `request.formData()` 로 폼 제출 시 전송된 데이터를 읽는다. `formData.get("title")` 은 `name="title"` 인 입력 필드의 값을 가져온다.
- 이렇게 얻은 `title` 값으로 `someApi.updateProject({ title })` 를 호출해서 실제로 프로젝트 데이터를 수정한다.
- `action` 이 반환한 값(`project`)은 이후 `useActionData()` 로 컴포넌트에서 받아볼 수 있다.
- `path: "/projects/:projectId"` 라우트는 `Component`(화면), `action`(데이터 변경) 을 함께 가지고 있으며, 앞서 살펴본 `loader`(데이터 조회)까지 셋을 한 라우트 객체 안에 모두 정의할 수도 있다.

- 이 `action` 이 성공적으로 끝나면, 리액트 라우터는 현재 페이지에서 사용 중인 모든 `loader` 데이터를 자동으로 다시 불러온다. 예를 들어 프로젝트 목록을 보여주는 `loader` 가 있었다면, 수정된 제목이 반영된 최신 목록으로 자동 갱신된다.
- 개발자는 "수정 후 목록을 다시 불러와서 화면을 갱신하라"는 코드를 따로 작성할 필요가 없다. 이것이 데이터 모드가 데이터 변경과 화면 동기화를 자동으로 처리해 주는 핵심 방식이다.

정리하면, `action` 은 라우트 객체에 정의하는 "데이터를 바꾸는 함수"이며, 폼 제출 등을 통해 호출되고 나면 관련된 `loader` 데이터가 자동으로 재검증되어 UI가 항상 최신 데이터와 일치하도록 유지된다.
