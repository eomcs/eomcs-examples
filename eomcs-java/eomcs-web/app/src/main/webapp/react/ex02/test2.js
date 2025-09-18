"use strict";

// JSX 표현식

var username = "홍길동";
var element = React.createElement(
  "h1",
  null,
  "Hello, ",
  username
);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);