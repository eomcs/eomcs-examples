# app11. 기본 사용법: persist 미들웨어

이번 실습에서는 `persist` 미들웨어로 스토어 상태를 localStorage에 저장하는 방법을 배운다.

## 실습: persist 미들웨어

`persist` 미들웨어는 스토어를 localStorage(또는 다른 저장소)에 보관해준다. 덕분에 페이지를 새로고침해도 곰의 수(`bears`)가 그대로 유지된다. 상태 지속(persistence)이 중요한 앱에 유용하다. TypeScript에서는 상태 타입이 그대로 유지되므로 런타임에서 예상치 못한 문제가 생기지 않는다. 자세한 내용은 API 문서를 참고하라.

```tsx
// src/bearStore.ts
import { create } from "zustand";
import { persist } from "zustand/middleware";

interface BearState {
  bears: number;
  increase: () => void;
}

export const useBearStore = create<BearState>()(
  persist(
    (set) => ({
      bears: 0,
      increase: () => set((s) => ({ bears: s.bears + 1 })),
    }),
    { name: "bear-storage" }, // localStorage 키
  ),
);
```

`persist`의 첫 번째 인자는 지금까지와 같은 상태·액션을 만드는 함수이고, 두 번째 인자 `{ name: "bear-storage" }`는 옵션 객체로 localStorage에 저장할 때 사용할 키 이름을 지정한다. 이 키로 저장된 값은 브라우저를 새로고침하거나 다시 열어도 그대로 남아 있어서, `useBearStore`가 처음 만들어질 때 localStorage에 저장된 값으로 상태를 복원한다.
