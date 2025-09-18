"use strict";

// 조건부 렌더링하기

function UserGreeting(props) {
  return React.createElement(
    "h1",
    null,
    "Welcome back!"
  );
}

function GuestGreeting(props) {
  return React.createElement(
    "h1",
    null,
    "Please sign up."
  );
}

function Greeting(props) {
  var isLoggedIn = props.isLoggedIn;
  if (isLoggedIn) {
    return React.createElement(UserGreeting, null);
  }
  return React.createElement(GuestGreeting, null);
}

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Greeting, { isLoggedIn: true }));