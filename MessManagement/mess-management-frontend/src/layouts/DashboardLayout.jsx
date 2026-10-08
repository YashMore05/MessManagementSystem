import React from "react";
import { LogOut, UtensilsCrossed } from "lucide-react";
import { useAuth } from "../auth/AuthContext";

export default function DashboardLayout({ title, subtitle, navItems, activeTab, onTabChange, children }) {
  const { user, logout } = useAuth();

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <div className="sidebar-brand-logo">
            <UtensilsCrossed size={20} />
          </div>
          <strong>MessHub</strong>
        </div>
        <nav className="sidebar-nav">
          {navItems.map((item) => {
            const Icon = item.icon;
            return (
              <button
                key={item.id}
                type="button"
                className={activeTab === item.id ? "active" : ""}
                onClick={() => onTabChange(item.id)}
                title={item.label}
              >
                <Icon size={18} />
                <span>{item.label}</span>
              </button>
            );
          })}
        </nav>
        <button type="button" className="logout-button" onClick={logout} title="Logout">
          <LogOut size={18} />
          <span>Logout</span>
        </button>
      </aside>
      <main className="workspace">
        <header className="workspace-header">
          <div>
            <h1>{title}</h1>
            <p>{subtitle}</p>
          </div>
          <div className="workspace-top-widgets">
            <div className="user-chip">
              <div className="user-chip-avatar">
                {user?.fullName?.slice(0, 1) || "U"}
              </div>
              <div className="user-chip-info">
                <strong>{user?.fullName}</strong>
                <small>{user?.role?.replace("ROLE_", "")}</small>
              </div>
            </div>
          </div>
        </header>
        {children}
      </main>
    </div>
  );
}
