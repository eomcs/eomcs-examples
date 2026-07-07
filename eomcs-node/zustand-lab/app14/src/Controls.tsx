import { useBearStore } from "./bearStore";
import { useFishStore } from "./fishStore";

function Controls() {
  const addBear = useBearStore((s) => s.addBear);
  const addFish = useFishStore((s) => s.addFish);

  return (
    <div className="controls">
      <button className="counter" onClick={() => addBear()}>
        곰 1마리 추가
      </button>
      <button className="counter" onClick={() => addFish()}>
        물고기 1마리 추가
      </button>
    </div>
  );
}

export default Controls;
