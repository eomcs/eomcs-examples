# app26. 리액트의 불가능한 State

## 개요

`App` 컴포넌트에서 리액트의 `useState` 훅을 여러 개 사용할 때, 각각의 개별 상태들 사이에 뭔가 어긋난 느낌을 받았을 수도 있다. 엄밀히 말하면 비동기 데이터와 관련된 모든 상태는 원래 하나로 묶여야 한다. 여기에는 실제 데이터인 `stories`뿐 아니라, 그 로딩 상태와 에러 상태도 포함된다. 바로 이 지점에서 **하나의 리듀서와 리액트의 `useReducer` 훅이 도메인 관련 상태들을 함께 관리하는 역할을 맡게 된다.** 그런데 왜 이걸 신경 써야 할까?

하나의 리액트 컴포넌트 안에 여러 개의 `useState` 훅을 두는 것 자체는 전혀 문제가 없다. 하지만 여러 개의 상태 갱신 함수가 연달아 호출되는 모습을 보게 된다면 주의해야 한다. 이런 조건부 상태들은 UI에서 **불가능한 상태(impossible state)**와 원치 않는 동작으로 이어질 수 있다.

> Java에 비유하면, 불가능한 상태는 여러 개의 독립된 `boolean`/`Optional` 필드로 하나의 개념(로딩 중, 에러, 데이터 있음)을 표현할 때 생기는 문제와 비슷하다. `isLoading = true`이면서 동시에 `isError = true`인 상태가 만들어질 수 있는 것처럼 말이다. 이를 `enum State { LOADING, SUCCESS, ERROR }` 같은 하나의 대수적 타입(sum type)으로 묶으면 애초에 불가능한 조합 자체를 만들 수 없게 된다. 리액트의 `useReducer`로 여러 상태를 하나의 객체로 합치는 것도 같은 발상이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    []
  );
  const [isLoading, setIsLoading] = React.useState(false);
  const [isError, setIsError] = React.useState(false);

  // ...
};
```

## 실습

### 실습 1. 불가능한 상태를 직접 재현해보기

가짜 데이터 페칭 함수를 아래처럼 바꿔서 에러를 시뮬레이션하고, 우리가 구현한 에러 처리를 확인해보자.

```jsx
// src/App.jsx
const getAsyncStories = () =>
  new Promise((resolve, reject) => setTimeout(reject, 2000));
```

**불가능한 상태**는 비동기 데이터에 에러가 발생했을 때 나타난다. 에러 상태는 설정되지만, 로딩 인디케이터 상태는 해제되지 않는다. UI에서는 이것이 무한히 계속되는 로딩 인디케이터와 에러 메시지가 함께 나타나는 결과로 이어진다. 사실은 에러 메시지만 보여주고 로딩 인디케이터는 숨기는 편이 더 나을 것이다. 불가능한 상태는 발견하기 쉽지 않아서, UI 버그의 악명 높은 원인이 되곤 한다. 직접 이 버그를 고쳐보려고 시도해봐도 좋다.

### 실습 2. 관련된 상태들을 하나의 `useReducer`로 합치기

다행히 서로 관련된 상태들을 여러 개의 `useState`(그리고 `useReducer`) 훅에서 하나의 `useReducer` 훅으로 옮기면, 이런 버그를 겪을 가능성을 줄일 수 있다. 아래 훅들을 살펴보자.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    []
  );
  const [isLoading, setIsLoading] = React.useState(false);
  const [isError, setIsError] = React.useState(false);

  // ...
};
```

이 훅들을 하나의 `useReducer` 훅으로 합쳐서, 좀 더 복잡한 상태 객체와 좀 더 복잡한 상태 전이를 아우르는 통합된 상태 관리를 만들어보자.

```jsx
const App = () => {
  // ...

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    { data: [], isLoading: false, isError: false }
  );

  // ...
};
```

### 실습 3. 데이터 페칭 로직을 새로운 액션 타입으로 디스패치하기

이제 `useState`의 상태 갱신 함수를 더 이상 쓸 수 없으므로, 비동기 데이터 페칭과 관련된 모든 것은 새로운 디스패치 함수로 상태 전이를 처리해야 한다. 가장 직관적인 방법은 상태 갱신 함수를 디스패치 함수로 교체하는 것이다. 그러면 디스패치 함수는 고유한 `type`과 `payload`를 받는데, 이 `payload`는 원래 상태 갱신 함수로 상태를 갱신할 때 사용했을 값과 같다.

