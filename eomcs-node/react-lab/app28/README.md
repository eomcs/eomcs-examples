# app28. 리액트에서 데이터 다시 가져오기

## 개요

이제 샘플 데이터 대신 원격 API에서 가져온 데이터를 사용하게 되어, 더 실감 나는 환경을 갖추게 됐다. 앞선 실습을 마친 뒤 애플리케이션을 실행해봤다면, 뭔가 부족하다는 느낌을 받았을 수도 있다. 미리 정해둔 쿼리('react')로 데이터를 가져오기 때문에, 우리는 항상 "React"와 관련된 게시물만 보게 된다. 검색 기능이 있긴 하지만, 이미 있는 story만 필터링할 수 있을 뿐이다. 그래서 이 검색 기능을 **클라이언트 사이드 검색(client-side search)** 이라고 부른다. 원격 API와는 상호작용하지 않고, 이미 가지고 있는 데이터 안에서만 동작하기 때문이다.

클라이언트 사이드 검색은 (초기 데이터 페칭 이후) 클라이언트에 있는 story만 필터링하는 반면, **서버 사이드 검색(server-side search)** 은 검색어를 바탕으로 원격 API에서 데이터를 가져올 수 있게 해준다. 클라이언트 사이드 검색과 서버 사이드 검색의 본질적인 차이는 검색 연산이 어디서 일어나느냐다. 클라이언트 사이드 검색은 사용자의 기기에서 일어나서 응답이 빠르지만 대용량 데이터셋에는 덜 적합할 수 있다. 서버 사이드 검색은 서버에서 일어나므로 대용량 데이터셋에 더 적합하지만, 서버 왕복 때문에 사용자 응답 시간이 느려질 수 있다. 어느 쪽을 택할지는 데이터셋 크기, 검색의 복잡도, 성능 고려사항 같은 요소에 달려 있다. **이번 실습에서는 클라이언트 사이드 검색을 서버 사이드 검색으로 바꿔본다.**

**과제**: 검색 기능은 이미 있는 데이터만 필터링하는 클라이언트 사이드 검색이다. 대신 검색어와 관련된 데이터를 가져오는 데 검색 기능을 사용할 수 있어야 한다.

**힌트**:

- API에서 이미 필터링된 데이터를 직접 받아올 것이므로, 계산된 값인 `searchedStories`는 없애도 된다.
- 데이터를 가져오는 과정에서 하드코딩된 `'react'`를 동적인 `searchTerm`으로 바꾼다.
- `searchTerm`이 빈 문자열인 예외 상황을 처리한다.

