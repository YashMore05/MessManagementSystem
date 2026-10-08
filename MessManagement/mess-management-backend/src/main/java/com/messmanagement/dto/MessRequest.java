package com.messmanagement.dto;

import com.messmanagement.enums.MessType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class MessRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    private String area;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    @NotNull
    private MessType messType;
    private List<String> photoPaths;

    public MessRequest() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<String> getPhotoPaths() {
        return this.photoPaths;
    }

    public void setPhotoPaths(final List<String> photoPaths) {
        this.photoPaths = photoPaths;
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

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MessRequest)) return false;
        final MessRequest other = (MessRequest) o;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$latitude = this.getLatitude();
        final Object other$latitude = other.getLatitude();
        if (this$latitude == null ? other$latitude != null : !this$latitude.equals(other$latitude)) return false;
        final Object this$longitude = this.getLongitude();
        final Object other$longitude = other.getLongitude();
        if (this$longitude == null ? other$longitude != null : !this$longitude.equals(other$longitude)) return false;
        final Object this$capacity = this.getCapacity();
        final Object other$capacity = other.getCapacity();
        if (this$capacity == null ? other$capacity != null : !this$capacity.equals(other$capacity)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$area = this.getArea();
        final Object other$area = other.getArea();
        if (this$area == null ? other$area != null : !this$area.equals(other$area)) return false;
        final Object this$messType = this.getMessType();
        final Object other$messType = other.getMessType();
        if (this$messType == null ? other$messType != null : !this$messType.equals(other$messType)) return false;
        final Object this$photoPaths = this.getPhotoPaths();
        final Object other$photoPaths = other.getPhotoPaths();
        if (this$photoPaths == null ? other$photoPaths != null : !this$photoPaths.equals(other$photoPaths)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $latitude = this.getLatitude();
        result = result * PRIME + ($latitude == null ? 43 : $latitude.hashCode());
        final Object $longitude = this.getLongitude();
        result = result * PRIME + ($longitude == null ? 43 : $longitude.hashCode());
        final Object $capacity = this.getCapacity();
        result = result * PRIME + ($capacity == null ? 43 : $capacity.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $area = this.getArea();
        result = result * PRIME + ($area == null ? 43 : $area.hashCode());
        final Object $messType = this.getMessType();
        result = result * PRIME + ($messType == null ? 43 : $messType.hashCode());
        final Object $photoPaths = this.getPhotoPaths();
        result = result * PRIME + ($photoPaths == null ? 43 : $photoPaths.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MessRequest(id=" + this.getId() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", area=" + this.getArea() + ", latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ", capacity=" + this.getCapacity() + ", messType=" + this.getMessType() + ", photoPaths=" + this.getPhotoPaths() + ")";
    }
}
