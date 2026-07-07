# app05. 기본 사용법: 스토어 생성과 사용

이번 실습에서는 TypeScript을 사용하여 Zustand 스토어의 상태·액션을 타입 안전하게 정의하고 컴포넌트에서 사용하는 방법을 배운다.

## 실습: 상태와 액션을 가진 스토어 만들기

`interface`로 상태(state)와 액션(actions)의 타입을 정의한다. `<BearState>` 제네릭은 스토어가 반드시 이 모양을 따르도록 강제한다. 필드를 빠뜨리거나 타입을 잘못 쓰면 TypeScript가 바로 알려준다. 일반 JavaScript와 달리 타입 안전한 상태 관리가 보장되는 것이다.

```tsx
// src/bearStore.ts
import { create } from "zustand";

// 상태와 액션의 타입을 정의한다.
interface BearState {
  bears: number;
  food: string;
  feed: (food: string) => void;
}

// create의 curried form을 사용해 스토어를 생성한다.
export const useBearStore = create<BearState>()((set) => ({
  bears: 2,
  food: "honey",
  feed: (food) => set(() => ({ food })),
}));
```

`create<BearState>((set) => ...)`처럼 한 번만 호출하는 대신, `create<BearState>()((set) => ...)`처럼 두 번 호출하는 curried form을 사용한다. 이 형태로 만들어진 `useBearStore`는 `UseBoundStore<StoreApi<BearState>>` 타입을 갖는다.

### curried form을 쓰는 이유

`create<BearState>(...)`처럼 제네릭과 실제 인자를 한 번에 넘기면 될 것 같지만, TypeScript는 상태 타입 `T`를 제대로 추론하지 못한다. `create`에 넘기는 함수는 `set`, `get`을 통해 `T`를 받기도 하고(`get: () => T`), 스토어 초기값으로 `T`를 반환하기도 한다. 즉 `T`가 함수의 입력과 출력 모두에 쓰이는 불변(invariant) 위치에 있는데, TypeScript는 이런 경우 `T`를 스스로 추론하지 못하고 `unknown`으로 처리해버린다.

그래서 Zustand는 아무 일도 하지 않는 빈 함수 호출 `()`을 하나 끼워 넣는 curried 형태를 택했다. `create<BearState>()`를 호출해서 `BearState`라는 타입만 먼저 명시적으로 지정하고, 그 다음 `(set) => ({...})` 함수는 별도로 호출해서 TypeScript가 `set`, `get` 같은 나머지 부분을 정상적으로 추론하게 한다. 즉 curried form은 런타임에서는 아무 의미가 없고, TypeScript에게 "상태 타입은 내가 알려줄 테니, 나머지는 네가 추론해라"라고 알려주기 위한 타입 레벨의 장치다.

반대로 `combine`, `redux`처럼 상태 자체를 만들어주는 미들웨어를 쓸 때는 상태 타입을 이미 추론할 수 있으므로 curried form이 필요 없다.

## 실습: 컴포넌트에서 스토어 사용하기

컴포넌트 안에서 상태를 읽고 액션을 호출할 수 있다. `(s) => s.bears`와 같은 셀렉터는 필요한 값만 구독하게 해준다. 이렇게 하면 불필요한 리렌더링을 줄이고 성능이 좋아진다. JavaScript로도 이렇게 할 수 있지만, TypeScript를 쓰면 IDE가 상태 필드를 자동완성해준다.

```tsx
// src/BearCounter.tsx
import { useBearStore } from "./bearStore";

function BearCounter() {
  // 'bears'만 선택해서 불필요한 리렌더링을 피한다.
  const bears = useBearStore((s) => s.bears);
  return <h1>{bears} bears around</h1>;
}

export default BearCounter;
```

## 실습: 액션 호출하기

```tsx
// src/Controls.tsx
import { useBearStore } from "./bearStore";

function Controls() {
  const food = useBearStore((s) => s.food);
  const feed = useBearStore((s) => s.feed);

  return (
    <div className="controls">
      <p>Current food: {food}</p>
      <button className="counter" onClick={() => feed("honey")}>
        honey
      </button>
      <button className="counter" onClick={() => feed("berries")}>
        berries
      </button>
    </div>
  );
}

export default Controls;
```