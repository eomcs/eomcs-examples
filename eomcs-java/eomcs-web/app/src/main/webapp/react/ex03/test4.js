"use strict";

// 함수 컴포넌트 만들기
// => 컴포넌트의 이름은 항상 대문자로 시작한다.

function Welcome(props) {
  return React.createElement(
    "h1",
    null,
    "Hello, ",
    props.name
  );
}

var element = React.createElement(Welcome, { name: "\uD64D\uAE38\uB3D9" });

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);