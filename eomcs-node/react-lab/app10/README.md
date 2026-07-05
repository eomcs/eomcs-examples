# app10. 리액트 Props

## 개요

지금까지는 `list`를 프로젝트의 **전역 변수**로 사용해왔다. 처음에는 `App` 컴포넌트에서, 이후에는 `List` 컴포넌트에서 전역 스코프의 값을 그대로 가져다 썼다. 전역 변수 하나, 파일 하나에 모든 컴포넌트가 모여 있을 때는 이 방식이 통하지만, 여러 폴더/파일에 걸쳐 여러 컴포넌트와 여러 변수를 다뤄야 하는 실제 프로젝트에서는 유지보수가 어렵다. 이럴 때 **Props**를 사용하면, 서로 다른 파일에 있는 컴포넌트 사이에도 변수를 정보로 전달할 수 있다.

> Java에 비유하면, Props는 메서드(생성자)의 **매개변수(parameter)**와 비슷하다. 부모 컴포넌트가 자식 컴포넌트를 "호출"할 때 값을 인자로 넘겨주는 것이다. 다만 리액트의 Props는 **불변(immutable)**이라서, 자식 쪽에서 전달받은 값을 직접 변경할 수 없다는 제약이 있다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const list = [
  {
    title: 'React',
    url: 'https://react.dev/',
    author: 'Jordan Walke',
    num_comments: 3,
    points: 4,
    objectID: 0,
  },
  {
    title: 'Redux',
    url: 'https://redux.js.org/',
    author: 'Dan Abramov, Andrew Clark',
    num_comments: 2,
    points: 5,
    objectID: 1,
  },
];

const App = () => (
  <div>
    <h1>My Hacker Stories</h1>

    <Search />

    <hr />

    <List />
  </div>
);

const Search = () => {
  const handleChange = (event) => {
    console.log(event.target.value);
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />
    </div>
  );
};

const List = () => (
  <ul>
    {list.map((item) => (
      <li key={item.objectID}>
        <span>
          <a href={item.url}>{item.title}</a>
        </span>
        <span>{item.author}</span>
        <span>{item.num_comments}</span>
        <span>{item.points}</span>
      </li>
    ))}
  </ul>
);

export default App;
```

## 실습

### 실습 1. `list`를 `App` 컴포넌트 안으로 옮기고 이름 바꾸기

Props를 사용해보기 전에, 전역 스코프에 있던 `list`를 `App` 컴포넌트 **내부**로 옮기고, 더 명확한 이름인 `stories`로 바꾼다. `return`문 앞에 변수를 선언할 수 있도록 `App` 컴포넌트를 간결한 바디에서 블록 바디로 되돌리는 것도 잊지 말자.

```jsx
const App = () => {
  const stories = [
    {
      title: 'React',
      url: 'https://react.dev/',
      author: 'Jordan Walke',
      num_comments: 3,
      points: 4,
      objectID: 0,
    },
    {
      title: 'Redux',
      url: 'https://redux.js.org/',
      author: 'Dan Abramov, Andrew Clark',
      num_comments: 2,
      points: 5,
      objectID: 1,
    },
  ];

  return ( /* ... */ );
};
```

### 실습 2. `List` 컴포넌트에 Props로 데이터 전달하기

이제 Props를 사용해서 아이템 리스트를 `List` 컴포넌트에 전달한다. `App` 컴포넌트에서는 이 변수를 `stories`라는 이름으로 갖고 있지만, `List` 컴포넌트를 인스턴스화할 때는 `list`라는 새로운 HTML 속성 이름으로 전달한다.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  return (
    <div>
      {/* ... */}

      <List list={stories} />
    </div>
  );
};
```

### 실습 3. `List` 컴포넌트에서 Props 받기

이제 `List` 컴포넌트의 함수 시그니처에 매개변수를 추가해서 전달받은 리스트를 꺼내보자.

```jsx
const List = (props) => (
  <ul>
    {props.list.map((item) => (
      <li key={item.objectID}>
        {/* ... */}
      </li>
    ))}
  </ul>
);
```

