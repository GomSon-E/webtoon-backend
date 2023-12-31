package com.erp.webtoon.dto.message;

import com.erp.webtoon.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageListDto {

    private String content;         // 메세지 내용

    private Long refId;             // 참조 ID

    private String programId;       // 참조 프로그램ID

    private String sendEmployeeId;  // 발신자 사번

    private String sendName;        // 발신자명

}
