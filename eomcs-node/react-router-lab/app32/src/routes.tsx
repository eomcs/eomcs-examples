import {
  createBrowserRouter,
  createContext,
  redirect,
  type LoaderFunctionArgs,
  type MiddlewareFunction,
} from "react-router";
import { Auth, Home, Login, Root } from "./App";

type User = {
  id: string;
  name: string;
};

export const userContext = createContext<User>();

async function rootLoader() {
  console.log("Root loader called");
  
  return {
    message: "Root loader data",
  };
}

async function authLoader({ context }: LoaderFunctionArgs) {
  const user = context.get(userContext);
  
  console.log("Auth loader called with user:", user);

  return {
    userName: user.name,
  };
}

function getUserId() {
  return "100";
}

async function getUserById(userId: string): Promise<User> {
  return {
    id: userId,
    name: `User ${userId}`,
  };
}

const loggingMiddleware: MiddlewareFunction = async ({ request }, next) => {
  const url = new URL(request.url);
  console.log(`Starting navigation: ${url.pathname}${url.search}`);

  const start = performance.now();
  await next();
  const duration = performance.now() - start;

  console.log(`Navigation completed in ${duration}ms`);
};

const authMiddleware: MiddlewareFunction = async ({ context }, next) => {
  const userId = getUserId();

  console.log("Auth middleware called with userId:", userId);
  
  if (!userId) {
    throw redirect("/login");
  }

  context.set(userContext, await getUserById(userId));
  await next();
};

const router = createBrowserRouter([
  {
    path: "/",
    middleware: [loggingMiddleware],
    loader: rootLoader,
    Component: Root,
    children: [
      { index: true, Component: Home },
      { path: "login", Component: Login },
      {
        path: "auth",
        middleware: [authMiddleware],
        loader: authLoader,
        Component: Auth,
      },
    ],
  },
]);

export default router;
