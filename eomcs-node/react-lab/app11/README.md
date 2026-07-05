# app11. 리액트 State

## 개요

개발자는 Props를 임의로 변경(mutate)할 수 없다. Props는 부모에서 자식으로 정보를 전달하는 용도일 뿐이기 때문이다. 반면 **State**는 리액트에서 **변경 가능한(mutable) 데이터 구조**, 즉 상태를 가진 값(stateful value)을 다룬다. 이 값은 컴포넌트 안에서 **state**로 인스턴스화되고, Props를 통해 자식 컴포넌트로 전달될 수도 있지만, 상태를 변경하는 함수를 통해 직접 **변경(mutate)**될 수도 있다. state가 변경되면 그 state를 가진 컴포넌트와 그 자식 컴포넌트들이 **다시 렌더링(re-render)**된다.

| 개념 | 목적 |
| --- | --- |
| **Props** | 컴포넌트 계층 구조를 따라 정보를 아래로 전달 |
| **State** | 시간에 따라 변하는 정보를 관리 |

> Java에 비유하면, Props는 메서드 매개변수, State는 객체의 **인스턴스 필드(instance field)**에 가깝다. 다만 State는 값을 직접 대입(`this.field = value`)하는 대신, 리액트가 제공하는 **전용 setter 함수**를 통해서만 바꿀 수 있고, 그 값이 바뀌면 화면이 자동으로 다시 그려진다는 점이 다르다.

## `App` 컴포넌트

이전 실습에서 만든 아래 `Search` 컴포넌트에서 시작한다.

```jsx
// src/App.jsx
const Search = () => {
  const handleChange = (event) => {
    console.log(event.target.value);
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />
    </div>
  );
};
```

## 실습

### 실습 1. (동작하지 않는) 직관적인 접근 시도해보기

사용자가 `Search` 컴포넌트의 입력 필드에 타이핑하면, 그 값을 옆에 함께 보여주고 싶다고 해보자. 직관적으로는 다음과 같이 시도해볼 수 있다.

```jsx
const Search = () => {
  let searchTerm = '';

  const handleChange = (event) => {
    searchTerm = event.target.value;
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />

      <p>
        Searching for <strong>{searchTerm}</strong>.
      </p>
    </div>
  );
};
```

- 브라우저에서 확인해보면, 타이핑을 해도 입력 필드 아래에 값이 나타나지 않는다.
- 처음 화면을 출력한 후 `searchTerm` 변수의 값을 변경해도, **리액트는 화면을 다시 그리지 않는다.**

### 실습 2. `useState`로 상태 선언하기

리액트는 이런 상태값을 다루기 위해 **`useState`**라는 메서드를 제공한다.

```jsx
// src/App.jsx
import * as React from 'react';

// ...

const Search = () => {
  const [searchTerm, setSearchTerm] = React.useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  // ...
};
```
 
- `useState`를 사용하면 리액트에게 "시간에 따라 바뀌는 상태값을 갖고 싶다"고 알려주는 셈이다. 
- 이 상태값이 바뀌면, 리액트는 그 상태값을 갖고 있는 컴포넌트와 그 하위 컴포넌트들을 **다시 렌더링(re-render)**한다.

**useState:**

- `useState`는 **초기 상태(initial state)**를 인자로 받는다. (여기서는 빈 문자열 `''`)
- 반환값은 **배열(array)** 이며, 두 개의 원소를 갖는다.
  - 첫 번째 원소(`searchTerm`): **현재 상태(current state)**
  - 두 번째 원소(`setSearchTerm`): **상태를 갱신하는 함수(state updater function)**
  - 이 두 가지만 있으면 현재 상태를 읽고(read), 갱신(write)할 수 있다.

### 실습 3. 상태를 화면에 표시하기

`handleChange`에서 상태 갱신 함수를 호출하고, 최신 상태를 JSX에 표시한다.

```jsx
const Search = () => {
  const [searchTerm, setSearchTerm] = React.useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" onChange={handleChange} />

      <p>
        Searching for <strong>{searchTerm}</strong>.
      </p>
    </div>
  );
};
```

- 사용자가 입력 필드에 타이핑하면 `change` 이벤트가 핸들러를 실행시킨다. 
- 핸들러는 이벤트 타깃의 값(`event.target.value`)을 상태 갱신 함수로 넘겨 새로운 상태를 설정한다. 
- 그러면 **컴포넌트가 다시 렌더링(컴포넌트 함수가 다시 실행)되고**, 갱신된 상태(`searchTerm`)가 JSX에 반영되어 화면에 표시된다.

## 실습 4. 렌더링과 리렌더링 확인해보기

각 컴포넌트에 `console.log()`를 하나씩 넣어보자. 예를 들어 `App` 컴포넌트에는 `console.log('App renders')`, `List` 컴포넌트에는 `console.log('List renders')`처럼 말이다. 브라우저를 확인해보면 다음을 알 수 있다.

- **처음 렌더링될 때**는 모든 컴포넌트의 로그가 찍힌다.
- **입력 필드에 타이핑할 때**는 `Search` 컴포넌트의 로그만 찍힌다.

리액트는 상태가 바뀐 컴포넌트(그리고 그 하위 컴포넌트들)만 다시 렌더링한다.

