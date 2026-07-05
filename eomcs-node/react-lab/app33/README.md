# app33. 리액트 폼

## 개요

폼을 쓰지 않는 현대적인 애플리케이션은 없다. **폼(form)**은 여러 입력 컨트롤(입력 필드, 체크박스, 라디오 버튼, 슬라이더 등)에서 받은 데이터를 버튼을 통해 제출하는, 이를 위한 제대로 된 수단일 뿐이다. 앞서 버튼 클릭으로 데이터를 명시적으로 가져오는 새로운 버튼을 소개했다. 이번에는 버튼과 검색어 입력 필드, 그 레이블을 감싸는 제대로 된 HTML `form`으로 발전시켜보자.

리액트의 JSX에서 폼은 HTML과 크게 다르지 않다. HTML/자바스크립트를 활용해서 두 단계 리팩터링으로 구현해본다.

> Java에 비유하면, `<form onSubmit={...}>`은 서블릿/스프링 MVC에서 `<form>` 태그가 특정 URL로 POST 요청을 보내는 것과 비슷한 역할을 한다. 다만 리액트에서는 실제로 페이지를 이동하지 않고, `event.preventDefault()`로 브라우저의 기본 제출 동작(전체 페이지 리로드)을 막은 뒤 자바스크립트 핸들러가 그 역할을 대신 수행한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleSearchInput = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = () => {
    setUrl(`${API_ENDPOINT}${searchTerm}`);
  };

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

## 실습

### 실습 1. 입력 필드와 버튼을 `form` 엘리먼트로 감싸기

먼저 입력 필드와 버튼을 HTML `form` 엘리먼트로 감싼다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <form onSubmit={handleSearchSubmit}>
        <InputWithLabel
          id="search"
          value={searchTerm}
          isFocused
          onInputChange={handleSearchInput}
        >
          <strong>Search:</strong>
        </InputWithLabel>

        <button type="submit" disabled={!searchTerm}>
          Submit
        </button>
      </form>

      <hr />

      {/* ... */}
    </div>
  );
};
```

`handleSearchSubmit()` 핸들러를 버튼에 전달하는 대신, 새로 만든 `form` 엘리먼트의 `onSubmit` 속성에서 사용한다. 버튼은 `submit`이라는 새로운 `type` 속성을 받는데, 이는 클릭을 처리하는 것이 버튼이 아니라 `form` 엘리먼트의 `onSubmit`이라는 것을 나타낸다.

### 실습 2. `preventDefault()`로 브라우저의 기본 동작 막기

그다음, 이 핸들러가 폼 이벤트에 쓰이므로, 리액트의 합성 이벤트(synthetic event)에 대해 추가로 `preventDefault()`를 실행한다. 이렇게 하면 브라우저가 새로고침되는 HTML 폼의 네이티브 기본 동작을 막을 수 있다.

```jsx
const App = () => {
  // ...

  const handleSearchSubmit = (event) => {
    setUrl(`${API_ENDPOINT}${searchTerm}`);

    event.preventDefault();
  };

  // ...
};
```

이제 독립된 버튼이 아니라 `form`을 사용하고 있으므로, 키보드의 "Enter" 키로도 검색 기능을 실행할 수 있다.

### 실습 3. `SearchForm` 컴포넌트로 분리하기

다음 두 단계에서는 이 폼 전체를 새로운 `SearchForm` 컴포넌트로 분리한다. 폼을 자체 컴포넌트로 추출하는 방법은 다음과 같다.

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
```

