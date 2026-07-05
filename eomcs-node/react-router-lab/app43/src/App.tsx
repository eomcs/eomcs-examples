import { Form, useActionData } from "react-router";

type ProjectActionData = {
  title: string;
};

export function Project() {
  const actionData = useActionData() as ProjectActionData | undefined;

  return (
    <div>
      <h1>Project</h1>
      <Form method="post">
        <input type="text" name="title" defaultValue="New Title" />
        <button type="submit">Submit</button>
      </Form>
      {actionData ? (
        <p>{actionData.title} updated</p>
      ) : null}
    </div>
  );
}
