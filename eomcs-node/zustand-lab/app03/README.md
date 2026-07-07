# app03. 컴포넌트 간에 상태 공유: Context API

이번 실습에서는 Context API를 사용하여 상태를 공유하는 방법을 배운다.

## 실습: Context API

Props drilling을 줄이기 위해 리액트가 기본 제공하는 Context API를 쓸 수도 있다.

```tsx
// src/BearContext.tsx
import { createContext, useContext, useState, type ReactNode } from "react";

type BearContextValue = {
  bears: number;
  increasePopulation: () => void;
};

const BearContext = createContext<BearContextValue | null>(null);

export function BearProvider({ children }: { children: ReactNode }) {
  const [bears, setBears] = useState(0);
  const increasePopulation = () => setBears((prev) => prev + 1);

  return (
    <BearContext.Provider value={{ bears, increasePopulation }}>
      {children}
    </BearContext.Provider>
  );
}

export function useBearContext() {
  const ctx = useContext(BearContext);
  if (!ctx) throw new Error("BearProvider 안에서만 사용할 수 있다.");
  return ctx;
}
```

```tsx
// src/BearCounter.tsx
import { useBearContext } from "./BearContext";

function BearCounter() {
  const { bears } = useBearContext();
  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
```

```tsx
// src/Controls.tsx
import { useBearContext } from "./BearContext";

function Controls() {
  const { increasePopulation } = useBearContext();
  return <button className="counter" onClick={increasePopulation}>one up</button>;
}

export default Controls;
```

```tsx
// src/Page.tsx
import Controls from "./Controls";
import Sidebar from "./Sidebar";

function Page() {
  return (
    <section className="page">
      <Sidebar />
      <Controls />
    </section>
  );
}

export default Page;
```

```tsx
// src/App.tsx
import { BearProvider } from "./BearContext";
import BearCounter from "./BearCounter";
import Page from "./Page";
import "./App.css";

function App() {
  return (
    <div className="bear-app">
      <BearProvider>
        <BearCounter />
        <Page />
      </BearProvider>
    </div>
  );
}

export default App;
```

이제 트리 깊이와 상관없이 `BearCounter`와 `Controls`가 어디에 있든 `useBearContext`로 상태에 접근할 수 있어 props drilling은 사라진다. 하지만 대가가 있다.

- 상태 하나를 공유하는 데도 Context 정의, Provider, 커스텀 훅까지 보일러플레이트 코드가 필요하다.
- 상태(도메인)가 여러 개로 늘어나면 Provider를 그만큼 겹겹이 쌓거나(Provider Hell), Context를 계속 새로 만들어야 한다.
- `Provider`의 `value`가 바뀌면 그 값을 구독하는 모든 하위 컴포넌트가 리렌더링된다. `bears`만 바뀌어도 `increasePopulation`만 쓰는 컴포넌트까지 함께 리렌더링될 수 있어, 세밀한 최적화가 어렵다.

## 정리

Zustand 없이 상태를 공유하려면 상태 끌어올리기(props drilling을 감수) 또는 Context API(보일러플레이트와 불필요한 리렌더링을 감수) 중 하나를 선택해야 한다. 두 방법 모두 상태나 컴포넌트가 늘어날수록 점점 다루기 번거로워진다.

Zustand는 `create`로 만든 스토어 훅 하나로 상태와 액션을 정의하고, `Provider` 없이 어떤 컴포넌트에서든 필요한 값만 선택(select)해서 구독할 수 있게 해준다. 다음 실습에서는 이 예제를 Zustand로 다시 구현하며 그 차이를 비교해본다.
