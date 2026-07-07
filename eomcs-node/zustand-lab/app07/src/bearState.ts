import { type ExtractState } from "zustand";
import { useBearStore } from "./bearStore";

// 라이브러리/외부 스토어 객체에서 상태의 타입을 공개하지 않았을 때,
// ExtractState를 사용하여 스토어 객체에서 타입을 추출할 수 있다.
export type BearState = ExtractState<typeof useBearStore>;
