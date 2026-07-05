# [선언형 모드] app12. NavLink 컴포넌트

## NavLink

이 컴포넌트는 활성(active) 상태를 화면에 표시해야 하는 내비게이션 링크에 사용한다.

```jsx
import { NavLink } from "react-router";

export function MyAppNav() {
  return (
    <nav>
      <NavLink to="/" end>
        Home
      </NavLink>
      <NavLink to="/trending" end>
        Trending Concerts
      </NavLink>
      <NavLink to="/concerts">All Concerts</NavLink>
      <NavLink to="/account">Account</NavLink>
    </nav>
  );
}
```

`NavLink` 가 활성 상태이면 CSS로 손쉽게 스타일을 줄 수 있도록 자동으로 `.active` 클래스 이름이 붙는다.

```css
a.active {
  color: red;
}
```

`className`, `style`, `children` 속성에는 활성 상태 값을 받는 콜백 함수를 전달할 수도 있어서, 인라인 스타일이나 조건부 렌더링에 활용할 수 있다.

```jsx
// className
<NavLink
  to="/messages"
  className={({ isActive }) =>
    isActive ? "text-red-500" : "text-black"
  }
>
  Messages
</NavLink>
```

```jsx
// style
<NavLink
  to="/messages"
  style={({ isActive }) => ({
    color: isActive ? "red" : "black",
  })}
>
  Messages
</NavLink>
```

```jsx
// children
<NavLink to="/message">
  {({ isActive }) => (
    <span className={isActive ? "active" : ""}>
      {isActive ? "👉" : ""} Tasks
    </span>
  )}
</NavLink>
```

## 라우트 설명

위 코드는 `NavLink` 가 활성 상태를 판단해서 스타일을 다르게 적용하는 여러 방법을 보여주는 예이다.

```tsx
<NavLink to="/" end>
  Home
</NavLink>
```

- `to="/"` 는 이 링크가 이동할 경로이다.
- `end` 속성은 경로를 "끝까지 정확히" 일치할 때만 활성 상태로 판단하라는 옵션이다. `end` 가 없으면 `/` 로 시작하는 모든 하위 경로(`/trending`, `/concerts` 등)에서도 `/` 링크가 활성 상태가 되어버릴 수 있으므로, 최상위 경로(`Home`)에는 보통 `end` 를 붙인다.

```tsx
<NavLink to="/trending" end>
  Trending Concerts
</NavLink>
<NavLink to="/concerts">All Concerts</NavLink>
```

- `/trending` 링크도 정확히 일치할 때만 활성화되도록 `end` 를 붙였다.
- `/concerts` 링크는 `end` 가 없으므로, `/concerts` 뿐 아니라 `/concerts/salt-lake-city` 같은 하위 경로에서도 활성 상태로 표시된다.

```css
a.active {
  color: red;
}
```

- `NavLink` 가 활성 상태이면 렌더링되는 `<a>` 태그에 자동으로 `active` 클래스가 붙는다.
- 그래서 별도의 로직 없이 CSS 선택자 `a.active` 만으로 활성 링크 스타일을 지정할 수 있다.

```tsx
<NavLink
  to="/messages"
  className={({ isActive }) =>
    isActive ? "text-red-500" : "text-black"
  }
>
  Messages
</NavLink>
```

- `className` 에 문자열 대신 함수를 전달하면, 그 함수는 `{ isActive }` 를 인자로 받아 클래스 이름을 동적으로 계산할 수 있다.
- 기본 제공되는 `active` 클래스 대신, Tailwind 같은 유틸리티 클래스 이름을 직접 조건에 따라 지정하고 싶을 때 유용하다.

```tsx
<NavLink
  to="/messages"
  style={({ isActive }) => ({
    color: isActive ? "red" : "black",
  })}
>
  Messages
</NavLink>
```

- `style` 에도 같은 방식으로 함수를 전달해서, 활성 상태에 따라 인라인 스타일 객체를 다르게 만들 수 있다.

```tsx
<NavLink to="/message">
  {({ isActive }) => (
    <span className={isActive ? "active" : ""}>
      {isActive ? "👉" : ""} Tasks
    </span>
  )}
</NavLink>
```

- `children` 자리에도 함수(render prop)를 전달할 수 있다. 이 함수 역시 `{ isActive }` 를 받아서, 활성 상태에 따라 다른 내용을 렌더링할 수 있다.
- 예시에서는 활성 상태일 때만 앞에 "👉" 이모지를 붙여서 보여준다.

정리하면, `NavLink` 는 `Link` 의 기능에 더해 현재 URL과의 활성 여부(`isActive`)를 `className`, `style`, `children` 콜백으로 전달해준다. `end` 속성으로 정확히 일치할 때만 활성으로 볼지, 하위 경로까지 포함해서 활성으로 볼지를 조절할 수 있다.
