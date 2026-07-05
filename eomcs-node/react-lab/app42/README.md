# app42. 리액트에서 CSS 모듈 사용하기

## 개요

**CSS Modules**는 좀 더 발전된 CSS-in-CSS 접근 방식이다. CSS 파일 자체는 이전과 동일하게 작성하며, Sass 같은 CSS 확장도 그대로 사용할 수 있다. 달라지는 것은 리액트 컴포넌트에서 그 CSS 파일을 사용하는 방식이다.

> Java에 비유하면, 일반 CSS에서 문자열로 클래스 이름을 매칭하는 방식은 컴파일 타임 검증이 없는 것과 비슷하다(오타가 나도 컴파일러가 잡아주지 않는다). CSS Modules는 CSS 클래스를 자바스크립트 객체의 프로퍼티(`styles.container`)로 접근하게 해주므로, 마치 문자열 상수 대신 enum이나 상수 필드를 참조하는 것과 비슷하다. 존재하지 않는 프로퍼티를 참조하면 `undefined`가 되어 스타일이 적용되지 않는 식으로 드러나기 때문에, 일반 CSS보다 실수를 알아채기 쉽다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import './App.css';

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

## 실습

### 실습 1. `App.css`를 `App.module.css`로 이름 바꾸기

Vite에서 CSS 모듈을 활성화하려면, `src/App.css` 파일의 이름을 `src/App.module.css`로 바꾼다. 프로젝트 디렉터리의 커맨드 라인에서 다음 명령을 실행한다.

```bash
mv src/App.css src/App.module.css
```

이름이 바뀐 `src/App.module.css` 파일에는 이전과 동일하게 첫 CSS 클래스 정의로 시작한다.

```css
/* src/App.module.css */
.container {
  height: 100vw;
  padding: 20px;

  background: #83a4d4; /* 오래된 브라우저를 위한 폴백 */
  background: linear-gradient(to left, #b6fbff, #83a4d4);

  color: #171212;
}

.headlinePrimary {
  font-size: 48px;
  font-weight: 300;
  letter-spacing: 2px;
}
```

### 실습 2. `App.module.css`를 자바스크립트 객체로 임포트하기

`src/App.module.css` 파일을 다시 상대 경로로 임포트한다. 이번에는 자바스크립트 객체로 임포트하는데, 그 객체의 이름(여기서는 `styles`)은 원하는 대로 정할 수 있다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import styles from './App.module.css';
```

`className`을 CSS 파일에 매핑된 문자열로 정의하는 대신, `styles` 객체에서 CSS 클래스를 직접 접근해서 JSX 안의 자바스크립트 표현식으로 엘리먼트에 지정한다.

```jsx
// src/App.jsx
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

### 실습 3. `Item` 컴포넌트에서 여러 CSS 클래스 결합하기

`styles` 객체를 통해 엘리먼트의 단일 `className` 속성에 여러 CSS 클래스를 추가하는 방법에는 여러 가지가 있다. 여기서는 자바스크립트의 템플릿 리터럴을 사용한다.

```jsx
// src/App.jsx
const Item = ({ item, onRemoveItem }) => (
  <li className={styles.item}>
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
        className={`${styles.button} ${styles.buttonSmall}`}
      >
        Dismiss
      </button>
    </span>
  </li>
);
```

인라인 스타일 역시 JSX 안에서 좀 더 동적인 스타일로 그대로 사용할 수 있다. 여기서도 Sass 같은 CSS 확장을 도입해서 CSS 중첩 같은 고급 기능을 쓸 수 있지만(이전 절 참고), 이 예제에서는 네이티브 CSS 기능만 사용한다.

```css
/* src/App.module.css */
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

그다음 `src/App.module.css` 파일에 버튼 CSS 클래스들을 추가한다.

```css
/* src/App.module.css */
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

.buttonSmall {
  padding: 5px;
}

