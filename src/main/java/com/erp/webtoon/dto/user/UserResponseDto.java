package com.erp.webtoon.dto.user;

import com.erp.webtoon.domain.Qualification;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class UserResponseDto {

    private String employeeId; // 사번

    private String LoginId; // 아이디

    private String name;    // 이름

    private String email;   // 이메일

    private String tel; // 전화번호

    private LocalDate birthDate;   // 생년월일

    private String deptName;    //부서명

    private int teamNum;    // 팀 번호

    private String position;    // 직급

    private LocalDate joinDate;    // 입사날짜

    private int dayOff;     // 연차개수

    private List<QualificationResponseDto> qualifications;     // 자격증들
}
