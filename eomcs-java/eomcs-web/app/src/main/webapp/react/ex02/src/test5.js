"use strict";

// JSX 속성 및 자식 태그
const url = "https://github.com";
const element = (
  <div>
    <a href='https://github.com'>문자열 링크</a>
    <br />
    <a href={url}>표현식 링크</a>
  </div>
);
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