.buttonLarge {
  padding: 10px;
}
```

여기서는 이전 절의 `button_small`, `button_large`와 달리, 유사 BEM(pseudo BEM) 네이밍 컨벤션으로 옮겨간 것을 볼 수 있다. 이전 방식의 네이밍을 그대로 유지했다면 `styles['button_small']`로만 접근할 수 있는데, 이는 자바스크립트 객체 프로퍼티 이름에 언더스코어(`_`)를 쓸 때의 한계 때문에 코드가 더 장황해진다. 대시(`-`)로 정의된 클래스에도 같은 문제가 있다. 반면 `buttonSmall`처럼 카멜케이스로 정의하면 `styles.buttonSmall`처럼 간결하게 접근할 수 있다(`Item` 컴포넌트 참고).

### 실습 4. `SearchForm`에 여러 클래스 적용하고 `clsx` 활용하기

```jsx
// src/App.jsx
const SearchForm = ({ /* ... */ }) => (
  <form action={searchAction} className={styles.searchForm}>
    <InputWithLabel /* ... */ >
      <strong>Search:</strong>
    </InputWithLabel>

    <button
      type="submit"
      disabled={!searchTerm}
      className={`${styles.button} ${styles.buttonLarge}`}
    >
      Submit
    </button>
  </form>
);
```

`SearchForm` 컴포넌트도 `styles` 객체를 전달받는다. 한 엘리먼트에 두 스타일을 함께 쓰려면 자바스크립트 템플릿 리터럴로 문자열을 조합해야 한다. 이를 대신할 방법으로 **clsx** 라이브러리가 있는데, 프로젝트 의존성으로 커맨드 라인에서 설치할 수 있다.

```bash
npm install clsx
```

```jsx
// src/App.jsx
import clsx from 'clsx';

// ...

// className 속성 어딘가에
className={clsx(styles.button, styles.buttonLarge)}
```

clsx 라이브러리는 조건부 스타일링도 지원한다. 객체의 왼쪽(key)은 계산된 프로퍼티 이름(computed property name)으로 사용해야 하며, 오른쪽(value)이 `true`로 평가될 때만 해당 클래스가 적용된다.

```jsx
// src/App.jsx
import clsx from 'clsx';

// ...

// className 속성 어딘가에
className={clsx(styles.button, { [styles.buttonLarge]: isLarge })}
```

### 실습 5. `InputWithLabel` 컴포넌트 스타일링 마무리하기

마지막으로 `InputWithLabel` 컴포넌트를 이어서 작성한다.

```jsx
// src/App.jsx
const InputWithLabel = ({ /* ... */ }) => {
  // ...

  return (
    <>
      <label htmlFor={id} className={styles.label}>
        {children}
      </label>
      &nbsp;
      <input
        ref={inputRef}
        id={id}
        type={type}
        value={value}
        onChange={onInputChange}
        className={styles.input}
      />
    </>
  );
};
```

그리고 `src/App.module.css` 파일에 남은 스타일을 마저 정의한다.

```css
/* src/App.module.css */
.searchForm {
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

앞 절에서와 같은 주의사항이 여기에도 적용된다. `input`이나 `label`처럼 일부 스타일은 CSS 모듈 없이 전역 `src/index.css` 파일에 정의하는 편이 더 효율적일 수 있다.

CSS Modules 역시 다른 CSS-in-CSS 접근 방식과 마찬가지로 Sass를 사용해서 중첩 같은 고급 CSS 기능을 쓸 수 있다. CSS Modules의 장점은, 정의되지 않은 스타일을 참조할 때마다 자바스크립트에서 에러(또는 `undefined`)를 통해 바로 알아챌 수 있다는 점이다. 반면 일반 CSS 방식에서는 자바스크립트와 CSS 파일 사이에 이름이 어긋나도 눈에 띄지 않고 지나칠 수 있다.

## 정리

- **CSS Modules**는 CSS 파일 자체는 그대로 두되, `.module.css` 확장자를 사용해서 클래스를 자바스크립트 객체(`styles`)로 임포트해 사용하는 CSS-in-CSS 접근 방식이다.
- `className={styles.container}`처럼 객체 프로퍼티로 클래스를 참조하므로, 오타나 정의되지 않은 클래스를 사용했을 때 일반 CSS보다 더 쉽게 알아챌 수 있다.
- 카멜케이스(`buttonSmall`)로 클래스 이름을 정의하면 언더스코어나 대시가 포함된 이름보다 자바스크립트에서 더 간결하게 접근할 수 있다.
- 한 엘리먼트에 여러 클래스를 적용할 때는 템플릿 리터럴을 직접 조합하거나, **clsx** 같은 라이브러리를 사용해서 조건부 스타일링까지 간편하게 처리할 수 있다.
- CSS Modules도 Sass 같은 CSS 확장과 함께 사용할 수 있으며, 전역으로 공유되는 스타일(`input`, `label` 등)은 여전히 `src/index.css`에 두는 편이 나을 수 있다.
