package com.example.janackoverflow.saving.domain.request;

import lombok.Getter;

@Getter
public class UpdateAccountStatusRequestDTO {

    private String status;

    public void updateStatus(String newStatus){
        this.status = newStatus;
    }

}
