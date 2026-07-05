import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import { Account, Concerts, Home, Messages, Tasks, Trending } from "./App";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="trending" element={<Trending />} />
        <Route path="concerts" element={<Concerts />} />
        <Route path="concerts/:city" element={<Concerts />} />
        <Route path="account" element={<Account />} />
        <Route path="messages" element={<Messages />} />
        <Route path="message" element={<Tasks />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
