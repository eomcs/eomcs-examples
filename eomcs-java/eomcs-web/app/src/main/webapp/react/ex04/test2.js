"use strict";

// 2단계: 함수 컴포넌트 사용 후

var root = ReactDOM.createRoot(document.getElementById("root"));

function Clock(props) {
  return React.createElement(
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
      props.date.toLocaleTimeString(),
      "."
    )
  );
}

function tick() {
  root.render(React.createElement(Clock, { date: new Date() }));
}

setInterval(tick, 1000);