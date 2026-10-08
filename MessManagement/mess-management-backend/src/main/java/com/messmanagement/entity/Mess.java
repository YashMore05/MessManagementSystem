package com.messmanagement.entity;

import com.messmanagement.enums.MessType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User provider;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    private String area;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessType messType;
    private boolean active;
    private LocalDateTime createdAt;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photoPaths;

    @Transient
    private java.math.BigDecimal startingPrice;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        active = true;
    }
    private static List<String> $default$photoPaths() {
        return new ArrayList<>();
    }
    public static class MessBuilder {
        private Long id;
        private User provider;
        private String name;
        private String address;
        private String area;
        private Double latitude;
        private Double longitude;
        private Integer capacity;
        private MessType messType;
        private boolean active;
        private LocalDateTime createdAt;
        private boolean photoPaths$set;
        private List<String> photoPaths$value;
        MessBuilder() {
        }

        
        public Mess.MessBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Mess.MessBuilder provider(final User provider) {
            this.provider = provider;
            return this;
        }

        
        public Mess.MessBuilder name(final String name) {
            this.name = name;
            return this;
        }

        
        public Mess.MessBuilder address(final String address) {
            this.address = address;
            return this;
        }

        
        public Mess.MessBuilder area(final String area) {
            this.area = area;
            return this;
        }

        
        public Mess.MessBuilder latitude(final Double latitude) {
            this.latitude = latitude;
            return this;
        }

        
        public Mess.MessBuilder longitude(final Double longitude) {
            this.longitude = longitude;
            return this;
        }

        
        public Mess.MessBuilder capacity(final Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        
        public Mess.MessBuilder messType(final MessType messType) {
            this.messType = messType;
            return this;
        }

        
        public Mess.MessBuilder active(final boolean active) {
            this.active = active;
            return this;
        }

        
        public Mess.MessBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        
        public Mess.MessBuilder photoPaths(final List<String> photoPaths) {
            this.photoPaths$value = photoPaths;
            photoPaths$set = true;
            return this;
        }
        public Mess build() {
            List<String> photoPaths$value = this.photoPaths$value;
            if (!this.photoPaths$set) photoPaths$value = Mess.$default$photoPaths();
            return new Mess(this.id, this.provider, this.name, this.address, this.area, this.latitude, this.longitude, this.capacity, this.messType, this.active, this.createdAt, photoPaths$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Mess.MessBuilder(id=" + this.id + ", provider=" + this.provider + ", name=" + this.name + ", address=" + this.address + ", area=" + this.area + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", capacity=" + this.capacity + ", messType=" + this.messType + ", active=" + this.active + ", createdAt=" + this.createdAt + ", photoPaths$value=" + this.photoPaths$value + ")";
        }
    }
    public static Mess.MessBuilder builder() {
        return new Mess.MessBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getProvider() {
        return this.provider;
    }
    public String getName() {
        return this.name;
    }
    public String getAddress() {
        return this.address;
    }
    public String getArea() {
        return this.area;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public Integer getCapacity() {
        return this.capacity;
    }
    public MessType getMessType() {
        return this.messType;
    }
    public boolean isActive() {
        return this.active;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public List<String> getPhotoPaths() {
        return this.photoPaths;
    }
    public java.math.BigDecimal getStartingPrice() {
        return this.startingPrice;
    }
    public void setStartingPrice(final java.math.BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setProvider(final User provider) {
        this.provider = provider;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public void setAddress(final String address) {
        this.address = address;
    }
    public void setArea(final String area) {
        this.area = area;
    }
    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }
    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }
    public void setMessType(final MessType messType) {
        this.messType = messType;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setPhotoPaths(final List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }
    public Mess() {
        this.photoPaths = Mess.$default$photoPaths();
    }
    public Mess(final Long id, final User provider, final String name, final String address, final String area, final Double latitude, final Double longitude, final Integer capacity, final MessType messType, final boolean active, final LocalDateTime createdAt, final List<String> photoPaths) {
        this.id = id;
        this.provider = provider;
        this.name = name;
        this.address = address;
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.messType = messType;
        this.active = active;
        this.createdAt = createdAt;
        this.photoPaths = photoPaths;
    }
}
