"use strict";

// ? : 연산자 사용하기

function LoginStatus(props) {
  var isLoggedIn = props.isLoggedIn;
  return React.createElement(
    "div",
    null,
    "The user is ",
    React.createElement(
      "b",
      null,
      isLoggedIn ? "currently" : "not"
    ),
    " logged in."
  );
}

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(LoginStatus, { isLoggedIn: false }));