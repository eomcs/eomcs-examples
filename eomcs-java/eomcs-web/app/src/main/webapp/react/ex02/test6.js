"use strict";

// JSX 속성 및 자식 태그

var title = "</h1><h1>삽입공격방지!</h1>";
var element = React.createElement(
  "h1",
  null,
  title
); // 태그 문법을 일반 문자열로 취급함!

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);