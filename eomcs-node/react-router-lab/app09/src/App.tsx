import { Link, useParams } from "react-router";

export function Home() {
  return (
    <div>
      <h1>Optional Segments</h1>
      <nav>
        <Link to="/categories">Categories</Link>{" "}
        <Link to="/ko/categories">Korean Categories</Link>{" "}
        <Link to="/users/100">User 100</Link>{" "}
        <Link to="/users/100/edit">Edit User 100</Link>
      </nav>
    </div>
  );
}

export function Categories() {
  const { lang } = useParams();

  return <h2>Categories (lang: {lang ?? "없음"})</h2>;
}

export function User() {
  const { userId } = useParams();

  return <h2>User {userId}</h2>;
}
