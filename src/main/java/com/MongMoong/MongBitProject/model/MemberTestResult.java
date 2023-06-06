package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("MemberTestResult")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTestResult {
    @Id
    private String id;
    private String memberId;
    private String testId;
    private LocalDateTime testDate;
    private String testResultId;
}
