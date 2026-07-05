import { useNavigate } from "react-router";

function MyHeader() {
  return <header>Login</header>;
}

function MyFooter() {
  return <footer>React Router useNavigate example</footer>;
}

function MyLoginForm({ onSuccess }: { onSuccess: () => void }) {
  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
        onSuccess();
      }}
    >
      <button type="submit">Log in</button>
    </form>
  );
}

export function LoginPage() {
  const navigate = useNavigate();

  return (
    <>
      <MyHeader />
      <MyLoginForm
        onSuccess={() => {
          navigate("/dashboard");
        }}
      />
      <MyFooter />
    </>
  );
}

export function Dashboard() {
  return <h1>Dashboard</h1>;
}
