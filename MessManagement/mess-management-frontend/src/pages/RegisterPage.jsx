import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { Eye, EyeOff, FileText, Lock, Mail, MapPin, Phone, Shield, User } from "lucide-react";
import AuthShell from "../layouts/AuthShell";
import { useAuth } from "../auth/AuthContext";
import { roleHome } from "../auth/routes";
import { getApiErrorMessage } from "../utils/errors";

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register } = useAuth();
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    collegeId: "",
    password: "",
    role: "ROLE_STUDENT",
    phone: "",
    area: "",
  });
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  function update(field, value) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submit(event) {
    event.preventDefault();
    setLoading(true);
    try {
      const payload = { ...form };
      if (payload.role === "ROLE_PROVIDER") delete payload.collegeId;
      const user = await register(payload);
      toast.success("Account created successfully");
      navigate(roleHome(user.role), { replace: true });
    } catch (error) {
      toast.error(getApiErrorMessage(error, "Registration failed"));
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthShell
      title="Create account"
      subtitle="Students and mess providers can register here."
      footer={
        <>
          Already registered? <Link to="/login">Sign in</Link>
        </>
      }
    >
      <form onSubmit={submit}>
        <div className="form-group-custom">
          <label htmlFor="role-select">Register As</label>
          <div className="input-icon-container">
            <select
              id="role-select"
              value={form.role}
              onChange={(e) => update("role", e.target.value)}
            >
              <option value="ROLE_STUDENT">Student</option>
              <option value="ROLE_PROVIDER">Mess provider</option>
            </select>
            <Shield className="input-icon" size={18} />
          </div>
        </div>

        <div className="form-group-custom">
          <label htmlFor="fullname-input">Full Name</label>
          <div className="input-icon-container">
            <input
              id="fullname-input"
              type="text"
              value={form.fullName}
              onChange={(e) => update("fullName", e.target.value)}
              placeholder="John Doe"
              required
            />
            <User className="input-icon" size={18} />
          </div>
        </div>

        <div className="form-group-custom">
          <label htmlFor="email-input">Email Address</label>
          <div className="input-icon-container">
            <input
              id="email-input"
              type="email"
              value={form.email}
              onChange={(e) => update("email", e.target.value)}
              placeholder="john@example.com"
              required
            />
            <Mail className="input-icon" size={18} />
          </div>
        </div>

        {form.role === "ROLE_STUDENT" && (
          <div className="form-group-custom">
            <label htmlFor="collegeid-input">College ID</label>
            <div className="input-icon-container">
              <input
                id="collegeid-input"
                type="text"
                value={form.collegeId}
                onChange={(e) => update("collegeId", e.target.value)}
                placeholder="COL-12345"
                required
              />
              <FileText className="input-icon" size={18} />
            </div>
          </div>
        )}

        <div className="form-group-custom">
          <label htmlFor="phone-input">Phone Number</label>
          <div className="input-icon-container">
            <input
              id="phone-input"
              type="tel"
              value={form.phone}
              onChange={(e) => update("phone", e.target.value)}
              placeholder="9876543210"
            />
            <Phone className="input-icon" size={18} />
          </div>
        </div>

        <div className="form-group-custom">
          <label htmlFor="area-input">Area/Location</label>
          <div className="input-icon-container">
            <input
              id="area-input"
              type="text"
              value={form.area}
              onChange={(e) => update("area", e.target.value)}
              placeholder="e.g. FC Road"
            />
            <MapPin className="input-icon" size={18} />
          </div>
        </div>

        <div className="form-group-custom">
          <label htmlFor="password-input">Password</label>
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

        <button className="btn btn-success w-100 mt-3" disabled={loading}>
          {loading ? "Creating account..." : "Create Account"}
        </button>
      </form>
    </AuthShell>
  );
}
