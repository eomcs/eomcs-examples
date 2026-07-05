import { createBrowserRouter } from "react-router";
import { Project } from "./App";

const someApi = {
  async updateProject({ title }: { title: FormDataEntryValue | null }) {
    return {
      id: "100",
      title: String(title ?? ""),
    };
  },
};

const router = createBrowserRouter([
  {
    path: "/projects/:projectId",
    Component: Project,
    action: async ({ request }) => {
      const formData = await request.formData();
      const title = formData.get("title");
      const project = await someApi.updateProject({ title });

      return project;
    },
  },
]);

export default router;
