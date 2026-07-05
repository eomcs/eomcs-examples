import { createBrowserRouter } from "react-router";
import { App, AppHome, Breadcrumbs, Settings } from "./App";

const router = createBrowserRouter([
  {
    path: "/app",
    Component: App,
    handle: {
      breadcrumb: "App",
    },
    children: [
      { index: true, Component: AppHome },
      {
        path: "settings",
        Component: Settings,
        handle: {
          breadcrumb: "Settings",
        },
      },
    ],
  },
  {
    path: "/breadcrumbs",
    Component: Breadcrumbs,
    handle: {
      breadcrumb: "Breadcrumbs",
    },
  },
]);

export default router;
