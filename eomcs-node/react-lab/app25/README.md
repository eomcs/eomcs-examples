# app25. 리액트 State 심화

## 개요

지금까지 이 애플리케이션의 모든 상태 관리는 리액트의 `useState` 훅에 크게 의존해왔다. 반면 리액트의 **`useReducer`** 훅을 사용하면, 복잡한 상태 구조와 전이(transition)를 좀 더 정교하게 관리할 수 있다. 자바스크립트에서 리듀서(reducer)에 대한 지식은 커뮤니티마다 호불호가 갈리는 주제이므로 여기서 기초까지 다루지는 않는다.

이번 실습에서는 `useState` 훅으로 관리하던 상태화된 `stories`를 리액트의 `useReducer` 훅으로 옮긴다. **여러 상태 값들이 서로 의존적이거나 하나의 도메인에 관련돼 있을 때는 `useState` 대신 `useReducer`를 쓰는 것이 합리적이다.** 예를 들어 `stories`, `isLoading`, `error`는 모두 데이터 페칭이라는 하나의 도메인에 관련돼 있다. 좀 더 추상화하면, 이 셋 모두 하나의 복잡한 객체(예: `data`, `isLoading`, `error`)의 프로퍼티로서 리듀서가 관리하게 만들 수도 있다. 이 부분은 나중 실습에서 다룰 것이고, 이번에는 먼저 `stories`와 그 상태 전이를 리듀서로 관리하는 것부터 시작한다.

> Java에 비유하면, 리듀서(reducer)는 상태 머신(state machine)의 전이 함수와 비슷하다. `(state, action) -> newState`라는 순수 함수 시그니처는 자바에서 `BiFunction<State, Action, State>`에 해당하고, `dispatch`로 액션을 보내는 것은 이벤트를 큐에 넣고 상태 머신이 그 이벤트 타입에 따라 다음 상태를 계산하게 하는 것과 비슷하다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const getAsyncStories = () =>
  new Promise((resolve) => /* ... */ );

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, setStories] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(false);
  const [isError, setIsError] = React.useState(false);

  React.useEffect(() => {
    setIsLoading(true);

    getAsyncStories()
      .then((result) => {
        setStories(result.data.stories);
        setIsLoading(false);
      })
      .catch(() => setIsError(true));
  }, []);

  const handleRemoveStory = (item) => {
    const newStories = stories.filter(
      (story) => item.objectID !== story.objectID
    );

    setStories(newStories);
  };

  // ...
};
```

## 실습

### 실습 1. `storiesReducer` 함수 만들기

먼저 컴포넌트 바깥에 리듀서 함수를 하나 도입한다. 리듀서 함수는 항상 `state`와 `action`을 인자로 받는다. 이 두 인자를 바탕으로, 리듀서는 항상 새로운 `state`를 반환한다.

```jsx
// src/App.jsx
const getAsyncStories = () =>
  new Promise((resolve) => /* ... */ );

