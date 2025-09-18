"use strict";

// 컴포넌트 합성
// => 다른 컴포넌트를 참조 할 수 있다.

function Welcome(props) {
  return React.createElement(
    'h1',
    null,
    'Hello, ',
    props.name
  );
}

function App() {
  return React.createElement(
    'div',
    null,
    React.createElement(Welcome, { name: '\uD64D\uAE38\uB3D9' }),
    React.createElement(Welcome, { name: '\uC784\uAEBD\uC815' }),
    React.createElement(Welcome, { name: '\uC720\uAD00\uC21C' })
  );
}

var element = React.createElement(Welcome, { name: '\uD64D\uAE38\uB3D9' });

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(App());