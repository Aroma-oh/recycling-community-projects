package com.ssts.ssts.domain.series.service;

import com.ssts.ssts.domain.series.entity.Series;
import com.ssts.ssts.domain.series.repository.SeriesRepository;
import com.ssts.ssts.domain.series.response.vote.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final SeriesRepository seriesRepo;


    //투표 생성
    public Object createVote(Long seriesId){

        Series targetSeries = findSeriesById(seriesId); //투표를 생성할 Series Entity 찾기

        //투표에 따른 상태값 변경
        targetSeries.setPublic(true); //시리즈 공개
        targetSeries.setEditable(false); //타이틀 수정 불가
        targetSeries.setActive(true); //활성 상태
        targetSeries.setSeriesStatus(Series.VoteStatus.SERIES_SLEEP); //투표중 할당
        targetSeries.setVoteCount(targetSeries.getVoteCount()+1); //최초투표이든, 아니든 +1 //투표함을 만들 때, voteCount가 증가

            //투표 생성시간 할당
        targetSeries.setVoteCreatedAt(LocalDateTime.now());
            //투표 마감기간 (2일) 할당
        //targetSeries.setVoteEndAt(targetSeries.getVoteCreatedAt().plusDays(2));

        targetSeries.setVoteEndAt(targetSeries.getVoteCreatedAt().plusSeconds(15));
        //ㄴ> 테스트 마감기간: 15초

       seriesRepo.save(targetSeries);

        return voteCountResponse(seriesId, targetSeries);




    }







    @NotNull //내부 로직: 시리즈 Id를 통해 Series 객체 꺼내기
    private Series findSeriesById(Long seriesId) {
        Optional<Series> findSeries = seriesRepo.findById(seriesId);
        Series targetSeries = findSeries.get();
        return targetSeries;
    }



    //내부 로직: 투표 횟수별 검증 메소드 (투표 전체 정보)
    public Object voteCountResponse(Long seriesId, Series targetSeries) {
        //이제 voteCount++ 된 상태이기 때문에 여기서부터 최초투표인지 재투표인지 알 수 있음

        if(targetSeries.getVoteCount() == 1){
            return VoteResponse.FirstVoteResponse.of(
                    seriesId,
                    targetSeries.getVoteCount(),
                    targetSeries.getVoteResult(),
                    targetSeries.getVoteAgree(),
                    targetSeries.getVoteDisagree(),
                    targetSeries.getVoteStatus(),
                    targetSeries.getVoteCreatedAt(),
                    targetSeries.getVoteEndAt()
            );

        }

        else if(targetSeries.getVoteCount() == 2){
            return VoteResponse.RevoteResponse.of(
                    seriesId,
                    targetSeries.getVoteCount(),
                    targetSeries.getRevoteResult(),
                    targetSeries.getRevoteAgree(),
                    targetSeries.getRevoteDisagree(),
                    targetSeries.getVoteStatus(),
                    targetSeries.getVoteCreatedAt(),
                    targetSeries.getVoteEndAt()
            );
        }

        return null;
    }


}
