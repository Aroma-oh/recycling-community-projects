package com.ssts.ssts.domain.series.dto;


import com.ssts.ssts.domain.series.entity.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SeriesResponseDto {

    private Long id;

    private String title;

    private int daylogCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private int voteCount;

    private Boolean voteResult;

    private int voteAgree;

    private int voteDisagree;

    private Boolean revoteResult;

    private int revoteAgree;

    private int revoteDisagree;

    private Series.SeriesStatus seriesStatus;

    private Boolean isPublic;

    private Boolean isEditable;

    private Boolean isActive;


    public static SeriesResponseDto of(Long id,
                                       String title,
                                       int daylogCount,
                                       LocalDateTime createdAt,
                                       LocalDateTime modifiedAt,
                                       int voteCount, Boolean voteResult,
                                       int voteAgree, int voteDisagree,
                                       Boolean revoteResult,
                                       int revoteAgree,
                                       int revoteDisagree,
                                       Series.SeriesStatus seriesStatus,
                                       Boolean isPublic,
                                       Boolean isEditable,
                                       Boolean isActive) {
        SeriesResponseDto seriesResponseDto = new SeriesResponseDto();

        seriesResponseDto.setId(id);
        seriesResponseDto.setTitle(title);
        seriesResponseDto.setDaylogCount(daylogCount);
        seriesResponseDto.setCreatedAt(createdAt);
        seriesResponseDto.setModifiedAt(modifiedAt);
        seriesResponseDto.setVoteCount(voteCount);
        seriesResponseDto.setVoteResult(voteResult);
        seriesResponseDto.setVoteAgree(voteAgree);
        seriesResponseDto.setVoteDisagree(voteDisagree);
        seriesResponseDto.setVoteResult(revoteResult);
        seriesResponseDto.setRevoteAgree(revoteAgree);
        seriesResponseDto.setRevoteDisagree(revoteDisagree);
        seriesResponseDto.setSeriesStatus(seriesStatus);
        seriesResponseDto.setIsPublic(isPublic);
        seriesResponseDto.setIsEditable(isEditable);
        seriesResponseDto.setIsActive(isActive);

        return seriesResponseDto;
    }
}
