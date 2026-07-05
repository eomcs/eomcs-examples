import { Link, useSearchParams } from "react-router";

export function Home() {
  return (
    <div>
      <h1>URL Search Params</h1>
      <nav>
        <Link to="/search?q=react-router">Search react-router</Link>{" "}
        <Link to="/search?q=vite">Search vite</Link>
      </nav>
    </div>
  );
}

function FakeSearchResults() {
  return <p>Fake search results...</p>;
}

export function SearchResults() {
  const [searchParams] = useSearchParams();

  return (
    <div>
      <p>
        You searched for <i>{searchParams.get("q")}</i>
      </p>
      <FakeSearchResults />
    </div>
  );
}
