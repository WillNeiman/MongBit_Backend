package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberTestResultResponse<T> {
    private List<T> memberTestResultDTOList;
    private boolean hasNextPage;
}
