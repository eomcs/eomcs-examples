import { useBearStore } from "./bearStore";

function Controls() {
  const food = useBearStore((s) => s.food);
  const feed = useBearStore((s) => s.feed);

  return (
    <div className="controls">
      <p>Current food: {food}</p>
      <button className="counter" onClick={() => feed("honey")}>
        honey
      </button>
      <button className="counter" onClick={() => feed("berries")}>
        berries
      </button>
    </div>
  );
}

export default Controls;
