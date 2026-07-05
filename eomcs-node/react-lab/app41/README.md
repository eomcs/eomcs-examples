# app41. 리액트에서 CSS 사용하기

## 개요

리액트에서 흔히 쓰이는 일반 CSS는 이미 배웠을 법한 표준 CSS와 비슷하다. 모든 웹 애플리케이션은 HTML 엘리먼트에 `class`(리액트에서는 `className`) 속성을 부여하고, 이를 CSS 파일로 스타일링한다.

> Java에 비유하면, `className`에 문자열로 클래스 이름을 지정하는 방식은 JSP나 타임리프에서 `<div class="container">`처럼 정적 클래스 이름을 그대로 쓰는 것과 같다. `style={{ width: '40%' }}`처럼 JSX 안에 자바스크립트 객체로 스타일을 넘기는 인라인 스타일은, 서버 사이드에서 모델 값에 따라 동적으로 `style` 속성 문자열을 조립해 넣는 것에 대응한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      <SearchForm
        searchTerm={searchTerm}
        onSearchInput={handleSearchInput}
        searchAction={searchAction}
      />

      <hr />

      {stories.isError && <p>Something went wrong ...</p>}

      {stories.isLoading ? (
        <p>Loading ...</p>
      ) : (
        <List list={stories.data} onRemoveItem={handleRemoveStory} />
      )}
    </div>
  );
};
```

## 실습

### 실습 1. `App` 컴포넌트에 `className` 붙이기

`<hr />`은 다음 단계에서 CSS가 테두리를 대신 처리해줄 것이므로 제거한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  return (
    <div className="container">
      <h1 className="headline-primary">My Hacker Stories</h1>

      <SearchForm
        searchTerm={searchTerm}
        onSearchInput={handleSearchInput}
        searchAction={searchAction}
      />

      {stories.isError && <p>Something went wrong ...</p>}

      {stories.isLoading ? (
        <p>Loading ...</p>
      ) : (
        <List list={stories.data} onRemoveItem={handleRemoveStory} />
      )}
    </div>
  );
};
```

### 실습 2. `App.css` 파일을 만들어 임포트하기

