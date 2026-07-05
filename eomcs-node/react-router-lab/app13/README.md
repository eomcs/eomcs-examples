# [선언형 모드] app13. Link 컴포넌트

## Link

활성 상태 스타일이 필요 없는 링크라면 `<Link>` 를 사용한다.

```jsx
import { Link } from "react-router";

export function LoggedOutMessage() {
  return (
    <p>
      You've been logged out.{" "}
      <Link to="/login">Login again</Link>
    </p>
  );
}
```

## 라우트 설명

위 코드는 단순히 다른 라우트로 이동시키는 `Link` 컴포넌트를 사용하는 예이다.

```tsx
<Link to="/login">Login again</Link>
```

- `to="/login"` 은 이 링크를 클릭했을 때 이동할 경로이다.
- 일반 HTML의 `<a href="/login">` 과 비슷하게 보이고 동작하지만, 브라우저가 페이지를 통째로 새로고침하지 않고 리액트 라우터가 URL과 화면만 바꿔주는 SPA(싱글 페이지 애플리케이션) 방식으로 이동한다.
- `NavLink` 와 달리 현재 URL과 일치하는지 여부(`isActive`)를 신경 쓰지 않는다. 그래서 "로그아웃되었습니다. 다시 로그인하세요." 같은 안내 문구 속 링크처럼, 활성 상태 표시가 필요 없는 곳에 적합하다.

```tsx
export function LoggedOutMessage() {
  return (
    <p>
      You've been logged out.{" "}
      <Link to="/login">Login again</Link>
    </p>
  );
}
```

- 이렇게 문장 중간에 자연스럽게 링크를 끼워 넣는 용도로도 `Link` 를 많이 사용한다.
- 내비게이션 메뉴처럼 "지금 어떤 메뉴가 선택되어 있는지" 보여줄 필요가 없다면, 굳이 `NavLink` 를 쓰지 않고 `Link` 로 충분하다.

정리하면, `Link` 는 활성 상태 표시가 필요 없는 일반적인 페이지 이동 링크에 사용하고, 활성 상태를 스타일로 표시해야 하는 내비게이션 메뉴에는 `NavLink` 를 사용한다.
