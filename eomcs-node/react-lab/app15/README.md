# app15. Props 다루기 (심화)

## 개요

Props는 부모에서 자식으로, 컴포넌트 트리를 따라 아래로 전달된다. 컴포넌트와 컴포넌트 사이에서(때로는 중간의 다른 컴포넌트를 거쳐서) 정보를 전달하기 위해 Props를 자주 사용하다 보니, Props를 더 편리하게 다루는 몇 가지 기술을 알아두면 유용하다.

이번 실습에서는 특정 상황에서 코드를 더 간결하고 읽기 쉽고 유지보수하기 좋게 만들어주는 **Props 심화 기법**을 배운다.

> Java에 비유하면, 구조 분해(destructuring)는 record/getter를 통해 필드를 꺼내 쓰는 것과 비슷하고, 스프레드 연산자는 빌더 패턴에서 기존 객체의 필드를 복사해 새 객체를 만드는 것과 비슷하다.

## `App` 컴포넌트

이전 실습들에서 만든 아래 코드에서 시작한다.

```jsx
// src/App.jsx
const Search = (props) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={props.search}
      onChange={props.onSearch}
    />
  </div>
);

const List = (props) => (
  <ul>
    {props.list.map((item) => (
      <Item key={item.objectID} item={item} />
    ))}
  </ul>
);

const Item = (props) => (
  <li>
    <span>
      <a href={props.item.url}>{props.item.title}</a>
    </span>
    <span>{props.item.author}</span>
    <span>{props.item.num_comments}</span>
    <span>{props.item.points}</span>
  </li>
);
```

## 실습

### 실습 1. 객체 구조 분해(Object Destructuring)로 Props 다루기

리액트의 `props`는 그냥 자바스크립트 객체다. 그렇지 않다면 `props.list`나 `props.onSearch`처럼 접근할 수 없었을 것이다. 자바스크립트의 **객체 구조 분해(object destructuring)**를 쓰면 이 객체의 프로퍼티에 훨씬 편하게 접근할 수 있다.

```js
const user = {
  firstName: 'Robin',
  lastName: 'Wieruch',
};

// 구조 분해 없이
const firstName = user.firstName;
const lastName = user.lastName;

// 구조 분해로
const { firstName, lastName } = user;
```

먼저 `Search` 컴포넌트를 간결한 바디에서 블록 바디로 바꾸고, 함수 몸통 안에서 `props` 객체를 구조 분해한다.

```jsx
const Search = (props) => {
  const { search, onSearch } = props;

  return (
    <div>
      <label htmlFor="search">Search: </label>
      <input
        id="search"
        type="text"
        value={search}
        onChange={onSearch}
      />
    </div>
  );
};
```

### 실습 2. 함수 시그니처에서 바로 구조 분해하기

매번 컴포넌트를 블록 바디로 바꿔가며 구조 분해를 하는 건 번거롭다. 한 걸음 더 나아가서, **함수 시그니처 자체**에서 `props` 객체를 바로 구조 분해하면 다시 간결한 바디로 되돌릴 수 있다.

```jsx
const Search = ({ search, onSearch }) => (
  <div>
    <label htmlFor="search">Search: </label>
    <input
      id="search"
      type="text"
      value={search}
      onChange={onSearch}
    />
  </div>
);
```

리액트의 Props는 그 자체(`props` 객체)로 쓰이기보다, 그 안에 담긴 정보들이 개별적으로 쓰이는 경우가 대부분이다. 함수 시그니처에서 바로 구조 분해하면 `props`라는 컨테이너를 거치지 않고 필요한 정보에 곧바로 접근할 수 있다. `List`, `Item` 컴포넌트에도 똑같이 적용해보자.

```jsx
const List = ({ list }) => (
  <ul>
    {list.map((item) => (
      <Item key={item.objectID} item={item} />
    ))}
  </ul>
);

const Item = ({ item }) => (
  <li>
    <span>
      <a href={item.url}>{item.title}</a>
    </span>
    <span>{item.author}</span>
    <span>{item.num_comments}</span>
    <span>{item.points}</span>
  </li>
);
```

객체 구조 분해는 자바스크립트의 모범 사례에도 맞고, 리액트 컴포넌트를 더 깔끔하고 효율적으로 만들어준다.

## 실습 3. (심화) 중첩 구조 분해(Nested Destructuring)

`Item` 컴포넌트가 전달받는 `item`도 결국 `props`와 마찬가지로 자바스크립트 객체다. 그런데 `item` 자체는 `Item` 컴포넌트에서 직접 쓰이지 않고, 그 프로퍼티들만 각 엘리먼트에 전달된다. 이럴 때 **중첩 구조 분해**를 쓸 수도 있다.

