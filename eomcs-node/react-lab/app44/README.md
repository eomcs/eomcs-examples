# app44. 리액트에서 SVG 사용하기

## 개요

모던 리액트 애플리케이션을 만들다 보면 SVG를 사용해야 하는 경우가 많다. 예를 들어 버튼 엘리먼트마다 텍스트를 넣는 대신, 아이콘을 사용해서 더 가볍게 만들고 싶을 수 있다. 이번 실습에서는 리액트 컴포넌트 중 하나에서 아이콘으로 SVG(Scalable Vector Graphic)를 사용해본다.

> 이번 실습은 앞서 다룬 "CSS in React"를 바탕으로 하며, 그 스타일을 활용해서 SVG 아이콘이 처음부터 보기 좋은 모습을 갖추도록 한다. CSS Modules나 Styled Components 같은 다른 스타일링 방식을 쓰거나, 전혀 스타일을 적용하지 않아도 상관없지만, 그 경우 SVG가 다소 어색해 보일 수 있다.

> Java에 비유하면, Vite에서 `vite-plugin-svgr` 플러그인을 설치해 SVG를 자바스크립트 컴포넌트로 임포트하는 것은, 자바 진영에서 리소스 파일을 다루기 위해 별도의 빌드 플러그인(예: Maven/Gradle 플러그인)을 추가해서 애너테이션 프로세싱이나 코드 생성을 활성화하는 것과 비슷하다. SVG 파일 하나가 `import Check from './check.svg?react'` 한 줄만으로 곧바로 사용 가능한 컴포넌트(클래스)로 변환되는 셈이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import './App.css';

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

## 실습

### 실습 1. `vite-plugin-svgr` 설치하기

Vite는 기본적으로 SVG 지원을 내장하고 있지 않다. Vite에서 SVG를 사용할 수 있게 하려면, 커맨드 라인에서 플러그인 하나를 설치해야 한다.

```bash
npm install vite-plugin-svgr --save-dev
```

### 실습 2. Vite 설정에 SVG 플러그인 추가하기

새로 설치한 플러그인을 Vite 설정에서 사용할 수 있다.

```js
// vite.config.js
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import svgr from 'vite-plugin-svgr';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), svgr()],
});
```

일반적인 설정은 여기까지다. 이제 Heroicons.com에서 `check` 로 검색하여 SVG를 복사한 후, `src/check.svg` 파일을 만들어 붙여 넣는다.

```xml
<!-- src/check.svg -->
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="m4.5 12.75 6 6 9-13.5" />
</svg>
```

### 실습 3. SVG를 리액트 컴포넌트로 임포트하기

이제 SVG를(CSS와 비슷하게) 바로 리액트 컴포넌트로 임포트할 수 있다. `src/App.jsx`에서 다음과 같은 문법으로 SVG를 임포트한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';
import './App.css';
import Check from "./check.svg?react"; // SVG를 리액트 컴포넌트로 변환하여 임포트
```

`?react` 쿼리를 붙이면, SVG 파일을 곧바로 리액트 컴포넌트로 사용할 수 있다. 이제 `Item` 컴포넌트에서 버튼의 "Dismiss" 텍스트 대신, SVG 컴포넌트를 전달한다.

여기서는 아이콘으로 사용하기 위해 SVG를 임포트했지만, 이 방식은 로고나 배경 이미지 같은 다양한 용도에도 그대로 적용할 수 있다.

### 실습 4. 버튼에 SVG 아이콘 적용하기

이제 버튼의 "Dismiss" 텍스트 대신, `height`와 `width` 속성을 지정해서 SVG 컴포넌트를 전달한다.

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
        <Check height="18px" width="18px" />
      </button>
    </span>
  </li>
);
```

Vite 플러그인 덕분에 별다른 추가 설정 없이 SVG를 손쉽게 사용할 수 있다. Webpack 같은 빌드 도구로 리액트 프로젝트를 처음부터 직접 구성한다면 이 부분을 직접 챙겨야 하므로 사정이 다르다. 어쨌든 SVG는 애플리케이션을 좀 더 친근하게 만들어주므로, 필요할 때마다 적극적으로 활용하자.

## 정리

- Vite는 SVG 지원을 기본으로 제공하지 않으므로, `vite-plugin-svgr` 같은 플러그인을 설치하고 `vite.config.js`에 등록해야 한다.
- `import Check from './check.svg?react';`처럼 `?react` 쿼리를 붙여 임포트하면, SVG 파일을 곧바로 리액트 컴포넌트로 사용할 수 있다.
- SVG 컴포넌트는 `height`, `width` 같은 일반적인 속성을 그대로 받아서 크기를 조절할 수 있다.
- 이 방식은 아이콘뿐 아니라 로고나 배경 이미지 등 다양한 용도로 SVG를 다룰 때도 동일하게 활용할 수 있다.
- 이 절은 "CSS in React"에서 다룬 `className` 기반 스타일을 전제로 하지만, CSS Modules나 Styled Components 같은 다른 스타일링 방식과 함께 사용해도 무방하다.
