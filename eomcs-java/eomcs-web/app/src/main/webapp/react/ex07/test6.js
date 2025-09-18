"use strict";

// JSX에 map() 포함시키기

function ListItem(props) {
  return React.createElement(
    "li",
    null,
    props.value
  );
}

function NumberList(props) {
  var numbers = props.numbers;
  return React.createElement(
    "ul",
    null,
    numbers.map(function (number) {
      return React.createElement(ListItem, { key: number.toString(), value: number });
    })
  );
}

var numbers = [1, 2, 3, 4, 5];
var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(NumberList, { numbers: numbers }));