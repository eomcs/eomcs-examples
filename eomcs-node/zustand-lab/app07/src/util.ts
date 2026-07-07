import { type BearState } from "./bearState";

// 추출한 타입 사용
export function logBearState(state: BearState) {
  console.log(`We have ${state.bears} bears eating ${state.food}`);
}
