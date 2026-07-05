# app23. 리액트 비동기 데이터

## 개요

지금 애플리케이션에는 두 가지 상호작용이 있다. 목록을 검색하는 것과, 목록에서 아이템을 제거하는 것이다. 첫 번째 상호작용은 서드파티 상태(`searchTerm`)를 통해 목록에 적용되는 유동적인 변경인 반면, 두 번째 상호작용은 목록에서 아이템을 되돌릴 수 없게(non-reversible) 삭제하는 것이다.

그런데 우리가 다루고 있는 목록은 여전히 그냥 샘플 데이터일 뿐이다. 이제 애플리케이션이 실제 데이터를 다룰 수 있도록 준비해보면 어떨까?

리액트 같은 클라이언트 사이드 애플리케이션에서는 보통 원격 백엔드/데이터베이스에서 오는 데이터가 **비동기적으로** 도착한다. 그래서 데이터 페칭을 시작하기도 전에 컴포넌트를 먼저 렌더링해야 하는 경우가 흔하다. 이번 실습에서는 먼저 애플리케이션의 샘플 데이터로 이런 비동기 데이터를 흉내 내보는 것부터 시작한다. 나중에는 이 샘플 데이터를 실제 원격 API에서 가져온 진짜 데이터로 교체할 것이다.

> Java에 비유하면, `Promise`는 자바의 `CompletableFuture`와 비슷하다. `getAsyncStories()`가 `Promise.resolve(...)`를 반환하는 것은 `CompletableFuture.completedFuture(...)`로 이미 완료된 퓨처를 만드는 것과 같고, `setTimeout`으로 지연시키는 것은 별도 스레드에서 지연 후 `complete()`를 호출하는 것에 비유할 수 있다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const initialStories = [ /* ... */ ];

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, setStories] = React.useState(initialStories);

  const handleRemoveStory = (item) => {
    const newStories = stories.filter(
      (story) => item.objectID !== story.objectID
    );

    setStories(newStories);
  };

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // ...
};
```

## 실습

### 실습 1. `Promise`를 반환하는 함수로 비동기 데이터 흉내 내기

우선, 완료되면 데이터를 담은 `Promise`를 반환하는 함수를 축약형으로 만든다. 이 `Promise`가 완료(resolve)되면 기존의 `stories` 목록을 담은 객체를 넘겨준다.

```jsx
const initialStories = [ /* ... */ ];

const getAsyncStories = () =>
  Promise.resolve({ data: { stories: initialStories } });
```

### 실습 2. `useEffect`로 비동기 데이터를 가져와 상태에 반영하기

`App` 컴포넌트에서는 `initialStories`를 바로 쓰는 대신, 초기 상태로 빈 배열을 사용한다. 처음에는 빈 `stories` 목록에서 시작해서, 이 목록을 비동기적으로 가져오는 것처럼 흉내 낸다. 새로운 `useEffect` 훅 안에서 이 함수를 호출하고, 반환된 `Promise`를 사이드 이펙트로 처리한다. 의존성 배열이 빈 배열이므로, 이 사이드 이펙트는 컴포넌트가 처음 렌더링될 때 딱 한 번만 실행된다.

```jsx
const App = () => {
  // ...

  const [stories, setStories] = React.useState([]);

  React.useEffect(() => {
    getAsyncStories().then((result) => {
      setStories(result.data.stories);
    });
  }, []);

  // ...
};
```

애플리케이션을 시작하면 데이터가 비동기적으로 도착해야 할 텐데도, 즉시 렌더링되는 것처럼 보여서 마치 동기적으로 도착하는 것처럼 느껴진다. 실제 원격 API로의 모든 네트워크 요청에는 지연 시간이 있기 마련이므로, 좀 더 현실적인 지연을 줘서 이 부분을 바꿔보자.

### 실습 3. 지연 시간을 추가해서 더 현실적으로 만들기

먼저, `Promise`의 축약형을 제거한다.

```jsx
const getAsyncStories = () =>
  new Promise((resolve) =>
    resolve({ data: { stories: initialStories } })
  );
```

그다음, `Promise`가 완료(resolve)될 때 2초의 지연을 준다.

```jsx
const getAsyncStories = () =>
  new Promise((resolve) =>
    setTimeout(
      () => resolve({ data: { stories: initialStories } }),
      2000
    )
  );
