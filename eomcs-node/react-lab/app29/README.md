# app29. 리액트의 메모이제이션 함수 (심화)

## 개요

리액트 컴포넌트 안에 정의된 함수는 대부분 이벤트 핸들러 역할을 한다. 하지만 리액트 컴포넌트 자체도 본질적으로 하나의 함수이므로, 컴포넌트 안에서 일반 함수, 함수 표현식, 화살표 함수 표현식을 얼마든지 선언할 수 있다. 이번 실습에서는 리액트의 **`useCallback`** 훅을 사용한 **메모이제이션 함수(memoized function)** 라는 개념을 소개한다.

> Java에 비유하면, `useCallback`은 같은 입력(의존성 배열)에 대해 매번 새 람다 객체를 만드는 대신, 캐시된 함수형 인터페이스 인스턴스를 재사용하는 것과 비슷하다. 자바에서 메서드 참조나 람다를 필드에 캐싱해서 `equals()` 비교나 `hashCode` 기반 캐시 무효화를 피하는 것과 유사한 발상이다. 여기서는 참조 동일성(reference equality)이 `useEffect`의 의존성 비교에 영향을 주기 때문에 이 캐싱이 중요하다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  React.useEffect(() => {
    if (!searchTerm) return;

    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}${searchTerm}`)
      .then((response) => response.json())
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [searchTerm]);

  // ...
};
```

## 실습

### 실습 1. 데이터 페칭 로직을 `useCallback`으로 감싼 함수로 추출하기

```jsx
// src/App.jsx
const App = () => {
  // ...

  // (A)
  const handleFetchStories = React.useCallback(() => { // (B)
    if (!searchTerm) return;

    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}${searchTerm}`)
      .then((response) => response.json())
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [searchTerm]); // (E)

  React.useEffect(() => {
    handleFetchStories(); // (C)
  }, [handleFetchStories]); // (D)

  // ...
};
```

- **(A)** 사이드 이펙트 안에 있던 모든 데이터 페칭 로직을 화살표 함수 표현식으로 옮긴다.
- **(B)** 추출한 함수를 리액트의 `useCallback` 훅으로 감싼다.
- **(C)** 이렇게 정의한 함수를 `useEffect` 훅 안에서 호출한다.
- **(D)** `useEffect` 훅의 의존성 배열에 `handleFetchStories`를 넣는다.
- **(E)** `useCallback` 훅의 의존성 배열에 `searchTerm`을 넣는다. 이 배열 안의 값이 바뀌면 새로운 메모이제이션 함수가 만들어진다.

근본적으로 애플리케이션의 동작은 그대로다. `useEffect` 훅에서 새로운 함수를 하나 추출했을 뿐이기 때문이다. 데이터 페칭 로직을 사이드 이펙트 안에서 직접 사용하는 대신, 애플리케이션 전체에서 쓸 수 있는 함수로 만들었다. 이 덕분에 **재사용성**을 얻게 된다. 이 새로운 함수를 호출하기만 하면, 애플리케이션의 다른 부분에서도 데이터 페칭을 사용할 수 있다.

## `useCallback`이 필요한 이유
그런데 이 추출한 함수를 리액트의 `useCallback` 훅으로 감쌌는데, 왜 여기서 이게 필요한지 살펴보자. 리액트의 `useCallback` 훅은 자신의 의존성 배열(E)이 바뀔 때마다 **메모이제이션된 함수**를 새로 만든다. 그 결과 `useEffect` 훅도 다시 실행되는데(C), 이는 새로운 함수(D)에 의존하고 있기 때문이다.

**흐름 시각화**

```
1. 변경: searchTerm (원인: 사용자 상호작용)
2. 변경: handleFetchStories (원인: searchTerm이 바뀜)
3. 실행: 사이드 이펙트 (원인: handleFetchStories가 바뀜)
```

만약 리액트의 `useCallback` 훅을 빼고 `handleFetchStories` 이벤트 핸들러를 그냥 정의했다면 어떻게 될까? `App` 컴포넌트가 리렌더링될 때마다 새로운 `handleFetchStories` 함수가 만들어지고, 이 함수가 `useEffect` 훅 안에서 실행되어 데이터를 가져올 것이다. 가져온 데이터는 컴포넌트의 상태로 저장된다. 그러면 컴포넌트의 상태가 바뀌었기 때문에 컴포넌트가 다시 렌더링되고, 또 새로운 `handleFetchStories` 함수가 만들어진다. 그러면 사이드 이펙트가 다시 데이터를 가져오도록 트리거되고, 결국 끝나지 않는 반복에 갇히게 된다.

**흐름 시각화**

```
1. 정의: handleFetchStories
2. 실행: 사이드 이펙트
3. 갱신: state
4. 리렌더링: 컴포넌트
5. 재정의: handleFetchStories
6. 실행: 사이드 이펙트
...
```

리액트의 `useCallback` 훅을 제거하고 이 무한 루프를 직접 시도해볼 수도 있지만, 브라우저가 멈출 각오는 해두는 게 좋다. `useCallback` 훅은 의존성 배열 안의 값 중 하나가 바뀔 때만 함수를 새로 만든다. 바로 그 시점이 우리가 데이터를 다시 가져오길(re-fetch) 원하는 시점이다. 입력 필드에 새로운 입력이 들어왔고, 그 새로운 데이터가 목록에 반영되길 원하기 때문이다.

데이터 페칭 함수를 `useEffect` 훅 바깥으로 옮김으로써, 이 함수는 애플리케이션의 다른 부분에서도 재사용할 수 있게 됐다. 지금 당장 다른 곳에서 사용하지는 않지만, 리액트의 메모이제이션 함수를 이해하기에 좋은 사례다. 이제 `useEffect` 훅은 `searchTerm`이 바뀔 때 암묵적으로 실행된다. `searchTerm`이 바뀔 때마다 `handleFetchStories`가 다시 정의되기 때문이다. `useEffect` 훅이 `handleFetchStories`에 의존하고 있으므로, 데이터 페칭을 위한 사이드 이펙트가 다시 실행된다.

## 정리

- **메모이제이션 함수**란, 의존성 배열의 값이 바뀔 때만 새로운 함수 인스턴스를 만들고, 그렇지 않으면 이전 함수 인스턴스를 그대로 재사용하는 함수를 말한다.
- 리액트의 **`useCallback`** 훅으로 함수를 감싸면, 그 함수를 의존성으로 사용하는 `useEffect`가 불필요하게 반복 실행되는 것을 막을 수 있다.
- 데이터 페칭 로직을 `useEffect` 안에 직접 두는 대신 별도의 함수로 추출하면, 애플리케이션의 다른 부분에서도 그 로직을 재사용할 수 있다.
- `useCallback` 없이 컴포넌트 안에서 함수를 매번 새로 정의하면, 그 함수를 의존성으로 삼는 `useEffect`가 렌더링마다 다시 실행되어 무한 루프에 빠질 수 있다.

## Q&A

- **Q. 리액트에서 `useCallback` 훅의 목적은 무엇인가?**
  - A. `useCallback`은 리액트에서 함수를 메모이제이션해서, 리렌더링마다 함수가 불필요하게 다시 만들어지는 것을 막아준다.
- **Q. 리액트 컴포넌트에서 `useCallback`은 언제 사용해야 하는가?**
  - A. 성능을 최적화하기 위해 함수를 메모이제이션하고 싶을 때, 특히 자식 컴포넌트에 콜백 함수를 전달하는 상황에서 `useCallback`을 사용한다.
- **Q. `useCallback` 훅의 인자는 무엇인가?**
  - A. 첫 번째 인자는 메모이제이션할 함수이고, 두 번째 인자는 의존성 배열로, 이 배열의 값이 바뀌면 새로운 메모이제이션 함수가 만들어진다.
- **Q. `useCallback`의 의존성 배열이 비어 있으면 어떻게 되는가?**
  - A. 의존성 배열이 비어 있으면, 메모이제이션된 함수는 딱 한 번만 만들어지고 컴포넌트의 생명주기 동안 계속 동일하게 유지된다.
