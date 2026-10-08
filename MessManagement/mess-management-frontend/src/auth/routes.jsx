import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export function roleHome(role) {
  if (role === "ROLE_ADMIN") return "/admin";
  if (role === "ROLE_PROVIDER") return "/provider";
  return "/student";
}

export function ProtectedRoute({ role, children }) {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (role && user?.role !== role) {
    return <Navigate to={roleHome(user?.role)} replace />;
  }

  return children;
}

export function GuestRoute({ children }) {
  const { user, isAuthenticated } = useAuth();
  if (isAuthenticated) {
    return <Navigate to={roleHome(user?.role)} replace />;
  }
  return children;
}
