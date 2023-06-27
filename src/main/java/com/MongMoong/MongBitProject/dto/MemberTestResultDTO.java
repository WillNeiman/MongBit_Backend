package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MemberTestResultDTO {

    String testId; // MemberTestResult에서
    String Title; // TestResult에서
    String content; // TestResult에서
    String imageUrl; // Test에서
    LocalDateTime testDate; // MemberTestResult에서
    String testResultId; // MemberTestResult에서
}
