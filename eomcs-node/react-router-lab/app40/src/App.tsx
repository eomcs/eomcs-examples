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
      {actionData ? (
        <p>
          Updated project {actionData.id}: {actionData.title}
        </p>
      ) : (
        <p>No action data yet.</p>
      )}
    </div>
  );
}

export function SomeComponent() {
  return (
    <div>
      <h1>Calling actions with a Form</h1>
      <Form action="/projects/123" method="post">
        <input type="text" name="title" defaultValue="New project title" />
        <button type="submit">Submit</button>
      </Form>
    </div>
  );
}
