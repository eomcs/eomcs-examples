import { createBrowserRouter } from "react-router";
import {
  EditProject,
  Project,
  ProjectsHome,
} from "./App";

const router = createBrowserRouter([
  {
    path: "/projects",
    children: [
      { index: true, Component: ProjectsHome },
      { path: ":pid", Component: Project },
      { path: ":pid/edit", Component: EditProject },
    ],
  },
]);

export default router;
