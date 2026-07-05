import { Form, useActionData, useLoaderData } from "react-router";

type TodoItem = {
  id: number;
  title: string;
};

type ItemsLoaderData = {
  items: TodoItem[];
};

type ItemsActionData = {
  ok: boolean;
};

function List({ items }: { items: TodoItem[] }) {
  return (
    <ul>
      {items.map((item) => (
        <li key={item.id}>{item.title}</li>
      ))}
    </ul>
  );
}

export function Items() {
  const data = useLoaderData() as ItemsLoaderData;
  const actionData = useActionData() as ItemsActionData | undefined;

  return (
    <div>
      <h1>Items</h1>
      <List items={data.items} />
      <Form method="post" navigate={false}>
        <input type="text" name="title" />
        <button type="submit">Create Todo</button>
      </Form>
      {actionData?.ok ? <p>Todo created.</p> : null}
    </div>
  );
}