Vite가 임포트를 처리해주는 방식을 활용해서 CSS 파일을 임포트한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import './App.css';
```

이 CSS 파일에는 `App` 컴포넌트에서 사용한(그리고 앞으로 사용할) 두 개(이상)의 CSS 클래스를 정의한다. `src/App.css` 파일에 다음과 같이 정의한다.

```css
/* src/App.css */
.container {
  height: 100vw;
  padding: 20px;

  background: #83a4d4; /* 오래된 브라우저를 위한 폴백 */
  background: linear-gradient(to left, #b6fbff, #83a4d4);

  color: #171212;
}

.headline-primary {
  font-size: 48px;
  font-weight: 300;
  letter-spacing: 2px;
}
```

애플리케이션을 다시 시작하면 첫 스타일링이 적용된 모습을 볼 수 있다.

### 실습 3. `Item` 컴포넌트에 `className`과 인라인 스타일 함께 사용하기

이제 `Item` 컴포넌트로 넘어간다. 이 컴포넌트의 일부 엘리먼트에도 `className`을 부여하는데, 여기서는 새로운 스타일링 기법도 함께 사용한다.

```jsx
// src/App.jsx
const Item = ({ item, onRemoveItem }) => (
  <li className="item">
    <span style={{ width: '40%' }}>
      <a href={item.url}>{item.title}</a>
    </span>
    <span style={{ width: '30%' }}>{item.author}</span>
    <span style={{ width: '10%' }}>{item.num_comments}</span>
    <span style={{ width: '10%' }}>{item.points}</span>
    <span style={{ width: '10%' }}>
      <button
        type="button"
        onClick={() => onRemoveItem(item)}
        className="button button_small"
      >
        Dismiss
      </button>
    </span>
  </li>
);
```

여기서 보듯이, HTML 엘리먼트에는 `style` 속성도 사용할 수 있다. JSX에서 `style`은 이런 속성에 자바스크립트 객체 형태로 전달할 수 있다. 이런 방식으로 정적인 CSS 파일 대신 자바스크립트 파일 안에서 동적인 스타일 속성을 정의할 수 있다. 이 접근 방식을 **인라인 스타일(inline style)**이라고 부르며, 빠른 프로토타이핑이나 동적인 스타일 정의에 유용하다. 다만 인라인 스타일은 아껴서 사용해야 하는데, CSS 파일로 스타일을 따로 정의해두는 편이 JSX를 더 간결하게 유지해주기 때문이다.

`src/App.css` 파일에 새로운 CSS 클래스를 정의한다. 여기서는 기본적인 CSS 기능만 사용하는데, Sass 같은 CSS 확장에서 제공하는 중첩(nesting) 같은 고급 CSS 기능은 선택적인 설정이 필요해서 이 예제에는 포함하지 않는다.

```css
/* src/App.css */
.item {
  display: flex;
  align-items: center;
  padding-bottom: 5px;
}

.item > span {
  padding: 0 5px;
  white-space: nowrap;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.item > span > a {
  color: inherit;
}
```

### 실습 4. 버튼 스타일 정의하기

이전 컴포넌트에서 쓰인 버튼 스타일이 아직 없으므로, 기본 버튼 스타일과 좀 더 구체적인 두 가지 버튼 스타일(small, large)을 정의한다. 하나는 이미 사용됐고, 다른 하나는 다음 단계에서 사용할 것이다.

```css
/* src/App.css */
.button {
  background: transparent;
  border: 1px solid #171212;
  padding: 5px;
  cursor: pointer;

  transition: all 0.1s ease-in;
}

.button:hover {
  background: #171212;
  color: #ffffff;
}

.button_small {
  padding: 5px;
}

.button_large {
  padding: 10px;
}
```

리액트의 스타일링 접근 방식과는 별개로, 네이밍 컨벤션(CSS 가이드라인)은 또 다른 주제다. 위 CSS 스니펫은 언더스코어(`_`)로 클래스의 변형을 정의하는 BEM 규칙을 따랐다. 여러분과 팀에 맞는 네이밍 컨벤션을 자유롭게 선택하면 된다.

### 실습 5. `SearchForm`과 `InputWithLabel`에 스타일 적용하기

곧바로 다음 리액트 컴포넌트를 스타일링해보자.

```jsx
// src/App.jsx
const SearchForm = ({ /* ... */ }) => (
  <form action={searchAction} className="search-form">
    <InputWithLabel /* ... */ >
      <strong>Search:</strong>
    </InputWithLabel>

    <button
      type="submit"
      disabled={!searchTerm}
      className="button button_large"
    >
      Submit
    </button>
  </form>
);
```

`className` 속성은 리액트 컴포넌트에 prop으로 전달할 수도 있다. 예를 들어 이 옵션을 활용해서 `SearchForm` 컴포넌트에 `className` prop으로 CSS 파일에 정의된 여러 클래스(예: `button_large`, `button_small`) 중 하나를 유연하게 전달할 수 있다. 마지막으로 `InputWithLabel` 컴포넌트도 스타일링한다.

```jsx
// src/App.jsx
const InputWithLabel = ({ /* ... */ }) => {
  // ...

  return (
    <>
      <label htmlFor={id} className="label">
        {children}
      </label>
      &nbsp;
      <input
        ref={inputRef}
        id={id}
        type={type}
        value={value}
        onChange={onInputChange}
        className="input"
      />
    </>
  );
};
```

`src/App.css` 파일에 남은 클래스들을 추가한다.

```css
/* src/App.css */
.search-form {
  padding: 10px 0 20px 0;
  display: flex;
  align-items: baseline;
}

.label {
  border-top: 1px solid #171212;
  border-left: 1px solid #171212;
  padding-left: 5px;
  font-size: 24px;
}

.input {
  border: none;
  border-bottom: 1px solid #171212;
  background-color: transparent;

  font-size: 24px;
}
```

단순화를 위해 `label`, `input` 같은 엘리먼트는 `src/App.css` 파일에서 개별적으로 스타일링했다. 하지만 실제 애플리케이션에서는 이런 엘리먼트를 `src/index.css` 파일에서 전역으로 한 번만 정의하는 편이 더 나을 수 있다. 리액트 컴포넌트가 여러 파일로 나뉘다 보니, 스타일을 공유하는 일이 필수가 된다. 결국 이것이 리액트에서 CSS를 사용하는 기본적인 방식이다. Sass(Syntactically Awesome Style Sheets) 같은 CSS 확장이 없으면, CSS 중첩 같은 기능이 네이티브 CSS에는 없기 때문에 스타일링이 좀 더 번거로워질 수 있다.

## 정리

- 리액트에서 일반 CSS는 표준 CSS와 크게 다르지 않으며, `class` 대신 `className` 속성을 사용해서 HTML 엘리먼트에 CSS 클래스를 부여한다.
- CSS 파일은 컴포넌트 파일에서 `import './App.css';`처럼 직접 임포트해서 사용할 수 있으며, Vite가 이 임포트를 알아서 처리해준다.
- `style={{ ... }}`로 자바스크립트 객체를 전달하는 **인라인 스타일**은 동적인 스타일 값을 다루기에 유용하지만, JSX를 장황하게 만들 수 있어 아껴서 사용하는 것이 좋다.
- `className`은 컴포넌트에 prop으로도 전달할 수 있어서, 컴포넌트를 사용하는 쪽에서 원하는 스타일 클래스를 유연하게 선택할 수 있다.
- 네이티브 CSS만으로는 중첩(nesting) 같은 고급 기능을 쓸 수 없으므로, 이런 기능이 필요하다면 Sass 같은 CSS 확장을 고려할 수 있다.
