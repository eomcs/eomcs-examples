"use strict";

// JSX 리턴

function getGreeting(user) {
  if (user) {
    return React.createElement(
      "h1",
      null,
      "Hello, ",
      formatName(user),
      "!"
    );
  }
  return React.createElement(
    "h1",
    null,
    "Hello!"
  );
}
function formatName(user) {
  return user.firstName + " " + user.lastName;
}
var user = {
  firstName: "길동",
  lastName: "홍"
};

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(getGreeting(user));