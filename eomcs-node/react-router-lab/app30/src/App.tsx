import { Link, useLoaderData, useParams } from "react-router";

type FileLoaderData = {
  filePath?: string;
};

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
  const data = useLoaderData() as FileLoaderData;

  return (
    <div>
      <h1>File</h1>
      <p>params["*"]: {splat}</p>
      <p>loader filePath: {data.filePath}</p>
    </div>
  );
}
