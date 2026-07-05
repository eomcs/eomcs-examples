# app24. 리액트 조건부 렌더링

## 개요

이전 실습에서 살펴본 비동기 데이터 처리와 관련하여 새로운 기능을 소개할 차례다. 실제 애플리케이션에서는 데이터가 로딩되는 동안 사용자에게 로딩 스피너 같은 피드백을 보여주는 것이 일반적이다. 이번 실습의 목표는 이 피드백 메커니즘을 구현하는 것이다.

**과제**: 샘플 데이터를 `Promise`에서 로드하는 데는 시간이 걸린다. 이 시간 동안 사용자에게는 가장 단순한 형태의 로딩 인디케이터(예: "Loading …"라는 텍스트)가 보여야 한다. 데이터가 비동기적으로 도착하면, 로딩 인디케이터는 사라져야 한다.

**힌트**:

- 로딩 인디케이터를 보여주려면 새로운 상태 값이 필요하다. `isLoading`이라는 불리언 값이 가장 좋은 해법일 것이다.
- 데이터를 로드하는 사이드 이펙트가 실행될 때 이 불리언 상태를 `true`로 설정한다. 데이터가 로드되면 다시 `false`로 설정한다.
- JSX에서 `isLoading` 불리언이 `true`일 때 "Loading …" 텍스트를 조건부로 보여준다.

리액트에서 **조건부 렌더링(conditional rendering)**은 정보(예: state, props)에 따라 서로 다른 JSX를 렌더링해야 할 때마다 등장한다. 비동기 데이터를 다루는 것은 조건부 렌더링을 활용하기에 좋은 사례다. 예를 들어 애플리케이션이 처음 초기화될 때는 시작할 데이터가 없다. 그다음 데이터를 로딩하고, 마침내 데이터를 손에 넣어 화면에 표시한다. 때로는 데이터 페칭이 실패해서 대신 에러를 받기도 한다. 개발자로서 다뤄야 할 것들이 많다.

> Java에 비유하면, 조건부 렌더링은 상태에 따라 다른 뷰(View)를 반환하는 컨트롤러 메서드와 비슷하다. `isLoading ? <p>Loading...</p> : <List />` 같은 삼항 연산자는 자바의 삼항 연산자와 똑같이 동작하며, `isError && <p>...</p>`는 `if (isError) { return errorView; }`에 해당하는 단축 평가 표현이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다. 다행히 몇 가지는 이미 처리돼 있다. 예를 들어 초기 상태가 `null`이 아니라 빈 배열 `[]`이기 때문에, 이 목록을 필터링하거나 매핑할 때 애플리케이션이 깨질 걱정은 하지 않아도 된다.

```jsx
// src/App.jsx
const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, setStories] = React.useState([]);

  React.useEffect(() => {
    getAsyncStories().then((result) => {
      setStories(result.data.stories);
    });
  }, []);

  // ...
};
```

## 실습

### 실습 1. `isLoading` 상태로 로딩 여부 추적하기

여전히 신경 써야 할 부분이 남아 있다. 대기 중인 데이터 요청에 대해 사용자에게 피드백을 줄 로딩 상태가 없다는 점이다. 새로운 상태 값을 도입해서, 데이터를 가져오는 동안 상태를 적절히 설정하도록 이 문제를 해결하자.

```jsx
const App = () => {
  // ...

  const [stories, setStories] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(false);

  React.useEffect(() => {
    setIsLoading(true);

    getAsyncStories().then((result) => {
      setStories(result.data.stories);
      setIsLoading(false);
    });
  }, []);

  // ...
};
```

이제 불리언 값은 제대로 토글된다. 남은 것은 사용자에게 로딩 인디케이터를 보여주는 일이다. 가장 단순한 접근은 `App` 컴포넌트에서 **이른 반환(early return)**을 사용하는 것이다.

```jsx
const App = () => {
  // ...

  if (isLoading) {
    return <p>Loading ...</p>;
  }

  return (
    <div>
      {/* ... */}
    </div>
  );
};
```

하지만 이 방식으로는 로딩 인디케이터만 렌더링될 뿐, 그 외에는 아무것도 렌더링되지 않는다. 우리가 원하는 것은 JSX 안에 로딩 인디케이터를 인라인으로 넣어서, 로딩 인디케이터나 `List` 컴포넌트 둘 중 하나를 보여주는 것이다.

### 실습 2. 삼항 연산자로 JSX 안에서 조건부 렌더링하기

