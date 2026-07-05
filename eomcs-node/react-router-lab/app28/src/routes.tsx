import { createBrowserRouter } from "react-router";
import {
  Home,
  Product,
  Team,
} from "./App";

async function fetchTeam(teamId: string | undefined) {
  return {
    id: teamId,
    name: `Team ${teamId}`,
  };
}

const router = createBrowserRouter([
  { path: "/", Component: Home },
  {
    path: "teams/:teamId",
    loader: async ({ params }) => {
      const team = await fetchTeam(params.teamId);
      return { name: team.name };
    },
    Component: Team,
  },
  { path: "c/:categoryId/p/:productId", Component: Product },
]);

export default router;
