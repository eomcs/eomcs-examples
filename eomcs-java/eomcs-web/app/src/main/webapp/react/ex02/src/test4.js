"use strict";

// JSX 리턴
function getGreeting(user) {
  if (user) {
    return <h1>Hello, {formatName(user)}!</h1>;
  }
  return <h1>Hello!</h1>;
}
function formatName(user) {
  return user.firstName + " " + user.lastName;
}
const user = {
  firstName: "길동",
  lastName: "홍",
};

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(getGreeting(user));
