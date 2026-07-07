import { create } from "zustand";

const initialState = { bears: 0, food: "honey" };

// initialState의 타입을 재사용한다.
type BearState = typeof initialState & {
  increase: (by: number) => void;
  reset: () => void;
};

export const useBearStore = create<BearState>()((set) => ({
  ...initialState,
  increase: (by) => set((s) => ({ bears: s.bears + by })),
  reset: () => set(initialState),
}));
