import { useBearStore } from "./bearStore";

function ResetZoo() {
  const { increase, reset } = useBearStore();

  return (
    <div>
      <button onClick={() => increase(5)}>Increase by 5</button>
      <button onClick={reset}>Reset</button>
    </div>
  );
}

export default ResetZoo;
