import { Link } from "react-router";

export function LoggedOutMessage() {
  return (
    <p>
      You've been logged out. <Link to="/login">Login again</Link>
    </p>
  );
}

export function Login() {
  return <h1>Login</h1>;
}
