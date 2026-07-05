import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import { Categories, Home, User } from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path=":lang?/categories" element={<Categories />} />
        <Route path="users/:userId/edit?" element={<User />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
