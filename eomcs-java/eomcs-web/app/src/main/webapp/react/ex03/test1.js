"use strict";

// DOM element vs React element

var element = React.createElement(
  "h1",
  null,
  "Hello, world!"
);
var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);