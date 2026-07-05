# app13. 리액트에서 State 끌어올리기

## 개요

이번 실습에서는 **자식 컴포넌트 간에 데이터를 공유**하기 위해 **부모 컴포넌트가 상태값을 보유하고 자식 컴포넌트에서 그 상태값을 사용하는 기법**을 배운다. 이 기법을 **State 끌어올리기(lifting state)**라고 부른다. 

- `Search` 컴포넌트의 상태값 `searchTerm`을 `App` 컴포넌트으로 옮긴다.
- 검색어가 입력되면 `List` 컴포넌트가 `stories`의 `title`을 기준으로 출력을 필터링 할 수 있도록 Props로 `searchTerm`을 전달한다. 

> Java에 비유하면, 여러 형제 객체가 같은 필드를 참조해야 할 때 그 필드를 공통 부모(또는 상위 계층)로 옮기는 것과 같은 감각이다. 상태를 "끌어올려서(lift)" 그 상태가 필요한 모든 컴포넌트의 공통 조상에 둔다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  const stories = [ /* ... */ ];

  const handleSearch = (event) => {
    console.log(event.target.value);
  };

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search onSearch={handleSearch} />

      <hr />

      <List list={stories} />
    </div>
  );
};

const Search = (props) => {
  const [searchTerm, setSearchTerm] = React.useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
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

## 실습

### 실습 1. `useState`를 `Search`에서 `App`으로 옮기기 (State 끌어올리기)

`useState` 훅을 `Search` 컴포넌트에서 `App` 컴포넌트로 옮기고, 상태 갱신 함수를 콜백 핸들러 안에서 사용한다. `Search` 컴포넌트는 더 이상 state를 직접 관리하지 않고, 입력 필드에 값이 입력되면 이벤트를 콜백 핸들러를 통해 부모(`App`)로 전달하기만 한다.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState('');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search onSearch={handleSearch} />

      <hr />

      <List list={stories} />
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

- 자식 컴포넌트(`Search`)에서 부모 컴포넌트(`App`)로 상태값을 전달하기 위해 콜백 핸들러를 활용한다. 
- `Search` 컴포넌트에 텍스트가 입력되면 그 이벤트를 콜백 핸들러를 통해 `App`으로 전달한다.
- 단 상태 자체는 `App`이 갱신한다. 
- 이렇게 상태를 자식 컴포넌트에서 부모 컴포넌트로 옮기는 것을 **State 끌어올리기(lifting state)**라고 부른다.

### 실습 2. `filter()`로 검색어에 맞는 스토리만 걸러내기

`App` 컴포넌트가 검색 상태를 직접 관리하게 되었으니, 이제 `searchTerm`으로 `stories`를 필터링한 다음 `List` 컴포넌트에 `list` prop으로 전달할 수 있다. 배열의 내장 메서드 `filter()`를 사용해보자.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState('');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const searchedStories = stories.filter(function (story) {
    return story.title.includes(searchTerm);
  });

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search onSearch={handleSearch} />

      <hr />

      <List list={searchedStories} />
    </div>
  );
};
```

- `filter()`는 배열을 순회하며 각 아이템에 대해 `true`/`false`를 반환하는 함수를 인자로 받는다. 
- 함수가 `true`(조건을 만족)를 반환하면 그 아이템은 새로 만들어지는 배열에 남고, `false`를 반환하면 제외된다.

```js
const words = [
  'spray',
  'limit',
  'elite',
  'exuberant',
  'destruction',
  'present',
];

const filteredWords = words.filter(function (word) {
  return word.length > 6;
});

