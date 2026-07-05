# app14. 리액트 제어 컴포넌트

## 개요

HTML 엘리먼트는 리액트와는 별개로 자기 자신의 **내부 상태(internal state)**를 갖고 있다. `Search` 컴포넌트의 `<input>` 엘리먼트를 다시 살펴보면 이를 확인할 수 있다. `id`, `type` 같은 기본 속성과 `onChange` 핸들러는 지정했지만, 이 엘리먼트에게 **값(value)**이 무엇인지는 알려준 적이 없다. 그런데도 사용자가 타이핑하면 화면에는 정확한 값이 표시된다. 이는 `<input>` 엘리먼트가 리액트 상태와는 무관하게 **자기 스스로** 내부 값을 추적하고 있기 때문이다.

> Java 웹 개발에 비유하면, 지금 상태는 HTML `<input>`이 서버(React state)와 동기화되지 않은 채 브라우저 쪽에서만 값을 들고 있는 것과 비슷하다. JSP의 `<input value="${...}">` 처럼 서버 쪽 값을 명시적으로 넣어주지 않으면, 폼과 서버 데이터가 어긋날 수 있는 것과 같은 문제다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState('');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search onSearch={handleSearch} />

      <hr />

      <List list={searchedStories} />
    </div>
  );
};

const Search = (props) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input id="search" type="text" onChange={props.onSearch} />
  </div>
);
```

## 실습

### 실습 1. 문제 재현하기: 초기 상태를 `'React'`로 바꿔보기

`App` 컴포넌트에서 `searchTerm`의 초기 상태를 빈 문자열 대신 `'React'`로 바꿔보자.

```jsx
const [searchTerm, setSearchTerm] = React.useState('React');
```

브라우저에서 애플리케이션을 열어보자. 무엇이 이상한지 찾을 수 있는가? `stories`는 새로운 초기 `searchTerm`에 맞춰 이미 필터링되어 표시되지만, 정작 HTML 입력 필드에는 그 값('React')이 보이지 않는다. 입력 필드에 직접 타이핑을 시작해야만 비로소 변경된 값이 반영되기 시작한다.

**왜 이런 문제가 생길까?** 입력 필드는 리액트의 상태(`searchTerm`)에 대해 아무것도 모른다. 오직 `onChange` 핸들러를 통해 자신의 내부 상태를 리액트 상태로 "통보"할 뿐이다. 사용자가 입력 필드에 타이핑을 시작하는 순간부터는 HTML 엘리먼트 스스로 변경 사항을 추적한다. 하지만 처음부터 제대로 만들려면, HTML도 리액트의 상태를 알고 있어야 한다.

### 실습 2. `value` 속성으로 리액트 상태와 동기화하기

`<input>` 엘리먼트에 현재 상태를 `value` 속성으로 전달해서, 두 상태를 동기화한다.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState('React');

  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search search={searchTerm} onSearch={handleSearch} />

      {/* ... */}
    </div>
  );
};

const Search = (props) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={props.search}
      onChange={props.onSearch}
    />
  </div>
);
```

이제 두 상태가 동기화됐다. HTML 엘리먼트가 자유롭게 자기 내부 상태를 관리하도록 두는 대신, `value` 속성을 통해 **리액트의 상태를 그대로 사용**하도록 만든 것이다. 흐름은 다음과 같다.

1. HTML 엘리먼트에서 `change` 이벤트가 발생한다.
2. 새로운 값이 리액트 상태(`searchTerm`)에 기록된다.
3. 리액트 상태가 바뀌었으므로 컴포넌트가 리렌더링된다.
4. HTML 엘리먼트는 다시 최신 상태를 `value`로 사용한다.

## 제어 컴포넌트(Controlled Component)란?

이전에는 HTML 엘리먼트가 자기 내부 상태를 알아서 관리했지만, 이제는 리액트의 상태를 이 엘리먼트에 명시적으로 흘려보내서 우리가 그 값을 **제어(control)** 하게 되었다. 그 결과:

- `<input>` 엘리먼트는 명시적으로 **제어되는 엘리먼트(controlled element)** 가 되었다.
- `Search` 컴포넌트는 암묵적으로 **제어 컴포넌트(controlled component)** 가 되었다.

리액트를 처음 배우는 단계에서는 제어 컴포넌트를 사용하는 것이 중요하다. 동작을 예측 가능하게 만들어주기 때문이다. (다만 나중에는 비제어 컴포넌트(uncontrolled component)가 필요한 경우도 있을 수 있다.)

## 정리

- HTML 폼 엘리먼트는 기본적으로 자기만의 내부 상태를 가지며, 리액트 상태와 자동으로 동기화되지 않는다.
- `value`(또는 체크박스의 `checked`) 속성에 리액트 상태를 연결하면, 그 엘리먼트를 리액트 상태로 완전히 제어할 수 있다.
- 이렇게 리액트 상태로 폼 엘리먼트를 제어하는 컴포넌트를 **제어 컴포넌트(controlled component)** 라고 부른다.

## Q&A

- **Q. 리액트에서 제어 컴포넌트(controlled component)란 무엇인가?**
  - A. 폼 엘리먼트의 값이 리액트 state에 의해 제어되는 컴포넌트를 말한다.
- **Q. 리액트에서 제어되는 input은 어떻게 만드는가?**
  - A. `input`의 `value` 속성을 state 변수에 연결하고, `onChange` 핸들러로 그 state를 갱신한다.
- **Q. 제어되는 input 엘리먼트에서 `value` prop의 역할은 무엇인가?**
  - A. `value` prop이 입력 필드의 현재 값을 설정하며, 이로써 그 엘리먼트가 제어 컴포넌트가 된다.
- **Q. 리액트에서 체크박스(checkbox)를 제어하려면 어떻게 하는가?**
  - A. `checked` 속성을 사용하고, 대응하는 state를 갱신하는 `onChange` 핸들러를 제공한다.
- **Q. 제어 컴포넌트의 값을 비우려면 어떻게 하는가?**
  - A. 해당 state 변수를 빈 값(또는 `null`)으로 설정하면 제어 컴포넌트의 값이 비워진다.
- **Q. 제어 컴포넌트를 사용할 때의 잠재적인 단점은 무엇인가?**
  - A. 입력 필드가 많은 폼에서는 코드가 장황해질 수 있다.
