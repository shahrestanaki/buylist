package com.model;

import com.tools.CorrectDate;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntityView implements Serializable {
    private static final long serialVersionUID = 1L;
    public Date createDate;
    public String createDateFa;
    public Date changeDate;

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        this.createDateFa = createDate == null ? "" : CorrectDate.miladiToShamsi(createDate, "/");
    }
}