console.log(filteredWords);
// ["exuberant", "destruction", "present"]
```

### 실습 3. `filter()` 콜백을 화살표 함수로 간결하게 만들기

`filter()`에 넘기는 함수도 암묵적 반환을 가진 화살표 함수로 더 간결하게 만들 수 있다.

```jsx
const App = () => {
  // ...

  const searchedStories = stories.filter((story) =>
    story.title.includes(searchTerm)
  );

  // ...
};
```

- 가독성과 간결함 사이의 균형을 항상 완벽하게 맞추기는 어렵지만, 가능하면 간결하게 작성하는 편이 대체로 가독성도 함께 챙길 수 있다.

### 실습 4. 대소문자 구분 없이 검색되도록 고치기

지금 상태에서는 한 가지 문제가 있다. `filter()`는 `searchTerm`이 각 스토리의 `title`에 포함되는지 확인하지만, **대소문자를 구분**한다. 예를 들어 "react"로 검색하면 "React" 스토리가 걸러지지 않는다. `searchTerm`과 `title` 모두 소문자로 변환해서 비교하면 이 문제를 해결할 수 있다.

```jsx
const App = () => {
  // ...

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // ...
};
```

- 이제 "eact", "React", "react" 어떤 형태로 검색해도 관련 스토리가 정상적으로 걸러진다. 
- State(필터링된 리스트를 도출)와 Props(콜백 핸들러를 `Search`에 전달)를 모두 활용해서 인터랙티브 기능을 추가해 보았다.

## State는 어디에 두어야 할까?

리액트 개발자에게 **state를 어디에 인스턴스화할지 아는 것**은 커리어 내내 중요한 역량이다. 기본 원칙(rule of thumb)은 다음과 같다.

- state는 항상 **그 state에 관심 있는 모든 컴포넌트가 접근할 수 있는 위치**에 두어야 한다.
- state를 직접 사용하는 컴포넌트는 두 가지 중 하나다.
  - state를 **관리(소유)하는 컴포넌트** — state 값을 직접 사용 (예: `App`)
  - state를 관리하는 컴포넌트의 **하위 컴포넌트** — props를 통해 값을 사용 (예: `List`, `Search`)
- 하위 컴포넌트가 state를 **갱신**해야 한다면(예: `Search`), 그 컴포넌트에 **콜백 핸들러**를 내려보내서 상위 컴포넌트의 state를 갱신할 수 있게 한다.
- 하위 컴포넌트가 state를 단순히 **사용(표시)** 만 해야 한다면, props로 그 값을 내려보낸다.

state를 관리하는 컴포넌트를 기준으로, 그 state에 의존하는 모든 컴포넌트는 이 컴포넌트의 **하위(descendant) 컴포넌트**여야 한다.

## 정리

- 여러 컴포넌트가 같은 state에 의존한다면, 그 state는 관련 컴포넌트들의 **공통 조상**으로 끌어올려야 한다. 이를 **State 끌어올리기(lifting state)** 라고 한다.
- `Search`는 더 이상 state를 갖지 않는(stateless) 컴포넌트가 되었고, `App`이 state를 소유하고 필터링 로직까지 담당한다.
- `filter()`로 배열을 필터링할 때는 대소문자 구분 여부 같은 세부 조건까지 신경 써야 한다.

## Q&A

- **Q. 리액트에서 State 끌어올리기(lifting state)란 무엇인가?**
  - A. 자식 컴포넌트에 있던 state를 부모 컴포넌트로 옮기는 작업을 말한다.
- **Q. State를 왜 끌어올리는가?**
  - A. 더 상위 레벨에서 state를 공유하고 관리해서, 여러 자식 컴포넌트가 접근할 수 있게 하기 위해서다.
- **Q. 리액트에서 State는 어떻게 끌어올리는가?**
  - A. state와 관련 함수를 공통 조상(보통 부모) 컴포넌트로 옮긴다.
- **Q. 여러 자식 컴포넌트가 끌어올려진 같은 state를 공유할 수 있는가?**
  - A. 가능하다. State 끌어올리기를 통해 여러 자식 컴포넌트가 같은 state를 공유할 수 있다.
- **Q. 컴포넌트 안에서 로컬 state를 쓰는 것보다 State를 끌어올리는 것의 장점은 무엇인가?**
  - A. 여러 컴포넌트 사이에서 state를 공유하기 쉬워진다.
- **Q. State 끌어올리기에서 콜백은 어떤 역할을 하는가?**
  - A. State를 끌어올릴 때 자식에서 부모로 데이터를 전달하는 데 콜백 함수가 사용된다.
- **Q. 자식 컴포넌트가 콜백 핸들러를 통해 부모 컴포넌트의 state를 직접 바꿀 수 있는가?**
  - A. 아니다. 자식 컴포넌트는 콜백을 호출해서 부모에게 알릴 뿐이고, state를 어떻게 갱신할지는 부모가 결정한다.
- **Q. 모든 state를 반드시 최상위 부모 컴포넌트까지 끌어올려야 하는가?**
  - A. 아니다. 여러 컴포넌트 사이에서 공유가 필요한 수준까지만 끌어올리면 된다.
- **Q. State 끌어올리기는 컴포넌트 재사용성에 어떻게 기여하는가?**
  - A. 상태 관련 로직을 공통 조상 컴포넌트에 모아둠으로써, 개별 컴포넌트를 더 재사용하기 쉽게 만들어준다.
