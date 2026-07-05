import { Link, useParams } from "react-router";

export function ProjectsHome() {
  return (
    <div>
      <h1>ProjectsHome</h1>
      <nav>
        <Link to="/projects/100">Project 100</Link>{" "}
        <Link to="/projects/100/edit">Edit Project 100</Link>
      </nav>
    </div>
  );
}

export function Project() {
  const { pid } = useParams();

  return (
    <div>
      <h1>Project {pid}</h1>
      <nav>
        <Link to="/projects">ProjectsHome</Link>{" "}
        <Link to={`/projects/${pid}/edit`}>Edit Project {pid}</Link>
      </nav>
    </div>
  );
}

export function EditProject() {
  const { pid } = useParams();

  return (
    <div>
      <h1>EditProject {pid}</h1>
      <nav>
        <Link to="/projects">ProjectsHome</Link>{" "}
        <Link to={`/projects/${pid}`}>Project {pid}</Link>
      </nav>
    </div>
  );
}
