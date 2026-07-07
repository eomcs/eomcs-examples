import { useState } from "react";
import BearCounter from "./BearCounter";
import Page from "./Page";
import "./App.css";

function App() {
  const [bears, setBears] = useState(0);

  const increasePopulation = () => setBears((prev) => prev + 1);

  return (
    <div className="bear-app">
      <BearCounter bears={bears} />
      <Page increasePopulation={increasePopulation} />
    </div>
  );
}

export default App;
