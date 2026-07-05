import { useFetcher } from "react-router";

type TaskActionData = {
  id: string;
  title: string;
};

export function Task() {
  const fetcher = useFetcher<TaskActionData>();
  const busy = fetcher.state !== "idle";

  return (
    <div>
      <h1>Task</h1>
      <fetcher.Form method="post" action="/update-task/123">
        <input type="text" name="title" defaultValue="New Title" />
        <button type="submit">{busy ? "Saving..." : "Save"}</button>
      </fetcher.Form>
      {fetcher.data ? (
        <p>
          Saved task {fetcher.data.id}: {fetcher.data.title}
        </p>
      ) : null}
    </div>
  );
}