부모 컴포넌트가 컴포넌트 엘리먼트의 HTML 속성을 통해 자식 컴포넌트로 전달한 모든 것은 자식 컴포넌트 안에서 접근할 수 있다. 자식 컴포넌트는 함수 시그니처에서 `props`라는 하나의 객체를 매개변수로 받는데, 이 객체는 전달받은 모든 속성을 프로퍼티(짧게 줄여서 **props**)로 담고 있다.

> **ESLint 참고**: "`'list' is missing in props validation`" 같은 에러를 볼 수도 있다. 순수 자바스크립트(TypeScript 없이) 환경에서는 `prop-types` 라이브러리로 컴포넌트가 어떤 props를 받는지 명시할 수 있지만, TypeScript만큼 견고하지는 않다. TypeScript를 쓰지 않는다면 아래처럼 이 ESLint 규칙을 꺼두는 방법도 있다.
>
> ```js
> // eslint.config.js
> rules: {
>   // ...
>   'react/prop-types': 'off',
> },
> ```

### 실습 4. `Item` 컴포넌트 추출하고 Props로 개별 아이템 전달하기

이전(app05)에는 `List`에서 `Item` 컴포넌트를 추출하고 싶어도, 개별 아이템을 어떻게 전달해야 할지 몰라 미뤄뒀었다. 이제 Props를 알았으니, `List`에서 `Item` 컴포넌트를 추출하고 `map()`의 콜백 함수 안에서 각 아이템을 `Item` 컴포넌트로 전달해보자.

```jsx
const List = (props) => (
  <ul>
    {props.list.map((item) => (
      <Item key={item.objectID} item={item} />
    ))}
  </ul>
);

const Item = (props) => (
  <li>
    <span>
      <a href={props.item.url}>{props.item.title}</a>
    </span>
    <span>{props.item.author}</span>
    <span>{props.item.num_comments}</span>
    <span>{props.item.points}</span>
  </li>
);
```

- `key` 속성을 잊지 말자. 
- 이전 실습에서 살펴본 것처럼 리스트를 렌더링할 때 `key`는 리액트가 각 아이템을 효율적으로 추적하고 갱신하는 데 핵심적인 역할을 한다. 
- 여기서는 `Item` 컴포넌트를 인스턴스화하는 `<Item />` 엘리먼트에 `key`를 지정한다.

## Props의 핵심 규칙

- **Props는 변경할 수 없다(불변, immutable).** 자식 컴포넌트 안에서 전달받은 props 값을 직접 바꿔서는 안 되며, props는 오직 정보를 아래로 전달하는 용도로만 사용한다.
- **Props는 항상 부모 → 자식 방향으로만 전달된다.** 자식에서 부모로는 props를 통해 정보를 직접 전달할 수 없다. (이 한계를 극복하는 방법은 이후 실습에서 다룬다.)
- 이 실습을 통해 리액트 컴포넌트 트리에서 정보를 위에서 아래로 전달하는 수단(Props)을 확보했다.

## 정리

| 위치 | 코드 | 의미 |
| --- | --- | --- |
| 부모(`App`) | `<List list={stories} />` | `list`라는 이름의 속성으로 `stories` 값을 전달 |
| 자식(`List`) | `(props) => ... props.list` | `props` 객체를 통해 전달받은 값에 접근 |
| 손자(`Item`) | `<Item item={item} />` → `props.item` | `List`가 `map()`으로 순회하며 각 아이템을 `Item`에 개별 전달 |

## Q&A

- **Q. 리액트에서 props란 무엇인가?**
  - A. props(properties의 줄임말)는 부모 컴포넌트에서 자식 컴포넌트로 데이터를 전달하는 메커니즘이다.
- **Q. JSX에서 컴포넌트에 props를 전달하려면 어떻게 하는가?**
  - A. 속성(attribute)으로 포함시킨다. 예: `<MyComponent prop1={value1} prop2={value2} />`
- **Q. 함수 컴포넌트에서 props는 어떻게 접근하는가?**
  - A. 함수의 매개변수로 접근한다. 예: `function MyComponent(props) {...}`
- **Q. 컴포넌트 내부에서 props 값을 수정할 수 있는가?**
  - A. 아니다. props는 불변(immutable)이며 읽기 전용으로 다뤄야 한다.
