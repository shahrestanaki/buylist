package com.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_Date", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_Date")
    private Date changeDate;

    @Column(name = "enabled", nullable = false)
    private Boolean active;

    public void setCreateDate(Date createDate) {
        this.createDate = new Date();
    }

    public void setActive(Boolean active) {
        this.active = true;
    }
}
