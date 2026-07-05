# [선언형 모드] app03. 복잡한 라우트 구성하기

## 복잡한 구조의 라우트 구성

```tsx
<Routes>
  <Route index element={<Home />} />
  <Route path="about" element={<About />} />

  <Route element={<AuthLayout />}>
    <Route path="login" element={<Login />} />
    <Route path="register" element={<Register />} />
  </Route>

  <Route path="concerts">
    <Route index element={<ConcertsHome />} />
    <Route path=":city" element={<City />} />
    <Route path="trending" element={<Trending />} />
  </Route>
</Routes>
```

위 코드는 여러 URL 경로를 하나의 `<Routes>` 안에서 선언하는 예이다.

```tsx
<Route index element={<Home />} />

// Home 컴포넌트
export function Home() {
  return (
    <div>
      <h1>Home</h1>
      <nav>
        <Link to="/about">About</Link>{" "}
        <Link to="/login">Login</Link>{" "}
        <Link to="/register">Register</Link>{" "}
        <Link to="/concerts">Concerts</Link>{" "}
        <Link to="/concerts/seoul">Seoul Concerts</Link>{" "}
        <Link to="/concerts/trending">Trending Concerts</Link>
      </nav>
    </div>
  );
}
```

- 부모 경로의 기본 화면 
- `/` → `<Home />`

```tsx
<Route path="about" element={<About />} />

// About 컴포넌트
export function About() {
  return <h1>About</h1>;
}
```

- path 속성에 지정한 URL 경로와 element 속성에 지정한 컴포넌트를 연결한다.
- `/about` → `<About />`

```tsx
<Route element={<AuthLayout />}>
  <Route path="login" element={<Login />} />
  <Route path="register" element={<Register />} />
</Route>

// AuthLayout 컴포넌트
export function AuthLayout() {
  return (
    <div>
      <h1>Auth</h1>
      <Outlet />
    </div>
  );
}

// Login 컴포넌트
export function Login() {
  return <h2>Login</h2>;
}

// Register 컴포넌트
export function Register() {
  return <h2>Register</h2>;
}
```

- `path` 없이 선언한 라우트는 URL 경로를 추가하지 않고 공통 레이아웃만 제공한다. 
- 이 안에 있는 `login`, `register` 라우트는 각각 `/login`, `/register` 경로에서 동작한다.
- 화면은 `<AuthLayout />` 내부의 `<Outlet />` 위치에 렌더링된다.

```tsx
<Route path="concerts">...</Route>
```

- `/concerts` 아래에 여러 하위 라우트를 묶는다. 
- 이 라우트 자체에는 `element` 가 없기 때문에 하위 라우트의 경로를 그룹화하는 역할만 한다.

```tsx
<Route index element={<ConcertsHome />} />

// ConcertsHome 컴포넌트
export function ConcertsHome() {
  return <h1>Concerts</h1>;
}
```

- `/concerts` 경로의 기본 화면을 지정한다.

```tsx
<Route path=":city" element={<City />} />

// City 컴포넌트
export function City() {
  const { city } = useParams();

  return <h1>{city} Concerts</h1>;
}
```
- `:city` 는 URL 파라미터이다. 
- `/concerts/seoul`, `/concerts/tokyo` 처럼 도시 이름이 바뀌어도 같은 `<City />` 컴포넌트가 렌더링된다.

```tsx
<Route path="trending" element={<Trending />} />

// Trending 컴포넌트
export function Trending() {
  return <h1>Trending Concerts</h1>;
}
```

- `/concerts/trending` 경로와 `<Trending />` 컴포넌트를 연결한다.

이처럼 라우트를 중첩하면 공통 레이아웃을 재사용하거나, 관련 있는 URL들을 계층적으로 묶어서 관리할 수 있다.
