# app01. 컴포넌트 간에 상태 공유: Lifting State Up

이번 실습에서는 "상태 끌어올리기(Lifting State Up)"를 통해 컴포넌트 간의 상태를 공유하는 방법을 배운다.

## 프로젝트 구성

리액트 프로젝트는 Vite + React + TypeScript + React Router 기반으로 구성되어 있다.

## 실습: 상태 끌어올리기 (Lifting State Up)

리액트에서 형제 컴포넌트끼리 상태를 공유하려면, 그 상태를 두 컴포넌트의 가장 가까운 공통 부모로 끌어올리고 `props`로 내려주어야 한다.

```tsx
// src/BearCounter.tsx
type BearCounterProps = {
  bears: number;
};

function BearCounter({ bears }: BearCounterProps) {
  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
```

```tsx
// src/Controls.tsx
type ControlsProps = {
  increasePopulation: () => void;
};

function Controls({ increasePopulation }: ControlsProps) {
  return <button onClick={increasePopulation}>one up</button>;
}

export default Controls;
```

```tsx
// src/App.tsx
import { useState } from "react";
import BearCounter from "./BearCounter";
import Controls from "./Controls";

function App() {
  const [bears, setBears] = useState(0);

  const increasePopulation = () => setBears((prev) => prev + 1);

  return (
    <div>
      <BearCounter bears={bears} />
      <Controls increasePopulation={increasePopulation} />
    </div>
  );
}

export default App;
```

`bears` 상태와 `increasePopulation` 함수를 `App`이 소유하고, 자식 컴포넌트에는 `props`로 전달만 한다. 두 컴포넌트가 같은 부모 밑에 나란히 있는 지금 구조에서는 이 방식으로 충분하다.
