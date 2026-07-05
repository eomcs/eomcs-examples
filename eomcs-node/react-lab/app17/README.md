# app17. 리액트 커스텀 훅 (심화)

## 개요

지금까지 리액트에서 가장 널리 쓰이는 두 훅, `useState`와 `useEffect`를 살펴봤다. `useState`는 변하는 값을 관리하는 데, `useEffect`는 컴포넌트 생명주기에 사이드 이펙트를 끼워 넣는 데 유용했다. 리액트가 제공하는 훅은 이 외에도 더 있지만, 이번 실습에서는 **커스텀 훅(custom hook)** — 즉 특정 요구사항에 맞춰 우리가 직접 만드는 훅 — 을 다룬다.

`useState`와 `useEffect`에 대한 이해를 바탕으로, 컴포넌트의 상태를 브라우저의 로컬 스토리지와 동기화해주는 새로운 커스텀 훅 **`useStorageState`**를 만들어본다.

> Java에 비유하면, 커스텀 훅은 여러 클래스에서 반복되는 로직을 **유틸리티 클래스**나 **템플릿 메서드**로 뽑아내는 것과 비슷하다. 다만 리액트에서는 그 결과물이 "값 + 값을 바꾸는 함수" 쌍을 반환하는 재사용 가능한 함수라는 점이 다르다.

## `App` 컴포넌트에서 사용할 모습 먼저 그려보기

`useStorageState`를 만들기 전에, `App` 컴포넌트에서 이 훅을 어떻게 사용하고 싶은지부터 정해보자.

```jsx
// src/App.jsx
const App = () => {
  const stories = [ /* ... */ ];

  const [searchTerm, setSearchTerm] = useStorageState('React');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    // ...
  );
};
```

이 커스텀 훅은 리액트 내장 `useState` 훅과 비슷한 방식으로 사용할 수 있다. 초기 상태를 인자로 받고, 상태 변수와 상태를 갱신하는 함수를 반환한다. 다만 내부적으로는 상태를 브라우저 로컬 스토리지와 동기화하는 역할까지 해준다. 위 코드에서 `App` 컴포넌트에는 더 이상 이전 실습에서 만든 로컬 스토리지 관련 코드가 보이지 않는다는 점에 주목하자. 그 로직을 이제 새 커스텀 훅으로 옮길 것이다.

## 실습

### 실습 1. `useStorageState` 함수 만들기

기존에 `App` 컴포넌트에 있던 `useState` + `useEffect` 로직을 그대로 새로운 함수로 옮긴다.

```jsx
// src/App.jsx
const useStorageState = () => {
  const [searchTerm, setSearchTerm] = React.useState(
    localStorage.getItem('search') || ''
  );

  React.useEffect(() => {
    localStorage.setItem('search', searchTerm);
  }, [searchTerm]);
};

const App = () => {
  // ...
};
```

지금까지는 이 커스텀 훅이 그저 `useState`와 `useEffect`를 감싸고 있는 함수일 뿐이다. 아직 초기 상태를 인자로 받지도, `App` 컴포넌트에 필요한 값을 반환하지도 않는다.

### 실습 2. 초기 상태를 인자로 받고, 값을 배열로 반환하기

```jsx
const useStorageState = (initialState) => {
  const [searchTerm, setSearchTerm] = React.useState(
    localStorage.getItem('search') || initialState
  );

  React.useEffect(() => {
    localStorage.setItem('search', searchTerm);
  }, [searchTerm]);

  return [searchTerm, setSearchTerm];
};
```

여기서 리액트 내장 훅들이 따르는 두 가지 관례(convention)를 그대로 따르고 있다.

- 모든 훅 이름 앞에 **`use`** 접두어를 붙인다.
- 반환값은 **배열**로 돌려준다.

### 실습 3. 재사용 가능하도록 일반화하기

커스텀 훅의 또 다른 목표는 **재사용성**이다. 지금은 훅 내부가 "검색"이라는 특정 도메인에 묶여 있는데, 더 범용적인 훅으로 만들려면 내부 이름들을 일반화해야 한다.

```jsx
const useStorageState = (initialState) => {
  const [value, setValue] = React.useState(
    localStorage.getItem('value') || initialState
  );

  React.useEffect(() => {
    localStorage.setItem('value', value);
  }, [value]);

  return [value, setValue];
};
```

이제 커스텀 훅 내부에서는 추상화된 이름인 `value`를 다룬다. `App` 컴포넌트에서 이 훅을 사용할 때는 배열 구조 분해를 이용해서 반환값에 `searchTerm`, `setSearchTerm`처럼 원하는 도메인 이름을 자유롭게 붙일 수 있다.

### 실습 4. 여러 번 재사용할 수 있도록 `key`를 인자로 받기

아직 문제가 하나 남아 있다. 이 커스텀 훅을 애플리케이션에서 두 번 이상 사용하면, 모두 로컬 스토리지의 같은 키(`'value'`)를 덮어쓰게 된다. 이를 해결하려면 **유동적인 key 값**을 인자로 받아야 한다. 이 `key`는 외부에서 주어지므로 바뀔 수 있다고 가정해야 하고, 따라서 `useEffect`의 의존성 배열에도 포함시켜야 한다. 그렇지 않으면 렌더링 사이에 `key`가 바뀌었을 때 사이드 이펙트가 오래된(stale) `key`로 실행될 수 있다.

