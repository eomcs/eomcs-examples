import { useBearStore } from "./bearStore";

function BearCounter() {
  // 'bears'만 선택해서 불필요한 리렌더링을 피한다.
  const bears = useBearStore((s) => s.bears);
  return <h1>{bears} bears around</h1>;
}

export default BearCounter;
