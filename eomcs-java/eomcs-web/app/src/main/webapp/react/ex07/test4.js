"use strict";

// key로 컴포넌트 추출하기

function ListItem(props) {
  return React.createElement(
    "li",
    null,
    props.value
  );
}

function NumberList(props) {
  var numbers = props.numbers;
  var listItems = numbers.map(function (number) {
    return (
      // key는 배열 안에 넣어야 한다.
      React.createElement(ListItem, { key: number.toString(), value: number })
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