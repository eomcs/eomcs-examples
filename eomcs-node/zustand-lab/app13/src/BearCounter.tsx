import { shallow } from "zustand/shallow";
import { useBearStore } from "./bearStore";

function BearCounter() {
  const { bears, food } = useBearStore(
    (state) => ({ bears: state.bears, food: state.food }),
    shallow, 
  );

  console.log("BearCounter render");

  return (
    <div>
      We have {food} units of food for {bears} bears
    </div>
  );
}

export default BearCounter;
