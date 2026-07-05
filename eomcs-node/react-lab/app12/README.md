# app12. JSX의 콜백 핸들러

## 개요

Props는 오직 부모에서 **자식 컴포넌트에게만** 정보를 전달할 수 있고, State는 시간에 따라 변하는 컴포넌트의 상태 정보를 다룬다. 만약 자식 컴포넌트의 상태를 부모에게 전달해야만 한다면 어떻게 해야 할까? 

이번 실습에서는 **콜백 핸들러(callback handler)** 를 사용해서, 자식 컴포넌트의 상태를 부모 컴포넌트로 전달하는 방법을 배운다. 예를 들어, `Search` 컴포넌트에서 사용자가 입력 필드에 타이핑할 때마다, 그 이벤트를 부모 컴포넌트인 `App`으로 전달해본다.

> Java에 비유하면, 콜백 핸들러는 옵저버 패턴(Observer Pattern) 또는 리스너(Listener)를 등록하는 것과 비슷하다. 부모가 "이 이벤트가 발생하면 이 메서드를 호출해줘"라는 콜백을 자식에게 넘겨주고, 자식은 이벤트가 발생했을 때 그 콜백을 실행해서 부모에게 알려준다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
import * as React from 'react';

const App = () => {
  const stories = [ /* ... */ ];

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search />

      <hr />

      <List list={stories} />
    </div>
  );
};

const Search = () => {
  const [searchTerm, setSearchTerm] = React.useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />

      <p>
        Searching for <strong>{searchTerm}</strong>.
      </p>
    </div>
  );
};
```

## 콜백 핸들러의 원리

컴포넌트 트리 위쪽으로는 정보를 직접 전달할 방법이 없다. Props는 원래 아래 방향으로만 흐르기 때문이다. 하지만 **콜백 핸들러(callback handler)**를 사용하면 이 문제를 해결할 수 있다. 콜백 핸들러는 다음 네 단계로 동작한다.

- **(A)** 부모 컴포넌트에서 이벤트 핸들러로 함수를 정의한다.
- **(B)** 그 함수를 Props를 통해 자식 컴포넌트로 전달한다.
- **(C)** 자식 컴포넌트에서 그 함수를 핸들러로 실행한다.
- **(D)** 실행된 함수는 원래 정의됐던 곳(부모)으로 "다시 호출(callback)"된다.

## 실습

### 실습 1. `App`에서 콜백 핸들러 정의하고 `Search`에 전달하기

`App` 컴포넌트에 `handleSearch` 함수를 정의(A)하고, 이를 `Search` 컴포넌트의 `onSearch`라는 이름의 Props로 전달(B)한다.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  // (A) 이벤트 핸들러 정의
  const handleSearch = (event) => {
    // (D) 콜백 실행
    console.log(event.target.value);
  };

  return (
    <div>
      <h1>My Hacker Stories</h1>

      {/* (B) 자식 컴포넌트에게 콜백 핸들러 전달 */}
      <Search onSearch={handleSearch} />

      <hr />

      <List list={stories} />
    </div>
  );
};
```

### 실습 2. `Search`에서 콜백 핸들러 실행하기

`Search` 컴포넌트는 Props로 받은 `onSearch` 함수를, 자신의 `handleChange` 핸들러 안에서 실행(C)한다.

```jsx
const Search = (props) => {
  const [searchTerm, setSearchTerm] = React.useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);

    // (C) 콜백 핸들러 호출
    props.onSearch(event);
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />

      <p>
        Searching for <strong>{searchTerm}</strong>.
      </p>
    </div>
  );
};
```

- 이제 사용자가 입력 필드에 타이핑할 때마다, `App` 컴포넌트에서 `Search` 컴포넌트로 내려보냈던 함수가 실행된다. 
- 이렇게 해서 `Search` 컴포넌트에서 타이핑이 일어났다는 사실을 부모 컴포넌트 `App` 에게 알려줄 수 있게 됐다. 
- 콜백 핸들러는 사실 조금 더 구체화된 형태의 이벤트 핸들러일 뿐이며, 이것이 바로 컴포넌트 트리 위쪽으로 정보를 전달하는 암묵적인 수단으로 사용된다.

## 콜백 핸들러란?

> 부모 컴포넌트(예: `App`)에서 자식 컴포넌트(예: `Search`)로 Props를 통해 함수를 전달하고, 그 함수를 자식 컴포넌트에서 **호출**하지만, 실제 구현은 부모 컴포넌트에 있다.

다시 말해, 이벤트 핸들러가 부모 컴포넌트에서 Props로 자식 컴포넌트에 전달되면 그것이 바로 **콜백 핸들러**가 된다. 리액트의 Props는 언제나 컴포넌트 트리 아래로만 전달되기 때문에, Props로 전달된 콜백 핸들러 함수는 결과적으로 컴포넌트 트리 **위쪽**으로 정보를 전달하는 통로로 쓰일 수 있다.

## 정리

| 단계 | 위치 | 설명 |
| --- | --- | --- |
| A | `App` (부모) | 콜백으로 쓸 이벤트 핸들러(`handleSearch`)를 정의 |
| B | `App` → `Search` | Props(`onSearch`)로 함수를 자식에게 전달 |
| C | `Search` (자식) | 전달받은 함수(`props.onSearch`)를 자신의 핸들러 안에서 실행 |
| D | `App` (부모) | 콜백이 실행되며 부모 쪽 로직이 동작 |

- Props는 부모 → 자식 방향으로만 흐르지만, **함수를 Props로 전달**하면 자식에서 부모로 정보를 "역방향"으로 전달하는 효과를 낼 수 있다.
- 이런 함수를 **콜백 핸들러**라고 부르며, 이는 리액트에서 컴포넌트 간 통신을 위한 핵심적인 패턴이다.

## Q&A

- **Q. 리액트에서 콜백 핸들러란 무엇인가?**
  - A. 부모 컴포넌트가 자식 컴포넌트에 prop으로 전달하는 함수로, 자식이 부모와 소통할 수 있게 해준다.
- **Q. 자식 컴포넌트에 콜백 핸들러는 어떻게 전달하는가?**
  - A. prop으로 포함시킨다. 예: `<ChildComponent callback={handleCallback} />`
- **Q. 부모 컴포넌트에서 콜백 핸들러는 어떻게 정의하는가?**
  - A. 부모 컴포넌트 안에 함수를 만든다. 예: `function handleCallback(data) {...}`
- **Q. 콜백 핸들러는 매개변수를 받을 수 있는가?**
  - A. 가능하다. 자식 컴포넌트가 전달하는 인자를 받을 수 있다.
- **Q. 콜백 핸들러는 비동기(asynchronous)일 수 있는가?**
  - A. 가능하다. 비동기 작업을 처리하는 콜백 핸들러도 만들 수 있다.
- **Q. 콜백 핸들러를 여러 단계의 컴포넌트를 거쳐 전달할 수 있는가?**
  - A. 가능하다. 여러 계층의 컴포넌트를 통해 콜백 핸들러를 계속 전달할 수 있다.
- **Q. 하나의 자식 컴포넌트가 같은 부모로부터 여러 개의 콜백 핸들러를 받을 수 있는가?**
  - A. 가능하다. 같은 부모 컴포넌트로부터 여러 콜백 핸들러를 전달받아 사용할 수 있다.
- **Q. 리액트에서 폼 제출(form submission)에도 콜백 핸들러를 흔히 사용하는가?**
  - A. 그렇다. 폼 제출을 처리하고 부모 컴포넌트의 상태를 갱신할 때 콜백 핸들러가 흔히 사용된다.
