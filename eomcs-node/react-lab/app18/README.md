# app18. 리액트 Fragment

## 개요

지금까지 만든 모든 리액트 컴포넌트는 **하나의 최상위(top-level) HTML 엘리먼트**를 반환하고 있다. 이전에 `Search` 컴포넌트를 만들 때, `label`과 `input`을 나란히 반환하려면 이 둘을 감싸는 최상위 엘리먼트가 필요했기 때문에 `<div>` 태그(컨테이너 엘리먼트)를 추가해야 했다.

```jsx
// src/App.jsx
const Search = ({ search, onSearch }) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </div>
);
```

이번 실습에서는 최상위 엘리먼트를 추가하지 않고 여러 형제 엘리먼트를 나란히 반환하는 방법을 알아본다. 이때 사용하는 것이 바로 **리액트 Fragment**이다.

> Java에 비유하면, 메서드는 값을 하나만 반환할 수 있어서 여러 값을 묶어 반환하려면 별도의 래퍼 객체(record, DTO 등)가 필요한 것과 비슷하다. 리액트에서도 JSX는 결국 하나의 값을 반환해야 하는데, `Fragment`는 "진짜 DOM에는 남기지 않는" 가벼운 래퍼 역할을 한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 `Search` 컴포넌트에서 시작한다.

```jsx
// src/App.jsx
const Search = ({ search, onSearch }) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </div>
);
```

## 실습

### 실습 1. (드물게 쓰이는 방법) 배열로 형제 엘리먼트 반환하기

최상위 엘리먼트 없이 여러 형제 엘리먼트를 나란히 렌더링하는 방법이 아예 없는 것은 아니다. 잘 쓰이지는 않지만, 모든 형제 엘리먼트를 **배열**로 반환하는 방법이 있다. 이 경우 리스트를 렌더링하는 것과 비슷하므로, 각 아이템마다 필수적으로 `key` 속성을 지정해야 한다.

```jsx
const Search = ({ search, onSearch }) => [
  <label key="1" htmlFor="search">
    Search:{' '}
  </label>,
  <input
    key="2"
    id="search"
    type="text"
    value={search}
    onChange={onSearch}
  />,
];
```

이 방식은 가독성이 떨어지고, `key` 속성까지 일일이 추가해야 해서 코드가 장황해진다.

### 실습 2. `React.Fragment`로 감싸기

다행히 최상위 엘리먼트 없이 형제 엘리먼트를 나란히 반환하는 더 나은 방법이 있다. 바로 **리액트 Fragment**를 사용하는 것이다.

```jsx
const Search = ({ search, onSearch }) => (
  <React.Fragment>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </React.Fragment>
);
```

`Fragment`는 형제 엘리먼트들을 하나의 최상위 엘리먼트로 감싸주지만, **실제로 렌더링되는 결과물(HTML)에는 추가되지 않는다.** 브라우저 개발자 도구에서 엘리먼트를 확인해보면 `Fragment` 자체는 DOM에 흔적을 남기지 않는다는 것을 직접 확인할 수 있다.

### 실습 3. 축약형 문법 `<> ... </>` 사용하기

요즘 더 널리 쓰이는 방식은 `Fragment`의 **축약형(shorthand)** 문법이다.

```jsx
const Search = ({ search, onSearch }) => (
  <>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </>
);
```

`Search` 컴포넌트의 두 엘리먼트(입력 필드와 레이블)는 여전히 브라우저에 정상적으로 보인다. 결국, 리액트를 만족시키기 위해서만 존재하는 불필요한 중간 엘리먼트를 넣고 싶지 않을 때는 `Fragment`를 도우미 "엘리먼트"로 사용하면 된다.

## 정리

| 방법 | 특징 |
| --- | --- |
| `<div>` 등으로 감싸기 | 가장 쉽지만, 실제 DOM에 불필요한 엘리먼트가 추가됨 |
| 배열로 반환 | 최상위 엘리먼트는 없지만, 각 아이템에 `key`가 필요해서 장황함 |
| `<React.Fragment>...</React.Fragment>` | 최상위 엘리먼트를 감싸지만 DOM에는 남지 않음 |
| `<>...</>` (축약형) | `Fragment`와 동일하지만 더 간결한 문법 (가장 널리 쓰임) |

## Q&A

- **Q. 리액트에서 Fragment란 무엇인가?**
  - A. 추가적인 DOM 엘리먼트를 만들지 않고 여러 리액트 엘리먼트를 그룹으로 묶는 방법이다.
- **Q. JSX에서 Fragment는 어떻게 사용하는가?**
  - A. `<React.Fragment>`로 감싸거나, 축약형 문법인 `<>...</>`를 사용한다.
- **Q. 리액트에서 Fragment는 왜 사용하는가?**
  - A. 부모 래퍼 엘리먼트를 원하지 않을 때, DOM에 추가 노드를 만들지 않고 엘리먼트를 그룹화할 수 있게 해준다.
- **Q. Fragment도 `key`를 가질 수 있는가?**
  - A. 가능하다. 리스트를 순회하며 Fragment를 매핑할 때 `key`를 지정할 수 있다.
- **Q. 모든 리액트 컴포넌트에서 Fragment가 반드시 필요한가?**
  - A. 아니다. Fragment는 선택 사항이며, 컴포넌트가 부모 래퍼 없이 여러 엘리먼트를 반환해야 할 때 주로 사용한다.
- **Q. Fragment는 렌더링되는 HTML 구조에 영향을 주는가?**
  - A. 아니다. Fragment는 HTML 구조에 어떤 추가 노드도 만들지 않는다.
- **Q. Fragment에 `class`나 `style` 같은 속성을 지정할 수 있는가?**
  - A. 아니다. Fragment 자체는 속성을 가질 수 없다. 속성은 Fragment 안의 개별 엘리먼트에 지정해야 한다.
- **Q. Fragment를 사용하는 것과 `div` 컨테이너를 사용하는 것은 어떤 차이가 있는가?**
  - A. Fragment는 추가적인 DOM 노드를 만들지 않아서, `div` 컨테이너를 사용할 때보다 더 깔끔한 HTML 구조를 만들어준다.
