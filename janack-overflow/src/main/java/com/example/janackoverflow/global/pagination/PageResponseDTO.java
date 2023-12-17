package com.example.janackoverflow.global.pagination;

import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class PageResponseDTO<T> {
    private List<T> data;
    private PageDTO pageDTO;
    private List<Integer> pageNumber;

    public PageResponseDTO(List<T> data, Page page, List<Integer> pageNumber) {
        this.data = data;
        this.pageDTO = new PageDTO(page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
        this.pageNumber = pageNumber;
    }

    public PageResponseDTO(List<T> data, Page page){
        this.data = data;
        this.pageDTO = new PageDTO(page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
