import { Link, useParams } from "react-router";

function useFakeDataLibrary(path: string) {
  return {
    path,
  };
}

export function Home() {
  return (
    <div>
      <h1>Route Params</h1>
      <nav>
        <Link to="/concerts/seoul">Seoul Concerts</Link>{" "}
        <Link to="/concerts/salt-lake-city">Salt Lake City Concerts</Link>
      </nav>
    </div>
  );
}

export function City() {
  const { city } = useParams();
  const data = useFakeDataLibrary(`/api/v2/cities/${city}`);

  return (
    <div>
      <h1>City {city}</h1>
      <p>API path: {data.path}</p>
    </div>
  );
}
