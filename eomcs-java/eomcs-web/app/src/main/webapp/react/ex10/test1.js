"use strict";

// 다른 컴포넌트 담기

function FancyBorder(props) {
  return React.createElement(
    "div",
    { className: "FancyBorder FancyBorder-" + props.color },
    props.children
  );
}

function WelcomeDialog() {
  return React.createElement(
    FancyBorder,
    { color: "blue" },
    React.createElement(
      "h1",
      { className: "Dialog-title" },
      "Welcome"
    ),
    React.createElement(
      "p",
      { className: "Dialog-message" },
      "Thank you for visiting our spacecraft!"
    )
  );
}

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(WelcomeDialog, null));