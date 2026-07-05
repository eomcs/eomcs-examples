import { Link, Outlet } from "react-router";

export function Home() {
  return (
    <div>
      <h1>Home</h1>
      <nav>
        <Link to="/dashboard">Dashboard</Link>{" "}
        <Link to="/dashboard/settings">Dashboard Settings</Link>
      </nav>
    </div>
  );
}

export function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/dashboard">Dashboard Home</Link>{" "}
        <Link to="/dashboard/settings">Settings</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function DashboardHome() {
  return <h2>DashboardHome</h2>;
}

export function DashboardSettings() {
  return <h2>DashboardSettings</h2>;
}
