package com.ssts.ssts.domain.series.controller;


import com.ssts.ssts.global.utils.MultipleResponseDto.PageResponseDto;
import com.ssts.ssts.domain.series.dto.SeriesPostDto;
import com.ssts.ssts.domain.series.dto.SeriesResponseDto;
import com.ssts.ssts.domain.series.dto.SeriesUpdateDto;
import com.ssts.ssts.domain.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
@RequiredArgsConstructor
public class SeriesController {


    private final SeriesService seriesService;


    @GetMapping("/members")
    public ResponseEntity getSeriesList(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "12") int size){

        PageResponseDto response = seriesService.getSeriesList(page-1, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity getMainSeriesList(@RequestParam(value = "sort", defaultValue = "newest") String sort, @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "12") int size){
        if("votes".equals(sort)){
            PageResponseDto response = seriesService.getMainSeriesListByVotes(page-1, size);
        }

        PageResponseDto response = seriesService.getMainSeriesListByNewest(page-1, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{series-id}")
    public ResponseEntity getSeries(@PathVariable("series-id") Long id){

        SeriesResponseDto response = seriesService.getSeries(id);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity createSeries(@RequestBody SeriesPostDto seriesPostDto){

        SeriesResponseDto response = seriesService.saveSeries(seriesPostDto);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{series-id}")
    public ResponseEntity updateSeries(@RequestBody
    SeriesUpdateDto seriesUpdateDto){

        SeriesResponseDto response = seriesService.updateSeries(seriesUpdateDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{series-id}")
    public ResponseEntity deleteSeries(@PathVariable("series-id") Long id){

        seriesService.deleteSeries(id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
