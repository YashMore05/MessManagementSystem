package com.messmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    private User user;
    private String phone;
    private String area;
    private String profilePhotoPath;
    @Column(nullable = false)
    private BigDecimal walletBalance;
    public static class StudentProfileBuilder {
        private Long id;
        private User user;
        private String phone;
        private String area;
        private String profilePhotoPath;
        private BigDecimal walletBalance;
        StudentProfileBuilder() {
        }

        
        public StudentProfile.StudentProfileBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public StudentProfile.StudentProfileBuilder user(final User user) {
            this.user = user;
            return this;
        }

        
        public StudentProfile.StudentProfileBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        
        public StudentProfile.StudentProfileBuilder area(final String area) {
            this.area = area;
            return this;
        }

        
        public StudentProfile.StudentProfileBuilder profilePhotoPath(final String profilePhotoPath) {
            this.profilePhotoPath = profilePhotoPath;
            return this;
        }

        
        public StudentProfile.StudentProfileBuilder walletBalance(final BigDecimal walletBalance) {
            this.walletBalance = walletBalance;
            return this;
        }
        public StudentProfile build() {
            return new StudentProfile(this.id, this.user, this.phone, this.area, this.profilePhotoPath, this.walletBalance);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "StudentProfile.StudentProfileBuilder(id=" + this.id + ", user=" + this.user + ", phone=" + this.phone + ", area=" + this.area + ", profilePhotoPath=" + this.profilePhotoPath + ", walletBalance=" + this.walletBalance + ")";
        }
    }
    public static StudentProfile.StudentProfileBuilder builder() {
        return new StudentProfile.StudentProfileBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getArea() {
        return this.area;
    }
    public String getProfilePhotoPath() {
        return this.profilePhotoPath;
    }
    public BigDecimal getWalletBalance() {
        return this.walletBalance;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setUser(final User user) {
        this.user = user;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    public void setArea(final String area) {
        this.area = area;
    }
    public void setProfilePhotoPath(final String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
    public void setWalletBalance(final BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }
    public StudentProfile() {
    }
    public StudentProfile(final Long id, final User user, final String phone, final String area, final String profilePhotoPath, final BigDecimal walletBalance) {
        this.id = id;
        this.user = user;
        this.phone = phone;
        this.area = area;
        this.profilePhotoPath = profilePhotoPath;
        this.walletBalance = walletBalance;
    }
}
