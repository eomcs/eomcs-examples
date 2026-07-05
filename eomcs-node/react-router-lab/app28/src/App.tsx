import { Link, useLoaderData, useParams } from "react-router";

type TeamLoaderData = {
  name: string;
};

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
  const params = useParams();
  const data = useLoaderData() as TeamLoaderData;

  return (
    <div>
      <h1>{data.name}</h1>
      <p>params.teamId: {params.teamId}</p>
    </div>
  );
}

export function Product() {
  const { categoryId, productId } = useParams();

  return (
    <div>
      <h1>Product</h1>
      <p>categoryId: {categoryId}</p>
      <p>productId: {productId}</p>
    </div>
  );
}