```js
const user = {
  firstName: 'Robin',
  pet: {
    name: 'Trixi',
  },
};

// 중첩 구조 분해 없이
const firstName = user.firstName;
const name = user.pet.name;

// 중첩 구조 분해로
const {
  firstName,
  pet: { name },
} = user;
```

이를 `Item`에 적용하면 다음과 같다.

```jsx
const Item = ({ item: { title, url, author, num_comments, points } }) => (
  <li>
    <span>
      <a href={url}>{title}</a>
    </span>
    <span>{author}</span>
    <span>{num_comments}</span>
    <span>{points}</span>
  </li>
);
```

중첩 구조 분해는 `props`나 `state` 안에 중첩된 객체·배열을 다룰 때 특히 강력하고 효율적인 기법이다. 다만 함수 시그니처가 들여쓰기로 복잡해지는 단점도 있다. 여기서는 딱히 가독성을 높여주지 않지만, 다른 상황에서는 유용할 수 있다.

## 실습 4. 객체 대신 낱개의 프로퍼티를 하나하나 전달하기

이번에는 `List`가 `item` 객체 전체를 넘기는 대신, `item`의 프로퍼티를 하나하나 전달하도록 리팩터링해보자.

```jsx
const List = ({ list }) => (
  <ul>
    {list.map((item) => (
      <Item
        key={item.objectID}
        title={item.title}
        url={item.url}
        author={item.author}
        num_comments={item.num_comments}
        points={item.points}
      />
    ))}
  </ul>
);

const Item = ({ title, url, author, num_comments, points }) => (
  <li>
    <span>
      <a href={url}>{title}</a>
    </span>
    <span>{author}</span>
    <span>{num_comments}</span>
    <span>{points}</span>
  </li>
);
```

## 실습 5. (심화) 스프레드(Spread) 연산자 사용하기

`Item`의 함수 시그니처는 더 간결해졌지만, 이번엔 `List` 쪽에 프로퍼티를 하나하나 전달하는 번거로움이 생겼다. 자바스크립트의 **스프레드 연산자(`...`)**로 이 문제를 해결할 수 있다.

```js
const profile = { firstName: 'Robin', lastName: 'Wieruch' };
const address = { country: 'Germany', city: 'Berlin' };

const user = {
  ...profile,
  gender: 'male',
  ...address,
};

console.log(user);
// {
//   firstName: "Robin",
//   lastName: "Wieruch",
//   gender: "male",
//   country: "Germany",
//   city: "Berlin",
// }
```

스프레드 연산자는 객체의 모든 키/값 쌍을 다른 객체(또는 JSX 엘리먼트의 속성)로 그대로 펼쳐 넣는다.

```jsx
const List = ({ list }) => (
  <ul>
    {list.map((item) => (
      <Item key={item.objectID} {...item} />
    ))}
  </ul>
);

const Item = ({ title, url, author, num_comments, points }) => (
  <li>
    <span>
      <a href={url}>{title}</a>
    </span>
    <span>{author}</span>
    <span>{num_comments}</span>
    <span>{points}</span>
  </li>
);
```

## 실습 6. (심화) 레스트(Rest) 연산자 사용하기

마지막으로 **레스트 연산자(rest operator)**를 사용해보자. 레스트 연산자는 항상 객체 구조 분해의 **마지막**에 등장한다.

```js
const user = {
  id: '1',
  firstName: 'Robin',
  lastName: 'Wieruch',
  country: 'Germany',
  city: 'Berlin',
};

const { id, country, city, ...userWithoutAddress } = user;

console.log(userWithoutAddress);
// { firstName: "Robin", lastName: "Wieruch" }

console.log(id); // "1"
console.log(city); // "Berlin"
```

문법(점 세 개)은 스프레드 연산자와 똑같지만, 대입식의 **왼쪽**(구조 분해)에서 쓰이면 레스트 연산자, **오른쪽**(객체를 만들 때)에서 쓰이면 스프레드 연산자다. `objectID`는 `Item`에서 직접 쓰이지 않고 `key`로만 쓰이므로, 레스트 연산자로 `objectID`를 따로 떼어내고 나머지 `item`만 스프레드로 전달할 수 있다.

