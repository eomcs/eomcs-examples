import { useLoaderData } from "react-router";

type RecordItem = {
  id: number;
  title: string;
};

type MyRouteLoaderData = {
  records: RecordItem[];
};

export function MyRoute() {
  const { records } = useLoaderData() as MyRouteLoaderData;

  return (
    <>
      <h1>Records: {records.length}</h1>
      <ul>
        {records.map((record) => (
          <li key={record.id}>{record.title}</li>
        ))}
      </ul>
    </>
  );
}
