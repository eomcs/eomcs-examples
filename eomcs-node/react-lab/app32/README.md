# app32. 리액트에서 Async/Await

## 개요

실제 애플리케이션을 다루다 보면 비동기 데이터를 피해갈 방법이 없다. 프런트엔드든 백엔드든 항상 데이터를 주는 원격 API가 있기 마련이므로, 이 데이터를 비동기적으로 다루는 방법을 이해해야 한다. 지금까지 우리 리액트 애플리케이션에서는 `then`/`catch` 블록으로 `Promise`를 처리해왔다. 하지만 요즘 자바스크립트(그리고 그에 따라 리액트)에서 더 널리 쓰이는 방법은 **`async`/`await`**다.

`async`/`await`에 이미 익숙하거나 직접 사용법을 탐구해보고 싶다면, `then`/`catch`를 `async`/`await`로 바꿔봐도 좋다. 여기까지 왔다면, `catch` 블록을 없애는 대신 `try`/`catch` 블록으로 에러 처리를 보완하는 것도 고려해볼 수 있다.

> Java에 비유하면, `then`/`catch` 체이닝은 `CompletableFuture.thenApply().exceptionally()`처럼 콜백을 연쇄하는 스타일이고, `async`/`await`는 마치 동기 코드처럼 보이게 해주는 문법이다. 자바에는 언어 차원의 `await` 키워드는 없지만, `CompletableFuture.get()`이나 가상 스레드(virtual thread)로 블로킹처럼 보이는 코드를 작성하는 것과 비슷한 발상이다. `try`/`catch`로 에러를 잡는 방식은 자바와 완전히 동일하다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    axios
      .get(url)
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.data.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [url]);

  // ...
};
```

## 실습

### 실습 1. `then`/`catch`를 `async`/`await`로 바꾸기

먼저 `then`/`catch` 문법을 `async`/`await` 문법으로 바꿔야 한다. 아래는 에러 처리 없이 `handleFetchStories()` 함수를 리팩터링하는 방법을 보여준다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(async () => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    const result = await axios.get(url);

    dispatchStories({
      type: 'STORIES_FETCH_SUCCESS',
      payload: result.data.hits,
    });
  }, [url]);

  // ...
};
```

- `async`/`await`를 사용하려면 함수에 `async` 키워드가 필요하다. 
- 반환된 `Promise`에 `await` 키워드를 사용하기 시작하면, 모든 코드가 동기 코드처럼 읽힌다. 
- `await` 키워드 다음에 오는 동작들은 `Promise`가 완료(resolve)될 때까지 실행되지 않는다. 즉, 코드가 기다리게 된다.

### 실습 2. `try`/`catch`로 에러 처리 되살리기

이전처럼 에러 처리를 포함하려면 `try`와 `catch` 블록이 도움이 된다. `try` 블록 안에서 뭔가 잘못되면, 코드는 `catch` 블록으로 넘어가서 에러를 처리한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(async () => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    try {
      const result = await axios.get(url);

      dispatchStories({
        type: 'STORIES_FETCH_SUCCESS',
        payload: result.data.hits,
      });
    } catch {
      dispatchStories({ type: 'STORIES_FETCH_FAILURE' });
    }
  }, [url]);

  // ...
};
```

결국 `then`/`catch`보다 `async`/`await`와 `try`/`catch`를 함께 사용하는 편이 더 읽기 쉬운 경우가 많다. 콜백 함수를 사용하는 대신, 코드를 좀 더 동기적인 방식으로 읽히게 만들기 때문이다. 그렇다고 `then`/`catch`를 쓰는 것이 잘못됐다는 뜻은 아니다. 결국 프로젝트에 참여하는 팀 전체가 하나의 문법에 합의하는 것이 중요하다.

## 정리

- **`async`/`await`**는 비동기 코드를 동기 코드처럼 보이게 만들어주는 자바스크립트 문법으로, `then`/`catch` 체이닝보다 가독성이 좋은 경우가 많다.
- 함수를 `async`로 선언하면 함수 내부에서 `await` 키워드로 `Promise`가 완료될 때까지 기다릴 수 있다.
- `try`/`catch` 블록으로 `async`/`await` 코드의 에러 처리를 할 수 있으며, `try` 블록에서 에러가 발생하면 `catch` 블록으로 제어가 넘어간다.
- `then`/`catch`와 `async`/`await` 중 어느 쪽이 절대적으로 옳은 것은 아니며, 팀 전체가 일관된 문법에 합의하는 것이 중요하다.

## Q&A

- **Q. `async`/`await`란 무엇인가?**
  - A. `async`와 `await`는 자바스크립트에서 비동기 작업을 동기적인 방식처럼 다룰 수 있게 해주는 키워드로, 코드를 더 읽기 쉽게 만들어준다.
- **Q. 리액트에서 함수와 함께 `async`/`await`는 어떻게 사용하는가?**
  - A. 함수를 `async` 키워드로 선언하고, 함수 안에서 `await`를 사용해서 `Promise`를 처리한다.
- **Q. 리액트에서 `async` 함수의 목적은 무엇인가?**
  - A. `async` 함수는 비동기 코드를 더 읽기 쉽고 순차적인 방식으로 다룰 수 있게 해줘서, `Promise` 처리를 개선해준다.
- **Q. 리액트에서 `async`/`await`로 에러는 어떻게 처리하는가?**
  - A. `async` 함수 안에서 `try`/`catch` 블록을 사용해서 에러를 잡고 처리한다.
- **Q. 리액트에서 `async`/`await`는 `Promise`의 `.then()`을 사용하는 것과 어떻게 다른가?**
  - A. `async`/`await`는 `.then()` 체이닝에 비해 더 간결한 문법을 제공해서, 비동기 코드가 동기 코드처럼 보이게 해준다.
- **Q. 리액트에서 Fetch API와 함께 `async`/`await`를 사용할 수 있는가?**
  - A. 가능하다. 리액트에서 비동기 데이터 페칭에 Fetch API와 `async`/`await`를 함께 사용하는 것은 흔한 방식이다.
- **Q. 리액트에서 `async`/`await`로 여러 비동기 작업을 어떻게 처리하는가?**
  - A. `async` 함수 안에서 `Promise.all()`을 사용하면 여러 비동기 작업을 동시에 처리할 수 있다.
