import { createBrowserRouter } from "react-router";
import { MyRoute } from "./App";

async function getSomeRecords() {
  return [
    { id: 1, title: "First record" },
    { id: 2, title: "Second record" },
    { id: 3, title: "Third record" },
  ];
}

const router = createBrowserRouter([
  {
    path: "/",
    loader: async () => {
      return { records: await getSomeRecords() };
    },
    Component: MyRoute,
  },
]);

export default router;
