"use strict";

// 여러 개의 컴포넌트 렌더링하기 - 함수 컴포넌트로 처리

function NumberList(props) {
  var numbers = props.numbers;
  var listItems = numbers.map(function (number) {
    return React.createElement(
      "li",
      null,
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