```

애플리케이션을 다시 시작해보면, 목록이 지연되어 렌더링되는 것을 확인할 수 있다. `stories`의 초기 상태는 빈 배열이므로 처음에는 `List` 컴포넌트에 아무것도 렌더링되지 않는다. `App` 컴포넌트가 렌더링된 후 사이드 이펙트 훅이 한 번 실행되어 비동기 데이터를 가져온다. `Promise`가 완료되고 그 데이터가 컴포넌트의 상태에 설정되면, 컴포넌트가 다시 렌더링되어 비동기적으로 로드된 목록을 화면에 보여준다.

이번 실습은 리액트에서 비동기 데이터를 다루는 첫걸음일 뿐이다. 처음부터 데이터를 가지고 있는 대신, `Promise`로부터 데이터를 비동기적으로 완료(resolve)시켰다. 하지만 우리는 그저 `stories`를 동기 데이터에서 비동기 데이터로 옮겼을 뿐, 여전히 샘플 데이터를 다루고 있다. 실제 데이터를 가져오는 방법은 앞으로 배우게 될 것이다.

## 정리

- 실제 애플리케이션에서 원격 데이터는 대부분 **비동기적으로** 도착하므로, 컴포넌트는 데이터가 도착하기 전에 먼저 렌더링돼야 하는 경우가 많다.
- `Promise`를 반환하는 함수로 비동기 데이터 페칭을 흉내 낼 수 있으며, 나중에 실제 API 호출로 손쉽게 교체할 수 있다.
- `useEffect`의 의존성 배열을 빈 배열(`[]`)로 지정하면, 컴포넌트가 처음 렌더링될 때 데이터 페칭 같은 사이드 이펙트를 한 번만 실행할 수 있다.
- `stories`의 초기 상태를 빈 배열로 두면, 데이터가 도착하기 전까지는 빈 목록이, 데이터가 도착한 뒤에는 갱신된 목록이 렌더링된다.

## Q&A

- **Q. 리액트 애플리케이션에서 비동기 데이터를 다루는 일이 흔한 이유는 무엇인가?**
  - A. 클라이언트 사이드 리액트 애플리케이션은 대부분 원격 소스에서 데이터를 가져오기 때문이다.
- **Q. 리액트에서 데이터 페칭을 시작하기 전에 컴포넌트를 렌더링하는 일반적인 방법은 무엇인가?**
  - A. 데이터가 도착하기 전에 컴포넌트를 먼저 렌더링하고, 데이터가 도착할 때까지 조건부 렌더링이나 플레이스홀더 콘텐츠를 사용하는 방식이 일반적이다.
- **Q. 샘플 데이터로 리액트에서 비동기 데이터 페칭을 흉내 내려면 어떻게 하는가?**
  - A. `Promise`를 반환하는 함수를 사용해서 샘플 데이터로 완료(resolve)시키고, 나중에 실제 데이터로 교체하는 방식으로 흉내 낼 수 있다.
- **Q. 리액트에서 비동기 데이터를 다룰 때 `Promise`의 역할은 무엇인가?**
  - A. `Promise`는 비동기 작업을 관리해서, 컴포넌트가 데이터가 완료(resolve)될 때까지 기다린 뒤 렌더링할 수 있게 해준다.
- **Q. 리액트에서 반응성 있는 사용자 인터페이스를 위해 비동기 데이터 페칭이 중요한 이유는 무엇인가?**
  - A. 비동기 데이터 페칭은 UI가 블로킹되지 않도록 막아줘서 반응성을 유지하고, 데이터가 준비되면 갱신된 정보를 보여줄 수 있게 해준다.
- **Q. 리액트에서 흉내 낸 샘플 데이터를 실제 원격 API에서 가져온 데이터로 교체할 수 있는가?**
  - A. 가능하다. 샘플 데이터로 비동기 데이터를 흉내 낸 뒤, 실제 원격 API에서 가져온 데이터로 매끄럽게 교체할 수 있다.
- **Q. 리액트에서 비동기 데이터를 다룰 때 `useState` 훅을 사용하는 의미는 무엇인가?**
  - A. `useState`는 로딩 상태와 비동기적으로 받은 갱신된 데이터를 포함해서, 컴포넌트가 상태 변화를 관리할 수 있게 해준다.
- **Q. 비동기 데이터가 도착했을 때 리액트는 컴포넌트가 다시 렌더링되도록 어떻게 보장하는가?**
  - A. 비동기 데이터가 도착해서 상태가 갱신되면, 리액트의 상태 관리 메커니즘이 컴포넌트를 다시 렌더링해서 새 데이터를 반영하도록 보장한다.
