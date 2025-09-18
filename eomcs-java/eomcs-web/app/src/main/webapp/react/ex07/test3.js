"use strict";

// 목록의 각 항목에 key 추가하기

function NumberList(props) {
  var numbers = props.numbers;
  var listItems = numbers.map(function (number) {
    return React.createElement(
      "li",
      { key: number.toString() },
      number
    );
  });
  return React.createElement(
    "ul",
    null,
    listItems
  );
}

var numbers = [1, 2, 3, 4, 5];
var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(NumberList, { numbers: numbers }));