이 새로운 컴포넌트는 `App` 컴포넌트에서 인스턴스화된다. 다만 폼을 위한 상태는 여전히 `App` 컴포넌트가 관리하는데, 이 상태가 `App` 컴포넌트에서 데이터 요청을 트리거하고, 요청된 데이터는 결국 `List` 컴포넌트에 props(여기서는 `stories.data`)로 전달되기 때문이다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <SearchForm
        searchTerm={searchTerm}
        onSearchInput={handleSearchInput}
        onSearchSubmit={handleSearchSubmit}
      />

      <hr />

      {stories.isError && <p>Something went wrong ...</p>}

      {stories.isLoading ? (
        <p>Loading ...</p>
      ) : (
        <List list={stories.data} onRemoveItem={handleRemoveStory} />
      )}
    </div>
  );
};
```

- 리액트에서 폼은 일반 HTML과 크게 다르지 않다. 
- 데이터를 제출할 입력 필드와 버튼이 있을 때, 이를 `onSubmit` 속성을 가진 `form` 엘리먼트로 감싸면 HTML에 더 나은 구조를 부여할 수 있다. 
- 그러므로 제출을 실행하는 버튼에는 그 처리를 `form` 엘리먼트의 핸들러에 넘기기 위해 `"submit"` 타입이 필요하다. 
- 결국 이 방식은 키보드 사용자에게도 더 접근하기 쉬운 경험을 제공한다.

## 정리

- 입력 필드와 제출 버튼을 `<form onSubmit={...}>`으로 감싸면, HTML 구조가 명확해지고 키보드의 Enter 키로도 제출할 수 있게 된다.
- 제출 버튼은 `type="submit"`을 지정해서, 클릭 처리를 버튼이 아니라 `form`의 `onSubmit` 핸들러에 위임한다.
- 폼 제출 핸들러에서 `event.preventDefault()`를 호출하면, 브라우저가 페이지를 새로고침하는 HTML 폼의 기본 동작을 막을 수 있다.
- 폼을 구성하는 입력 필드와 버튼을 `SearchForm`처럼 별도 컴포넌트로 추출하면, `App` 컴포넌트는 상태 관리에만 집중하고 렌더링 구조는 분리해서 관리할 수 있다.

## Q&A

- **Q. 리액트에서 폼 입력은 어떻게 다루는가?**
  - A. 리액트에서 폼 입력은 보통 상태로 관리한다. 각 입력 필드마다 대응하는 상태 변수가 있고, 입력값은 그 상태로 설정된다.
- **Q. 리액트 폼에서 `onChange` 이벤트의 목적은 무엇인가?**
  - A. `onChange` 이벤트는 실시간으로 사용자 입력을 캡처해서 그에 맞게 상태를 갱신하며, 폼이 최신 입력값을 반영하도록 보장한다.
- **Q. 리액트에서 폼의 기본 제출 동작을 어떻게 막는가?**
  - A. 폼의 제출 핸들러 안에서 `e.preventDefault()` 메서드를 사용해서 폼의 기본 제출 동작을 막는다.
- **Q. 리액트에서 제어(controlled) 폼 입력과 비제어(uncontrolled) 폼 입력이란 무엇인가?**
  - A. 제어 폼 입력은 리액트 상태가 입력값을 관리하는 방식이고, 비제어 입력은 DOM이 입력을 처리하며 리액트가 그 상태를 추적하지 않는 방식이다.
- **Q. 리액트에서 폼 유효성 검사는 어떻게 수행하는가?**
  - A. 리액트에서 폼 유효성 검사는 보통 입력값을 특정 조건과 비교하거나 유효성 검사 라이브러리를 사용해서 수행한다. `onSubmit` 핸들러가 유효성 검사를 구현하는 흔한 위치다.
- **Q. 폼 입력의 `value` 속성은 어떤 목적을 가지는가?**
  - A. `value` 속성은 폼 입력의 초기값을 설정하고, 그 입력이 리액트 상태에 의해 제어되도록 보장한다.
- **Q. 리액트에서 하나의 `onChange` 핸들러로 여러 폼 입력을 어떻게 다루는가?**
  - A. 각 입력 필드에 `name` 속성을 사용하고, `onChange` 핸들러 안에서 `event.target.name`으로 해당 값을 접근한다.
- **Q. 리액트 폼에서 `onSubmit` 이벤트의 역할은 무엇인가?**
  - A. `onSubmit` 이벤트는 폼이 제출될 때 트리거된다. 이곳에서 폼 유효성 검사, 데이터 처리, 폼 제출과 관련된 그 밖의 작업을 처리한다.
