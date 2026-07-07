import { create } from "zustand";

interface BearStore {
  bears: number;
  foodPerBear: number;
  increase: (by: number) => void;
}

export const useBearStore = create<BearStore>((set) => ({
  bears: 3,
  foodPerBear: 2,
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));

