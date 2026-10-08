import React from "react";

export default function StatCard({ label, value, tone = "primary", icon: Icon }) {
  return (
    <div className={`stat-card stat-${tone}`}>
      {Icon && (
        <div className="mb-2" style={{ display: "flex", color: "inherit" }}>
          <Icon size={22} />
        </div>
      )}
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}
