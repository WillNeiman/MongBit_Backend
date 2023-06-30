package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestCoverResponse<T> {
    //Test
    private List<T> testCoverDTOList;
    private boolean hasNextPage;
}
