import { useCallback, useEffect } from "react";
import { useActionData, useSubmit } from "react-router";

type EndQuizActionData = {
  quizTimedOut: boolean;
  submittedAt: string;
};

function useFakeTimer(ms: number, callback: () => void) {
  useEffect(() => {
    const timeoutId = window.setTimeout(callback, ms);

    return () => window.clearTimeout(timeoutId);
  }, [callback, ms]);
}

function useQuizTimer() {
  const submit = useSubmit();

  const cb = useCallback(() => {
    submit(
      { quizTimedOut: "true" },
      { action: "/end-quiz", method: "post" }
    );
  }, [submit]);

  const tenMinutes = 5000; //10 * 60 * 1000;
  useFakeTimer(tenMinutes, cb);
}

export function Quiz() {
  useQuizTimer();

  return (
    <div>
      <h1>Quiz</h1>
      <p>The quiz will be submitted automatically when the timer ends.</p>
    </div>
  );
}

export function EndQuiz() {
  const actionData = useActionData() as EndQuizActionData | undefined;

  return (
    <div>
      <h1>End Quiz</h1>
      {actionData ? (
        <>
          <p>Quiz timed out: {String(actionData.quizTimedOut)}</p>
          <p>Submitted at: {actionData.submittedAt}</p>
        </>
      ) : (
        <p>No action data yet.</p>
      )}
    </div>
  );
}
