package com.messmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JsonIgnore
    private Poll poll;
    @Column(nullable = false)
    private String dishName;
    public static class PollOptionBuilder {
        private Long id;
        private Poll poll;
        private String dishName;
        PollOptionBuilder() {
        }

        
        public PollOption.PollOptionBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        @JsonIgnore
        public PollOption.PollOptionBuilder poll(final Poll poll) {
            this.poll = poll;
            return this;
        }

        
        public PollOption.PollOptionBuilder dishName(final String dishName) {
            this.dishName = dishName;
            return this;
        }
        public PollOption build() {
            return new PollOption(this.id, this.poll, this.dishName);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "PollOption.PollOptionBuilder(id=" + this.id + ", poll=" + this.poll + ", dishName=" + this.dishName + ")";
        }
    }
    public static PollOption.PollOptionBuilder builder() {
        return new PollOption.PollOptionBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Poll getPoll() {
        return this.poll;
    }
    public String getDishName() {
        return this.dishName;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setPoll(final Poll poll) {
        this.poll = poll;
    }
    public void setDishName(final String dishName) {
        this.dishName = dishName;
    }
    public PollOption() {
    }
    public PollOption(final Long id, final Poll poll, final String dishName) {
        this.id = id;
        this.poll = poll;
        this.dishName = dishName;
    }
}
