"use strict";

// 함수 컴포넌트 만들기
// => 컴포넌트의 이름은 항상 대문자로 시작한다.
function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}

// Welcome() 함수를 통해 DOM 엘리먼트를 생성할 리액트 컴포넌트(엘리먼트)를 만든다.
const element = <Welcome name="홍길동" />;

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
