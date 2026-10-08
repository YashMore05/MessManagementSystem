import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { ArrowRight, Eye, EyeOff, Mail } from "lucide-react";
import AuthShell from "../layouts/AuthShell";
import { useAuth } from "../auth/AuthContext";
import { roleHome } from "../auth/routes";
import { getApiErrorMessage } from "../utils/errors";

export default function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [form, setForm] = useState({ email: "admin@mess.com", password: "admin123" });
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  function update(field, value) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submit(event) {
    event.preventDefault();
    setLoading(true);
    try {
      const user = await login(form);
      toast.success("Logged in successfully");
      navigate(roleHome(user.role), { replace: true });
    } catch (error) {
      toast.error(getApiErrorMessage(error, "Login failed"));
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthShell
      title="Sign in"
      subtitle="Welcome back! Please enter your details."
      footer={
        <>
          New user? <Link to="/register">Create an account</Link>
        </>
      }
    >
      <form onSubmit={submit}>
        <div className="form-group-custom">
          <label htmlFor="email-input">Email Address</label>
          <div className="input-icon-container">
            <input
              id="email-input"
              type="email"
              value={form.email}
              onChange={(e) => update("email", e.target.value)}
              placeholder="Enter your email"
              required
            />
            <Mail className="input-icon" size={18} />
          </div>
        </div>

        <div className="form-group-custom">
          <div className="d-flex justify-content-between align-items-center mb-1">
            <label htmlFor="password-input" className="mb-0">Password</label>
            <a href="#forgot" className="text-decoration-none text-success fw-semibold" style={{ fontSize: "13px" }}>
              Forgot Password?
            </a>
          </div>
          <div className="input-icon-container">
            <input
              id="password-input"
              type={showPassword ? "text" : "password"}
              value={form.password}
              onChange={(e) => update("password", e.target.value)}
              placeholder="••••••••"
              required
            />
            <span
              className="input-icon"
              style={{ cursor: "pointer", pointerEvents: "auto" }}
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
            </span>
          </div>
        </div>

        <div className="d-flex justify-content-between align-items-center mb-4">
          <label className="checkbox-custom mb-0">
            <input type="checkbox" />
            <span>Remember for 30 days</span>
          </label>
        </div>

        <button className="btn btn-success w-100 d-flex align-items-center justify-content-center gap-2" disabled={loading}>
          {loading ? "Signing in..." : "Sign In"}
          <ArrowRight size={18} />
        </button>
      </form>
    </AuthShell>
  );
}
