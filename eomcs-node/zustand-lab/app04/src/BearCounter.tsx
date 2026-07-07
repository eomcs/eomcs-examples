import { useBearStore } from "./bearStore";

function BearCounter() {
  const bears = useBearStore((state) => state.bears);

  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