```jsx
const List = ({ list }) => (
  <ul>
    {list.map(({ objectID, ...item }) => (
      <Item key={objectID} {...item} />
    ))}
  </ul>
);

const Item = ({ title, url, author, num_comments, points }) => (
  <li>
    <span>
      <a href={url}>{title}</a>
    </span>
    <span>{author}</span>
    <span>{num_comments}</span>
    <span>{points}</span>
  </li>
);
```

## 결국 어떤 방식을 쓸 것인가?

이번 실습에서 살펴본 변형(중첩 구조 분해, 스프레드/레스트 연산자)이 항상 더 나은 것은 아니다. 앞으로 이 프로젝트에서는 아래처럼 **가장 이해하기 쉬운 원래 버전**을 계속 사용한다.

```jsx
const List = ({ list }) => (
  <ul>
    {list.map((item) => (
      <Item key={item.objectID} item={item} />
    ))}
  </ul>
);

const Item = ({ item }) => (
  <li>
    <span>
      <a href={item.url}>{item.title}</a>
    </span>
    <span>{item.author}</span>
    <span>{item.num_comments}</span>
    <span>{item.points}</span>
  </li>
);
```

- 가장 간결한 버전은 아니지만, 가장 이해하기 쉽다. 
- 중첩 구조 분해는 별다른 이득 없이 컴포넌트만 복잡하게 만들었고, 
- 스프레드/레스트 연산자는 모두에게 익숙하지 않은 고급 자바스크립트 문법을 요구한다. 
- 리팩터링할 때는 항상 **가독성**을 최우선으로 고려하고, 특히 팀으로 작업한다면 모두가 같은 코드 스타일을 따르도록 하자.

## 기준(Rules of thumb)

| 상황 | 권장 방식 |
| --- | --- |
| 함수 컴포넌트에서 props를 사용할 때 | 함수 시그니처에서 **객체 구조 분해**를 사용한다 (props 자체가 그대로 다음 컴포넌트로만 전달되는 경우는 예외) |
| 객체의 모든 키/값 쌍을 자식 컴포넌트에 그대로 전달할 때 | **스프레드 연산자**(`{...props}`)를 사용한다 |
| props 객체에서 일부 프로퍼티만 따로 떼어내고 싶을 때 | **레스트 연산자**를 사용한다 |
| 중첩된 객체/배열을 다룰 때 | 가독성이 좋아질 때만 **중첩 구조 분해**를 사용한다 |

## Q&A

- **Q. 함수 컴포넌트의 매개변수에서 props는 어떻게 구조 분해하는가?**
  - A. 함수 매개변수에서 바로 구조 분해할 수 있다. 예: `function MyComponent({ prop1, prop2 }) {...}`
- **Q. props를 구조 분해하면서 기본값을 줄 수 있는가?**
  - A. 가능하다. 예: `{ prop1 = 'default', prop2 }`
- **Q. props를 반드시 전부 구조 분해해야 하는가, 일부만 선택할 수도 있는가?**
  - A. 컴포넌트에 필요한 프로퍼티만 선택적으로 구조 분해할 수 있다.
- **Q. 리액트 props에서 스프레드 연산자(`...`)는 어떻게 사용하는가?**
  - A. 객체의 모든 프로퍼티를 각각의 prop으로 전달할 때 사용한다. 예: `<MyComponent {...obj} />`
- **Q. 스프레드 연산자로 기존 props에 새로운 prop을 추가해서 전달할 수 있는가?**
  - A. 가능하다. 예: `<MyComponent {...props} newProp={value} />`
- **Q. 스프레드 연산자는 객체를 얕은 복사(shallow copy)하는가, 깊은 복사(deep copy)하는가?**
  - A. 얕은 복사를 만든다. 즉 중첩된 객체는 여전히 원본을 참조한다.
- **Q. 리액트에서 레스트 연산자(`...rest`)의 목적은 무엇인가?**
  - A. 나머지 프로퍼티들을 새로운 객체로 모으는 데 사용하며, props 구조 분해와 함께 자주 쓰인다.
- **Q. `useState` 같은 훅은 배열 구조 분해를, props는 객체 구조 분해를 쓰는 이유는 무엇인가?**
  - A. `useState`는 배열을 반환하고 props는 객체이기 때문에, 각 데이터 구조에 맞는 구조 분해 방식을 사용해야 한다. `useState`가 배열을 반환하는 덕분에 구조 분해할 때 원하는 이름을 자유롭게 붙일 수 있다는 장점이 있다.
- **Q. 리액트에서 프롭 드릴링(prop drilling)이란 무엇인가?**
  - A. props를 여러 단계의 컴포넌트를 거쳐 깊숙이 중첩된 자식 컴포넌트까지 전달하는 과정을 말한다.
