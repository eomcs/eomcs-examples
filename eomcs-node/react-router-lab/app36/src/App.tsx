import { useLoaderData } from "react-router";

type AppLoaderData = {
  message: string;
};

export function Component() {
  const data = useLoaderData() as AppLoaderData;

  return (
    <div>
      <h1>Lazy Route</h1>
      <p>{data.message}</p>
    </div>
  );
}
