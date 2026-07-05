import { createBrowserRouter } from "react-router";
import { Items } from "./App";

type TodoItem = {
  id: number;
  title: string;
};

let nextId = 3;

const fakeDb = {
  items: [
    { id: 1, title: "Learn loader" },
    { id: 2, title: "Learn action" },
  ] satisfies TodoItem[],

  async getItems() {
    return this.items;
  },

  async addItem({ title }: { title: FormDataEntryValue | null }) {
    const item = {
      id: nextId++,
      title: String(title ?? ""),
    };

    this.items = [...this.items, item];
    return item;
  },
};

async function action({ request }: { request: Request }) {
  const data = await request.formData();
  await fakeDb.addItem({
    title: data.get("title"),
  });

  return { ok: true };
}

async function loader() {
  const items = await fakeDb.getItems();
  return { items };
}

const router = createBrowserRouter([
  {
    path: "/items",
    action: action,
    loader: loader,
    Component: Items,
  },
]);

export default router;
