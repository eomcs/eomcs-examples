import { createContext, useContext, useState, type ReactNode } from "react";

type BearContextValue = {
  bears: number;
  increasePopulation: () => void;
};

const BearContext = createContext<BearContextValue | null>(null);

export function BearProvider({ children }: { children: ReactNode }) {
  const [bears, setBears] = useState(0);
  const increasePopulation = () => setBears((prev) => prev + 1);

  return (
    <BearContext.Provider value={{ bears, increasePopulation }}>
      {children}
    </BearContext.Provider>
  );
}

export function useBearContext() {
  const ctx = useContext(BearContext);
  if (!ctx) throw new Error("BearProvider 안에서만 사용할 수 있다.");
  return ctx;
}