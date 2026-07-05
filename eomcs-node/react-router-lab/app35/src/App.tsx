import { Link, useLoaderData, useSearchParams } from "react-router";

type MyRouteLoaderData = {
  loadedAt: string;
};

export function MyRoute() {
  const data = useLoaderData() as MyRouteLoaderData;
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") ?? "none";

  return (
    <div>
      <h1>shouldRevalidate</h1>
      <p>Loader ran at: {data.loadedAt}</p>
      <p>Current page search param: {page}</p>
      <nav>
        <Link to="/">No page</Link>{" "}
        <Link to="/?page=1">Page 1</Link>{" "}
        <Link to="/?page=2">Page 2</Link>
      </nav>
    </div>
  );
}
