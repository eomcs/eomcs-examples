import { useBearStore } from "./bearStore";

function TotalFood() {
  // 유도된 값: 모든 곰에게 필요한 먹이의 양
  const totalFood = useBearStore((s) => s.bears * s.foodPerBear);

  // 스토어에 `{ totalFood: 6 }`라는 별도 프로퍼티를 둘 필요가 없다.
  return <div>We need ${totalFood} jars of honey</div>;
}

export default TotalFood;