import { createWithEqualityFn } from "zustand/traditional";

interface BearStore {
  bears: number;
  food: string;
  increase: (by: number) => void;
}

export type BearState = BearStore;

export const useBearStore = createWithEqualityFn<BearStore>((set) => ({
  bears: 3,
  food: "honey",
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));
