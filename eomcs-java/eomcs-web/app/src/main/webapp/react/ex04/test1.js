"use strict";

// 1단계: 함수 컴포넌트 사용 전

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
}

setInterval(tick, 1000);