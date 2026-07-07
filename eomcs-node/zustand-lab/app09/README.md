# app09. 기본 사용법: 셀렉터로 유도된 상태

이번 실습에서는 스토어에 값을 직접 저장하지 않고, 셀렉터로 기존 상태로부터 값을 계산해서 사용하는 방법을 배운다.

## 실습: 셀렉터로 유도된 상태(Derived State) 만들기

모든 값을 스토어에 직접 저장할 필요는 없다. 이미 있는 상태로부터 계산할 수 있는 값이라면 셀렉터에서 유도(derive)하면 된다. 이렇게 하면 상태 중복을 피하고 스토어를 최소한으로 유지할 수 있다. TypeScript는 `bears`가 숫자라는 것을 보장하므로, 이런 계산도 안전하다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

interface BearStore {
  bears: number;
  foodPerBear: number;
  increase: (by: number) => void;
}

export const useBearStore = create<BearStore>((set) => ({
  bears: 3,
  foodPerBear: 2,
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));
```

```tsx
// src/TotalFood.tsx
import { useBearStore } from "./bearStore";

function TotalFood() {
  // 유도된 값: 모든 곰에게 필요한 먹이의 양
  const totalFood = useBearStore((s) => s.bears * s.foodPerBear);

  // 스토어에 `{ totalFood: 6 }`라는 별도 프로퍼티를 둘 필요가 없다.
  return <div>We need ${totalFood} jars of honey</div>;
}

export default TotalFood;
```

`totalFood`는 스토어에 저장된 값이 아니라 `bears * foodPerBear`로 매번 계산되는 값이다. 셀렉터 `(s) => s.bears * s.foodPerBear`가 `useBearStore`를 구독하는 시점에 계산을 수행하므로, `bears`나 `foodPerBear` 중 하나라도 바뀌면 `totalFood`도 자동으로 최신 값을 반영한다. 이렇게 계산 가능한 값은 스토어에 별도 필드로 두지 않는 편이, 상태와 파생 값이 서로 어긋날 걱정 없이 스토어를 단순하게 유지하는 방법이다.

```tsx
// src/App.tsx
import TotalFood from "./TotalFood";
import Page from "./Page";
import "./App.css";

function App() {
  return (
    <div className="bear-app">
      <TotalFood />
      <Page />
    </div>
  );
}

export default App;
```