import {
  createBrowserRouter,
  type ShouldRevalidateFunctionArgs,
} from "react-router";
import { MyRoute } from "./App";

async function loader() {
  return {
    loadedAt: new Date().toLocaleTimeString(),
  };
}

function shouldRevalidate(arg: ShouldRevalidateFunctionArgs) {
  console.log("shouldRevalidate called:", {
    currentUrl: arg.currentUrl.href,
    nextUrl: arg.nextUrl.href,
    defaultShouldRevalidate: arg.defaultShouldRevalidate,
  });

  return true;
}

const router = createBrowserRouter([
  {
    path: "/",
    loader: loader,
    shouldRevalidate: shouldRevalidate,
    Component: MyRoute,
  },
]);

export default router;
