"use strict";

// JSX 속성 및 자식 태그

var url = "https://github.com";
var element = React.createElement(
  "div",
  null,
  React.createElement(
    "a",
    { href: "https://github.com" },
    "\uBB38\uC790\uC5F4 \uB9C1\uD06C"
  ),
  React.createElement("br", null),
  React.createElement(
    "a",
    { href: url },
    "\uD45C\uD604\uC2DD \uB9C1\uD06C"
  )
);
var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);