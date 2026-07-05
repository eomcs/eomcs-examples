# app43. 리액트에서 Styled Components 사용하기

## 개요

앞서 살펴본 CSS-in-CSS 접근 방식들과 달리, **Styled Components**는 CSS-in-JS를 위한 여러 접근 방식 중 하나다. 여러 방식 중에서도 가장 널리 쓰이는 방식이라서 이 라이브러리를 선택했다. 자바스크립트 의존성으로 제공되므로 커맨드 라인에서 설치해야 한다.

> Java에 비유하면, 지금까지의 일반 CSS나 CSS Modules는 스타일을 별도의 리소스 파일(.css)로 관리하고 클래스 이름으로 연결하는 방식이었다면, Styled Components는 스타일 정의 자체를 자바(여기서는 자바스크립트) 코드 안에 완전히 캡슐화하는 방식이다. 마치 인라인 SQL 대신 JPA 엔티티에 애너테이션으로 매핑 정보를 직접 넣는 것처럼, 별도 파일 없이 컴포넌트 코드 자체에 스타일이라는 관심사를 함께 녹여 넣는다고 볼 수 있다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import styles from './App.module.css';

const App = () => {
  // ...

  return (
    <div className={styles.container}>
      <h1 className={styles.headlinePrimary}>My Hacker Stories</h1>

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

## 실습

### 실습 1. styled-components 설치하고 임포트하기

먼저 커맨드 라인에서 styled-components를 설치한다.

```bash
npm install styled-components
```

그다음 `src/App.jsx` 파일에서 임포트한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import styled from 'styled-components';
```

### 실습 2. 첫 Styled Component 정의하기

이름에서 알 수 있듯이, CSS-in-JS는 자바스크립트 파일 안에서 이뤄진다. `src/App.jsx` 파일에 첫 번째 styled component들을 정의한다.

```jsx
// src/App.jsx
const StyledContainer = styled.div`
  height: 100vw;
  padding: 20px;

  background: #83a4d4;
  background: linear-gradient(to left, #b6fbff, #83a4d4);

  color: #171212;
`;

const StyledHeadlinePrimary = styled.h1`
  font-size: 48px;
  font-weight: 300;
  letter-spacing: 2px;
`;
```

Styled Components를 사용할 때는 자바스크립트 템플릿 리터럴을 마치 자바스크립트 함수처럼 사용한다. 백틱(`` ` ``) 사이의 모든 내용은 하나의 인자로 볼 수 있고, `styled` 객체는 필요한 모든 HTML 엘리먼트(예: `div`, `h1`)에 함수로 접근할 수 있게 해준다. 스타일과 함께 이 함수를 호출하면, `App` 컴포넌트에서 바로 사용할 수 있는 리액트 컴포넌트가 반환된다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  return (
    <StyledContainer>
      <StyledHeadlinePrimary>My Hacker Stories</StyledHeadlinePrimary>

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
    </StyledContainer>
  );
};
```

### 실습 3. `Item` 컴포넌트에 전용 Styled Component 적용하기

이렇게 만들어진 리액트 컴포넌트는 일반적인 리액트 컴포넌트와 동일한 규칙을 따른다. 엘리먼트 태그 사이에 전달되는 모든 내용은 자동으로 `children` prop으로 전달된다. `Item` 컴포넌트에서는 이번에는 인라인 스타일 대신, 전용 styled component를 정의해서 사용한다. `StyledColumn`은 리액트 prop을 통해 동적으로 스타일을 전달받는다.

```jsx
// src/App.jsx
const Item = ({ item, onRemoveItem }) => (
  <StyledItem>
    <StyledColumn width="40%">
      <a href={item.url}>{item.title}</a>
    </StyledColumn>
    <StyledColumn width="30%">{item.author}</StyledColumn>
    <StyledColumn width="10%">{item.num_comments}</StyledColumn>
    <StyledColumn width="10%">{item.points}</StyledColumn>
    <StyledColumn width="10%">
      <StyledButtonSmall
        type="button"
        onClick={() => onRemoveItem(item)}
      >
        Dismiss
      </StyledButtonSmall>
    </StyledColumn>
  </StyledItem>
);
```

유연한 `width` prop은 styled component의 템플릿 리터럴 안에서 인라인 함수의 인자로 접근할 수 있다. 이 함수의 반환값이 문자열로 그 자리에 적용된다. 화살표 함수의 본문을 생략하는 즉시 반환(implicit return) 문법을 사용하면 간결한 인라인 함수로 표현할 수 있다.

```jsx
// src/App.jsx
const StyledItem = styled.li`
  display: flex;
  align-items: center;
  padding-bottom: 5px;
`;

