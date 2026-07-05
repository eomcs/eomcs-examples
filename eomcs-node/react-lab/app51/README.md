# app51. 리액트에서 TypeScript 사용하기

## 개요

자바스크립트와 리액트에 **TypeScript**를 도입하면 견고한 애플리케이션을 만드는 데 여러 이점이 있다. 타입 오류를 커맨드 라인이나 브라우저에서 런타임에 발견하는 대신, **TypeScript는 컴파일 타임에 IDE 안에서 바로 오류를 보여준다. 이는 개발자의 피드백 루프를 단축시키는 동시에 개발 경험을 향상시킨다.** 또한 모든 변수가 타입과 함께 정의되므로 코드가 좀 더 스스로를 설명하고(self-documenting) 읽기 쉬워진다. 코드 블록을 옮기거나 대규모 리팩터링을 수행하는 작업도 훨씬 효율적으로 바뀐다. TypeScript 같은 정적 타입 언어는 자바스크립트 같은 동적 타입 언어에 비해 이런 이점들 덕분에 점점 더 널리 쓰이고 있다.

> Java에 비유하면, 지금까지 순수 자바스크립트로 작성한 컴포넌트들은 마치 컴파일러 없이 오직 런타임에만 타입 오류를 발견할 수 있는 언어로 자바 코드를 작성해온 것과 비슷하다. TypeScript를 도입하는 것은 이 코드베이스에 자바의 정적 타입 검사와 컴파일 단계를 그대로 들여오는 것에 해당한다. `Story`, `StoriesAction` 같은 TypeScript의 `type` 정의는 자바의 클래스나 인터페이스와 비슷한 역할을 하고, 유니온 타입(`A | B`)은 자바의 `sealed interface`와 그 구현체들의 조합, 혹은 여러 타입 중 하나임을 나타내는 개념과 견줄 수 있다.

## 실습

### 실습 1. TypeScript와 관련 의존성 설치하기

Vite로 만든 리액트 프로젝트에서 TypeScript를 사용하려면, 커맨드 라인에서 TypeScript와 그 의존성들을 설치한다.

```bash
npm install typescript @types/react @types/react-dom --save-dev
npm install @typescript-eslint/eslint-plugin --save-dev
npm install @typescript-eslint/parser --save-dev
```

### 실습 2. TypeScript 설정 파일 세 개 추가하기

브라우저 환경을 위한 설정 파일 하나, Node 환경을 위한 설정 파일 하나, 그리고 이 둘을 합치는 설정 파일 하나, 이렇게 세 개의 TypeScript 설정 파일을 추가한다.

```bash
touch tsconfig.json tsconfig.app.json tsconfig.node.json
```

브라우저 환경을 위한 TypeScript 파일에는 다음 설정을 작성한다.

```json
// tsconfig.app.json
{
  "compilerOptions": {
    "tsBuildInfoFile": "./node_modules/.tmp/tsconfig.app.tsbuildinfo",
    "target": "ES2020",
    "useDefineForClassFields": true,
    "lib": ["ES2020", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "skipLibCheck": true,

    /* Bundler mode */
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "isolatedModules": true,
    "moduleDetection": "force",
    "noEmit": true,
    "jsx": "react-jsx",

    /* Linting */
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "noUncheckedSideEffectImports": true
  },
  "include": ["src"]
}
```

그다음 Node 환경을 위한 TypeScript 파일에는 다음과 같은 설정을 추가로 작성한다.

```json
// tsconfig.node.json
{
  "compilerOptions": {
    "tsBuildInfoFile": "./node_modules/.tmp/tsconfig.node.tsbuildinfo",
    "target": "ES2022",
    "lib": ["ES2023"],
    "module": "ESNext",
    "skipLibCheck": true,

    /* Bundler mode */
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "isolatedModules": true,
    "moduleDetection": "force",
    "noEmit": true,

    /* Linting */
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "noUncheckedSideEffectImports": true
  },
  "include": ["vite.config.ts"]
}
```

마지막으로 두 설정을 메인 TypeScript 설정 파일에서 합친다.

```json
// tsconfig.json
{
  "files": [],
  "references": [
    { "path": "./tsconfig.app.json" },
    { "path": "./tsconfig.node.json" }
  ]
}
```

### 실습 3. ESLint 설정을 TypeScript용으로 조정하기

기존에 ESLint 설정이 있다면, 이 역시 TypeScript에 맞게 조정해야 한다.

