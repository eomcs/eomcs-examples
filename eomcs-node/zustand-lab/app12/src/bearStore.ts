import { create } from "zustand";

interface BearData {
  count: number;
}

function getBearData(): Promise<BearData> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({ count: 7 });
    }, 2000);
  });
}

interface BearState {
  bears: number;
  fetchBears: () => Promise<void>;
}

export const useBearStore = create<BearState>()((set) => ({
  bears: 0,
  fetchBears: async () => {
    const data = await getBearData();
    set({ bears: data.count });
  },
}));
