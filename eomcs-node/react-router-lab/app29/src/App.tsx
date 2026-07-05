import { Link, useLocation, useParams } from "react-router";

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

  return (
    <div>
      <h1>Categories</h1>
      <p>params.lang: {lang ?? "undefined"}</p>
    </div>
  );
}

export function User() {
  const { userId } = useParams();
  const location = useLocation();
  const isEdit = location.pathname.endsWith("/edit");

  return (
    <div>
      <h1>{isEdit ? "Edit User" : "User"}</h1>
      <p>params.userId: {userId}</p>
      <p>edit segment: {isEdit ? "있음" : "없음"}</p>
    </div>
  );
}
