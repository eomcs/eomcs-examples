import { useBearStore } from "./bearStore";

function Controls() {
  const increase = useBearStore((s) => s.increase);

  return (
    <div className="controls">
      <button className="counter" onClick={() => increase(1)}>
        곰 1마리 추가
      </button>
    </div>
  );
}

export default Controls;