const StyledColumn = styled.span`
  padding: 0 5px;
  white-space: nowrap;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;

  a {
    color: inherit;
  }

  width: ${(props) => props.width};
`;
```

### 실습 4. 중첩 CSS와 파생 컴포넌트 만들기

Styled Components에서는 CSS 중첩(nesting) 같은 고급 기능을 기본으로 사용할 수 있다. 중첩된 엘리먼트에 접근할 수 있고, `&` CSS 연산자로 현재 엘리먼트 자신을 선택할 수도 있다.

```jsx
// src/App.jsx
const StyledButton = styled.button`
  background: transparent;
  border: 1px solid #171212;
  padding: 5px;
  cursor: pointer;

  transition: all 0.1s ease-in;

  &:hover {
    background: #171212;
    color: #ffffff;
  }
`;
```

또한 라이브러리의 함수에 다른 컴포넌트를 전달해서 styled component의 특화된 버전을 만들 수도 있다. 아래의 특화된 버튼들은 앞서 정의한 `StyledButton` 컴포넌트의 기본 스타일을 그대로 물려받는다.

```jsx
// src/App.jsx
const StyledButtonSmall = styled(StyledButton)`
  padding: 5px;
`;

const StyledButtonLarge = styled(StyledButton)`
  padding: 10px;
`;

const StyledSearchForm = styled.form`
  padding: 10px 0 20px 0;
  display: flex;
  align-items: baseline;
`;
```

### 실습 5. `SearchForm`과 `InputWithLabel`에 적용하기

`StyledSearchForm`처럼 styled component를 사용하더라도, 실제 HTML 출력에서는 그 기반이 되는 `form` 엘리먼트가 그대로 사용된다. 따라서 `onSubmit`, `type`, `disabled` 같은 네이티브 HTML 속성도 계속 그대로 사용할 수 있다.

```jsx
// src/App.jsx
const SearchForm = ({ /* ... */ }) => (
  <StyledSearchForm action={searchAction}>
    <InputWithLabel
      id="search"
      value={searchTerm}
      isFocused
      onInputChange={onSearchInput}
    >
      <strong>Search:</strong>
    </InputWithLabel>

    <StyledButtonLarge type="submit" disabled={!searchTerm}>
      Submit
    </StyledButtonLarge>
  </StyledSearchForm>
);
```

마지막으로 `InputWithLabel`에도 아직 정의하지 않은 styled component를 적용한다.

```jsx
// src/App.jsx
const InputWithLabel = ({ /* ... */ }) => {
  // ...

  return (
    <>
      <StyledLabel htmlFor={id}>{children}</StyledLabel>
      &nbsp;
      <StyledInput
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

그리고 짝이 되는 styled component를 같은 파일에 정의한다.

```jsx
// src/App.jsx
const StyledLabel = styled.label`
  border-top: 1px solid #171212;
  border-left: 1px solid #171212;
  padding-left: 5px;
  font-size: 24px;
`;

const StyledInput = styled.input`
  border: none;
  border-bottom: 1px solid #171212;
  background-color: transparent;

  font-size: 24px;
`;
```

Styled Components를 사용하는 CSS-in-JS 방식은 스타일 정의의 초점을 실제 리액트 컴포넌트로 옮긴다. Styled Components는 중간 CSS 파일 없이 리액트 컴포넌트 자체로 정의된 스타일이다. 만약 styled component를 자바스크립트에서 사용하지 않으면, IDE/에디터가 이를 알려준다. 프로덕션 빌드에서는 Styled Components도 다른 자바스크립트 자산들과 함께 번들링된다. 즉, CSS-in-JS 전략을 사용할 때는 별도의 CSS 파일 없이 오직 자바스크립트만 존재한다. CSS-in-JS(Styled Components)와 CSS-in-CSS(CSS Modules) 두 전략 모두 리액트 개발자들 사이에서 인기가 있다. 자신과 팀에 가장 잘 맞는 방식을 선택하면 된다.

## 정리

- **Styled Components**는 CSS-in-JS 방식 중 가장 널리 쓰이는 라이브러리로, `styled.div`처럼 HTML 엘리먼트에 대응하는 함수에 템플릿 리터럴로 스타일을 전달해서 리액트 컴포넌트를 만든다.
- styled component는 일반 리액트 컴포넌트와 같은 규칙을 따르며, prop(예: `width`)을 템플릿 리터럴 안에서 함수로 받아 동적인 스타일을 적용할 수 있다.
- `&:hover`처럼 중첩과 `&` 연산자를 기본으로 지원해서, 별도의 CSS 확장 없이도 고급 CSS 기능을 사용할 수 있다.
- `styled(StyledButton)`처럼 기존 styled component를 넘겨서 기본 스타일을 물려받는 특화된 버전을 만들 수 있다.
- 별도의 CSS 파일 없이 스타일이 자바스크립트 코드에 완전히 포함되므로, 프로덕션 빌드 시 다른 자바스크립트 자산과 함께 번들링된다.
