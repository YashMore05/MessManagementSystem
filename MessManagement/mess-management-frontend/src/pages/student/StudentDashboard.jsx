import React, { useEffect, useState } from "react";
import {
  Bell,
  Calendar,
  CalendarOff,
  CreditCard,
  Heart,
  Home as HomeIcon,
  MapPin,
  Megaphone,
  MessageSquareWarning,
  QrCode,
  Search,
  Star,
  Store,
  TicketCheck,
  Utensils,
  Vote,
} from "lucide-react";
import QRCode from "react-qr-code";
import { toast } from "react-toastify";
import { api, getAssetUrl } from "../../api/client";
import EmptyState from "../../components/EmptyState";
import { FormInput, FormSelect, FormTextarea } from "../../components/FormInput";
import StatCard from "../../components/StatCard";
import DashboardLayout from "../../layouts/DashboardLayout";
import { addDaysIso, currency, todayIso } from "../../utils/format";
import { useAuth } from "../../auth/AuthContext";

const loadRazorpay = () => {
  return new Promise((resolve) => {
    if (window.Razorpay) {
      resolve(true);
      return;
    }
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.onload = () => resolve(true);
    script.onerror = () => resolve(false);
    document.body.appendChild(script);
  });
};

const navItems = [
  { id: "home", label: "Home", icon: HomeIcon },
  { id: "menu", label: "Menu", icon: Utensils },
  { id: "payments", label: "Payments", icon: CreditCard },
  { id: "qr", label: "QR Attendance", icon: QrCode },
  { id: "notifications", label: "Notifications", icon: Bell },
  { id: "feedback", label: "Feedback", icon: Star },
];

