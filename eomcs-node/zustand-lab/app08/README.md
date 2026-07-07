# app08. 기본 사용법: 멀티 셀렉터

이번 실습에서는 스토어에서 여러 값을 한 번에 선택하는 방법과, 그때 발생할 수 있는 불필요한 리렌더링을 `useShallow`로 막는 방법을 배운다.

## 실습: 여러 값을 선택하는 셀렉터

컴포넌트가 여러 값을 필요로 할 때는 셀렉터에서 객체를 반환하여 여러 필드를 한 번에 선택할 수 있다. 하지만 셀렉터는 호출될 때마다 새로운 객체를 생성하므로, 비록 `bears`와 `food`의 값이 이전과 같더라도 객체의 참조(reference)는 항상 달라진다. **Zustand는 기본적으로 반환값의 참조를 비교하므로 이를 값의 변경으로 판단하여 불필요한 리렌더링이 발생할 수 있다.** `useShallow`는 객체의 참조 대신 최상위 프로퍼티들을 얕은 비교(shallow comparison) 하여 실제 값이 변하지 않았다면 이전 객체를 재사용하고 리렌더링을 건너뛴다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

interface BearStore {
  bears: number;
  food: string;
  increase: (by: number) => void;
}

export const useBearStore = create<BearStore>((set) => ({
  bears: 3,
  food: "honey",
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));
```

```tsx
// src/MultipleSelectors.tsx
import { useShallow } from "zustand/react/shallow";
import { useBearStore } from "./bearStore";

function MultipleSelectors() {
  const { bears, food } = useBearStore(
   useShallow((state) => ({ bears: state.bears, food: state.food })),
  );

  console.log("MultipleSelectors render");

  return (
    <div>
      We have {food} units of food for {bears} bears
    </div>
  );
}

export default MultipleSelectors;
```

`useBearStore(useShallow((state) => ({ bears: state.bears, food: state.food })))`처럼 셀렉터가 반환하는 객체를 `useShallow`로 감싸면, `bears`와 `food` 값이 실제로 바뀌었을 때만 컴포넌트가 리렌더링된다. `useShallow` 없이 `(state) => ({ bears: state.bears, food: state.food })`만 쓰면, 스토어의 다른 값이 바뀌어 셀렉터가 다시 호출될 때마다 매번 새로운 객체가 생성되어 참조가 달라지므로, 실제 값이 그대로여도 리렌더링이 일어난다.

```tsx
// src/App.tsx
import MultipleSelectors from "./MultipleSelectors";
import Page from "./Page";
import "./App.css";

function App() {
  return (
    <div className="bear-app">
      <MultipleSelectors />
      <Page />
    </div>
  );
}

export default App;
```
