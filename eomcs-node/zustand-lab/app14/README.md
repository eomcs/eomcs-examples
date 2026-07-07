# app14. 기본 사용법: 다중 스토어

이번 실습에서는 도메인별로 스토어를 여러 개 나누어 만드는 방법을 배운다.

## 실습: 여러 개의 스토어 만들기

하나의 앱에서 서로 다른 도메인을 위해 스토어를 여러 개 만들 수 있다. 예를 들어 `BearStore`는 곰(bears)을, `FishStore`는 물고기(fish)를 관리한다. 이렇게 나누면 상태가 서로 분리되어 앱 규모가 커져도 유지보수하기 쉽다. TypeScript를 쓰면 각 스토어가 고유한 타입을 가지므로, `bears`와 `fish`를 실수로 섞어 쓰는 일을 막을 수 있다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

// 명시적인 타입을 가진 bear store
interface BearState {
  bears: number;
  addBear: () => void;
}

export const useBearStore = create<BearState>()((set) => ({
  bears: 2,
  addBear: () => set((s) => ({ bears: s.bears + 1 })),
}));
```

```tsx
// src/fishStore.ts
import { create } from "zustand";

// 명시적인 타입을 가진 fish store
interface FishState {
  fish: number;
  addFish: () => void;
}

export const useFishStore = create<FishState>()((set) => ({
  fish: 5,
  addFish: () => set((s) => ({ fish: s.fish + 1 })),
}));
```

```tsx
// src/BearCounter.tsx
import { useBearStore } from "./bearStore";
import { useFishStore } from "./fishStore";

function BearCounter() {
  const bears = useBearStore((s) => s.bears);
  const fish = useFishStore((s) => s.fish);

  return <h1>{bears} bears and {fish} fish</h1>;
}

export default BearCounter;
```

````tsx
// src/Controls.tsx
import { useBearStore } from "./bearStore";
import { useFishStore } from "./fishStore";

function Controls() {
  const addBear = useBearStore((s) => s.addBear);
  const addFish = useFishStore((s) => s.addFish);

  return (
    <div className="controls">
      <button className="counter" onClick={() => addBear()}>
        곰 1마리 추가
      </button>
      <button className="counter" onClick={() => addFish()}>
        물고기 1마리 추가
      </button>
    </div>
  );
}

export default Controls;
````

`Zoo` 컴포넌트는 `useBearStore`와 `useFishStore`를 각각 호출해서 두 스토어의 상태와 액션을 동시에 사용한다. 두 훅은 서로 완전히 독립된 스토어이므로, 한쪽 상태가 바뀐다고 해서 다른 쪽 스토어까지 영향을 받지 않는다. 스토어를 도메인별로 나누면 각 스토어의 책임이 명확해지고, 큰 스토어 하나에 모든 상태를 몰아넣는 것보다 관리하기 쉬워진다.
