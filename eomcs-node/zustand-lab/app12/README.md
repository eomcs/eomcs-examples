# app12. 기본 사용법: 비동기 액션

이번 실습에서는 비동기 액션(async action)을 스토어에 정의하는 방법을 배운다.

## 실습: 비동기 액션

액션은 데이터를 가져오기 위해 비동기(async)일 수도 있다. 여기서는 로컬에서 테스트할 수 있도록 서버 API 대신 `Promise`와 `setTimeout`으로 가짜 API를 만들고, 곰의 수를 가져와서 상태를 갱신한다. TypeScript는 API 응답 타입(`BearData`)이 올바른지 강제한다. JavaScript에서는 `count`를 잘못 써도 알아채기 어렵지만, TypeScript는 이런 실수를 막아준다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

interface BearData {
  count: number;
}

function getBearData(): Promise<BearData> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({ count: 7 });
    }, 500);
  });
}

interface BearState {
  bears: number;
  fetchBears: () => Promise<void>;
}

export const useBearStore = create<BearState>()((set) => ({
  bears: 0,
  fetchBears: async () => {
    const data = await getBearData();
    set({ bears: data.count });
  },
}));
```

`getBearData`는 서버 없이 비동기 동작을 확인하기 위한 로컬 mock API다. `setTimeout`으로 2초 뒤 `{ count: 7 }`을 반환하므로, 네트워크나 백엔드 서버 없이도 `fetchBears`가 비동기로 실행되는 흐름을 테스트할 수 있다.

`fetchBears`는 `Promise<void>`를 반환하는 비동기 함수다. `getBearData()`로 데이터를 가져온 뒤 `data.count`를 `set({ bears: data.count })`로 상태에 반영한다. `BearData`에 `count` 대신 다른 이름을 쓰거나 타입을 잘못 지정하면 TypeScript가 컴파일 시점에 바로 알려주므로, API 응답 구조가 바뀌었을 때도 안전하게 대응할 수 있다.

```tsx
// src/Controls.tsx
import { useBearStore } from "./bearStore";

function Controls() {
  const fetchBears = useBearStore((s) => s.fetchBears);

  return (
    <div className="controls">
      <button className="counter" onClick={() => fetchBears()}>
        데이터 가져오기
      </button>
    </div>
  );
}

export default Controls;
```