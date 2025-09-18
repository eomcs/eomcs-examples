"use strict";

// ? : 연산자 사용하기

function LoginStatus(props) {
  const isLoggedIn = props.isLoggedIn;
  return (
    <div>
      The user is <b>{isLoggedIn ? "currently" : "not"}</b> logged in.
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<LoginStatus isLoggedIn={false} />);
