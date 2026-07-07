# app07. 기본 사용법: 타입 추출

이번 실습에서는 Zustand가 제공하는 `ExtractState`로 스토어의 상태·액션 타입을 추출하는 방법을 배운다.

## 실습: 스토어 타입 추출하기

Zustand는 `ExtractState`라는 내장 헬퍼를 제공한다. 상태와 액션의 타입을 수동으로 다시 정의하지 않고도 스토어 전체의 타입을 그대로 뽑아 쓸 수 있어서, 테스트나 유틸리티 함수, 컴포넌트 props의 타입을 정의할 때 유용하다.

**스토어 타입 추출하기:**

```tsx
// src/bearStore.ts
import { create, type ExtractState } from "zustand";

export const useBearStore = create((set) => ({
  bears: 3,
  food: "honey",
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));

// 스토어 전체 상태의 타입을 추출한다.
export type BearState = ExtractState<typeof useBearStore>;
```

**테스트에서 추출한 타입 사용하기:**

```tsx
// test.cy.ts
import { BearState } from "./bearStore";

test("should reset store", () => {
  const snapshot: BearState = useBearStore.getState();
  expect(snapshot.bears).toBeGreaterThanOrEqual(0);
});
```

**유틸리티 함수에서 사용하기:**

```tsx
// util.ts
import { BearState } from "./bearStore";

function logBearState(state: BearState) {
  console.log(`We have ${state.bears} bears eating ${state.food}`);
}

logBearState(useBearStore.getState());
```

`ExtractState<typeof useBearStore>`는 `useBearStore`를 만들 때 넘긴 상태와 액션을 그대로 읽어와 타입으로 만들어준다. 그래서 `bears`, `food`, `increase`의 타입을 `BearState`에 다시 옮겨 적을 필요가 없고, 스토어의 필드가 바뀌면 `BearState`도 자동으로 따라서 바뀐다. `BearState`처럼 추출한 타입은 테스트 코드에서 `useBearStore.getState()`의 반환값을 검증하거나, 유틸리티 함수의 매개변수 타입을 선언하는 데 그대로 재사용할 수 있다.
