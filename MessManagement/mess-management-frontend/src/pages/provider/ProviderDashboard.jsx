import React from "react";
import { BarChart3, CalendarOff, ClipboardList, Megaphone, QrCode, ScanLine, Soup, Star, Store, Vote } from "lucide-react";
import { Html5QrcodeScanner } from "html5-qrcode";
import { useEffect, useRef, useState } from "react";
import { toast } from "react-toastify";
import { api, getAssetUrl } from "../../api/client";
import EmptyState from "../../components/EmptyState";
import { FormInput, FormSelect, FormTextarea } from "../../components/FormInput";
import StatCard from "../../components/StatCard";
import DashboardLayout from "../../layouts/DashboardLayout";
import { addDaysIso, currency, todayIso } from "../../utils/format";

const navItems = [
  { id: "profile", label: "Mess Profile", icon: Store },
  { id: "menu", label: "Menu", icon: Soup },
  { id: "plans", label: "Plans", icon: ClipboardList },
  { id: "subscriptions", label: "Subscriptions", icon: BarChart3 },
  { id: "leaves", label: "Leaves", icon: CalendarOff },
  { id: "polls", label: "Sunday Polls", icon: Vote },
  { id: "scanner", label: "QR Scanner", icon: ScanLine },
  { id: "reviews", label: "Reviews", icon: Star },
  { id: "notices", label: "Notices", icon: Megaphone },
];

