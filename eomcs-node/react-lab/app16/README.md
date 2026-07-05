# app16. 리액트 사이드 이펙트

## 개요

리액트 컴포넌트가 반환하는 결과물은 기본적으로 props와 state에 의해 결정된다. 그런데 **사이드 이펙트(side-effect)**도 이 결과물에 영향을 줄 수 있다. 사이드 이펙트는 브라우저의 `localStorage` API, 원격 데이터를 가져오는 API 같은 **서드파티 API**와 상호작용하거나, HTML 엘리먼트의 너비/높이 측정, 타이머/인터벌 같은 자바스크립트 내장 기능과 상호작용할 때 등장한다.

지금 애플리케이션은 검색어를 입력하면 결과가 바로 반영되지만, 브라우저를 닫았다가 다시 열면 검색어가 사라진다. `Search` 컴포넌트가 가장 최근 검색어를 기억해서, 애플리케이션이 재시작될 때도 그 값을 보여준다면 사용자 경험이 훨씬 좋아질 것이다.

> Java에 비유하면, 사이드 이펙트는 메서드 안에서 DB나 파일 시스템, 외부 API를 호출하는 것과 비슷하다. `useEffect`는 이런 부수 효과를 특정 값이 바뀔 때만 실행되도록 관리해주는 리액트만의 방식이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState('React');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <Search search={searchTerm} onSearch={handleSearch} />

      <hr />

      <List list={searchedStories} />
    </div>
  );
};
```

## 실습

### 실습 1. 검색어를 `localStorage`에 저장하기

사용자가 입력 필드에 타이핑할 때마다, 식별자(`'search'`)와 함께 `searchTerm`을 브라우저의 로컬 스토리지에 저장한다.

```jsx
const App = () => {
  // ...

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);

    localStorage.setItem('search', event.target.value);
  };

  // ...
};
```

### 실습 2. 저장된 값으로 초기 상태 설정하기

이번엔 저장된 값이 있으면 그 값을, 없으면 기존처럼 `'React'`를 초기 상태로 사용하도록 `useState`를 수정한다.

```jsx
const App = () => {
  // ...

  const [searchTerm, setSearchTerm] = React.useState(
    localStorage.getItem('search') || 'React'
  );

  // ...
};
```

**자바스크립트의 논리 OR 연산자(`||`)**: `localStorage.getItem('search')`가 참(truthy)이면 그 값을, 아니면 오른쪽 값을 반환하는 단축 평가(short-circuit)이다. 다음과 동일한 의미다.

```js
let hasStored;
if (localStorage.getItem('search')) {
  hasStored = true;
} else {
  hasStored = false;
}

