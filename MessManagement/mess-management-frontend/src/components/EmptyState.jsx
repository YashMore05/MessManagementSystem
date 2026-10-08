import React from "react";
export default function EmptyState({ title = "No data yet", detail = "Create or search for records to see them here." }) {
  return (
    <div className="empty-state">
      <strong>{title}</strong>
      <span>{detail}</span>
    </div>
  );
}
