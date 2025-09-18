"use strict";

// JSX {모든 자바스크립트 표현식} 기능

function formatName(user) {
  return user.firstName + " " + user.lastName;
}
var user = {
  firstName: "길동",
  lastName: "홍"
};
var element = React.createElement(
  "h1",
  null,
  "Hello, ",
  formatName(user),
  "!"
);

var root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);