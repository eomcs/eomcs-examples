# [데이터 모드] app36. 라우트 객체: lazy

## `lazy`

대부분의 속성은 초기 번들 크기를 줄이기 위해 지연 임포트(lazily import)할 수 있다.

```jsx
createBrowserRouter([
  {
    path: "/app",
    lazy: async () => {
      // load component and loader in parallel before rendering
      const [Component, loader] = await Promise.all([
        import("./app"),
        import("./app-loader"),
      ]);
      return { Component, loader };
    },
  },
]);
```

## 라우트 설명

위 코드는 라우트 객체에 `Component`, `loader` 를 미리 적어두는 대신, `lazy` 함수로 필요할 때에만 불러오도록 만드는 예이다.

```tsx
createBrowserRouter([
  {
    path: "/app",
    lazy: async () => {
      ...
    },
  },
]);
```

- 지금까지의 예제들은 `Component: Home` 처럼 컴포넌트를 미리 임포트해서 라우트 객체에 바로 연결했다. 이 경우 앱이 처음 실행될 때 그 컴포넌트 코드까지 한 번에 번들에 포함되어 다운로드된다.
- `lazy` 속성을 쓰면, 이 라우트가 실제로 매칭되어 필요해지는 시점에야 관련 코드를 불러오도록 미룰 수 있다. 그만큼 앱을 처음 열 때 받아야 하는 자바스크립트 용량(초기 번들 크기)이 줄어든다.

```tsx
lazy: async () => {
  // load component and loader in parallel before rendering
  const [Component, loader] = await Promise.all([
    import("./app"),
    import("./app-loader"),
  ]);
  return { Component, loader };
},
```

- `lazy` 에는 비동기 함수를 지정한다. 이 함수는 라우트가 필요해지는 시점에 리액트 라우터가 자동으로 호출해 준다.
- `import("./app")`, `import("./app-loader")` 는 동적 임포트(dynamic import) 문법으로, 해당 모듈을 필요한 시점에 별도의 파일로 나누어(코드 스플리팅) 불러온다.
- `Promise.all([...])` 로 두 모듈을 동시에(병렬로) 불러와서, 컴포넌트를 렌더링하기 전에 필요한 것들을 함께 준비한다.
- 마지막으로 `{ Component, loader }` 형태의 객체를 반환한다. 이렇게 반환된 값이, 마치 라우트 객체에 `Component`, `loader` 속성을 직접 적어놓은 것과 똑같이 동작한다.

정리하면, `lazy` 는 라우트에 필요한 컴포넌트나 `loader` 등을 앱 시작 시점이 아니라 그 라우트가 실제로 필요해지는 시점에 불러오도록 미루는 속성이며, 라우트별로 코드를 나누어(코드 스플리팅) 초기 로딩 속도를 개선하는 데 사용한다.