```js
// eslint.config.js
import js from "@eslint/js";
import globals from "globals";
import reactHooks from "eslint-plugin-react-hooks";
import reactRefresh from "eslint-plugin-react-refresh";
import tseslint from "typescript-eslint";

export default tseslint.config(
  { ignores: ["dist"] },
  {
    extends: [js.configs.recommended, ...tseslint.configs.recommended],
    files: ["**/*.{ts,tsx}"],
    languageOptions: {
      ecmaVersion: 2020,
      globals: globals.browser,
    },
    plugins: {
      "react-hooks": reactHooks,
      "react-refresh": reactRefresh,
    },
    rules: {
      ...reactHooks.configs.recommended.rules,
      "react/prop-types": "off",
      "react-refresh/only-export-components": [
        "warn",
        { allowConstantExport: true },
      ],
    },
  }
);
```

### 실습 4. `.jsx` 파일을 `.tsx` 파일로 이름 바꾸기

이제 모든 자바스크립트 파일(`.jsx`)의 이름을 TypeScript 파일(`.tsx`)로 바꾼다.

```bash
mv src/main.jsx src/main.tsx
mv src/App.jsx src/App.tsx
```

그리고 `index.html` 파일에서도 자바스크립트 파일 대신 새로운 TypeScript 파일을 참조하도록 수정한다.

```html
<!-- index.html -->
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Vite + React</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
```

프로젝트 루트에 다음 내용으로 `vite-env.d.ts` 파일도 새로 만들어야 할 수 있다.

```ts
/// <reference types="vite/client" />
```

커맨드 라인에서 개발 서버를 다시 시작한다. 브라우저와 에디터/IDE에서 컴파일 에러를 마주칠 수 있다. 이름을 바꾼 TypeScript 파일(예: `src/App.tsx`)을 열었을 때 에디터/IDE에서 아무 에러도 보이지 않는다면, 에디터용 TypeScript 플러그인이나 IDE용 TypeScript 확장을 설치해보자. 보통은 TypeScript 타입 정의가 없는 값 아래에 빨간 밑줄이 표시돼야 한다.

## 함수와 컴포넌트를 위한 타입 안전성

애플리케이션은 여전히 실행되겠지만, `src/main.tsx`와 `src/App.tsx` 파일에 타입 정의가 빠져 있다. 먼저 변경이 적은 `main.tsx`부터 시작해보자.

### 실습 5. `main.tsx`에 타입 단언(type assertion) 적용하기

```tsx
// src/main.tsx
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
```

이 수정이 없으면 TypeScript는 다음과 같은 에러를 낸다. `Argument of type 'HTMLElement | null' is not assignable to parameter of type 'Element | DocumentFragment'.` 이 메시지는 "`getElementById()`가 반환하는 HTML 엘리먼트는 해당 엘리먼트가 없으면 `null`일 수 있는데, `createRoot()`는 반드시 `Element`가 전달되기를 기대한다"는 뜻으로 풀이할 수 있다. `index.html` 파일에 해당 식별자를 가진 HTML 엘리먼트가 반드시 있다는 것을 우리가 알고 있으므로, TypeScript에게 "내가 더 잘 안다"고 알려주는 **타입 단언**(여기서는 `as` 키워드에 해당하는 `!` 연산자)을 사용한다.

### 실습 6. `useStorageState` 훅에 타입 지정하기

이제 `src/App.tsx` 파일 전체에 타입 안전성을 추가한다. 프로그래밍 언어 관점에서만 보면, 커스텀 리액트 훅도 그저 하나의 함수일 뿐이다. TypeScript에서는 함수의 입력(그리고 선택적으로 출력)이 타입 안전해야 한다. 먼저 `useStorageState()` 훅에 타입을 지정해서, 두 인자를 문자열 원시값(primitive)으로 기대하도록 만들어보자.

```tsx
// src/App.tsx
const useStorageState = (key: string, initialState: string) => {
  // ...
};
```

또한 이 함수가 배열(`[]`)을 반환하도록 지정할 수 있는데, 첫 번째 값(현재 상태)은 `string` 타입이고, 두 번째 값(상태 갱신 함수)은 새로운 상태값(`string` 타입)을 인자로 받아 아무것도 반환하지 않는(`void`) 함수다.

```tsx
// src/App.tsx
const useStorageState = (
  key: string,
  initialState: string
): [string, (newValue: string) => void] => {
  // ...

  return [value, setValue];
};
```

