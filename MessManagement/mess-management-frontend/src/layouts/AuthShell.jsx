import React from "react";
import { Link } from "react-router-dom";
import { UtensilsCrossed } from "lucide-react";

export default function AuthShell({ title, subtitle, footer, children }) {
  return (
    <main className="auth-page">
      <section className="auth-panel">
        <Link to="/login" className="d-block text-decoration-none">
          <div className="auth-brand-logo">
            <UtensilsCrossed size={28} />
          </div>
          <div className="auth-brand-name">MessHub</div>
        </Link>
        <div className="auth-subtitle">
          Open your role dashboard and continue mess operations.
        </div>
        
        <div>
          <h1 className="mb-1">{title}</h1>
          <p className="text-muted mb-4">{subtitle}</p>
        </div>

        {children}

        <div className="auth-footer">{footer}</div>
      </section>

      <div className="auth-bottom-links">
        <a href="#privacy">Privacy Policy</a>
        <span>•</span>
        <a href="#terms">Terms of Service</a>
        <span>•</span>
        <a href="#support">Support</a>
      </div>
    </main>
  );
}
