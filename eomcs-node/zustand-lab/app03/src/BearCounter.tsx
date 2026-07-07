import { useBearContext } from "./BearContext";

function BearCounter() {
  const { bears } = useBearContext();
  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
