# app27. 리액트로 데이터 가져오기

## 개요

지금까지 리액트에서 비동기 데이터 페칭을 위한 모든 준비를 마쳤다. 하지만 여전히 우리가 직접 만든 가짜 API용 `Promise`에서 오는 **가짜(pseudo) 데이터**를 사용하고 있다. 그럼에도, 지금까지 다룬 비동기 리액트와 심화된 상태 관리에 대한 모든 내용은 실제 원격 서드파티 API에서 데이터를 가져오기 위한 준비 과정이었다. 이번 실습에서는 인기 있는 기술 관련 게시물을 요청하기 위해 유용한 **해커 뉴스(Hacker News) API**를 사용해본다.

**과제**: 애플리케이션은 우리가 만든 `Promise`에서 오는 비동기적인 가짜 데이터를 사용하고 있다. `getAsyncStories()` 함수를 사용하는 대신, 해커 뉴스 API로 데이터를 가져오도록 바꿔보자.

**힌트**:

- 해커 뉴스 API의 `https://hn.algolia.com/api/v1/search?query=React` 엔드포인트를 사용한다.
- `initialStories` 변수는 이제 데이터가 API에서 오게 되므로 제거한다.
- 브라우저 내장 `fetch` API로 요청을 수행한다.
- 참고: 요청이 성공하든 실패하든, 이미 마련해둔 것과 같은 구현 로직을 그대로 사용할 수 있다.

우리는 이미 비동기 데이터를 가져오기 위한 훌륭한 토대를 갖춰뒀으므로, 모든 준비는 이미 끝나 있다. 유일하게 우리를 정답에서 떼어놓는 것은 실제 세계의 데이터 대신 샘플 데이터를 쓰고 있다는 점뿐이다.

> Java에 비유하면, `fetch(url).then(response => response.json())`은 `HttpClient`로 HTTP 요청을 보내고 응답 바디를 Jackson 같은 라이브러리로 역직렬화하는 것과 비슷하다. `.then()` 체이닝은 `CompletableFuture.thenApply()`를 연쇄적으로 호출하는 것에 해당한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const initialStories = [ /* ... */ ];

const getAsyncStories = () =>
  new Promise((resolve, reject) => setTimeout(reject, 2000));

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    { data: [], isLoading: false, isError: false }
  );

  React.useEffect(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    getAsyncStories()
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.data.stories,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, []);

  // ...
};
```

## 실습

### 실습 1. `API_ENDPOINT`와 `fetch`로 실제 데이터 가져오기

이제 원격 API에 연결하기 위해 바꿔야 할 부분을 모두 살펴보자. `initialStories` 배열과 `getAsyncStories` 함수는 이제 제거하고, API에서 직접 데이터를 가져온다.

```jsx
// src/App.jsx

// (A)
const API_ENDPOINT = 'https://hn.algolia.com/api/v1/search?query=';

