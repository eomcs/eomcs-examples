# app02. 리액트 컴포넌트 만나기

## 개요

모든 리액트 애플리케이션은 **리액트 컴포넌트**를 기반으로 만들어진다. 이번 장에서는 `src/App.jsx` 파일에 있는 첫 번째 리액트 컴포넌트를 살펴본다. Vite 버전에 따라 파일 내용은 조금씩 다를 수 있지만, 구조는 동일하다.

## `App` 컴포넌트

Vite로 프로젝트를 생성하면 `src/App.jsx`에 다음과 비슷한 코드가 만들어져 있다.

```jsx
// src/App.jsx
import { useState } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
```

## 실습

### 실습 1. 불필요한 보일러플레이트 제거하기

처음 시작할 때 방해되는 코드를 걷어내고 가볍게 시작하자.

```jsx
// src/App.jsx
function App() {
  return (
    <div>
      <h1>Hello React</h1>
    </div>
  );
}

export default App;
```

**1) 컴포넌트는 그냥 자바스크립트 함수다**

- `App` 컴포넌트는 특별한 문법이 아니라 **일반 자바스크립트 함수**다.
- 다만 일반 함수와 달리 이름을 **PascalCase**(예: `App`, `HelloWorld`)로 짓는다. 이름이 **대문자로 시작하지 않으면 리액트는 이것을 컴포넌트로 인식하지 않는다**.
- 이런 형태의 컴포넌트를 **함수 컴포넌트(Function Component)** 라고 부른다. 함수 컴포넌트는 요즘 리액트에서 컴포넌트를 작성하는 표준적인 방식이며, 이 외에 클래스 컴포넌트 같은 다른 방식도 존재한다.

> Java에 비유하면, 함수 컴포넌트는 `public static void main(String[] args)` 같은 "실행 가능한 메서드"에 가깝다. 다만 매번 값을 반환하는 순수 함수이고, 반환값이 화면(UI)이라는 점이 다르다.

**2) 아직 매개변수가 없다**

- 지금의 `App` 함수는 함수 시그니처에 매개변수가 없다.
- 이후 한 컴포넌트가 다른 컴포넌트로 정보를 전달하는 방법(**Props**)을 배우는데, 이때 전달된 값들이 바로 함수의 매개변수로 전달된다. (Java의 메서드 파라미터와 유사하다.)

**3) HTML처럼 생긴 코드를 반환한다**

- `App` 컴포넌트는 HTML처럼 생긴 코드를 `return`한다.
- 이 새로운 문법을 **JSX**라고 부르며(다음 절에서 자세히 다룬다), 자바스크립트와 HTML을 결합해서 브라우저에 동적이고 인터랙티브한 화면을 그릴 수 있게 해준다.

### 실습 2. 함수 몸통(body)에 구현 내용 넣기

다른 자바스크립트 함수와 마찬가지로, 함수 컴포넌트도 함수 시그니처와 `return`문 사이에 구현 로직을 넣을 수 있다.

```jsx
function App() {
  // 이 사이에 원하는 로직을 작성할 수 있다

  return (
    <div>
      <h1>Hello React</h1>
    </div>
  );
}

export default App;
```

함수 몸통 안에서 정의한 변수는 이 함수가 실행될 때마다 매번 새로 정의된다. 자바스크립트 함수에 익숙하다면 낯설지 않은 개념이다.

```jsx
function App() {
  const title = 'React';

  return (
    <div>
      <h1>Hello React</h1>
    </div>
  );
}

export default App;
```

- **렌더링**: 컴포넌트 함수는 브라우저에 컴포넌트가 처음 표시될 때 실행된다.
- **리렌더링**: 컴포넌트 함수는 상태가 바뀌어 화면에 다른 내용을 그려야 할 때마다 다시 실행된다.

### 실습 3. 변수를 컴포넌트 밖으로 꺼내기

**함수가 실행될 때마다 변수를 다시 정의하고 싶지 않다면**, 아래처럼 변수를 컴포넌트 **바깥**에 정의할 수도 있다. `title`이 함수 컴포넌트 내부의 어떤 정보(예: 매개변수)에도 의존하지 않는다면 바깥으로 옮겨도 무방하다. 이렇게 하면 함수가 호출될 때마다가 아니라 **딱 한 번만** 정의된다.

```jsx
const title = 'React';

function App() {
  return (
    <div>
      <h1>Hello React</h1>
    </div>
  );
}

export default App;
```

**변수의 위치를 결정하는 기준:**

> 변수(또는 함수)가 함수 컴포넌트 내부의 어떤 것(예: 매개변수)도 필요로 하지 않는다면, 컴포넌트 **바깥**에 정의해서 함수 호출마다 다시 정의되는 것을 피한다.

| 위치 | 재정의 시점 | 사용 조건 |
| --- | --- | --- |
| 컴포넌트 안 | 렌더링/리렌더링마다 매번 | 컴포넌트 내부 값(props, state 등)에 의존할 때 |
| 컴포넌트 밖 | 최초 1회 | 컴포넌트 내부 값에 의존하지 않을 때 |