TypeScript는 리액트의 `useState` 훅으로부터 이 타입을 이미 추론할 수 있으므로, 반환 타입은 다시 제거해도 무방하다. 다만 반환하는 배열을 TypeScript의 `const` 단언으로 선언해야 하는데, 그렇지 않으면 배열 안 항목들의 순서를 애플리케이션의 다른 부분에서 알 수 없기 때문이다.

```tsx
// src/App.tsx
const useStorageState = (key: string, initialState: string) => {
  const [value, setValue] = React.useState(
    localStorage.getItem(key) || initialState
  );

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);

  return [value, setValue] as const;
};
```

앞서 커스텀 훅에 대해 타입 안전성을 개선하긴 했지만, 함수 본문 안의 리액트 내장 훅에는 별도로 타입을 지정할 필요가 없었다는 점에 주목하자. 이는 리액트 훅에 대해 대부분의 경우 타입 추론이 기본적으로 잘 동작하기 때문이다. `useState` 훅의 초기 상태가 자바스크립트 문자열 원시값이라면, 반환되는 현재 상태는 `string`으로 추론되고, 반환되는 상태 갱신 함수도 `string`만 인자로 받도록 추론된다.

```ts
const [value, setValue] = React.useState('React');
// value는 string으로 추론된다
// setValue는 오직 string만 인자로 받는다
```

하지만 초기 상태가 처음에 `null`이라면, TypeScript에게 `useState` 훅이 가질 수 있는 모든 타입을 알려줘야 한다(여기서는 `|`로 두 개 이상의 타입을 합치는 유니온 타입을 사용한다). 이때는 TypeScript **제네릭(generic)**을 사용해서 함수(여기서는 리액트 훅)에 이를 알려준다.

```ts
const [value, setValue] = React.useState<string | null>(null);
// value는 string 아니면 null이어야 한다
// setValue는 string 아니면 null만 인자로 받는다
```

### 실습 7. `Story` 타입 정의하고 `Item` 컴포넌트에 적용하기

리액트 애플리케이션과 그 컴포넌트에 나중에서야 타입 안전성을 추가하는 경우, 여러 접근 방식이 있다. 애플리케이션의 말단(leaf) 컴포넌트의 props와 상태부터 시작해보자. 예를 들어 `Item` 컴포넌트는 스토리(여기서는 `item`)와 콜백 핸들러 함수(여기서는 `onRemoveItem`)를 받는다. 처음에는 이렇게 장황하게 두 함수 인자에 인라인 타입을 지정할 수 있다.

```tsx
// src/App.tsx
const Item = ({
  item,
  onRemoveItem,
}: {
  item: {
    objectID: string;
    url: string;
    title: string;
    author: string;
    num_comments: number;
    points: number;
  };
  onRemoveItem: (item: {
    objectID: string;
    url: string;
    title: string;
    author: string;
    num_comments: number;
    points: number;
  }) => void;
}) => (
  <li>
    {/* ... */}
  </li>
);
```

여기에는 두 가지 문제가 있다. 코드가 장황하고, 중복(`item` 부분)이 있다는 점이다. `src/App.tsx` 파일 최상단, 컴포넌트 바깥에 커스텀 `Story` 타입을 정의해서 두 문제를 모두 해결해보자.

```tsx
// src/App.tsx
type Story = {
  objectID: string;
  url: string;
  title: string;
  author: string;
  num_comments: number;
  points: number;
};

// ...

const Item = ({
  item,
  onRemoveItem,
}: {
  item: Story;
  onRemoveItem: (item: Story) => void;
}) => (
  <li>
    {/* ... */}
  </li>
);
```

`item`은 `Story` 타입이고, `onRemoveItem` 함수는 `Story` 타입의 `item`을 인자로 받아 아무것도 반환하지 않는다. 이어서 `Item` 컴포넌트의 props를 컴포넌트 바깥에 별도 타입으로 정의해서 코드를 좀 더 정리한다.

```tsx
// src/App.tsx
type ItemProps = {
  item: Story;
  onRemoveItem: (item: Story) => void;
};

const Item = ({ item, onRemoveItem }: ItemProps) => (
  <li>
    {/* ... */}
  </li>
);
```

### 실습 8. `List` 컴포넌트와 `App` 컴포넌트에 타입 적용하기

여기서 컴포넌트 트리를 따라 위로 올라가 `List` 컴포넌트에도 같은 방식으로 타입 정의를 적용할 수 있다.

