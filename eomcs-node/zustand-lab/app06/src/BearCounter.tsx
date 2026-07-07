import { useBearStore } from "./bearStore";

function BearCounter() {
  const bears = useBearStore((s) => s.bears);

  return <h1>{bears} bears around</h1>;
}

export default BearCounter;
