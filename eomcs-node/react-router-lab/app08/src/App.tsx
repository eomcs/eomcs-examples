import { Link, useParams } from "react-router";

export function Home() {
  return (
    <div>
      <h1>Dynamic Segments</h1>
      <nav>
        <Link to="/teams/100">Team 100</Link>{" "}
        <Link to="/teams/react-router">Team react-router</Link>{" "}
        <Link to="/c/shoes/p/42">Shoes Product 42</Link>
      </nav>
    </div>
  );
}

export function Team() {
  const { teamId } = useParams();

  return <h2>Team {teamId}</h2>;
}

export function Product() {
  const { categoryId, productId } = useParams();

  return (
    <h2>
      Category {categoryId} / Product {productId}
    </h2>
  );
}
