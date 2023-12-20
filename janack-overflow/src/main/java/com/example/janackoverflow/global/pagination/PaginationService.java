package com.example.janackoverflow.global.pagination;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Transactional
@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationPageNumber(int currentPageNumber, int totalPages){
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }
    public int currentPageLength(){
        return BAR_LENGTH;
    }
}