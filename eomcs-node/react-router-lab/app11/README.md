# [선언형 모드] app11. UI 연결

## Linking

UI에서 `Link` 와 `NavLink` 로 다른 라우트로 연결하는 링크를 만들 수 있다.

```jsx
import { Link, NavLink, useParams } from "react-router";
import "./App.css";

function Header() {
  return (
    <nav className="site-nav">
      <NavLink
        to="/"
        className={({ isActive }) => (isActive ? "active" : "")}
        end
      >
        Home
      </NavLink>{" "}
      <Link to="/concerts/salt-lake-city">Concerts</Link>
    </nav>
  );
}
```

```css
/* App.css */
.site-nav {
  display: flex;
  justify-content: center;
  gap: 8px;
  padding: 24px;
  border-bottom: 1px solid var(--border);
}

.site-nav a {
  color: var(--text);
  text-decoration: none;
  border-radius: 6px;
  padding: 6px 12px;
  transition:
    background 0.2s,
    color 0.2s;
}

.site-nav a:hover {
  color: var(--text-h);
  background: var(--social-bg);
}

.site-nav a.active {
  color: var(--accent);
  background: var(--accent-bg);
  font-weight: 600;
}
```

## 라우트 설명

위 코드는 `NavLink` 와 `Link` 로 내비게이션 메뉴를 만드는 예이다.

```tsx
<NavLink
  to="/"
  className={({ isActive }) =>
    isActive ? "active" : ""
  }
>
  Home
</NavLink>
```

- `NavLink` 는 현재 URL과 `to` 값이 일치하는지 스스로 판단해서 "활성(active) 상태"를 알려주는 링크 컴포넌트이다.
- `className` 에 함수를 전달하면, 그 함수는 `{ isActive }` 객체를 인자로 받는다. 현재 URL이 `/` 와 일치하면 `isActive` 가 `true` 가 되어 `"active"` 클래스가 붙는다.
- 즉, 현재 보고 있는 메뉴를 강조 표시할 때 `NavLink` 를 사용한다(예: 내비게이션 바에서 선택된 메뉴 스타일 표시).

```tsx
<Link to="/concerts/salt-lake-city">Concerts</Link>
```

- `Link` 는 `NavLink` 와 달리 활성 상태를 신경 쓰지 않는, 단순한 이동용 링크이다.
- `to="/concerts/salt-lake-city"` 로 지정한 경로로 이동하며, 일반 HTML의 `<a href="...">` 와 비슷하게 동작하지만 페이지를 새로 불러오지 않고 SPA 방식으로 이동한다.
- 활성 스타일이 필요 없는 단순한 링크라면 `NavLink` 대신 `Link` 를 쓰는 편이 간단하다.

정리하면, `Link` 는 단순히 다른 라우트로 이동하는 링크이고, `NavLink` 는 여기에 더해 현재 URL과 일치하는지 여부(`isActive`)를 알려주어 활성 메뉴 스타일을 쉽게 적용할 수 있게 해주는 링크이다.
