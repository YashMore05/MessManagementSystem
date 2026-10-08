package com.messmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Mess mess;
    private String title;
    private LocalDate pollDate;
    private LocalDate sundayDate;
    private boolean published;
    private String winningDish;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    private static List<PollOption> $default$options() {
        return new ArrayList<>();
    }
    public static class PollBuilder {
        private Long id;
        private Mess mess;
        private String title;
        private LocalDate pollDate;
        private LocalDate sundayDate;
        private boolean published;
        private String winningDish;
        private LocalDateTime createdAt;
        private boolean options$set;
        private List<PollOption> options$value;
        PollBuilder() {
        }

        
        public Poll.PollBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Poll.PollBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Poll.PollBuilder title(final String title) {
            this.title = title;
            return this;
        }

        
        public Poll.PollBuilder pollDate(final LocalDate pollDate) {
            this.pollDate = pollDate;
            return this;
        }

        
        public Poll.PollBuilder sundayDate(final LocalDate sundayDate) {
            this.sundayDate = sundayDate;
            return this;
        }

        
        public Poll.PollBuilder published(final boolean published) {
            this.published = published;
            return this;
        }

        
        public Poll.PollBuilder winningDish(final String winningDish) {
            this.winningDish = winningDish;
            return this;
        }

        
        public Poll.PollBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        
        public Poll.PollBuilder options(final List<PollOption> options) {
            this.options$value = options;
            options$set = true;
            return this;
        }
        public Poll build() {
            List<PollOption> options$value = this.options$value;
            if (!this.options$set) options$value = Poll.$default$options();
            return new Poll(this.id, this.mess, this.title, this.pollDate, this.sundayDate, this.published, this.winningDish, this.createdAt, options$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Poll.PollBuilder(id=" + this.id + ", mess=" + this.mess + ", title=" + this.title + ", pollDate=" + this.pollDate + ", sundayDate=" + this.sundayDate + ", published=" + this.published + ", winningDish=" + this.winningDish + ", createdAt=" + this.createdAt + ", options$value=" + this.options$value + ")";
        }
    }
    public static Poll.PollBuilder builder() {
        return new Poll.PollBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Mess getMess() {
        return this.mess;
    }
    public String getTitle() {
        return this.title;
    }
    public LocalDate getPollDate() {
        return this.pollDate;
    }
    public LocalDate getSundayDate() {
        return this.sundayDate;
    }
    public boolean isPublished() {
        return this.published;
    }
    public String getWinningDish() {
        return this.winningDish;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public List<PollOption> getOptions() {
        return this.options;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setPollDate(final LocalDate pollDate) {
        this.pollDate = pollDate;
    }
    public void setSundayDate(final LocalDate sundayDate) {
        this.sundayDate = sundayDate;
    }
    public void setPublished(final boolean published) {
        this.published = published;
    }
    public void setWinningDish(final String winningDish) {
        this.winningDish = winningDish;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setOptions(final List<PollOption> options) {
        this.options = options;
    }
    public Poll() {
        this.options = Poll.$default$options();
    }
    public Poll(final Long id, final Mess mess, final String title, final LocalDate pollDate, final LocalDate sundayDate, final boolean published, final String winningDish, final LocalDateTime createdAt, final List<PollOption> options) {
        this.id = id;
        this.mess = mess;
        this.title = title;
        this.pollDate = pollDate;
        this.sundayDate = sundayDate;
        this.published = published;
        this.winningDish = winningDish;
        this.createdAt = createdAt;
        this.options = options;
    }
}
