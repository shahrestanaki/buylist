package com.model;

import com.tools.CorrectDate;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntityView implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date createDate;
    private String createDateFa;
    private Date changeDate;
    private Boolean active;

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        this.createDateFa = CorrectDate.miladiToShamsi(createDate, "/");
    }
}
