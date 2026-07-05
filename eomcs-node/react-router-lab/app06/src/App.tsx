import { Link, Outlet } from "react-router";

export default function Root() {
  return (
    <div>
      <h1>Root</h1>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/dashboard">Dashboard</Link>{" "}
        <Link to="/dashboard/settings">Settings</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function Home() {
  return <h2>Home</h2>;
}

export function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      <Outlet />
    </div>
  );
}

export function DashboardHome() {
  return <h2>DashboardHome</h2>;
}

export function Settings() {
  return <h2>Settings</h2>;
}
