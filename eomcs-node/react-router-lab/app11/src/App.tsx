import { Link, NavLink, useParams } from "react-router";
import "./App.css";

function Header() {
  return (
    <nav className="site-nav">
      <NavLink
        to="/"
        className={({ isActive }) => (isActive ? "active" : "")}
        end
      >
        Home
      </NavLink>{" "}
      <Link to="/concerts/salt-lake-city">Concerts</Link>
    </nav>
  );
}

export function Home() {
  return (
    <div>
      <Header />
      <h1>Home</h1>
    </div>
  );
}

export function Concerts() {
  const { city } = useParams();

  return (
    <div>
      <Header />
      <h1>Concerts</h1>
      <p>City: {city}</p>
    </div>
  );
}
