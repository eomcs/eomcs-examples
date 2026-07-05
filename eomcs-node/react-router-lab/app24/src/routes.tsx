import { createBrowserRouter } from "react-router";
import { Dashboard, Home, Settings } from "./App";

const router = createBrowserRouter([
  {
    path: "/dashboard",
    Component: Dashboard,
    children: [
      { index: true, Component: Home },
      { path: "settings", Component: Settings },
    ],
  },
]);

export default router;
