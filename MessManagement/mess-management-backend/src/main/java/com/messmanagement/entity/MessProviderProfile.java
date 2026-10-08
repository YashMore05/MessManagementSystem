package com.messmanagement.entity;

import com.messmanagement.enums.ProviderStatus;
import jakarta.persistence.*;

@Entity
public class MessProviderProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    private User user;
    private String phone;
    private String documentPath;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderStatus status;
    public static class MessProviderProfileBuilder {
        private Long id;
        private User user;
        private String phone;
        private String documentPath;
        private ProviderStatus status;
        MessProviderProfileBuilder() {
        }

        
        public MessProviderProfile.MessProviderProfileBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public MessProviderProfile.MessProviderProfileBuilder user(final User user) {
            this.user = user;
            return this;
        }

        
        public MessProviderProfile.MessProviderProfileBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        
        public MessProviderProfile.MessProviderProfileBuilder documentPath(final String documentPath) {
            this.documentPath = documentPath;
            return this;
        }

        
        public MessProviderProfile.MessProviderProfileBuilder status(final ProviderStatus status) {
            this.status = status;
            return this;
        }
        public MessProviderProfile build() {
            return new MessProviderProfile(this.id, this.user, this.phone, this.documentPath, this.status);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "MessProviderProfile.MessProviderProfileBuilder(id=" + this.id + ", user=" + this.user + ", phone=" + this.phone + ", documentPath=" + this.documentPath + ", status=" + this.status + ")";
        }
    }
    public static MessProviderProfile.MessProviderProfileBuilder builder() {
        return new MessProviderProfile.MessProviderProfileBuilder();
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
    public String getDocumentPath() {
        return this.documentPath;
    }
    public ProviderStatus getStatus() {
        return this.status;
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
    public void setDocumentPath(final String documentPath) {
        this.documentPath = documentPath;
    }
    public void setStatus(final ProviderStatus status) {
        this.status = status;
    }
    public MessProviderProfile() {
    }
    public MessProviderProfile(final Long id, final User user, final String phone, final String documentPath, final ProviderStatus status) {
        this.id = id;
        this.user = user;
        this.phone = phone;
        this.documentPath = documentPath;
        this.status = status;
    }
}
