import { createBrowserRouter } from "react-router";
import { File, Home } from "./App";

const router = createBrowserRouter([
  { path: "/", Component: Home },
  {
    path: "files/*",
    loader: async ({ params }) => {
      const { "*": splat } = params;
      return { filePath: splat };
    },
    Component: File,
  },
]);

export default router;
