import { Form, useActionData, useParams } from "react-router";

type ProjectActionData = {
  id: string;
  title: string;
};

export function Project() {
  const { projectId } = useParams();
  const actionData = useActionData() as ProjectActionData | undefined;

  return (
    <div>
      <h1>Project {projectId}</h1>
      <Form method="post">
        <input type="text" name="title" defaultValue="New project title" />
        <button type="submit">Update Project</button>
      </Form>
      {actionData ? (
        <p>
          Updated project {actionData.id}: {actionData.title}
        </p>
      ) : null}
    </div>
  );
}
