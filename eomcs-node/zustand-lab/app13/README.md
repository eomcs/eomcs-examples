# app13. 기본 사용법: createWithEqualityFn

이번 실습에서는 셀렉터마다 원하는 동등성 비교(equality check) 함수를 직접 지정할 수 있는 `createWithEqualityFn`을 배운다.

## 실습: createWithEqualityFn

`createWithEqualityFn`은 동등성 비교 기능이 내장된 `create`의 변형이다. 항상 커스텀 동등성 비교를 쓰고 싶을 때 유용하다. 자주 쓰이는 방식은 아니지만, Zustand가 얼마나 유연한지 보여준다. TypeScript는 이 경우에도 타입 추론을 온전히 유지한다. 자세한 내용은 API 문서를 참고하라.

`zustand/traditional` 패키지를 사용하려면 `use-sync-external-store` 패키지를 설치해야 한다.

```bash
npm install use-sync-external-store
```

```tsx
// src/bearStore.ts
import { createWithEqualityFn } from "zustand/traditional";
import { shallow } from "zustand/shallow";

export const useBearStore = createWithEqualityFn(() => ({
  bears: 0,
}));

const bears = useBearStore((s) => s.bears, Object.is);
// 또는
const bears2 = useBearStore((s) => ({ bears: s.bears }), shallow);
```

`useBearStore`를 호출할 때 셀렉터 뒤에 두 번째 인자로 동등성 비교 함수를 넘길 수 있다. `(s) => s.bears`처럼 값 하나를 그대로 선택하면 `Object.is`로 비교해도 충분하지만, `(s) => ({ bears: s.bears })`처럼 매번 새 객체를 반환하는 셀렉터라면 `zustand/shallow`의 `shallow`를 넘겨서 얕은 비교를 하도록 지정할 수 있다. 이렇게 셀렉터별로 원하는 비교 방식을 직접 고를 수 있다는 점이 `create`와 다른 `createWithEqualityFn`만의 특징이다.