- **초기 렌더링(initial rendering)**: 리액트 컴포넌트가 브라우저에 처음 표시될 때 일어난다.
- **리렌더링(re-rendering)**: 사용자 인터랙션 같은 사이드 이펙트가 발생해서 상태가 바뀌면, 그 상태를 관리하는 컴포넌트와 그 하위 컴포넌트 전체가 다시 렌더링된다.

## `useState`는 리액트 훅(Hook)이다

`useState`는 **리액트 훅(hook)**이라고 부르는 함수 중 하나다. 리액트가 제공하는 여러 훅 중 하나일 뿐이며, 이후 실습에서 다른 훅들도 다룬다. 지금 알아둘 점은 다음과 같다.

- 하나의 컴포넌트, 또는 여러 컴포넌트에서 `useState`를 원하는 만큼 여러 번 사용할 수 있다.
- state는 문자열(지금 예제처럼)뿐만 아니라 배열, 객체 등 더 복잡한 자바스크립트 데이터 구조도 될 수 있다.
- 브라우저 개발자 도구에서 로그가 두 번씩 찍히는 것을 볼 수도 있는데, 이는 `src/main.jsx`에 있는 리액트의 **`StrictMode`** 때문이다. `StrictMode`는 개발 환경에서만 동작하며, 잠재적인 문제를 미리 찾아내기 위해 추가 검사를 수행한다. 프로덕션 환경에서는 렌더링이 한 번만 일어난다.
- UI가 처음 렌더링될 때 각 컴포넌트의 `useState` 훅은 초기 상태로 초기화되고, 그 값을 현재 상태로 반환한다. 이후 상태 변경으로 리렌더링될 때는, `useState` 훅이 내부 **클로저(closure)** 에 저장해둔 가장 최근 상태 값을 사용한다. 리액트는 각 컴포넌트 옆에 상태 같은 정보를 저장해두는 객체를 메모리에 할당해두고, 그 컴포넌트가 더 이상 렌더링되지 않으면 자바스크립트의 가비지 컬렉션을 통해 정리한다.

## 리액트 훅(Hook)

리액트 훅이란,

- 함수 컴포넌트에서 리액트의 기능(state, lifecycle, context 등)을 사용할 수 있게 해주는 특수한 함수이다. 
- 즉 **함수형 컴포넌트에 React 기능을 "걸어(Hook)" 넣는 함수**이다.

훅이 등장하기 전,

- 클래스 컴포넌트를 작성해야만 상태 관리와 사이드 이펙트 처리를 할 수 있었다.
- 이 방식은:
  - 문법이 복잡하고
  - `this`를 이해해야 하며
  - 코드 재사용이 어렵다.
- 이를 해결하기 위해 2019년 2월 React v16.8에서 훅(Hook)이 도입되었다.

## 정리

- **Props**는 정보를 부모 → 자식으로 전달하고, **State**는 시간에 따라 변하는 정보를 관리한다.
- `const [state, setState] = React.useState(initialState);` 형태로 상태를 선언한다.
- 상태를 직접 변경(mutate)하지 말고, 반드시 상태 갱신 함수(`setState`)를 통해서만 변경한다.
- 상태가 바뀌면 해당 컴포넌트와 그 하위 컴포넌트들이 리렌더링된다.

## Q&A

- **Q. 리액트에서 `useState`란 무엇인가?**
  - A. 함수 컴포넌트가 상태를 관리하고 갱신할 수 있게 해주는 리액트 훅이다.
- **Q. 함수 컴포넌트에서 `useState`로 상태를 선언하려면 어떻게 하는가?**
  - A. `const [state, setState] = useState(initialState);` 형태로 작성한다.
- **Q. 리액트에서 무엇이 리렌더링을 유발하는가?**
  - A. 상태(state) 변경이나 props 갱신이 리렌더링을 유발할 수 있다.
- **Q. `useState`의 초기 상태(initial state)는 어떤 역할을 하는가?**
  - A. 상태 변수의 초기값을 설정하며, 최초 렌더링에서만 적용된다.
- **Q. `useState`로 상태는 어떻게 갱신하는가?**
  - A. `useState`가 반환하는 두 번째 값(상태 갱신 함수)을 사용한다.
- **Q. `setState`를 호출하면 즉시 리렌더링이 일어나는가?**
  - A. 아니다. 리액트는 성능을 위해 상태 갱신을 일괄 처리(batch)하고 비동기적으로 리렌더링을 수행한다.
- **Q. `useState`를 여러 번 호출하는 것과 객체 하나로 `useState`를 한 번 호출하는 것의 차이는 무엇인가?**
  - A. 여러 번 호출하면 서로 독립적인 상태 변수가 만들어지고, 객체 하나로 호출하면 여러 상태 값을 하나의 변수 안에서 관리하게 된다.
- **Q. `useState`로 얻은 상태 변수를 직접 변경(mutate)할 수 있는가?**
  - A. 아니다. 항상 `setState` 함수를 사용해서 불변적인 방식으로 상태를 갱신해야 한다.
- **Q. 상태를 갱신하면 항상 리렌더링이 일어나는가?**
  - A. 그렇다. `setState`로 상태를 갱신하면 해당 컴포넌트가 리렌더링된다.
