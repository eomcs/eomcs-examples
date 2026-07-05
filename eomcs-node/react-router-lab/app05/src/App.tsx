import { Link, Outlet, useParams } from "react-router";

export function MarketingLayout() {
  return (
    <div>
      <header>Marketing</header>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/contact">Contact</Link>{" "}
        <Link to="/projects">Projects</Link>{" "}
        <Link to="/projects/100">Project 100</Link>{" "}
        <Link to="/projects/100/edit">Edit Project 100</Link>
      </nav>
      <Outlet />
    </div>
  );
}

export function MarketingHome() {
  return <h2>MarketingHome</h2>;
}

export function Contact() {
  return <h2>Contact</h2>;
}

export function ProjectsHome() {
  return <h2>ProjectsHome</h2>;
}

export function ProjectsLayout() {
  return (
    <div>
      <nav>Projects Nav</nav>
      <Outlet />
    </div>
  );
}

export function Project() {
  const { pid } = useParams();

  return <h2>Project {pid}</h2>;
}

export function EditProject() {
  const { pid } = useParams();

  return <h2>EditProject {pid}</h2>;
}
