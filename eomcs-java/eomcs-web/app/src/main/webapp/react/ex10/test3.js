"use strict";

// 다른 컴포넌트 담기 - 특수화

function FancyBorder(props) {
  return React.createElement(
    "div",
    { className: "FancyBorder FancyBorder-" + props.color },
    props.children
  );
}

function Dialog(props) {
  return React.createElement(
    FancyBorder,
    { color: "blue" },
    React.createElement(
      "h1",
      { className: "Dialog-title" },
      props.title
    ),
    React.createElement(
      "p",
      { className: "Dialog-message" },
      props.message
    )
  );
}

function WelcomeDialog() {
  return React.createElement(Dialog, { title: "Welcome", message: "Thank you for visiting our spacecraft!" });
}

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(WelcomeDialog, null));