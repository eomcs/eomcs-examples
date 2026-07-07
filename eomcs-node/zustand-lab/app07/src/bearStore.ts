import { create } from "zustand";

interface BearStore {
  bears: number;
  food: string;
  increase: (by: number) => void;
}

export const useBearStore = create<BearStore>((set) => ({
  bears: 3,
  food: "honey",
  increase: (by: number) => set((s) => ({ bears: s.bears + by })),
}));

