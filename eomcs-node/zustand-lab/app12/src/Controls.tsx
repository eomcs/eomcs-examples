import { useBearStore } from "./bearStore";

function Controls() {
  const fetchBears = useBearStore((s) => s.fetchBears);

  return (
    <div className="controls">
      <button className="counter" onClick={() => fetchBears()}>
        데이터 가져오기
      </button>
    </div>
  );
}

export default Controls;
