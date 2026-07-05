import { createBrowserRouter } from "react-router";
import { MyRoute } from "./App";

async function loader() {
  return { message: "Hello, world!" };
}

const router = createBrowserRouter([
  {
    path: "/",
    loader: loader,
    Component: MyRoute,
  },
]);

export default router;
