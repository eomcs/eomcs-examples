import { createBrowserRouter } from "react-router";
import {
  Contact,
  EditProject,
  Home,
  MarketingLayout,
  Project,
  ProjectLayout,
  ProjectsHome,
} from "./App";

const router = createBrowserRouter([
  {
    Component: MarketingLayout,
    children: [
      { index: true, Component: Home },
      { path: "contact", Component: Contact },
    ],
  },
  {
    path: "projects",
    children: [
      { index: true, Component: ProjectsHome },
      {
        Component: ProjectLayout,
        children: [
          { path: ":pid", Component: Project },
          { path: ":pid/edit", Component: EditProject },
        ],
      },
    ],
  },
]);

export default router;
