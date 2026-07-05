import { NavLink, useParams } from "react-router";
import "./App.css";

export function MyAppNav() {
  return (
    <nav className="site-nav">
      <NavLink to="/" end>
        Home
      </NavLink>
      <NavLink to="/trending" end>
        Trending Concerts
      </NavLink>
      <NavLink to="/concerts">All Concerts</NavLink>
      <NavLink to="/account">Account</NavLink>
      <NavLink
        to="/messages"
        className={({ isActive }) =>
          isActive ? "text-red-500" : "text-black"
        }
      >
        Messages
      </NavLink>
      <NavLink
        to="/messages"
        style={({ isActive }) => ({
          color: isActive ? "red" : "black",
        })}
      >
        Styled Messages
      </NavLink>
      <NavLink to="/message">
        {({ isActive }) => (
          <span className={isActive ? "active" : ""}>
            {isActive ? "👉" : ""} Tasks
          </span>
        )}
      </NavLink>
    </nav>
  );
}

export function Home() {
  return (
    <div>
      <MyAppNav />
      <h1>Home</h1>
    </div>
  );
}

export function Trending() {
  return (
    <div>
      <MyAppNav />
      <h1>Trending Concerts</h1>
    </div>
  );
}

export function Concerts() {
  const { city } = useParams();

  return (
    <div>
      <MyAppNav />
      <h1>{city ? `${city} Concerts` : "All Concerts"}</h1>
    </div>
  );
}

export function Account() {
  return (
    <div>
      <MyAppNav />
      <h1>Account</h1>
    </div>
  );
}

export function Messages() {
  return (
    <div>
      <MyAppNav />
      <h1>Messages</h1>
    </div>
  );
}

export function Tasks() {
  return (
    <div>
      <MyAppNav />
      <h1>Tasks</h1>
    </div>
  );
}
