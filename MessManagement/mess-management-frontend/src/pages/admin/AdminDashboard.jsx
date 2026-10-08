import React from "react";
import { BarChart3, ClipboardCheck, ListOrdered, ShieldCheck, TicketCheck, Users } from "lucide-react";
import { ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Tooltip } from "chart.js";
import { Bar, Doughnut } from "react-chartjs-2";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { api } from "../../api/client";
import EmptyState from "../../components/EmptyState";
import { FormInput, FormSelect, FormTextarea } from "../../components/FormInput";
import StatCard from "../../components/StatCard";
import DashboardLayout from "../../layouts/DashboardLayout";
import { addDaysIso, monthStartIso, todayIso } from "../../utils/format";

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Tooltip, Legend);

const navItems = [
  { id: "analytics", label: "Analytics", icon: BarChart3 },
  { id: "providers", label: "Providers", icon: ShieldCheck },
  { id: "users", label: "Users", icon: Users },
  { id: "complaints", label: "Complaints", icon: TicketCheck },
  { id: "audit", label: "Hygiene Audit", icon: ClipboardCheck },
  { id: "leaderboard", label: "Leaderboard", icon: ListOrdered },
];

export default function AdminDashboard() {
  const [activeTab, setActiveTab] = useState("analytics");
  const [analytics, setAnalytics] = useState({});
  const [providers, setProviders] = useState([]);
  const [users, setUsers] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [leaderboard, setLeaderboard] = useState([]);
  const [messes, setMesses] = useState([]);

  async function loadAll() {
    try {
      const [analyticsRes, providersRes, usersRes, complaintsRes, leaderboardRes, messesRes] = await Promise.all([
        api.get("/api/admin/analytics"),
        api.get("/api/admin/providers/pending"),
        api.get("/api/admin/users"),
        api.get("/api/admin/complaints"),
        api.get("/api/admin/leaderboard"),
        api.get("/api/admin/messes"),
      ]);
      setAnalytics(analyticsRes.data);
      setProviders(providersRes.data);
      setUsers(usersRes.data);
      setComplaints(complaintsRes.data);
      setLeaderboard(leaderboardRes.data);
      setMesses(messesRes.data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load admin data");
    }
  }

  useEffect(() => {
    loadAll();
  }, []);

  return (
    <DashboardLayout
      title="Admin Dashboard"
      subtitle="Approve providers, manage users, complaints, audits, polls, and platform health."
      navItems={navItems}
      activeTab={activeTab}
      onTabChange={setActiveTab}
    >
      <div className="stat-row">
        <StatCard label="Students" value={analytics.totalStudents || 0} />
        <StatCard label="Providers" value={analytics.totalProviders || 0} tone="blue" />
        <StatCard label="Today's Bookings" value={analytics.todayBookings || 0} tone="warning" />
        <StatCard label="Today's Consumed" value={analytics.todayConsumed || 0} />
      </div>
      {activeTab === "analytics" && <AnalyticsPanel analytics={analytics} />}
      {activeTab === "providers" && <ProvidersPanel providers={providers} onChange={loadAll} />}
      {activeTab === "users" && <UsersPanel users={users} onChange={loadAll} />}
      {activeTab === "complaints" && <ComplaintsPanel complaints={complaints} onChange={loadAll} />}
      {activeTab === "audit" && <AuditPanel messes={messes} />}
      {activeTab === "leaderboard" && <LeaderboardPanel leaderboard={leaderboard} />}
    </DashboardLayout>
  );
}

function AnalyticsPanel({ analytics }) {
  const doughnut = {
    labels: ["Students", "Providers", "Active messes"],
    datasets: [{ data: [analytics.totalStudents || 0, analytics.totalProviders || 0, analytics.activeMesses || 0], backgroundColor: ["#0f766e", "#2563eb", "#b45309"] }],
  };
  const bar = {
    labels: ["Active subscriptions", "Open complaints", "Bookings", "Consumed"],
    datasets: [{ label: "Platform count", data: [analytics.activeSubscriptions || 0, analytics.openComplaints || 0, analytics.todayBookings || 0, analytics.todayConsumed || 0], backgroundColor: ["#0f766e", "#b42318", "#b45309", "#2563eb"] }],
  };
  return (
    <div className="content-grid">
      <section className="panel half"><div className="panel-title"><h2>User mix</h2></div><Doughnut data={doughnut} /></section>
      <section className="panel half"><div className="panel-title"><h2>Operations</h2></div><Bar data={bar} /></section>
    </div>
  );
}

function ProvidersPanel({ providers, onChange }) {
  async function update(profileId, status) {
    try {
      await api.patch(`/api/admin/providers/${profileId}/status`, null, { params: { status } });
      toast.success(`Provider ${status.toLowerCase()}`);
      onChange();
    } catch (error) {
      toast.error(error.response?.data?.message || "Provider update failed");
    }
  }
  return (
    <section className="panel">
      <div className="panel-title"><h2>Pending providers</h2></div>
      <div className="table-responsive">
        <table className="table"><thead><tr><th>Name</th><th>Email</th><th>Phone</th><th>Status</th><th></th></tr></thead>
          <tbody>{providers.map((profile) => <tr key={profile.id}><td>{profile.user?.fullName}</td><td>{profile.user?.email}</td><td>{profile.phone}</td><td>{profile.status}</td><td className="text-end"><button className="btn btn-sm btn-success me-2" onClick={() => update(profile.id, "ACTIVE")}>Approve</button><button className="btn btn-sm btn-outline-danger" onClick={() => update(profile.id, "SUSPENDED")}>Suspend</button></td></tr>)}</tbody>
        </table>
      </div>
      {!providers.length && <EmptyState title="No pending providers" detail="New provider applications will appear here." />}
    </section>
  );
}

function UsersPanel({ users, onChange }) {
  async function ban(user, banned) {
    try {
      await api.patch(`/api/admin/users/${user.id}/ban`, null, { params: { banned } });
      toast.success(banned ? "User banned" : "User unbanned");
      onChange();
    } catch (error) {
      toast.error(error.response?.data?.message || "User update failed");
    }
  }
  return (
    <section className="panel">
      <div className="panel-title"><h2>User management</h2></div>
      <div className="table-responsive">
        <table className="table"><thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th></th></tr></thead>
          <tbody>{users.map((user) => <tr key={user.id}><td>{user.fullName}</td><td>{user.email}</td><td>{user.role}</td><td>{user.banned ? "Banned" : "Active"}</td><td className="text-end"><button className={`btn btn-sm ${user.banned ? "btn-outline-success" : "btn-outline-danger"}`} onClick={() => ban(user, !user.banned)}>{user.banned ? "Unban" : "Ban"}</button></td></tr>)}</tbody>
        </table>
      </div>
    </section>
  );
}

function ComplaintsPanel({ complaints, onChange }) {
  async function update(complaintId, status) {
    try {
      await api.patch(`/api/admin/complaints/${complaintId}/status`, null, { params: { status } });
      toast.success("Complaint updated");
      onChange();
    } catch (error) {
      toast.error(error.response?.data?.message || "Complaint update failed");
    }
  }
  return (
    <section className="panel">
      <div className="panel-title"><h2>Complaint management</h2></div>
      <div className="table-responsive">
        <table className="table"><thead><tr><th>Title</th><th>Category</th><th>Mess</th><th>Status</th><th></th></tr></thead>
          <tbody>{complaints.map((ticket) => <tr key={ticket.id}><td>{ticket.title}</td><td>{ticket.category}</td><td>{ticket.mess?.name}</td><td>{ticket.status}</td><td className="text-end"><button className="btn btn-sm btn-outline-primary me-2" onClick={() => update(ticket.id, "ESCALATED")}>Escalate</button><button className="btn btn-sm btn-success" onClick={() => update(ticket.id, "RESOLVED")}>Resolve</button></td></tr>)}</tbody>
        </table>
      </div>
      {!complaints.length && <EmptyState title="No complaints" detail="Tickets from students will appear here." />}
    </section>
  );
}

function PollPanel() {
  const [form, setForm] = useState({ messId: "", title: "Sunday Special Poll", pollDate: todayIso(), sundayDate: addDaysIso(2), options: "Paneer Biryani, Veg Thali, Chole Bhature" });
  const [pollId, setPollId] = useState("");
  const [results, setResults] = useState(null);

  async function create(event) {
    event.preventDefault();
    try {
      const payload = { ...form, messId: Number(form.messId), options: form.options.split(",").map((item) => item.trim()).filter(Boolean) };
      const { data } = await api.post("/api/admin/polls", payload);
      setPollId(data.id);
      toast.success("Poll created");
    } catch (error) {
      toast.error(error.response?.data?.message || "Poll creation failed");
    }
  }

  async function loadResults() {
    try {
      const { data } = await api.get(`/api/admin/polls/${pollId}/results`);
      setResults(data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load results");
    }
  }

  async function publish() {
    try {
      await api.patch(`/api/admin/polls/${pollId}/publish`);
      toast.success("Winning dish published");
    } catch (error) {
      toast.error(error.response?.data?.message || "Publish failed");
    }
  }

  return (
    <div className="content-grid">
      <section className="panel half">
        <div className="panel-title"><h2>Create poll</h2></div>
        <form onSubmit={create}>
          <FormInput label="Mess ID" value={form.messId} onChange={(e) => setForm((f) => ({ ...f, messId: e.target.value }))} required />
          <FormInput label="Title" value={form.title} onChange={(e) => setForm((f) => ({ ...f, title: e.target.value }))} />
          <FormInput label="Poll date" type="date" value={form.pollDate} onChange={(e) => setForm((f) => ({ ...f, pollDate: e.target.value }))} />
          <FormInput label="Sunday date" type="date" value={form.sundayDate} onChange={(e) => setForm((f) => ({ ...f, sundayDate: e.target.value }))} />
          <FormTextarea label="Options" value={form.options} onChange={(e) => setForm((f) => ({ ...f, options: e.target.value }))} />
          <button className="btn btn-success">Create poll</button>
        </form>
      </section>
      <section className="panel half">
        <div className="panel-title"><h2>Poll results</h2></div>
        <FormInput label="Poll ID" value={pollId} onChange={(e) => setPollId(e.target.value)} />
        <button className="btn btn-outline-success me-2" onClick={loadResults}>View results</button>
        <button className="btn btn-success" onClick={publish}>Publish winner</button>
        {results && <pre className="mt-3 bg-light p-3 rounded">{JSON.stringify(results, null, 2)}</pre>}
      </section>
    </div>
  );
}

function AuditPanel({ messes }) {
  const [form, setForm] = useState({ messId: "", auditMonth: monthStartIso(), kitchenScore: 80, staffScore: 80, storageScore: 80, redFlags: "" });
  async function submit(event) {
    event.preventDefault();
    try {
      await api.post("/api/admin/hygiene-audits", { ...form, messId: Number(form.messId), kitchenScore: Number(form.kitchenScore), staffScore: Number(form.staffScore), storageScore: Number(form.storageScore) });
      toast.success("Audit saved");
    } catch (error) {
      toast.error(error.response?.data?.message || "Audit failed");
    }
  }
  return (
    <section className="panel">
      <div className="panel-title"><h2>Monthly hygiene audit</h2></div>
      <form onSubmit={submit} className="row g-3">
        <div className="col-md-3">
          <FormSelect label="Mess" value={form.messId} onChange={(e) => setForm((f) => ({ ...f, messId: e.target.value }))} required>
            <option value="">Select mess</option>
            {messes.map((mess) => <option key={mess.id} value={mess.id}>{mess.name} · ID {mess.id}</option>)}
          </FormSelect>
        </div>
        <div className="col-md-3"><FormInput label="Audit month" type="date" value={form.auditMonth} onChange={(e) => setForm((f) => ({ ...f, auditMonth: e.target.value }))} /></div>
        <div className="col-md-2"><FormInput label="Kitchen" type="number" value={form.kitchenScore} onChange={(e) => setForm((f) => ({ ...f, kitchenScore: e.target.value }))} /></div>
        <div className="col-md-2"><FormInput label="Staff" type="number" value={form.staffScore} onChange={(e) => setForm((f) => ({ ...f, staffScore: e.target.value }))} /></div>
        <div className="col-md-2"><FormInput label="Storage" type="number" value={form.storageScore} onChange={(e) => setForm((f) => ({ ...f, storageScore: e.target.value }))} /></div>
        <div className="col-12"><FormTextarea label="Red flags" rows="3" value={form.redFlags} onChange={(e) => setForm((f) => ({ ...f, redFlags: e.target.value }))} /></div>
        <div className="col-12"><button className="btn btn-success">Save audit</button></div>
      </form>
    </section>
  );
}

function LeaderboardPanel({ leaderboard }) {
  return (
    <section className="panel">
      <div className="panel-title"><h2>Mess leaderboard</h2></div>
      <div className="table-responsive">
        <table className="table"><thead><tr><th>Rank</th><th>Mess</th><th>Rating</th><th>Hygiene</th><th>Score</th></tr></thead>
          <tbody>{leaderboard.map((row, index) => <tr key={row.messId}><td>{index + 1}</td><td>{row.name}</td><td>{Number(row.rating).toFixed(1)}</td><td>{Number(row.hygieneScore).toFixed(0)}</td><td>{Number(row.compositeScore).toFixed(1)}</td></tr>)}</tbody>
        </table>
      </div>
      {!leaderboard.length && <EmptyState title="No messes ranked" detail="Create messes, reviews, and audits to populate this list." />}
    </section>
  );
}
