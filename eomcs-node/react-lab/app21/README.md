# app21. 명령형 리액트

## 개요

**명령형 프로그래밍(imperative programming)**은 프로그램이 어떤 작업을 수행해야 하는지, 그 단계 하나하나를 명시적으로 지시하는 방식이다. 반면 **선언형 프로그래밍(declarative programming)**은 모든 절차적 단계를 나열하지 않고, 원하는 결과(outcome)만 명시하는 데 집중한다. 일반적으로 선언형 코드가 더 간결하고, 읽기 쉽고, 유지보수하기 좋다고 여겨진다.

리액트는 선언형 프로그래밍 접근 방식을 취한다. UI를 갱신하기 위해 DOM을 직접 조작하는 대신, 개발자는 원하는 UI 상태를 선언하기만 하면 리액트가 알아서 렌더링 과정을 처리해준다. 이런 선언형 접근 방식은 DOM 조작의 복잡성을 감춰주면서 코드의 가독성과 확장성을 높여주고, 더 높은 추상화 수준에서 동적인 사용자 인터페이스를 만들 수 있게 해준다.

JSX를 사용할 때 우리는 리액트에게 "어떤 엘리먼트를 보고 싶은지"를 말할 뿐, "그 엘리먼트를 어떻게 만들지"는 말하지 않는다. 상태(state)를 위한 훅을 사용할 때도 "무엇을 상태값으로 관리하고 싶은지"만 말할 뿐, "어떻게 관리할지"는 말하지 않는다. 이벤트 핸들러를 구현할 때도 리스너를 명령형으로 직접 등록할 필요가 없다.

```js
// 명령형 자바스크립트 + DOM API
element.addEventListener('click', () => {
  // 뭔가를 한다
});

// 선언형 리액트
const App = () => {
  const handleClick = () => {
    // 뭔가를 한다
  };

  return (
    <button type="button" onClick={handleClick}>
      Click
    </button>
  );
};
```

> Java에 비유하면, 선언형 프로그래밍은 스트림 API로 "무엇을 원하는지"만 기술하는 것(`list.stream().filter(...).map(...)`)과 비슷하고, 명령형 프로그래밍은 `for` 루프를 돌며 "어떻게 할지"를 한 단계씩 직접 지시하는 것과 비슷하다.

하지만 모든 것을 선언형으로 처리하고 싶지 않은 경우도 있다. 예를 들어 사이드 이펙트로서, 렌더링된 엘리먼트에 명령형으로 접근해야 할 때가 있다.

- DOM API를 통한 엘리먼트의 읽기/쓰기 접근
  - 엘리먼트의 너비/높이 측정(읽기)
  - 입력 필드의 포커스 상태 설정(쓰기)
- 더 복잡한 애니메이션 구현
  - 트랜지션 설정
  - 트랜지션 오케스트레이션
- 서드파티 라이브러리 통합
  - D3는 대표적인 명령형 차트 라이브러리다

## `App` 컴포넌트

이전 실습들에서 만든 아래 `InputWithLabel` 컴포넌트에서 시작한다.

```jsx
// src/App.jsx
const InputWithLabel = ({
  id,
  value,
  type = 'text',
  onInputChange,
  children,
}) => (
  <>
    <label htmlFor={id}>{children}</label>
    &nbsp;
    <input
      id={id}
      type={type}
      value={value}
      onChange={onInputChange}
    />
  </>
);
```

## 실습

리액트에서 명령형 프로그래밍은 장황하고 직관에 어긋나는 면이 있어서, 여기서는 입력 필드에 포커스를 명령형으로 설정하는 짧은 예제 하나만 살펴본다. 참고로 같은 결과는 입력 필드의 `autoFocus` 속성을 사용하는 선언형 방식으로도 얻을 수 있다.

### 실습 1. (선언형) `autoFocus` 속성으로 포커스 주기

```jsx
const InputWithLabel = ({ /* ... */ }) => (
  <>
    <label htmlFor={id}>{children}</label>
    &nbsp;
    <input
      id={id}
      type={type}
      value={value}
      autoFocus
      onChange={onInputChange}
    />
  </>
);
```
- autoFocus는 autoFocus={true}의 축약형이다.
- true로 설정되는 모든 속성은 이런 축약형을 쓸 수 있다.

이 방식은 동작하긴 하지만, 재사용 가능한 컴포넌트가 여러 번 렌더링되는 경우에는 문제가 된다. 예를 들어 `App` 컴포넌트가 `InputWithLabel`을 두 번 렌더링한다면, 가장 마지막에 렌더링된 컴포넌트만 `autoFocus`를 받게 된다.

### 실습 2. `isFocused` prop으로 자동 포커스 여부를 외부에서 결정하기

