package com.messmanagement.entity;

import com.messmanagement.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(unique = true)
    private String collegeId;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private boolean enabled;
    private boolean banned;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        enabled = true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public static class UserBuilder {
        private Long id;
        private String fullName;
        private String email;
        private String collegeId;
        private String password;
        private Role role;
        private boolean enabled;
        private boolean banned;
        private LocalDateTime createdAt;
        UserBuilder() {
        }

        
        public User.UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public User.UserBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        
        public User.UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        
        public User.UserBuilder collegeId(final String collegeId) {
            this.collegeId = collegeId;
            return this;
        }

        
        @JsonIgnore
        public User.UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        
        public User.UserBuilder role(final Role role) {
            this.role = role;
            return this;
        }

        
        public User.UserBuilder enabled(final boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        
        public User.UserBuilder banned(final boolean banned) {
            this.banned = banned;
            return this;
        }

        
        public User.UserBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public User build() {
            return new User(this.id, this.fullName, this.email, this.collegeId, this.password, this.role, this.enabled, this.banned, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "User.UserBuilder(id=" + this.id + ", fullName=" + this.fullName + ", email=" + this.email + ", collegeId=" + this.collegeId + ", password=" + this.password + ", role=" + this.role + ", enabled=" + this.enabled + ", banned=" + this.banned + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }
    public Long getId() {
        return this.id;
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
    public boolean isEnabled() {
        return this.enabled;
    }
    public boolean isBanned() {
        return this.banned;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setId(final Long id) {
        this.id = id;
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
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    public void setBanned(final boolean banned) {
        this.banned = banned;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public User() {
    }
    public User(final Long id, final String fullName, final String email, final String collegeId, final String password, final Role role, final boolean enabled, final boolean banned, final LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.collegeId = collegeId;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.banned = banned;
        this.createdAt = createdAt;
    }
}
