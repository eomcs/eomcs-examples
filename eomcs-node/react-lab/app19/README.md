# app19. 재사용 가능한 리액트 컴포넌트

## 개요

`Search` 컴포넌트를 다시 자세히 들여다보자. 세세한 구현 하나하나가 전부 "검색"이라는 특정 기능에 단단히 묶여 있다. 그런데 내부적으로 보면 이 컴포넌트는 그저 `label`과 `input` 하나로 이루어져 있을 뿐이다. 왜 굳이 하나의 도메인에만 이렇게 강하게 종속돼야 할까? 이런 좁은 결합 때문에 `Search` 컴포넌트는 애플리케이션의 다른 기능에는 재사용하기 어렵고, 검색과 무관한 작업에는 아예 쓸모가 없어진다.

게다가 `Search` 컴포넌트는 버그를 유발할 위험도 있다. 같은 페이지에 이 컴포넌트의 인스턴스가 여러 개 렌더링되면, `htmlFor`/`id` 조합이 중복돼서 사용자가 레이블을 클릭했을 때 포커스 동작이 꼬이게 된다. 이런 문제들을 해결하기 위해, `Search` 컴포넌트의 재사용성을 높여보자.

> Java에 비유하면, 특정 도메인 로직이 잔뜩 박혀 있는 클래스를 제네릭 타입 매개변수와 인터페이스로 일반화해서 여러 곳에서 재사용 가능한 유틸리티 클래스로 리팩터링하는 것과 비슷하다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 `Search` 컴포넌트에서 시작한다.

```jsx
// src/App.jsx
const Search = ({ search, onSearch }) => (
  <>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </>
);
```

## 실습

### 실습 1. `Search`를 범용 `InputWithLabel` 컴포넌트로 일반화하기

사실 `Search` 컴포넌트에는 진짜 "검색" 기능이 들어있지 않으므로, 다른 기능에서도 재사용할 수 있게 만드는 데는 큰 노력이 들지 않는다. `id`와 `label`을 동적인 props로 받고, 값과 콜백 핸들러의 이름을 더 일반적인 이름으로 바꾸고, 컴포넌트 이름 자체도 바꿔보자.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <InputWithLabel
        id="search"
        label="Search"
        value={searchTerm}
        onInputChange={handleSearch}
      />

      {/* ... */}
    </div>
  );
};

const InputWithLabel = ({ id, label, value, onInputChange }) => (
  <>
    <label htmlFor={id}>{label}</label>
    &nbsp;
    <input
      id={id}
      type="text"
      value={value}
      onChange={onInputChange}
    />
  </>
);
```

### 실습 2. `type` prop 추가해서 다른 입력 종류도 지원하기

지금은 완전히 재사용 가능해졌지만, `type="text"`가 고정돼 있어서 텍스트 입력만 지원한다. 숫자(`number`)나 전화번호(`tel`) 같은 다른 입력 타입도 지원하려면, `input`의 `type` 속성도 외부에서 받을 수 있어야 한다.

```jsx
const InputWithLabel = ({
  id,
  label,
  value,
  type = 'text',
  onInputChange,
}) => (
  <>
    <label htmlFor={id}>{label}</label>
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

- `App` 컴포넌트에서 `InputWithLabel`에 `type` prop을 넘기지 않으면, 함수 시그니처의 **기본 매개변수(default parameter)**인 `type = 'text'`가 대신 사용된다. 
- 즉 `type` prop 없이 `InputWithLabel`을 쓸 때마다 기본 타입은 `"text"`가 된다.

몇 가지 변경만으로 특정 기능에 특화된 `Search` 컴포넌트를 훨씬 더 재사용하기 좋은 `InputWithLabel` 컴포넌트로 바꿨다. 내부 구현의 이름을 일반화하고, 외부에서 설정할 수 있는 속성(props)을 늘려 다양한 상황에서 사용할 수 있도록 했다. 아직 다른 곳에서 이 컴포넌트를 실제로 재사용하고 있지는 않지만, 필요할 때 언제든 재사용할 수 있는 능력을 갖추게 됐다.

## 일반화 vs 특수화, 그 사이의 트레이드오프

컴포넌트를 얼마나 **일반화(generalization)**할지, 얼마나 **특수화(specialization)**할지는 항상 트레이드오프의 문제다. 이번 실습에서는 아주 특화된 컴포넌트(`Search`)를 일반화된 컴포넌트(`InputWithLabel`)로 바꿨다.

- **일반화된 컴포넌트**는 애플리케이션 여러 곳에서 재사용될 가능성이 높다.
- **특수화된 컴포넌트**는 하나의 특정 유스케이스에 대한 비즈니스 로직을 그대로 구현하므로, 재사용성이 거의 없다.

## 정리

- 컴포넌트가 특정 도메인 이름(예: `search`, `onSearch`)에 강하게 묶여 있으면 재사용이 어렵고, 같은 컴포넌트를 여러 번 쓸 때 `id` 중복 같은 버그의 위험도 생긴다.
- `id`, `label`, `value`, `onInputChange`처럼 일반화된 이름의 props로 API를 넓히면, 하나의 컴포넌트를 다양한 용도로 재사용할 수 있다.
- 함수 시그니처의 기본 매개변수(`type = 'text'`)를 활용하면 자주 쓰이는 기본값을 지정하면서도 필요할 때 값을 오버라이드할 수 있다.
- 컴포넌트 설계는 항상 일반화와 특수화 사이에서 균형을 맞추는 작업이다.

## Q&A

- **Q. 리액트에서 재사용성이 왜 중요한가?**
  - A. 재사용성은 컴포넌트를 애플리케이션 여러 곳에서 사용할 수 있게 해줌으로써 코드 효율성, 유지보수성, 일관성을 높여준다.
- **Q. 리액트 컴포넌트를 재사용 가능하게 만들려면 어떻게 해야 하는가?**
  - A. props를 통해 동작을 커스터마이즈할 수 있게 하고, 특정 기능에 강하게 결합되지 않도록 컴포넌트를 더 범용적으로 설계한다.
- **Q. 리액트 props는 재사용성에 어떻게 기여하는가?**
  - A. props는 컴포넌트를 동적으로 커스터마이즈할 수 있게 해줘서, 컴포넌트를 더 유연하고 재사용 가능하게 만들어준다.
- **Q. 재사용 가능한 컴포넌트도 내부 상태(state)를 가질 수 있는가?**
  - A. 가능하다. `useState` 같은 훅을 사용해서 재사용 가능한 컴포넌트도 내부 상태를 가질 수 있다.
- **Q. 컴포넌트 추상화는 재사용성에 왜 중요한가?**
  - A. 추상화는 불필요한 세부사항을 감춰서, 내부 복잡성을 드러내지 않고도 컴포넌트를 더 다재다능하고 재사용하기 쉽게 만들어준다.
- **Q. 리액트 애플리케이션의 모든 컴포넌트를 재사용 가능하게 만드는 것이 바람직한가?**
  - A. 재사용성은 유용하지만, 모든 컴포넌트가 재사용 가능할 필요는 없다. 하나의 목적만 가지고 애플리케이션에서 딱 한 번만 쓰이는 컴포넌트도 많다.
