import { useBearStore } from "./bearStore";

function Controls() {
  const increase = useBearStore((s) => s.increase);

  return (
    <div className="controls">
      <button className="counter" onClick={() => increase(1)}>
        Increase by 1
      </button>
    </div>
  );
}

export default Controls;