export default function ProviderDashboard() {
  const [activeTab, setActiveTab] = useState("profile");
  const [messes, setMesses] = useState([]);
  const [selectedMess, setSelectedMess] = useState(null);
  const [subscriptions, setSubscriptions] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [leaves, setLeaves] = useState([]);
  const [polls, setPolls] = useState([]);
  const [mealBookings, setMealBookings] = useState([]);
  const [liveCounts, setLiveCounts] = useState(null);
  const [dashboard, setDashboard] = useState(null);
  const [plans, setPlans] = useState([]);
  const [notices, setNotices] = useState([]);

  async function loadMesses() {
    try {
      const { data } = await api.get("/api/provider/messes");
      setMesses(data);
      if (data.length) {
        if (!selectedMess) {
          setSelectedMess(data[0]);
        } else {
          const updated = data.find((m) => m.id === selectedMess.id);
          if (updated) setSelectedMess(updated);
        }
      }
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load mess profile");
    }
  }

  async function loadOperationalData(mess = selectedMess) {
    if (!mess) return;
    try {
      const [subs, revs, dash] = await Promise.all([
        api.get(`/api/provider/messes/${mess.id}/subscriptions`),
        api.get(`/api/provider/messes/${mess.id}/reviews`),
        api.get(`/api/provider/messes/${mess.id}/dashboard`),
      ]);
      setSubscriptions(subs.data);
      setReviews(revs.data);
      setDashboard(dash.data);
      const [leaveRes, pollRes, bookingsRes, countsRes, plansRes, noticesRes] = await Promise.all([
        api.get(`/api/provider/messes/${mess.id}/leaves`),
        api.get(`/api/provider/messes/${mess.id}/polls`),
        api.get(`/api/provider/messes/${mess.id}/meal-bookings`),
        api.get(`/api/provider/messes/${mess.id}/live-counts`),
        api.get(`/api/provider/messes/${mess.id}/plans`),
        api.get(`/api/provider/messes/${mess.id}/notices`),
      ]);
      setLeaves(leaveRes.data);
      setPolls(pollRes.data);
      setMealBookings(bookingsRes.data);
      setLiveCounts(countsRes.data);
      setPlans(plansRes.data);
      setNotices(noticesRes.data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load provider data");
    }
  }

  useEffect(() => {
    loadMesses();
  }, []);

  useEffect(() => {
    if (selectedMess) loadOperationalData(selectedMess);
  }, [selectedMess?.id]);

  return (
    <DashboardLayout
      title="Provider Dashboard"
      subtitle="Manage mess profile, menu, plans, attendance, notices, and revenue."
      navItems={navItems}
      activeTab={activeTab}
      onTabChange={setActiveTab}
    >
      <div className="stat-row">
        <StatCard label="Messes" value={messes.length} />
        <StatCard label="Active Subscriptions" value={dashboard?.activeSubscriptions || 0} tone="blue" />
        <StatCard label="Pending Payments" value={dashboard?.paymentPendingCount || 0} tone="warning" />
        <StatCard label="Today's Bookings" value={dashboard?.todayBookings || 0} tone="warning" />
      </div>
      <MessSelector messes={messes} selectedMess={selectedMess} onSelect={setSelectedMess} />
      {activeTab === "profile" && <ProfilePanel liveCounts={liveCounts} selectedMess={selectedMess} onCreated={loadMesses} />}
      {activeTab === "menu" && <MenuPanel selectedMess={selectedMess} />}
      {activeTab === "plans" && <PlanPanel selectedMess={selectedMess} plans={plans} onCreated={() => loadOperationalData()} />}
      {activeTab === "subscriptions" && <SubscriptionsPanel subscriptions={subscriptions} />}
      {activeTab === "leaves" && <LeavesPanel leaves={leaves} />}
      {activeTab === "polls" && <PollPanel selectedMess={selectedMess} polls={polls} onChange={() => loadOperationalData()} />}
      {activeTab === "scanner" && <ScannerPanel bookings={mealBookings} onScanSuccess={() => loadOperationalData()} />}
      {activeTab === "reviews" && <ReviewsPanel reviews={reviews} />}
      {activeTab === "notices" && <NoticePanel selectedMess={selectedMess} notices={notices} onCreated={() => loadOperationalData()} />}
    </DashboardLayout>
  );
}

function MessSelector({ messes, selectedMess, onSelect }) {
  if (!messes.length) return null;
  return (
    <div className="panel mb-3">
      <div className="row align-items-end g-3">
        <div className="col-md-8">
          <FormSelect label="Working mess" value={selectedMess?.id || ""} onChange={(e) => onSelect(messes.find((mess) => String(mess.id) === e.target.value))}>
            {messes.map((mess) => <option value={mess.id} key={mess.id}>{mess.name}</option>)}
          </FormSelect>
        </div>
        <div className="col-md-4"><span className="badge-soft">{selectedMess?.messType}</span></div>
      </div>
    </div>
  );
}

function LiveCountsPanel({ liveCounts }) {
  if (!liveCounts) return null;
  const rows = ["BREAKFAST", "LUNCH", "DINNER"];
  return (
    <section className="panel mb-3">
      <div className="panel-title"><h2>Live expected vs consumed count</h2><span className="badge-soft">{liveCounts.date}</span></div>
      <div className="row g-3">
        {rows.map((meal) => (
          <div className="col-md-4" key={meal}>
            <div className="border rounded p-3">
              <strong>{meal}</strong>
              <div className="d-flex justify-content-between mt-2"><span>Booked</span><b>{liveCounts[meal]?.booked || 0}</b></div>
              <div className="d-flex justify-content-between"><span>Paid</span><b>{liveCounts[meal]?.paid || 0}</b></div>
              <div className="d-flex justify-content-between"><span>Consumed</span><b>{liveCounts[meal]?.consumed || 0}</b></div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

function ProfilePanel({ liveCounts, selectedMess, onCreated }) {
  const [form, setForm] = useState({ name: "", address: "", area: "", latitude: "", longitude: "", capacity: 100, messType: "VEG", photoPaths: [] });
  const [uploading, setUploading] = useState(false);

  useEffect(() => {
    if (selectedMess) {
      setForm({
        id: selectedMess.id,
        name: selectedMess.name || "",
        address: selectedMess.address || "",
        area: selectedMess.area || "",
        latitude: selectedMess.latitude !== null ? String(selectedMess.latitude) : "",
        longitude: selectedMess.longitude !== null ? String(selectedMess.longitude) : "",
        capacity: selectedMess.capacity || 100,
        messType: selectedMess.messType || "VEG",
        photoPaths: selectedMess.photoPaths || [],
      });
    } else {
      setForm({ name: "", address: "", area: "", latitude: "", longitude: "", capacity: 100, messType: "VEG", photoPaths: [] });
    }
  }, [selectedMess]);

  async function handlePhotoUpload(event) {
    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    setUploading(true);
    try {
      const { data } = await api.post("/api/uploads/mess_photos", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setForm((f) => ({ ...f, photoPaths: [data.url] }));
      toast.success("Photo uploaded successfully!");
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to upload photo");
    } finally {
      setUploading(false);
    }
  }

  async function submit(event) {
    event.preventDefault();
    try {
      const payload = {
        ...form,
        latitude: form.latitude ? Number(form.latitude) : null,
        longitude: form.longitude ? Number(form.longitude) : null,
        capacity: Number(form.capacity),
      };
      await api.post("/api/provider/messes", payload);
      toast.success("Mess profile saved");
      if (!form.id) {
        setForm({ name: "", address: "", area: "", latitude: "", longitude: "", capacity: 100, messType: "VEG", photoPaths: [] });
      }
      onCreated();
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not save mess");
    }
  }

  return (
    <>
    <LiveCountsPanel liveCounts={liveCounts} />
    <section className="panel">
      <div className="panel-title">
        <h2>{form.id ? "Edit mess profile" : "Create mess profile"}</h2>
      </div>
      <form onSubmit={submit} className="row g-3">
        <div className="col-md-6"><FormInput label="Mess name" value={form.name} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} required /></div>
        <div className="col-md-6"><FormInput label="Area" value={form.area} onChange={(e) => setForm((f) => ({ ...f, area: e.target.value }))} /></div>
        <div className="col-12"><FormTextarea label="Address" rows="2" value={form.address} onChange={(e) => setForm((f) => ({ ...f, address: e.target.value }))} required /></div>
        <div className="col-md-3"><FormInput label="Latitude" value={form.latitude} onChange={(e) => setForm((f) => ({ ...f, latitude: e.target.value }))} /></div>
        <div className="col-md-3"><FormInput label="Longitude" value={form.longitude} onChange={(e) => setForm((f) => ({ ...f, longitude: e.target.value }))} /></div>
        <div className="col-md-3"><FormInput label="Capacity" type="number" value={form.capacity} onChange={(e) => setForm((f) => ({ ...f, capacity: e.target.value }))} /></div>
        <div className="col-md-3"><FormSelect label="Type" value={form.messType} onChange={(e) => setForm((f) => ({ ...f, messType: e.target.value }))}><option>VEG</option><option>NON_VEG</option><option>BOTH</option></FormSelect></div>
        
        <div className="col-12">
          <label className="form-label" style={{ fontWeight: 500 }}>Mess Photo</label>
          <div className="image-upload-wrapper">
            {uploading ? (
              <p className="mb-0 text-muted">Uploading photo...</p>
            ) : (
              <>
                <p className="mb-0 text-muted">Drag & drop or click to upload mess photo</p>
                <input type="file" accept="image/*" onChange={handlePhotoUpload} />
              </>
            )}
          </div>
          {form.photoPaths && form.photoPaths.length > 0 && (
            <div className="image-upload-preview">
              <img src={getAssetUrl(form.photoPaths[0])} alt="Mess preview" />
            </div>
          )}
        </div>

        <div className="col-12 mt-4"><button className="btn btn-success">Save profile</button></div>
      </form>
    </section>
    </>
  );
}

function MenuPanel({ selectedMess }) {
  const [form, setForm] = useState({ mealType: "LUNCH", menuDate: todayIso(), items: "", price: 80 });
  async function submit(event) {
    event.preventDefault();
    if (!selectedMess) return toast.info("Create or select a mess first");
    try {
      await api.post("/api/provider/menus", { ...form, price: Number(form.price), messId: selectedMess.id });
      toast.success("Menu saved");
      setForm((f) => ({ ...f, items: "" }));
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not save menu");
    }
  }
  return (
    <section className="panel">
      <div className="panel-title"><h2>Set daily menu</h2></div>
      <form onSubmit={submit} className="row g-3">
        <div className="col-md-4"><FormSelect label="Meal" value={form.mealType} onChange={(e) => setForm((f) => ({ ...f, mealType: e.target.value }))}><option>BREAKFAST</option><option>LUNCH</option><option>DINNER</option></FormSelect></div>
        <div className="col-md-3"><FormInput label="Date" type="date" value={form.menuDate} onChange={(e) => setForm((f) => ({ ...f, menuDate: e.target.value }))} /></div>
        <div className="col-md-2"><FormInput label="Price" type="number" value={form.price} onChange={(e) => setForm((f) => ({ ...f, price: e.target.value }))} required /></div>
        <div className="col-md-3"><FormInput label="Items" value={form.items} onChange={(e) => setForm((f) => ({ ...f, items: e.target.value }))} required /></div>
        <div className="col-12"><button className="btn btn-success">Save menu</button></div>
      </form>
    </section>
  );
}

function PlanPanel({ selectedMess, plans, onCreated }) {
  const [form, setForm] = useState({ name: "Monthly Standard", price: 3500, mealsIncluded: 60, seatLimit: 50 });
  async function submit(event) {
    event.preventDefault();
    if (!selectedMess) return toast.info("Create or select a mess first");
    try {
      await api.post("/api/provider/plans", { ...form, messId: selectedMess.id, price: Number(form.price), mealsIncluded: Number(form.mealsIncluded), seatLimit: Number(form.seatLimit) });
      toast.success("Plan created");
      onCreated();
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not create plan");
    }
  }
  return (
    <>
    <section className="panel mb-3">
      <div className="panel-title"><h2>Create monthly plan</h2></div>
      <form onSubmit={submit} className="row g-3">
        <div className="col-md-3"><FormInput label="Plan name" value={form.name} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} /></div>
        <div className="col-md-3"><FormInput label="Price" type="number" value={form.price} onChange={(e) => setForm((f) => ({ ...f, price: e.target.value }))} /></div>
        <div className="col-md-3"><FormInput label="Meals included" type="number" value={form.mealsIncluded} onChange={(e) => setForm((f) => ({ ...f, mealsIncluded: e.target.value }))} /></div>
        <div className="col-md-3"><FormInput label="Seat limit" type="number" value={form.seatLimit} onChange={(e) => setForm((f) => ({ ...f, seatLimit: e.target.value }))} /></div>
        <div className="col-12"><button className="btn btn-success">Create plan</button></div>
      </form>
    </section>

    <section className="panel">
      <div className="panel-title"><h2>Existing Plans</h2></div>
      <div className="table-responsive">
        <table className="table">
          <thead>
            <tr>
              <th>Plan Name</th>
              <th>Price</th>
              <th>Meals Included</th>
              <th>Seat Limit</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {plans.map((p) => (
              <tr key={p.id}>
                <td>{p.name}</td>
                <td className="fw-bold">{currency(p.price)}</td>
                <td>{p.mealsIncluded}</td>
                <td>{p.seatLimit}</td>
                <td>
                  <span className={`badge-soft ${p.active ? "" : "badge-soft-danger"}`}>
                    {p.active ? "Active" : "Inactive"}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {!plans.length && <EmptyState title="No plans created" detail="Plans you create for your selected mess will show up here." />}
    </section>
    </>
  );
}

function SubscriptionsPanel({ subscriptions }) {
  return (
    <section className="panel">
      <div className="panel-title"><h2>Subscriptions</h2></div>
      <div className="table-responsive">
        <table className="table"><thead><tr><th>Student</th><th>Plan</th><th>Join</th><th>Amount</th><th>Status</th><th>Payment</th></tr></thead>
          <tbody>{subscriptions.map((sub) => <tr key={sub.id}><td>{sub.student?.fullName}</td><td>{sub.plan?.name}</td><td>{sub.startDate}</td><td>{currency(sub.amountCharged)}</td><td>{sub.status}</td><td>{sub.paymentStatus}</td></tr>)}</tbody>
        </table>
      </div>
      {!subscriptions.length && <EmptyState title="No subscriptions" detail="Subscribed students will appear here." />}
    </section>
  );
}

function LeavesPanel({ leaves }) {
  return (
    <section className="panel">
      <div className="panel-title"><h2>Student leave / absence</h2></div>
      <div className="table-responsive">
        <table className="table">
          <thead><tr><th>Student</th><th>From</th><th>To</th><th>Reason</th><th>Status</th></tr></thead>
          <tbody>
            {leaves.map((leave) => (
              <tr key={leave.id}>
                <td>{leave.student?.fullName}</td>
                <td>{leave.fromDate}</td>
                <td>{leave.toDate}</td>
                <td>{leave.reason || "-"}</td>
                <td><span className="badge-soft">{leave.status}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {!leaves.length && <EmptyState title="No absence marked" detail="Student leave requests for this mess will appear here." />}
    </section>
  );
}

function PollPanel({ selectedMess, polls, onChange }) {
  const [form, setForm] = useState({
    title: "Sunday Special Poll",
    pollDate: todayIso(),
    sundayDate: addDaysIso(2),
    options: "Paneer Biryani, Veg Thali, Chole Bhature",
  });
  const [resultPollId, setResultPollId] = useState("");
  const [results, setResults] = useState(null);

  async function create(event) {
    event.preventDefault();
    if (!selectedMess) return toast.info("Create or select a mess first");
    try {
      await api.post("/api/provider/polls", {
        ...form,
        messId: selectedMess.id,
        options: form.options.split(",").map((item) => item.trim()).filter(Boolean),
      });
      toast.success("Poll created");
      onChange();
    } catch (error) {
      toast.error(error.response?.data?.message || "Poll creation failed");
    }
  }

  async function loadResults(pollId = resultPollId) {
    try {
      const { data } = await api.get(`/api/provider/polls/${pollId}/results`);
      setResults(data);
      setResultPollId(String(pollId));
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load poll results");
    }
  }

  async function publish(pollId) {
    try {
      await api.patch(`/api/provider/polls/${pollId}/publish`);
      toast.success("Winning dish published");
      onChange();
    } catch (error) {
      toast.error(error.response?.data?.message || "Publish failed");
    }
  }

  return (
    <div className="content-grid">
      <section className="panel half">
        <div className="panel-title"><h2>Create Sunday special poll</h2></div>
        <form onSubmit={create}>
          <FormInput label="Title" value={form.title} onChange={(e) => setForm((f) => ({ ...f, title: e.target.value }))} />
          <FormInput label="Poll date" type="date" value={form.pollDate} onChange={(e) => setForm((f) => ({ ...f, pollDate: e.target.value }))} />
          <FormInput label="Sunday date" type="date" value={form.sundayDate} onChange={(e) => setForm((f) => ({ ...f, sundayDate: e.target.value }))} />
          <FormTextarea label="Three options, comma separated" rows="3" value={form.options} onChange={(e) => setForm((f) => ({ ...f, options: e.target.value }))} />
          <button className="btn btn-success">Create poll</button>
        </form>
      </section>
      <section className="panel half">
        <div className="panel-title"><h2>Today's polls</h2></div>
        {polls.length ? polls.map((poll) => (
          <div className="border rounded p-3 mb-3" key={poll.id}>
            <strong>{poll.title}</strong>
            <p className="text-muted mb-2">Poll ID {poll.id} · Sunday {poll.sundayDate}</p>
            <div className="d-flex gap-2 flex-wrap">
              <button className="btn btn-sm btn-outline-success" onClick={() => loadResults(poll.id)}>Results</button>
              <button className="btn btn-sm btn-success" onClick={() => publish(poll.id)}>Publish winner</button>
            </div>
          </div>
        )) : <EmptyState title="No poll today" detail="Create a poll for your selected mess." />}
        {results && <pre className="mt-3 bg-light p-3 rounded">{JSON.stringify(results, null, 2)}</pre>}
      </section>
    </div>
  );
}

function ScannerPanel({ bookings, onScanSuccess }) {
  const scannerRef = useRef(null);
  const containerId = "qr-reader";
  const [manualToken, setManualToken] = useState("");

  async function scanToken(token) {
    try {
      await api.post("/api/provider/qr/scan", { token });
      toast.success("Attendance marked");
      setManualToken("");
      onScanSuccess();
    } catch (error) {
      toast.error(error.response?.data?.message || "Scan failed");
    }
  }

  useEffect(() => {
    const scanner = new Html5QrcodeScanner(containerId, { fps: 8, qrbox: 220 }, false);
    scannerRef.current = scanner;
    scanner.render((decodedText) => scanToken(decodedText));
    return () => scannerRef.current?.clear().catch(() => {});
  }, []);

  return (
    <section className="panel">
      <div className="panel-title"><h2>Scan student QR</h2><QrCode size={18} /></div>
      <div id={containerId} className="scanner-box" />
      <div className="mt-3">
        <FormTextarea label="Manual QR token for same-browser testing" rows="3" value={manualToken} onChange={(e) => setManualToken(e.target.value)} />
        <button className="btn btn-success" onClick={() => scanToken(manualToken)} disabled={!manualToken.trim()}>Mark attendance</button>
      </div>
      <div className="mt-4">
        <div className="panel-title"><h2>Today's meal bookings</h2></div>
        <div className="table-responsive">
          <table className="table">
            <thead><tr><th>Student</th><th>Meal</th><th>Status</th><th>Amount</th></tr></thead>
            <tbody>
              {bookings.map((booking) => (
                <tr key={booking.id}>
                  <td>{booking.student?.fullName}</td>
                  <td>{booking.mealType}</td>
                  <td><span className="badge-soft">{booking.status}</span></td>
                  <td>{currency(booking.amount)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {!bookings.length && <EmptyState title="No bookings yet" detail="Student meal bookings for today will appear here." />}
      </div>
    </section>
  );
}

function ReviewsPanel({ reviews }) {
  return (
    <section className="panel">
      <div className="panel-title"><h2>Reviews</h2></div>
      {reviews.length ? reviews.map((review) => <div className="border-bottom py-3" key={review.id}><strong>{review.rating}/5 · {review.mealType}</strong><p className="mb-0 text-muted">{review.comment || "No comment"}</p></div>) : <EmptyState title="No reviews" detail="Student ratings will appear here." />}
    </section>
  );
}

function NoticePanel({ selectedMess, notices, onCreated }) {
  const [form, setForm] = useState({ title: "", message: "", expiryDate: addDaysIso(7) });
  async function submit(event) {
    event.preventDefault();
    if (!selectedMess) return toast.info("Create or select a mess first");
    try {
      await api.post("/api/provider/notices", { ...form, messId: selectedMess.id });
      toast.success("Notice posted");
      setForm({ title: "", message: "", expiryDate: addDaysIso(7) });
      onCreated();
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not post notice");
    }
  }
  return (
    <>
    <section className="panel mb-3">
      <div className="panel-title"><h2>Notice board</h2></div>
      <form onSubmit={submit}>
        <FormInput label="Title" value={form.title} onChange={(e) => setForm((f) => ({ ...f, title: e.target.value }))} required />
        <FormTextarea label="Message" rows="4" value={form.message} onChange={(e) => setForm((f) => ({ ...f, message: e.target.value }))} required />
        <FormInput label="Expiry date" type="date" value={form.expiryDate} onChange={(e) => setForm((f) => ({ ...f, expiryDate: e.target.value }))} />
        <button className="btn btn-success">Post notice</button>
      </form>
    </section>

    <section className="panel">
      <div className="panel-title"><h2>Posted Notices</h2></div>
      {notices.length ? notices.map((notice) => (
        <div className="border-bottom py-3" key={notice.id}>
          <strong>{notice.title}</strong>
          <p className="mb-1 text-muted" style={{ fontSize: "14px" }}>{notice.message}</p>
          <small className="text-muted">Expires {notice.expiryDate} · Read count {notice.readCount || 0}</small>
        </div>
      )) : <EmptyState title="No notices posted" detail="Posted notices will appear here." />}
    </section>
    </>
  );
}
