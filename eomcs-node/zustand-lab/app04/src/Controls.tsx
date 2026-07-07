import { useBearStore } from "./bearStore";

function Controls() {
  const increasePopulation = useBearStore((state) => state.increasePopulation);

  return <button className="counter" onClick={increasePopulation}>one up</button>;
}

export default Controls;
