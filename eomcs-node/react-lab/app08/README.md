# app08. 리액트 컴포넌트 선언

## 개요

지금까지 여러 개의 리액트 컴포넌트를 선언해봤다. 이 컴포넌트들은 이른바 **함수 컴포넌트**이기 때문에, 자바스크립트에서 함수를 선언하는 다양한 방법을 그대로 활용할 수 있다. 지금까지는 일반적인 **함수 선언(function declaration)** 방식을 사용했지만, **화살표 함수(arrow function)**를 쓰면 더 간결하게 컴포넌트를 선언할 수 있다. 실제로 요즘 리액트 프로젝트에서는 화살표 함수 방식이 새로운 표준처럼 자리 잡고 있다.

```js
// 함수 선언(function declaration)
function App() { /* ... */ }

// 화살표 함수 표현식(arrow function expression)
const App = () => { /* ... */ };
```

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const list = [ /* ... */ ];

function App() {
  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search />

      <hr />

      <List />
    </div>
  );
}

function Search() {
  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
}

function List() {
  return (
    <ul>
      {list.map(function (item) {
        return (
          <li key={item.objectID}>
            <span>
              <a href={item.url}>{item.title}</a>
            </span>
            <span>{item.author}</span>
            <span>{item.num_comments}</span>
            <span>{item.points}</span>
          </li>
        );
      })}
    </ul>
  );
}

export default App;
```

## 실습

### 실습 1. 함수 컴포넌트를 화살표 함수로 리팩터링하기

프로젝트 전체를 돌아다니며 함수 선언을 화살표 함수 표현식으로 바꿔보자. 이 리팩터링은 함수 컴포넌트뿐 아니라 프로젝트에서 사용하는 다른 일반 함수에도 똑같이 적용할 수 있다.

```jsx
// src/App.jsx
const App = () => {
  return ( /* ... */ );
};

const Search = () => {
  return ( /* ... */ );
};

const List = () => {
  return ( /* ... */ );
};
```

함수 컴포넌트뿐만 아니라, `map()` 메서드에 넘기는 콜백 함수 같은 다른 함수도 화살표 함수로 바꿀 수 있다.

```jsx
const List = () => {
  return (
    <ul>
      {list.map((item) => {
        return (
          <li key={item.objectID}>
            {/* ... */}
          </li>
        );
      })}
    </ul>
  );
};
```

### 실습 2. 암묵적 반환(implicit return)으로 더 간결하게 만들기

화살표 함수가 그 안에서 별다른 로직 없이 값 하나만 반환한다면, 중괄호로 된 **블록 바디(block body)**를 생략할 수 있다. 이렇게 **간결한 바디(concise body)**로 작성하면 `return`문도 함께 생략된다(암묵적 반환).
- 
```js
// 블록 바디
const addOne = (count) => {
  // 중간에 다른 작업을 수행할 수 있다

  return count + 1;
};

// 간결한 바디 (여러 줄)
const addOne = (count) =>
  count + 1;

// 간결한 바디 (한 줄)
const addOne = (count) => count + 1;
```

`Search` 컴포넌트는 다른 로직 없이 JSX만 반환하므로, 다음처럼 간결한 바디로 바꿀 수 있다.

```jsx
const Search = () => (
  <div>
    <label htmlFor="search">Search: </label>
    <input id="search" type="text" />
  </div>
);
```

`App`, `List` 컴포넌트도 JSX만 반환하고 중간에 별도 작업이 없으므로 마찬가지로 바꿀 수 있다. `map()`에 넘기는 화살표 함수에도 똑같이 적용할 수 있다.

```jsx
const App = () => (
  <div>
    {/* ... */}
  </div>
);

const List = () => (
  <ul>
    {list.map((item) => (
      <li key={item.objectID}>
        {/* ... */}
      </li>
    ))}
  </ul>
);
```

`function` 키워드, 중괄호, `return`문이 모두 생략되면서 JSX가 훨씬 간결해졌다.

### 실습 3. 언제 블록 바디가 필요한가?

이번 리팩터링은 **선택 사항**이다. 함수 선언 대신 화살표 함수를 쓸지, 간결한 바디 대신 블록 바디를 쓸지는 자유롭게 선택할 수 있다. 다만 함수 시그니처와 `return`문 사이에 별도의 로직(비즈니스 로직)이 필요해지는 순간에는 블록 바디가 필요해진다.

```jsx
const App = () => {
  // 중간에 어떤 작업을 수행한다

  return (
    <div>
      {/* ... */}
    </div>
  );
};
```

## 정리

| 구분 | 문법 | 언제 쓰나 |
| --- | --- | --- |
| 함수 선언 | `function App() { ... }` | 전통적인 방식, 어느 쪽을 써도 무방 |
| 화살표 함수(블록 바디) | `const App = () => { ... }` | 함수 시그니처와 `return` 사이에 로직이 필요할 때 |
| 화살표 함수(간결한 바디) | `const App = () => ( ... )` | JSX만 그대로 반환하고 별도 로직이 없을 때 |

- 함수 선언과 화살표 함수 표현식 둘 다 사용해도 무방하지만, 같은 프로젝트/팀 안에서는 **일관된 스타일**을 유지하는 것이 중요하다.
- 암묵적 반환은 코드를 간결하게 만들어주지만, 나중에 로직을 추가해야 할 때 간결한 바디에서 블록 바디로 다시 리팩터링해야 하는 번거로움이 생길 수 있다. 그래서 처음부터 블록 바디를 가진 화살표 함수로 통일해두는 것을 선호하는 개발자도 많다.

## Q&A

- **Q. 함수 선언(function declaration)으로 함수 컴포넌트를 선언하려면 어떻게 하는가?**
  - A. `function` 키워드를 사용한다. 예: `function MyComponent() { ... }`
- **Q. 화살표 함수 표현식(arrow function expression)으로 함수 컴포넌트를 선언하려면 어떻게 하는가?**
  - A. 화살표 함수 문법을 사용한다. 예: `const MyComponent = () => { ... };`
