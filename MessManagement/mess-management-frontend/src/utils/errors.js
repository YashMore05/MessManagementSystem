export function getApiErrorMessage(error, fallback) {
  if (error.response?.data?.message) {
    return error.response.data.message;
  }
  if (error.code === "ERR_NETWORK") {
    return "Backend not reachable. Start Spring Boot on http://localhost:8080 and restart the frontend.";
  }
  return fallback;
}