> **useEffect 안에서 읽는 값은 모두 의존성 배열에 포함시켜야 한다.** 그렇지 않으면 렌더링 사이에 값이 바뀌었을 때, 오래된(stale) 값을 참조하게 된다. 

```jsx
const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(
    localStorage.getItem(key) || initialState
  );

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]); // value와 key를 useEffect 안에서 사용

  return [value, setValue];
};

const App = () => {
  // ...

  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  // ...
};
```

- 이제 `key`를 인자로 받으므로, 이 커스텀 훅을 애플리케이션 여러 곳에서 재사용할 수 있다. 
- 단, 첫 번째 인자로 넘기는 `key`가 서로 겹치지 않는 **고유한 식별자**여야 한다. 
- 같은 `key`를 여러 `useStorageState` 훅에서 함께 사용하면, 그 훅들은 로컬 스토리지의 같은 키/값 쌍을 공유하게 된다.

## 커스텀 훅이 주는 것

첫 번째 커스텀 훅을 만들어봤다. 커스텀 훅이 아직 낯설다면, 이전처럼 `App` 컴포넌트 안에서 `useState`와 `useEffect`를 직접 쓰는 방식으로 되돌려도 무방하다. 하지만 커스텀 훅을 알아두면 다음과 같은 새로운 가능성이 열린다.

- **사소하지 않은 구현 세부사항을 컴포넌트로부터 감출(캡슐화할) 수 있다.**
- **하나 이상의 리액트 컴포넌트에서 재사용할 수 있다.**
- 다른 훅들을 조합해서 만들 수도 있다.
- 심지어 외부 라이브러리로 오픈소스화할 수도 있다. 실제로 검색해보면 이미 수백 개의 재사용 가능한 리액트 훅이 공개되어 있다.

## 정리

- **커스텀 훅**은 `use`로 시작하는 이름을 가진, 리액트 훅을 활용하는 일반 함수다.
- 값을 배열로 반환하는 관례를 따르면, 사용하는 쪽에서 원하는 이름으로 구조 분해할 수 있다.
- 재사용성을 높이려면 훅 내부의 이름을 특정 도메인에 묶지 않고 일반화하고, 필요한 값(예: `key`)은 인자로 받는다.
- 외부에서 받은 값을 `useEffect` 안에서 사용한다면, 그 값도 의존성 배열에 포함시켜야 stale한 값을 참조하지 않는다.

## Q&A

- **Q. 리액트 커스텀 훅이란 무엇인가?**
  - A. 리액트 훅을 활용해서 함수 컴포넌트의 로직을 캡슐화하고 재사용할 수 있게 해주는 자바스크립트 함수다.
- **Q. 리액트에서 커스텀 훅은 어떻게 만드는가?**
  - A. 이름이 "use"로 시작하는 함수를 만들고, 그 안에서 기존 리액트 훅이나 다른 커스텀 훅을 사용한다.
- **Q. 커스텀 훅도 상태(state)를 가질 수 있는가?**
  - A. 가능하다. 커스텀 훅 안에서 `useState` 같은 훅을 사용할 수 있다.
- **Q. 커스텀 훅은 어떤 네이밍 컨벤션을 따라야 하는가?**
  - A. 리액트 훅과의 연관성을 나타내기 위해 이름 앞에 "use" 접두어를 붙여야 한다.
- **Q. 커스텀 훅은 매개변수를 받을 수 있는가?**
  - A. 가능하다. 매개변수를 받으면 커스텀 훅을 더 유연하고 커스터마이즈 가능하게 만들 수 있다.
- **Q. 커스텀 훅으로 여러 컴포넌트 간에 상태 관련 로직을 공유하려면 어떻게 하는가?**
  - A. 공유할 로직을 커스텀 훅으로 뽑아낸 다음, 여러 컴포넌트에서 그 훅을 사용하면 된다.
- **Q. 커스텀 훅이 컴포넌트의 props에 직접 접근할 수 있는가?**
  - A. 아니다. 커스텀 훅은 컴포넌트의 props에 직접 접근하지 못하며, 보통 필요한 데이터를 인자로 전달받는다.
- **Q. 하나의 컴포넌트에서 여러 개의 커스텀 훅을 사용할 수 있는가?**
  - A. 가능하다. 서로 다른 재사용 로직을 활용하기 위해 하나의 컴포넌트에서 여러 커스텀 훅을 사용할 수 있다.
- **Q. 커스텀 훅을 사용하는 핵심적인 이점은 무엇인가?**
  - A. 코드 재사용, 복잡한 로직의 추상화, 함수 컴포넌트의 유지보수성 향상을 꼽을 수 있다.
- **Q. 커스텀 훅도 데이터 페칭 같은 사이드 이펙트를 가질 수 있는가?**
  - A. 가능하다. `useEffect` 같은 훅을 활용해서 데이터 페칭 같은 작업을 캡슐화할 수 있다.
- **Q. 커스텀 훅은 상태 관리 용도로만 쓰이는가?**
  - A. 아니다. 상태를 관리할 수도 있지만, 사이드 이펙트나 각종 계산 로직 등 재사용 가능한 로직이라면 무엇이든 캡슐화할 수 있다.
