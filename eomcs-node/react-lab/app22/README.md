# app22. JSX의 인라인 핸들러

## 개요

이번 실습에서는 리액트의 새로운 기본 빌딩 블록인 **인라인 핸들러(inline handler)**를 배운다. 동시에 리스트에서 아이템을 제거하는 기능을 함께 구현해본다.

**과제**: 애플리케이션은 아이템 목록을 렌더링하고, 검색 기능으로 목록을 필터링할 수 있게 해준다. 이제 각 리스트 아이템 옆에 버튼을 렌더링해서, 사용자가 그 아이템을 목록에서 제거할 수 있도록 해야 한다.

**힌트**:

- 나중에 조작(예: 아이템 제거)할 수 있으려면, 아이템 목록을 `useState`로 상태화된 값(여기서는 상태 배열)으로 만들어야 한다.
- 각 리스트 아이템은 클릭 핸들러가 달린 버튼을 렌더링한다. 버튼을 클릭하면 상태를 조작해서 목록에서 해당 아이템이 제거된다.
- 상태를 가진 목록은 `App` 컴포넌트에 있으므로, `Item` 컴포넌트가 식별자로 아이템을 제거하려면 `App` 컴포넌트와 소통할 수 있는 콜백 핸들러가 필요하다.

> Java에 비유하면, 콜백 핸들러는 자식 컴포넌트가 부모 컴포넌트의 메서드를 마치 `Consumer<Item>` 같은 함수형 인터페이스를 주입받아 호출하는 것과 비슷하다. 인라인 핸들러는 그 `Consumer`를 익명 람다로 즉석에서 만들어 바로 넘기는 것에 해당한다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다. 지금 `App` 컴포넌트가 가진 `stories`(원서에서는 목록 예시로 사용) 목록은 아직 **상태가 없는(unstateful)** 변수다. 검색 기능으로 렌더링되는 목록을 필터링할 수는 있지만, 목록 자체는 그대로다.

```jsx
// src/App.jsx
const initialStories = [
  {
    title: 'React',
    url: 'https://react.dev/',
    author: 'Jordan Walke',
    num_comments: 3,
    points: 4,
    objectID: 0,
  },
  {
    title: 'Redux',
    url: 'https://redux.js.org/',
    author: 'Dan Abramov, Andrew Clark',
    num_comments: 2,
    points: 5,
    objectID: 1,
  },
];

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const searchedStories = initialStories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // ...
};
```

## 실습

### 실습 1. `stories`를 `useState`로 상태화하기

목록을 조작할 수 있으려면, 먼저 리액트의 `useState` 훅으로 초기 상태를 만들어 목록에 대한 제어권을 가져와야 한다. 배열에서 반환되는 값은 현재 상태(`stories`)와 상태 갱신 함수(`setStories`)다.

```jsx
const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', 'React');

  const [stories, setStories] = React.useState(initialStories);

  const searchedStories = stories.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // ...
};
```

`useState` 훅에서 상태화된 목록으로 반환된 `stories`도 여전히 `searchedStories`로 필터링되어 `List` 컴포넌트에 표시되므로, 애플리케이션은 이전과 동일하게 동작한다. 달라진 것은 `stories`가 오는 출처뿐이다. 아직 실제로 목록을 수정하지는 않는다.

### 실습 2. 아이템을 제거하는 이벤트 핸들러 작성하기

목록에서 아이템을 제거하는 이벤트 핸들러를 작성해보자.

```jsx
const App = () => {
  // ...

  const [stories, setStories] = React.useState(initialStories);

  const handleRemoveStory = (item) => {
    const newStories = stories.filter(
      (story) => item.objectID !== story.objectID
    );

    setStories(newStories);
  };

  // ...

  return (
    <div>
      <h1>My Hacker Stories</h1>

      {/* ... */}

      <hr />

      <List list={searchedStories} onRemoveItem={handleRemoveStory} />
    </div>
  );
};
```

