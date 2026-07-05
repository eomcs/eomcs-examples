import { Link, Outlet, useParams } from "react-router";

export function Home() {
  return (
    <div>
      <h1>Home</h1>
      <nav>
        <Link to="/about">About</Link>{" "}
        <Link to="/login">Login</Link>{" "}
        <Link to="/register">Register</Link>{" "}
        <Link to="/concerts">Concerts</Link>{" "}
        <Link to="/concerts/seoul">Seoul Concerts</Link>{" "}
        <Link to="/concerts/trending">Trending Concerts</Link>
      </nav>
    </div>
  );
}

export function About() {
  return <h1>About</h1>;
}

export function AuthLayout() {
  return (
    <div>
      <h1>Auth</h1>
      <Outlet />
    </div>
  );
}

export function Login() {
  return <h2>Login</h2>;
}

export function Register() {
  return <h2>Register</h2>;
}

export function ConcertsHome() {
  return <h1>Concerts</h1>;
}

export function City() {
  const { city } = useParams();

  return <h1>{city} Concerts</h1>;
}

export function Trending() {
  return <h1>Trending Concerts</h1>;
}
