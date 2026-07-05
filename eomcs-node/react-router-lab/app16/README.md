# [선언형 모드] app16. URL Search Params

## URL Search Params

검색 파라미터(search params)는 URL에서 `?` 뒤에 오는 값들이다. `useSearchParams` 를 통해 접근할 수 있으며, 이 훅은 [`URLSearchParams`](https://developer.mozilla.org/en-US/docs/Web/API/URLSearchParams) 인스턴스를 반환한다.

```jsx
function SearchResults() {
  let [searchParams] = useSearchParams();
  return (
    <div>
      <p>
        You searched for <i>{searchParams.get("q")}</i>
      </p>
      <FakeSearchResults />
    </div>
  );
}
```

## 라우트 설명

위 코드는 URL의 쿼리 스트링(`?q=...`) 값을 `useSearchParams()` 로 읽어오는 예이다.

```tsx
let [searchParams] = useSearchParams();
```

- `useSearchParams()` 는 배열을 반환하는데, 첫 번째 값은 현재 URL의 검색 파라미터를 담은 `URLSearchParams` 객체이고, 두 번째 값은 검색 파라미터를 바꿀 때 쓰는 함수이다(여기서는 값을 읽기만 하므로 첫 번째 값만 구조 분해했다).
- 예를 들어 URL이 `/search?q=react-router` 라면, `searchParams` 에는 `q=react-router` 라는 쿼리 값이 담겨 있다.

```tsx
searchParams.get("q")
```

- `URLSearchParams` 객체의 `get(이름)` 메서드로 특정 파라미터 값을 꺼낸다.
- `/search?q=react-router` 로 접속했다면 `searchParams.get("q")` 는 `"react-router"` 를 반환한다.
- 해당 파라미터가 URL에 없으면 `null` 을 반환한다.

```tsx
function SearchResults() {
  let [searchParams] = useSearchParams();
  return (
    <div>
      <p>
        You searched for <i>{searchParams.get("q")}</i>
      </p>
      <FakeSearchResults />
    </div>
  );
}
```

- 검색 결과 화면에서 사용자가 입력했던 검색어(`q`)를 다시 보여주거나, 이 값을 이용해 실제 검색 API를 호출하는 데 사용할 수 있다.
- Route Params(`useParams`)가 `/concerts/:city` 처럼 경로 세그먼트에 포함된 값을 다루는 것과 달리, URL Search Params(`useSearchParams`)는 `?key=value&key2=value2` 형태의 부가적인 값을 다룬다는 점이 다르다.

정리하면, URL Search Params는 URL의 `?` 뒤에 오는 값들이며, `useSearchParams()` 가 반환하는 `URLSearchParams` 객체의 `get()` 메서드로 원하는 파라미터 값을 읽는다.