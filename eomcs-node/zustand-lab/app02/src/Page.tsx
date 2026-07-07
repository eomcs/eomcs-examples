import Controls from "./Controls";
import Sidebar from "./Sidebar";

type PageProps = {
  increasePopulation: () => void;
};

function Page({ increasePopulation }: PageProps) {
  return (
    <section className="page">
      <Sidebar />
      <Controls increasePopulation={increasePopulation} />
    </section>
  );
}

export default Page;
