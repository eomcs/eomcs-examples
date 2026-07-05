import { createBrowserRouter } from "react-router";
import { Project, SomeComponent } from "./App";

const someApi = {
  async updateProject({
    projectId,
    title,
  }: {
    projectId: string | undefined;
    title: FormDataEntryValue | null;
  }) {
    return {
      id: projectId ?? "unknown",
      title: String(title ?? ""),
    };
  },
};

const router = createBrowserRouter([
  {
    path: "/",
    Component: SomeComponent,
  },
  {
    path: "/projects/:projectId",
    Component: Project,
    action: async ({ params, request }) => {
      const formData = await request.formData();
      const title = formData.get("title");
      const project = await someApi.updateProject({
        projectId: params.projectId,
        title,
      });

      return project;
    },
  },
]);

export default router;