> Java에 비유하면, 클라이언트 사이드 검색은 DB에서 전체 목록을 미리 다 가져와 자바 컬렉션의 스트림(`stream().filter(...)`)으로 걸러내는 것과 비슷하고, 서버 사이드 검색은 매번 조건에 맞는 `WHERE` 절을 담은 새 쿼리를 DB에 다시 날리는 것과 비슷하다. `useEffect`의 의존성 배열에 `searchTerm`을 추가하는 것은, 검색 파라미터가 바뀔 때마다 그 쿼리를 다시 실행하도록 트리거를 거는 것에 해당한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const API_ENDPOINT = 'https://hn.algolia.com/api/v1/search?query=';

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, dispatchStories] = React.useReducer(
    storiesReducer,
    { data: [], isLoading: false, isError: false }
  );

  React.useEffect(() => {
    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}react`)
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
  }, []);

  const searchedStories = stories.data.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // ...
};
```

## 실습

클라이언트 사이드 검색에서 서버 사이드 검색으로 애플리케이션을 옮기는 데는 그리 많은 단계가 필요하지 않다.

### 실습 1. `searchedStories` 제거하고 `stories.data`를 바로 전달하기

먼저 `searchedStories`를 제거한다. 검색어로 필터링된 story는 API로부터 받아올 것이기 때문이다. `List` 컴포넌트에는 그냥 일반적인 `stories`만 전달한다.

```jsx
const App = () => {
  // ...

  return (
    <div>
      {/* ... */}

      {stories.isLoading ? (
        <p>Loading ...</p>
      ) : (
        <List list={stories.data} onRemoveItem={handleRemoveStory} />
      )}
    </div>
  );
};
```

### 실습 2. 하드코딩된 검색어 대신 `searchTerm` 사용하기

하드코딩된 검색어(`'react'`) 대신 컴포넌트 상태에 있는 실제 `searchTerm`을 사용한다. 이렇게 하면 사용자가 입력 필드로 검색할 때마다, 그 `searchTerm`으로 원격 API에 해당 story들을 요청하게 된다. 추가로 `searchTerm`이 빈 문자열인 예외 상황도 처리해서, 요청 자체가 발생하지 않도록 막아야 한다.

```jsx
const App = () => {
  // ...

  React.useEffect(() => {
    if (searchTerm === '') return;

    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}${searchTerm}`)
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
  }, []);

  // ...
};
```

### 실습 3. 의존성 배열에 `searchTerm` 추가하기

이제 결정적으로 빠진 부분이 하나 있다. 초기 데이터 페칭은 `searchTerm`(여기서는 초기 상태로 설정된 `'React'`)을 반영하지만, 사용자가 입력 필드에 타이핑해서 `searchTerm`이 바뀌어도 그 값이 반영되지 않는다. `useEffect` 훅의 의존성 배열을 살펴보면 비어 있다는 것을 알 수 있다. 이는 이 사이드 이펙트가 `App` 컴포넌트의 최초 렌더링에서만 실행된다는 뜻이다. `searchTerm`이 바뀔 때도 이 사이드 이펙트를 실행하고 싶다면, 의존성 배열에 이 값을 포함시켜야 한다.

```jsx
const App = () => {
  // ...

  React.useEffect(() => {
    // `searchTerm`이 존재하지 않으면
    // 예: null, 빈 문자열, undefined
    // 아무것도 하지 않는다
    // searchTerm === '' 보다 더 일반화된 조건

    if (!searchTerm) return;

    dispatchStories({ type: 'STORIES_FETCH_INIT' });

    fetch(`${API_ENDPOINT}${searchTerm}`)
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
  }, [searchTerm]);

  // ...
};
```

이렇게 해서 클라이언트 사이드 검색에서 서버 사이드 검색으로 기능을 옮겼다. 정해진 story 목록을 클라이언트에서 필터링하는 대신, 이제는 `searchTerm`을 사용해서 서버에서 필터링된 목록을 가져온다. 서버 사이드 검색은 초기 데이터 페칭뿐 아니라 `searchTerm`이 바뀔 때도 일어난다. 이제 검색 기능은 완전히 서버 사이드로 동작한다.

**참고**: 다만 키 입력마다 데이터를 다시 가져오는 것은 최선의 방식이 아니다. 이 구현은 잦은 요청으로 API에 부담을 준다. 요청이 지나치게 많아지면 많은 API가 대량 요청으로부터 자신을 보호하기 위해 적용하는 **요청 제한(rate limiting)**(예: 1분에 X회까지만 요청 허용) 때문에 API 에러로 이어질 수 있다. 이 문제는 곧 다음 실습에서 다룰 예정이다.

## 정리

- **클라이언트 사이드 검색**은 이미 가져온 데이터를 클라이언트에서 필터링하는 방식이고, **서버 사이드 검색**은 검색어를 바탕으로 서버에서 필터링된 데이터를 새로 요청하는 방식이다.
- 대용량 데이터셋이나 복잡한 검색 로직이 서버 쪽에 있을 때는 서버 사이드 검색이 더 적합하다.
- `useEffect`의 의존성 배열에 `searchTerm`을 추가하면, `searchTerm`이 바뀔 때마다 데이터 페칭이 다시 실행된다.
- `searchTerm`이 빈 값(`''`, `null`, `undefined` 등)일 때는 불필요한 요청이 발생하지 않도록 사이드 이펙트 안에서 이른 반환(early return)으로 막아야 한다.
- 키 입력마다 요청을 다시 보내는 방식은 API에 부담을 주고 요청 제한(rate limiting)에 걸릴 위험이 있어, 앞으로 개선이 필요하다.

## Q&A

- **Q. 클라이언트 사이드 검색(client-side searching)이란 무엇인가?**
  - A. 클라이언트 사이드 검색은 사용자의 기기나 브라우저에서 데이터를 필터링하고 조작하는 것을 말한다.
- **Q. 클라이언트 사이드 검색은 성능에 어떤 영향을 주는가?**
  - A. 응답 시간이 빠를 수 있지만, 서버에서 미리 로드해야 하는 데이터 양에 따라 제약을 받을 수 있다.
- **Q. 서버 사이드 검색(server-side searching)이란 무엇인가?**
  - A. 서버 사이드 검색은 검색 쿼리를 서버로 보내서 그곳에서 데이터를 필터링한 뒤, 결과를 클라이언트로 반환받는 방식이다.
- **Q. 서버 사이드 검색은 언제 선호되는가?**
  - A. 대용량 데이터셋이거나, 복잡한 검색 로직과 데이터가 서버에 있을 때 서버 사이드 검색이 더 바람직하다.
- **Q. 클라이언트 사이드 검색의 잠재적 단점은 무엇인가?**
  - A. 대용량 데이터셋에서 한계가 있을 수 있고, 초기 페이지 로딩이 느려지며, 방대한 데이터를 클라이언트에 로드해야 할 수 있다.
- **Q. 클라이언트 사이드 검색에서 잦은 API 요청은 어떤 영향을 미치는가?**
  - A. 잦은 요청은 API에 부담을 줄 수 있으며, 특히 API가 요청 제한(rate limiting) 조치를 두고 있다면 에러로 이어질 수 있다.
- **Q. 잦은 API 요청으로 인한 성능 문제는 어떻게 해결할 수 있는가?**
  - A. 디바운싱(debouncing)이나 스로틀링(throttling) 기법을 적용하면 잦은 API 요청의 영향을 줄이고 서버 과부하를 막을 수 있다.
