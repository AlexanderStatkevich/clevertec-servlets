package com.statkevich.receipttask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageDto<T> {

    private long number;

    private long size;

    @JsonProperty(value = "total_pages")
    private long totalPages;

    private boolean first;

    @JsonProperty(value = "number_of_elements")
    private long numberOfElements;

    private boolean last;

    private List<T> content;
}
