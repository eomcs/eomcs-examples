# app03. 리액트 JSX

## 개요

리액트 컴포넌트가 반환하는 것은 모두 브라우저에 표시된다. 지금까지는 `App` 컴포넌트가 HTML만 반환했지만, 실제로 그 반환값은 HTML을 닮았을 뿐만 아니라 자바스크립트와 섞어서 쓸 수도 있다. 이 문법을 **JSX(JavaScript XML)**라고 부르며, HTML과 자바스크립트를 강력하게 결합해준다.

> Java/JSP에 비유하면, JSX는 JSP나 Thymeleaf 같은 **템플릿 엔진**과 비슷한 역할(HTML 안에 동적인 값 끼워 넣기)을 하지만, 별도의 템플릿 문법(`${...}`, `<c:forEach>` 등) 대신 **자바스크립트 문법을 그대로** 사용한다는 점이 다르다.

## `App` 컴포넌트

이전 실습에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
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

## 실습

### 실습 1. 변수를 JSX에 표시하기

`title` 변수를 화면에 표시해보자. **중괄호 `{ }`** 안에 자바스크립트 표현식을 넣으면 JSX에 값을 끼워 넣을 수 있다.

```jsx
const title = 'React';

function App() {
  return (
    <div>
      <h1>Hello {title}</h1>
    </div>
  );
}

export default App;
```

`npm run dev`로 애플리케이션을 실행(또는 계속 실행 중인지 확인)하고 브라우저를 확인하면 "Hello React"가 표시된다. 소스 코드에서 `title` 값을 바꾸면 브라우저 화면도 자동으로 바뀐다.

- 이렇게 소스 코드를 바꾸면 브라우저에 즉시 반영되는 것은 리액트만의 기능이 아니라, 애플리케이션을 실행할 때 함께 뜨는 **개발 서버** 덕분이다.
- 파일이 바뀔 때마다 개발 서버가 이를 감지해서 관련된 파일을 다시 불러온다.
- 리액트 쪽에서는 이 기능을 **React Fast Refresh**(예전에는 React Hot Loader), 개발 서버 쪽에서는 **Hot Module Replacement(HMR)** 라고 부른다.

### 실습 2. HTML 요소를 JSX로 작성하기 (input, label)

이번에는 HTML의 `<input>`과 `<label>`을 JSX로 직접 작성해보자. 레이블을 클릭했을 때 입력 필드에 포커스가 가도록, `<label>` 안에 `<input>`을 중첩하거나 두 태그에 전용 HTML 속성을 지정한다.

```jsx
const title = 'React';

function App() {
  return (
    <div>
      <h1>Hello {title}</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
}

export default App;
```

- HTML 속성 중 `id`, `type`은 순수 HTML 속성이지만, `htmlFor`는 JSX 전용 속성이다.
- **JSX는 리액트 내부 구현 때문에 일부 HTML 속성 이름을 바꿔서 사용한다.**
- JSX는 HTML보다는 자바스크립트에 더 가깝기 때문에, 속성 이름도 **camelCase** 규칙을 따른다.

#### JSX 전용 속성 예:

| 순수 HTML | JSX | 이유 |
| --- | --- | --- |
| `class` | `className` | `class`는 자바스크립트 예약어와 충돌 |
| `for` | `htmlFor` | `for`는 자바스크립트 예약어와 충돌 |
| `onclick` | `onClick` | JSX는 camelCase 네이밍 컨벤션을 따름 |

리액트는 JSX 안의 HTML 속성을 내부적으로 자바스크립트로 변환하는 과정에서 `class`, `for` 같은 예약어와 충돌하는 이름들을 `className`, `htmlFor`로 바꿔서 제공한다. 실제 브라우저에 HTML이 렌더링될 때는 다시 원래의 HTML 속성으로 변환된다.

### 실습 3. 객체(object) 데이터를 JSX에 표시하기

