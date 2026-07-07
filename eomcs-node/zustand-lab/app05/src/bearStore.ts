import { create } from "zustand";

// 상태와 액션의 타입을 정의한다.
interface BearState {
  bears: number;
  food: string;
  feed: (food: string) => void;
}

// create의 curried form을 사용해 스토어를 생성한다.
export const useBearStore = create<BearState>()((set) => ({
  bears: 2,
  food: "honey",
  feed: (food) => set(() => ({ food })),
}));
