import { create } from "zustand";

// 상태와 액션의 타입을 정의한다.
export type BearState = {
  bears: number;
  increasePopulation: () => void;
};

// zustand를 사용하여 스토어를 생성한다.
export const useBearStore = create<BearState>((set) => ({
  bears: 0,
  increasePopulation: () => set((state) => ({ bears: state.bears + 1 })),
}));

