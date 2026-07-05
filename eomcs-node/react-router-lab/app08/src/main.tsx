import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import { Home, Product, Team } from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="teams/:teamId" element={<Team />} />
        <Route path="/c/:categoryId/p/:productId" element={<Product />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
