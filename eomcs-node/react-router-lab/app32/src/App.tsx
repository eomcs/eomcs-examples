import { Link, Outlet, useLoaderData } from "react-router";

type RootLoaderData = {
  message: string;
};

type AuthLoaderData = {
  userName: string;
};

export function Root() {
  const data = useLoaderData() as RootLoaderData;

  return (
    <div>
      <h1>Middleware</h1>
      <p>{data.message}</p>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/auth">Auth</Link>{" "}
        <Link to="/login">Login</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function Home() {
  return <h2>Home</h2>;
}

export function Auth() {
  const data = useLoaderData() as AuthLoaderData;

  return <h2>Authenticated as {data.userName}</h2>;
}

export function Login() {
  return <h2>Login</h2>;
}
