"use strict";

// 컴포넌트 합성
// => 다른 컴포넌트를 참조 할 수 있다.
function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}

function App() {
  return (
    <div>
      <Welcome name="홍길동" />
      <Welcome name="임꺽정" />
      <Welcome name="유관순" />
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(App());
