import { create } from "zustand";
import { combine } from "zustand/middleware";

interface BearState {
  bears: number;
  increase: (by: number) => void;
}

// 상태와 액션을 분리한다.
export const useBearStore = create<BearState>()(
  combine({ 
      bears: 0 
    }, (set) => ({
    increase: (by: number) => set((s) => ({ bears: s.bears + by })),
  })),
);

