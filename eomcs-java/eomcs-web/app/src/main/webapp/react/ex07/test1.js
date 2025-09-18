"use strict";

// 여러 개의 컴포넌트 렌더링하기

var numbers = [1, 2, 3, 4, 5];
var listItems = numbers.map(function (number) {
  return React.createElement(
    "li",
    null,
    number
  );
});

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(
  "ul",
  null,
  listItems
));