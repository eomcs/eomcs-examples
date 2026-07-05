import { createBrowserRouter } from "react-router";
import {
  Dashboard,
  DashboardHome,
  DashboardSettings,
  Home,
} from "./App";

const router = createBrowserRouter([
  { index: true, Component: Home },
  {
    Component: Dashboard,
    path: "/dashboard",
    children: [
      { index: true, Component: DashboardHome },
      { path: "settings", Component: DashboardSettings },
    ],
  },
]);

export default router;
