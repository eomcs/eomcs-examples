import { createBrowserRouter } from "react-router";
import { Task } from "./App";

const tasks = new Map([
  ["123", { id: "123", title: "Write route examples" }],
]);

const router = createBrowserRouter([
  {
    path: "/",
    Component: Task,
  },
  {
    path: "/update-task/:taskId",
    action: async ({ params, request }) => {
      const formData = await request.formData();
      const title = String(formData.get("title") ?? "");
      const taskId = params.taskId ?? "unknown";
      const task = { id: taskId, title };

      tasks.set(taskId, task);

      return task;
    },
  },
]);

export default router;
