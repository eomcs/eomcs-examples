type ControlsProps = {
  increasePopulation: () => void;
};

function Controls({ increasePopulation }: ControlsProps) {
  return (
    <button className="counter" onClick={increasePopulation}>
      one up
    </button>
  );
}

export default Controls;
