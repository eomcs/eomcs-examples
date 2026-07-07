import { useShallow } from "zustand/react/shallow";
import { useBearStore } from "./bearStore";

function MultipleSelectors() {
  const { bears, food } = useBearStore(
   useShallow((state) => ({ bears: state.bears, food: state.food })),
  );

  console.log("MultipleSelectors render");

  return (
    <div>
      We have {food} units of food for {bears} bears
    </div>
  );
}

export default MultipleSelectors;