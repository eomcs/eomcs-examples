import { useBearStore } from "./bearStore";
import { logBearState } from "./util";

function Controls() {
  const bears = useBearStore((s) => s.bears);
  const food = useBearStore((s) => s.food);
  const increase = useBearStore((s) => s.increase);
  const state = { bears, food, increase };

  // 상태의 타입을 명확하게 검사한다.
  logBearState(state);

  return (
    <div className="controls">
      <button className="counter" onClick={() => increase(1)}>
        Increase by 1
      </button>
    </div>
  );
}

export default Controls;
