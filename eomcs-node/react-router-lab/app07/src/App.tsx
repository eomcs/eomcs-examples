import { Link, Outlet, useParams } from "react-router";

export function ProjectsHome() {
  return (
    <div>
      <h2>ProjectsHome</h2>
      <nav>
        <Link to="/projects/100">Project 100</Link>{" "}
        <Link to="/projects/100/edit">Edit Project 100</Link>
      </nav>
    </div>
  );
}

export function ProjectsLayout() {
  return (
    <div>
      <nav>
        <Link to="/projects">ProjectsHome</Link>{" "}
        <Link to="/projects/100">Project 100</Link>{" "}
        <Link to="/projects/100/edit">Edit Project 100</Link>
      </nav>
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
