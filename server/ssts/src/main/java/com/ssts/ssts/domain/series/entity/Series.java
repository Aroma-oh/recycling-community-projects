package com.ssts.ssts.domain.series.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssts.ssts.domain.member.entity.Member;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ColumnDefault("0")
    @Column
    private int daylogCount;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime modifiedAt;

    @ColumnDefault("0")
    @Column
    private int voteCount;

    @Column
    private Boolean voteResult;

    @Column
    private int voteAgree;

    @Column
    private int voteDisagree;

    @Column
    private Boolean revoteResult;

    @Column
    private int revoteAgree;

    @Column
    private int revoteDisagree;

    @Enumerated(value = EnumType.STRING)
    @Column
    private VoteStatus voteStatus = VoteStatus.SERIES_ACTIVE;

    @Column
    private Boolean isPublic = false;

    @Column
    private Boolean isEditable = true;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private Boolean isActive = true;


    //투표 개설 시간
    @Column
    private LocalDateTime voteCreatedAt;

    //투표 마감 시간(임시)
    @Column
    private LocalDateTime voteEndAt;


    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAgree(int voteAgree) {
        this.voteAgree = voteAgree;
    }

    public void setVoteDisagree(int voteDisagree) {
        this.voteDisagree = voteDisagree;
    }

    public void setRevoteResult(Boolean revoteResult) {
        this.revoteResult = revoteResult;
    }

    public void setRevoteAgree(int revoteAgree) {
        this.revoteAgree = revoteAgree;
    }

    public void setRevoteDisagree(int revoteDisagree) {
        this.revoteDisagree = revoteDisagree;
    }

    public void setSeriesStatus(VoteStatus seriesStatus) {
        this.voteStatus = seriesStatus;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public void setEditable(Boolean editable) {
        isEditable = editable;
    }

    public void setDaylogCount(int daylogCount) {
        this.daylogCount = daylogCount;
    }

    public void setVoteResult(Boolean voteResult) {
        this.voteResult = voteResult;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    //투표 시간 관련 setMethod
    public void setVoteCreatedAt(LocalDateTime voteCreaateAt){
        this.voteCreatedAt = voteCreaateAt;
    }

    public void setVoteEndAt(LocalDateTime voteEndAt){
        this.voteEndAt = voteEndAt;
    }



    public static Series of(String title){
        Series series = new Series();

        series.setTitle(title);

        return series;

    }

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getSeries().contains(this)) {
            this.member.getSeries().add(this);
        }
    }
    public enum VoteStatus {
        SERIES_ACTIVE("투표 미완료"),
        SERIES_SLEEP("투표 중"),
        SERIES_QUIT("투표 완료");

        @Getter
        private String status;

        VoteStatus(String status) {
            this.status = status;
        }
    }
}
