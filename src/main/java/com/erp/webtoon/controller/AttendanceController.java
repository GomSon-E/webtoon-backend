package com.erp.webtoon.controller;

import com.erp.webtoon.dto.attendance.AttendanceRequestDto;
import com.erp.webtoon.dto.attendance.AttendanceResponseDto;
import com.erp.webtoon.dto.attendance.TotalAttendanceResponseDto;
import com.erp.webtoon.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /*
        출근 & 퇴근
     */
    @PostMapping
    public void addAttendanceRecord(@RequestBody AttendanceRequestDto dto) throws IOException {
        attendanceService.addAttendance(dto);
    }

    /*
        개인 근태 조회
     */
    @GetMapping("/individual/{employeeId}")
    public AttendanceResponseDto getIndividualAttendance(@PathVariable String employeeId) {
        return attendanceService.getIndividualAttendance(employeeId);
    }

    /*
        전체 근태 조회
     */
    @GetMapping("/total")
    public TotalAttendanceResponseDto getTotalAttendance() {
        return attendanceService.getTotalAttendance();
    }

}