문자열 하나(`title`)가 아니라, `title`과 `greeting` 프로퍼티를 가진 자바스크립트 **객체** `welcome`을 정의하고, 두 프로퍼티를 `<h1>` 안에 나란히 렌더링해보자.

```jsx
const welcome = {
  greeting: 'Hey',
  title: 'React',
};

function App() {
  return (
    <div>
      <h1>
        {welcome.greeting} {welcome.title}
      </h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
}

export default App;
```

- HTML은 (속성 이름을 제외하면) JSX 안에서 거의 그대로 사용할 수 있지만, **중괄호 안에 들어가는 모든 것은 자바스크립트로 해석**된다.

### 실습 4. 함수 호출 결과를 JSX에 표시하기

중괄호 안에서는 값뿐 아니라 함수 호출도 가능하다. `title`을 반환하는 함수를 정의하고, 중괄호 안에서 바로 실행해보자.

```jsx
function getTitle(title) {
  return title;
}

function App() {
  return (
    <div>
      <h1>Hello {getTitle('React')}</h1>

      <label htmlFor="search">Search: </label>
      <input id="search" type="text" />
    </div>
  );
}

export default App;
```

## JSX는 결국 자바스크립트로 변환(트랜스파일)된다

JSX는 자바스크립트의 문법 확장(syntax extension)이다. 예전에는 JSX를 사용하는 파일은 `.js`가 아니라 `.jsx` 확장자를 써야 했다. 요즘은 여러 빌드 도구가 `.js` 파일 안의 JSX도 인식하도록 설정할 수 있지만, Vite 같은 도구는 개발자에게 더 명확하게 보여주기 위해 `.jsx` 확장자를 권장한다.

1) JSX 코드:

```jsx
const title = 'React';

// JSX ...
const myElement = <h1>Hello {title}</h1>;
```

2) JvaScript로 트랜스파일된 코드:

```javascript
// ... 트랜스파일 되면 자바스크립트로 변환된다
const myElement = React.createElement('h1', null, `Hello ${title}`);
```

3) 리액트가 렌더링한 HTML:

```html
<h1>Hello React</h1>
```

**JSX 덕분에 개발자는 HTML과 자바스크립트를 섞어서 "무엇을 렌더링할지"를 표현할 수 있다.** 예전에는 마크업(HTML)과 로직(자바스크립트)을 분리하는 것이 일반적이었지만, 리액트는 이 둘을 하나의 리액트 컴포넌트 안에 함께 둔다. 실제로 리액트는 JSX 없이 `createElement()` 같은 메서드로도 UI를 표현할 수 있지만, 대부분의 개발자는 명령형으로 UI를 표현하는 메서드 방식보다 **선언적**으로 표현할 수 있는 JSX를 더 직관적이라고 느낀다.

JSX는 원래 리액트를 위해 만들어졌지만, 지금은 다른 라이브러리/프레임워크에서도 널리 쓰인다. 별도의 템플릿 문법 없이(중괄호만으로) HTML 안에서 자바스크립트를 사용할 수 있고, 원시 타입부터 복합 타입까지 모든 자바스크립트 데이터 구조를 JSX 안에서 다룰 수 있다.

## Q&A

- **Q. 리액트에서 JSX란 무엇인가?**
  - A. JSX는 UI가 어떻게 보여야 하는지를 표현하기 위해 리액트가 권장하는 자바스크립트 문법 확장이다.
- **Q. 브라우저가 JSX를 바로 렌더링할 수 있는가?**
  - A. 아니다. 브라우저는 JSX를 이해하지 못하며, Babel 같은 도구로 일반 자바스크립트로 트랜스파일되어야 한다.
- **Q. 리액트에서 JSX는 필수인가?**
  - A. 아니다. 필수는 아니지만, 널리 쓰이는 편리한 컴포넌트 작성 방식이다.
- **Q. JSX 안에서 변수는 어떻게 렌더링하는가?**
  - A. 중괄호 `{ }`로 변수를 감싸서 표현한다. 예: `{myVariable}`
