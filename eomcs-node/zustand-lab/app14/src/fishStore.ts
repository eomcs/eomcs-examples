import { create } from "zustand";

// 명시적인 타입을 가진 fish store
interface FishState {
  fish: number;
  addFish: () => void;
}

export const useFishStore = create<FishState>()((set) => ({
  fish: 5,
  addFish: () => set((s) => ({ fish: s.fish + 1 })),
}));