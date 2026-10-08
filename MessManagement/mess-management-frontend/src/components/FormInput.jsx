import React from "react";
export function FormInput({ label, ...props }) {
  return (
    <label className="form-label w-100">
      <span>{label}</span>
      <input className="form-control" {...props} />
    </label>
  );
}

export function FormSelect({ label, children, ...props }) {
  return (
    <label className="form-label w-100">
      <span>{label}</span>
      <select className="form-select" {...props}>
        {children}
      </select>
    </label>
  );
}

export function FormTextarea({ label, ...props }) {
  return (
    <label className="form-label w-100">
      <span>{label}</span>
      <textarea className="form-control" {...props} />
    </label>
  );
}
