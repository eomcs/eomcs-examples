# app06. 기본 사용법: 스토어 리셋

이번 실습에서는 Zustand 스토어를 초기 상태로 리셋하는 방법을 배운다.

## 실습: 스토어 리셋하기

로그아웃하거나 세션을 초기화할 때처럼, 스토어의 상태를 처음 값으로 되돌려야 할 때가 있다. `initialState`를 별도 객체로 두고 `typeof initialState`로 타입을 재사용하면, 상태 필드의 타입을 두 번 반복해서 적지 않아도 된다. `initialState`가 바뀌면 타입도 자동으로 따라서 갱신되므로, 타입을 일일이 맞춰 써야 하는 JavaScript보다 더 안전하고 간결하다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

const initialState = { bears: 0, food: "honey" };

// initialState의 타입을 재사용한다.
// - `initialState`가 바뀌면 타입도 자동으로 따라서 갱신되는 이점이 있다.
type BearState = typeof initialState & {
  increase: (by: number) => void;
  reset: () => void;
};

export const useBearStore = create<BearState>()((set) => ({
  ...initialState,
  increase: (by) => set((s) => ({ bears: s.bears + by })),
  reset: () => set(initialState),
}));
```

```tsx
// src/ResetZoo.tsx
import { useBearStore } from "./bearStore";

function ResetZoo() {
  const { increase, reset } = useBearStore();

  return (
    <div>
      <button onClick={() => increase(5)}>Increase by 5</button>
      <button onClick={reset}>Reset</button>
    </div>
  );
}

export default ResetZoo;
```

`reset` 액션은 `set(initialState)`를 호출해서 상태를 다시 `initialState`로 덮어쓴다. 상태 타입과 초기값을 `initialState` 하나로 관리하기 때문에, `bears`나 `food`의 초기값이 바뀌어도 `reset`이 되돌리는 값과 타입이 항상 일치한다.
