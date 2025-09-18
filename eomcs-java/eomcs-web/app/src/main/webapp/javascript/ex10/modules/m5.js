const v1 = 100;
const v2 = "aaa";
function f1() {
  console.log("test..ok!");
}

export { v1, f1 }; // 목록으로 내보낼 수 있다.
export default v2;
