"use strict";

// JSX 표현식
const username = "홍길동";
const element = <h1>Hello, {username}</h1>;

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
