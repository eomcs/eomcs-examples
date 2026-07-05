# app34. Actions를 사용한 폼

## 개요

리액트 폼은 데이터를 제출하는 강력한 도구다. 이전 실습에서는 검색어를 API에 제출해서 데이터를 가져오는 폼을 소개했다. 이때 데이터 페칭 과정을 트리거하기 위해 `onSubmit` 이벤트 핸들러를 사용했다. 이번 실습에서는 폼에 새로운 개념인 **`action` 속성**을 소개한다.

`action` 속성은 폼 데이터가 어디로 제출돼야 하는지 URL을 지정하는 표준 HTML 속성이다. 리액트를 사용할 때는 폼 컴포넌트에 **액션 함수**를 전달할 수 있는데, 이 함수는 폼이 제출될 때 실행된다.

> Java에 비유하면, 기존 HTML `<form action="/search" method="GET">`이 서버의 특정 엔드포인트로 요청을 위임하는 것과 비슷하게, 리액트 19의 `action` 속성은 그 자리에 자바스크립트 함수를 직접 꽂아 넣어서 브라우저의 네이티브 폼 제출 메커니즘에 더 가깝게 동작하게 만든다. `onSubmit` + `event.preventDefault()` 조합보다 더 선언적인 방식이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const SearchForm = ({
  searchTerm,
  onSearchInput,
  onSearchSubmit,
}) => (
  <form onSubmit={onSearchSubmit}>
    <InputWithLabel
      id="search"
      value={searchTerm}
      isFocused
      onInputChange={onSearchInput}
    >
      <strong>Search:</strong>
    </InputWithLabel>

    <button type="submit" disabled={!searchTerm}>
      Submit
    </button>
  </form>
);

const App = () => {
  // ...

  const handleSearchSubmit = (event) => {
    setUrl(`${API_ENDPOINT}${searchTerm}`);

    event.preventDefault();
  };

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <SearchForm
        searchTerm={searchTerm}
        onSearchInput={handleSearchInput}
        onSearchSubmit={handleSearchSubmit}
      />

      {/* ... */}
    </div>
  );
};
```

## 실습

### 실습 1. `SearchForm`에서 `onSubmit` 대신 `action` 속성 사용하기

```jsx
// src/App.jsx
const SearchForm = ({ searchTerm, onSearchInput, searchAction }) => (
  <form action={searchAction}>
    <InputWithLabel
      id="search"
      value={searchTerm}
      isFocused
      onInputChange={onSearchInput}
    >
      <strong>Search:</strong>
    </InputWithLabel>

    <button type="submit" disabled={!searchTerm}>
      Submit
    </button>
  </form>
);
```

제출 핸들러를 `form`의 `onSubmit` 속성에 전달하는 대신, 새로운 `searchAction` 함수를 `form`의 `action` 속성에 전달한다.

### 실습 2. `App` 컴포넌트에서 `searchAction` 전달하기

```jsx
// src/App.jsx
<SearchForm
  searchTerm={searchTerm}
  onSearchInput={handleSearchInput}
  searchAction={searchAction}
/>
```

### 실습 3. `preventDefault()` 호출 제거하기

이제 네이티브 폼 동작에 더 가까워졌으므로, 제출 핸들러에서 `preventDefault()` 호출을 제거할 수 있다.

```jsx
// src/App.jsx
const searchAction = () => {
  setUrl(`${API_ENDPOINT}${searchTerm}`);

  // event.preventDefault(); <--- 더 이상 필요 없다
};
```

**리액트 19부터는 폼 데이터를 제출할 때 `onSubmit` 속성보다 `action` 속성을 사용하는 쪽으로 나아가고 있는데, 이 방식이 네이티브 폼 동작을 더 잘 활용하기 때문이다.** 참고로 폼 액션 함수의 시그니처를 통해 폼 데이터(`FormData`)에 접근할 수도 있는데, 이는 폼 유효성 검사나 그 밖의 폼 관련 작업에 유용하게 쓰일 수 있다.

## 정리

- **`action` 속성**은 폼 데이터가 어디로 제출돼야 하는지를 나타내는 표준 HTML 속성이며, 리액트에서는 여기에 자바스크립트 함수를 직접 전달할 수 있다.
- `onSubmit` 핸들러를 `action`에 연결된 액션 함수로 바꾸면, 네이티브 폼 제출 동작에 더 가까워지므로 `event.preventDefault()` 호출이 더 이상 필요하지 않다.
- 리액트 19부터는 `onSubmit`보다 `action` 속성을 사용하는 방향으로 나아가고 있다.
- 폼 액션 함수는 시그니처를 통해 `FormData`에 접근할 수 있어서, 폼 유효성 검사 등 다양한 폼 관련 작업에 활용할 수 있다.
