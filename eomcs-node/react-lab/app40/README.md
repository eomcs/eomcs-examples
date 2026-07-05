# app40. 리액트에서 스타일링하기

## 개요

리액트 애플리케이션을 스타일링하는 방법은 여러 가지이며, 어떤 스타일링 전략과 접근 방식이 최선인지에 대한 논쟁도 길게 이어져 왔다. 여기서는 이런 전략들 중 몇 가지를 각각 하나의 접근 방식을 대표하는 예시로 살펴보되, 어느 한쪽에 지나치게 무게를 싣지는 않는다. 장단점에 대한 논의도 있겠지만, 결국은 개발자와 팀에게 무엇이 가장 잘 맞는지의 문제에 가깝다.

리액트 스타일링은 흔히 쓰이는 **일반 CSS** 로 시작한 다음, 좀 더 고급 **CSS-in-CSS 전략(CSS Modules)** 과 **CSS-in-JS 전략(Styled Components)** 이라는 두 가지 대안을 살펴본다. CSS Modules와 Styled Components는 이 두 그룹의 전략 안에 있는 여러 접근 방식 중 두 가지일 뿐이다. 또한 로고나 아이콘 같은 SVG(Scalable Vector Graphics)를 리액트 애플리케이션에 포함시키는 방법도 다룬다.

> Java에 비유하면, CSS 파일과 `className`을 매칭하는 일반 CSS 방식은 스프링 MVC에서 정적 리소스로 CSS 파일을 서빙하고 JSP/타임리프 템플릿에서 `class` 속성으로 참조하는 것과 비슷하다. CSS Modules는 클래스 이름 충돌을 막기 위해 이름을 자동으로 스코프 처리해주는 것이 마치 자바 패키지가 클래스 이름 충돌을 막아주는 것과 비슷하고, Styled Components(CSS-in-JS)는 스타일 정의 자체를 자바 코드(여기서는 자바스크립트) 안에 완전히 캡슐화하는 방식이다.

버튼, 다이얼로그, 드롭다운 같은 흔히 쓰이는 UI 컴포넌트를 처음부터 직접 만들고 싶지 않다면, 리액트에 특화된 인기 있는 UI 라이브러리를 선택할 수도 있다. 이런 라이브러리들은 기본적으로 이런 컴포넌트들을 제공한다. 다만 이미 만들어진 솔루션을 쓰기 전에 직접 이런 컴포넌트를 만들어보는 편이 리액트를 배우는 데는 더 낫다. 따라서 실습에서는 UI 컴포넌트 라이브러리를 사용하지 않는다.

## 실습

다음에 나오는 스타일링 접근 방식들과 SVG는 대부분 Vite에 이미 사전 설정돼 있다. Webpack 같은 빌드 도구를 직접 커스텀 구성해서 사용하고 있다면, CSS나 SVG 파일을 임포트할 수 있도록 별도로 설정해야 할 수도 있다. 하지만 Vite를 사용하고 있으므로 이 파일들을 바로 사용할 수 있다.

### 실습 1. 전역 CSS 파일 임포트하기

예를 들어 `src/main.jsx` 파일에서 `src/index.css` 파일을 반드시 임포트해야 한다.

```jsx
// src/main.jsx
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
);
```

### 실습 2. 전역 스타일 정의하기

`src/index.css` 파일에 다음 CSS를 사용해서 여백(margin)을 제거하고, 폴백을 포함한 표준화된 폰트를 사용하도록 설정한다.

```css
/* src/index.css */
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
```

기본적으로 프로젝트 전역에 적용돼야 하는 모든 CSS는 이 파일에 선언할 수 있다.

## 정리

- 리액트 애플리케이션을 스타일링하는 방법에는 일반 CSS, CSS Modules(CSS-in-CSS), Styled Components(CSS-in-JS) 등 여러 전략이 있으며, 어느 것이 "정답"이라기보다는 팀에 맞는 선택이 중요하다.
- Vite는 CSS와 SVG 임포트를 기본적으로 지원하므로, 별도의 빌드 설정 없이 바로 사용할 수 있다.
- `src/index.css`처럼 프로젝트 전역에 적용할 CSS(여백 초기화, 폰트 설정 등)는 애플리케이션의 진입점(`main.jsx`)에서 임포트해서 전역으로 선언한다.
- 버튼, 다이얼로그 같은 흔한 UI 컴포넌트도, 배우는 단계에서는 라이브러리에 의존하기보다 직접 만들어보는 것이 리액트 학습에 더 도움이 된다.
