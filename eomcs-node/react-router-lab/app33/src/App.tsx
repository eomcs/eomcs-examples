import { useLoaderData } from "react-router";

type MyRouteLoaderData = {
  message: string;
};

export function MyRoute() {
  const data = useLoaderData() as MyRouteLoaderData;

  return <h1>{data.message}</h1>;
}
