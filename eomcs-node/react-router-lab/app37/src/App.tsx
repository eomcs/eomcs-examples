import { Link, Outlet, useMatches } from "react-router";

type BreadcrumbHandle = {
  breadcrumb?: string;
};

export function Breadcrumbs() {
  const matches = useMatches();
  const crumbs = matches
    .filter((match) => (match.handle as BreadcrumbHandle | undefined)?.breadcrumb)
    .map((match) => (match.handle as BreadcrumbHandle).breadcrumb);

  return (
    <nav>
      {crumbs.map((crumb, index) => (
        <span key={index}>
          {index > 0 ? " / " : ""}
          {crumb}
        </span>
      ))}
    </nav>
  );
}

export function App() {
  return (
    <div>
      <Breadcrumbs />
      <h1>App</h1>
      <nav>
        <Link to="/app">App Home</Link>{" "}
        <Link to="/app/settings">Settings</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function AppHome() {
  return <h2>App Home</h2>;
}

export function Settings() {
  return <h2>Settings</h2>;
}
