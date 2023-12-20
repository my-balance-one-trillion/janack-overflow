package com.example.janackoverflow.global.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDTO {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
