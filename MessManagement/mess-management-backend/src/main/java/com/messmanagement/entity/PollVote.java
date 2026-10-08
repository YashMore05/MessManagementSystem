package com.messmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "poll_id"}))
public class PollVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private Poll poll;
    @ManyToOne(optional = false)
    private PollOption option;
    private LocalDateTime votedAt;

    @PrePersist
    public void prePersist() {
        votedAt = LocalDateTime.now();
    }
    public static class PollVoteBuilder {
        private Long id;
        private User student;
        private Poll poll;
        private PollOption option;
        private LocalDateTime votedAt;
        PollVoteBuilder() {
        }

        
        public PollVote.PollVoteBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public PollVote.PollVoteBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public PollVote.PollVoteBuilder poll(final Poll poll) {
            this.poll = poll;
            return this;
        }

        
        public PollVote.PollVoteBuilder option(final PollOption option) {
            this.option = option;
            return this;
        }

        
        public PollVote.PollVoteBuilder votedAt(final LocalDateTime votedAt) {
            this.votedAt = votedAt;
            return this;
        }
        public PollVote build() {
            return new PollVote(this.id, this.student, this.poll, this.option, this.votedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "PollVote.PollVoteBuilder(id=" + this.id + ", student=" + this.student + ", poll=" + this.poll + ", option=" + this.option + ", votedAt=" + this.votedAt + ")";
        }
    }
    public static PollVote.PollVoteBuilder builder() {
        return new PollVote.PollVoteBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getStudent() {
        return this.student;
    }
    public Poll getPoll() {
        return this.poll;
    }
    public PollOption getOption() {
        return this.option;
    }
    public LocalDateTime getVotedAt() {
        return this.votedAt;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setStudent(final User student) {
        this.student = student;
    }
    public void setPoll(final Poll poll) {
        this.poll = poll;
    }
    public void setOption(final PollOption option) {
        this.option = option;
    }
    public void setVotedAt(final LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }
    public PollVote() {
    }
    public PollVote(final Long id, final User student, final Poll poll, final PollOption option, final LocalDateTime votedAt) {
        this.id = id;
        this.student = student;
        this.poll = poll;
        this.option = option;
        this.votedAt = votedAt;
    }
}
