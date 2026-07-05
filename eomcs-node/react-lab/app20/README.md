# app20. 리액트 컴포넌트 조합

## 개요

리액트 애플리케이션은 본질적으로 트리 형태로 배치된 수많은 리액트 컴포넌트의 집합이다. JSX에서 컴포넌트를 엘리먼트로 초기화하는 방법을 배울 때, 다른 HTML 엘리먼트와 똑같은 방식으로 컴포넌트를 사용할 수 있다는 것을 확인했다. 그런데 지금까지는 컴포넌트를 항상 **자체 종료(self-closing) 태그**로만 사용해왔다.

리액트 엘리먼트에도 여는 태그와 닫는 태그를 따로 쓸 수 있다면 어떨까? 여기서 등장하는 개념이 바로 **컴포넌트 조합(component composition)**이다.

> Java에 비유하면, 메서드에 값을 인자로 넘기는 것과 별개로, 특정 인터페이스를 구현한 객체(예: `Runnable`)를 통째로 넘겨서 실행 시점에 그 내용을 채워 넣는 것과 비슷하다. 리액트의 `children`은 컴포넌트의 여는 태그와 닫는 태그 "사이"에 무엇을 채울지를 호출하는 쪽에서 자유롭게 결정하게 해준다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
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

## 실습

### 실습 1. `label` prop 대신 여는 태그와 닫는 태그 사이에 콘텐츠 넣기

`InputWithLabel`을 사용하는 쪽에서, `label` prop을 넘기는 대신 컴포넌트의 여는 태그와 닫는 태그 사이에 "Search:" 텍스트를 직접 써보자.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <InputWithLabel
        id="search"
        value={searchTerm}
        onInputChange={handleSearch}
      >
        Search:
      </InputWithLabel>

      {/* ... */}
    </div>
  );
};
```

### 실습 2. `children` prop으로 받아서 렌더링하기

`InputWithLabel` 컴포넌트 안에서는 이렇게 태그 사이에 전달된 내용을 리액트의 **`children`** prop을 통해 그대로 접근할 수 있다. `label` prop을 없애고, 그 자리에 `children`을 사용하도록 고친다.

```jsx
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

이제 리액트 컴포넌트의 엘리먼트도 네이티브 HTML 엘리먼트와 비슷하게 동작한다. 컴포넌트의 여는 태그와 닫는 태그 사이에 전달된 모든 것은 컴포넌트 내부에서 `children`으로 접근하고 렌더링할 수 있다.

### 실습 3. 문자열이 아닌 리액트 엘리먼트를 `children`으로 전달하기

`children`으로 전달할 수 있는 것은 문자열만이 아니다. 컴포넌트 안쪽에서 무엇을 렌더링할지 바깥쪽에서 좀 더 자유롭게 결정하고 싶을 때, HTML 엘리먼트로 감싼 콘텐츠를 그대로 넘길 수도 있다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <InputWithLabel
        id="search"
        value={searchTerm}
        onInputChange={handleSearch}
      >
        <strong>Search:</strong>
      </InputWithLabel>

      {/* ... */}
    </div>
  );
};
```

`children` prop 덕분에 리액트 컴포넌트를 서로 조합해서 쓸 수 있다. 여기서는 일반 문자열과, `<strong>` 태그로 감싼 문자열을 `children`으로 넘겨봤지만, 이게 끝이 아니다. 리액트 엘리먼트 자체를 `children`으로 전달할 수도 있으니, 더 복잡한 구조의 컴포넌트도 얼마든지 조합할 수 있다.

## 정리

- **컴포넌트 조합**은 컴포넌트를 자체 종료 태그가 아니라, 여는 태그와 닫는 태그를 가진 형태로 사용해서 그 사이에 콘텐츠를 채워 넣는 방식이다.
- 태그 사이에 전달된 내용은 컴포넌트 내부에서 리액트가 기본으로 제공하는 **`children`** prop을 통해 접근할 수 있다.
- `label`처럼 이름이 정해진 prop 대신 `children`을 사용하면, 컴포넌트를 사용하는 쪽에서 내부에 무엇을 렌더링할지 더 자유롭게 결정할 수 있다.
- `children`에는 문자열뿐 아니라 HTML 엘리먼트, 나아가 다른 리액트 컴포넌트까지 전달할 수 있어서, 컴포넌트를 서로 조합하는 강력한 수단이 된다.

## Q&A

- **Q. 리액트 컴포넌트에서 "children"이란 무엇을 가리키는가?**
  - A. 컴포넌트의 여는 태그와 닫는 태그 사이에 놓인 콘텐츠를 가리킨다.
- **Q. 리액트 컴포넌트의 "children"에 접근하고 렌더링하려면 어떻게 하는가?**
  - A. `props.children`을 사용해서 컴포넌트 안에 전달된 콘텐츠를 접근하고 렌더링한다.
- **Q. 리액트 컴포넌트는 여러 개의 children을 가질 수 있는가?**
  - A. 가능은 하지만, 보통은 `<MyComponent slotOne={<em>1</em>} slotTwo={<em>2</em>} />`처럼 별도의 속성을 사용하고, 컴포넌트 안에서 `props.slotOne`, `props.slotTwo`로 각각 접근한다.
- **Q. 다른 컴포넌트에 리액트 컴포넌트를 "children"으로 전달할 수 있는가?**
  - A. 가능하다. 리액트 컴포넌트를 다른 컴포넌트의 "children"으로 전달할 수 있으며, 이것이 조합성(composability)을 가능하게 한다.
- **Q. 리액트의 `React.Children` 유틸리티는 어떤 목적으로 쓰이는가?**
  - A. `React.Children` 유틸리티는 컴포넌트의 "children"을 다루고 조작하기 위한 여러 메서드를 제공한다.
- **Q. 리액트 컴포넌트에서 각 child를 순회하며 조작하려면 어떻게 하는가?**
  - A. `React.Children.map`이나 `React.Children.forEach`를 사용해서 컴포넌트의 각 child를 순회하며 작업을 수행한다.
- **Q. 리액트 컴포넌트가 "children" 없이 존재할 수 있는가?**
  - A. 가능하다. 여는 태그와 닫는 태그 사이에 아무 콘텐츠도 넣지 않으면, 컴포넌트는 children 없이 존재할 수 있다.
- **Q. "children"과 다른 props의 차이는 무엇인가?**
  - A. "children"은 태그 사이에 놓인 콘텐츠를 가리키는 반면, 다른 props는 컴포넌트에 전달되는 키-값 쌍이라는 차이가 있다.
