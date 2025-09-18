"use strict";

// 제어 컴포넌트의 value에 값을 지정할 때와 null을 지정할 때
// => 값을 지정하면, 사용자가 변경할 수 없다.
// => null을 지정하면, 사용자가 변경할 수 있다.

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<input value="hi" />);

setTimeout(function () {
  root.render(<input value={null} />);
}, 3000);
