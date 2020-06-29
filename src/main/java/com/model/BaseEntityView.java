package com.model;

import com.googlecode.jmapper.annotations.JMap;
import com.tools.CorrectDate;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntityView implements Serializable {
    private static final long serialVersionUID = 1L;
    @JMap
    public Date createDate;
    public String createDateFa;
    @JMap
    public Date changeDate;

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        this.createDateFa = CorrectDate.miladiToShamsi(createDate, "/");
    }
}
