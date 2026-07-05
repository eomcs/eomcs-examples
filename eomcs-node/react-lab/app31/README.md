# app31. 리액트의 서드파티 라이브러리

## 개요

앞선 실습들에서 해커 뉴스 API에 요청을 보내기 위해 브라우저가 제공하는 네이티브 `fetch` API를 사용했다. 하지만 모든 브라우저가 이를 지원하는 것은 아니며, 특히 오래된 브라우저는 지원하지 않는 경우가 많다. 또한 헤드리스 브라우저 환경에서 애플리케이션을 테스트하기 시작하면, 실제 브라우저가 없기 때문에 `fetch` API와 관련된 문제가 생길 수 있다. 오래된 브라우저에서 `fetch`가 동작하게 만드는 방법(폴리필)이나 테스트 환경에서 동작하게 만드는 방법(isomorphic fetch)도 있지만, 이 개념들은 이번 학습 목적에서는 다소 벗어난 주제다.

한 가지 대안은, 원격 API에 비동기 요청을 수행하는 안정적인 라이브러리인 **axios**로 네이티브 `fetch` API를 대체하는 것이다. 이번 실습에서는 라이브러리 하나(여기서는 브라우저의 네이티브 API)를 npm 레지스트리의 다른 라이브러리로 교체하는 방법을 살펴본다.

> Java에 비유하면, `fetch`는 JDK 기본 제공 `HttpClient`를, axios는 Apache HttpClient나 OkHttp 같은 서드파티 HTTP 클라이언트 라이브러리를 npm으로 설치해서 쓰는 것과 비슷하다. 표준 라이브러리 대신 좀 더 편의 기능(자동 JSON 파싱, 인터셉터 등)을 제공하는 서드파티 라이브러리로 바꾸는 것이다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(url)
      .then((response) => response.json())
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [url]);

  // ...
};
```

## 실습

### 실습 1. axios 설치하기

먼저 커맨드 라인에서 axios를 설치한다.

```bash
npm install axios
```

### 실습 2. `App` 컴포넌트 파일에서 axios 임포트하기

그다음, `App` 컴포넌트 파일에서 axios를 임포트한다.

```jsx
// src/App.jsx
import * as React from 'react';
import axios from 'axios';

// ...
```

### 실습 3. `fetch` 대신 `axios.get()` 사용하기

`fetch` 대신 axios를 사용할 수 있다. 사용법은 네이티브 `fetch` API와 거의 동일하다. URL을 인자로 받고 `Promise`를 반환한다. axios는 결과를 자바스크립트의 `data` 객체로 감싸서 돌려주기 때문에, 반환된 응답을 JSON으로 변환할 필요가 더 이상 없다. 다만 반환되는 데이터 구조에 맞게 코드를 조정해야 한다.

```jsx
// src/App.jsx
const App = () => {
  // ...

  const handleFetchStories = React.useCallback(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    axios
      .get(url)
      .then((result) => {
        dispatchStories({
          type: 'STORIES_FETCH_SUCCESS',
          payload: result.data.hits,
        });
      })
      .catch(() =>
        dispatchStories({ type: 'STORIES_FETCH_FAILURE' })
      );
  }, [url]);

  // ...
};
```

이 코드에서는 명시적인 HTTP GET 요청을 위해 `axios.get()`을 호출했는데, 이는 브라우저의 네이티브 `fetch` API를 기본값으로 사용했을 때와 동일한 HTTP 메서드다. `axios.post()`처럼 HTTP POST 같은 다른 HTTP 메서드도 사용할 수 있다. 이 예제들을 통해 axios가 원격 API에 요청을 수행하는 강력한 라이브러리라는 것을 확인할 수 있다. **요청이 복잡해지거나, 오래된 브라우저를 지원해야 하거나, 테스트를 다뤄야 할 때는 네이티브 `fetch` API보다 axios를 추천한다.**

## 정리

- 브라우저 지원 범위, 헤드리스 테스트 환경 호환성 같은 이유로 네이티브 `fetch` API 대신 **axios** 같은 서드파티 라이브러리를 선택하기도 한다.
- axios의 사용법은 `fetch`와 거의 비슷하다. URL을 받아 `Promise`를 반환한다.
- axios는 응답을 자동으로 `data` 객체로 감싸주므로, `response.json()`으로 직접 변환할 필요가 없다. 대신 `result.data.hits`처럼 데이터 구조가 살짝 달라진다는 점에 유의해야 한다.
- axios는 `axios.get()`, `axios.post()` 등 다양한 HTTP 메서드를 명시적으로 제공한다.
- 요청이 복잡해지거나, 오래된 브라우저 지원, 테스트 환경 호환성이 중요할 때는 axios 같은 라이브러리가 네이티브 `fetch`보다 유리할 수 있다.

## Q&A

- **Q. 리액트 맥락에서 Axios란 무엇인가?**
  - A. Axios는 HTTP 요청을 수행하기 위한 인기 있는 자바스크립트 라이브러리로, 리액트 애플리케이션의 데이터 페칭에 흔히 사용된다.
- **Q. Axios는 리액트에서 Fetch API와 어떻게 다른가?**
  - A. Axios는 네이티브 Fetch API에 비해 더 다양한 기능과 더 편리한 API를 제공하는 서드파티 라이브러리다.
- **Q. 리액트 프로젝트에서 Fetch 대신 Axios를 선택하는 이유는 무엇인가?**
  - A. Axios는 자동 JSON 파싱, 요청/응답 인터셉터, 더 나은 브라우저 지원 같은 기능을 제공해서 많은 개발자가 선호하는 선택지가 된다.
- **Q. 리액트에서 Axios로 GET 요청은 어떻게 만드는가?**
  - A. `axios.get(url)`을 사용해서 Axios로 GET 요청을 만든다.
- **Q. 리액트의 Axios 요청에서 에러 처리는 어떻게 하는가?**
  - A. Axios는 요청에서 에러를 처리할 수 있는 `.catch()` 메서드를 제공한다.
- **Q. 리액트에서 Fetch API를 사용하는 주된 장점은 무엇인가?**
  - A. Fetch API는 최신 브라우저에 내장돼 있어서 추가 의존성이 필요 없으며, 단순한 상황에서는 가벼운 선택지가 된다.
