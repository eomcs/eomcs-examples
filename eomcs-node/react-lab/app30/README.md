# app30. 리액트의 명시적 데이터 가져오기

## 개요

사용자가 입력 필드에 타이핑할 때마다 매번 모든 데이터를 다시 가져오는 것은 최선의 방식이 아니다. 데이터를 가져오는 데 서드파티 API를 쓰고 있는 이상, 그 내부 동작은 우리가 어찌할 수 있는 영역이 아니다. 결국에는 데이터 대신 에러를 돌려주는 **요청 제한(rate limiting)**을 마주치게 될 것이다. 이 문제를 해결하기 위해, 데이터를 **암묵적으로(implicit)** 다시 가져오던 방식을 **명시적으로(explicit)** 다시 가져오는 방식으로 바꿔본다. 다시 말해, 사용자가 확인 버튼을 클릭했을 때만 애플리케이션이 데이터를 다시 가져오게 만든다.

**과제**: 서버 사이드 검색이 사용자가 입력 필드에 타이핑할 때마다 실행되고 있다. 새로운 구현에서는 사용자가 확인 버튼을 클릭했을 때만 검색이 실행돼야 한다. 버튼을 클릭하기 전까지는 검색어가 바뀌어도 API 요청으로 이어지지 않아야 한다.

**힌트**:

- 검색 요청을 확인하는 버튼 엘리먼트를 추가한다.
- 확인된 검색어를 위한 상태 값을 새로 만든다.
- 버튼의 이벤트 핸들러는 현재 검색어를 사용해서 확인된 검색어를 상태로 설정한다.
- 새로 확인된 검색어가 상태로 설정될 때만, 서버 사이드 검색을 수행하는 사이드 이펙트를 실행한다.

> Java에 비유하면, 지금까지는 텍스트 필드에 한 글자 입력할 때마다 자동으로 쿼리를 다시 날리는 "실시간 검색"이었다면, 이번 실습은 "검색" 버튼을 눌러야만 쿼리가 실행되는 방식으로 바꾸는 것과 같다. 화면의 입력값(뷰 모델)과 실제로 서버에 전송된 확정된 쿼리 파라미터를 별개의 필드로 분리해서 관리하는 것과 같은 발상이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const API_ENDPOINT = 'https://hn.algolia.com/api/v1/search?query=';

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    { data: [], isLoading: false, isError: false }
  );

  const handleFetchStories = React.useCallback(() => {
    if (!searchTerm) return;

    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}${searchTerm}`)
      .then((response) => response.json())
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [searchTerm]);

  React.useEffect(() => {
    handleFetchStories();
  }, [handleFetchStories]);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  // ...
};
```

## 실습

이번 기능에서 중요한 점은, 계속 변하는 `searchTerm`을 위한 상태 하나와, **확인된 검색어**를 위한 새로운 상태가 각각 필요하다는 것이다.

### 실습 1. 검색을 확인하는 버튼 추가하기

먼저 검색을 확인하고 결국 데이터 요청을 실행할 새로운 버튼 엘리먼트를 만든다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <InputWithLabel
        id="search"
        value={searchTerm}
        isFocused
        onInputChange={handleSearchInput}
      >
        <strong>Search:</strong>
      </InputWithLabel>

      <button
        type="button"
        disabled={!searchTerm}
        onClick={handleSearchSubmit}
      >
        Submit
      </button>

      {/* ... */}
    </div>
  );
};
```

- 검색 요청 버튼을 추가한다.
- 입력 필드 핸들러를 `handleSearchInput`으로 바꾼다.
- 버튼 핸들러는 `handleSearchSubmit`으로 한다.

### 실습 2. 입력 필드 핸들러와 버튼 핸들러를 구분하기

그다음, 입력 필드의 핸들러와 버튼의 핸들러를 구분한다. 이름이 바뀐 입력 필드 핸들러는 여전히 상태화된 `searchTerm`을 설정하지만, 새로운 버튼 핸들러는 현재 `searchTerm`과 정적인 API 엔드포인트로부터 파생된 새로운 상태 값 `url`을 설정한다.

```jsx
const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState(
    'search',
    'React'
  );

  const [url, setUrl] = React.useState(
    `${API_ENDPOINT}${searchTerm}`
  );

  // ...

  const handleSearchInput = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = () => {
    setUrl(`${API_ENDPOINT}${searchTerm}`);
  };

  // ...
};
```

### 실습 3. `searchTerm` 대신 `url`을 사이드 이펙트의 의존성으로 사용하기

이전처럼 (입력 필드 값이 바뀔 때마다) `searchTerm`이 바뀔 때마다 데이터 페칭 사이드 이펙트를 실행하는 대신, 이제 사용자가 버튼을 클릭해서 검색 요청을 확인함으로써 바뀌는 새로운 상태화된 `url`을 사용한다.

```jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(url)
      .then((response) => response.json())
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [url]);

  React.useEffect(() => {
    handleFetchStories();
  }, [handleFetchStories]);

  // ...
};
```

이전에는 `searchTerm`이 두 가지 용도로 쓰였다. 입력 필드의 상태를 갱신하는 것과, 데이터를 가져오는 사이드 이펙트를 활성화하는 것이다. 이제는 전자의 용도로만 쓰인다. 사용자가 확인 버튼을 클릭할 때만 데이터를 가져오는 사이드 이펙트를 트리거하기 위해, `url`이라는 두 번째 상태를 새로 도입했다.

## 정리

- 데이터를 **명시적으로** 가져오게 만들면, 사용자가 확인 버튼을 클릭할 때만 API 요청이 발생해서 키 입력마다 요청을 보내는 것을 막을 수 있다.
- 계속 변하는 입력값(`searchTerm`)과 실제로 서버에 요청을 보낼 확정된 값(`url`)을 서로 다른 상태로 분리해서 관리한다.
- `url` 상태는 버튼 클릭 시에만 갱신되므로, `handleFetchStories`의 `useCallback` 의존성 배열도 `searchTerm`이 아니라 `url`로 바뀐다.
- 버튼에 `disabled={!searchTerm}`을 지정하면, 검색어가 비어 있을 때 버튼을 비활성화해서 빈 검색어에 대한 별도 방어 로직 없이도 예외 상황을 막을 수 있다.

## Q&A

- **Q. `url` 상태를 관리할 때 `useStorageState` 대신 `useState`를 사용하는 이유는 무엇인가?**
  - A. `url`은 이미 정적인 문자열(`API_ENDPOINT`)과, 브라우저 로컬 스토리지에서 오는 `searchTerm`으로부터 파생된 값이므로, 브라우저 로컬 스토리지에 따로 기억해둘 필요가 없기 때문이다.
- **Q. `handleFetchStories` 함수에 더 이상 빈 `searchTerm`에 대한 검사가 없는 이유는 무엇인가?**
  - A. 서버 사이드 검색을 막는 역할은 새로 추가된 버튼이 담당하기 때문이다. 이 버튼은 `searchTerm`이 없을 때 비활성화된다.
