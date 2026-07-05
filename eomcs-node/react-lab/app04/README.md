# app04. 리액트의 리스트

## 개요

자바스크립트에서 데이터를 다룰 때는 대부분 **객체 배열(array of objects)** 형태로 다루게 된다. 이번 실습에서는 리액트에서 리스트를 렌더링하는 방법을 배운다. 그 전에 자바스크립트에서 가장 많이 쓰이는 데이터 조작 메서드 중 하나인 배열의 내장 메서드 **`map()`**을 먼저 복습한다. `map()`은 리스트의 각 아이템을 순회하면서, 각 아이템의 새로운 버전을 반환하는 데 사용한다.

> Java에 비유하면, `map()`은 `Stream.map()`과 동일한 역할을 한다. 다만 리액트에서는 `map()`이 반환하는 결과가 새로운 값이 아니라 **JSX(화면에 그려질 조각)** 라는 점이 다르다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
function getTitle(title) {
  return title;
}

function App() {
  return (
    <div>
      <h1>Hello {getTitle('React')}</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
}

export default App;
```

## 실습

### 실습 1. `map()` 복습하기

리액트 코드를 작성하기 전에, 순수 자바스크립트에서 `map()`이 어떻게 동작하는지 먼저 확인해보자.

```js
const numbers = [1, 2, 3, 4];

const exponentialNumbers = numbers.map(function (number) {
  return number * number;
});

console.log(exponentialNumbers);
// [1, 4, 9, 16]
```

리액트에서도 배열의 `map()` 메서드를 그대로 사용하는데, 다만 아이템마다 새로운 값 대신 **JSX**를 반환해서 리스트를 화면에 그린다.

### 실습 2. 객체 배열을 컴포넌트 밖에 정의하기

렌더링할 데이터를 컴포넌트 **바깥**에 배열로 정의한다. (컴포넌트 내부 값에 의존하지 않는 데이터이므로 이전 실습에서 정리한 기준대로 바깥에 둔다.)

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
  // ...
}

export default App;
```

- 각 아이템은 `title`, `url`, `author`, 식별자 역할을 하는 `objectID`, 인기도를 나타내는 `points`, 댓글 수를 나타내는 `num_comments`를 갖는다.
- `num_comments`처럼 언더스코어를 쓰는 이름은 자바스크립트의 일반적인 네이밍 컨벤션과는 맞지 않지만, 실제 REST API의 데이터 형태를 그대로 반영한 것이다.

### 실습 3. 리스트를 JSX로 렌더링하기

이제 `list` 배열을 JSX 안에서 `map()`으로 순회하며 렌더링한다. 여기서는 하나의 자바스크립트 타입을 다른 타입으로 매핑하는 것이 아니라, 각 아이템에 대응하는 **JSX를 반환**한다는 점이 핵심이다.

```jsx
function App() {
  return (
    <div>
      <h1>My Hacker Stories</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />

      <hr />

      <ul>
        {list.map(function (item) {
          return <li>{item.title}</li>;
        })}
      </ul>
    </div>
  );
}
```

- 별도의 템플릿 문법 없이, 순수 자바스크립트만으로 객체 배열을 HTML 리스트로 바꿀 수 있다는 것이 JSX의 강력한 지점이다. 
- 결국 JSX는 개발자에게 있어 "HTML과 뒤섞인 자바스크립트"일 뿐이다.

### 실습 4. `key` 속성 추가하기

위 코드를 실행하면 브라우저 개발자 도구의 콘솔 탭에 "리스트의 각 리액트 엘리먼트에는 `key`가 있어야 한다"는 경고가 뜬다. `key`는 HTML 속성이며, **안정적인 식별자(stable identifier)**여야 한다. list에 각 아이템에 있는 `objectID`를 사용하여 key를 지정해 보자.

```jsx
function App() {
  return (
    <div>
      {/* ... */}

      <ul>
        {list.map(function (item) {
          return <li key={item.objectID}>{item.title}</li>;
        })}
      </ul>
    </div>
  );
}
```

**`key`가 필요한 이유:**

- 리액트가 리스트를 리렌더링할 때, 어떤 아이템이 바뀌었는지 확인해야 한다.
- `key`를 사용하면 바뀐 아이템만 효율적으로 교체할 수 있다.
- `key`가 없으면 리스트 맨 앞에 아이템이 하나 추가되는 것처럼 단순한 변경에도 리액트가 비효율적으로 전체 리스트를 업데이트할 수 있다.

**적절한 `key`가 없을 때:**

데이터에 고유한 `id`가 없다면 다른 식별자(예: 값이 바뀌지 않고 배열 내에서 유일한 `title`)를 사용할 수 있다. 최후의 수단으로는 배열의 인덱스를 `key`로 사용할 수도 있다.

```jsx
<ul>
  {list.map(function (item, index) {
    return (
      <li key={index}>
        {/* 인덱스는 정말 최후의 수단으로만 사용한다 */}
        {/* 참고로 JSX 주석은 이렇게 작성한다 */}

        {item.title}
      </li>
    );
  })}
</ul>
```

- **인덱스를 `key`로 쓰는 것은 되도록 피해야 한다.**
- 아이템의 순서가 바뀌는 경우(재정렬, 추가, 삭제 등) 실제 UI 버그로 이어질 수 있다. 
- 다만 리스트의 순서가 절대 바뀌지 않는다면 최후의 수단으로 인덱스를 사용해도 괜찮다.

### 실습 5. 나머지 속성도 함께 렌더링하기

지금까지는 `title`만 표시했다. 이번에는 `url`, `author`, `num_comments`, `points`도 함께 렌더링해보자. `url`은 `title`을 감싸는 HTML 앵커(`<a>`) 태그에 사용한다.

```jsx
function App() {
  return (
    <div>
      {/* ... */}

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
```

- `map()`은 JSX 안에 인라인으로 간결하게 작성할 수 있다. 
- `map()` 내부에서는 각 객체와 그 프로퍼티에 접근할 수 있으며, 아이템의 `url` 프로퍼티는 앵커 태그의 `href` 속성 값으로 사용된다. 
- JSX 안의 자바스크립트는 엘리먼트를 표시하는 것뿐만 아니라 **HTML 속성 값을 동적으로 지정하는 데도** 사용할 수 있다.

## 정리

- 리액트에서 리스트는 배열의 `map()` 메서드로 각 아이템을 JSX로 변환해서 렌더링한다.
- 리스트의 각 아이템에는 안정적인 식별자를 `key` 속성으로 지정해야 한다.
- `key`는 되도록 데이터의 고유 id를 사용하고, 인덱스는 최후의 수단으로만 사용한다.
- `map()` 안에서 프로퍼티 값을 JSX 콘텐츠뿐 아니라 HTML 속성 값으로도 사용할 수 있다.

## Q&A

- **Q. JSX에서 아이템 리스트는 어떻게 렌더링하는가?**
  - A. 배열의 `map()` 메서드로 순회하면서, 각 아이템에 대해 JSX 엘리먼트를 반환한다.
- **Q. JSX 대신 `null`을 반환하면 어떻게 되는가?**
  - A. JSX에서 `null`을 반환하는 것은 허용된다. 아무것도 렌더링하고 싶지 않을 때 사용한다.
- **Q. "JSX 표현식(JSX expressions)"이란 무엇인가?**
  - A. JSX 안의 중괄호 `{ }`에 담긴 자바스크립트 표현식을 말하며, 동적인 콘텐츠를 가능하게 한다.
- **Q. JSX 안에 HTML을 직접 삽입할 수 있는가?**
  - A. 가능은 하지만 보안 위험(XSS 등) 때문에 일반적으로 권장하지 않는다. 꼭 필요하다면 `dangerouslySetInnerHTML`을 신중하게 사용해야 한다.
- **Q. JSX에서 주석은 어떻게 작성하는가?**
  - A. 중괄호로 감싼 자바스크립트 주석 형태, 즉 `{/* 주석 내용 */}`으로 작성한다.