- `App` 컴포넌트의 이 콜백 핸들러는 (나중에 `List`/`Item` 컴포넌트에서 사용될 것인데) 제거해야 할 아이템을 인자로 받는다. 
- 이 정보를 바탕으로, 조건을 만족하지 않는 모든 아이템을 제거해서 현재 `stories`를 필터링한다. 
- 원하는 아이템(story)이 제거된 새 `stories`는 새로운 상태로 설정되어 `List` 컴포넌트에 전달된다. 
- 새 상태가 설정됐으므로 `App` 컴포넌트와 그 하위 컴포넌트들(예: `List`/`Item`)이 다시 렌더링되어, 갱신된 `stories` 상태를 화면에 보여준다.

### 실습 3. `List`/`Item` 컴포넌트에서 콜백 핸들러 사용하기

`List` 컴포넌트 자신은 이 새로운 콜백 핸들러를 사용하지 않고, `Item` 컴포넌트로 그대로 전달하기만 한다.

```jsx
const List = ({ list, onRemoveItem }) => (
  <ul>
    {list.map((item) => (
      <Item
        key={item.objectID}
        item={item}
        onRemoveItem={onRemoveItem}
      />
    ))}
  </ul>
);
```

마지막으로 `Item` 컴포넌트가 전달받은 콜백 핸들러를, 새로운 핸들러 안에서 함수로 사용한다. 이 핸들러 안에서 특정 아이템을 콜백 핸들러에 넘긴다. 실제 이벤트를 발생시킬 버튼 엘리먼트도 추가해야 한다.

```jsx
const Item = ({ item, onRemoveItem }) => {
  const handleRemoveItem = () => {
    onRemoveItem(item);
  };

  return (
    <li>
      <span>
        <a href={item.url}>{item.title}</a>
      </span>
      <span>{item.author}</span>
      <span>{item.num_comments}</span>
      <span>{item.points}</span>
      <span>
        <button type="button" onClick={handleRemoveItem}>
          Dismiss
        </button>
      </span>
    </li>
  );
};
```

이 기능을 구현하기 위해 앞서 배운 여러 내용, 즉 state, props, 핸들러, 콜백 핸들러를 모두 활용했다.

- 리액트의 `useState` 훅으로 `stories` 목록을 상태화하고, 
- 여전히 검색된 목록을 `List` 컴포넌트에 props로 전달했으며, 
- 각 컴포넌트에서 사용할 콜백 핸들러(`handleRemoveStory`)와 핸들러(`handleRemoveItem`)를 구현해서 버튼 클릭으로 story를 제거하는 기능을 완성했다. 

### 실습 4. 인라인 핸들러로 더 간결하게 만들기

`Item` 컴포넌트에서 전달받은 `onRemoveItem` 콜백 핸들러를 실행하기 위해, `handleRemoveItem`이라는 추가 핸들러를 도입해야 했다는 점을 눈치챘을 것이다. 콜백 핸들러의 인자로 아이템을 넘기기 위해 이 별도의 이벤트 핸들러를 만들어야 했다.

좀 더 우아하게 만들고 싶다면, **인라인 핸들러(inline handler)**를 사용해서 `Item` 컴포넌트의 콜백 핸들러 함수를 JSX 안에서 바로 실행할 수 있다. `Item` 컴포넌트에서 전달받은 `onRemoveItem` 함수를 인라인 핸들러로 사용하는 방법은 두 가지다.

첫 번째는 자바스크립트의 `bind` 메서드를 사용하는 방법이다.

```jsx
const Item = ({ item, onRemoveItem }) => (
  <li>
    <span>
      <a href={item.url}>{item.title}</a>
    </span>
    <span>{item.author}</span>
    <span>{item.num_comments}</span>
    <span>{item.points}</span>
    <span>
      <button type="button" onClick={onRemoveItem.bind(null, item)}>
        Dismiss
      </button>
    </span>
  </li>
);
```

- 함수에 자바스크립트의 `bind` 메서드를 사용하면, 그 함수를 실행할 때 사용할 인자를 함수에 직접 묶을(bind) 수 있다. 
- `bind` 메서드는 묶인 인자가 함께 붙은 새로운 함수를 반환한다.

