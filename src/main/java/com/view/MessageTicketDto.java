package com.view;

import com.tools.Mobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MessageTicketDto {
    @Mobile
    @NotNull
    private String mobile;

    @NotNull(message = "ارسال مقدار برای تیکت اجباریست")
    private long ticket;

}