JSX 안에 if-else 문을 인라인으로 넣는 것은 JSX의 한계 때문에 권장되지 않는다(직접 시도해봐도 좋다). 대신 **삼항 연산자(ternary operator)**를 사용해서 JSX 안에 조건부 렌더링을 만들 수 있다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      {/* ... */}

      <hr />

      {isLoading ? (
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

이것으로 끝이다. 상태화된 불리언 값을 기준으로 로딩 인디케이터나 `List` 컴포넌트를 조건부로 렌더링하게 됐다.

### 실습 3. `isError` 상태로 에러 처리하기

이제 비동기 데이터에 대한 에러 처리도 구현해보자. 지금 흉내 내고 있는 환경에서는 에러가 발생하지 않지만, 실제 원격 API에서 데이터를 가져오기 시작하면 에러가 생길 수 있다. 그러니 에러 처리를 위한 상태를 하나 더 도입하고, `Promise`를 완료(resolve)할 때 `catch()` 블록에서 이를 처리하자.

```jsx
const App = () => {
  // ...

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

  // ...
};
```

### 실습 4. 논리 `&&` 연산자로 에러 메시지 보여주기

이번에는 다른 조건부 렌더링으로 뭔가 잘못됐을 때 사용자에게 피드백을 주자. 이번에는 무언가를 렌더링하거나 아무것도 렌더링하지 않거나 둘 중 하나이므로, 한쪽이 `null`을 반환하는 삼항 연산자 대신 축약형인 논리 `&&` 연산자를 사용한다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      {/* ... */}

      <hr />

      {isError && <p>Something went wrong ...</p>}

      {isLoading ? (
        <p>Loading ...</p>
      ) : (
        // ...
      )}
    </div>
  );
};
```

자바스크립트에서 `true && 'Hello World'`는 항상 `'Hello World'`로 평가된다. `false && 'Hello World'`는 항상 `false`로 평가된다. 리액트에서는 이 동작을 유리하게 활용할 수 있다. 조건이 `true`면 논리 `&&` 연산자 뒤의 표현식이 결과로 출력된다. 조건이 `false`면 리액트는 그 표현식을 무시하고 건너뛴다. `expression && JSX`를 사용하는 것이 `expression ? JSX : null`을 사용하는 것보다 더 간결하다.

조건부 렌더링이 비동기 데이터에만 쓰이는 것은 아니다. 가장 단순한 조건부 렌더링 예는 버튼으로 토글되는 불리언 상태다. 불리언 플래그가 `true`면 뭔가를 렌더링하고, `false`면 아무것도 렌더링하지 않는다. 리액트의 이 기능을 알고 있으면 JSX를 조건부로 렌더링할 수 있어서 매우 강력하다. UI를 더 동적으로 만드는 또 하나의 도구인 셈이며, 앞서 살펴봤듯 비동기 데이터 같은 더 복잡한 제어 흐름을 다룰 때도 자주 필요하다.

## 정리

- **조건부 렌더링**은 state나 props 같은 정보에 따라 서로 다른 JSX를 렌더링해야 할 때 사용한다.
- 초기 상태를 `null`이 아니라 빈 배열(`[]`)로 두면, 데이터가 도착하기 전에 목록을 필터링/매핑해도 애플리케이션이 깨지지 않는다.
- `isLoading` 상태로 데이터 로딩 여부를, `isError` 상태로 에러 발생 여부를 각각 추적할 수 있다.
- JSX 안에서 if-else 문은 사용할 수 없으므로, 두 가지 중 하나를 렌더링할 때는 **삼항 연산자**(`condition ? A : B`)를, 렌더링하거나 아무것도 하지 않을 때는 **논리 `&&` 연산자**(`condition && A`)를 사용한다.

## Q&A

- **Q. 가짜 API에서 데이터를 가져오기 전, 빈 `stories`에 대해 조건부 렌더링이 필요하지 않았던 이유는 무엇인가?**
  - A. `stories`는 `List` 컴포넌트에서 `map()` 메서드로 목록으로 매핑된다. 목록을 매핑할 때 `map()` 메서드는 각 아이템마다 변환된 결과(여기서는 JSX)를 반환하는데, 목록에 아이템이 없으면 `map()`은 아무것도 반환하지 않는다. 따라서 여기서는 조건부 렌더링이 필요 없다.
- **Q. `stories`의 초기 상태를 `[]` 대신 `null`로 설정했다면 어떻게 됐을까?**
  - A. 그렇다면 `List` 컴포넌트에서 조건부 렌더링이 필요했을 것이다. `null`에 대해 `map()`을 호출하면 예외가 발생하기 때문이다.
- **Q. `useState`를 사용해서 state에 따라 콘텐츠를 조건부로 렌더링하려면 어떻게 하는가?**
  - A. JSX 안에서 state 값을 기준으로 조건문(예: if 문이나 삼항 연산자)을 사용한다.
- **Q. `useState`(또는 다른 훅들)를 조건문이나 반복문 안에서 사용할 수 있는가?**
  - A. 아니다. 훅은 조건문이나 반복문 안이 아니라, 함수 컴포넌트의 최상위 레벨에서 사용해야 한다.
- **Q. 리액트에서 비동기 데이터를 다룰 때 조건부 렌더링이 자주 사용되는 이유는 무엇인가?**
  - A. 조건부 렌더링은 비동기 데이터의 상태에 따라 UI 표시를 관리해서, 필요에 맞게 로딩 인디케이터나 실제 콘텐츠를 보여주는 데 도움이 되기 때문이다.
- **Q. 리액트에서 비동기 데이터를 기다리는 동안 로딩 상태는 어떻게 처리하는가?**
  - A. 조건부 렌더링이나 상태 변수를 사용해서 로딩 상태를 관리하며, 데이터를 가져오고 있음을 사용자에게 알려준다.
- **Q. 리액트에서 비동기 데이터 페칭 중 에러를 처리할 수 있는가?**
  - A. 가능하다. `try...catch` 블록이나 `Promise`의 `.catch()`와 같은 에러 처리 메커니즘을 구현해서 데이터 페칭 중 발생하는 에러를 관리할 수 있다.