export default function StudentDashboard() {
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState("home");
  const [messes, setMesses] = useState([]);
  const [selectedMess, setSelectedMess] = useState(null);
  const [menu, setMenu] = useState([]);
  const [plans, setPlans] = useState([]);
  const [qrToken, setQrToken] = useState("");
  const [notifications, setNotifications] = useState([]);
  const [subscriptions, setSubscriptions] = useState([]);
  const [mealBookings, setMealBookings] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [notices, setNotices] = useState([]);
  const [polls, setPolls] = useState([]);
  const [search, setSearch] = useState({ area: "", messType: "" });

  const [showLeaveModal, setShowLeaveModal] = useState(false);
  const [leaveForm, setLeaveForm] = useState({ fromDate: todayIso(), toDate: addDaysIso(2), reason: "" });

  async function searchMesses() {
    try {
      const { data } = await api.get("/api/student/messes", {
        params: { ...search, messType: search.messType || undefined },
      });
      setMesses(data);
      if (!selectedMess && data.length) {
        loadMessDetails(data[0]);
      }
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load messes");
    }
  }

  async function loadMessDetails(mess) {
    if (!mess) return;
    setSelectedMess(mess);
    try {
      const [menuRes, planRes] = await Promise.all([
        api.get(`/api/student/messes/${mess.id}/today-menu`),
        api.get(`/api/student/messes/${mess.id}/plans`),
      ]);
      setMenu(menuRes.data);
      setPlans(planRes.data);
      const [noticeRes, pollRes] = await Promise.all([
        api.get(`/api/student/messes/${mess.id}/notices`),
        api.get(`/api/student/messes/${mess.id}/polls`),
      ]);
      setNotices(noticeRes.data);
      setPolls(pollRes.data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load mess details");
    }
  }

  async function subscribe(planId) {
    try {
      const { data } = await api.post(`/api/student/plans/${planId}/subscribe`);
      toast.success(`Subscribed. Amount due: ${currency(data.amountCharged)}`);
      loadSubscriptions();
    } catch (error) {
      toast.error(error.response?.data?.message || "Subscription failed");
    }
  }

  async function generateQr(mealType) {
    if (!selectedMess) return toast.info("Select a mess first");
    try {
      const { data } = await api.get("/api/student/qr", { params: { messId: selectedMess.id, mealType } });
      setQrToken(data.token);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not generate QR");
    }
  }

  async function loadNotifications() {
    try {
      const { data } = await api.get("/api/student/notifications");
      setNotifications(data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load notifications");
    }
  }

  async function loadSubscriptions() {
    try {
      const { data } = await api.get("/api/student/subscriptions");
      setSubscriptions(data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load subscriptions");
    }
  }

  async function loadMealBookings() {
    try {
      const { data } = await api.get("/api/student/meal-bookings");
      setMealBookings(data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load meal bookings");
    }
  }

  async function bookMeal(menuItemId) {
    try {
      await api.post(`/api/student/menu/${menuItemId}/book`);
      toast.success("Meal booked. Pay to activate QR.");
      loadMealBookings();
    } catch (error) {
      toast.error(error.response?.data?.message || "Meal booking failed");
    }
  }

  async function handlePayment(amount, purpose, onSuccess) {
    const scriptLoaded = await loadRazorpay();
    if (!scriptLoaded) {
      toast.error("Razorpay SDK failed to load. Are you offline?");
      return;
    }

    try {
      const { data: order } = await api.post("/api/student/payments/order", {
        amount: String(amount),
        purpose: purpose,
      });

      const options = {
        key: "rzp_test_SaGjQ8ouWVQr8Y",
        amount: Math.round(order.amount * 100),
        currency: order.currency,
        name: "MessHub",
        description: "Payment for " + purpose.replace("-", " ID "),
        order_id: order.razorpayOrderId,
        handler: async function (response) {
          try {
            await api.post("/api/student/payments/verify", {
              orderId: response.razorpay_order_id,
              paymentId: response.razorpay_payment_id,
              signature: response.razorpay_signature,
            });
            toast.success("Payment successful!");
            onSuccess();
          } catch (err) {
            toast.error(err.response?.data?.message || "Verification failed");
          }
        },
        prefill: {
          name: user?.fullName,
          email: user?.email,
        },
        theme: {
          color: "#115e42",
        },
      };

      const paymentObject = new window.Razorpay(options);
      paymentObject.open();
    } catch (error) {
      toast.error(error.response?.data?.message || "Payment initialization failed");
    }
  }

  async function payMealBooking(bookingId, amount) {
    handlePayment(amount, `MEALBOOKING-${bookingId}`, () => {
      loadMealBookings();
    });
  }

  async function loadComplaints() {
    try {
      const { data } = await api.get("/api/student/complaints");
      setComplaints(data);
    } catch (error) {
      toast.error(error.response?.data?.message || "Could not load complaints");
    }
  }

  async function demoPay(subscriptionId, amount) {
    handlePayment(amount, `SUBSCRIPTION-${subscriptionId}`, () => {
      loadSubscriptions();
    });
  }

  async function submitLeave(event) {
    event.preventDefault();
    if (!selectedMess) return toast.info("Select a mess first");
    try {
      await api.post("/api/student/leave", { ...leaveForm, messId: selectedMess.id });
      toast.success("Leave marked successfully");
      setShowLeaveModal(false);
      setLeaveForm({ fromDate: todayIso(), toDate: addDaysIso(2), reason: "" });
    } catch (error) {
      toast.error(error.response?.data?.message || "Leave request failed");
    }
  }

  useEffect(() => {
    searchMesses();
    loadSubscriptions();
    loadMealBookings();
    loadNotifications();
    loadComplaints();
  }, []);

  const activeSubscriptionCount = subscriptions.filter((s) => s.status === "ACTIVE").length;

  return (
    <DashboardLayout
      title="Dashboard"
      subtitle=""
      navItems={navItems}
      activeTab={activeTab}
      onTabChange={(tab) => {
        setActiveTab(tab);
        if (tab === "home") {
          searchMesses();
          loadSubscriptions();
        }
        if (tab === "notifications") loadNotifications();
        if (tab === "payments") {
          loadSubscriptions();
          loadMealBookings();
        }
        if (tab === "menu" || tab === "qr") loadMealBookings();
        if (tab === "feedback") loadComplaints();
      }}
    >
      {/* Welcome Banner */}
      <div className="mb-4">
        <h2 className="fw-bold" style={{ fontFamily: "var(--font-heading)" }}>
          Welcome back, {user?.fullName?.split(" ")[0]}!
        </h2>
        <p className="text-muted" style={{ fontSize: "14px" }}>
          Stay updated with your daily meals and active mess subscriptions.
        </p>
      </div>

      {/* Stats row */}
      <div className="stat-row">
        <StatCard label="Messes Found" value={String(messes.length).padStart(2, "0")} tone="success" icon={Store} />
        <StatCard label="Selected Mess" value={selectedMess?.name || "None"} tone="warning" icon={Utensils} />
        <StatCard label="Today's Meals" value={String(menu.length).padStart(2, "0")} tone="danger" icon={Calendar} />
        <StatCard label="Active Plans" value={String(activeSubscriptionCount).padStart(2, "0")} tone="blue" icon={CreditCard} />
      </div>

      {activeTab === "home" && (
        <>
          {/* Quick Actions Card */}
          <div className="quick-actions-card">
            <h3>Quick Actions</h3>
            <div className="quick-actions-row">
              <button className="btn btn-success d-flex align-items-center gap-2" onClick={() => setActiveTab("qr")}>
                <QrCode size={16} /> Mark Attendance
              </button>
              <button className="btn btn-outline-success d-flex align-items-center gap-2" onClick={() => setActiveTab("feedback")}>
                <MessageSquareWarning size={16} /> Leave Feedback
              </button>
              <button className="btn btn-outline-dark d-flex align-items-center gap-2" onClick={() => setShowLeaveModal(true)}>
                <CalendarOff size={16} /> Report Absence
              </button>
            </div>
          </div>

          {/* Search Card */}
          <section className="panel">
            <div className="panel-title d-flex justify-content-between align-items-center">
              <h2>Find Your Perfect Mess</h2>
              <span className="text-muted" style={{ fontSize: "12px", fontWeight: "700" }}>FILTERS APPLIED: NONE</span>
            </div>
            <div className="row g-3 align-items-end">
              <div className="col-md-5">
                <label className="form-label" style={{ fontSize: "13px", fontWeight: "600", textTransform: "uppercase" }}>Location / Area</label>
                <div className="input-icon-container">
                  <input
                    type="text"
                    placeholder="Search by area (e.g. FC Road)"
                    value={search.area}
                    onChange={(e) => setSearch((s) => ({ ...s, area: e.target.value }))}
                  />
                  <MapPin className="input-icon" size={18} />
                </div>
              </div>
              <div className="col-md-4">
                <label className="form-label" style={{ fontSize: "13px", fontWeight: "600", textTransform: "uppercase" }}>Food Preference</label>
                <div className="input-icon-container">
                  <select value={search.messType} onChange={(e) => setSearch((s) => ({ ...s, messType: e.target.value }))}>
                    <option value="">Any Type</option>
                    <option value="VEG">Veg</option>
                    <option value="NON_VEG">Non-veg</option>
                    <option value="BOTH">Both</option>
                  </select>
                </div>
              </div>
              <div className="col-md-3">
                <button className="btn btn-success w-100" onClick={searchMesses}>
                  Search Now
                </button>
              </div>
            </div>
          </section>

          {/* Mess Grid list */}
          <div className="mb-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h2 className="fw-bold" style={{ fontSize: "20px", fontFamily: "var(--font-heading)" }}>Available Messes Near You</h2>
              <a href="#messes" className="text-success fw-bold text-decoration-none" style={{ fontSize: "14px" }} onClick={searchMesses}>
                VIEW ALL →
              </a>
            </div>

            {messes.length ? (
              <div className="mess-card-grid">
                {/* Left Card: Featured / Selected Mess */}
                <div className="featured-mess-card">
                  <div className="featured-mess-image">
                    <img
                      src={selectedMess?.photoPaths?.[0] ? getAssetUrl(selectedMess.photoPaths[0]) : "/default_mess.png"}
                      alt={selectedMess?.name || "Mess"}
                    />
                    <div className="featured-mess-badges">
                      <span className="badge-soft">BEST RATED</span>
                    </div>
                    <div className="rating-badge">
                      <Star size={14} />
                      <span>4.8</span>
                    </div>
                  </div>
                  <div className="featured-mess-content">
                    <div className="featured-mess-title-row">
                      <div>
                        <h3>{selectedMess?.name}</h3>
                        <div className="featured-mess-location mt-1">
                          <MapPin size={14} />
                          <span>{selectedMess?.area || selectedMess?.address}</span>
                        </div>
                      </div>
                      <div className="featured-mess-price">
                        <span>Starting</span>
                        <strong>{selectedMess?.startingPrice ? currency(selectedMess.startingPrice) : "₹0"}<span style={{ fontSize: "12px", fontWeight: "normal" }}>/mo</span></strong>
                      </div>
                    </div>

                    <div className="featured-mess-meta-badges">
                      <span>{selectedMess?.messType}</span>
                      <span>WIFI AVAILABLE</span>
                      <span>{selectedMess?.capacity ? `70/${selectedMess.capacity} SLOTS` : "70/100 SLOTS"}</span>
                    </div>

                    <div className="featured-mess-actions">
                      <button className="btn btn-success flex-grow-1" onClick={() => loadMessDetails(selectedMess)}>
                        Select Mess
                      </button>
                      <button className="btn-like">
                        <Heart size={20} />
                      </button>
                    </div>

                    {/* Subscription plan details for selected mess */}
                    {plans.length > 0 && (
                      <div className="mt-4 pt-4 border-top">
                        <h4 style={{ fontSize: "16px", fontWeight: 700 }} className="mb-3">Subscribe to a Plan</h4>
                        <div className="row g-2">
                          {plans.map((plan) => (
                            <div className="col-12" key={plan.id}>
                              <div className="border rounded p-3 d-flex justify-content-between align-items-center bg-light">
                                <div>
                                  <h5 className="mb-1 fw-bold" style={{ fontSize: "14px" }}>{plan.name}</h5>
                                  <small className="text-muted">{plan.mealsIncluded} meals · {plan.seatLimit} seats</small>
                                </div>
                                <div className="text-end">
                                  <span className="d-block fw-bold text-success mb-1">{currency(plan.price)}</span>
                                  <button className="btn btn-success btn-sm py-1 px-3" onClick={() => subscribe(plan.id)}>
                                    Subscribe
                                  </button>
                                </div>
                              </div>
                            </div>
                          ))}
                        </div>
                      </div>
                    )}
                  </div>
                </div>

                {/* Right Column: Other Mess List */}
                <div className="mess-side-list">
                  {messes
                    .filter((m) => m.id !== selectedMess?.id)
                    .map((mess) => (
                      <div
                        className={`mess-side-item ${selectedMess?.id === mess.id ? "selected" : ""}`}
                        key={mess.id}
                        onClick={() => loadMessDetails(mess)}
                      >
                        <div className="mess-side-image">
                          <img src={mess.photoPaths?.[0] ? getAssetUrl(mess.photoPaths[0]) : "/default_mess.png"} alt={mess.name} />
                        </div>
                        <div className="mess-side-content">
                          <h4>{mess.name}</h4>
                          <div className="mess-side-info">
                            {mess.area || mess.address}
                          </div>
                          <div className="mess-side-price text-success">
                            {mess.startingPrice ? `${currency(mess.startingPrice)}/mo` : "₹0/mo"}
                          </div>
                        </div>
                        <span className="badge-soft">{mess.messType}</span>
                      </div>
                    ))}

                  {messes.filter((m) => m.id !== selectedMess?.id).length === 0 && (
                    <EmptyState title="No other messes nearby" detail="You have selected the only mess in this search." />
                  )}
                </div>
              </div>
            ) : (
              <EmptyState title="No messes found" detail="Try a different area or food type filter." />
            )}
          </div>

          {/* Notices & Sunday Special Polls widgets side-by-side */}
          <div className="content-grid mt-4">
            <section className="panel half">
              <div className="panel-title">
                <h2>Notice Board</h2>
                <Megaphone size={18} className="text-success" />
              </div>
              {notices.length ? (
                notices.map((notice) => (
                  <div className="border-bottom py-3" key={notice.id}>
                    <strong className="d-block text-success">{notice.title}</strong>
                    <p className="mb-2 text-muted" style={{ fontSize: "14px" }}>{notice.message}</p>
                    <div className="d-flex justify-content-between align-items-center">
                      <small className="text-muted">Expires {notice.expiryDate}</small>
                      <button className="btn btn-sm btn-outline-success py-1 px-2" style={{ fontSize: "12px" }} onClick={async () => {
                        try {
                          await api.post(`/api/student/notices/${notice.id}/read`);
                          toast.success("Notice marked read");
                          loadMessDetails(selectedMess);
                        } catch (err) {
                          toast.error("Could not mark notice read");
                        }
                      }}>
                        Mark read
                      </button>
                    </div>
                  </div>
                ))
              ) : (
                <EmptyState title="No active notices" detail="Notices from your provider will appear here." />
              )}
            </section>

            <section className="panel half">
              <div className="panel-title">
                <h2>Sunday Special Polls</h2>
                <Vote size={18} className="text-success" />
              </div>
              {polls.length ? (
                polls.map((poll) => (
                  <div className="border rounded p-3 mb-3" key={poll.id}>
                    <strong className="d-block text-success mb-2">{poll.title}</strong>
                    <p className="text-muted" style={{ fontSize: "12px" }}>Sunday date: {poll.sundayDate}</p>
                    <div className="d-flex flex-wrap gap-2 mt-3">
                      {poll.options?.map((option) => (
                        <button
                          key={option.id}
                          className="btn btn-outline-success btn-sm"
                          onClick={async () => {
                            try {
                              await api.post(`/api/student/polls/${poll.id}/vote/${option.id}`);
                              toast.success("Vote submitted successfully");
                            } catch (err) {
                              toast.error(err.response?.data?.message || "Failed to submit vote");
                            }
                          }}
                        >
                          {option.dishName}
                        </button>
                      ))}
                    </div>
                  </div>
                ))
              ) : (
                <EmptyState title="No active polls" detail="Participate in Sunday menu polls here." />
              )}
            </section>
          </div>
        </>
      )}

      {activeTab === "menu" && (
        <section className="panel">
          <div className="panel-title">
            <h2>Today's Menu</h2>
            <span className="badge-soft">{selectedMess?.name || "No mess selected"}</span>
          </div>
          {menu.length ? (
            <div className="row g-3">
              {menu.map((item) => (
                <div className="col-md-4" key={item.id}>
                  <div className="border rounded p-4 h-100 d-flex flex-column justify-content-between bg-light">
                    <div>
                      <div className="d-flex justify-content-between align-items-center mb-3">
                        <strong className="fs-5">{item.mealType}</strong>
                        <span className="badge-soft text-success">{currency(item.price)}</span>
                      </div>
                      <p className="text-muted" style={{ fontSize: "14px", lineHeight: "1.6" }}>{item.items}</p>
                    </div>
                    <button className="btn btn-success w-100 mt-4" onClick={() => bookMeal(item.id)}>
                      Book {item.mealType.toLowerCase()}
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <EmptyState title="No menu posted today" detail="Select another mess or check back later." />
          )}
        </section>
      )}

      {activeTab === "payments" && (
        <div className="content-grid">
          <section className="panel half">
            <div className="panel-title">
              <h2>Subscription Payments</h2>
            </div>
            <div className="table-responsive">
              <table className="table">
                <thead>
                  <tr>
                    <th>Mess</th>
                    <th>Plan</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {subscriptions.map((sub) => (
                    <tr key={sub.id}>
                      <td>{sub.mess?.name}</td>
                      <td>{sub.plan?.name}</td>
                      <td className="fw-bold">{currency(sub.amountCharged)}</td>
                      <td>
                        <span className={`badge-soft ${sub.paymentStatus === "PAID" ? "" : "badge-soft-warning"}`}>
                          {sub.paymentStatus}
                        </span>
                      </td>
                      <td className="text-end">
                        {sub.paymentStatus !== "PAID" && (
                          <button className="btn btn-sm btn-success py-1 px-3" onClick={() => demoPay(sub.id, sub.amountCharged)}>
                            Pay
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            {!subscriptions.length && <EmptyState title="No subscriptions" detail="Purchase a subscription plan." />}
          </section>

          <section className="panel half">
            <div className="panel-title">
              <h2>Meal Bookings</h2>
              <button className="btn btn-sm btn-outline-success py-1 px-2" onClick={loadMealBookings}>
                Refresh
              </button>
            </div>
            <div className="table-responsive">
              <table className="table">
                <thead>
                  <tr>
                    <th>Meal</th>
                    <th>Mess</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {mealBookings.map((booking) => (
                    <tr key={booking.id}>
                      <td>{booking.mealType}</td>
                      <td>{booking.mess?.name}</td>
                      <td className="fw-bold">{currency(booking.amount)}</td>
                      <td>
                        <span className={`badge-soft ${booking.status === "PAID" ? "" : "badge-soft-warning"}`}>
                          {booking.status}
                        </span>
                      </td>
                      <td className="text-end">
                        {booking.status === "BOOKED" && (
                          <button className="btn btn-sm btn-success py-1 px-3" onClick={() => payMealBooking(booking.id, booking.amount)}>
                            Pay
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            {!mealBookings.length && <EmptyState title="No meal bookings" detail="Book a single meal from menu." />}
          </section>
        </div>
      )}

      {activeTab === "qr" && (
        <div className="content-grid">
          {/* Active pass */}
          <section className="panel half">
            <div className="panel-title">
              <h2>Today's Meal Pass</h2>
            </div>
            {(() => {
              const active = mealBookings.find((b) => b.status === "PAID") || mealBookings.find((b) => b.status === "BOOKED");
              if (!active) return <EmptyState title="No meal pass yet" detail="Book and pay for a meal to activate your pass." />;
              return (
                <div className="d-flex flex-column align-items-center">
                  <div className="qr-box p-3 border rounded mb-3 bg-white" style={{ display: "inline-block" }}>
                    {active.status === "PAID" ? (
                      <QRCode value={active.qrToken} size={200} />
                    ) : (
                      <div className="text-center p-3">
                        <strong className="text-warning">Payment Pending</strong>
                        <p className="small text-muted mt-2">Pay this booking in Payments tab to activate QR.</p>
                      </div>
                    )}
                  </div>
                  <h3 className="fw-bold fs-5 mt-2">{active.mealType} at {active.mess?.name}</h3>
                  <span className="badge-soft mt-1">{currency(active.amount)}</span>
                  {active.status === "PAID" && (
                    <div className="w-100 mt-3">
                      <FormTextarea label="QR Token String" rows="2" value={active.qrToken} readOnly />
                    </div>
                  )}
                </div>
              );
            })()}
          </section>

          {/* Generate QR Attendance */}
          <section className="panel half">
            <div className="panel-title">
              <h2>QR Attendance</h2>
            </div>
            <p className="text-muted" style={{ fontSize: "14px" }}>
              Going to mess? Choose your meal type to generate your attendance QR code.
            </p>
            <div className="d-flex gap-2 mb-4">
              {["BREAKFAST", "LUNCH", "DINNER"].map((meal) => (
                <button key={meal} className="btn btn-outline-success btn-sm flex-grow-1" onClick={() => generateQr(meal)}>
                  {meal}
                </button>
              ))}
            </div>

            <div className="d-flex flex-column align-items-center border rounded p-4 bg-light">
              {qrToken ? (
                <>
                  <div className="qr-box p-3 bg-white border rounded mb-3">
                    <QRCode value={qrToken} size={180} />
                  </div>
                  <div className="w-100">
                    <FormTextarea label="Manual Attendance Token" rows="2" value={qrToken} readOnly />
                  </div>
                </>
              ) : (
                <EmptyState title="Generate QR" detail="Choose meal type to display attendance QR." />
              )}
            </div>
          </section>
        </div>
      )}

      {activeTab === "notifications" && (
        <section className="panel">
          <div className="panel-title">
            <h2>Notifications Log</h2>
          </div>
          {notifications.length ? (
            notifications.map((note) => (
              <div className="border-bottom py-3" key={note.id}>
                <strong className="d-block text-success">{note.title}</strong>
                <p className="mb-0 text-muted" style={{ fontSize: "14px" }}>{note.message}</p>
              </div>
            ))
          ) : (
            <EmptyState title="No notifications" detail="Your alerts log is empty." />
          )}
        </section>
      )}

      {activeTab === "feedback" && (
        <div className="content-grid">
          <section className="panel half">
            <div className="panel-title">
              <h2>Meal Review</h2>
            </div>
            <form
              onSubmit={async (event) => {
                event.preventDefault();
                if (!selectedMess) return toast.info("Select a mess first");
                const fd = new FormData(event.target);
                try {
                  await api.post("/api/student/reviews", {
                    messId: selectedMess.id,
                    mealType: fd.get("mealType"),
                    mealDate: fd.get("mealDate"),
                    rating: Number(fd.get("rating")),
                    comment: fd.get("comment"),
                  });
                  toast.success("Review submitted");
                  event.target.reset();
                } catch (err) {
                  toast.error("Failed to submit review");
                }
              }}
            >
              <div className="form-group-custom">
                <label>Meal Type</label>
                <select name="mealType" defaultValue="LUNCH" className="form-select">
                  <option>BREAKFAST</option>
                  <option>LUNCH</option>
                  <option>DINNER</option>
                </select>
              </div>
              <div className="form-group-custom">
                <label>Date</label>
                <input type="date" name="mealDate" defaultValue={todayIso()} className="form-control" />
              </div>
              <div className="form-group-custom">
                <label>Rating (1-5)</label>
                <input type="number" name="rating" min="1" max="5" defaultValue="5" className="form-control" required />
              </div>
              <div className="form-group-custom">
                <label>Comment</label>
                <textarea name="comment" className="form-control" rows="3" placeholder="Food quality, hygiene, taste etc."></textarea>
              </div>
              <button className="btn btn-success mt-2">Submit Review</button>
            </form>
          </section>

          <section className="panel half">
            <div className="panel-title">
              <h2>Raise Complaint</h2>
            </div>
            <form
              onSubmit={async (event) => {
                event.preventDefault();
                if (!selectedMess) return toast.info("Select a mess first");
                const fd = new FormData(event.target);
                try {
                  await api.post("/api/student/complaints", {
                    messId: selectedMess.id,
                    category: fd.get("category"),
                    title: fd.get("title"),
                    description: fd.get("description"),
                  });
                  toast.success("Complaint raised");
                  event.target.reset();
                  loadComplaints();
                } catch (err) {
                  toast.error("Failed to raise complaint");
                }
              }}
            >
              <div className="form-group-custom">
                <label>Category</label>
                <select name="category" defaultValue="FOOD" className="form-select">
                  <option>FOOD</option>
                  <option>HYGIENE</option>
                  <option>BILLING</option>
                </select>
              </div>
              <div className="form-group-custom">
                <label>Title</label>
                <input type="text" name="title" className="form-control" required placeholder="Brief description of issue" />
              </div>
              <div className="form-group-custom">
                <label>Description</label>
                <textarea name="description" className="form-control" rows="3" required placeholder="Describe the details..."></textarea>
              </div>
              <button className="btn btn-success mt-2" style={{ backgroundColor: "#854d0e", borderColor: "#854d0e" }}>
                Raise Complaint
              </button>
            </form>

            <div className="mt-4 pt-4 border-top">
              <h4 style={{ fontSize: "16px", fontWeight: "700" }} className="mb-3">Raised Complaints</h4>
              <div className="table-responsive">
                <table className="table" style={{ fontSize: "13px" }}>
                  <thead>
                    <tr>
                      <th>Title</th>
                      <th>Category</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {complaints.map((c) => (
                      <tr key={c.id}>
                        <td>{c.title}</td>
                        <td>{c.category}</td>
                        <td>
                          <span className={`badge-soft ${c.status === "RESOLVED" ? "" : "badge-soft-warning"}`}>
                            {c.status}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </section>
        </div>
      )}

      {/* Leave/Absence Modal */}
      {showLeaveModal && (
        <div
          className="modal-backdrop"
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: "rgba(0,0,0,0.5)",
            display: "grid",
            placeItems: "center",
            zIndex: 1050,
          }}
        >
          <div className="panel" style={{ width: "min(480px, 100%)", margin: "20px" }}>
            <div className="panel-title d-flex justify-content-between align-items-center">
              <h2>Report Absence / Leave</h2>
              <button
                type="button"
                className="btn-close border-0 bg-transparent text-muted fs-4"
                onClick={() => setShowLeaveModal(false)}
                style={{ cursor: "pointer" }}
              >
                ×
              </button>
            </div>
            <form onSubmit={submitLeave}>
              <div className="form-group-custom">
                <label>From Date</label>
                <input
                  type="date"
                  value={leaveForm.fromDate}
                  onChange={(e) => setLeaveForm((lf) => ({ ...lf, fromDate: e.target.value }))}
                  required
                />
              </div>
              <div className="form-group-custom">
                <label>To Date</label>
                <input
                  type="date"
                  value={leaveForm.toDate}
                  onChange={(e) => setLeaveForm((lf) => ({ ...lf, toDate: e.target.value }))}
                  required
                />
              </div>
              <div className="form-group-custom">
                <label>Reason</label>
                <textarea
                  className="form-control border rounded p-2"
                  rows="3"
                  value={leaveForm.reason}
                  onChange={(e) => setLeaveForm((lf) => ({ ...lf, reason: e.target.value }))}
                  placeholder="E.g. going home for weekend"
                  style={{ width: "100%", fontSize: "14px" }}
                ></textarea>
              </div>
              <div className="d-flex justify-content-end gap-2 mt-4">
                <button type="button" className="btn btn-outline-dark" onClick={() => setShowLeaveModal(false)}>
                  Cancel
                </button>
                <button type="submit" className="btn btn-success">
                  Submit Request
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </DashboardLayout>
  );
}
