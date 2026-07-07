# app10. 기본 사용법: combine 미들웨어

이번 실습에서는 `combine` 미들웨어로 초기 상태(state)와 액션(actions)을 분리해서 스토어를 만드는 방법을 배운다.

## 실습: combine 미들웨어

`combine` 미들웨어는 초기 상태와 액션을 분리해서 코드를 더 깔끔하게 만들어준다. TypeScript는 상태와 액션으로부터 타입을 자동으로 추론하므로, 별도의 `interface`가 필요 없다. 타입 안전성이 보장되지 않는 JavaScript와는 다른 점이다. TypeScript 프로젝트에서 매우 널리 쓰이는 스타일이다. 자세한 내용은 API 문서를 참고하라.

```tsx
// src/bearStore.ts
import { create } from "zustand";
import { combine } from "zustand/middleware";

interface BearState {
  bears: number;
  increase: () => void;
}

// 상태와 액션을 분리한다.
export const useBearStore = create<BearState>()(
  combine({ 
      bears: 0 
    }, (set) => ({
      increase: () => set((s) => ({ bears: s.bears + 1 })),
  })),
);
```

`combine`의 첫 번째 인자 `{ bears: 0 }`는 초기 상태이고, 두 번째 인자 `(set) => ({ increase: ... })`는 액션을 정의하는 함수다. 이렇게 상태와 액션을 나눠서 넘기면, 상태와 액션이 한 객체 리터럴에 뒤섞여 있던 기존 방식보다 스토어의 구조가 더 명확하게 드러난다.