```tsx
// src/App.tsx
type ListProps = {
  list: Story[];
  onRemoveItem: (item: Story) => void;
};

const List = ({ list, onRemoveItem }: ListProps) => (
  <ul>
    {/* ... */}
  </ul>
);
```

`onRemoveItem` 함수는 이제 `ItemProps`와 `ListProps` 양쪽에서 두 번 타입이 지정된 상태다. 좀 더 정확하게 하려면, 이를 독립된 `OnRemoveItem`이라는 TypeScript 타입으로 추출해서 두 `onRemoveItem` prop 타입 정의에서 재사용할 수도 있다. 다만 컴포넌트가 여러 파일로 나뉠수록 이런 개발은 점점 더 까다로워지므로, 여기서는 중복을 그대로 유지한다.

이제 `Story` 타입을 다른 컴포넌트에서도 재사용할 수 있다. 예를 들어 `App` 컴포넌트의 콜백 핸들러에도 `Story` 타입을 추가한다.

```tsx
// src/App.tsx
const App = () => {
  // ...

  const handleRemoveStory = (item: Story) => {
    dispatchStories({
      type: 'REMOVE_STORY',
      payload: item,
    });
  };

  // ...
};
```

### 실습 9. 리듀서에 타입 안전성 적용하기

리듀서 함수 역시 `Story` 타입을 다루지만, 상태와 액션 타입 때문에 실제로 이를 직접 건드리지는 않는다. 애플리케이션을 만드는 우리는 이 리듀서 함수에 전달되는 두 객체의 모양을 알고 있다.

```tsx
// src/App.tsx
type StoriesState = {
  data: Story[];
  isLoading: boolean;
  isError: boolean;
};

type StoriesAction = {
  type: string;
  payload: any;
};

const storiesReducer = (
  state: StoriesState,
  action: StoriesAction
) => {
  // ...
};
```

`string`과 `any`(TypeScript의 와일드카드) 타입으로만 정의된 `Action` 타입은 여전히 너무 광범위해서, 액션들이 서로 구분되지 않으므로 실질적인 타입 안전성을 얻지 못한다. 각 액션을 개별 TypeScript 타입으로 명시하고, 최종적으로 유니온 타입(여기서는 `StoriesAction`)을 사용하면 이를 개선할 수 있다.

```tsx
// src/App.tsx
type StoriesFetchInitAction = {
  type: 'STORIES_FETCH_INIT';
};

type StoriesFetchSuccessAction = {
  type: 'STORIES_FETCH_SUCCESS';
  payload: Story[];
};

type StoriesFetchFailureAction = {
  type: 'STORIES_FETCH_FAILURE';
};

type StoriesRemoveAction = {
  type: 'REMOVE_STORY';
  payload: Story;
};

type StoriesAction =
  | StoriesFetchInitAction
  | StoriesFetchSuccessAction
  | StoriesFetchFailureAction
  | StoriesRemoveAction;

const storiesReducer = (
  state: StoriesState,
  action: StoriesAction
) => {
  // ...
};
```

이제 리듀서의 현재 상태, 액션, 그리고 (추론된) 반환 상태까지 모두 타입 안전해졌다. 예를 들어 정의되지 않은 액션 타입으로 리듀서에 액션을 디스패치하면 TypeScript가 에러를 낸다. 또한 스토리를 제거하는 액션에 스토리가 아닌 다른 것을 전달해도 타입 에러가 발생한다.

### 실습 10. `SearchForm`과 이벤트 콜백에 타입 적용하기

이제 이벤트를 다루는 콜백 핸들러가 있는 `SearchForm` 컴포넌트로 초점을 옮겨보자.

```tsx
// src/App.tsx
type SearchFormProps = {
  searchTerm: string;
  onSearchInput: (event: React.ChangeEvent<HTMLInputElement>) => void;
  searchAction: (formData: FormData) => void;
};

const SearchForm = ({
  searchTerm,
  onSearchInput,
  searchAction,
}: SearchFormProps) => (
  // ...
);
```

대부분의 경우 `React.ChangeEvent`나 `React.FormEvent` 대신 `React.SyntheticEvent`를 사용하는 것으로 충분하다. 하지만 대부분의 애플리케이션에서는 좀 더 구체적인 타입이 필요하다. 다시 `App` 컴포넌트로 올라가서, 해당 콜백 핸들러에도 같은 타입을 적용한다.

```tsx
// src/App.tsx
const App = () => {
  // ...

  const handleSearchInput = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSearchTerm(event.target.value);
  };

  // ...
};
```

### 실습 11. `InputWithLabel`에서 `useRef`와 props 타입 지정하기

