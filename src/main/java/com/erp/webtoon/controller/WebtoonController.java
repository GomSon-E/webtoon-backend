package com.erp.webtoon.controller;

import com.erp.webtoon.dto.webtoon.WebtoonDtResponseDto;
import com.erp.webtoon.service.FileService;
import com.erp.webtoon.service.WebtoonService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final FileService fileService;

    /**
     * 특정 웹툰 조회
     */
    @GetMapping("/webtoon/{webtoonId}")
    public ResponseEntity<Result> getWebtoon(@PathVariable("webtoonId") Long webtoonId) throws MalformedURLException {
        WebtoonDtResponseDto webtoon = webtoonService.getOneWebtoon(webtoonId);

        // 썸네일 이미지 조회
        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(webtoon.getThumbnailFileName()));

        return ResponseEntity.ok(new Result(resource, webtoon));

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T Resource;
        private T info;
    }
}