const storiesReducer = (state, action) => {
  if (action.type === 'SET_STORIES') {
    return action.payload;
  } else {
    throw new Error();
  }
};
```

리듀서의 액션은 항상 `type`을 가지며, 모범 사례로서 `payload`도 함께 갖는 것이 좋다. `type`이 리듀서 안의 어떤 조건과 일치하면, 들어온 `state`와 `action`을 바탕으로 새로운 `state`를 반환한다. 리듀서가 다루지 않는 타입이라면, 구현이 누락됐음을 스스로 상기할 수 있도록 에러를 던진다.

`storiesReducer` 함수는 하나의 타입만 다루며, 현재 `state`를 사용하지 않고 그냥 들어온 `action`의 `payload`를 그대로 반환한다. 즉, 새로운 `state`는 단순히 `payload`다.

### 실습 2. `useState`를 `useReducer`로 교체하기

`App` 컴포넌트에서 `stories`를 관리하던 `useState`를 `useReducer`로 교체한다. 이 새로운 훅은 리듀서 함수와 초기 상태를 인자로 받고, 두 개의 아이템이 담긴 배열을 반환한다. 첫 번째 아이템은 현재 상태이고, 두 번째 아이템은 상태 갱신 함수(**디스패치(dispatch) 함수**라고도 부른다)다.

```jsx
const App = () => {
  // ...

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    []
  );

  // ...
};
```

- 새로운 디스패치 함수는 이전에 `useState`가 반환했던 `setStories` 함수 대신 사용할 수 있다. 
- `useState`의 상태 갱신 함수로 상태를 명시적으로 설정하는 대신, **`useReducer`의 상태 갱신 함수는 리듀서를 향해 액션을 디스패치함으로써 암묵적으로 상태를 설정한다.** 

액션에는 `type`과 선택적인 `payload`가 담긴다.

```jsx
const App = () => {
  // ...

  React.useEffect(() => {
    setIsLoading(true);

    getAsyncStories()
      .then((result) => {
        dispatchStories({
          type: 'SET_STORIES',
          payload: result.data.stories,
        });
        setIsLoading(false);
      })
      .catch(() => setIsError(true));
  }, []);

  const handleRemoveStory = (item) => {
    const newStories = stories.filter(
      (story) => item.objectID !== story.objectID
    );

    dispatchStories({
      type: 'SET_STORIES',
      payload: newStories,
    });
  };

  // ...
};
```

브라우저에서 보이는 애플리케이션의 모습은 그대로지만, 이제는 리듀서와 리액트의 `useReducer` 훅이 `stories`의 상태를 관리하고 있다.

### 실습 3. 리듀서로 상태 전이 로직 옮기기

**리듀서라는 개념을 최소한의 형태로 제대로 활용하려면, 하나 이상의 상태 전이를 다뤄야 한다.** 상태 전이가 하나뿐이라면 굳이 리듀서를 쓸 이유가 없다.

지금까지는 `handleRemoveStory` 핸들러가 직접 새로운 `stories`를 계산했다. 이 로직을 리듀서 함수 안으로 옮기고, 액션으로 리듀서를 다루도록 하는 것도 타당한 접근이다. 이 또한 명령형에서 선언형 프로그래밍으로 옮겨가는 또 다른 사례다. "어떻게" 해야 하는지를 우리가 직접 말하는 대신, 리듀서에게 "무엇을" 해야 하는지만 알려주는 것이다. 나머지는 전부 리듀서 안에 감춰진다.

```jsx
const App = () => {
  // ...

  const handleRemoveStory = (item) => {
    dispatchStories({
      type: 'REMOVE_STORY',
      payload: item,
    });
  };

  // ...
};
```

이제 리듀서 함수는 새로운 조건부 상태 전이로 이 새로운 케이스도 다뤄야 한다. story를 제거하는 조건이 만족되면, 리듀서가 story를 제거하는 데 필요한 모든 구현 세부사항을 가지고 있다. 액션은 story를 현재 상태에서 제거하는 데 필요한 모든 정보(여기서는 아이템의 식별자)를 제공하고, 필터링된 새로운 story 목록을 상태로 반환한다.

```jsx
const storiesReducer = (state, action) => {
  if (action.type === 'SET_STORIES') {
    return action.payload;
  } else if (action.type === 'REMOVE_STORY') {
    return state.filter(
      (story) => action.payload.objectID !== story.objectID
    );
  } else {
    throw new Error();
  }
};
```

### 실습 4. `switch` 문으로 리팩터링하기

하나의 리듀서 함수에 상태 전이가 더 늘어나면, 이런 if-else 문들은 결국 지저분해질 것이다. 이를 모든 상태 전이를 다루는 `switch` 문으로 리팩터링하면 가독성이 좋아지며, 리액트 커뮤니티에서도 모범 사례로 여겨진다.

```jsx
const storiesReducer = (state, action) => {
  switch (action.type) {
    case 'SET_STORIES':
      return action.payload;
    case 'REMOVE_STORY':
      return state.filter(
        (story) => action.payload.objectID !== story.objectID
      );
    default:
      throw new Error();
  }
};
```

여기까지 자바스크립트의 리듀서와, 리액트의 `useReducer` 훅을 통한 그 사용법을 최소한의 형태로 살펴봤다. **이 리듀서는 두 가지 상태 전이를 다루고**, 현재 상태와 액션을 어떻게 새로운 상태로 계산하는지 보여주며, 상태 전이를 위해 (story 제거라는) 일부 비즈니스 로직도 사용한다. 이제 비동기적으로 도착하는 데이터를 위한 `stories` 목록을 상태로 설정하고, 목록에서 story를 제거하는 일 모두를, 하나의 상태 관리 리듀서와 이에 연결된 `useReducer` 훅만으로 처리할 수 있게 됐다.

## 정리

- 여러 상태 값이 서로 의존적이거나 하나의 도메인에 관련돼 있다면, `useState`보다 **`useReducer`**를 쓰는 것이 더 적합하다.
- 리듀서 함수는 `(state, action) => newState` 형태를 가지며, 액션의 `type`에 따라 새로운 상태를 계산해서 반환한다.
- `useReducer`는 현재 상태와 **디스패치 함수**를 반환한다. 디스패치 함수는 `type`과 `payload`를 가진 액션을 리듀서에 보내서 상태를 암묵적으로 갱신한다.
- 상태를 어떻게 바꿀지에 대한 로직을 이벤트 핸들러가 아니라 리듀서 안에 모아두면, 핸들러는 "무엇을" 할지만 선언하고 리듀서가 "어떻게" 할지를 처리하게 된다.
- 상태 전이가 여러 개로 늘어나면 if-else 문 대신 `switch` 문을 사용하는 것이 가독성 면에서 더 낫다.

## Q&A

- **Q. 리액트에서 `useReducer`란 무엇인가?**
  - A. `useReducer`는 함수 컴포넌트에서 액션을 디스패치해서 상태를 갱신함으로써 복잡한 상태 로직을 관리하는 리액트 훅이다.
- **Q. `useReducer`는 리액트의 `useState`와 어떻게 다른가?**
  - A. `useState`는 개별 상태 변수를 관리하기에 더 단순한 반면, `useReducer`는 여러 값이 서로 의존적인 복잡한 상태 로직에 더 적합하다.
- **Q. `useReducer` 훅의 기본 구조는 어떻게 되는가?**
  - A. 리듀서 함수와 초기 상태를 인자로 받아, 현재 상태와 상태 갱신을 위한 디스패치 함수를 반환한다.
- **Q. `useReducer`에서 리듀서 함수란 무엇인가?**
  - A. 리듀서 함수는 현재 상태와 액션을 바탕으로, 디스패치된 액션에 대한 응답으로 상태가 어떻게 바뀌어야 하는지를 지정하는 역할을 한다.
- **Q. `useReducer`를 사용할 때 상태는 어떻게 갱신되는가?**
  - A. 액션을 디스패치함으로써 상태가 갱신되며, 리듀서 함수가 현재 상태와 액션 타입을 바탕으로 새로운 상태를 결정한다.
- **Q. `useReducer`가 리액트에서 `useState`의 모든 사용 사례를 대체할 수 있는가?**
  - A. `useReducer`가 강력하긴 하지만, 모든 상황에 필요한 것은 아니다. 개별 상태 변수를 관리할 때는 `useState`가 더 단순하고 적합하다.
