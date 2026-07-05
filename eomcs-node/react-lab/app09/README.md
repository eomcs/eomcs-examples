# app09. JSX의 핸들러 함수

## 개요

리액트로 애플리케이션을 개발하다 보면 언젠가는 사용자 인터랙션을 구현해야 하는 순간이 온다. 이번 실습에서는 사용자와의 **상호작용(interaction)** 하는 방법에 대해 배운다. `<input>` 엘리먼트를 가지고 있는 `Search` 컴포넌트를 가지고 사용자와의 상호작용을 구현해보자.

순수 HTML/자바스크립트에서는 DOM 노드에 `addEventListener()`를 호출해서 이벤트 핸들러를 등록한다. 리액트에서는 JSX 안에서 **선언적인 방식**으로 핸들러를 추가한다.

> Java 웹 개발에 비유하면, 서블릿에서 `request.getParameter()`로 폼 값을 읽던 것과 비슷한 일을, 리액트에서는 `onChange` 핸들러의 `event.target.value`로 실시간(입력할 때마다)으로 읽는다고 생각하면 된다.

## `App` 컴포넌트

이전 실습에서 만든 아래 `Search` 컴포넌트에서 시작한다.

```jsx
// src/App.jsx
const Search = () => (
  <div>
    <label htmlFor="search">Search: </label>
    <input id="search" type="text" />
  </div>
);
```

## 실습

### 실습 1. `Search` 컴포넌트를 블록 바디로 되돌리기

`return`문 앞에 구현 내용을 추가할 수 있도록, 간결한 바디(concise body)로 되어 있던 `Search` 컴포넌트를 다시 블록 바디(block body)로 리팩터링한다.

```jsx
const Search = () => {
  // 중간에 구현 내용을 추가할 수 있다

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
};
```

### 실습 2. `onChange` 이벤트 핸들러 등록하기

입력 필드의 `change` 이벤트를 처리할 함수를 정의한다. 함수 선언이든 화살표 함수 표현식이든 상관없다. 리액트에서는 이런 함수를 **(이벤트) 핸들러(handler)**라고 부른다. 이 함수를 HTML `input` 엘리먼트의 `onChange` 속성(JSX 이름 지정 속성)에 전달한다.

```jsx
const Search = () => {
  const handleChange = (event) => {
    // 합성 이벤트(synthetic event)
    console.log(event);
    // 타깃(여기서는 input 엘리먼트)의 값
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

- 브라우저에서 애플리케이션을 열고 개발자 도구의 "Console" 탭을 연 상태에서 입력 필드에 타이핑해보면, 타이핑할 때마다 로그가 찍히는 것을 볼 수 있다. 
- 여기서 보이는 것은 자바스크립트 객체인 **합성 이벤트(synthetic event)**와 입력 필드 내부의 값이다.

## 합성 이벤트(SyntheticEvent)란?

**리액트의 합성 이벤트**는 **브라우저의 네이티브 이벤트를 감싼 래퍼(wrapper)** 다. 리액트는 원래 SPA를 위한 라이브러리로 시작했기 때문에, 브라우저의 기본 동작을 막기 위한 확장된 기능이 이벤트에 필요했다. 예를 들어 순수 HTML에서는 폼을 제출(submit)하면 페이지가 새로고침되지만, 리액트에서는 이 새로고침을 막아야(그리고 그다음 동작을 개발자가 직접 제어해야) 하는 경우가 많다.

> 네이티브 브라우저 이벤트에 직접 접근하고 싶다면 `event.nativeEvent`를 사용할 수 있다. (실무에서 이 방법이 필요한 경우는 매우 드물다.)

## 핸들러 함수를 전달할 때 주의할 점

JSX 핸들러 속성에는 **함수 자체**를 전달해야 한다. 함수를 실행한 **결과값**을 전달하면 안 된다(단, 그 결과값이 다시 함수인 경우는 예외). 이는 리액트 초보자가 흔히 저지르는 실수이자 버그의 원인이다.

```jsx
// handleChange가 함수를 반환하지 않는 일반 함수라면
// 이렇게 하면 안 된다 (렌더링될 때마다 즉시 실행됨)
<input onChange={handleChange()} />

// 이렇게 해야 한다 (이벤트가 발생할 때 실행됨)
<input onChange={handleChange} />
```

## 정리

JSX 안에서 HTML과 자바스크립트가 얼마나 자연스럽게 어우러지는지 다시 확인할 수 있다.

- 자바스크립트 변수를 화면에 표시할 수 있다. (예: `<span>{title}</span>`)
- 자바스크립트 원시값을 HTML 속성에 전달할 수 있다. (예: `<a href={url}>`)
- 사용자 인터랙션을 처리하기 위해 함수를 HTML 엘리먼트의 속성에 전달할 수 있다. (예: `<input onChange={handleChange} />`)

리액트 애플리케이션을 개발하면서 HTML과 자바스크립트를 JSX 안에서 섞어 쓰는 일은 앞으로 계속 반복하게 될 가장 기본적인 작업이다.

## Q&A

- **Q. 리액트에서 이벤트 핸들러는 어떻게 정의하는가?**
  - A. 이벤트를 처리하는 함수를 만든다. 예: `function handleClick() {...}`
- **Q. JSX에서 이벤트 핸들러는 어떻게 연결하는가?**
  - A. 알맞은 속성(attribute)을 사용한다. 예: `onClick={handleClick}`
- **Q. 이벤트 핸들러 함수의 이름은 보통 어떻게 짓는가?**
  - A. 함수 이름 앞에 "handle"을 붙이고 뒤에 이벤트 이름을 붙인다. 예: 클릭 이벤트라면 `handleClick`
- **Q. JSX 안에서 화살표 함수를 이벤트 핸들러로 직접 사용할 수 있는가?**
  - A. 가능하다. 간결한 이벤트 핸들러를 작성할 때 흔히 쓰는 패턴이다.
- **Q. JSX에서 이벤트 핸들러에 인자를 전달하려면 어떻게 하는가?**
  - A. 화살표 함수로 감싸서 핸들러를 인자와 함께 호출한다. 예: `onClick={() => handleClick(arg)}`
- **Q. 이벤트 핸들러를 여러 엘리먼트에서 재사용할 수 있는가?**
  - A. 가능하다. 같은 종류의 이벤트라면 여러 엘리먼트에서 같은 핸들러를 재사용할 수 있다.
- **Q. 이벤트 핸들러의 `e.target` 속성은 어떤 역할을 하는가?**
  - A. 이벤트를 발생시킨 DOM 엘리먼트를 가리키며, 그 속성에 접근하거나 조작할 수 있게 해준다.
- **Q. 이벤트 핸들러에서 이벤트 객체는 어떻게 접근하는가?**
  - A. 핸들러 함수의 매개변수로 `(event)`를 받으면 된다. 예: `function handleClick(event) {...}`
- **Q. 이벤트 핸들러의 `event.preventDefault()`는 무엇을 하는가?**
  - A. 폼 제출이나 링크 이동 같은 이벤트의 기본 동작을 막는다.
- **Q. 이벤트 핸들러의 `event.stopPropagation()`은 어떤 역할을 하는가?**
  - A. 이벤트가 DOM 트리를 따라 위/아래로 전파되는 것을 막아, 부모나 자식 엘리먼트가 같은 이벤트를 처리하지 못하게 한다.
