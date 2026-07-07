import { create } from "zustand";

// 명시적인 타입을 가진 bear store
interface BearState {
  bears: number;
  addBear: () => void;
}

export const useBearStore = create<BearState>()((set) => ({
  bears: 2,
  addBear: () => set((s) => ({ bears: s.bears + 1 })),
}));