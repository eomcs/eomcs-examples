# [데이터 모드] app31. 라우트 객체와 컴포넌트

## Introduction

`createBrowserRouter` 에 전달하는 객체들을 라우트 객체(Route Objects)라고 부른다.

```jsx
createBrowserRouter([
  {
    path: "/",
    Component: App,
  },
]);
```

라우트 모듈(route modules)은 리액트 라우터가 제공하는 데이터 기능의 기반이며, 다음과 같은 것들을 정의한다.

- 데이터 로딩(data loading)
- 액션(actions)
- 재검증(revalidation)
- 에러 바운더리(error boundaries)
- 그 외 여러 기능들

## Component

라우트 객체의 `Component` 속성은 라우트가 매칭되었을 때 렌더링할 컴포넌트를 지정한다.

```jsx
createBrowserRouter([
  {
    path: "/",
    Component: MyRouteComponent,
  },
]);

function MyRouteComponent() {
  return (
    <div>
      <h1>Look ma!</h1>
      <p>
        I'm still using React Router after like 10 years.
      </p>
    </div>
  );
}
```

## 라우트 설명

위 코드는 라우트 객체가 어떤 성격의 것인지, 그리고 그중 화면을 담당하는 `Component` 속성이 어떻게 동작하는지 보여주는 예이다.

```tsx
createBrowserRouter([
  {
    path: "/",
    Component: App,
  },
]);
```

- `createBrowserRouter()` 에 전달하는 배열의 각 원소 하나하나가 "라우트 객체"이다.
- 라우트 객체는 단순히 `path` 와 화면만 연결하는 것이 아니라, 뒤에서 다룰 `loader`(데이터 로딩), `action`(데이터 변경), `middleware`, `shouldRevalidate`(재검증 제어), `lazy`(지연 로딩), `handle` 등 다양한 속성을 함께 가질 수 있다.
- 즉, 데이터 모드의 라우트 객체는 "URL과 화면의 연결"뿐 아니라 "이 라우트에서 데이터를 어떻게 가져오고 어떻게 바꿀지"까지 한 곳에 정의하는 단위이다.

```tsx
createBrowserRouter([
  {
    path: "/",
    Component: MyRouteComponent,
  },
]);

function MyRouteComponent() {
  return (
    <div>
      <h1>Look ma!</h1>
      <p>
        I'm still using React Router after like 10 years.
      </p>
    </div>
  );
}
```

- `Component` 속성에는 라우트가 매칭되었을 때 렌더링할 리액트 컴포넌트를 그대로 지정한다(함수를 호출한 결과인 JSX가 아니라, 컴포넌트 함수 자체를 전달한다는 점에 유의한다).
- 예를 들어 `path: "/"` 이고 `Component: MyRouteComponent` 이면, `/` 로 접속했을 때 `<MyRouteComponent />` 가 렌더링된 것과 같은 결과가 된다.
- 이전 예제들(`Component: Home`, `Component: Dashboard` 등)에서 이미 계속 사용해 온 속성이 바로 이 `Component` 이다.

정리하면, 라우트 객체는 라우팅 정보 이상으로 데이터 로딩·변경·재검증까지 아우르는 설정 단위이며, 그중 `Component` 속성이 화면에 무엇을 렌더링할지를 결정한다.
