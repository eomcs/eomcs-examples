import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import {
  EditProject,
  Project,
  ProjectsHome,
  ProjectsLayout,
} from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="projects">
          <Route index element={<ProjectsHome />} />
          <Route element={<ProjectsLayout />}>
            <Route path=":pid" element={<Project />} />
            <Route path=":pid/edit" element={<EditProject />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
