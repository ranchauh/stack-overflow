package com.overflow.stack.update.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RICH_CONTENT")
public class RichContent {

    @Id
    private String contentId;

    @Column(nullable = false)
    private String contentName;

    private String contentLocation;

    private String contentType;

    @Transient
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    @JsonIgnore
    private Answer answer;

    RichContent(String contentId, String contentName, String contentLocation, String contentType, Long answerId, Answer answer) {
        this.contentId = contentId;
        this.contentName = contentName;
        this.contentLocation = contentLocation;
        this.contentType = contentType;
        this.answerId = answerId;
        this.answer = answer;
    }

    public static RichContentBuilder builder() {
        return new RichContentBuilder();
    }

    public static class RichContentBuilder {
        private String contentId;
        private String contentName;
        private String contentLocation;
        private String contentType;
        private Long answerId;
        private Answer answer;

        RichContentBuilder() {
        }

        public RichContentBuilder contentId(String contentId) {
            this.contentId = contentId;
            return this;
        }

        public RichContentBuilder contentName(String contentName) {
            this.contentName = contentName;
            return this;
        }

        public RichContentBuilder contentLocation(String contentLocation) {
            this.contentLocation = contentLocation;
            return this;
        }

        public RichContentBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public RichContentBuilder answerId(Long answerId) {
            this.answerId = answerId;
            return this;
        }

        public RichContentBuilder answer(Answer answer) {
            this.answer = answer;
            return this;
        }

        public RichContent build() {
            return new RichContent(contentId, contentName, contentLocation, contentType, answerId, answer);
        }

        public String toString() {
            return "RichContent.RichContentBuilder(contentId=" + this.contentId + ", contentName=" + this.contentName + ", contentLocation=" + this.contentLocation + ", contentType=" + this.contentType + ", answerId=" + this.answerId + ", answer=" + this.answer + ")";
        }
    }
}