### 실습 5. 인라인 화살표 함수 더 간결하게 만들기

두 번째, 그리고 더 널리 쓰이는 방법은 **인라인 화살표 함수(inline arrow function)**를 사용하는 것이다. 이 방식은 `item` 같은 인자를 슬쩍 끼워 넣을 수 있게 해준다.

```jsx
const Item = ({ item, onRemoveItem }) => (
  <li>
    <span>
      <a href={item.url}>{item.title}</a>
    </span>
    <span>{item.author}</span>
    <span>{item.num_comments}</span>
    <span>{item.points}</span>
    <span>
      <button type="button" onClick={() => onRemoveItem(item)}>
        Dismiss
      </button>
    </span>
  </li>
);
```

**인라인 핸들러를 사용하면** 일반 이벤트 핸들러를 쓸 때보다 더 간결해지지만, **자바스크립트 로직이 JSX 안에 숨어버려서 디버깅이 더 어려워질 수도 있다.** 

**인라인 화살표 함수가** 간결한(concise) 몸체 대신 블록(block) 몸체를 사용해서 **한 줄 이상의 구현 로직을 담게 되면, 코드는 더욱 장황해진다.**

```jsx
const Item = ({ item, onRemoveItem }) => (
  <li>
    {/* ... */}
    <span>
      <button
        type="button"
        onClick={() => {
          // 뭔가 다른 작업을 한다

          // 주의: JSX 안에 복잡한 로직을 두는 것은 피하자

          onRemoveItem(item);
        }}
      >
        Dismiss
      </button>
    </span>
  </li>
);
```

경험칙(rule of thumb)은 이렇다. **인라인 핸들러가 핵심 구현 세부사항을 가리지 않는다면 사용해도 괜찮다.** 만약 인라인 핸들러가 한 줄 이상의 코드를 실행해야 해서 블록 몸체가 필요해진다면, 그때는 일반 이벤트 핸들러로 분리해낼 시점이다. 결국 이 예제에서는 어떤 핸들러 버전을 쓰든 가독성이 있으므로 모두 받아들일 만하다.

## 정리

- 조작 가능한 목록을 만들려면 `React.useState`로 상태화해야 한다.
- 콜백 핸들러(`handleRemoveStory`)는 `App` 컴포넌트에서 상태를 갱신하고, `List` → `Item`으로 전달되어 자식 컴포넌트가 부모의 상태를 조작할 수 있게 해준다.
- **인라인 핸들러**를 사용하면 별도의 핸들러 함수 없이, JSX 안에서 바로 콜백 핸들러에 인자를 넘겨 실행할 수 있다. `bind` 메서드나 인라인 화살표 함수로 구현할 수 있으며, 화살표 함수 방식이 더 널리 쓰인다.
- 인라인 핸들러는 간결하지만 로직이 JSX 안에 숨어 디버깅이 어려워질 수 있으므로, 한 줄 이상의 로직이 필요하다면 일반 핸들러로 분리하는 것이 좋다.

## Q&A

- **Q. 리액트에서 인라인 함수(inline function)란 무엇인가?**
  - A. 리액트에서 인라인 함수는 보통 JSX 안에 직접 정의되는 함수를 말한다.
- **Q. 리액트 이벤트 핸들러에서 인라인 함수를 사용하는 이점은 무엇인가?**
  - A. 인라인 함수를 사용하면 추가적인 인자를 쉽게 전달할 수 있다.
- **Q. 리액트 이벤트 핸들러에서 인라인 함수의 대안은 무엇인가?**
  - A. 렌더링 로직 바깥에 핸들러 함수를 만들어두고, 그 함수에 대한 참조를 전달하는 것이 대안이 될 수 있다.
- **Q. 리액트 JSX 이벤트 핸들러에서 인라인 함수를 만드는 문법은 무엇인가?**
  - A. `onClick={() => myFunction()}`처럼, 이벤트 핸들러 속성 안에 화살표 함수 문법을 직접 사용한다.