```jsx
const App = () => {
  // ...

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

### 실습 4. 복잡한 상태 객체를 다루도록 리듀서 고치기

리듀서 함수에 대해 두 가지를 바꿨다. 첫째, 바깥에서 디스패치 함수를 호출할 때 새로운 타입들을 도입했으므로, 그에 맞는 새로운 상태 전이 케이스를 추가해야 한다. 둘째, 상태 구조를 배열에서 복잡한 객체로 바꿨으므로, 들어오는 상태와 반환하는 상태 모두 이 새로운 복잡한 객체를 고려해야 한다.

```jsx
const storiesReducer = (state, action) => {
  switch (action.type) {
    case 'STORIES_FETCH_INIT':
      return {
        ...state,
        isLoading: true,
        isError: false,
      };
    case 'STORIES_FETCH_SUCCESS':
      return {
        ...state,
        isLoading: false,
        isError: false,
        data: action.payload,
      };
    case 'STORIES_FETCH_FAILURE':
      return {
        ...state,
        isLoading: false,
        isError: true,
      };
    case 'REMOVE_STORY':
      return {
        ...state,
        data: state.data.filter(
          (story) => action.payload.objectID !== story.objectID
        ),
      };
    default:
      throw new Error();
  }
};
```

모든 상태 전이에서, 자바스크립트의 스프레드 연산자(`...state`)로 현재 상태 객체의 모든 키/값 쌍을 그대로 가져오고, 새로 덮어쓸 프로퍼티만 추가한 새로운 상태 객체를 반환한다. 예를 들어 `STORIES_FETCH_FAILURE`는 `isLoading` 불리언을 `false`로, `isError` 불리언을 `true`로 설정하면서도 다른 상태(예: `data`, 즉 `stories`)는 그대로 유지한다. 에러가 발생하면 로딩 불리언도 함께 `false`가 되어야 하므로, 이렇게 해서 앞서 소개했던 불가능한 상태 버그를 피할 수 있다.

`REMOVE_STORY` 액션도 바뀌었다는 점에 주목하자. 이제 단순한 `state`가 아니라 `state.data`를 대상으로 동작한다. 상태는 더 이상 단순한 story 목록이 아니라, `data`, `isLoading`, `error`를 가진 복잡한 객체이기 때문이다. 나머지 코드에서도 이제 상태를 배열이 아니라 객체로 다뤄야 한다.

```jsx
const App = () => {
  // ...

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    { data: [], isLoading: false, isError: false }
  );

  // ...

  const searchedStories = stories.data.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      {/* ... */}

      {stories.isError && <p>Something went wrong ...</p>}

      {stories.isLoading ? (
        <p>Loading ...</p>
      ) : (
        <List
          list={searchedStories}
          onRemoveItem={handleRemoveStory}
        />
      )}
    </div>
  );
};
```

에러를 발생시키는 데이터 페칭 함수를 다시 사용해서, 이제 모든 것이 예상대로 동작하는지 확인해보자.

```jsx
const getAsyncStories = () =>
  new Promise((resolve, reject) => setTimeout(reject, 2000));
```

여러 개의 `useState` 훅으로 인해 신뢰할 수 없던 상태 전이에서, 리액트의 `useReducer` 훅으로 예측 가능한 상태 전이로 옮겨왔다. **리듀서가 관리하는 상태 객체는 로딩/에러 상태를 포함해 story를 가져오는 것과 관련된 모든 것을, 그리고 story 목록에서 아이템을 제거하는 것 같은 구현 세부사항까지도 함께 감싸안는다.** 불가능한 상태를 완전히 없애지는 못했다. 예전처럼 중요한 불리언 플래그 하나를 빠뜨리는 일이 여전히 가능하기 때문이다. 하지만 좀 더 예측 가능한 상태 관리를 향해 한 걸음 더 나아간 셈이다.

## 정리

- **불가능한 상태**는 여러 개의 독립된 `useState` 훅이 서로 관련돼 있음에도 따로 관리될 때, 있어서는 안 될 상태 조합(예: 로딩 중이면서 동시에 에러)이 생기는 문제를 말한다.
- **서로 관련된 상태들(`stories`, `isLoading`, `isError`)을 하나의 복잡한 상태 객체로 합쳐 `useReducer`로 관리하면, 이런 불가능한 상태를 피하는 데 도움이 된다.**
- 상태 전이마다 스프레드 연산자(`...state`)로 기존 상태를 유지하면서 필요한 프로퍼티만 덮어써야, 의도치 않게 다른 상태를 잃어버리지 않는다.
- 상태 구조가 배열에서 객체로 바뀌면, 그 상태를 사용하는 나머지 코드(`stories.data`, `stories.isLoading`, `stories.isError` 등)도 그에 맞게 수정해야 한다.
- `useReducer`로 옮긴다고 불가능한 상태가 완전히 사라지는 것은 아니지만, 상태 관리를 더 예측 가능하게 만들어준다.

## Q&A

- **Q. 리액트에서 "불가능한 상태(impossible states)"란 무엇인가?**
  - A. "불가능한 상태"는 잘 설계된 애플리케이션이라면 절대 일어나서는 안 되는 상태 값들의 조합을 말한다.
- **Q. 리액트 애플리케이션에서 불가능한 상태를 다루는 것이 중요한 이유는 무엇인가?**
  - A. 불가능한 상태를 다루면 애플리케이션이 유효하고 예측 가능한 상태를 유지하도록 보장해서, 예상치 못한 동작을 막을 수 있다.
- **Q. 리듀서는 리액트에서 불가능한 상태를 관리하는 데 어떻게 도움이 되는가?**
  - A. 리듀서는 상태를 통제된 방식으로 갱신할 수 있게 해줘서, 개발자가 규칙을 강제하고 불가능한 상태로의 전이를 피할 수 있게 해준다.
- **Q. 중앙화된 상태 관리는 불가능한 상태를 다루는 데 어떻게 기여하는가?**
  - A. 리듀서를 통한 중앙화된 상태 관리는 일관된 검증과 상태 갱신을 가능하게 해서, 여러 컴포넌트에 걸친 불가능한 상태의 위험을 줄여준다.
