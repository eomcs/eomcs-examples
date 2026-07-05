import { Link, useLoaderData } from "react-router";

type TeamLoaderData = {
  name: string;
};

export function Home() {
  return (
    <div>
      <h1>Route Objects</h1>
      <nav>
        <Link to="/teams/100">Team 100</Link>{" "}
        <Link to="/teams/react-router">Team react-router</Link>
      </nav>
    </div>
  );
}

export function Team() {
  const data = useLoaderData() as TeamLoaderData;

  return <h1>{data.name}</h1>;
}
