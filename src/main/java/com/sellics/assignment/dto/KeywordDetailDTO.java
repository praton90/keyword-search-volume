package com.sellics.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordDetailDTO {

    private String keyword;
    private Integer score;
}
