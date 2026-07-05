import { Link, Outlet, useParams } from "react-router";

export function Root() {
  return (
    <div>
      <h1>Root</h1>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/about">About</Link>{" "}
        <Link to="/auth/login">Login</Link>{" "}
        <Link to="/auth/register">Register</Link>{" "}
        <Link to="/concerts">Concerts</Link>{" "}
        <Link to="/concerts/seoul">Seoul Concerts</Link>{" "}
        <Link to="/concerts/trending">Trending Concerts</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function Home() {
  return <h2>Home</h2>;
}

export function About() {
  return <h2>About</h2>;
}

export function AuthLayout() {
  return (
    <div>
      <h2>Auth</h2>
      <Outlet />
    </div>
  );
}

export function Login() {
  return <h3>Login</h3>;
}

export function Register() {
  return <h3>Register</h3>;
}

export function ConcertsHome() {
  return <h2>Concerts</h2>;
}

export function ConcertsCity() {
  const { city } = useParams();

  return <h2>{city} Concerts</h2>;
}

export function ConcertsTrending() {
  return <h2>Trending Concerts</h2>;
}