이제 `InputWithLabel` 컴포넌트만 남았다. 이 컴포넌트의 props를 다루기 전에, 리액트의 `useRef` 훅이 반환하는 `ref`부터 살펴보자. 아쉽게도 이 반환값은 자동으로 추론되지 않는다.

```tsx
// src/App.tsx
const InputWithLabel = ({ /* ... */ }) => {
  const inputRef = React.useRef<HTMLInputElement>(null);

  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isFocused]);

  // ...
};
```

이렇게 하면 반환되는 `ref`가 타입 안전해지고, `focus` 메서드만(읽기용으로) 실행하므로 읽기 전용(read-only)으로 타입이 지정된다. DOM 엘리먼트를 `current` 프로퍼티에 설정하는 것은 리액트가 대신 처리해준다. 마지막으로 `InputWithLabel` 컴포넌트의 props에도 타입 검사를 적용한다. `children` prop에는 리액트 전용 타입을 사용하고, 선택적(optional) 타입에는 물음표를 붙인다는 점에 주목하자.

```tsx
// src/App.tsx
type InputWithLabelProps = {
  id: string;
  value: string;
  type?: string;
  onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  isFocused?: boolean;
  children: React.ReactNode;
};

const InputWithLabel = ({
  id,
  value,
  type = 'text',
  onInputChange,
  isFocused,
  children,
}: InputWithLabelProps) => {
  // ...
};
```

`type`과 `isFocused` 프로퍼티는 둘 다 선택적이다. TypeScript를 사용하면 이 props들이 컴포넌트에 반드시 전달되지 않아도 된다고 컴파일러에 알려줄 수 있다. `children` prop에는 이 개념에 적용할 수 있는 다양한 TypeScript 타입 정의가 있는데, 그중 가장 범용적인 것이 리액트 라이브러리의 `React.ReactNode`다.

이제 우리 리액트 애플리케이션 전체가 TypeScript로 타입이 지정되어, 컴파일 타임에 타입 오류를 손쉽게 찾아낼 수 있게 됐다. 리액트 애플리케이션에 TypeScript를 추가할 때는, 함수의 인자에 타입 정의를 추가하는 것부터 시작하자. 이 함수들은 순수 자바스크립트 함수일 수도, 커스텀 리액트 훅일 수도, 리액트 함수 컴포넌트일 수도 있다. 리액트를 사용할 때만 폼 엘리먼트, 이벤트, JSX에 대한 특정 타입을 아는 것이 중요해진다.

## 정리

- TypeScript는 런타임이 아닌 컴파일 타임에 타입 오류를 IDE 안에서 바로 보여줘서, 개발자의 피드백 루프를 단축시키고 코드를 더 읽기 쉽게 만들어준다.
- Vite 기반 리액트 프로젝트에 TypeScript를 도입하려면 `typescript`, `@types/react`, `@types/react-dom` 등을 설치하고, `tsconfig.json`/`tsconfig.app.json`/`tsconfig.node.json` 세 설정 파일을 구성한 뒤, `.jsx` 파일을 `.tsx`로 이름을 바꿔야 한다.
- `document.getElementById('root')!`처럼 `!` 연산자로 타입 단언을 사용하면, "이 값은 절대 `null`이 아니다"라고 TypeScript에게 알려줄 수 있다.
- 커스텀 훅(예: `useStorageState`)의 인자와 반환값에 타입을 지정할 수 있으며, 반환하는 배열에는 `as const`를 사용해서 각 항목의 순서를 고정해야 한다.
- 리액트 훅은 대부분 초기값으로부터 타입을 자동으로 추론해주지만, 초기값이 `null`일 수 있는 경우처럼 여러 타입이 가능할 때는 `useState<string | null>`처럼 제네릭과 유니온 타입을 명시해야 한다.
- 컴포넌트 props에 반복적으로 등장하는 데이터 구조는 `Story`처럼 별도의 `type`으로 정의해서 재사용하면, 코드의 장황함과 중복을 줄일 수 있다.
- 리듀서의 액션은 하나의 넓은 타입보다, `StoriesFetchInitAction`처럼 액션별로 구체적인 타입을 정의하고 유니온 타입으로 묶는 편이 실질적인 타입 안전성을 제공한다.
- 이벤트 핸들러에는 `React.ChangeEvent<HTMLInputElement>`처럼 구체적인 리액트 이벤트 타입을, `children` prop에는 `React.ReactNode`를 사용한다.
