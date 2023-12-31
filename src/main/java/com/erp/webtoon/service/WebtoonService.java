package com.erp.webtoon.service;

import com.erp.webtoon.domain.File;
import com.erp.webtoon.domain.Webtoon;
import com.erp.webtoon.dto.webtoon.WebtoonDtListDto;
import com.erp.webtoon.dto.webtoon.WebtoonListResponseDto;
import com.erp.webtoon.dto.webtoon.WebtoonRequestDto;
import com.erp.webtoon.dto.webtoon.WebtoonResponseDto;
import com.erp.webtoon.dto.webtoon.WebtoonUpdaateDto;
import com.erp.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final FileService fileService;

    /**
     * 등록 웹툰 생성
     */
    @Transactional
    public Long save(WebtoonRequestDto dto, MultipartFile thumbnailFile) throws IOException {
        Webtoon webtoon = dto.toEntity();

        if(thumbnailFile != null) {
            File uploadfile = fileService.save(thumbnailFile);
            uploadfile.updateFileWebtoon(webtoon);
            webtoon.getFiles().add(uploadfile);
        }

        webtoonRepository.save(webtoon);
        return webtoon.getId();
    }

    /**
     * 등록 웹툰 전체 조회 (List)
     */
    public List<WebtoonListResponseDto> getAllWebtoon() {

        List<WebtoonListResponseDto> webtoonList = webtoonRepository.findAll(Sort.by("category")).stream()
                .map(WebtoonListResponseDto::new)
                .collect(Collectors.toList());

        return webtoonList;
    }

    /**
     * 웹툰 카드뷰 조회 -> 임시와 최종 나눠서 각각 6개씩
     * 주차별로 나눠야 함...주차를 뭘로 판단하지..
     */

    /**
     * 등록 웹툰 개별 상세 조회
     */
    public WebtoonResponseDto getOneWebtoon(Long webtoonId) {
        Webtoon findWebtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 웹툰입니다."));

        List<WebtoonDtListDto> episodeDtos = findWebtoon.getWebtoonDts().stream()
                .map(webtoonDt -> WebtoonDtListDto.builder()
                        .episodeNum(webtoonDt.getEpisodeNum())
                        .subTitle(webtoonDt.getSubTitle())
                        .uploadDate(LocalDate.now())
                        .manager(webtoonDt.getManager())
                        .finalUploadYN(false).build())
                .collect(Collectors.toList());

        return WebtoonResponseDto.builder()
                .title(findWebtoon.getTitle())
                .artist(findWebtoon.getArtist())
                .illustrator(findWebtoon.getIllustrator())
                .intro(findWebtoon.getIntro())
                .category(findWebtoon.getCategory())
                .keyword(findWebtoon.getKeyword())
                .thumbnailFileName(findWebtoon.getFiles().get(findWebtoon.getFiles().size()-1).getFileName())   // 저장된 썸네일 파일 중 가장 마지막 썸네일 파일
                .episode(episodeDtos).build();
    }

    /**
     * 등록 웹툰 수정
     */
    @Transactional
    public void update(Long webtoonId, MultipartFile file, WebtoonUpdaateDto dto) throws IOException {
        Webtoon findWebtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 웹툰입니다."));


        findWebtoon.updateInfo(dto.getTitle(), dto.getIntro(), dto.getArtist(), dto.getIllustrator(), dto.getCategory(), dto.getKeyword());

        //파일 업데이트
        //만약 파일을 업데이트 하는 경우
        if (file != null) {
            // 기존의 저장된 가장 최근의 파일 상태 변경
            File oldFile = findWebtoon.getFiles().get(findWebtoon.getFiles().size()-1);
            fileService.changeStat(oldFile.getId());

            File uploadFile = fileService.save(file);
            uploadFile.updateFileWebtoon(findWebtoon);
            findWebtoon.getFiles().add(uploadFile);
        }
    }
}
