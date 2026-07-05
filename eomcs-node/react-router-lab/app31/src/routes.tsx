import { createBrowserRouter } from "react-router";
import { MyRouteComponent } from "./App";

const router = createBrowserRouter([
  {
    path: "/",
    Component: MyRouteComponent,
  },
]);

export default router;