`InputWithLabel`은 이미 재사용 가능한 컴포넌트이므로, 개발자가 입력 필드에 자동 포커스를 줄지 말지를 직접 결정할 수 있도록 전용 prop을 추가하자.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <InputWithLabel
        id="search"
        value={searchTerm}
        isFocused
        onInputChange={handleSearch}
      >
        <strong>Search:</strong>
      </InputWithLabel>

      {/* ... */}
    </div>
  );
};
```

- 여기서도 `isFocused`라는 속성만 적는 것은 `isFocused={true}`와 같은 의미다. 

컴포넌트 내부에서는 이 새로운 prop을 입력 필드의 `autoFocus` 속성에 그대로 연결한다.

```jsx
const InputWithLabel = ({
  id,
  value,
  type = 'text',
  onInputChange,
  isFocused,
  children,
}) => (
  <>
    <label htmlFor={id}>{children}</label>
    &nbsp;
    <input
      id={id}
      type={type}
      value={value}
      autoFocus={isFocused}
      onChange={onInputChange}
    />
  </>
);
```

이 기능은 정상적으로 동작하지만, 여전히 **선언형** 구현이다. 우리는 리액트에게 "무엇을" 할지 말하고 있을 뿐, "어떻게" 할지는 말하지 않는다. 선언형 방식으로도 충분히 구현할 수 있지만(오히려 이 방식이 권장된다), 이번에는 일부러 명령형 접근 방식으로 리팩터링해보자. 즉, 입력 필드 엘리먼트가 렌더링된 뒤 DOM API의 `focus()` 메서드를 프로그램적으로 직접 실행하려는 것이다.

### 실습 3. `useRef`와 `useEffect`로 명령형으로 포커스 주기

```jsx
const InputWithLabel = ({
  id,
  value,
  type = 'text',
  onInputChange,
  isFocused,
  children,
}) => {
  // (A)
  const inputRef = React.useRef();

  // (C)
  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      // (D)
      inputRef.current.focus();
    }
  }, [isFocused]);

  return (
    <>
      <label htmlFor={id}>{children}</label>
      &nbsp;
      {/* (B) */}
      <input
        ref={inputRef}
        id={id}
        type={type}
        value={value}
        onChange={onInputChange}
      />
    </>
  );
};
```

주석으로 표시한 각 단계는 다음과 같은 의미를 가진다.

- **(A)** 먼저, 리액트의 `useRef` 훅으로 ref를 하나 만든다. 이 ref 객체는 컴포넌트가 살아있는 동안 계속 유지되는 값이며, `current`라는 프로퍼티를 가지고 있는데 ref 객체 자체와 달리 이 `current` 값은 바뀔 수 있다.
- **(B)** 그다음, 이 ref를 엘리먼트의 JSX 예약 속성인 `ref`에 전달한다. 그러면 엘리먼트 인스턴스가 변경 가능한 `current` 프로퍼티에 할당된다.
- **(C)** 그다음, 리액트의 `useEffect` 훅으로 컴포넌트의 생명주기에 관여해서, 컴포넌트가 렌더링될 때(혹은 의존성이 바뀔 때) 엘리먼트에 포커스를 준다.
- **(D)** 마지막으로, ref가 엘리먼트의 `ref` 속성에 전달돼 있으므로 `current` 프로퍼티를 통해 그 엘리먼트에 접근할 수 있다. `isFocused`가 설정돼 있고 `current`가 존재할 때만, 사이드 이펙트로서 `focus()`를 프로그램적으로 실행한다.

**이것이 리액트에서 선언형 방식을 명령형 방식으로 바꾸는 예이다.** 이번 경우에는 선언형과 명령형 중 어느 쪽이든 사용할 수 있다는 것을 직접 확인했다. 하지만 항상 선언형 접근 방식을 쓸 수 있는 것은 아니므로, 필요할 때는 언제든 명령형 접근 방식을 사용하면 된다.

## 정리

- **선언형 프로그래밍**은 "무엇을" 원하는지 기술하고, **명령형 프로그래밍**은 "어떻게" 할지 단계별로 지시한다. 리액트는 기본적으로 선언형 접근 방식을 취한다.
- 엘리먼트의 크기 측정, 포커스 제어, 복잡한 애니메이션, 서드파티 라이브러리 연동처럼 DOM에 직접 접근해야 하는 경우에는 명령형 접근이 필요할 수 있다.
- 리액트의 **`useRef`** 훅은 컴포넌트가 살아있는 동안 유지되는 변경 가능한 `current` 프로퍼티를 가진 ref 객체를 만들어준다.
- ref를 엘리먼트의 `ref` 속성에 연결하면, `current` 프로퍼티를 통해 실제 DOM 엘리먼트에 접근해서 `focus()` 같은 명령형 DOM API를 호출할 수 있다.

## Q&A

- **Q. 리액트에서 `useRef`란 무엇인가?**
  - A. 변경 가능한 값을 담을 수 있고 렌더링 사이에도 유지되는, `ref`라는 변경 가능한 객체를 제공하는 리액트 훅이다.
- **Q. `useRef`는 `useState`와 어떻게 다른가?**
  - A. `useState`와 달리 `useRef`는 값이 바뀌어도 리렌더링을 유발하지 않는다. 주로 렌더링에 영향을 주지 않는 변경 가능한 값에 사용한다.
- **Q. `useRef`는 렌더링 사이에 유지되는 변경 가능한 값을 담는 데 사용할 수 있는가?**
  - A. 가능하다. `useRef`의 주된 목적이 바로 리렌더링을 유발하지 않으면서 렌더링 사이에 유지되는 값을 담는 것이다.
- **Q. 리액트에서 `useRef`의 대표적인 사용 사례는 무엇인가?**
  - A. DOM 엘리먼트에 대한 참조를 담아 접근하고 상호작용하는 것이 대표적인 사용 사례다.
- **Q. `useRef`로 리렌더링을 유발할 수 있는가?**
  - A. 아니다. `useRef`로 만든 ref의 값을 바꿔도 리렌더링은 일어나지 않는다.
- **Q. `useRef`는 함수 호출 사이에 값을 유지하는 데 사용할 수 있는가?**
  - A. 가능하다. `useRef`의 값은 렌더링 사이에 유지되므로, 리렌더링을 유발하지 않으면서 함수 호출 사이에 값을 유지하는 데 적합하다.
- **Q. `useRef`로 만든 ref의 현재 값에는 어떻게 접근하는가?**
  - A. `myRef.current`로 `useRef`가 만든 ref의 현재 값에 접근한다.
