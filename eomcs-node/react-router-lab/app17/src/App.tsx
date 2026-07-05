import { type ReactNode, useEffect } from "react";
import { Link, useLocation } from "react-router";

function sendFakeAnalytics(pathname: string) {
  console.log(`Analytics pageview: ${pathname}`);
}

function fakeRestoreScroll(locationKey: string) {
  console.log(`Restore scroll for location: ${locationKey}`);
}

function useAnalytics() {
  const location = useLocation();

  useEffect(() => {
    sendFakeAnalytics(location.pathname);
  }, [location]);
}

function useScrollRestoration() {
  const location = useLocation();

  useEffect(() => {
    fakeRestoreScroll(location.key);
  }, [location]);
}

export default function App({ children }: { children: ReactNode }) {
  useAnalytics();
  useScrollRestoration();

  const location = useLocation();

  return (
    <div>
      <nav>
        <Link to="/">Home</Link>{" "}
        <Link to="/about">About</Link>
      </nav>
      <section>
        <h1>Location Object</h1>
        <p>Pathname: {location.pathname}</p>
        <p>Search: {location.search || "(empty)"}</p>
        <p>Hash: {location.hash || "(empty)"}</p>
        <p>Key: {location.key}</p>
      </section>
      {children}
    </div>
  );
}

export function Home() {
  return <h2>Home</h2>;
}

export function About() {
  return <h2>About</h2>;
}
