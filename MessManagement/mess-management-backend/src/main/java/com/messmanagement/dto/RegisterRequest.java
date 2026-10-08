package com.messmanagement.dto;

import com.messmanagement.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String email;
    private String collegeId;
    @NotBlank
    private String password;
    @NotNull
    private Role role;
    private String phone;
    private String area;
    public RegisterRequest() {
    }
    public String getFullName() {
        return this.fullName;
    }
    public String getEmail() {
        return this.email;
    }
    public String getCollegeId() {
        return this.collegeId;
    }
    public String getPassword() {
        return this.password;
    }
    public Role getRole() {
        return this.role;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getArea() {
        return this.area;
    }
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public void setCollegeId(final String collegeId) {
        this.collegeId = collegeId;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public void setRole(final Role role) {
        this.role = role;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    public void setArea(final String area) {
        this.area = area;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RegisterRequest)) return false;
        final RegisterRequest other = (RegisterRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$fullName = this.getFullName();
        final java.lang.Object other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$collegeId = this.getCollegeId();
        final java.lang.Object other$collegeId = other.getCollegeId();
        if (this$collegeId == null ? other$collegeId != null : !this$collegeId.equals(other$collegeId)) return false;
        final java.lang.Object this$password = this.getPassword();
        final java.lang.Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final java.lang.Object this$role = this.getRole();
        final java.lang.Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final java.lang.Object this$phone = this.getPhone();
        final java.lang.Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        final java.lang.Object this$area = this.getArea();
        final java.lang.Object other$area = other.getArea();
        if (this$area == null ? other$area != null : !this$area.equals(other$area)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof RegisterRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $collegeId = this.getCollegeId();
        result = result * PRIME + ($collegeId == null ? 43 : $collegeId.hashCode());
        final java.lang.Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final java.lang.Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final java.lang.Object $area = this.getArea();
        result = result * PRIME + ($area == null ? 43 : $area.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "RegisterRequest(fullName=" + this.getFullName() + ", email=" + this.getEmail() + ", collegeId=" + this.getCollegeId() + ", password=" + this.getPassword() + ", role=" + this.getRole() + ", phone=" + this.getPhone() + ", area=" + this.getArea() + ")";
    }
}
