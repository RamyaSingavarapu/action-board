import React from "react";
import './App.css'
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Boards from "./pages/Boards";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={<Boards />} />
      </Routes>
    </Router>
  );
}

export default App
