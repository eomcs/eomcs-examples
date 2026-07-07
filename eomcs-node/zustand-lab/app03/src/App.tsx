import { BearProvider } from "./BearContext";
import BearCounter from "./BearCounter";
import Page from "./Page";
import "./App.css";

function App() {
  return (
    <div className="bear-app">
      <BearProvider>
        <BearCounter />
        <Page />
      </BearProvider>
    </div>
  );
}

export default App;
