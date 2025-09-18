"use strict";

// JSX 속성 및 자식 태그
const title = "</h1><h1>삽입공격방지!";
const element = <h1>{title}</h1>; // 태그 문법을 일반 문자열로 취급함!

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