const App = () => {
  // ...

  React.useEffect(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}react`) // (B)
      .then((response) => response.json()) // (C)
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits, // (D)
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, []);

  // ...
};
```

- **(A)** 먼저, `API_ENDPOINT`를 사용해서 특정 쿼리(검색어)에 대한 인기 있는 기술 게시물을 가져올 URL을 설정한다. 이 URL은 해커 뉴스 API의 엔드포인트를 가리킨다.
- **(B)** 이번 경우에는 React에 관한 게시물을 가져온다. 이 요청을 수행하기 위해 브라우저 내장 `fetch` API를 사용한다.
- **(C)** `fetch` API를 쓸 때는 응답을 JSON으로 변환해야 한다.
- **(D)** 마지막으로, 반환된 결과는 다른 데이터 구조를 가지고 있는데, 이를 컴포넌트의 상태 리듀서에 `payload`로 전달한다.

### 실습 2. 템플릿 리터럴로 문자열 조합하기

fetch() 함수를 호출할 때, URL(예:`` `${API_ENDPOINT}react` ``)은 자바스크립트의 **템플릿 리터럴(template literals)** 을 사용해서 만들었다. 이 기능이 없던 시절에는 문자열에 `+` 연산자를 사용해야 했을 것이다.

```js
const greeting = 'Hello';

// + 연산자
const welcome = greeting + ' React';
console.log(welcome);
// Hello React

// 템플릿 리터럴
const anotherWelcome = `${greeting} React`;
console.log(anotherWelcome);
// Hello React
```

브라우저를 확인해서, 해커 뉴스 API에서 가져온 초기 쿼리와 관련된 게시물들을 살펴보자. 샘플 story와 동일한 데이터 구조를 사용했기 때문에, `Item` 컴포넌트는 아무것도 바꿀 필요가 없다. 게시물을 가져온 뒤에도 여전히 `title` 프로퍼티를 가지고 있으므로 검색 기능으로 필터링하는 것도 그대로 가능하다. 이 동작은 다음 실습들 중 하나에서 바뀔 것이다.

## 정리

- 리액트 애플리케이션에서 데이터 페칭은 대개 `fetch` API나 서드파티 라이브러리로 원격 API에 요청을 보내는 방식으로 이뤄진다.
- 이미 `useReducer`와 `useEffect`로 마련해둔 비동기 상태 관리 구조 덕분에, 가짜 데이터를 실제 API 호출로 교체하는 작업은 최소한의 변경만으로 끝난다.
- `fetch`의 응답은 `response.json()`으로 JSON으로 변환해야 실제 데이터에 접근할 수 있다.
- 템플릿 리터럴(`` `${...}` ``)을 사용하면 문자열 연결(`+`)보다 더 간결하게 동적인 문자열을 만들 수 있다.

## Q&A

- **Q. 리액트 애플리케이션에서 API로부터 데이터를 가져오는 일이 흔한 이유는 무엇인가?**
  - A. API에서 데이터를 가져오면 리액트 애플리케이션이 외부 소스의 정보를 동적으로 조회하고 표시할 수 있기 때문이다.
- **Q. API를 다룰 때 리액트의 `useEffect` 훅은 어떤 목적으로 쓰이는가?**
  - A. `useEffect`는 함수 컴포넌트에서 데이터 페칭 같은 사이드 이펙트를 수행하는 데 사용되며, 렌더링 이후에 이펙트가 실행되도록 보장한다.
- **Q. 리액트 컴포넌트에서 비동기 API 호출은 어떻게 처리하는가?**
  - A. 비동기 API 호출은 보통 `useEffect` 안에서 `async/await` 문법이나 `Promise`로 처리한다.
- **Q. 리액트에서 `useEffect`로 API 요청 이후 정리(cleanup) 작업을 수행할 수 있는가?**
  - A. 가능하다. `useEffect`는 정리 함수를 반환함으로써 대기 중인 요청을 취소하거나 구독을 해제하는 등의 정리 작업을 할 수 있다.
- **Q. API를 다룰 때 `useEffect`의 두 번째 인자(의존성 배열)는 어떤 의미를 가지는가?**
  - A. 의존성 배열은 이펙트가 언제 실행될지를 제어한다. 의존성을 지정하면 그 값이 바뀔 때만 이펙트가 다시 실행되도록 보장한다.
- **Q. 리액트에서 API 요청 중 발생하는 에러는 어떻게 처리하는가?**
  - A. API 요청 중 발생하는 에러는 `try...catch` 블록, `Promise`의 `.catch()`, 또는 에러 상태 변수를 설정하는 방식으로 처리할 수 있다.
- **Q. 리액트에서 API 데이터를 다룰 때 상태 관리는 어떤 중요성을 가지는가?**
  - A. 상태 관리를 통해 리액트 컴포넌트는 API에서 가져온 데이터를 저장하고 갱신하며, 필요할 때 리렌더링을 트리거할 수 있다.
- **Q. 리액트에서 API 요청의 디바운싱(debouncing)이란 무엇인가?**
  - A. 디바운싱은 짧은 시간 안에 발생하는 API 요청 횟수를 줄이기 위해 요청 실행을 지연시키는 것으로, 보통 성능을 높이고 요청 제한(rate limit)을 피하기 위해 사용한다.
- **Q. 리액트에서 API 요청 시 로딩 상태를 처리하는 것이 중요한 이유는 무엇인가?**
  - A. 로딩 상태를 처리하면 데이터를 가져오는 동안 사용자에게 피드백을 줄 수 있어서, 사용자 경험을 향상시키고 백그라운드 작업이 진행 중임을 알려줄 수 있다.
