package com.messmanagement.dto;

import com.messmanagement.enums.Role;

public class AuthResponse {
    private String token;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
    public String getToken() {
        return this.token;
    }
    public Long getUserId() {
        return this.userId;
    }
    public String getFullName() {
        return this.fullName;
    }
    public String getEmail() {
        return this.email;
    }
    public Role getRole() {
        return this.role;
    }
    public void setToken(final String token) {
        this.token = token;
    }
    public void setUserId(final Long userId) {
        this.userId = userId;
    }
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public void setRole(final Role role) {
        this.role = role;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthResponse)) return false;
        final AuthResponse other = (AuthResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$userId = this.getUserId();
        final java.lang.Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        final java.lang.Object this$fullName = this.getFullName();
        final java.lang.Object other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$role = this.getRole();
        final java.lang.Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AuthResponse;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        final java.lang.Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "AuthResponse(token=" + this.getToken() + ", userId=" + this.getUserId() + ", fullName=" + this.getFullName() + ", email=" + this.getEmail() + ", role=" + this.getRole() + ")";
    }
    public AuthResponse(final String token, final Long userId, final String fullName, final String email, final Role role) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
}
