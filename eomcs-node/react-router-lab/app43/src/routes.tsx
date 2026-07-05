import { createBrowserRouter } from "react-router";
import { Project } from "./App";

const router = createBrowserRouter([
  {
    path: "/",
    Component: Project,
    action: async ({ request }) => {
      const formData = await request.formData();
      const title = String(formData.get("title") ?? "");

      return { title };
    },
  },
]);

export default router;
