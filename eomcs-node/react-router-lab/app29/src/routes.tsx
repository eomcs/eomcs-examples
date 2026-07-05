import { createBrowserRouter } from "react-router";
import {
  Categories,
  Home,
  User,
} from "./App";

const router = createBrowserRouter([
  { path: "/", Component: Home },
  { path: ":lang?/categories", Component: Categories },
  { path: "users/:userId/edit?", Component: User },
]);

export default router;
