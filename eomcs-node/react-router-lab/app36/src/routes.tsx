import { createBrowserRouter } from "react-router";

const router = createBrowserRouter([
  {
    path: "/app",
    lazy: async () => {
      const [componentModule, loaderModule] = await Promise.all([
        import("./App"),
        import("./app-loader"),
      ]);

      return {
        Component: componentModule.Component,
        loader: loaderModule.loader,
      };
    },
  },
]);

export default router;
