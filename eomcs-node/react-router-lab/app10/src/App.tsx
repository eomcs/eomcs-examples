import { Link, useParams } from "react-router";

export function Home() {
  return (
    <div>
      <h1>Splats</h1>
      <nav>
        <Link to="/files/docs/2026/report.pdf">Report File</Link>{" "}
        <Link to="/files/images/profile/avatar.png">Avatar File</Link>
      </nav>
    </div>
  );
}

export function File() {
  const { "*": splat } = useParams();

  return <h2>File path: {splat}</h2>;
}
