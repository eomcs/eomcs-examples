# [데이터 모드] app32. 라우트 객체: middleware

## `middleware`

라우트 [미들웨어(middleware)](https://reactrouter.com/how-to/middleware)는 내비게이션 전후로 순차적으로 실행된다. 로깅이나 인증 같은 작업을 한 곳에서 처리할 수 있게 해준다. `next` 함수는 체인을 따라 다음 단계로 진행시키며, 리프(leaf) 라우트에서는 `next` 함수가 해당 내비게이션의 `loader`/`action` 을 실행한다.

```jsx
createBrowserRouter([
  {
    path: "/",
    middleware: [loggingMiddleware],
    loader: rootLoader,
    Component: Root,
    children: [{
      path: 'auth',
      middleware: [authMiddleware],
      loader: authLoader,
      Component: Auth,
      children: [...]
    }]
  },
]);

async function loggingMiddleware({ request }, next) {
  let url = new URL(request.url);
  console.log(`Starting navigation: ${url.pathname}${url.search}`);
  const start = performance.now();
  await next();
  const duration = performance.now() - start;
  console.log(`Navigation completed in ${duration}ms`);
}

const userContext = createContext<User>();

async function authMiddleware ({ context }) {
  const userId = getUserId();

  if (!userId) {
    throw redirect("/login");
  }

  context.set(userContext, await getUserById(userId));
};
```

참고:

- [Middleware 사용법](https://reactrouter.com/how-to/middleware)

## 라우트 설명

위 코드는 루트 라우트와 그 자식 라우트 각각에 미들웨어를 붙여서, 로깅과 인증 검사를 내비게이션마다 자동으로 실행시키는 예이다.

```tsx
{
  path: "/",
  middleware: [loggingMiddleware],
  loader: rootLoader,
  Component: Root,
  children: [...]
},
```

- `middleware` 는 라우트 객체에 배열로 지정하는 속성이며, 이 라우트가 매칭될 때 `loader`/`action` 실행 전후로 호출된다.
- 루트 라우트에 `loggingMiddleware` 를 붙였으므로, `/` 이하 모든 내비게이션에서 이 미들웨어가 실행된다.

```tsx
async function loggingMiddleware({ request }, next) {
  let url = new URL(request.url);
  console.log(`Starting navigation: ${url.pathname}${url.search}`);
  const start = performance.now();
  await next();
  const duration = performance.now() - start;
  console.log(`Navigation completed in ${duration}ms`);
}
```

- 미들웨어 함수는 첫 번째 인자로 `{ request }` 등을 담은 컨텍스트를, 두 번째 인자로 `next` 함수를 받는다.
- `await next()` 를 호출하기 전의 코드는 내비게이션이 "시작되기 전"에 실행되고, `await next()` 이후의 코드는 그 내비게이션이(하위 미들웨어와 `loader` 까지 포함해서) "완료된 후"에 실행된다.
- 이 예제에서는 `next()` 호출 전에 시작 로그를, 호출 후에 걸린 시간과 함께 완료 로그를 남긴다. 즉, 하나의 미들웨어 함수 안에서 "이전(before)"과 "이후(after)" 동작을 모두 정의할 수 있다.

```tsx
{
  path: 'auth',
  middleware: [authMiddleware],
  loader: authLoader,
  Component: Auth,
  children: [...]
}
```

- 자식 라우트인 `auth` 에는 별도로 `authMiddleware` 를 붙였다.
- 미들웨어는 라우트 트리를 따라 순차적으로 실행되므로, `/auth` 이하 경로로 이동하면 `loggingMiddleware` → `authMiddleware` → (해당 라우트의 `loader`/`action`) 순서로 체인이 이어진다.

```tsx
const userContext = createContext<User>();

async function authMiddleware ({ context }) {
  const userId = getUserId();

  if (!userId) {
    throw redirect("/login");
  }

  context.set(userContext, await getUserById(userId));
};
```

- `authMiddleware` 는 로그인 여부를 확인해서, 로그인하지 않은 사용자라면 `redirect("/login")` 를 던져서 로그인 페이지로 보내버린다(이 미들웨어에는 `next` 를 별도로 호출하지 않았으므로, 다음 단계로 진행시키려면 정상적인 경우 마지막에 `next()` 를 호출해 주어야 한다).
- `context.set(userContext, ...)` 처럼 `context` 에 값을 저장해 두면, 이 요청을 처리하는 뒤쪽의 미들웨어나 `loader`/`action`, 그리고 컴포넌트에서까지 그 값을 꺼내 쓸 수 있다. 이런 방식으로 "로그인한 사용자 정보를 한 번만 조회해서 여러 곳에서 재사용"하는 인증 처리를 구현할 수 있다.

정리하면, `middleware` 는 라우트별로 내비게이션 전후에 실행할 로직(로깅, 인증 검사 등)을 체인 형태로 구성할 수 있게 해주는 속성이며, `next()` 호출 시점을 기준으로 "이전/이후" 동작을 나누고, `context` 를 통해 미들웨어 사이에 값을 공유한다.
