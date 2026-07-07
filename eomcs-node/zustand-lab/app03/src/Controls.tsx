import { useBearContext } from "./BearContext";

function Controls() {
  const { increasePopulation } = useBearContext();
  return <button className="counter" onClick={increasePopulation}>one up</button>;
}

export default Controls;
