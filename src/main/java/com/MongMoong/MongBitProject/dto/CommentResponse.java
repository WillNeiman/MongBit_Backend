package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentResponse<T> {
    private List<T> commentDTOList;
    private boolean hasNextPage;
}
