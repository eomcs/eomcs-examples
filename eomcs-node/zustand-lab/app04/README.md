# app04. 컴포넌트 간에 상태 공유: Zustand

이번 실습에서는 `Zustand`를 사용하여 상태를 공유하는 방법을 배운다.

## 실습: Zustand

**Zustand 설치:**

```bash
npm install zustand
```

`zustand`는 컴포넌트 바깥에 독립적인 store를 만들고, 필요한 컴포넌트가 그 store에서 원하는 값만 선택해서 사용할 수 있게 해준다. 그래서 `props drilling`이나 `Context Provider` 없이도 여러 컴포넌트가 같은 상태를 공유할 수 있다.

**스토어 (store) 생성:**

```tsx
// src/bearStore.ts
import { create } from "zustand";

// 상태와 액션의 타입을 정의한다.
export type BearState = {
  bears: number;
  increasePopulation: () => void;
};

// zustand를 사용하여 스토어를 생성한다.
export const useBearStore = create<BearState>((set) => ({
  bears: 0,
  increasePopulation: () => set((state) => ({ bears: state.bears + 1 })),
}));
```

`BearState`는 store가 가지고 있는 상태와 액션의 타입이다. `bears`는 현재 곰의 수이고, `increasePopulation`은 곰의 수를 1 증가시키는 함수다.

`create<BearState>()`처럼 제네릭 타입을 넘기면 Zustand store 안에서 `state.bears`와 액션 타입을 TypeScript가 추론할 수 있다. `set` 함수에는 변경할 상태 일부를 반환하면 되고, 이전 상태가 필요할 때는 `set((state) => ...)` 형태를 사용한다.

`useBearStore`는 React hook처럼 사용할 수 있는 store 접근 함수다. 컴포넌트는 이 함수를 통해 필요한 상태나 액션만 골라서 구독한다.

**컴포넌트에 적용:**

```tsx
// src/BearCounter.tsx
import { useBearStore } from "./bearStore";

function BearCounter() {
  const bears = useBearStore((state) => state.bears);

  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
```

`BearCounter`는 `useBearStore((state) => state.bears)`로 store에서 `bears` 값만 선택한다. 이렇게 selector를 사용하면 컴포넌트가 필요한 데이터가 무엇인지 코드에 명확히 드러난다.

```tsx
// src/Controls.tsx
import { useBearStore } from "./bearStore";

function Controls() {
  const increasePopulation = useBearStore((state) => state.increasePopulation);

  return <button className="counter" onClick={increasePopulation}>one up</button>;
}

export default Controls;
```

`Controls`는 `bears` 값을 직접 읽지 않고 `increasePopulation` 액션만 꺼내서 버튼의 `onClick`에 연결한다. 상태를 표시하는 컴포넌트와 상태를 변경하는 컴포넌트가 서로 부모-자식 관계가 아니어도 같은 store를 사용할 수 있다.

App 컴포넌트는 더 이상 `BearProvider`로 감싸지 않아도 된다.

```tsx
// src/App.tsx
import BearCounter from "./BearCounter";
import Page from "./Page";
import "./App.css";

function App() {
  return (
    <div className="bear-app">
      <BearCounter />
      <Page />
    </div>
  );
}

export default App;
```

`App`은 더 이상 `bears` 상태를 직접 만들거나 `increasePopulation` 함수를 props로 내려보내지 않는다. 상태 관리는 `bearStore.ts`로 이동했고, `BearCounter`와 `Controls`가 각각 store에서 필요한 값과 액션을 직접 가져간다.

이 구조에서는 `Page`, `Sidebar` 같은 중간 컴포넌트가 상태 전달을 위해 불필요한 props를 받을 필요가 없다. 이것이 Zustand를 사용했을 때 props drilling을 줄일 수 있는 핵심 장점이다.
