import { createBrowserRouter } from "react-router";
import { EndQuiz, Quiz } from "./App";

const router = createBrowserRouter([
  {
    path: "/",
    Component: Quiz,
  },
  {
    path: "/end-quiz",
    Component: EndQuiz,
    action: async ({ request }) => {
      const formData = await request.formData();
      const quizTimedOut = formData.get("quizTimedOut") === "true";

      return {
        quizTimedOut,
        submittedAt: new Date().toLocaleTimeString(),
      };
    },
  },
]);

export default router;
