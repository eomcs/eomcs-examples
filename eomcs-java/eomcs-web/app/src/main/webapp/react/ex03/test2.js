"use strict";

// React element 업데이트하기

var root = ReactDOM.createRoot(document.getElementById("root"));

function tick() {
  var element = React.createElement(
    "div",
    null,
    React.createElement(
      "h1",
      null,
      "Hello, world!"
    ),
    React.createElement(
      "h2",
      null,
      "It is ",
      new Date().toLocaleTimeString(),
      "."
    )
  );
  root.render(element);
  // 코드는 전체 UI를 다시 그리는 것이지만,
  // React DOM은 변경된 텍스트 노드만 다시 그린다.
  // 개발자 도구의 Element를 확인해보라!
}

setInterval(tick, 1000);