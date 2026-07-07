import { useBearStore } from "./bearStore";
import { useFishStore } from "./fishStore";

function BearCounter() {
  const bears = useBearStore((s) => s.bears);
  const fish = useFishStore((s) => s.fish);

  return <h1>{bears} bears and {fish} fish</h1>;
}

export default BearCounter;
