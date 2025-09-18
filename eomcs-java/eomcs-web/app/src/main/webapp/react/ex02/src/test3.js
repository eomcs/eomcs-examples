"use strict";

// JSX {모든 자바스크립트 표현식} 기능
function formatName(user) {
  return user.firstName + " " + user.lastName;
}
const user = {
  firstName: "길동",
  lastName: "홍",
};
const element = <h1>Hello, {formatName(user)}!</h1>;

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(element);