const initialState = hasStored ? localStorage.getItem('search') : 'React';
```

- 이제 입력 필드를 사용하고 브라우저 탭을 새로고침하면, 가장 최근 검색어가 기억돼 있는 것을 확인할 수 있다. 
- 브라우저의 로컬 스토리지 값으로 상태를 초기화하고, 
- 핸들러가 호출될 때마다 새 값을 로컬 스토리지와 컴포넌트 상태 양쪽에 기록하여
- 두 저장소를 동기화 한다.

#### 문제점 확인하기: 핸들러에 숨어 있는 사이드 이펙트

기능은 완성됐지만, 장기적으로 버그를 낳을 수 있는 결함이 하나 있다. 핸들러 함수는 원래 상태를 갱신하는 데만 집중해야 하는데, 지금은 사이드 이펙트(로컬 스토리지 저장)까지 함께 떠안고 있다. **만약 애플리케이션 다른 곳에서 `setSearchTerm` 상태 갱신 함수를 호출한다면, 그 경로에서는 로컬 스토리지가 갱신되지 않기 때문에 기능이 깨진다.** 사이드 이펙트가 특정 이벤트 핸들러 하나에만 묶여 있기 때문이다.

### 실습 3. `useEffect`로 사이드 이펙트를 한 곳에 모으기

특정 핸들러가 아니라, **중앙화된 위치**에서 사이드 이펙트를 처리하도록 고쳐보자. `searchTerm`이 바뀔 때마다 원하는 사이드 이펙트를 실행하기 위해 리액트의 **`useEffect`** 훅을 사용한다.

```jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = React.useState(
    localStorage.getItem('search') || 'React'
  );

  React.useEffect(() => {
    localStorage.setItem('search', searchTerm);
  }, [searchTerm]);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  // ...
};
```

## `useEffect` 이해하기

`useEffect`는 두 개의 인자를 받는다.

- **첫 번째 인자**: 사이드 이펙트를 실행하는 함수. 여기서는 `searchTerm`을 브라우저 로컬 스토리지에 저장하는 역할을 한다.
- **두 번째 인자**: **의존성 배열(dependency array)**. 이 배열 안의 값 중 하나라도 바뀌면 사이드 이펙트 함수가 다시 실행된다. 여기서는 `searchTerm`이 바뀔 때마다(예: 사용자가 입력 필드에 타이핑할 때) 실행되며, 컴포넌트가 처음 렌더링될 때도 한 번 실행된다.

| 두 번째 인자(의존성 배열) | 동작 |
| --- | --- |
| 생략(없음) | 컴포넌트가 렌더링될 때마다(최초 렌더링 + 모든 업데이트) 매번 실행 |
| `[searchTerm]` | `searchTerm`이 바뀔 때(+ 최초 렌더링 시) 실행 |
| `[]` (빈 배열) | 컴포넌트가 최초로 렌더링될 때 **한 번만** 실행 |

결국 `useEffect` 훅은 컴포넌트가 **마운트(mount)**, **업데이트(update)**, **언마운트(unmount)** 되는 생명주기(lifecycle)에 관여할 수 있게 해준다. 컴포넌트가 처음 마운트될 때도, 이후 어떤 값(state, props, 또는 이들로부터 파생된 값)이 바뀌어 업데이트될 때도 트리거될 수 있다.

결론적으로, **사이드 이펙트를 특정 이벤트 핸들러 안에 두는 대신 `useEffect`로 관리함으로써 애플리케이션이 더 견고해졌다.** `setSearchTerm`으로 `searchTerm` 상태가 갱신되는 경로가 어디든, 브라우저의 로컬 스토리지는 항상 최신 상태와 동기화된다.

## 정리

- **사이드 이펙트**는 서드파티 API, 브라우저 API, 타이머 등 컴포넌트 바깥 세계와 상호작용하는 코드를 말한다.
- 이벤트 핸들러 안에 사이드 이펙트를 두면, 상태를 갱신하는 다른 경로에서 사이드 이펙트가 누락될 위험이 있다.
- `React.useEffect(effectFn, dependencyArray)`를 사용하면 특정 값이 바뀔 때만 사이드 이펙트를 한 곳에서 실행하도록 중앙화할 수 있다.
- 의존성 배열을 생략하면 매 렌더링마다, 빈 배열(`[]`)을 넘기면 최초 렌더링 시 한 번만 실행된다.

## Q&A

- **Q. 리액트에서 `useEffect`란 무엇인가?**
  - A. 함수 컴포넌트가 사이드 이펙트를 수행할 수 있게 해주는 리액트 훅이다.
- **Q. 하나의 컴포넌트에서 `useEffect`를 여러 번 사용할 수 있는가?**
  - A. 가능하다. 하나의 컴포넌트 안에서 여러 개의 `useEffect`를 사용할 수 있다.
- **Q. `useEffect`의 두 번째 인자는 무엇을 의미하는가?**
  - A. 의존성(dependency) 배열이다. 이 배열의 값 중 하나라도 바뀌면 effect가 실행된다.
- **Q. `useEffect`를 마운트 시 한 번만 실행하려면 어떻게 하는가?**
  - A. 두 번째 인자로 빈 배열(`[]`)을 전달한다.
- **Q. `useEffect`는 정리(cleanup) 함수를 반환할 수 있는가?**
  - A. 가능하다. `useEffect`에서 반환하는 함수가 정리 함수 역할을 한다.
- **Q. `useEffect`의 정리 함수는 어떤 목적을 가지는가?**
  - A. 컴포넌트가 언마운트되거나 의존성이 바뀔 때, 리소스를 정리(해제)하는 역할을 한다.
- **Q. 매 렌더링마다 `useEffect`에서 정리 작업을 수행하려면 어떻게 하는가?**
  - A. `useEffect` 내부에서 정리 로직을 담은 함수를 반환하면 된다.
- **Q. 특정 조건에 따라 `useEffect`를 조건부로 실행할 수 있는가?**
  - A. 가능하다. `useEffect` 내부에서 조건문을 사용해 실행 여부를 제어할 수 있다.
- **Q. `useEffect`의 두 번째 인자를 생략하면 어떻게 되는가?**
  - A. 매 렌더링마다 effect가 실행되어, 잠재적인 성능 문제로 이어질 수 있다.
- **Q. `useEffect`는 리액트에서 경쟁 조건(race condition)을 피하는 데 어떻게 도움이 되는가?**
  - A. 비동기 작업을 처리하고 실행 순서를 관리함으로써 경쟁 조건을 피할 수 있게 해준다.
