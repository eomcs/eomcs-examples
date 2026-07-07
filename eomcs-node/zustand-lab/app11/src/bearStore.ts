import { create } from "zustand";
import { persist } from "zustand/middleware";

interface BearState {
  bears: number;
  increase: (by: number) => void;
}

// 상태를 localStorage에 저장하고 불러오는 기능을 추가한다.
export const useBearStore = create<BearState>()(
  persist(
    (set) => ({
      bears: 0,
      increase: (by: number) => set((s) => ({ bears: s.bears + by })),
    }),
    { name: "bear-storage" }, // localStorage 키
  ),
);

