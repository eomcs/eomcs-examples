# app02. 컴포넌트 간에 상태 공유: Props Drilling

이번 실습에서는 "상태 끌어올리기(Lifting State Up)"의 한계를 이해한다.

## 실습: '상태 끌어올리기'의 한계 - Props Drilling

'상태 끌어올리기' 방식의 문제는 컴포넌트 계층 구조가 깊어지면 코드가 복잡해지고 유지보수가 어려워진다는 점이다.

```tsx
// Controls를 사용하지 않는 Header, Sidebar 같은 컴포넌트도
// Controls까지 props를 전달하기 위해 어쩔 수 없이 끼어들어야 한다.
function App() {
  const [bears, setBears] = useState(0);
  const increasePopulation = () => setBears((prev) => prev + 1);

  return (
    <div>
      <BearCounter bears={bears} />
      <Page increasePopulation={increasePopulation} />
    </div>
  );
}

function Page({ increasePopulation }: { increasePopulation: () => void }) {
  return (
    <section>
      <Sidebar />
      <Controls increasePopulation={increasePopulation} />
    </section>
  );
}
```

`Page` 컴포넌트는 `increasePopulation`을 직접 사용하지 않는데도, 오직 `Controls`에 전달하기 위해 props를 받아서 다시 내려보내야 한다. 이런 현상을 **props drilling**이라고 부른다. 컴포넌트 트리가 깊어지고 공유해야 할 상태와 액션이 늘어날수록:

- 중간 컴포넌트들이 자신과 상관없는 props를 계속 떠안게 되고,
- `App`은 앱 전체의 상태와 로직을 떠맡아 점점 비대해지며,
- 상태를 사용하는 컴포넌트의 위치가 바뀔 때마다 props 전달 경로 전체를 수정해야 한다.