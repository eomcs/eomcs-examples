# [선언형 모드] app17. Location Object

## Location Object

리액트 라우터는 몇 가지 유용한 정보를 담은 커스텀 `location` 객체를 만들며, `useLocation` 으로 접근할 수 있다.

```jsx
function useAnalytics() {
  let location = useLocation();
  useEffect(() => {
    sendFakeAnalytics(location.pathname);
  }, [location]);
}

function useScrollRestoration() {
  let location = useLocation();
  useEffect(() => {
    fakeRestoreScroll(location.key);
  }, [location]);
}
```

## 라우트 설명

위 코드는 `useLocation()` 이 반환하는 `location` 객체를 이용해서, URL이 바뀔 때마다 특정 동작을 실행하는 예이다.

```tsx
function useAnalytics() {
  let location = useLocation();
  useEffect(() => {
    sendFakeAnalytics(location.pathname);
  }, [location]);
}
```

- `useLocation()` 은 현재 URL에 대한 정보를 담은 `location` 객체를 반환한다. 이 객체에는 `pathname`, `search`, `hash`, `key` 등의 속성이 들어 있다.
- `location.pathname` 은 도메인과 쿼리 스트링을 뺀, 순수한 경로 부분이다(예: `/concerts/seoul`).
- `useEffect` 의 의존성 배열에 `location` 을 넣었으므로, 사용자가 다른 페이지로 이동해서 `location` 이 바뀔 때마다 콜백이 다시 실행된다.
- 이런 방식으로, 페이지가 바뀔 때마다 방문 기록을 분석 서비스로 보내는 "페이지뷰 트래킹"을 구현할 수 있다.

```tsx
function useScrollRestoration() {
  let location = useLocation();
  useEffect(() => {
    fakeRestoreScroll(location.key);
  }, [location]);
}
```

- `location.key` 는 방문한 각 위치(location)마다 고유하게 부여되는 값이다. 같은 `pathname` 이라도 다른 시점에 방문했다면 `key` 값이 다르다.
- 이 값을 이용하면 "이전에 봤던 페이지로 돌아왔을 때 스크롤 위치를 복원한다" 같은 기능을 구현할 수 있다.
- 즉, `pathname` 만으로는 구분할 수 없는 "같은 경로라도 서로 다른 방문"을 구분하고 싶을 때 `location.key` 를 활용한다.

정리하면, `useLocation()` 은 현재 URL에 대한 상세 정보(`pathname`, `search`, `hash`, `key` 등)를 담은 `location` 객체를 반환하며, 이를 `useEffect` 와 함께 사용하면 URL이 바뀔 때마다 분석 로그 전송, 스크롤 위치 복원 같은 부가 작업을 실행할 수 있다.
