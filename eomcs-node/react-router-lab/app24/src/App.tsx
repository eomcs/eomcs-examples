import { Link, Outlet } from "react-router";

export function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      <nav>
        <Link to="/dashboard">Home</Link>{" "}
        <Link to="/dashboard/settings">Settings</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function Home() {
  return <h2>Home</h2>;
}

export function Settings() {
  return <h2>Settings</h2>;
}
