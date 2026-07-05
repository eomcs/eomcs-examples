# app05. 또 다른 리액트 컴포넌트

## 개요

컴포넌트는 모든 리액트 애플리케이션의 기초다. 프로젝트가 커질수록 관리해야 할 컴포넌트도 점점 늘어난다. 각 컴포넌트는 하나의 기능(예: 아이템 목록 렌더링)을 캡슐화한다. 지금까지는 `App` 컴포넌트 하나만 사용해왔는데, 이 방식은 애플리케이션이 커질수록 좋지 않다. 컴포넌트는 애플리케이션의 규모에 맞춰 함께 커져야 하기 때문이다. 그래서 이번 실습에서는 하나의 컴포넌트를 계속 크고 복잡하게 키우는 대신, 하나의 컴포넌트를 여러 컴포넌트로 **분리(추출, extract)**하는 방법을 배운다.

> Java에 비유하면, 커진 메서드를 더 작은 메서드로 리팩터링해서 뽑아내는 것("Extract Method")과 같은 감각이다. 다만 리액트에서는 그 결과물이 재사용 가능한 **UI 조각(컴포넌트)**이 된다는 점이 다르다.

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

function App() {
  return (
    <div>
      <h1>My Hacker Stories</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />

      <hr />

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
    </div>
  );
}

export default App;
```

## 실습

### 실습 1. `List` 컴포넌트 분리하기

`App` 컴포넌트에서 리스트를 렌더링하던 부분을 떼어내서 새로운 `List` 컴포넌트로 만든다.

```jsx
// src/App.jsx
const list = [ /* ... */ ];

function App() { /* ... */ }

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
```

이제 `App` 컴포넌트에서 인라인으로 작성했던 HTML 대신, 새로 만든 `List` 컴포넌트를 태그처럼 사용한다.

```jsx
function App() {
  return (
    <div>
      <h1>My Hacker Stories</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />

      <hr />

      <List />
    </div>
  );
}
```

- App 컴포넌트에서 목록 부분을 "컴포넌트 분리(추출)"를 했다. 
- 컴포넌트는 이렇게 의미 있는 작업 단위를 캡슐화하면서 더 큰 리액트 프로젝트 전체에 기여하는 방식으로 설계된다. 
- 컴포넌트는 시간이 지나면서 크기와 복잡도가 계속 커지기 때문에, **컴포넌트를 추출하는 작업은 리액트 개발자 자주 하는 일** 중 하나다.

### 실습 2. `Search` 컴포넌트 분리하기

이번 단계에서는 `App` 컴포넌트에 남아 있는 `label`, `input` 엘리먼트를 별도의 `Search` 컴포넌트로 추출한다.

```jsx
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
```

- 이제 애플리케이션에는 `App`, `List`, `Search` 세 개의 컴포넌트가 생겼다.

## 컴포넌트 계층 구조 (Component Tree)

리액트 애플리케이션은 일반적으로 여러 컴포넌트가 계층 구조를 이루며 구성된다. 이를 **컴포넌트 트리(component tree)**라고 부른다. `List` 컴포넌트에서 `Item` 컴포넌트를 한 단계 더 추출했다고 가정하면, 다음과 같은 용어로 계층을 설명할 수 있다.

- **루트 컴포넌트(root component)**: 트리 최상단의 진입점 컴포넌트. 보통 `App`이 여기에 해당한다.
- **부모(parent) / 자식(child) 컴포넌트**: `App`은 `List`와 `Search`의 부모 컴포넌트이고, `List`와 `Search`는 `App`의 자식 컴포넌트다.
- **형제(sibling) 컴포넌트**: `List`와 `Search`처럼 부모가 같은 컴포넌트끼리는 서로 형제 컴포넌트다.
- **리프(leaf) 컴포넌트**: 더 이상 다른 컴포넌트를 렌더링하지 않는 최말단 컴포넌트. 지금 코드에서는 `List`(또는 `Search`)가 리프에 해당하고, 이후 `List`에서 `Item`을 추출하면 `Item`이 리프가 된다.

```text
App (root)
├── Search (leaf)
└── List (parent of Item)
    └── Item (leaf)
```

모든 컴포넌트는 자식 컴포넌트를 0개, 1개, 또는 여러 개 가질 수 있다. 보통은 `App` 컴포넌트에서 시작해서 점점 컴포넌트 트리를 키워나간다. 미리 어떤 컴포넌트를 만들지 계획할 수도 있고, 하나의 컴포넌트에서 시작해서 점차 컴포넌트를 추출해나갈 수도 있다.

**언제 컴포넌트를 추출해야 할까?**

초보자에게는 언제 새 컴포넌트를 만들고 언제 기존 컴포넌트에서 추출해야 하는지 판단하기 어려울 수 있다. 보통은 다음과 같은 상황에서 자연스럽게 결정된다.

- 컴포넌트의 크기/복잡도가 너무 커졌을 때
- 도메인/기능상 자연스러운 경계가 보일 때 (예: `List`는 목록을 렌더링, `Search`는 검색 폼을 렌더링)

결국 각 컴포넌트는 애플리케이션 안에서 하나의 단위(unit)를 표현하며, 이는 애플리케이션을 더 유지보수하기 쉽고 예측 가능하게 만들어준다.

## 정리

- **리액트 애플리케이션은 하나의 큰 컴포넌트가 아니라, 여러 개의 작은 컴포넌트로 나눠서 구성한다.**
- 컴포넌트 추출은 자바의 메서드 추출 리팩터링과 비슷한 감각으로, 반복되는 코드/명확한 책임 단위를 별도 컴포넌트로 뽑아내는 작업이다.
- 컴포넌트들은 부모-자식-형제 관계를 가지는 계층 구조(컴포넌트 트리)를 이룬다.

## Q&A

- **Q. 리액트에서 컴포넌트를 추출하면 어떤 점이 좋은가?**
  - A. 재사용성, 유지보수성, 더 깔끔한 컴포넌트 구조를 얻을 수 있다.
- **Q. 언제 컴포넌트를 추출해야 하는지 어떻게 판단하는가?**
  - A. 코드 안에서 반복되는 UI 패턴이나 기능이 보일 때 컴포넌트로 추출한다.
- **Q. 리액트에서 컴포넌트를 추출하는 과정을 무엇이라고 부르는가?**
  - A. 리팩터링(refactoring)이라고 부르며, 구체적으로는 "컴포넌트 추출(extracting a component)"이라고 한다.
- **Q. 컴포넌트를 서로 다른 파일로도 추출할 수 있는가?**
  - A. 가능하다. 컴포넌트를 별도 파일로 분리하면 파일 구조와 모듈화 측면에서 더 좋다.
