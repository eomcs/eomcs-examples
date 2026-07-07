type BearCounterProps = {
  bears: number;
};

function BearCounter({ bears }: BearCounterProps) {
  return <h1>{bears} bears around here...</h1>;
}

export default BearCounter;
